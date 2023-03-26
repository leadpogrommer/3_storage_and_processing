@file:Suppress("UNCHECKED_CAST")

package t1

import java.io.FileInputStream
import javax.xml.stream.XMLEventReader
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLStreamConstants
import javax.xml.stream.events.StartElement
import javax.xml.stream.events.XMLEvent

const val MALE = "m"
const val FEMALE = "f"

data class Person(
    var id: String? = null,
    var name: String? = null,
    var gender: String? = null,
    var spouse: MutableSet<String> = mutableSetOf(),
    var children: MutableSet<String> = mutableSetOf(),
    var parents: MutableSet<String> = mutableSetOf(),
    var siblings: MutableSet<String> = mutableSetOf(),
    var childrenNumber: Int? = null,
    var siblingNumber: Int? = null,
){
    fun merge(other: Person): Person{
        return Person().also {
            it.id = this.id ?: other.id
            it.name = this.name ?: other.name
            it.gender = this.gender ?: other.gender
            it.spouse =
        }
    }
}
data class Element(val name: String, val data: String?, val attributes: Map<String, String>, val children: List<Element>){
    fun child(name: String): Element?{
        return children.find { it.name == name }
    }
}
data class Selector(var id: String? = null, var name: String? = null)

fun parseElement(start: StartElement, reader: XMLEventReader): Element{
    val children = mutableListOf<Element>()
    var content = ""
    for(event in (reader as Iterator<XMLEvent>)){
        when(event.eventType){
            XMLStreamConstants.START_ELEMENT -> {
                val e = event.asStartElement()
                children.add(parseElement(e, reader))
            }
            XMLStreamConstants.CHARACTERS -> {
                content = event.asCharacters().data
            }
            XMLStreamConstants.END_ELEMENT -> {
                return Element(
                    start.name.toString(),
                    if(content.isEmpty()) null else content.replace("\\s+".toRegex(), " ").trim(),
                    start.attributes.asSequence().map { it.name.toString() to it.value.toString() }.toMap(),
                    children,
                )
            }
        }
    }
    throw java.lang.Exception("Invalid XML")
}

fun sanitizeName(name: String?): String?{
    return name?.replace("\\s+".toRegex(), " ")?.trim()?.let { if(it.split(" ").size == 2) it else null }
}

class People{
    private val people = arrayListOf<Person>()
    private val mapByName = mutableMapOf<String, Person>()
    private val mapById = mutableMapOf<String, Person>()
    var createUnknown = false
    var nextID = 1

    fun create(): Person{
        return Person().also {
            people.add(it)
        }
    }

    fun query(id: String? = null, name: String? = null): Person? {
        val name = sanitizeName(name)
        if(createUnknown && name != null && mapByName[name] == null && id == null){
            return create().also {
                it.name = name
                it.id = "UNK${nextID++}"
                rehash(it)
            }
        }
        return id?.let { mapById[it] } ?: name?.let { mapByName[it] }
    }

    fun query(selector: Selector): Person? {
        return query(selector.id, selector.name)
    }

    fun rehash(p: Person){
        p.name?.let {mapByName[it] = p}
        p.id?.let { mapById[it] = p }
    }

    fun all(): List<Person>{
        return  people
    }
}

fun getIdAndName(e: Element): Pair<String?, String?>{
    // get id
    var id: String? = e.attributes["id"]
    if(id == null){
        id = e.children.find { it.name == "id" }?.attributes?.get("value")
    }

    val fullname_tag = e.child("fullname")
    val fname = fullname_tag?.child("first")?.data ?: e.child("firstname")?.let { it.attributes["value"] ?: it.data }
    val lname = fullname_tag?.child("family")?.data ?: e.child("family-name")?.data ?: e.child("surname")?.attributes?.get("value")

    val name = (e.attributes["name"] ?: if(fname != null && lname != null) "$fname $lname" else null)?.replace("\\s+".toRegex(), " ")?.strip()

    return  id to name
}



fun main(array: Array<String>){
    val xmlInputFactory = XMLInputFactory.newFactory()
    var reader = xmlInputFactory.createXMLEventReader(FileInputStream("people.xml"))

    val people = People()

    for(event in (reader as Iterator<XMLEvent>)){
        if(event.eventType == XMLStreamConstants.START_ELEMENT && event.asStartElement().name.toString() == "person"){
            val e = parseElement(event.asStartElement(), reader)

            val (id, name) = getIdAndName(e)

            val person = id?.let { _ ->
                people.query(id, null)
            } ?: name?.let { _ ->
                val p = people.query(null, name)
                if(p?.id != null && id != null && p.id != id){
                    println("WARNING: people with same name and different id detected: $name")
                    val np = people.create()
                    np.id = id
                    np
                }else {
                    p
                }
            } ?: people.create().also {
                it.name = name
                it.id = id
            }

            if (name == null && id == null){
                println("WARNING: no name and no id. We are doomed!")
            }

            person.name = person.name ?: name
            person.id = person.id ?: id

            people.rehash(person)
        }
    }

    val plist = people.all()

    println(plist.filter { it.id == null })

    people.createUnknown = true

    // second pass
    reader = xmlInputFactory.createXMLEventReader(FileInputStream("people.xml"))
    for(event in (reader as Iterator<XMLEvent>)){
        if(event.eventType == XMLStreamConstants.START_ELEMENT && event.asStartElement().name.toString() == "person"){
            val e = parseElement(event.asStartElement(), reader)

            val person = getIdAndName(e).let { (id, name) ->
                people.query(id, name)
            }!!
            val selfID = person.id!!

            fun setGender(p: Person, gender: String?){
                if(gender == null)return
                if (p.gender != null && p.gender != gender){
                    println("WARNING: conflicting genders")
                }
                p.gender = gender
            }

            fun addChild(id: String, gender: String?){
                person.children.add(id)
                people.query(id)?.let {
                    it.parents.add(selfID)
                    setGender(it, gender)
                }
            }

            fun addParent(id: String, gender: String?){
                person.parents.add(id)
                people.query(id)?.let {
                    it.children.add(selfID)
                    setGender(it, gender)
                }
            }

            fun addSibling(id: String, gender: String?){
                person.siblings.add(id)
                people.query(id)?.let {
                    it.siblings.add(selfID)
                    setGender(it, gender)
                }
            }

            fun setSpouse(id: String, gender: String?){
//                val spouse = people.query(id)!!
//                if ((person.spouse != null && person.spouse != id) || (spouse.spouse != null && spouse.spouse != selfID)){
//                    println("WARNING: conflicting spouses")
//                }
//                person.spouse = id
//                spouse.spouse = selfID
//                setGender(spouse, gender)
                val spouse = people.query(id)!!
                spouse.spouse.add(selfID)
                person.spouse.add(id)
                setGender(spouse, gender)
            }


            // children
            val children_tag = e.child("children")
            children_tag?.children?.forEach { tag ->
                when(tag.name){
                    "daughter" -> {
                        addChild(tag.attributes["id"]!!, FEMALE)
                    }
                    "son" -> {
                        addChild(tag.attributes["id"]!!, MALE)
                    }
                    "child" -> {
                        addChild(people.query(name=tag.data)!!.id!!, null)
                    }
                }
            }
            // children-number
            e.child("children-number")?.attributes?.get("value")?.let { person.childrenNumber = it.toInt() }

            // father
            e.child("father")?.data?.let {
                addParent(people.query(name = it)!!.id!!, MALE)
            }

            // mother
            e.child("mother")?.data?.let {
                addParent(people.query(name = it)!!.id!!, FEMALE)
            }

            // parent (processing only value attribute, tag data always set to UNKNOWN)
            e.child("parent")?.data?.let {
                if(it != "UNKNOWN"){
                    addChild(it, null)
                }
            }

            // gender
            e.child("gender")?.let { it.attributes["value"] ?: it.data }?.uppercase()?.get(0)?.let {
                when(it){
                    'M' -> setGender(person, MALE)
                    'F' -> setGender(person, FEMALE)
                    else -> println("WARNING: unknown gender")
                }
            }

            // husband
            e.child("husband")?.attributes?.get("value")?.let {
                setSpouse(it, MALE)
            }

            // wife
            e.child("wife")?.attributes?.get("value")?.let {
                setSpouse(it, FEMALE)
            }

            // spouce (spouse)
            e.child("spouce")?.attributes?.get("value")?.let{if(it == "NONE") null else it}?.let {
                val spouse = people.query(name = it)
                setSpouse(spouse!!.id!!, null)
            }

            // siblings
            val siblings_tag = e.child("siblings")
            siblings_tag?.attributes?.get("val")?.split("\\s+".toRegex())?.filter { it.isNotBlank() }?.forEach {
                addSibling(it, null)
            }
            siblings_tag?.children?.forEach { tag ->
                when(tag.name){
                    "brother" -> addSibling(people.query(name = tag.data)!!.id!!, MALE)
                    "sister" -> addSibling(people.query(name = tag.data)!!.id!!, FEMALE)
                    else -> println("WARNING: unknown sibling subtag")
                }
            }

            e.child("siblings-number")?.attributes?.get("value")?.let {
                person.childrenNumber = it.toInt()
            }
        }
    }

//    println(plist)
    println(plist.size)
    println(plist.filter { it.gender == null }.size)
    plist.forEach {
        if (it.spouse.isEmpty())return@forEach
        if(it.spouse.count { it.startsWith("P") } == 1)return@forEach
        println("WARNING: BULLSHIT: ${it.spouse.map { "$it ${people.query(it)}" }}")
    }
}



/*
<person name="Tonya Loschiavo">
    <id value="P390327"/>
    <siblings val="P390329"/>
  </person>

  <person name="Tonya Loschiavo">
    <id value="P385164"/>
    <surname value="Loschiavo"/>
    <children-number value="0"/>
  </person>
 */
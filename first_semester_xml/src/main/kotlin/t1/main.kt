package t1

import java.io.File
import java.io.FileInputStream
import javax.xml.XMLConstants
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller
import javax.xml.bind.SchemaOutputResolver
import javax.xml.parsers.SAXParserFactory
import javax.xml.transform.Result
import javax.xml.transform.stream.StreamResult
import javax.xml.validation.SchemaFactory


fun main(){
    val parser = SAXParserFactory.newInstance().newSAXParser()
    val inputStr = FileInputStream(File("./people.xml"))
    val handler = PersonHandler()
    parser.parse(inputStr, handler)

    val people1 = handler.personList
    val (mergedById, unmergedById) = mergeByIds(people1)
    println("Merge by IDs: id count = ${mergedById.size}, unmerged = ${unmergedById.size}")

    val (finalMerged, unmergedByName) = mergeByNames(mergedById, unmergedById)
    println("Merge by Name: unmerged = ${unmergedByName.size}")



    var finalUnmerged = unmergedByName

    while (true){
        val newUnmerged =  mergeKostyl(finalMerged, finalUnmerged.toMutableList())
        propagateInfo(finalMerged)
        if(finalUnmerged.size == newUnmerged.size){
            break
        }
        finalUnmerged = newUnmerged
    }

    for(i in 0 until 5){
        // I hope it helps
        propagateInfo(finalMerged)
    }

    println("Unused records:")
    debugPrint(finalUnmerged)


    val sanePeople = finalMerged.values.map {person ->
        SanePerson(
            person.id!!,
            person.calculateName(),
            person.gender,
            person.childrenNumber ?: 0,
            person.siblingsNumber ?: 0,
            null,
            null,
            null,
            null,
            emptyList(),
            emptyList(),

        )
    }.sortedBy { it.childrenNumber }

    val sanePeopleById = sanePeople.map { it.id to it }.toMap()

    sanePeople.forEach { sanePerson ->
        val person = finalMerged[sanePerson.id]!!

        sanePerson.husband = (person.husbandId)?.let { sanePeopleById[it]!! }
        sanePerson.wife = (person.wifeId)?.let { sanePeopleById[it]!! }
        sanePerson.father = person.fatherId?.let { sanePeopleById[it]!! }
        sanePerson.mother = person.motherId?.let { sanePeopleById[it]!! }
        sanePerson.childrenIds = person.childrenIds.toSet().map { sanePeopleById[it]!! }
        sanePerson.siblingsIds = person.siblingsIds.toSet().map { sanePeopleById[it]!! }
    }

    println("Inconsistencies:")
    sanePeople.forEach {
        if(it.childrenNumber != it.childrenIds.size){
            println("${it.id} ${it.name}: Expected children number = ${it.childrenNumber}, got ${it.childrenIds.size}")
        }
        if(it.siblingsNumber != it.siblingsIds.size){
            println("${it.id} ${it.name}: Expected sibling number = ${it.siblingsNumber}, got ${it.siblingsIds.size}")
        }
    }

    val data = JAXBContext.newInstance(SanePersonStorage::class.java)
    data.generateSchema(object : SchemaOutputResolver() {
        override fun createOutput(namespaceUri: String, suggestedFileName: String): Result {
            val file = File("output.xsd")
            val result = StreamResult(file)
            result.setSystemId(file.toURI().toURL().toString())
            return result
        }

    })
    val marshaller = data.createMarshaller()
    marshaller.schema = SchemaFactory
        .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
        .newSchema(
            File("./output.xsd")
        )
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
    marshaller.marshal(SanePersonStorage(sanePeople), File("./output.xml"))

}



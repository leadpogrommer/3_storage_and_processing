package t1

import java.io.File
import java.io.FileInputStream
import javax.xml.parsers.SAXParserFactory






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
            person.childrenNumber ?: 0,
            person.siblingsNumber ?: 0,
            person.husbandId ?: person.wifeId,
            person.fatherId,
            person.motherId,
            person.childrenIds.toSet(),
            person.siblingsIds.toSet(),
        )
    }.sortedBy { it.childrenNumber }

    val sanePeopleById = sanePeople.map { it.id to it }.toMap()

    println("Inconsistencies:")
    sanePeople.forEach {
        if(it.childrenNumber != it.childrenIds.size){
            println("${it.id} ${it.name}: Expected children number = ${it.childrenNumber}, got ${it.childrenIds.size}")
        }
        if(it.siblingsNumber != it.siblingsIds.size){
            println("${it.id} ${it.name}: Expected sibling number = ${it.siblingsNumber}, got ${it.siblingsIds.size}")
        }
    }

}



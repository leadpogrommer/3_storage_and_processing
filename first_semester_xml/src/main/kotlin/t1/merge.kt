package t1

fun mergeByIds(people: List<Person>): Pair<Map<String, Person>, List<Person>>{
    val mergedByIds = mutableMapOf<String, Person>()
    val unmerged = mutableListOf<Person>()

    people.forEach { person ->
        if (person.id == null){
            unmerged.add(person)
        }else{
            if(mergedByIds.containsKey(person.id)){
                mergedByIds[person.id]!!.mergePerson(person)
            }else{
                mergedByIds[person.id!!] = person
            }
        }
    }
    return  mergedByIds to unmerged
}
fun mergeByNames(peopleWithIds: Map<String, Person>, unmerged: List<Person>): Pair<Map<String, Person>, List<Person>>{
    val withIdByName = toMapByNames(peopleWithIds.values)

    val stillUnmerged = mutableListOf<Person>()

    unmerged.forEach { person ->
        if (withIdByName.containsKey(person.calculateName()) && withIdByName[person.calculateName()]!!.size == 1){
            withIdByName[person.calculateName()]!![0].mergePerson(person)
        }else{
            stillUnmerged.add(person)
        }
    }

    return peopleWithIds to stillUnmerged
}


fun mergeKostyl(peopleById: Map<String, Person>, unmerged: MutableList<Person>): List<Person>{
    val resultUnmerged = mutableListOf<Person>()
    val peopleByName = toMapByNames(peopleById.values)

    for (person in unmerged){
        val available = peopleByName[person.calculateName()]!!
        // Incomplete records
        if (person.name == null || person.surname == null){
            resultUnmerged.add(person)
            continue
        }
        // Useless records
        if(toDebugMap(person).keys.toSet() == setOf("name", "surname", "sources")){
            println("Meaningless record: ${toDebugMap(person)}")
            continue
        }
        // Some crappy logic
        if(
            person.childrenNumber != null &&
            peopleByName[person.calculateName()]!!.filter { it.childrenNumber == null }.size == 1
        ){
            peopleByName[person.calculateName()]!!.filter { it.childrenNumber == null }[0].mergePerson(person)
            continue
        }

        if(available.count { it.canBeMerged(person) } == 1){
            available.first { it.canBeMerged(person) }.mergePerson(person)
            continue
        }

        resultUnmerged.add(person)
    }

    println(resultUnmerged.size)
    return resultUnmerged
}

fun propagateInfo(peopleById: Map<String, Person>){
    val peopleByName = toMapByNames(peopleById.values)

    // name-id duplicates

    fun uniqueByName(name: String): Person?{
        return peopleByName[name]!!.let {
            if(it.size == 1){
                it[0]
            }else{
                null
            }
        }
    }

    for(person in peopleById.values){
        // wifeId - husbandId - spouceNames - gender
        if(person.calculateName() == "Earline Prins"){
            println("dbg")
        }
        if (person.wifeId != null){
            person.gender =  Gender.M
        }
        if (person.husbandId != null){
            person.gender =  Gender.F
        }
        if (person.wifeId != null && person.spouceNames.isEmpty()){
            person.spouceNames.add(peopleById[person.wifeId]!!.calculateName())
        }
        if (person.husbandId != null && person.spouceNames.isEmpty()){
            person.spouceNames.add(peopleById[person.husbandId]!!.calculateName())
        }
        if(person.spouceNames.isNotEmpty()){
            val spouse = peopleByName[person.spouceNames[0]]!!.let {
                if(it.size != 1) {
                    null
                }else{
                    it[0]
                }

            }
            if(spouse != null) {
                when (person.gender) {
                    Gender.F -> person.husbandId = spouse.id
                    Gender.M -> person.wifeId = spouse.id
                    else -> {}
                }
            }
        }

        // siblingsIds - brotherNames - sisterNames - brotherIds - sisterIds
        person.brotherIds = person.brotherIds.toSet().union(person.brotherNames.mapNotNull { uniqueByName(it)?.id }).toMutableList()
        person.sisterIds = person.sisterIds.toSet().union(person.sisterNames.mapNotNull { uniqueByName(it)?.id }).toMutableList()
        person.siblingsIds = person.siblingsIds.union(person.brotherIds).union(person.sisterIds).toMutableList()

        person.brotherIds.forEach{
            peopleById[it]!!.gender = Gender.M
        }

        person.sisterIds.forEach{
            peopleById[it]!!.gender = Gender.F
        }

        person.siblingsIds.forEach {
            val sibling = peopleById[it]!!
            when(person.gender){
                Gender.M -> {
                    if(!sibling.brotherIds.contains(person.id)){
                        sibling.brotherIds.add(person.id!!)
                    }
                }
                Gender.F -> {
                    if(!sibling.sisterIds.contains(person.id)){
                        sibling.sisterIds.add(person.id!!)
                    }
                }
                null -> {
                    if(!sibling.siblingsIds.contains(person.id)){
                        sibling.siblingsIds.add(person.id!!)
                    }
                }
            }
        }

        // parentIDs - mother - father
        val possibleMother = person.mother?.let { uniqueByName(it) }
        val possibleFather = person.father?.let { uniqueByName(it) }
        possibleMother?.let {
            person.motherId = it.id
            it.gender = Gender.F
        }
        possibleFather?.let {
            person.fatherId = it.id
            it.gender = Gender.M
        }
        for(pid in person.parentIds){
            val p = peopleById[pid]!!
            when(p.gender){
                Gender.M -> person.fatherId = pid
                Gender.F -> person.motherId = pid
                else -> {}
            }
        }
        person.fatherId?.let {
            distinctAddAll(peopleById[it]!!.childrenIds, listOf(person.id!!))
        }
        person.fatherId?.let {
            distinctAddAll(peopleById[it]!!.childrenIds, listOf(person.id!!))
        }

        // daughterIds - sonIds - childrenNames - childrenIds
        val uniqueChildrenNames = person.childrenNames.mapNotNull { uniqueByName(it)?.id }
        distinctAddAll(person.childrenIds, uniqueChildrenNames)
        distinctAddAll(person.childrenIds, person.sonsIds)
        distinctAddAll(person.childrenIds, person.daugtersIds)

        person.childrenIds.forEach {
            val child = peopleById[it]!!
            when(person.gender){
                Gender.F -> child.motherId = person.id!!
                Gender.M -> child.fatherId = person.id!!
                else -> {}
            }
        }

        person.sonsIds.forEach { peopleById[it]!!.gender = Gender.M }
        person.daugtersIds.forEach { peopleById[it]!!.gender = Gender.F }


    }

}

package t1

import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties

fun distinctAddAll(
    old: MutableList<String>, new: List<String>
): MutableList<String> {
    old.addAll(new)
    return old.distinct().toMutableList()
}

private fun <T> replaceVal(old: T?, new: T?): T? {
    if (old == null) {
        return new
    }
    if (new == null) {
        return old
    }
    if (new == old) {
        return old
    }
    throw Exception("Cannot replace var old: $old != new: $new")
}

fun toMapByNames(people: Collection<Person>): Map<String, List<Person>>{
    val result = mutableMapOf<String, MutableList<Person>>()
    people.forEach { person ->
        if (result.containsKey(person.calculateName())){
            result[person.calculateName()]!!.add(person)
        } else {
            result[person.calculateName()] = mutableListOf(person)
        }
    }
    return result
}



fun toDebugMap(v: Any): Map<String, Any>{
    val res = LinkedHashMap<String, Any>()
    v::class.declaredMemberProperties.forEach {
        val value = (it as KProperty1<Any, *>).get(v)
        if(value == null){
            return@forEach
        }
        if (value is List<*> && value.size != 0){
            res[it.name] = value
        } else if(value !is List<*>) {
            res[it.name] = value
        }
    }
    return res
}
fun debugPrint(v: Collection<Any>, filter: String = ""){
    v.forEach { debugPrint(it, filter) }
}

fun debugPrint(v: Any, filter: String = ""){

    toDebugMap(v).toString().let {
        if(it.contains(filter)){
            println(it)
        }
    }
}

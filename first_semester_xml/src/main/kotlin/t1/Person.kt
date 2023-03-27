package t1

enum class Gender {
    F, M
}

class Person(
    var id: String? = null,
    name: String? = null,
    surname: String? = null,
    var gender: Gender? = null,
    var siblingsNumber: Int? = null,
    var childrenNumber: Int? = null,
    var wifeId: String? = null,
    var husbandId: String? = null,
    var spouceNames: MutableList<String> = mutableListOf(),//+
    var parentIds: MutableList<String> = mutableListOf(),//+
    var siblingsIds: MutableList<String> = mutableListOf(),//+
    var daugtersIds: MutableList<String> = mutableListOf(),
    var sisterNames: MutableList<String> = mutableListOf(),//+
    var brotherNames: MutableList<String> = mutableListOf(),//+
    var childrenNames: MutableList<String> = mutableListOf(),//+
    var sonsIds: MutableList<String> = mutableListOf(),
    var mother: String? = null,//+
    var father: String? = null,//+

    var sisterIds: MutableList<String> = mutableListOf(),
    var brotherIds: MutableList<String> = mutableListOf(),
    // after merge


    var fatherId: String? = null,
    var motherId: String? = null,
    var childrenIds: MutableList<String> = mutableListOf(),//+
    // husbandId
    // wifeID
    // siblingsId

    // debug
    val sources: MutableList<Int> = mutableListOf()

) {
    var name = name
        set(value) {
            field = updateName(field, value)
        }

    var surname = surname
        set(value) {
            field = updateName(field, value)
        }

    fun setFullName(newName: String?) {
        if (newName == null) {
            return
        }
        val t = newName.trim().replace("\\s+".toRegex(), " ").split(" ")
        name = t[0]
        surname = t[1]
    }

    private fun <T> updateName(oldValue: T?, newValue: T?): T? {
        if (oldValue == null) {
            return newValue
        }
        if (newValue == null) {
            return oldValue
        }
        if (oldValue == newValue) {
            return oldValue
        }
        return newValue
    }

    fun mergePerson(other: Person) {
        if (id == null) {
            id = other.id
        }
        name = other.name
        surname = other.surname
        gender = replaceVal(gender, other.gender)
        siblingsNumber = replaceVal(siblingsNumber, other.siblingsNumber)
        childrenNumber = replaceVal(childrenNumber, other.childrenNumber)
        wifeId = replaceVal(wifeId, other.wifeId)
        husbandId = replaceVal(husbandId, other.husbandId)
        mother = replaceVal(mother, other.mother)
        father = replaceVal(father, other.father)
        spouceNames = distinctAddAll(spouceNames, other.spouceNames)
        parentIds = distinctAddAll(parentIds, other.parentIds)
        daugtersIds = distinctAddAll(daugtersIds, other.daugtersIds)
        sisterNames = distinctAddAll(sisterNames, other.sisterNames)
        brotherNames = distinctAddAll(brotherNames, other.brotherNames)
        childrenNames = distinctAddAll(childrenNames, other.childrenNames)
        sonsIds = distinctAddAll(sonsIds, other.sonsIds)
        siblingsIds = distinctAddAll(siblingsIds, other.siblingsIds)

        sources.addAll(other.sources)
    }

    fun canBeMerged(other: Person): Boolean{
        if (other.id != null) return false
        return isValMergable(name, other.name) &&
                isValMergable(surname, other.surname) &&
                isValMergable(gender, other.gender) &&
                isValMergable(siblingsNumber, other.siblingsNumber) &&
                isValMergable(childrenNumber, other.childrenNumber) &&
                isValMergable(wifeId, other.wifeId) &&
                isValMergable(husbandId, other.husbandId) &&
                isValMergable(mother, other.mother) &&
                isValMergable(father, other.father) &&
                isSiblingsCountCompatible(other) &&
                other.isSiblingsCountCompatible(this)

    }

    private fun isSiblingsCountCompatible(other: Person): Boolean{
        if(siblingsNumber == null || other.siblingsNumber != null)return true
        val otherMaxPossibleSiblings = other.brotherIds.toSet().union(other.sisterIds).union(other.siblingsIds).size + other.brotherNames.size + other.sisterNames.size
        if (otherMaxPossibleSiblings > siblingsNumber!!)return false
        return true
    }


    private fun <T> isValMergable(a: T?, b: T?): Boolean{
        return (a == null || b == null || a == b)
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

    fun calculateName() = "$name $surname"

    private fun distinctAddAll(
        old: MutableList<String>, new: MutableList<String>
    ): MutableList<String> {
        old.addAll(new)
        return old.distinct().toMutableList()
    }

    fun getActualChildrenNumber(): Int =
        childrenIds.size + childrenNames.size + daugtersIds.size + sonsIds.size

    fun getActualSiblingsNumber(): Int =
        brotherIds.size + brotherNames.size + sisterIds.size + sisterNames.size
}

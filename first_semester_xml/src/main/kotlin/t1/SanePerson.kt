package t1

import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlAccessorType(XmlAccessType.NONE)
data class SanePerson(
    @field:XmlAttribute
    @field:XmlID
    val id: String,

    @field:XmlAttribute
    val name: String,

    @field:XmlElement
    val gender: Gender?,

    val childrenNumber: Int,
    val siblingsNumber: Int,

    var wife: SanePerson?,
    var husband: SanePerson?,

    var father: SanePerson?,
    var mother: SanePerson?,

    var childrenIds: List<SanePerson>,

    var siblingsIds: List<SanePerson>,
){
    @get:XmlElement(name = "spouse")
    val xmlSpouse
        get() = XMLSpouse(
        wife,
        husband
    )

    @get:XmlElement(name="children")
    val xmlChildren
    get() = XMLChildren(
        childrenIds.filter { it.gender == Gender.F },
        childrenIds.filter { it.gender == Gender.M },
        childrenIds.filter { it.gender == null }
    )

    @get:XmlElement(name="siblings")
    val xmlSiblings
            get()= XMLSiblings(
        childrenIds.filter { it.gender == Gender.M },
        childrenIds.filter { it.gender == Gender.F },
        childrenIds.filter { it.gender == null }
    )

    @get:XmlElement(name="parents")
    val xmlParents
    get() = XMLParents(
        mother,
        father
    )

    override fun toString(): String {
        return "ERROR!!!"
    }
}

class XMLSpouse(
    @field:XmlElement
    @field:XmlIDREF
    val wife: SanePerson?,
    @field:XmlElement
    @field:XmlIDREF
    val husband: SanePerson?,
)

class XMLChildren(
    @field:XmlElement
    @field:XmlIDREF
    val daughter: List<SanePerson>,

    @field:XmlElement
    @field:XmlIDREF
    val son: List<SanePerson>,

    @field:XmlElement
    @field:XmlIDREF
    // For children with unknown gender
    val child: List<SanePerson>
)

class XMLSiblings(
    @field:XmlElement
    @field:XmlIDREF
    val brother: List<SanePerson>,

    @field:XmlElement
    @field:XmlIDREF
    val sister: List<SanePerson>,

    @field:XmlElement
    @field:XmlIDREF
    // For siblings with unknown gender
    val sibling: List<SanePerson>
)

class XMLParents(
    @field:XmlElement
    @XmlIDREF
    val mother: SanePerson?,
    @field:XmlElement
    @XmlIDREF
    val father: SanePerson?
)

@XmlRootElement(name = "people")
class SanePersonStorage
    (
    @get:XmlElement(name = "person")
    val personList: List<SanePerson>
) {
    @XmlAttribute
    val count = personList.size

    constructor() : this(emptyList())
}
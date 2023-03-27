package t1

import org.xml.sax.Attributes
import org.xml.sax.Locator
import org.xml.sax.helpers.DefaultHandler
import org.xml.sax.helpers.LocatorImpl

class PersonHandler : DefaultHandler() {
    var personCount = 0
    lateinit var currentPerson: Person
    val personList = mutableListOf<Person>()
    val data = StringBuilder()

    lateinit var locator: Locator

    override fun setDocumentLocator(locator: Locator) {
        super.setDocumentLocator(locator)
        this.locator = locator
    }

    override fun startElement(
        uri: String, localName: String, qName: String, attributes: Attributes
    ) {
        val elementValue = getElementValue(attributes)?.trim()?.replace("\\s+".toRegex(), " ")

        if (elementValue != null) {
            when (qName) {
                "first", "firstname" -> {
                    currentPerson.name = elementValue
                }

                "family-name", "surname", "family" -> {
                    currentPerson.surname = elementValue
                }

                "id" -> {
                    currentPerson.id = elementValue
                }

                "gender" -> {
                    currentPerson.gender = convertGender(elementValue)
                }

                "siblings-number" -> {
                    currentPerson.siblingsNumber = elementValue.toInt()
                }

                "children-number" -> {
                    currentPerson.childrenNumber = elementValue.toInt()
                }

                "wife" -> {
                    currentPerson.wifeId = elementValue
                }

                "husband" -> {
                    currentPerson.husbandId = elementValue
                }

                "spouce" -> {
                    currentPerson.spouceNames.add(elementValue)
                }

                "parent" -> {
                    currentPerson.parentIds.add(elementValue)
                }

                "siblings" -> {
                    currentPerson.siblingsIds.addAll(elementValue.split(" "))
                }

                else -> {
                    println("strange non null $qName")
                }
            }
        } else {
            when (qName) {
                "people" -> {
                    personCount = attributes.getValue("count").toInt()
                }

                "person" -> {
                    currentPerson = Person()
                    currentPerson.sources.add(locator.lineNumber)
                    currentPerson.id = attributes.getValue("id")?.trim()
                    currentPerson.setFullName(attributes.getValue("name")?.trim())
                }

                "daughter" -> {
                    currentPerson.daugtersIds.add(attributes.getValue("id")!!.trim())
                }

                "son" -> {
                    currentPerson.sonsIds.add(attributes.getValue("id")!!.trim())
                }
            }
        }
    }

    override fun endElement(uri: String, localName: String, qName: String) {
        val d = data.toString().trim().replace("\\s+".toRegex(), " ")

        if (d.isNotBlank()) {
            when (qName) {
                "firstname", "first" -> {
                    currentPerson.name = d
                }

                "family-name", "surname", "family" -> {
                    currentPerson.surname = d
                }

                "gender" -> {
                    currentPerson.gender = convertGender(d)
                }

                "mother" -> {
                    currentPerson.mother = d
                }

                "father" -> {
                    currentPerson.father = d
                }

                "sister" -> {
                    currentPerson.sisterNames.add(d)
                }

                "brother" -> {
                    currentPerson.brotherNames.add(d)
                }

                "child" -> {
                    currentPerson.childrenNames.add(d)
                }
            }
        }
        when (qName) {
            "person" -> {
                personList.add(currentPerson)
            }
        }
        data.clear()
    }


    override fun characters(ch: CharArray, start: Int, length: Int) {
        data.appendRange(ch, start, start + length)
    }

    override fun endDocument() {
        println("declared $personCount, got ${personList.size}")
    }

    private fun getElementValue(attributes: Attributes): String? {
        val value = attributes.getValue("val") ?: attributes.getValue("value")
        if (value == null || value == "NONE" || value == "UNKNOWN") {
            return null
        }
        return value
    }

    private fun convertGender(gender: String?): Gender? {
        return when (gender) {
            "F", "female" -> Gender.F
            "M", "male" -> Gender.M
            null -> null

            else -> {
                throw Exception("Strange gender $gender")
            }
        }
    }
}
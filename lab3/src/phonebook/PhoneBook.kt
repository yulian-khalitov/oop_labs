package phonebook

data class PhoneNumber (var number: String, var type: String) : Comparable<PhoneNumber> {
    override fun compareTo(other: PhoneNumber): Int = number.compareTo(other.number)
}

class Contact(var name: String,
              var surname: String) : Comparable<Contact> {
    val phoneNumbers = sortedSetOf<PhoneNumber>()

    override fun compareTo(other: Contact) : Int = "$name $surname".compareTo("${other.name} ${other.surname}")

    fun addPhoneNumber(number : String, type: String) = phoneNumbers.add(PhoneNumber(number, type))

    override fun toString(): String {
        var result = "$name $surname "
        phoneNumbers.forEach { result += "${it.type}: ${it.number} " }
        return result
    }

    fun toStringIndexed() : String {
        var result = "$name $surname "
        phoneNumbers.forEachIndexed { i, phoneNumber -> result += "($i) ${phoneNumber.type}: ${phoneNumber.number} " }
        return result
    }

    fun updatePhoneNumber(index: Int, number: String) {
        phoneNumbers.elementAt(index).number = number
    }
    fun removePhoneNumber(index: Int) {
        phoneNumbers.remove(phoneNumbers.elementAt(index))
    }

    fun updatePhoneNumberType(index: Int, type: String) {
        phoneNumbers.elementAt(index).type = type
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Contact ||
                name != other.name || surname != other.surname || phoneNumbers != other.phoneNumbers) return false
        return true
    }

    override fun hashCode(): Int = listOf(name, surname, phoneNumbers).hashCode()
}

class PhoneBook {
    val contacts = sortedSetOf<Contact>()

    fun addContact(contact: Contact) = contacts.add(contact)

    fun removeContact(index: Int) = contacts.remove(this[index])

    fun find(substr : String) : Set<Contact> {
        val result = mutableSetOf<Contact>()
        contacts.forEach { contact ->
            val stringFields = mutableListOf(contact.name, contact.surname)
            contact.phoneNumbers.forEach { phoneNumber -> stringFields.add(phoneNumber.number) }
            for (str in stringFields) {
                if (str.contains(substr)) {
                    result.add(contact)
                    break
                }
            }
        }
        return result
    }

    operator fun get(i: Int) : Contact = contacts.elementAt(i)

    inline fun forEach(action: (Contact) -> Unit) = contacts.forEach { action(it) }

    inline fun forEachIndexed(action: (index: Int, Contact) -> Unit) = contacts.forEachIndexed { index, contact ->  action(index, contact) }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is PhoneBook ||
                contacts != other.contacts) return false
        return true
    }

    override fun hashCode(): Int = contacts.hashCode()
}
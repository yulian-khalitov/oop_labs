import phonebook.PhoneBook
import phonebook.Contact


fun main() {
    val phoneBook = PhoneBook()

    val contact1 = Contact("Yulian", "Khalitov")
    contact1.addPhoneNumber("89991111111", "MOBILE")
    contact1.addPhoneNumber("89992222222", "MOBILE")
    phoneBook.addContact(contact1)

    val contact2 = Contact("Ivan", "Ivanov")
    contact2.addPhoneNumber("89993333333", "MOBILE")
    phoneBook.addContact(contact2)

    println("Welcome to PHONEBOOK app!")
    while (true) {
        println("Options: ")
        println("1 - add contact")
        println("2 - edit contact")
        println("3 - print all contacts")
        println("4 - find contact")
        println("5 - delete contact")
        println("6 - exit")
        print("Enter option: ")
        when (readLine()!!) {
            "1" -> {
                print("Enter name: ")
                val name = readLine()!!
                print("Enter surname: ")
                val surname = readLine()!!
                val contact = Contact(name, surname)

                if (phoneBook.addContact(contact)) {
                    print("Wanna enter a phone number? [y/n]: ")
                    var yesNoOption = readLine()!!
                    while (yesNoOption == "y") {
                        print("Enter phone number: ")
                        val number = readLine()!!
                        print("Select number type [MOBILE, HOME, JOB, etc...]: ")
                        val numberType = readLine()!!
                        if (!contact.addPhoneNumber(number, numberType))
                            println("ERROR: Contact already contain phone number $number ($numberType)!")
                        print("Wanna enter a phone number? [y,n]: ")
                        yesNoOption = readLine()!!
                    }
                } else {
                    println("ERROR: Phonebook already contain contact $contact!")
                }
            }
            "2" -> {
                phoneBook.forEachIndexed { i, contact -> println("$i ${contact.toStringIndexed()}") }
                print("Enter contact index: ")
                val index = readLine()!!.toInt()
                val contact = phoneBook[index]
                println("1 - change name")
                println("2 - change surname")
                println("3 - change phone number")
                println("4 - change phone number type")
                println("5 - delete phone number")
                println("6 - add phone number")
                print("Enter option: ")
                val editOption = readLine()!!
                when (editOption) {
                    "1" -> {
                        print("Enter new name: ")
                        contact.name = readLine()!!
                    }
                    "2" -> {
                        print("Enter new surname: ")
                        contact.surname = readLine()!!
                    }
                    "3" -> {
                        print("Enter phone number index to change: ")
                        val i = readLine()!!.toInt()
                        print("Enter new phone number: ")
                        contact.updatePhoneNumber(i, readLine()!!)
                    }
                    "4" -> {
                        print("Enter phone number index to change: ")
                        val i = readLine()!!.toInt()
                        print("Enter new phone number type: ")
                        contact.updatePhoneNumberType(i, readLine()!!)
                    }
                    "5" -> {
                        print("Enter phone number index to remove: ")
                        val i = readLine()!!.toInt()
                        contact.removePhoneNumber(i)
                    }
                    "6" -> {
                        print("Enter phone number: ")
                        val number = readLine()!!
                        print("Select number type [MOBILE, HOME, JOB, etc...]: ")
                        val numberType = readLine()!!
                        if (!contact.addPhoneNumber(number, numberType))
                            println("ERROR: Contact already contain phone number $number ($numberType)!")
                    }
                }
            }
            "3" -> {
                phoneBook.forEach { println(it) }
            }
            "4" -> {
                print("Enter substring: ")
                val substr = readLine()!!
                phoneBook.find(substr).forEach { println(it) }
            }
            "5" -> {
                phoneBook.forEachIndexed { i, contact -> println("$i $contact") }
                print("Enter contact index to delete: ")
                val i = readLine()!!.toInt()
                phoneBook.removeContact(i)
            }
            "6" -> break
        }
    }
}
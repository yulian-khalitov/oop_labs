package view

import ezvcard.VCard
import ezvcard.parameter.TelephoneType
import ezvcard.property.Address
import ezvcard.property.Birthday
import ezvcard.property.StructuredName
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.*
import vcards.VCardSerial
import java.time.LocalDate
import java.time.ZoneId

class ContactView(is_new: Boolean = true) : View() {
    data class WrappedPhoneNumber (var number: SimpleStringProperty, var type: SimpleStringProperty)
    val vcard = if (is_new) VCard() else find<MainView>().selectedVCard.value
    var nameField = SimpleStringProperty()
    var surnameField = SimpleStringProperty()
    var patronymicField = SimpleStringProperty()
    var birthdayField = SimpleObjectProperty<LocalDate>()

    val emailsList = FXCollections.observableArrayList<SimpleStringProperty>()
    val phoneNumberList = FXCollections.observableArrayList<WrappedPhoneNumber>()
    val phoneNumberTypes = FXCollections.observableArrayList("VOICE", "HOME", "WORK")

    var streetAddressField = SimpleStringProperty()
    var localityField = SimpleStringProperty()
    var countryField = SimpleStringProperty()
    val infoField = SimpleStringProperty()

    init {
        vcard.apply {
            emails.forEach {
                emailsList.add(SimpleStringProperty(it.value))
            }
            telephoneNumbers.forEach {
                phoneNumberList.add(
                    WrappedPhoneNumber(
                        SimpleStringProperty(it.text),
                        SimpleStringProperty(it.types.firstOrNull()?.toString()?.toUpperCase())
                    )
                )
            }
        }
    }

    override val root = form {
        fieldset {
            field("Name") {
                textfield(nameField) {
                    text = vcard.structuredName?.given
                }
            }
            field("Surname") {
                textfield(surnameField) {
                    text = vcard.structuredName?.family
                }
            }
            field("Patronymic") {
                textfield(patronymicField) {
                    text = vcard.structuredName?.additionalNames?.firstOrNull()
                }
            }
            field("Birthday") {
                datepicker(birthdayField) {
                    useMaxWidth = true
                    // convert Date to LocalDate
                    value = vcard.birthday?.date?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()
                }
            }
        }
        fieldset {
            bindChildren(emailsList) {
                field ("Email") {
                    textfield(it)
                    button("delete") {
                        action {
                            emailsList.remove(it)
                        }
                    }
                }
            }
        }
        fieldset {
            bindChildren(phoneNumberList) {
                field ("Phone Number") {
                    combobox(it.type) {
                        items = phoneNumberTypes
                    }
                    textfield(it.number)
                    button("delete") {
                        action {
                            phoneNumberList.remove(it)
                        }
                    }
                }
            }
        }
        fieldset("Address") {
            field("Street") {
                textfield(streetAddressField) {
                    text = vcard.addresses.firstOrNull()?.streetAddress
                }
            }
            field("Locality") {
                textfield(localityField) {
                    text = vcard.addresses.firstOrNull()?.locality
                }
            }
            field("Country") {
                textfield(countryField) {
                    text = vcard.addresses.firstOrNull()?.country
                }
            }
        }
        form {
            label(infoField)
            button("add email") {
                useMaxWidth = true
                action {
                    emailsList += SimpleStringProperty()
                    currentWindow?.sizeToScene()
                }
            }
            button("add phone number") {
                useMaxWidth = true
                action {
                    phoneNumberList += WrappedPhoneNumber(
                        SimpleStringProperty(),
                        SimpleStringProperty("VOICE")
                    )
                    currentWindow?.sizeToScene()
                }
            }
            button("Save") {
                useMaxWidth = true
                action {
                    if (nameField.value != null && nameField.value.isNotEmpty()) {
                        val name = StructuredName()
                        name.given = nameField.value
                        name.family = surnameField.value
                        patronymicField.value?.let {
                            name.additionalNames.add(it)
                        }
                        vcard.structuredName = name

                        birthdayField.value?.let {
                            // convert LocalDate to Date
                            it.atStartOfDay()?.atZone(ZoneId.systemDefault())?.toInstant()?.let {
                                vcard.birthday = Birthday(java.util.Date.from(it))
                            }
                        }

                        vcard.emails.clear()
                        emailsList.forEach {
                            it.value?.let {
                                if (it.isNotEmpty()) {
                                    vcard.addEmail(it)
                                }
                            }
                        }

                        vcard.telephoneNumbers.clear()
                        phoneNumberList.forEach { phoneNumber ->
                            phoneNumber.number.value?.let { number ->
                                if (number.isNotEmpty()) {
                                    vcard.addTelephoneNumber(number, TelephoneType.find(phoneNumber.type.value))
                                }
                            }
                        }

                        val address = Address()
                        address.streetAddress = streetAddressField.value
                        address.locality = localityField.value
                        address.country = countryField.value
                        vcard.addresses.clear()
                        vcard.addAddress(address)
                        find<MainView>().apply {
                            if (is_new) {
                                vcards.add(vcard)
                            }
                            vcards[vcards.indexOf(vcard)] = vcard // force listview update
                            VCardSerial.save(vcards)
                        }
                        close()
                    } else {
                        infoField.value = "Name is required!"
                    }
                }
            }
        }
    }
}
package view

import tornadofx.*
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.scene.text.FontWeight
import ezvcard.VCard
import vcards.VCardSerial
import java.text.SimpleDateFormat


class MainView : View("Contacts App") {
    val selectedVCard = SimpleObjectProperty<VCard>()
    val vcards = FXCollections.observableArrayList(VCardSerial.read())

    override val root = form {
        listview(vcards) {
            prefWidth = 400.0
            cellFormat { vcard ->
                graphic = form {
                    fieldset {
                        label {
                            style {
                                fontSize = 22.px
                                fontWeight = FontWeight.BOLD
                            }
                            val n = vcard.structuredName
                            text = "${n?.given ?: ""} ${n?.family ?: ""} ${n?.additionalNames?.firstOrNull() ?: ""}"
                        }
                        field("Phone Numbers") {
                            label {
                                vcard.telephoneNumbers.forEach {
                                    text += "${it.text ?: ""} (${it.types.firstOrNull()?.value ?: ""})\n"
                                }
                            }
                        }
                        field("Emails") {
                            label {
                                vcard.emails.forEach {
                                    it.value?.let {
                                        text += it + '\n'
                                    }
                                }
                            }
                        }
                        field("Birthday") {
                            label {
                                val format = SimpleDateFormat("dd/MM/yyy")
                                text = vcard.birthday?.let { format.format(it.date) }
                            }
                        }
                    }
                    fieldset("Address") {
                        field("Street") {
                            label {
                                text = vcard.addresses?.firstOrNull()?.streetAddress
                            }
                        }
                        field("Locality") {
                            label {
                                text = vcard.addresses?.firstOrNull()?.locality
                            }
                        }
                        field("Country") {
                            label {
                                text = vcard.addresses?.firstOrNull()?.country
                            }
                        }
                    }
                }
            }
            bindSelected(selectedVCard)
        }
        button("New") {
            useMaxWidth = true
            action {
                ContactView().openWindow()
            }
        }
        button("Edit") {
            useMaxWidth = true
            action {
                val vcard = selectedVCard.value
                val index = vcards.indexOf(vcard)
                if (index >= 0 && index < vcards.size) {
                    ContactView(is_new = false).openWindow()
                }
            }
        }
        button("Delete") {
            useMaxWidth = true
            action {
                val vcard = selectedVCard.value
                val index = vcards.indexOf(vcard)
                if (index >= 0 && index < vcards.size) {
                    vcards.remove(vcard)
                    VCardSerial.save(vcards)
                }
            }
        }
        button("Import VCard") {
            useMaxWidth = true
            action {
                val vcard = selectedVCard.value
                val index = vcards.indexOf(vcard)
                if (index >= 0 && index < vcards.size) {
                    VCardSerial.save(vcard)
                }
            }
        }
    }
}
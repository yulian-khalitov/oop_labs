package vcards

import ezvcard.VCard
import ezvcard.VCardVersion
import ezvcard.io.text.VCardReader
import ezvcard.io.text.VCardWriter
import javafx.collections.ObservableList
import java.io.File

object VCardSerial {
    val filename = "vcards.vcf"
    fun read() : List<VCard> {
        val vcards = mutableListOf<VCard>()
        val file = File(filename)
        if (file.exists()) {
            val reader = VCardReader(file)
            reader.use { reader ->
                while (true) {
                    val vcard = reader.readNext() ?: break
                    vcards.add(vcard)
                }
            }
        }
        return vcards
    }

    fun save(vcards: ObservableList<VCard>) {
        val file = File(filename);
        val writer = VCardWriter(file, VCardVersion.V4_0)
        writer.use {
            vcards.forEach { vcard ->
                it.write(vcard)
            }
        }
    }

    fun save(vcard: VCard) {
        val name = vcard.structuredName.given
        val filename = name + ".vcf"
        var file = File(filename)
        var i = 0
        while (file.exists()) {
            file = File("$name${i++}.vcf")
        }
        val writer = VCardWriter(file, VCardVersion.V4_0)
        writer.use {
            it.write(vcard)
        }
    }
}
package dev.mobile.medicalink.fragments.traitements.builderTreatment

import java.io.Serializable
import java.util.Locale

class Sample(
    var numeroPrise: String,
    var heurePrise: String,
    var quantite: Int,
    var dosageUnite: String
) : Serializable {

    fun enMajuscule() {
        heurePrise = heurePrise.uppercase(Locale.getDefault())
    }

    fun getName(): String {
        return heurePrise
    }

    override fun toString(): String {
        return "$numeroPrise;$heurePrise;$quantite;$dosageUnite"
    }



}
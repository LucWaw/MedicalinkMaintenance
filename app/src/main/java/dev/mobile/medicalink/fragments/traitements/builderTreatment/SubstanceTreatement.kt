package dev.mobile.medicalink.fragments.traitements.builderTreatment

import java.util.UUID

class SubstanceTreatement(
    nomTraitement: String,
    typeComprime: String,
    timeline: Timeline,
    dosage: Dosage,
    codeCis: Int?,
    uuid : UUID,
    uuidUser : UUID,
    prises : MutableList<Sample>,
    stock : Stock

) : Treatment(
    nomTraitement,
    typeComprime,
    timeline,
    uuid,
    uuidUser,
    prises,
    stock
) {
    private var codeCis: Int? = null
    private var dosage: Dosage? = null

    init {
        this.codeCis = codeCis
        this.dosage = dosage
    }
}
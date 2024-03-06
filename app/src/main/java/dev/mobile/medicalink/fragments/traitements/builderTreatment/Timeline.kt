package dev.mobile.medicalink.fragments.traitements.builderTreatment

import java.time.LocalDate

data class Timeline (val dateDbtTraitement: LocalDate, val dateFinTraitement:LocalDate? = null, val expire : Boolean)
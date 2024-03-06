package dev.mobile.medicalink.fragments.traitements.builderTreatment

abstract class BaseBuilder : Builder {
    protected var treatment: Treatment = Treatment()

    fun build(): Treatment {
        return treatment
    }
}
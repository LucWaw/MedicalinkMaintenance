package dev.mobile.medicalink.fragments.traitements.builderTreatment

class Director(builder: Builder) {
    private val builder: Builder

    init {
        this.builder = builder
    }

    private fun makeBaseTreatment(name: String) {
        builder.setName(name)
    }

    private fun makeTypeTraitement(typeTraitement: String) {
        builder.setType(typeTraitement)
    }

    private fun makeCodeCIS(codeCIS: String) {
        builder.setCodeCIS(codeCIS)
    }

    private fun makeDosage(dosage: Int, unite: String) {
        builder.setDosage(dosage, unite)
    }

    private fun makeTimeline(timeline: Timeline) {
        builder.setTimeline(timeline)
    }

    private fun makeUuid(uuid: String) {
        builder.setUuid(uuid)
    }

    private fun makeUuidUser(uuidUser: String) {
        builder.setUuidUser(uuidUser)
    }

    private fun makePrises(prises: MutableList<Sample>) {
        builder.setPrises(prises)
    }

    private fun makeStock(stock: Stock) {
        builder.setStock(stock)
    }

    fun addPrise(prise: Sample) {
        builder.addPrise(prise)
    }

    private fun makeBaseBd(uuidUser: String, uuid: String) {
        builder.setUuid(uuid)
        builder.setUuidUser(uuidUser)
    }

    fun reset() {
        builder.reset()
    }



    fun makeSubstanceTreatment(name: String,
                               typeTraitement: String,
                               timeline: Timeline,
                               dosage: Int,
                               unite: String,
                               codeCIS: String,
                                 uuid: String,
                                    uuidUser: String,
                                    prises: MutableList<Sample>,
                                    stock: Stock) : Treatment {
        makeBaseTreatment(name)
        makeTypeTraitement(typeTraitement)
        makeDosage(dosage, unite)
        makeTimeline(timeline)
        makeCodeCIS(codeCIS)
        makeUuid(uuid)
        makeUuidUser(uuidUser)
        makePrises(prises)
        makeStock(stock)
        makeBaseBd(uuidUser, uuid)
        return builder.result()

    }

    fun makeTextTreatment(name: String, typeTraitement: String, timeline: Timeline, uuid: String, uuidUser: String, prises: MutableList<Sample>, stock: Stock) : Treatment {
        makeBaseTreatment(name)
        makeTypeTraitement(typeTraitement)
        makeTimeline(timeline)
        makeBaseBd(uuidUser, uuid)
        makePrises(prises)
        makeStock(stock)
        return builder.result()
    }





}

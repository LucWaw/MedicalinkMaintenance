package dev.mobile.medicalink.fragments.traitements.builderTreatment

interface Builder {

    /**
     * Reset the builded treatment
     */
    fun reset()

    /**
     * Set the name of the treatment
     */
    fun setName(name: String)

    /**
     * Set CodeCIS of the treatment
     */
    fun setCodeCIS(codeCIS: String)


    /**
     * Set the dosage of the treatment
     */
    fun setDosage(dosage: Int, unite: String)

    /**
     * Set the type of the treatment
     */
    fun setType(type: String)

    /**
     * Set the timeline of the treatment
     */
    fun setTimeline(timeline: Timeline)

    /**
     * Set the uuid of the treatment
     */
    fun setUuid(uuid: String)

    /**
     * Set the uuidUser of the treatment
     */
    fun setUuidUser(uuidUser: String)

    /**
     * Set the prises of the treatment
     */
    fun setPrises(prises: MutableList<Sample>)

    /**
     * Add a prise to the treatment
     */
    fun addPrise(prise: Sample)

    /**
     * Set the stock of the treatment
     */
    fun setStock(stock: Stock)

    /**
     * Get the treatment
     */
    fun result(): Treatment

}
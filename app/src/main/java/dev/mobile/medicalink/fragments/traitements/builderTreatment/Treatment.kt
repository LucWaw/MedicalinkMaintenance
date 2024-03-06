package dev.mobile.medicalink.fragments.traitements.builderTreatment

import dev.mobile.medicalink.fragments.traitements.Prise
import java.time.LocalDate
import java.util.UUID

open class Treatment (
    nomTraitement : String,                //base
    typeComprime : String,                 //Page type
    timeline: Timeline,                    //Page Shéma de prise de Traitement et dates
    uuid : UUID? = null,                   //base par la bd$
    uuidUser : UUID? = null,               //base par la bd
    prises : MutableList<Sample>? = null,   //Page prise
    stock : Stock? = null,                 //page Stock
) {
    private var nomTraitement: String
    private var typeComprime: String
    private var timeline: Timeline
    private var uuid: UUID?
    private var uuidUser: UUID?
    private var prises: MutableList<Sample>?
    private var stock: Stock?


    init {
        this.nomTraitement = nomTraitement
        this.typeComprime = typeComprime
        this.timeline = timeline
        this.uuid = uuid
        this.uuidUser = uuidUser
        this.prises = prises
        this.stock = stock
    }

    fun getProchainePrise(prise: Sample?): Sample {
        if (prise == null) {
            return prises!![0]
        } else {
            var prochainePrise = prise
            //S'il n'y a qu'une seule prise, on retourne cette prise
            if (prises?.size == 1) {
                return prochainePrise
            }
            //Sinon :
            //On tri les prises en fonction de leur heure de prise
            prises?.sortBy { it.heurePrise }
            //On boucle sur les prises pour trouver la prochaine prise, si la prise est la dernière de la liste, on retourne la première prise
            for (i in 0 until prises!!.size) {
                if (prises!![i] == prise) {
                    prochainePrise = if (i == prises!!.size - 1) {
                        prises!![0]
                    } else {
                        prises!![i + 1]
                    }
                }
            }

            //On est de toute façon dans le else alors prochainePrise ne peut pas être null
            return prochainePrise!!
        }
    }




}




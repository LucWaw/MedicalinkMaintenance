package dev.mobile.medicalink.db.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val uuid: String,
    @ColumnInfo(name = "statut") val statut: String?,
    @ColumnInfo(name = "nom") val nom: String?,
    @ColumnInfo(name = "prenom") val prenom: String?,
    @ColumnInfo(name = "dateDeNaissance") val dateDeNaissance: String?,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "password") var password: String?,
    @ColumnInfo(name = "isConnected") var isConnected: Boolean?,
)





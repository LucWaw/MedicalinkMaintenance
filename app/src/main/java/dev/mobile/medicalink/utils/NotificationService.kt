package dev.mobile.medicalink.utils


import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import dev.mobile.medicalink.MainActivity
import dev.mobile.medicalink.R
import dev.mobile.medicalink.fragments.traitements.Traitement
import java.lang.System.currentTimeMillis
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime


class NotificationService : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // Obtenez le titre et le contenu de l'intent
        val title = intent?.getStringExtra("title") ?: "Titre par défaut"
        val content = intent?.getStringExtra("content") ?: "Contenu par défaut"
        val notificationId = intent?.getIntExtra("notificationId", -1)!!
        val sauter = intent.getBooleanExtra("sauter", false)
        val prendre = intent.getBooleanExtra("prendre", false)

        // C'est ici que vous affichez la notification
        showNotification(context, title, content, notificationId, sauter, prendre)
    }

    /**
     * Fonction qui affiche une notification
     * @param context : le contexte de l'application
     * @param titre : le titre de la notification
     * @param contenu : le contenu de la notification
     * @param notificationId : l'id de la notification
     * @param sauter : si c'est à vrai alors il y aura un bouton "Sauter"
     * @param prendre : si c'est à vrai alors il y aura un bouton "Prendre"
     */
    private fun showNotification(context: Context?, titre: String, contenu: String, notificationId: Int, sauter: Boolean = false, prendre: Boolean = false) {
        // Code pour afficher la notification
        val notificationManager = ContextCompat.getSystemService(
            context!!,
            NotificationManager::class.java
        ) as NotificationManager

        // Intent pour l'action "Sauter"
        val sauterIntent = Intent(context, SauterReceiver::class.java)
        sauterIntent.action = "ACTION_SAUTE"
        sauterIntent.putExtra("notificationId", notificationId)
        val sauterPendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId,
            sauterIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // Intent pour l'action "Prendre"
        val prendreIntent = Intent(context, PrendreReceiver::class.java)
        prendreIntent.action = "ACTION_PRENDRE"
        prendreIntent.putExtra("notificationId", notificationId)
        val prendrePendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId+1,
            prendreIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = "medicalinkNotificationChannel" // Remplacez par votre propre ID de canal
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.logo_medicalink)
            .setContentTitle(titre)
            .setContentText(contenu)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        if (sauter) {
            notificationBuilder.addAction(0, "Sauter", sauterPendingIntent)
        }
        if (prendre) {
            notificationBuilder.addAction(0, "Prendre", prendrePendingIntent)
        }

        // utiliser l'id si on veut avoir plusieurs notifications en même temps
        // Sinon la nouvelle notif remplacera la première
        notificationManager.notify(notificationId, notificationBuilder.build())
    }


    companion object {
        /**
         * Fonction qui créer la première notification d'un traitement
         * @param context : le contexte de l'application
         * @param heurePremierePriseStr : l'heure de la première prise
         * @param jourPremierePrise : le jour de la première prise
         * @param traitement : le traitement concerné
         */
        @RequiresApi(Build.VERSION_CODES.O)
        fun createFirstNotif(
            context: Context,
            heurePremierePriseStr: String,
            jourPremierePrise: LocalDate,
            traitement: Traitement
        ) {
            //On découpe le string pour récupérer l'heure et les minutes
            val heure = heurePremierePriseStr.split(":").first().toInt()
            val minute = heurePremierePriseStr.split(":").last().toInt()

            //On récupère l'heure actuelle en millisecondes
            val heureActuelle = LocalTime.now().toNanoOfDay() / 1000000

            //On récupère l'heure de la prochaine prise en millisecondes
            val heureProchainePriseMillis = LocalTime.of(heure, minute).toNanoOfDay() / 1000000

            //On calcule le nombre de jour entre ajourd'hui et le jour de la première prise
            var nbJours =
                Duration.between(LocalDate.now().atStartOfDay(), jourPremierePrise.atStartOfDay())
                    .toDays().toInt()

            //On rajoute un jour si l'heure de la première prise est inférieure à l'heure actuelle
            if (heureProchainePriseMillis < heureActuelle) {
                nbJours++
            }

            // Appelez createNotif avec le PendingIntent nouvellement créé
            createNotif(
                context,
                heurePremierePriseStr,
                traitement,
                nbJours
            )
        }

        /**
         * Fonction qui créer une notification pour la prochaine prise du traitement
         * @param context : le contexte de l'application
         * @param heureProchainePriseStr : l'heure de la prochaine prise
         * @param traitement : le traitement concerné
         */
        @RequiresApi(Build.VERSION_CODES.O)
        fun createNextNotif(
            context: Context,
            heureProchainePriseStr: String,
            traitement: Traitement
        ) {
            // Appelez createNotif avec le PendingIntent nouvellement créé
            createNotif(
                context,
                heureProchainePriseStr,
                traitement,
                1
            )
        }

        /**
         * Fonction qui créer une notification pour une prise future du traitement
         * @param context : le contexte de l'application
         * @param heurePriseStr : l'heure de la prise
         * @param traitement : le traitement concerné
         * @param nbJour : le nombre de jour entre aujourd'hui et le jour de la prise
         */
        @RequiresApi(Build.VERSION_CODES.O)
        private fun createNotif(
            context: Context,
            heurePriseStr: String,
            traitement: Traitement,
            nbJour: Int
        ) : Int {
            val notificationId = uniqueId(context)

            // On découpe le string pour récupérer l'heure et les minutes
            val heure = heurePriseStr.split(":").first().toInt()
            val minute = heurePriseStr.split(":").last().toInt()

            // On récupère l'heure actuelle en millisecondes
            val heureActuelle = LocalTime.now().toNanoOfDay() / 1000000

            // On récupère l'heure de la prochaine prise en millisecondes
            val heureProchainePriseMillis = LocalTime.of(heure, minute).toNanoOfDay() / 1000000

            // On récupère la durée entre l'heure actuelle et l'heure de la prochaine prise
            // Il faut faire attention à la date, si l'heure de la prochaine prise est inférieure à l'heure actuelle, on ajoute un jour à la date
            val duree = if (heureProchainePriseMillis < heureActuelle) {
                Duration.ofMillis(heureProchainePriseMillis + 86400000 * nbJour - heureActuelle)
            } else {
                Duration.ofMillis(heureProchainePriseMillis - heureActuelle)
            }

            // On crée la notification en utilisant l'ID généré
            return sendNotification(
                context,
                "Prise de ${traitement.nomTraitement} de $heure h $minute",
                "Il est l'heure de prendre votre médicament ${traitement.nomTraitement}",
                duree.toMillis(),
                notificationId,
                sauter = true,
                prendre = true
            )
        }

        /**
         * Fonction qui créer une notification pour notifier qu'il n'y a plus de stock
         * @param context : le contexte de l'application
         * @param titre : le titre de la notification
         * @param contenu : le contenu de la notification
         */
        fun createNotifStock(
            context: Context,
            titre: String,
            contenu: String,
        ) : Int {
            val notificationId = uniqueId(context)

            // On crée la notification en utilisant l'ID généré
            return sendNotification(
                context,
                titre,
                contenu,
                0,
                notificationId
            )
        }

        /**
         * Fonction qui créer une notification
         * @param context : le contexte de l'application
         * @param titre : le titre de la notification
         * @param contenu : le contenu de la notification
         * @param delayMillis : le délai avant l'affichage de la notification
         * @param notificationId : l'id de la notification
         * @param sauterPendingIntent : le pending intent pour l'action "Sauter"
         * @param prendrePendingIntent : le pending intent pour l'action "Prendre"
         * @return l'id de la notification
         */
        fun sendNotification(
            context: Context,
            titre: String,
            contenu: String,
            delayMillis: Long,
            notificationId: Int,
            sauter: Boolean = false,
            prendre: Boolean = false
        ): Int {
            val notificationIntent = Intent(context, NotificationService::class.java)
                .putExtra("title", titre)
                .putExtra("content", contenu)
                .putExtra("notificationId", notificationId)
                .putExtra("sauter", sauter)
                .putExtra("prendre", prendre)

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                notificationId,
                notificationIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            Log.d("Notif duree", delayMillis.toString())

            // Configurez l'alarme avec le délai
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                currentTimeMillis() + delayMillis,
                pendingIntent
            )

            return notificationId
        }


        fun uniqueId(context: Context): Int {
            val PREFS_NAME = "notification_prefs"
            val KEY_NOTIFICATION_ID = "notification_id"
            val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            // Get the current notification ID
            val notificationId = sharedPref.getInt(KEY_NOTIFICATION_ID, 0)

            // Increment the notification ID and store it
            with(sharedPref.edit()) {
                putInt(KEY_NOTIFICATION_ID, notificationId + 2)
                apply()
            }

            return notificationId + 2
        }


    }

}




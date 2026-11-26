package org.fossify.voicerecorder.extensions

import android.content.Context
import ch.rosenweg4303.cloud.Ablage
import org.fossify.voicerecorder.models.Recording
import java.util.Locale

/*
 * Rosenweg: Aufnahmen in der Nextcloud (Persönlich/Diktiergerät/<Monat>/...).
 * Sie stehen im Player neben den lokalen, noch nicht hochgeladenen. Ihr Pfad
 * beginnt mit "nc:" + Pfad in der Nextcloud; abgespielt wird per Stream.
 */

const val NC_PRAEFIX = "nc:"
const val NC_APP_ORDNER = "Diktiergerät"

private val AUDIO = setOf("m4a", "mp3", "ogg", "opus", "aac", "wav", "3gp", "amr", "flac", "webm")

val Recording.istNextcloud get() = path.startsWith(NC_PRAEFIX)
val Recording.ncPfad get() = path.removePrefix(NC_PRAEFIX)

/** Aufnahmen in der Nextcloud; leer ohne Anmeldung oder ohne Netz. Blockiert. */
fun Context.nextcloudAufnahmen(): List<Recording> =
    Ablage.dateien(this, NC_APP_ORDNER).orEmpty()
        .filter { it.name.substringAfterLast('.', "").lowercase(Locale.ROOT) in AUDIO }
        .map {
            Recording(
                id = (NC_PRAEFIX + it.pfad).hashCode(),
                title = it.name,
                path = NC_PRAEFIX + it.pfad,
                timestamp = it.geaendert,
                duration = 0, // unbekannt bis zum Abspielen
                size = it.groesse.coerceAtMost(Int.MAX_VALUE.toLong()).toInt()
            )
        }

/**
 * Lokale und Nextcloud-Aufnahmen zusammen. Gibt es eine Aufnahme schon in der
 * Nextcloud (Hochladen fertig, lokales Loeschen steht noch aus), zaehlt die dort.
 */
fun Context.mitNextcloud(lokal: ArrayList<Recording>): ArrayList<Recording> {
    val oben = nextcloudAufnahmen()
    if (oben.isEmpty()) return lokal
    val namen = oben.map { it.title }.toSet()
    return ArrayList(lokal.filter { it.title !in namen } + oben)
}

/** Loescht in der Nextcloud (landet dort im Papierkorb). Blockiert. */
fun Context.nextcloudLoeschen(aufnahmen: Collection<Recording>) {
    val dav = Ablage.webDav(this) ?: return
    aufnahmen.filter { it.istNextcloud }.forEach { dav.loeschen(it.ncPfad) }
}

/** Benennt in der Nextcloud um; die Endung bleibt. Blockiert. */
fun Context.nextcloudUmbenennen(aufnahme: Recording, neuerTitel: String) {
    val dav = Ablage.webDav(this) ?: return
    val endung = aufnahme.title.substringAfterLast('.', "")
    val neu = if (endung.isEmpty()) neuerTitel else "${neuerTitel.removeSuffix(".$endung")}.$endung"
    val ordner = aufnahme.ncPfad.substringBeforeLast('/', "")
    dav.verschieben(aufnahme.ncPfad, if (ordner.isEmpty()) neu else "$ordner/$neu")
}

/** Pfade zum Teilen/Oeffnen: Nextcloud-Aufnahmen werden dafuer in den Cache geladen. Blockiert. */
fun Context.teilbarePfade(aufnahmen: Collection<Recording>): List<String> =
    aufnahmen.map { if (it.istNextcloud) Ablage.lokaleKopie(this, it.ncPfad).absolutePath else it.path }

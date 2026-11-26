<p align="center"><img src="assets/logo/rosenweg-diktiergeraet-512.png" width="96" alt="Logo"></p>
<h1 align="center">Rosenweg Diktiergerät</h1>
<p align="center">Diktiergerät für die Smartphones der Siedlung Rosenweg – Aufnahmen landen direkt in der eigenen Rosenweg-Nextcloud.</p>
<p align="center"><a href="https://www.rosenweg4303.ch/apps">Herunterladen</a> · <a href="docs/anleitung.md">Anleitung</a></p>

## Inhalt

- [Überblick](#überblick)
- [Plattformen](#plattformen)
- [Rechte und Gruppen](#rechte-und-gruppen)
- [Bedienung](#bedienung)
- [Rosenweg-Nextcloud](#rosenweg-nextcloud)
- [Geteilte Rosenweg-Telefone](#geteilte-rosenweg-telefone)
- [Rosenweg Updater](#rosenweg-updater)
- [Aufbau](#aufbau)
- [Bauen](#bauen)
- [Logo](#logo)
- [CI und Release](#ci-und-release)
- [Konfiguration](#konfiguration)
- [Zweige](#zweige)
- [Herkunft und Lizenz](#herkunft-und-lizenz)

## Überblick

Diktiergerät für die Smartphones der Siedlung Rosenweg in Kaiseraugst. Aufnahmen gehen in die Rosenweg-Nextcloud der Person (`Persönlich/Diktiergerät`) und werden auf dem Telefon gelöscht, sobald sie oben sind.

Was die App kann:

- **Aufnehmen:** wie gewohnt; die Aufnahme geht in die Nextcloud nach `Persönlich/Diktiergerät`.
- **Nichts geht verloren:** auf dem Telefon gelöscht wird erst, wenn die Nextcloud den Empfang bestätigt hat.
- **Abspielen:** lokale (noch nicht hochgeladene) Aufnahmen und die aus der Nextcloud; Nextcloud-Aufnahmen werden gestreamt.
- **Verwalten:** Nextcloud-Aufnahmen löschen, umbenennen und teilen.
- **Einstellungen pro Person:** auf jedem Telefon wieder da.

## Plattformen

| Plattform | Stand |
|---|---|
| Android, eigenes Handy | ja, APK auf [rosenweg4303.ch/apps](https://www.rosenweg4303.ch/apps); danach aktualisiert der Rosenweg Updater |
| Geteilte Rosenweg-Telefone | ja, Rosenweg Gerät installiert sie aus dem App-Katalog der Telefonanlage und hält sie aktuell |
| Play Store, F-Droid | nein – die App ist mit dem Rosenweg-Schlüssel signiert; Fassungen anderer Herkunft lassen sich nicht darüber installieren |

## Rechte und Gruppen

Die App hat keine eigenen Rollen. Sie arbeitet mit dem Rosenweg-Konto der angemeldeten Person.

| Wer | sieht und darf |
|---|---|
| Angemeldete Person | die eigene Rosenweg-Nextcloud, Arbeitsordner `Persönlich/Diktiergerät`; es gelten die Rechte der Nextcloud (auch für geteilte Ordner) |
| Ohne Anmeldung | nur Dateien auf dem Telefon, wie im Original |
| Technik | legt den Katalog-Eintrag `ch.rosenweg4303.voicerecorder` auf rosenweg4303.ch/smartphones an und bestimmt dort, für welche Gruppen die App auf den geteilten Telefonen erscheint |

## Bedienung

| Was | So geht’s |
|---|---|
| Aufnehmen | Aufnahmeknopf; der Speicherordner ist fest vorgegeben (eigener App-Ordner), „Speichern in“ ist ausgeblendet |
| Abspielen | Player-Reiter: lokale und Nextcloud-Aufnahmen in einer Liste |
| Löschen, umbenennen, teilen | langer Druck auf eine Aufnahme |

Ausführlich: [docs/anleitung.md](docs/anleitung.md).

## Rosenweg-Nextcloud

| Thema | So ist es |
|---|---|
| Anmeldung | über den Login der Nextcloud mit dem Rosenweg-Konto im Browser; die App erhält ein eigenes App-Passwort, nie das eigentliche Passwort |
| Erster Start | die App fragt einmal, ob man sich anmelden möchte; „Nein, danke“ wird gemerkt, Anmelden geht weiter über die Einstellungen |
| Hochladen | über eine Warteschlange, sobald Netz da ist; lokal gelöscht wird erst, wenn die Nextcloud den Empfang bestätigt hat |
| Einstellungen pro Person | liegen in der Nextcloud der Person (`Persönlich/.rosenweg/`) und sind auf jedem Telefon wieder da, auf dem sie angemeldet ist. Gerätebezogenes (Widgets, Pfade, Zähler) bleibt auf dem Gerät. |

## Geteilte Rosenweg-Telefone

Auf den geteilten Telefonen (Anmeldung per Karte am Sperrbildschirm) übernimmt **Rosenweg Gerät**:

| Was | Wie |
|---|---|
| Installation und Updates | automatisch aus dem App-Katalog der Telefonanlage |
| Zugang zur Nextcloud | kommt von Rosenweg Gerät, kein eigener Login nötig |
| Voraussetzung | Rosenweg-Signatur: Rosenweg Gerät gibt Daten nur an Apps mit seiner Signatur weiter (`ch.rosenweg4303.permission.ANLAGE`) |

## Rosenweg Updater

Auf Handys ohne Rosenweg Gerät prüft die App den App-Katalog der Telefonanlage und bietet Aktualisierungen für alle installierten Rosenweg-Apps an. Jede APK wird vorher geprüft (Prüfsumme, Paketname, Version, Rosenweg-Signatur); Android lässt jede Installation bestätigen. Auf geteilten Telefonen übernimmt das Rosenweg Gerät.

## Aufbau

| Teil | Inhalt |
|---|---|
| Grundlage | [Fossify Voice Recorder](https://github.com/FossifyOrg/Voice-Recorder), Kotlin; Kotlin-Namensraum wie im Original, nur die applicationId ist eigen |
| Paketname | `ch.rosenweg4303.voicerecorder` (in `gradle.properties` als `APP_ID`), aktuelle Version 1.7.5 |
| [commons-rosenweg](https://github.com/Rosenweg/commons-rosenweg) | Rosenweg-Fork der Fossify Commons, Version `7.1.3-rosenweg.1` (`gradle/libs.versions.toml`) |
| Rosenweg-Modul | `ch.rosenweg4303:cloud` 0.8.2 aus commons-rosenweg (Nextcloud-Anmeldung, Warteschlange, Einstellungen pro Person, Updater) |
| `extensions/Nextcloud.kt` | Hochladen und Nextcloud-Liste |
| `fragments/PlayerFragment.kt` | Player mit lokalen und Nextcloud-Aufnahmen |
| `services/RecorderService.kt` | Aufnahme in den festen App-Ordner |
| `assets/logo/` | Logo aus dem Baukasten von rosenweg-basis (siehe [Logo](#logo)) |

Kotlin-Pfade relativ zu `app/src/main/kotlin/org/fossify/voicerecorder/`.

## Bauen

Die App baut gegen commons-rosenweg und die Rosenweg-Module (`ch.rosenweg4303:*`) aus dem lokalen Maven-Repository (`mavenLocal()`). Also zuerst commons-rosenweg veröffentlichen:

```bash
# in commons-rosenweg, Zweig rosenweg
./gradlew -PVERSION=7.1.3-rosenweg.1 publishToMavenLocal

# in diesem Repo, Zweig main
./gradlew assembleFossRelease
```

| Was | Wo |
|---|---|
| APK | `app/build/outputs/apk/foss/release/` |
| Signatur | `keystore.properties` (Vorlage `keystore.properties_sample`); Datei und Schlüssel liegen nicht im Repo |
| Version | `VERSION_NAME`, `VERSION_CODE` in `gradle.properties` |

Mehr: [docs/entwickeln.md](docs/entwickeln.md).

## Logo

Rosenweg-Wappen (Turm, Weg, Rose auf Rot) mit dem Zeichen der App rechts unten, erzeugt mit dem Logo-Baukasten aus rosenweg-basis; dazu ein monochromes Designsymbol (Android 13+). Die wählbaren Symbolfarben von Fossify gibt es nicht mehr: alle zeigen dasselbe Rosenweg-Symbol.

Neu erzeugen (aus dem Repo-Wurzelverzeichnis, rosenweg-basis daneben): `sh assets/logo/erzeugen.sh`. Welches Zeichen die App trägt, steht in rosenweg-basis `werkzeug/logo/android-apps.txt`.

## CI und Release

Die APK wird lokal mit dem Rosenweg-Schlüssel gebaut und als GitHub-Release veröffentlicht:

```bash
gh release create v<versionName> <datei.apk> --notes "Was ist neu"
```

| Auslöser | Ergebnis |
|---|---|
| Release veröffentlicht (Tag = versionName, eine APK je Release) | `.github/workflows/apps.yml` schickt die APK an **[rosenweg4303.ch/apps](https://www.rosenweg4303.ch/apps)** und in den App-Katalog der Telefonanlage – damit bekommen sie die geteilten Telefone und der Rosenweg Updater |
| `gh workflow run apps.yml --ref <tag>` | erneut senden, z.B. nach einer Störung |

`apps.yml` nutzt den wiederverwendbaren Workflow `apps-release.yml` aus Rosenweg/Website. Anmeldung per GitHub-OIDC, kein gespeichertes Geheimnis; die Anlage prüft Tag, Prüfsumme, Rosenweg-Signatur und Paketnamen. Der Workflow liegt im Standardzweig `main`; `gh release create` ohne `--target` setzt den Tag dort hin. Die App muss auf rosenweg4303.ch/smartphones schon im Katalog stehen.

Die übrigen Workflows (`pr.yml`, `release.yml`, `testing-build.yml`, `update-commons.yml` …) stammen aus dem Original und rufen Fossify-Workflows auf; für Rosenweg-Releases zählt nur `apps.yml`.

## Konfiguration

Keine GitHub-Secrets nötig (OIDC). Lokal, nur die Namen:

| Datei | Schlüssel | Wozu |
|---|---|---|
| `keystore.properties` | `storeFile`, `storePassword`, `keyAlias`, `keyPassword` | Rosenweg-Signierschlüssel für den Release-Bau |
| `gradle.properties` | `APP_ID`, `VERSION_NAME`, `VERSION_CODE` | Paketname und Version |

## Zweige

`main` enthält den ganzen Rosenweg-Stand; die Anpassungen wurden im Zweig `rosenweg` entwickelt und sind in `main` zusammengeführt. Gebaut und veröffentlicht wird aus `main`.

## Herkunft und Lizenz

Basiert auf [Fossify Voice Recorder](https://github.com/FossifyOrg/Voice-Recorder) – herzlichen Dank an das Fossify-Team und alle Mitwirkenden für die solide Grundlage. Spenden-, Store- und Rückmeldekanäle des Originals sind entfernt.

Lizenz: GNU General Public License v3.0 (GPL-3.0), siehe [LICENSE](LICENSE).

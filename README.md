# Rosenweg Diktiergerät

Diktiergerät für die Smartphones der Siedlung Rosenweg in Kaiseraugst. Aufnahmen gehen in die Rosenweg-Nextcloud der Person (`Persönlich/Diktiergerät`) und werden auf dem Telefon gelöscht, sobald sie oben sind.

## Im Rosenweg

- Der Speicherordner ist fest vorgegeben (eigener App-Ordner); die Einstellung "Speichern in" ist ausgeblendet.
- Der Player zeigt lokale (noch nicht hochgeladene) Aufnahmen und die aus der Nextcloud; Nextcloud-Aufnahmen werden gestreamt und lassen sich löschen, umbenennen und teilen.
- **Rosenweg-Nextcloud:** Anmeldung über den Login der Nextcloud mit dem Rosenweg-Konto im Browser; die App erhält ein eigenes App-Passwort, nie das eigentliche Passwort. Auf geteilten Telefonen kommt der Zugang von Rosenweg Gerät. Hochgeladen wird über eine Warteschlange, sobald Netz da ist; lokal gelöscht wird erst, wenn die Nextcloud den Empfang bestätigt hat. Beim ersten Start fragt die App einmal, ob man sich anmelden möchte ("Nein, danke" wird gemerkt, Anmelden geht weiter über die Einstellungen).
- **Einstellungen pro Person:** Die Einstellungen der App liegen in der Nextcloud der Person (`Persönlich/.rosenweg/`) und sind auf jedem Telefon wieder da, auf dem sie angemeldet ist. Gerätebezogenes (Widgets, Pfade, Zähler) bleibt auf dem Gerät.
- **Rosenweg Updater:** Auf Handys ohne Rosenweg Gerät prüft die App den App-Katalog der Telefonanlage und bietet Aktualisierungen für alle installierten Rosenweg-Apps an. Jede APK wird vorher geprüft (Prüfsumme, Paketname, Version, Rosenweg-Signatur); Android lässt jede Installation bestätigen. Auf geteilten Telefonen übernimmt das Rosenweg Gerät.
- **Logo:** Rosenweg-Wappen (Turm, Weg, Rose auf Rot) mit dem Zeichen der App rechts unten, erzeugt mit dem Logo-Baukasten aus rosenweg-basis; dazu ein monochromes Designsymbol (Android 13+). Die wählbaren Symbolfarben von Fossify gibt es nicht mehr: alle zeigen dasselbe Rosenweg-Symbol.

## Installation

- **Geteilte Rosenweg-Telefone:** Rosenweg Gerät installiert die App automatisch aus dem App-Katalog der Telefonanlage und hält sie aktuell.
- **Eigenes Handy:** APK von [www.rosenweg4303.ch/apps](https://www.rosenweg4303.ch/apps) herunterladen und installieren. Danach hält der Rosenweg Updater die Rosenweg-Apps aktuell.

Die App gibt es nicht im Play Store und nicht auf F-Droid. Sie ist mit dem Rosenweg-Schlüssel signiert; Fassungen anderer Herkunft lassen sich nicht darüber installieren.

## Bauen

Die App baut gegen den Rosenweg-Fork der Fossify Commons und die Rosenweg-Module `cloud`/`updater` (`ch.rosenweg4303:*`). Diese kommen aus dem lokalen Maven-Repository (`mavenLocal()`), also zuerst [commons-rosenweg](https://github.com/Rosenweg/commons-rosenweg) veröffentlichen:

```bash
# in commons-rosenweg, Zweig rosenweg
./gradlew -PVERSION=7.1.3-rosenweg.1 publishToMavenLocal

# in diesem Repo, Zweig rosenweg
./gradlew assembleFossRelease
```

Die APK liegt danach unter `app/build/outputs/apk/foss/release/`. Signiert wird mit `keystore.properties` (Vorlage: `keystore.properties_sample`); die Datei und der Schlüssel liegen nicht im Repo. Paketname: `ch.rosenweg4303.voicerecorder`.

Logo neu erzeugen (aus dem Repo-Wurzelverzeichnis, rosenweg-basis daneben): `sh assets/logo/erzeugen.sh`. Welches Zeichen die App trägt, steht in rosenweg-basis `werkzeug/logo/android-apps.txt`.

## Neue Version

APK wie oben bauen und signieren, dann `gh release create v<versionName> <datei.apk> --notes "Was ist neu"` (Tag = versionName der APK, eine APK je Release). Der Workflow `.github/workflows/apps.yml` (wiederverwendbarer Workflow `apps-release.yml` aus Rosenweg/Website) schickt sie an [www.rosenweg4303.ch/apps](https://www.rosenweg4303.ch/apps) und in den App-Katalog der Telefonanlage; damit bekommen sie die geteilten Telefone und der Rosenweg Updater. Anmeldung per GitHub-OIDC, kein gespeichertes Geheimnis; die Anlage prüft Tag, Prüfsumme, Rosenweg-Signatur und Paketnamen. Der Workflow liegt im Standardzweig `main`; `gh release create` ohne `--target` setzt den Tag dort hin. Erneut senden: `gh workflow run apps.yml --ref <tag>`.

## Herkunft und Lizenz

Fork von [Fossify Voice Recorder](https://github.com/FossifyOrg/Voice-Recorder). Der Zweig `main` folgt dem Original, die Rosenweg-Anpassungen liegen im Zweig `rosenweg`. Herzlichen Dank an das Fossify-Team und alle Mitwirkenden für die solide Grundlage.

Lizenz: GNU General Public License v3.0, siehe [LICENSE](LICENSE).

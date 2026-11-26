# Rosenweg Diktiergerät – Für Entwickler

Fork von [Fossify Voice Recorder](https://github.com/FossifyOrg/Voice-Recorder), Paketname `ch.rosenweg4303.voicerecorder`. Die Rosenweg-Anpassungen sind im Zweig `main`
zusammengeführt (entwickelt im Zweig `rosenweg`).

## Bauen

Die App baut gegen den Rosenweg-Fork der Fossify Commons und die Rosenweg-Module `cloud`/`updater` (`ch.rosenweg4303:*`). Diese kommen aus dem lokalen Maven-Repository (`mavenLocal()`), also zuerst [commons-rosenweg](https://github.com/Rosenweg/commons-rosenweg) veröffentlichen:

```bash
# in commons-rosenweg, Zweig rosenweg
./gradlew -PVERSION=7.1.3-rosenweg.1 publishToMavenLocal

# in diesem Repo, Zweig main
./gradlew assembleFossRelease
```

Die APK liegt danach unter `app/build/outputs/apk/foss/release/`. Signiert wird mit `keystore.properties` (Vorlage: `keystore.properties_sample`); die Datei und der Schlüssel liegen nicht im Repo. Paketname: `ch.rosenweg4303.voicerecorder`.

## Logo

Rosenweg-Wappen (Turm, Weg, Rose auf Rot) mit dem Zeichen der App rechts unten, erzeugt mit dem Logo-Baukasten aus rosenweg-basis; dazu ein monochromes Designsymbol (Android 13+). Die wählbaren Symbolfarben von Fossify gibt es nicht mehr: alle zeigen dasselbe Rosenweg-Symbol.

Dateien: `assets/logo/`. Logo neu erzeugen (aus dem Repo-Wurzelverzeichnis, rosenweg-basis daneben): `sh assets/logo/erzeugen.sh`. Welches Zeichen die App trägt, steht in rosenweg-basis `werkzeug/logo/android-apps.txt`.

## Neue Version

APK wie oben bauen und signieren, dann `gh release create v<versionName> <datei.apk> --notes "Was ist neu"` (Tag = versionName der APK, eine APK je Release). Der Workflow `.github/workflows/apps.yml` (wiederverwendbarer Workflow `apps-release.yml` aus Rosenweg/Website) schickt sie an [www.rosenweg4303.ch/apps](https://www.rosenweg4303.ch/apps) und in den App-Katalog der Telefonanlage; damit bekommen sie die geteilten Telefone und der Rosenweg Updater. Anmeldung per GitHub-OIDC, kein gespeichertes Geheimnis; die Anlage prüft Tag, Prüfsumme, Rosenweg-Signatur und Paketnamen. Der Workflow liegt im Standardzweig `main`; `gh release create` ohne `--target` setzt den Tag dort hin. Erneut senden: `gh workflow run apps.yml --ref <tag>`.

## Herkunft

Fork von [Fossify Voice Recorder](https://github.com/FossifyOrg/Voice-Recorder). Herzlichen Dank an das Fossify-Team und alle Mitwirkenden für die solide Grundlage.

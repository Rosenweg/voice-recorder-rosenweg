<p align="center"><img src="assets/logo/rosenweg-diktiergeraet-512.png" width="96" alt="Logo"></p>
<h1 align="center">Rosenweg Diktiergerät</h1>
<p align="center">Diktiergerät für die Smartphones der Siedlung Rosenweg – Aufnahmen landen direkt in der eigenen Rosenweg-Nextcloud.</p>
<p align="center"><a href="https://www.rosenweg4303.ch/apps">Herunterladen</a> · <a href="docs/anleitung.md">Anleitung</a> · <a href="docs/entwickeln.md">Für Entwickler</a></p>

## Was die App kann

- Aufnehmen wie gewohnt; die Aufnahmen gehen nach `Persönlich/Diktiergerät`.
- Auf dem Telefon gelöscht wird erst, wenn die Aufnahme sicher in der Nextcloud ist.
- Aufnahmen aus der Nextcloud abspielen, umbenennen, teilen und löschen.
- Einstellungen gehören der Person und sind auf jedem Telefon wieder da.

## Plattformen

Android.

## Entwickeln

Android-App (Kotlin) auf Basis von Fossify Voice Recorder, gebaut gegen
[commons-rosenweg](https://github.com/Rosenweg/commons-rosenweg): `./gradlew assembleFossRelease` –
Bau, Signatur, Logo und Veröffentlichung stehen in [docs/entwickeln.md](docs/entwickeln.md).

## Herkunft und Lizenz

Basiert auf [Fossify Voice Recorder](https://github.com/FossifyOrg/Voice-Recorder) – herzlichen Dank an das Fossify-Team und alle Mitwirkenden.
GPL-3.0, siehe [LICENSE](LICENSE).

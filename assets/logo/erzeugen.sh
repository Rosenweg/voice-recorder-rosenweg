#!/bin/sh
# SPDX-License-Identifier: GPL-3.0-or-later
# App-Logo aus dem Logo-Baukasten von rosenweg-basis (Wappen mit Turm, Weg und
# Rose, rechts unten das Zeichen der App). Name, Titel und Zeichen stehen in
# rosenweg-basis werkzeug/logo/android-apps.txt. Aus dem Repo-Wurzelverzeichnis:
#   sh assets/logo/erzeugen.sh [pfad/zu/rosenweg-basis]
set -eu
BASIS="${1:-../rosenweg-basis}"
sh "$BASIS/werkzeug/logo/android-apps.sh" . voice-recorder-rosenweg

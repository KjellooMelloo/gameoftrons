# **Dokumentation Game of Trons**

**Autoren**: Kathleen Neitzel, Kjell May, Viviam Ribeiro <br>
**Modul**: Verteilte Systeme

- [Einführung und Ziele](#einfuehrung)
   - [Use Cases](#usecases)
   - [Qualitätsziele](#qualitaetsziele)
   - [Stakeholder](#stakeholder)
   - [Randbedingungen](#randbedingungen)
- [Kontextabgrenzung](#kontextabgrenzung)
  - [Fachlicher Kontext](#fachlicherkontext)
  - [Technischer Kontext](#technischerkontext)
- [Lösungsstrategie](#loesungsstrategie)
- [Bausteinsicht](#bausteinsicht)
  - [Whitebox Gesamtsystem](#whiteboxgesamt)
    - [Model Blackbox](#modelblackbox)
    - [View Blackbox](#viewblackbox)
    - [Controller Blackbox](#controllerblackbox)
    - [Application Stub Blackbox](#applicationstubblackblox)
  - [Whitebox Ebene 2](#ebene2)
  - [Whitebox Ebene 3](#ebene3)
- [Laufzeitsicht](#laufzeitsicht)
- [Verteilungssicht](#verteilungssicht)
- [Architekturentscheidungen](#architektur)
- [Risiken und technische Schulden](#risiken)
- [Qualitätsanforderungen](#qualitaet)
- [Glossar](#glossar)



<a name="einfuehrung"></a>
# Einführung und Ziele
## Aufgabenstellung

Die Anforderungen wurden mit Hilfe der Storyboard-Methode aufgenommen. Dafür wurden die Bildschirmanzeigen aller Use Cases skizziert und die dazu zugehörigen Anforderungen aufgenommen. Die Anforderungen werden in der unteren Tabelle neben der Verlinkung zur zugehörigen Bildschirmskizze aufgeführt.

| Bildschirmanzeige  | Anforderungen |
| --- | --- |
| ![Startbildschirm](images/tron2.PNG) | <ul><li>Der Übergang zum Wartebildschirm erfolgt über das Anklicken des Start-Buttons</li><li>Das Spiel kann mit 2 bis 6 Spielern gespielt werden</li><li>Die gewünschte Anzahl von Spielern wird über ein Eingabefeld eingestellt</li><li>Die Spieleranzahl wird bei falscher Benutzerangabe der Default-Wert aus der Config-Datei geladen</li></ul> |
| ![Wartebildschirm](images/tron3.PNG) | <ul><li>Der Wartebildschirm zeigt an, wie viele Spieler bereits dem Spiel beigetreten sind und wie viele Spieler fehlen, bis die gewünschte Spieleranzahl erreicht ist.</li><li>Die maximale Wartezeit auf die gewünschte Anzahl von Spielern ist parametrisierbar (Default: 120 Sekunden)</li><li>Das Warten wird automatisch abgebrochen nach Ablauf der maximalen Wartezeit.</li><li>Das Warten auf anderen Spieler kann über den Cancel-Button abgebrochen werden, wenn man alleine wartet</li><li>Wenn das Warten abgebrochen wird, wird automatisch zum Startbildschirm gewechselt.</li><li>Das Spiel startet automatisch, wenn alle Spieler beigetreten sind</li></ul> |
| ![Spielbildschirm](images/tron1.png) | <ul><li>Alle Spieler spielen gegen einander</li><li>Das Spielfeld ist rasterförmig mit sichtbarem Raster</li><li>Die Spielfeldgröße soll über die Config-Datei parametrisierbar sein</li><li>Der Parameter der Spielfeldgröße bestimmt die Anzahl der Reihen und Spalten (Default: Geschwindkeit * 5, Minimum:Geschwindigkeit * 5 , Maximum: Geschwindigkeit * 12)</li><li>Beim Start des Spiels gibt es einen Countdown von 3 Sekunden</li><li>Es müssen faire Startkonditionen für alle Spieler geben</li><li>Ein Spieler stirbt bei Kollision mit einer Wand, einem Motorrad oder einer Spur</li><li>Bei einer Frontalkollision zwischen zwei Spielern sterben beide Spieler</li><li>Wenn die letzten zwei Spieler durch eine Frontalkollision sterben, endet das Spiel unentschieden</li><li>Wenn die letzten zwei Spieler im Spiel gleichzeitig durch Kollision mit einer Wand und/oder Spur sterben, dann endet das Spiel unentschieden</li><li>Die zugehörige Spur verschwindet beim Tod des Spielers</li><li>Es soll erkennbar sein, welcher Spieler zum Nutzer gehört</li><li>Die Geschwindigkeit soll zwischen 1-500 Bewegungen pro Sekunde parametrisierbar sein. Bei ungültiger Eingabe, wird Geschwindigkeit auf den Default-Wert 100 gesetzt.</li><li>Das Motorrad bewegt sich automatisch geradeaus und kann nach rechts oder links gesteuert werden</li></ul> |
| ![Endbildschirm Fall 1](images/tron4a.png)<br> ![Endbildschirm Fall 2](images/tron5.PNG)| <ul><li>Der Endbildschirm erscheint, wenn das Spiel vorbei ist</li><li>Im Endbildschirm wird angezeigt, wer gewonnen hat oder ob das Spiel unentschieden ausgegangen ist</li><li>Nach 3 Sekunden wird zum Startbildschirm gewechselt</li><li>Der Endbildschirm wird erst angezeigt, wenn das Spiel vorbei ist und nicht sobald man stirbt</li></ul>
| **Konfiguration** | <ul><li>Die Konfigurationsdatei wird beim Start des Spiels geladen</li><li>Die Konfigurationsdatei kann vom Nutzer bearbeitet werden</li><li>Darüber wird der Default-Wert 2 der Spieleranzahl, die maximale Wartezeit bis zum Spielstart, die Spielfeldgröße, die Geschwindigkeit und die Tastenbelegung für die Steuerung konfiguriert</li></ul>

### **Weitere Storyboard Ansichten**

**Spielstart**

![spielstart.png](./images/spielstart.png)
<br>

**Im Spiel**

![im_spiel.png](./images/im_spiel.png)
<br>

**Kollision mit der Wand**

![kollision_wand.png](./images/kollision_wand.png)
<br>

**Gleichzeitige Kollision zweier Spieler**

![kollision_gleich.png](./images/kollision_gleich.png)
<br>

**Frontalkollision**

![frontalkollision.png](./images/frontalkollision.png)
<br>

<a name="usecases"></a>
### **UseCases**

| Usecase | Objekt/Klasse | Vorbedingungen | Nachbedingungen |Erfolgsfall | Erweiterungsfälle| Fehlerfälle |
|---|---|---|---|---|---|---|
|**UC1** Create|Game|Der Spieler befindet sich im Startbildschirm.|Ein Spiel wurde erstellt und es wird auf weitere Spieler gewartet. Der Wartebildschirm wird angezeigt. Dem Spieler wird seine Farbe im Spiel angezeigt|**1.** Der Nutzer gibt die gewünschte Spieleranzahl in das Eingabefeld ein und klickt den Start-Knopf an. <br><br>**2.** Das System erzeugt eine Spielinstanz mit den Parametern aus der Config-Datei und dem Feld vom Startbildschirm.<br><br>**3.** Das System registriert den Nutzer im Spiel.<br><br> **4.** Das System zeigt den Wartebildschirm an||**1a** Die vom Nutzer eingegebene Spieleranzahl ist nicht zwischen 2-6. <br><br> **1a.1** Das System übernimmt den Default-Wert aus der Config-Datei <br><br> **1a.2** Das System informiert den Nutzer über die altenative Spieleranzahl über eine Fehlermeldung<br><br> **2a** Ein Parameter in der Config-Datei ist nicht gültig <br><br> **2a.1** Für alle ungültigen Parameter werden Default-Werte eingesetzt<br><br> **2a.2** Der Nutzer wird über eine Meldung darüber informiert, dass Parameter auf ihren Default-Wert gesetzt wurden|
|**UC2** Cancel Wait|Game|Der Spieler befindet sich in der Lobby (Wartebildschirm).|Der Spieler befindet sich wieder im Startbildschirm. Die Spielinstanz wurde gelöscht.|**1.** Der Nutzer drückt auf den Button "Cancel".<br><br>**2.** Das System leitet ihn zum Startbildschirm zurück.<br><br>**3.** Das System löscht die Spielinstanz inkl. des Spielers.|**1a.1** Die maximale Wartezeit aus der Config-Datei ist abgelaufen. <br><br> **1a.2** Das System informiert den Nutzer über eine Meldung, dass die maximale Wartezeit abgelaufen ist. |**1a.1** Der Nutzer wartet nicht allein und klickt auf "Cancel". <br><br> **1a.2** Das System zeigt eine Fehlermeldung an, da der Nutzer nicht alleine wartet und somit das Warten nicht beenden kann.|
|**UC3** Start|Game|Alle bis auf den letzten Mitspieler befinden sich im Warteraum. Der letzte fehlende Spieler betritt den Warteraum.|Das Spiel wurde gestartet und allen Spielern wird der Spielbildschirm angezeigt.|**1.** Das System fügt den letzten Spieler der Spielerliste der Spielinstanz hinzu.<br><br>**2.** Das System zeigt den 3-Sekunden-Countdown an.<br><br>**3.** Das System wechselt zum Spielbildschirm und zeigt die Farbe des Spielers an.|||
|**UC4** Steer|Spieler|Der Spieler befindet sich im Spiel und ist noch am Leben.|Das Motorrad des Spielers bewegt sich in einer Richtung weiter|**1.** Der Nutzer drückt keine Taste an <br><br> **2.** Das System zeigt die Bewegung des Motorrades in der aktuellen Richtung und Geschwindigkeit an| **1.a** Der Nutzer drückt auf eine der Steuerungstasten gemäß der angegebenen Tastenbelegung in der Config-Datei. <br><br> **1.a.2** Das System registriert den Tastendruck <br><br> **1.a.3** Das System berechnet die neue Richtung und aktualisiert die Richtung des Spielers entsprechend der gedrückten Taste <br><br> **1.a.4** Das System zeigt die neue Richtung des Motorrads des Spielers an.||
|**UC5** Collide on|Game Field|Der Spieler ist noch am Leben und bewegt sich auf dem Spielfeld|Der Spieler ist gestorben und wurde aus dem Spiel inkl. seiner Spur entfernt.|**1.** Das System stellt fest, dass sich auf der neuen Position des Motorrads des Spielers eine Wand, eine Spur oder ein anderes Motorrad befindet.<br><br> **2.** Das System entfernt die Spur des Spielers und entfernt den Spieler aus dem Spiel.<br><br> **3.** Das System zeigt eine Meldung an, um den Nutzer zu informieren, dass er gestorben ist.|||
|**UC6** Win |Game|Der Spieler befindet sich mit nur einem weiteren Spieler auf dem Spielfeld|Das Spiel wurde gelöscht und alle Nutzer wurden zum Startbildschirm weitergeleitet.|**1.** Der andere Spieler kollidiert (siehe UC5).<br><br> **2.** Das System legt den letzten überlebenden Spieler als Sieger fest <br><br> **3.** Das System zeigt allen Nutzern den Endschirm an, wo angezeigt wird, welcher Spieler gewonnen hat.<br><br> **4.** Nach 3 Sekunden löscht das System die Spielinstanz und zeigt allen wieder den Startbildschirm an.| |
|**UC7** Lose|Game|Der Spieler befindet sich mit mindestens einem weiteren Spieler auf dem Spielfeld|Der Spieler wurde aus dem Spiel entfernt|**1.** Der Spieler kollidiert (siehe UC5).<br><br> **2.** Das System entfernt den Spieler aus dem Spiel| | |
|**UC8** Tie|Game|Der Spieler befindet sich mit nur einem weiteren Spieler auf dem Spielfeld|Das Spiel wurde gelöscht und alle Nutzer wurden zum Startbildschirm weitergeleitet.|**1.** Beide Spieler kollidieren gleichzeitig (siehe UC5).<br><br> **2.** Das System legt fest, dass das Spiel unentschieden ist.<br><br> **3.** Das System zeigt allen Nutzern den Endschirm an, wo angezeigt wird, dass das Spiel unentschieden ist.<br><br> **4.** Nach 3 Sekunden löscht das System die Spielinstanz und zeigt allen wieder den Startbildschirm an.| |

<a name="qualitaetsziele"></a>
## Qualitätsziele

|Qualitätsziel  |Erklärung|
|---------------|---------|
|Kompatibilität |Es können mindestens zwei Spieler auf unterschiedlichen Geräten miteinander spielen|
|Fehlertoleranz/ Stabilität| Das Spiel soll bestehen/ stabil bleiben, auch wenn Teilnehmer abstürzen|
|Zuverlässigkeit|Das Spiel soll immer gleich schnell laufen (kein "Jittering")|
|Ein Spiel am Stück (Rematch-Option)|Es reicht aus, wenn ein Spiel am Stück spielbar ist (Keine "direkte" Rematch-Option)|

<a name="stakeholder"></a>
## Stakeholder

|Rolle  |Kontakt        |Erwartungshaltung|
|-------|---------------|-----------------|
|Kunde  |Martin Becke   |Entwicklung eines Tron-Spiels als verteiltes System, gut dokumentiert (Code <-> Dokumentation), Konzepte aus der Vorlesung sinnvoll angewendet und verstanden|
|Entwickler|Kathleen Neitzel, Kjell May, Viviam Ribeiro| - Das Spiel als verteiltes System entwickeln und dabei die Inhalte aus der Vorlesung praktisch verstehen und anwenden können <br>- PVL erhalten|


<a name="randbedingungen"></a>
# Randbedingungen


| Randbedingung           | Erläuterung                                 |
|-------------------------|---------------------------------------------|
| Programmiersprache | Die Vorgabe der Aufgabenstellung erfordert die Nutzung einer objektorientierten Programmiersprache. Die Nutzung von Java wird empfohlen, da in dieser Sprache Code-Beispiele in den Vorlesungen gezeigt werden. Wir haben uns aus diesem Grund für Java entschieden. |
| Versionsverwaltung | Die Nutzung von unserem hochschuleigenen Gitlab ist ebenfalls vorgeschrieben. Wir arbeiten gerne mit dieser Versionsverwaltung, da ein effizientes Zusammenarbeiten im Team ermöglicht und zu intensivem Austausch angeregt wird. |
| Schnittstellen     | Kommunikation RPC und REST |

**\<Organisatorische Randbedingungen>**
| Randbedingung   | Erläuterung |
|-----------------|-------------|
| Team            | Kjell May, Viviam Ribeiro und Kathleen Neitzel aus dem Studiengang der Angewandten Informatik. Fachsemester 6 und 7. |
| Zeit            | Standalone Applikation bis Mitte November, endgültige Abgabe Ende Januar 2023. |


<a name="kontextabgrenzung"></a>
# Kontextabgrenzung

<a name="fachlicherkontext"></a>
## Fachlicher Kontext


![fachlicher_trontext.png](./images/fachlicher_trontext.png)



<a name="technischerkontext"></a>
## Technischer Kontext



![technischer_trontext.png](./images/technischer_trontext.png)


<a name="loesungsstrategie"></a>
# Lösungsstrategie


|Use Case| Akteur | Funktionssignatur |Vorbedingung| Nachbedingung | Ablaufsemantik | Fehlersemantik |
| --- | --- | --- | --- | --- | --- | --- |
|UC1 | Controller | int handleInputPlayerCount() | Der Nutzer hat die gewünschte Spieleranzahl eingegeben und auf den Button "Start" gedrückt. | Die Spieleranzahl der Spielinstanz wird im Model gespeichert. |Die Methode liefert die durch den Benutzer eingegebenen Spieleranzahl | Wenn die Spieleranzahl keine Zahl zwischen 2 und 6 ist, wird die Methode loadDefaultPlayerCount() aufgerufen |
|UC1 | Controller | int loadDefaultPlayerCount() | Der Nutzer hat eine ungültige Spieleranzahl eingegeben. | Die Default-Spieleranzahl wird im Model gespeichert.  |Die Methode liefert den Default-Wert für die Spieleranzahl aus der Config-Datei und ruft die Methode informUser("Spieleranzahl muss eine Zahl zwischen 2 und 6 sein. Der Default-Wert <<Default-Wert>> wird gesetzt") | Wenn keine Zahl geladen werden konnte, wird eine Exception mit Fehlerbeschreibung geworfen. |
|UC1 | Controller |int[] loadConfigParams() | Eine gültige Spieleranzahl wurd im Model gespeichert.  | Es wurde eine Liste mit Spielparametern erzeugt. |Die Methode liefert die Parameter aus der Config-Datei in einem int-Array der Länge 4. <br> **Index 0:** Die maximale Wartezeit <br> **Index 1:** Die Tastenbelegung (0: Steuerung über die Pfeiltasten rechts/links; 1: Steuerung über die Tasten 'A'/'D') <br> **Index 2:** Die Geschwindigkeit<br> **Index 3:** Die Spielfeldgröße | Wenn ein Parameter nicht im gültigen Wertebereich liegt oder nicht geladen werden konnte, wird der entsprechende Default-Wert gesetzt: <br> **Default maximale Wartezeit:** 120 Sekunden<br> **Default Geschwindkeit:** 100 (Einheit: Bewegungen/Sekunde) <br> **Default Spielfeldgröße:** Geschwindigkeit * 5 <br><br> Anschließend wird die Methode informUser("Ein oder mehr Parameter aus der Konfigurationsdatei waren ungültig oder konnten nicht geladen werden. Die betroffenen Parameter wurde auf Default-Werte gesetzt.") aufgerufen |
|UC1 | Model | static Game createGameInstance() | Die Spielparameter wurde durch Benutzereingabe und Laden aus der Config-Datei gesetzt. | Es wird eine Spielinstanz mit der gespeicherten Spieleranzahl und den geladenen Spielparametern erstellt und im Model gespeichert. |Die Methode erstellt eine neue Spielinstanz mit den geladenen Parametern. Die Methode gibt die erstellte Spielinstanz zurück|  |
|UC1 | Model | Player createPlayer() | Es wurde eine Spielinstanz erstellt. Der Nutzer wurde zum Warteraum weitergeleitet. | Es wurde eine neue Spielerinstanz erzeugt. | Die Methode erstellt einen neuen Spieler, indem dem Spieler eine noch nicht vergebene ID und Farbe vergeben wird. Die Methode gibt den erstellten Spieler zurück | |
|UC1 | Model | void registerPlayer(Player) | Es wurde ein Spielerinstanz erzeugt. | Die zuletzt erzeugt Spielerinstanz in der SPielerliste im Model gespeichert worden. |Die Methode wird aufgerufen, nachdem ein Spieler erstellt wurde. Die Methode fügt die als Parameter übergebene Spielerinstanz in die Spielerliste hinzu.| Wenn der übergebene Player null ist, wird eine NullPointerException geworfen.<br>Wenn der übergebene Player eine Farbe hat, die bereits vergeben ist, wird eine Exception mit Fehlerbeschreibung geworfen<br>Wenn der übergebene Player eine bereits vergebene ID hat, wird eine Exception mit Fehlerbeschreibung geworfen|
|UC1, UC2, UC5 | View | void informUser(String) | Eine Exception wurde geworfen. | Dem Nutzer wird ein Text mit der entsprechenden Fehlerbeschreibung angezeigt. |Zeigt Fehlerbeschreibung der Exception dem Nutzer an | |
|UC1, UC2, UC6, UC8 | View |void showScreen(String) | Der Spielzustand wurde im Controller gewechselt. | Dem Nutzer wird einen anderen Bildschirm angezeigt. |Die Methode zeigt den Bildschirm an, der zum übergebenen Bildschirmzustand passt.  | Wenn zum übergebenen Zustandsparameter kein anzuzeigenden Bildschirm gehört, wird eine Exception mit einer Fehlerbeschreibung geworfen. |
|UC2 | Controller | void handleWaitingButtonClick() | Der Nutzer befindet sich im Wartebildschirm und hat auf den Button "Cancel" geklickt.| Der Spieler wird im Erfolgsfall zum Startbildschirm weitergeleitet. |Die Methode bricht den Wartevorgang ab. | Wenn mehr als ein Spieler warten, wird die Methode informUser("Wartevorgang kann nicht abgebrochen werden, wenn mehrere Spieler warten") aufgerufen und anschließend eine Exception mit Fehlerbeschreibung geworfen.|
|UC2, UC6, UC8 | Controller | void deleteGameInstance() | Der Wartevorgang wurde durch Nutezraktion oder Timerablauf abgebrochen oder das Spiel wurde zu Ende gespielt. | Die Spielinstanz wurde gelöscht. |Die Methode löscht die aktuelle Spielinstanz. |  |
|UC2 | Controller| void cancelWaitingTimer() | Der Nutzer befindet sich im Warteraum und der Timer des Warteraums ist abgelaufen, weil zu lange auf anderen Spieler gewartet wurde. |Der Nutzer wird zum Startbildschirm weitergeleitet. |Die Methode bricht den Wartevorgang ab und informiert den Nutzer über den Aufruf der Methode informUser("Wartezeit zu lang. Der Wartevorgang wird abgebrochen ...").| |
|UC3 |Controller | void notifyCountdownOver() | Der Countdown wurde von der View dem Nutzer angezeigt. | Der Controller bekommt mit, dass der Countdown vorbei ist und ruft die Methode startGame() des Models auf. | Die Methode erzeugt einen Event für den Controller, dass der Countdown vorbei ist.|  |
|UC3 | View | Color getPlayerColor(int) |Es wurden Spielerinstanzen erzeugt. | Die Farbe wird zurückgegeben. |Die Methode gibt die Farbe des Spieler zurück, dessen ID als Parameter übergeben wurde |Wenn die übergebene ID keinem Spieler gehört, wird eine Exception mit Fehlerbeschribung geworfen |
|UC3 | View | void showPlayerColor(Color) | Die Farbe des Spielers wurde ermittelt und wird als Aufrufparameter übergeben. | Dem Nutzer wird die übergebene Farbe im aktuellen Bildschirm angezeigt. |Die Methode zeigt die Spielerfarbe an, die als Parameter übergeben wird | | 
|UC4 | Model | void movePlayers() | Der Nutzer befindet sich im Spielbildschirm. | Die Position der Spieler hat sich geändert. |Die Methode bewegt alle Spieler geradeaus entsprechend ihrer aktuellen Richtung und dem geladenen Geschwindigkeitsparameter. | |
|UC4 | View | void drawPlayers() | Der Nutzer befindet sich im Spielbildschirm und die Spielfeldanzeige soll die aktuelle Positionen der Spieler zeigen | Auf dem Spielfeld werden die aktuell lebenden Spieler an ihren aktuellen Positionen angezeigt. |Die Methode ruft die Methode getPlayersinGameField() auf, um Informationen über die aktuellen Spieler zu erhalten. Anhand dieser Informationen werden die Spieler an ihrer aktuellen Position und Ausrichtung in Szene gesetzt. | Im Fehlerfall wird eine Exception mit Fehlerbeschreibung geworfen |
|UC4 | View | void drawTileColors() | Der Nutzer befindet sich im Spielbildschirm und die Spielfeldanzeige soll die aktuelle Einfärbung des Spielfeldes zeigen | Auf dem Spielfeld wird die aktuell gültige Einfärbung des Spielfeldes angezeigt. |Die Methode holt von jedem Spieler im Spielfeld die eingefärbten Felder und die entsprechende Farbe. Anhand dieser Informationen wird jedes mit Farbe belegte Feld des Spielfeldes in der ermittelten Farbe angezigt. | |
|UC4| Controller | String handleDirectionKeyboardInput() |Der Nutzer hat eine Taste füe die Steuerung seines Spielers gedrückt.| Die gewünschte Richtungsänderung wird zurückgegeben. | Die Methode liefert die Richtung, die über die Tastatur vom Nutzer eingegeben wurde. Wenn die entsprechende Tastenbelegung für die Steuerung des Motorrads nach links gedrückt wurde, gibt die Methode den String 'left' zurück. <br> Wenn die entsprechende Tastenbelegung für die Steuerung des Motorrads nach rechts gedrückt wurde, gibt die Methode den String 'right' zurück.| Im Fehlerfall wird eine Exception mit Fehlerbeschreibung geworfen |
|UC4 | Model | void changePlayerDirection(int, String) | Der Nutzer hat eine Taste für die Richtungsänderung gedrückt und die gewünscht Richtung wurde ermittelt. | Der Spieler wurde um 90° in die gewünschte Richtung gedreht.|Der wird als Parameter die Spieler-ID übergeben. Die Methode ändert die Richtung des Spielers mit der übergebenen Spieler-ID nach links oder rechts. Der übergebene String liefert die Information, ob der Spieler nach links oder nach rechts gesteuert wird. | Im Fehlerfall wird eine Exception mit Fehlerbeschreibung geworfen. |
|UC5 | Model | boolean checkCollision(Position) | Die Position des Spielers wurde geändert. | Die Information über das Vorhandensein einer Kollision wurde ermittelt. |Die Methode prüft, ob es eine Kollision an der übergebenen Position gegeben hat. Sie gibt true zurück, wenn eine Kollision stattgefunden hat, sonst false. | Im Fehlerfall wird eine Exception mit Fehlerbeschreibung geworfen|
|UC5, UC7|  Model| Player removePlayer(int) | Es hat eine Kollision stattgefunden. | Der Nutzer, dessen Spieler entfernt wurde, ist nicht mehr auf dem Spielfeld zu sehen. Tastendrücken des Nutzers für die Steuerung werden nicht mehr registriert. | Die Methode entfernt den Spieler mit der übergebenen Spieler-ID aus dem Spielfeld. Die Methode informUser("You lose...Du wurdest aus dem Spiel entfernt"), um den Nutzer zu informieren und gibt den entfernten Spieler zurück. | |
|UC6 | Model |void setGameWinner(int) | Es gibt nur noch ein Spieler im Spiel. | Die ID des letzten Spielers wurde in einer globalen Variable "gameResult" gespeichert.|Der übergebene Player ist der letzte im Spiel. Seine Spieler-ID wird von der Methode als Sieger registriert. | |
|UC8| Model | void saveCollisionTimeStamps() |Es befinden sich nur noch zwei Spieler im Spiel und eine Kollision hat stattgefunden. | Der Kollisionszeitpunkt wurde in einer globalen Variable gespeichert.| Die Methode speichert den Zeitpunkt der Kollision  | |
|UC8| Model| boolean checkCollisionTimeStamps(Long, Long) | Es befinden sich nur noch zwei Spieler auf dem Spielfeld gibt ein Kollisionszeeitpunkt wurde gespeichert.| Liefert die Information, on zwei zeitgleiche Kollisionen stattgefunden haben. | Sie prüft, ob die Differenz der beiden übergebenen Zeitpunkte <= 0,1 ist. Wenn ja, dann gibt sie true zurück, sonst false.|Wenn einer der übergeben Parameter null ist, wird eine NullPointerException mit Fehlerbeschreibung geworfen. |
|UC8| Model | void setGameAsTied() | ZWei Kollisionen haben gleichzeitig stattgefunden.| Die globale Variable "gameResult" wurde auf -1 gesetzt.| Die Methode setzt den Gewinner auf -1, um zu zeigen, dass das Spiel unentschieden ausgegangen ist.| |
| UC1-8 | View |updatePlayer(int[]) |Im Model wurden Daten zu den Spielern geändert. | Die View hat ihre Daten aktualisiert. | Die Methode aktualisiert die Spielerliste, die in der View gehalten wird. | |
| UC3 | View | setGameFieldSize(int)| Alle Spieler haben den Warteraum betreten.|Die Spielfeldgröße wird in der View gespeichert.  |Die Methode setzt die Spielfeldgröße in der View, die aus der Config-Datei geladen wurden| |
|UC6,7,8| Controller| endGame()| Im Model wurde ein Gewinner festgelegt oder das Spiel wurde als unentschieden entschieden. | Die State Maschne im Controller befindet sich im Zustand "End" | Die Methode ändert die State Maschine im Controller zum Zustand "End"| |
|UC6,7,8|View|notifyGameResult(int)| Die State Maschine des Controllers befindet sich im Zustand "End"| Die View weiß, wie das Spiel ausgegangen ist und zeigt im nächsten Schitt den Endbildschirm an.| Die Methode setzt den Gewinner des Spiels in der View-Komponente.| |







<a name="bausteinsicht"></a>
# Bausteinsicht

<a name="whiteboxgesamt"></a>
## Whitebox Gesamtsystem

Game Of Trons ist in drei Komponenten aufgeteilt, die in der unteren Abbildung zu sehen sind.
Die Komponenten bieten über Schnittstellen ihre Funktionalitäten an und nutzen ebenso über Schnittstellen die Funktionalitäten anderer Komponenten.

![Whitebox_Gesamtsystem_Abb](images/Whitebox_Gesamt.png) 

Die Komponentenaufteilung richtet sich nach dem eingesetzten MVC-Architekturmuster. 

**Enthaltene Bausteine**

| Baustein | Kurzbeschreibung |
| --- | --- |
| Model | Enthält das Datenmodell und die Spielelogik |
| View | Verantwortlich für die GUI-Anzeige|
|Controller | Regelt die Ablaufsemantik außerhalb des Spiels und vermittelt zwischen Model und View.|


<a name="modelblackbox"></a>
### Model (Blackbox)

**Zweck/ Verantwortung**

Das Model ist in unserem Spiel sowohl für die Spielelogik, als auch die Lobbylogik zuständig. Es berechnet den aktuellen Spielstand anhand der Eingaben und gibt dies an die View weiter. Die Lobbylogik kümmert sich um das Erstellen und Starten von Spielen, also vom Start- über den Warte- zum Spielbildschirm.

**Schnittstelle(n)**

Um die Tasteneingaben verarbeiten zu können benötigt das Model die angebotene Schnittstelle *ModelController* vom Controller. Um den neuen Spielstand an die View zu übergeben, bietet das Model selbst eine Schnittstelle *ViewModel* an.


<a name="viewblackbox"></a>
### View (Blackbox) 

**Zweck/ Verantwortung**

 Das View-Subsystem implementiert die gleichnamige View des eingesetzten MVC-Patterns.
 Das Subsystem stellt die grafische Benutzeroberfläche bereit. Es nimmt Aktionen vom Nutzer entgegen und leitet diese zum Controller weiter. 

 Bei Bedarf, im Falle einer Änderung im Datenmodell (Datenmodell wird im Subsystem Model verwaltet), informiert der Controller die View über die Änderung. Daraufhin passt die View die angezeigten Inhalte an.

**Schnittstelle(n)**

Die View bietet die Anzeigefunktionalitäten, die Aktualisierung der Spielfeldgröße und das Setzen des Spielergebnisses über die Schnittstelle **IControllerView**


| Methode | Kurzbeschreibung |
| --- | --- |
| showScreen(String) | Zeigt den Bildschirm an, der zum als String übergebenen Programmzustand passt |
| setGameFieldSize(int)| setzt die Spielfeldgröße in der View|
| notifyGameResult(int) | setzt ein Spielergebnis in der View |


Die View erlaubt das Aktualisieren der Spielerdaten über die Schnittstelle **IModelView**

| Methode | Kurzbeschreibung |
| --- | --- |
|updatePlayer(int[]) | Aktualisiert die Spielerliste, die in der View gehalten wird|


<a name="controllerblackbox"></a>
### Controller (Blackbox) 

**Zweck/ Verantwortung**

Der Controller steuert den gesamten Ablauf rund um das Spiel. Dieser umfasst das Weiterleiten vom Startbildschirm in die Lobby, das Warten auf weitere Mitspieler, das Mitteilen des Siegers am Ende des Spiels, das Löschen der beendeten Runde und das Zurückleiten zum Startbildschirm. Außerdem setzt der Controller die Kommunikation zwischen der View- und der Modelkomponente sowohl vor und nach als auch während der laufenden Runde über mehrere Schnittstellen um.
Während des Spiels nimmt der Controller die Benutzereingaben zur Steuerung des Spielers an und leitet diese an das Model weiter.

**Schnittstelle(n)**

Der Controller bietet Funktionalitäten für das Model v.a. zur Kommunikation mit der View über die Schnittstelle **IModelController** an.


| Methode | Kurzbeschreibung |
| --- | --- |
| void updatePlayersInGameField(List<Player>) | Zeigt den Bildschirm an, der zum als String übergebenen Programmzustand passt |
| void endGame(int?) | aktualisiert die Spielerliste in der View |
| static Game createGameInstance() | setzt ein Spielergebnis in der View|


Der Controller bietet Funktionalitäten für die View v.a. zur Kommunikation mit dem Model über die Schnittstelle **IViewController** an.

| Methode | Kurzbeschreibung |
| --- | --- |
| int handleInputPlayerCount() | |
| void handleWaitingButtonClick()| |
| String handleDirectionKeyboardInput()|  |
| void notifyGameResult(int) | |
| void notifyCountdownOver() | |

<a name="applicationstubblackblox"></a>
### Application Stub (Blackbox) 


<a name="ebene2"></a>
## Ebene 2 



**Controller Whitebox (Ebene 1)**

![Controller_Blackbox.png](./images/Controller_Blackbox.png)
<br>


## Ebene 2 {#_ebene_2}

### Whitebox Model

![Model_Ebene2](./images/Model_Ebene2.png)


### Whitebox View 

![View_Whitebox](./images/Whitebox_View.png)

|Methode| Kurzbeschreibung|
| --- | --- |
|drawScreen() | Abstrakte Methode, die in den konkreten Klassen die Bildschirmanzeige zeichnet. |
|informUser(String) | konkrete Methode, die eine Meldung (als Aufrufparameter übergebe) dem Nutzer anzeigt. |
|getPlayerColor(int) | |
|showPlayerColor(int) | |
|drawPlayers() | |
|drawTileColors() | |





<a name="ebene3"></a>
## Ebene 3 

### Whitebox Model

![Model_Ebene3](images/Model_Ebene3.png)

|Methode    |Kurzbeschreibung|
|-----------|----------------|
|addPlayer()|Diese Methode kümmert sich um das Hinzufügen eines neuen Spielers zum Spiel. Dabei wird ein Player-Objekt initialisiert mit einer noch nicht vergebenen Farbe.|
|cancelWait()|Diese Methode bricht die Spielesuche/ das Warten auf weitere Spieler ab|
|startGame()|Diese Methode initialisiert und startet das eigentliche Spiel, sobald alle Spieler beigetreten sind.|
|movePlayer()|Mit dieser Methode wird die Bewegung im Spiel modelliert. Tasteneingaben vom Spieler bestimmen seine Richtung. Hier wird außerdem mit internen Methoden weiter überprüft, ob es Kollisionen gab und demnach gehandelt.|
|checkCollision()| Diese Methode überprüft, ob es eine Kollision zwischen einem Spieler und einem anderen Spieler, Spur oder Wand gegeben hat.|
|clearPlayer()|Wenn ein Spieler kollidiert ist, soll er vom Spielfeld verschwinden.|

### Whitebox View


![Diagramme_VS__1_.jpg](Diagramme_VS__1_.jpg)

### Whitebox Controller

**Controller Whitebox (Ebene 3)**

![WB_C.png](./images/WB_C.png)
<br>

State Machine
![WB_SM.png](./images/WB_SM.png)
<br>


Methodenliste
| Methode           | Beschreibung                                 |
|-------------------------|---------------------------------------------|
| createGame() | Erstellt eine neue Spielinstanz, nutzt User Input der View für die Spielerzahl und lädt die Config-Datei. Falls bereits eine vorhanden --> bestehende ersetzt. |
| deleteGame() | Löscht die bestehende Spielinstanz. Falls keine vorhanden --> Exception |
| checkState() | Prüft, ob eine gültige Anzahl an Spielern vorhanden sind. Prüft, ob benötigte Spielinstanz für angefragte Operation vorhanden. |
| next() | Wechselt in den gültigen angefragten Zustand. Aktualisierung des aktuellen States. Vorheriger State wird auf Stack gespeichert. |
| back() | Lädt den letzten (gültigen) Zustand auf dem Stack als aktuellen State. |
| resetStateMachine() | Löscht alle auf dem Stack gespeicherten States und lädt den Default State als aktuellen State. |
| updateSpieler() | Aktualisiert die Spielerliste. |
| updateField() | Aktualisiert alle Farben des Spielfelds. |
| ...() | ... |

? Model und Controller auseinanderpflücken --> Use Cases überlegen.

<a name="laufzeitsicht"></a>
# Laufzeitsicht

## *\<Bezeichnung Laufzeitszenario 1>* {#__emphasis_bezeichnung_laufzeitszenario_1_emphasis}

-   \<hier Laufzeitdiagramm oder Ablaufbeschreibung einfügen>

-   \<hier Besonderheiten bei dem Zusammenspiel der Bausteine in diesem
    Szenario erläutern>

## *\<Bezeichnung Laufzeitszenario 2>* {#__emphasis_bezeichnung_laufzeitszenario_2_emphasis}

...

## *\<Bezeichnung Laufzeitszenario n>* {#__emphasis_bezeichnung_laufzeitszenario_n_emphasis}

## Usecase 1 Create
![LFZ_create](images/LFZ_create.png)

![LFZ_create_b](images/LFZ_create_b.png)

## Usecase 2 CancelWait
![LFZ_cancel](images/LFZ_cancel.png)

## Usecase 3 Start
![LFZ_start](images/LFZ_start.png)

## Usecase 3 Spielstart
![Sequenzdiagramm_Spielstart](images/SD_Spielstart.png)

## Usecase 4 Spieler steuern

![Sequenzdiagramm_UC4](images/Sequenzdiagramm_UC4.png)

## Usecase 4.a Erweiterungsfall Spieler steuern
![Sequenzdiagramm_UC4a](images/Sequenzdiagramm_UC4_1a.png)

## Usecase 5 gegen Spielobjekt kollidieren
![Sequenzdiagramm_UC5](images/Sequenzdiagramm_UC5.png)

## UC6 Win
![Sequenzdiagramm_Spielende](images/SD_UC6Win.png)

## UC7 Lose
![Sequenzdiagramm_Spielende](images/SD_UC7Lose.png)

## UC8 Tie
![Sequenzdiagramm_Spielende](images/SD_UC8Tie.png)
...

<a name="verteilungssicht"></a>
# Verteilungssicht {#section-deployment-view}

## Infrastruktur Ebene 1 {#_infrastruktur_ebene_1}

***\<Übersichtsdiagramm>***

Begründung

:   *\<Erläuternder Text>*

Qualitäts- und/oder Leistungsmerkmale

:   *\<Erläuternder Text>*

Zuordnung von Bausteinen zu Infrastruktur

:   *\<Beschreibung der Zuordnung>*

## Infrastruktur Ebene 2 {#_infrastruktur_ebene_2}

### *\<Infrastrukturelement 1>* {#__emphasis_infrastrukturelement_1_emphasis}

*\<Diagramm + Erläuterungen>*

### *\<Infrastrukturelement 2>* {#__emphasis_infrastrukturelement_2_emphasis}

*\<Diagramm + Erläuterungen>*

...

### *\<Infrastrukturelement n>* {#__emphasis_infrastrukturelement_n_emphasis}

*\<Diagramm + Erläuterungen>*

<a name="querschnitt"></a>
# Querschnittliche Konzepte {#section-concepts}

## *\<Konzept 1>* {#__emphasis_konzept_1_emphasis}

*\<Erklärung>*

## *\<Konzept 2>* {#__emphasis_konzept_2_emphasis}

*\<Erklärung>*

...

## *\<Konzept n>* {#__emphasis_konzept_n_emphasis}

*\<Erklärung>*

<a name="architektur"></a>
# Architekturentscheidungen 

Ausschlaggebend für die Architektur ist das MVC-Entwurfsmuster, das häufig bei Anwendungen mit Benutzeroberfläche eingesetzt wird, was auch bei der hier behandelten Anwendung der Fall ist.

Dieses Entwurfsmuster implementiert das Prinzip des Separation of Concerns, wodurch die Wartbarbeit und des Systems steigt und Auswirkungen von Änderungen eher lokal bleiben. Diese Eigenschaften führen auch dazu, dass das System erweiterbar ist.

Die Vorteile, die das Einsetzen dieses Patterns bringen, sind für die Entwicklung dieser Software unerlässlich, da der Softwareentwicklungsprozess iterativ gestaltet ist und da Änderungen in der Logik oder Architektur aufgrund von neuen Wunschäußerungen durch die Stakeholder oder aufgrund von Fehleinschätzungen durch das unerfahrene Entwicklungsteam zu erwarten sind.

<a name="qualitaet"></a>
# Qualitätsanforderungen

## Qualitätsbaum

![Qualitätsbaum](./images/qualitaetsbaum.png)

## Qualitätsszenarien

|ID |Szenario|
|---|--------|
|K01|Es lässt sich ein faires Spiel erstellen und starten mit 2-6 Spielern|
|F01|Ein Spieler verliert die Verbindung zum Spiel. Das Spiel geht trotzdem weiter für die anderen Spieler|
|F02|Eingaben eines Spielers kommen verzögert oder unregelmäßig an. Das Spiel registriert trotzdem für jeden Spieler regelmäßig gleich viele Eingaben und geht fair weiter|
|Z01|Pakete im Netzwerk haben Varianz in der Laufzeit (Jittering). Das Spiel geht trotzdem gleich schnell weiter|
|R01|Ein Spiel wurde beendet. Alle Spieler können den Endbildschirm sehen und werden dann zurück zum Startbildschirm geleitet. Die Option dasselbe Spiel zu wiederholen gibt es nicht.|

**TODO Wahrscheinlich noch mehr Szenarien**

<a name="risiken"></a>
# Risiken und technische Schulden {#section-technical-risks}

<a name="glossar"></a>
# Glossar {#section-glossary}

|Begriff    |Definition|
|-----------|----------|
|||

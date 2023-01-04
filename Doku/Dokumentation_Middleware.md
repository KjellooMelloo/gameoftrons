# 

**Über arc42**

arc42, das Template zur Dokumentation von Software- und
Systemarchitekturen.

Template Version 8.1 DE. (basiert auf AsciiDoc Version), Mai 2022

Created, maintained and © by Dr. Peter Hruschka, Dr. Gernot Starke and
contributors. Siehe <https://arc42.org>.

# Einführung und Ziele

Es wird eine Middleware für die verteilte Anwendung Game Of Trons entwickelt.

## Aufgabenstellung 

1. Callees in Application Stubs können sich als Remote Objects bei der Middleware mit ihrer physikalischen Adresse registrieren
2. Entgegennehmen und Weiterleitung eines Methodenaufrufs auf ein Remote Object
3. Umwandlung von Funktionsaufrufen in Nachrichten 
4. Vereinheitlicht Methodenaufrufe in ein vordefiniertes Nachrichtenformat
5. Die physikalische Adresse von Diensten kann abgefragt werden.
6. Kommunikation mit dem Betriebssystem, um Nachrichten zu versenden und zu empfangen
7. Nachrichten, die vom Betriebssystem empfangen wurden, können entgegengenommen werden
8. Umwandlung von Nachrichten in Funktionssignaturen
9. Funktionssignaturen werden an die ausführende Komponente weitergegeben



## Qualitätsziele 

| ID | Qualitätsziel | Kurzbeschreibung |
| --- | --- | --- |
| Q1 | Verteilungstransparenz| Entfernte Zugriffe, Bewegungen, Migrationen und Replikationen werden vom Nutzer versteckt|
| Q2 | Offenheit (Openness) |  Schnittstellen müssen gut definierte Ablauf- und Fehlersemantik haben, Komponenten sollten austauschbar sein, Erweiterung um Komponenten sollte möglich sein|
| Q3 | Geographische Skalierung | Die Benutzer können sich überall auf der Welt befinden |




## Stakeholder 

|Rolle|Kontakt|Erwartungshaltung|
|-|-|-|
|Kunde  |Martin Becke   |Entwicklung eines Tron-Spiels als verteiltes System, gut dokumentiert (Code <-> Dokumentation), Konzepte aus der Vorlesung sinnvoll angewendet und verstanden|
|Entwickler|Kathleen Neitzel, Kjell May, Viviam Ribeiro| - Funktionsfähige Middleware mit gewünschter Funktionalität entwickeln, dass das Spiel an mehreren Rechnern gespielt werden kann|
|Kunde|GameOfTrons|Möglichkeit auf mehreren Rechnern gespielt werden zu können|


# Randbedingungen 

# Kontextabgrenzung 

## Fachlicher Kontext 

![Fachlicher_Trontext](./images/middleware_fachlicher_trontext.png)

| Use Case |Vorbedingung |Ablaufsemantik |Nachbedingung |Fehlerfälle | Erweiterungsfälle |
| --- | --- | --- | --- | --- | --- |
| UC1 Register Method | Ein definiertes Interface soll für RPCs erreichbar sein | **1.** Der Application Stub ruft den Server-Stub der Middleware auf <br> **2.**  Der ServerStub ruft den Name-Server auf <br>**3.** Der NameServer prüft, ob die Schnittstelle nicht bereits in der Tabelle eingetragen ist.<br> **4.** Der NameServer trägt den Schnittstellenidentifikator und die dazugehörige Adresse in eine Tabelle ein<br> | In der Tabelle des NameServers ist die Schnittstelle und ihre Adresse eingetragen. | |**3.a.1** Die Schnittstelle ist bereits m NameServer eingetragen <br> **3.a.2** Die eingetragene Adresse wird mit der neuen überschrieben |
| UC2 Invoke Method |In der Anwendung wird eine Methode einer Remote-Komponente aufgerufen | **1.** Das System ruft den Application-Caller-Stub der aufrufenden Komponente auf <br> **2.** Der  Application Stub ruft die Middleware-Schnittstelle auf <br> **3.** Es wird geprüft, ob eine Adresse und Portnummer zur aufgerufenen Methode eingetragen ist  (siehe UC5) <br> **4.** Die Adresse und die Portnummer werden aus dem Name Server geholt <br> **5.** Der Client Stub wandelt den Methodenaufruf in eine Nachricht um (siehe UC3) <br> **6.** Der Client Stub ruft die Senderkomponente auf (siehe UC 3.1)| Die Nachricht wurde verschickt|**3.a.1** Die aufgerufene Komponente ist nicht bei der Middleware registriert <br> **3.a.2** Das System wirft eine Exception auf.| |
| UC3 Marshaling Method Call| UC2 bis Schritt 2 | **1.** Der Client Stub serialisiert den Methodenaufruf in ein Nachrichtenformat <br> **2.** Weiter mit UC 3.1| Der Methodenaufruf ist als Nachricht vorhanden | | |
| UC3.1 send |Eine Nachricht wurde in der Middleware erzeugt und soll versendet werden.|**1.** Der Client Stub ruft den Sender auf <br> **2.** Der Sender erstellt ein Socket  <br> **3.** Den Sender erstellt ein UDP-Datagramm mit der zu versendenden Nachricht, die Empfänger-Adresse und die Empfänger-Portnummer. |Die Nachricht wurde versendet|||
| UC4 Unmarshaling Message | Der Receiver in der Middleware hat eine Nachricht empfangen| **1.** Der Receiver ruft die ServerStub-Komponente auf <br> **2.** Der ServerStub nutzt den Unmarshaler, um die Nachricht in einen Methodenaufruf umzuwandeln <br> **3.** Der Server Stub ruft die Application-Stub-Callee-Schnittstelle der Komponente auf, die den Methodenaufruf empfangen soll (siehe UC5)| Ein Methodenaufruf wurde erzeugt| | |
| UC4.1 receive | UC 3.1: Eine Nachricht wurde über das Netzwerk versendet  |**1.** Der Socket im Receiver der Middleware des Empfängers bekommt ein UDP-Datagramm <br> **2.** Der Receiver ruft die Server-Stub-Komponente auf (siehe UC4)| Eine Nachricht liegt in der Middleware des Empfängers vor|||
| UC5 lookup | UC2: Invoke Method bis Schritt 2 | **1.** Der Client Stub ruft den Nameserver auf <br> **2.** Der NameServer prüft,ob die aufgerufene Methode mit der dazugehörigen Adresse und Portnummer in der Tabelle eingetragen ist<br> **3.** Der NameServer liefert die zugehörige IP-Adresse und Portnummer zurück | Die  IP-Adresse und die Portnummer zu der gesuchten Funktion werden zurückgegeben | Es gibt keinen Eintrag mit dieser ID. Dann wird der RPC verworfen. ||
| UC6 Call Method | UC 4 : Der Server Stub hat eine Nachricht in einen Methodenaufruf umgewandelt |**1.** Der ServerStub ruft die Call-Schnittstelle des Application-Callee-Stubs <br> **2.** Der Application-Callee-Stub ruft die dazugehörige Komponente lokal auf. | Die aufgerufene Methode wird ausgeführt.|


## Technischer Kontext

![Technischer_Trontext](./images/middleware_technischer_trontext.png)



# Lösungsstrategie 

## Methodenliste

| Usecase | Akteur |Funktionssignatur| Vorbedingung | Nachbedingung | Ablaufsemantik | Fehlersemantik |
|---|---|---|---|---|---|---|
|UC1| ServerStub, NameServer | int register(int, String InetAddress, int) | Ein CalleeStub aus dem ApplicationStub möchte sich als RemoteObject registrieren | Der Name Server hat eine Antwort zurückgeschickt | Der Server Stub schickt eine Nachricht mit den notwendigen Daten (Aufrufparameter) zur Registrierung nach dem TCP-Protokoll an den Name Server. Der Name Server schickt die eingetragene ID über eine Nachricht zurück. Die ID wird zurückgegeben. | Eine Registrierung war nicht möglich, weil die Komponente sich nur einmal registrieren darf, wenn es sich um das Model handelt. In dem Fall wird -1 zurückgegeben.|
|UC1|  NameServer | void register(int, String InetAddress, int) | Eine Nachricht ist aus einem ServerStub angekommen | Das RemoteObject wurde im NameServer gespeichert | Der NameServer entpackt die Daten aus der Nachricht und prüft, ob zur mitgegebenen ID und Methodennamen bereits ein Eintrag vorhanden ist(Aufruf checkInterfaceInTable).  Aus der übergebenen Interface-ID wird eine eindeutige Object-ID erzeugt. Die Object-ID und der Methodenname werden mit der übergebenen InetAddress und Portnummer eingetragen. Die erzeugte Object-ID wird an den anfragenden Server Stub zurückgeschickt.| Eintrag mit der ID und dem Methodennamen existiert bereits. Dann wird der Eintrag überschrieben. Wenn es sich um eine Modelschnittstelle handelt und eine andere Model-Komponente hat sich bereits eingetragen, dann wird keine weitere Model-Komponente registriert. Der Name Server schickt in dem Fall -1 an den Server Stub zurück. |
|UC1| NameServer | boolean checkInterfaceInTable(int, String) | Ein CalleeStub möchte sich als RemoteObject eintragen und hat register() aufgerufen | Es wird true zurückgegeben, wenn bereits ein Eintrag existiert sonst false| Der Name Server prüft, ob zur übergebenen ID und Methodennamen bereits ein Eintrag in der Tabelle existiert| |
|UC2| ClientStub | void invoke(int, String, Object[]) | Eine Komponente ruft eine Remote-Komponente über eine Application Stub Schnittstelle auf | Der Aufruf wurde geprüft und die Methode marshal() wurde aufgerufen |  Prüft mithilfe von lookup() ob die übergebene Objekt-ID (erster Parameter) und die Methode (zweiter Parameter) registriert ist. Dann wird die Methode marshal() aufgerufen | Wenn die Objekt-ID nicht registriert ist, wird eine Exception geworfen|
|UC3| ClientStub | byte[] marshal(int,String, Object[]) | Funktionsaufruf über invoke wurde getätigt, zugehörige InetAddress und Portnummer wurde durch NameResolver ermittelt | Funktionsaufruf wurde marshaled und zurückgegeben | Es wird eine Nachricht im JSON-Format aus den übergebenen Parametern InterfaceID, Methodenname und Methodenparameter (Object[]) zusammengebaut. Das JSON-Objekt wird in ein byte-Array umgewandelt und zurückgegeben | |
|UC3.1| ClientStub | void send(InetAdress, int, byte[]) | Funktionsaufruf wurde marshaled, zugehörige InetAddress und Portnummer druch NameResolver ermittelt | Nachricht wurde verschickt | Die marshaled Nachricht wird über einen Socket an die passende InetAddress und Portnummer verschickt. | |
|UC4| ServerStub | JSON unmarshal(byte[]) | Nachricht wurde über receive empfangen | Nachrichteninhalt wurde extrahiert und kann für call genutzt werden | | (checksum stimmt nicht überein -> ignorieren) |
|UC4.1| ServerStub |  receive(DatagramPacket)) | Ein Socket im Server Stub hat eine Nachricht empfangen | Nachricht wurde aus dem Packet entpackt und dem Marshaler übergeben. | Die Nachricht wird aus dem übergebenen Datagram-Paket herausgeholt und dem Unmarshaler übergeben | |
|UC5| Client Stub | String[] cacheOrLookup(int,String) | Ein Application Stub hat invoke aufgerufen, um eine Remote-Methode aufzurufen. Der Client Stub muss im nächsten Schritt prüfen, ob die aufgerufene Methode im Cache des Client Stubs eingetragen ist. | Die Adresse und die Portnummer der angefragten Methode werden zurückgegeben.| Es wird in der Cache-Tabelle des Client-Stubs nachgeschaut, ob es einen Eintrag zur angegebenen ID(int) und dem angegebenen Methodenname(String) existiert. Wenn ja, dann wird ein Array mit der eingetragenen IP-Adresse (Index 0) und Portnummer (Index 1) zurückgegeben. Wenn nicht, dann wird der Name Server über eine Nachricht (Kommunikation mit TCP) angefragt. Das erhaltene das Ergebnis wird im selben Array-Format zurückgegeben.| Es gibt auch im Name Server keinen Eintrag mit der ID und dem Methodennamen. Der RPC abgebrochen|
|UC5| Name Server | void lookup(int, String) | Eine angefragte Methode ist nicht im Cache des Client Stubs gespeichert. Der Client Stub schickt eine Nachricht an den Name Server mit einer Lookup-Anfrage.| Die Adresse und die Portnummer der angefragten Methode werden über eine Nachricht zurückgeschickt.|Der Name Server prüft, ob es einen Eintrag in der Tabelle mit der übergebenen Interface-ID und Methodenname gibt. Wenn ja, dann werden die IP-Adresse und die Portnummer an den anfragenden Client Stub zurückgeschickt.| Es gibt keinen Eintrag mit der ID und dem Methodennamen. Der RPC abgebrochen |
|UC6| ServerStub | void callRemoteObjectInterface(JSON) | Nachricht wurde vom ServerStub empfangen und unmarshaled | Der Server Stub holt die Interface-ID, den Methodennamen und die Aufrufparameter aus dem JSON-Objekt heraus und ruft das korrekte Remote-Object-Interface auf.|Das RemoteObject-Interface wurde aufgerufen  | | 

## Nachrichtenformat
Um RPCs durchzuführen müssen Methodenaufrufe in Nachrichten umgewandelt werden. Dafür bringen wir die Methodenaufrufe erstmal in ein JSON-Format.
<br>
<br>
message = 
<br>
{
        <br>
        "interface" : Interface-ID als String,
        <br>
        "method": Methodenname als String,
        <br>
        "type1": primitiver Datentyp des ersten Aufrufparameters als String,
        <br>
        "value1" : der Wert des ersten Aufrufparameters,
        <br>
        ...,
        <br>
        "typeN": ,
        <br>
        "valueN":
        <br>
}

Die Nachricht im JSON-Format wird dann in ein byte-Array umgewandelt, und über das Netzwerk verschickt.

Für die Registrierung beim Name Server wird ein Callback benötigt. Somit liegen hier zusätzliche Anforderungen an die Kommunikation vor. Damit eine Antwort des angefragten Servers an den aufrufenden Client gesendet werden kann, wird die Interface-ID des Clients bereits bei der Anfrage mitgesendet. Die Returnwerte der aufgerufenen Methode werden im Server Stub als Strings in einem JSON-Objekt gespeichert, anschließend verpackt und als Byte-Array übers Netzwerk versendet. Die Nachrichten sehen so aus:

request =
{
        <br>
        "sender-interface" : Interface-ID des Senders als String,
        <br>
        "interface" : Interface-ID als String,
        <br>
        "method": Methodenname als String,
        <br>
        "type1": primitiver Datentyp des ersten Aufrufparameters als String,
        <br>
        "value1" : der Wert des ersten Aufrufparameters,
        <br>
        ...,
        <br>
        "typeN": ,
        <br>
        "valueN":
        <br>
}
<br>
<br>
answer =
{
        <br>
        "client-interface": Interface-ID des Clients als String,
        <br>
        "type": Rückgabetyp des Ergebnisses als String,
        <br>
        "value": Rückgabewert des Ergebnisses String
        <br>
}
<br>
<br>
Beispiel: message = 
<br>
{
        <br>
        "type": int,
        <br>
        "value": 17
        <br>
}
<br><br>

# Bausteinsicht 

## Whitebox Gesamtsystem

![Middleware_Ebene1](./images/Middleware_Ebene1.png)

Die Middleware ist in drei Komponenten aufgeteilt: Client Stub, Server Stub und Name Service. Des Weiteren werden die Middleware-Schnittstellen hinter einer Fassade gekapselt.
Die Funktionen der einzelnen Komponenten werden im weiteren Verlauf aufgeführt.


### Blackbox Client Stub

Der Client Stub nimmt Funktionsaufrufe vom Application Stub entgegen und wandelt diese in ein vordefiniertes Nachrichtenformat um.
Die Nachricht wird an eine Remote-Komponente über das Betriebssystem geschickt. Dafür fragt der Client-Stub erstmal nach der physikalischen Adresse der Remote-Komponente.

*Schnittstelle IRemoteInvocation*

Die Schnittstelle IRemoteInvocation kann aufgerufen werden, um eine Remote-Methode aufruzufen.

|Methode| Kurzbeschreibung |
| --- | --- |
|invoke(int interfaceID, String methodName, Object[] args) | Erzeugt eine Nachricht aus dem Funktionsaufruf und ruft die Betriebssystemschnittstelle zum Versenden der Nachricht auf. |

### Blackbox Server Stub

Der Server Stub empfängt Nachrichten über das Betriebssystem und wandelt sie in Funktionsaufrufe um. Die Funktion wird dann an das zuständige Remote Object im Application Stub weitergeleitet. Dafür müssen sich die Remote Objects erstmal über den Server Stub beim Name Service registrieren.

*Schnittstelle IRegister**

Die Schnittstelle IRegister kann aufgerufen werden, um sich als Remote-Object im Name Server einzutragen.

|Methode| Kurzbeschreibung |
|---|---|
| register(int interfaceID, String, InetAdress, int)| Schickt eine Nachricht an die NameService-Komponenten mit den Informationen, die für das Eintragen notwendig sind. Diese Informationen werden über die Aufrufparameter übergeben.|

### Blackbox Name Service

Der NameService verwaltet die IDs der Remote Objects, die sich registriert haben und die dazugehörigen IP-Adressen und Portnummern.
Beim Name Service kann man die physikalische Adresse eines Remote Objects anfragen oder sich als Remote Object registrieren. Dafür werden Nachrichten über TCP ausgetauscht.

**Diese Komponente bietet keine Schnittstellen an. Die Dienste können über Nachrichtenaustausch genutzt werden**

Nachrichten werden im folgenden JSON-Format ausgetauscht:

JsonObjectLookUp = {
"method": 0,
"args": [
"ifaceID": Int,
"methodName": Str,
("playerID": Int)
]
}

JsonObjectRegister = {
"method": 1,
"args": [
"ifaceID": Int,
"methodName": Str,
"IpAddr": "XXX.XXX.XXX.XXX",
"Port": "YYYYY"
]
}



## Ebene 2 


### Whitebox Client Stub
<br>
![MW_ClientStub](./images/MW_ClientStub.png)
<br>

|Methode| Kurzbeschreibung|
| --- | --- |
|invoke(int, String, Object[]) | Ruft die Methoden marshal(), dann cacheOrLookup(), dann send() auf|
|byte[] marshal(int,String,Object[]) | erzeugt ein JSON-Objekt aus den übergebenen Parametern und wandelt es in ein byte-Array um, das zurückgegeben wird. |
|cacheOrLookup(int,String,String[])| 
|send(InetAddress, int, byte[]|


### Whitebox Server Stub
<br>
![MW_ServerStub](./images/MW_ServerStub.png)
<br>

### Whitebox Name Service
<br>
![MW_NameServer](./images/MW_NameServer.png)
<br>




# Laufzeitsicht 

## Use Case 1 Register Method

![MW_UC1](./images/MW_UC1.png)

## Use Case 2/3 Invoke Method / Lookup Method

![MW_UC2_3](./images/MW_UC2_3.png)

## AD register

![MW_AD_register](./images/MW_AD_register.png)

## AD lookup

![MW_AD_lookup](./images/MW_AD_lookup.png)

# Verteilungssicht {#section-deployment-view}

Gesamtsystem
![Deployment_Tron](./images/Deployment_Tron.png)

Zoom in Middleware
![Deployment_ZoomMW](./images/Deployment_ZoomMW.png)

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

# Querschnittliche Konzepte {#section-concepts}

## *\<Konzept 1>* {#__emphasis_konzept_1_emphasis}

*\<Erklärung>*

## *\<Konzept 2>* {#__emphasis_konzept_2_emphasis}

*\<Erklärung>*

...

## *\<Konzept n>* {#__emphasis_konzept_n_emphasis}

*\<Erklärung>*

# Architekturentscheidungen {#section-design-decisions}

# Qualitätsanforderungen {#section-quality-scenarios}

::: formalpara-title
**Weiterführende Informationen**
:::

Siehe [Qualitätsanforderungen](https://docs.arc42.org/section-10/) in
der online-Dokumentation (auf Englisch!).

## Qualitätsbaum {#_qualit_tsbaum}

## Qualitätsszenarien {#_qualit_tsszenarien}

# Risiken und technische Schulden {#section-technical-risks}

# Glossar {#section-glossary}

+-----------------------+-----------------------------------------------+
| Begriff               | Definition                                    |
+=======================+===============================================+
| *\<Begriff-1>*        | *\<Definition-1>*                             |
+-----------------------+-----------------------------------------------+
| *\<Begriff-2*         | *\<Definition-2>*                             |
+-----------------------+-----------------------------------------------+

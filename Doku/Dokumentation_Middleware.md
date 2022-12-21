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

1. Umwandlung von Funktionsaufrufen in Nachrichten 
2. Umwandlung von Nachrichten in Funktionsaufrufen
3. Kommunikation mit dem Betriebssystem, um Nachrichten zu versenden und zu empfangen.
4. Vereinheitlicht die Kommunikationsdatentypen durch RPC
5. Application Stubs können sich als Remote Objects bei der Middleware anmelden
6. Die physikalische Adresse von Diensten kann abgefragt werden.



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
| UC2 Invoke Method |In der Anwendung wird eine Methode einer Remote-Komponente aufgerufen | **1.** Das System ruft den Application-Caller-Stub der aufrufenden Komponente auf <br> **2.** Der  Application Stub ruft die Middleware-Schnittstelle auf <br> **3.** Die Adresse der aufgerufenen Methode wird angefragt (siehe UC5) <br> **4.** Die Middleware wandelt den Methodenaufruf in eine Nachricht um (siehe UC3) <br> **5.** Die Middleware ruft das Betriebssystem auf| Die Nachricht wurde verschickt|**3.a.1** Die aufgerufene Komponente ist nicht bei der Middleware registriert <br> **3.a.2** Das System wirft eine Exception auf.| |
| UC3 Marshaling Method Call| UC2 bis Schritt 2 | **1.** Der Marshaler serialisiert den Methodenaufruf in ein Nachrichtenformat <br> **2.** Weiter mit UC Schritt 4| Der Methodenaufruf ist als Nachricht vorhanden | | |
| UC3.1 send |Eine Nachricht wurde in der Middleware erzeugt und soll versendet werden.|**1.** Der Marshaler ruft den Sender auf <br> **2.** Der Sender versendet die Nachricht über einen Socket |Die Nachricht wurde versendet|||
| UC4 Unmarshaling Message | Die Middleware hat eine Nachricht empfangen| **1.** Der Unmarshaler wandelt die Nachricht in einen Methodenaufruf um <br> **2.** Der Unmarshaler ruft den Application-Stub der Komponente auf, die den Methodenaufruf empfangen soll (siehe UC5)| Ein Methodenaufruf wurde erzeugt| | |
| UC4.1 receive | |||||
| UC5 lookup | UC2: Invoke Method bis Schritt 2 | **1.** Der Client Stub ruft den Nameserver auf <br> **2.** Der NameServer prüft,ob die aufgerufene Methode mit der zugehörigen Adresse in der Tabelle eingetragen ist **3.** Der NameServer liefert die zugehörige IP-Adresse zurück | Die  IP-Adresse zu der gesuchten Funktion wird zurückgegeben | Es gibt keinen Eintrag mit dieser ID. Dann wird  null zurückgegeben und der Aufruf verworfen. ||
| UC6 Call Method | UC 4 : Der Unmarshaler hat eine Nachricht in einen Methodenaufruf umgewandelt |**1.** Der Unmarshaler ruft die Call-Schnittstelle des Application-Callee-Stubs <br> **2.** Der Application-Callee-Stub ruft die dazugehörige Komponente lokal auf. | Die aufgerufene Methode wird ausgeführt.|


## Technischer Kontext

![Technischer_Trontext](./images/middleware_technischer_trontext.png)



# Lösungsstrategie 

## Methodenliste

| Usecase | Akteur |Funktionssignatur| Vorbedingung | Nachbedingung | Ablaufsemantik | Fehlersemantik |
|---|---|---|---|---|---|---|
|UC1| ServerStub | void register(int, InetAddress) | Ein CalleeStub aus dem ApplicationStub möchte sich als RemoteObject registrieren | Das RemoteObject wurde im NameServer gespeichert |  NameServer wird aufgerufen und id mit InetAddress eingetragen | Eintrag mit der ID existiert bereits. Dann wird überschrieben(?) |
|UC2| ClientStub | void invoke(int, String, Object[]) | Eine Komponente ruft eine Remote-Komponente über eine Application Stub Schnittstelle auf | Der Aufruf wurde geprüft und die Methode marshal() wurde aufgerufen |  Prüft mithilfe von lookup() ob die übergebene Objekt-ID (erster Parameter) registriert ist. Dann wird die Methode marshal() aufgerufen | Wenn die Objekt-ID nicht registriert ist, wird eine Exception geworfen|
|UC3| ClientStub | Message marshal(String, Object[]) | Funktionsaufruf über invoke wurde getätigt, zugehörige InetAddress wurde durch NameResolver ermittelt | Funktionsaufruf wurde marshaled und zurückgegeben | | |
|UC3.1| ClientStub | void send(Message, InetAddress) | Funktionsaufruf wurde marshaled, zugehörige InetAddress druch NameResolver ermittelt | Nachricht wurde verschickt | Die marshaled Nachricht wird über einen Socket an die passende InetAddress verschickt. | |
|UC4| ServerStub | void unmarshal(Message) | Nachricht wurde über receive empfangen | Nachrichteninhalt wurde extrahiert und kann für call genutzt werden | | (checksum stimmt nicht überein -> ignorieren) |
|UC4.1| ServerStub | receive() | Ein Socket im Server Stub befindet sich im Lauschzustand | Nachricht wurde empfangen | Nachricht am Socket wird empfangen und gespeichert. | |
|UC5| ClientStub | INetAddress lookup(int) | invoke wurde aufgerufen |  | Aufrufparameter: ID, liefert die Inet-Adresse und die Portnummer zum Eintrag mit ID im NameServer | Es gibt keinen Eintrag mit der ID. Es wird null zurückgegeben und der RPC abgebrochen |
|UC6| ServerStub | void call(InetAddress, Object[]) | Nachricht wurde vom ServerStub empfangen und unmarshaled | RemoteObject mit der zugehörigen InetAddress wurde informiert | | |

## Nachrichtenformat

# Bausteinsicht 

## Whitebox Gesamtsystem

![Middleware_Ebene1](./images/Middleware_Ebene1.png)


Wichtige Schnittstellen

:   *\<Beschreibung wichtiger Schnittstellen>*

### \<Name Blackbox 1> {#__name_blackbox_1}

*\<Zweck/Verantwortung>*

*\<Schnittstelle(n)>*

*\<(Optional) Qualitäts-/Leistungsmerkmale>*

*\<(Optional) Ablageort/Datei(en)>*

*\<(Optional) Erfüllte Anforderungen>*

*\<(optional) Offene Punkte/Probleme/Risiken>*

### \<Name Blackbox 2> {#__name_blackbox_2}

*\<Blackbox-Template>*

### \<Name Blackbox n> {#__name_blackbox_n}

*\<Blackbox-Template>*

### \<Name Schnittstelle 1> {#__name_schnittstelle_1}

...

### \<Name Schnittstelle m> {#__name_schnittstelle_m}

## Ebene 2 {#_ebene_2}

![Middleware_Ebene2](./images/Middleware_Ebene2.png)

### Whitebox *\<Baustein 1>* {#_whitebox_emphasis_baustein_1_emphasis}

*\<Whitebox-Template>*

### Whitebox *\<Baustein 2>* {#_whitebox_emphasis_baustein_2_emphasis}

*\<Whitebox-Template>*

...

### Whitebox *\<Baustein m>* {#_whitebox_emphasis_baustein_m_emphasis}

*\<Whitebox-Template>*

## Ebene 3 {#_ebene_3}

### Whitebox \<\_Baustein x.1\_\> {#_whitebox_baustein_x_1}

*\<Whitebox-Template>*

### Whitebox \<\_Baustein x.2\_\> {#_whitebox_baustein_x_2}

*\<Whitebox-Template>*

### Whitebox \<\_Baustein y.1\_\> {#_whitebox_baustein_y_1}

*\<Whitebox-Template>*

# Laufzeitsicht {#section-runtime-view}

## *\<Bezeichnung Laufzeitszenario 1>* {#__emphasis_bezeichnung_laufzeitszenario_1_emphasis}

-   \<hier Laufzeitdiagramm oder Ablaufbeschreibung einfügen>

-   \<hier Besonderheiten bei dem Zusammenspiel der Bausteine in diesem
    Szenario erläutern>

## *\<Bezeichnung Laufzeitszenario 2>* {#__emphasis_bezeichnung_laufzeitszenario_2_emphasis}

...

## *\<Bezeichnung Laufzeitszenario n>* {#__emphasis_bezeichnung_laufzeitszenario_n_emphasis}

...

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

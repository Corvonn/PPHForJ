# PPHForJ 
## Vorwort 
PPHForJ ist eine Java SDK für die API von [Prepaid-Hoster.de](https://prepaid-hoster.de). Sie stellt stellt die
grundlegenden Funktionen der API für Java zur verfügung und ermöglicht somit einfach Informationen abzufragen.  
POSTs werden im Moment noch nicht unterstützt, können allerdings noch in die SDK eingebaut werden. 

Diese API unterstützt im Moment nur wenige der möglichen Abfragen. Jeder wird eingeladen, die SDK nach dem bisher 
verwendeten Schema zu erweitern, damit die API im besten Fall am Ende alle Möglichkeiten der API von PPH unterstützt.

## Verwendung
### Maven
Clone dir dieses Repo und installiere dir die SDK mithilfe von Maven in dein lokales Maven-Repository. Anschließend
kannst du einfach folgende Zeilen in deine pom.xml einfügen: 
```xml
<dependency>
    <groupId>de.corvonn</groupId>
    <artifactId>PPHForJ</artifactId>
    <version>VERSION</version>
    <scope>compile</scope>
</dependency>
```

### Java
Nun kannst du mit deinem Projekt starten. Um Abfragen tätigen zu können, musst du dir zuerst einen Token bei 
PPH auf der entsprechenden [Seite](https://www.vionity.de/clientarea/api-tokens) erstellen lassen. Dann kannst du 
ein PPHForJ Objekt erstellen: 
```java
PPHForJ pphForJ = new PPHForJ("DEIN TOKEN");
```

Mithilfe von diesem Objekt und deinem Token kannst du nun abfragen tätigen: 
```java
//Spenden abfragen
pphForJ.getInvoices(InvoiceType.DONATION).forEach(invoice -> {
    if(!invoice.getInvoiceItems().isEmpty()) {
        InvoiceDonationItem donation = (InvoiceDonationItem) invoice.getInvoiceItems().get(0);
        OffsetDateTime time = invoice.getDatePaidCET();
        System.out.println(donation.getDonatorName() + " hat am " + time.getDayOfMonth() + "." + time.getMonthValue() + "." + time.getYear() + " " + donation.getAmount() + "€ gespendet!");
    }
});

//Aktive Hostings (KVM/VPS/Webserver/etc) abfragen
pphForJ.getHostings(false).forEach(hosting -> {
    System.out.println(hosting.toString());
});
```

## Hinweise
* Sowohl SDK, als auch die API von PPH befinden sich im Moment noch in der Entwicklung -> es kann zu Änderungen oder
Bugs kommen
* Die API von PPH benötigt zum Teil mehrere Sekunden, um auf Anfragen zu antworten (auch abhängig von der Anzahl der 
angefragten Objekte). **Somit kann das Ausführen einer Anfrage der SDK ebenfalls mehrere Sekunden dauern!**
* Die SDK wurde mit JavaDocs geschrieben. In IntelliJ können diese jederzeit über Tools -> Generate JavaDocs generiert
und eingesehen werden.
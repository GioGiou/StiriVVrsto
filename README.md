# Pametni agent za igro 4 v vrsto 

Implementacija pametnega agenta za 4 v vrsto v Javi. Implementirani so bili Naključni agent, Min Max agent in Min Max agent z Alfa Beta rezanjem.

## Poganjanje programa

Program je bil izdelan in testiran z uporabo različice Jave 17.0.7. Prevedba javanske kode je bila storjena z ukazom:

```bash
javac *.java
```

Za pognati prevedeno kodo je bil uporabljen ukaz:

```bash
java Connect4
```

Če program se ne požene in vrne napako
```bash
Error: Could not find or load main class Connect4
Caused by: java.lang.ClassNotFoundException: Connect4
```
program poženite z ukazom:
```bash
 java -cp . Connect4
```
## Uporaba programa

Za igranje igre uporabimo gumbe od 1 do 7 nad stolpci, s katerimi postavimo plošček v željeni stolpec.

 V meniju File lahko zapremo igro (gumb Exit) in ponovno začnemo igro (gumb New). 
 
 V meniju Options izberemo igralca, ki bo začel igro, bodisi rdečega (gumb Red starts) bodisi rumenega igralca (gumb Yellow starts). 
 
 V menujih Player - Red in Player - Yellow izberemo tip igralca, ki bo igral določeno barvo. Možne izbire so:

 - Random Player, igralec bo računalnik, ki naključno izbere naslednjo potezo,
 - Min-Max Player, igralec bo računalnik, ki izbere naslednjo potezo s pomočjo Min-Max algoritma,
 - Alpha-Beta Player, igralec bo računalnik, ki izbere naslednjo potezo s pomočjo Min-Max algoritma, pri čemer je uporabljeno tudi alfa beta rečenje,
 - Human, igralec je človek.

 V meniju Test lahko test, ki primerja algoritme robotov.
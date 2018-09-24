"Copyright [2017] Gavan Adrian-George, 324CA"
Nume, prenume: Gavan, Adrian-George
Grupa, seria: 324CA

Tema 1 - League of OOP

Bonus: Bonusul a fost implementat cu ajutorul metodei calculareAdversar din
clasele Hero, Knight, Wizard, Pyromancer si Rogue. Functia apeleaza metoda
adversarului de calcul a abilitatilor in functie de race modifiers si paseaza
ca parametru "this", referinta la instanta curenta a eroului, astfel incat
se evita folosirea instanceof.

Prezentarea implementarii
=========================

A. Clase utilizate (toate clasele sunt detaliate si in codurile sursa):

1. Pachetul main
a) Clasa Main:
- Clasa Main reprezinta entry-point-ul programului.
- Clasa Main prezinta metoda "main" ce primeste ca parametrii fisierele de input
si output ale programului.
- Trimite catre clasa Parsare variabila Filesystem si va primi datele
create in urma parsarii fisierului de input si initializarilor corespunzatoare.
Pentru fiecare runda, se realizeaza mutarea eroilor cu ajutorul clasei Miscare,
iar atacurile dintre eroi(daca este cazul) se realizeaza cu ajutorul clasei
Atac. In final, se scriu informatiile despre eroi, conform cerintei, in
fisierul de iesire.
 
2. Pachetul parsare 
a) Parsare:
- Clasa Parsare este responsabila de parsarea fisierului de intrare si instantierea
obiectelor corespunzatoare input-ului. De asemenea, seteaza harta, vectorul ce
tine corespondenta dintre indicele eroului si eroul respectiv si vectorul ce
retine linia si coloana celulei in care se afla fiecare erou.
- Metoda citireFis initializeaza mapa cu tipul corespunzator de teren, citeste
numarul de personaje, initializeaza eroii conform input-ului si citeste si
numarul de runde.

b) Miscare:
- Clasa Miscare este responsabila cu miscarea eroilor pe mapa.
- Metoda executaMiscare verifica pentru fiecare erou daca are aplicata o abilitate
ce impiedica mutarea acestuia. Daca eroul se poate misca, se interpreteaza noua
pozitie in functie de comanda primita, pune eroul in noua locatie si il elimina
din veche locatie. De asemenea, se actualizeaza si linia si coloana celulei in
care se afla eroul.

c) Ataca:
- Aceasta clasa realizeaza atacul dintre 2 jucatori. De asemenea, calculeaza
si damage-ul overtime primit de fiecare jucator (indiferet daca sunt 2 eroi
in aceeasi locatie sau e un singur jucator in locatie).
- Metoda executaAtac verifica daca este un singur erou in celula sau daca
sunt doi. In primul caz, se calculeaza DoT al eroului si se verifica daca
acesta a murit. Daca avem 2 eroi in aceeasi locatie, se calculeaza DoT pentru
fiecare dintre acestia si se verifica daca vreunul din ei a murit (daca unul
din ei a murit, lupta nu va mai avea loc). Se calculeaza damage-ul dat de
fiecare erou si se actualizeaza vietile celor 2. Daca unul din ei a murit,
invingatorului i se actualizeaza XP-ul, iar eroul mort este scos din mapa.

d) CelulaMapa:
- Clasa CelulaMapa reprezinta un "patratel" din harta 2D a jocului. Fiecare
celula va continte 2 indici (pentru 2 eroi), numarul de eroi din locatie si
tipul de teren.
- Metoda setIndice adauga un nou indice pentru erou pe prima pozitie daca
este libera, iar daca este ocupata il adauga pe a doua pozitie.
- Metoda resetIndice elimina un erou din celula astefel incat prima pozitie
sa fie mereu ocupata (daca avem un singur erou in celula, acesta sa fie
pe prima pozitie).

e) CelulaLinieColoana:
- In aceasta clasa se retin linia si coloana celulei in care se afla
eroul corespunzator.

f) TipEroi:
- Clasa enum TipComanda este folosita pentru tipul de erou primit.

g) TipTeren:
- Clasa enum TipComanda este folosita pentru tipul de teren primit.

3. Pachetul eroi:
a) Clasa Hero:
- Aceasta clasa abstracta contine datele si metodele generale pentru fiecare
erou.
- Metoda setXP seteaza xp-ul curent, nivelul eroului si pragul de XP pentru
noul nivel.
- Metoda setHp va actualiza noul hp cand eroul creste un nivel.
- Metoda dmgHeroOvertime calculeaza damage-ul overtime si decrementeaza contorii
pentru abilitatile corespunzatoare.
- Metoda dmgHeroTeren va calcula damage-ul eroului in functie de abilitatile sale.
De asemenea, va apela si metoda executeTeren pentru a calcula abilitatile
eroului in functie de tipul terenului, daca este cazul.
- Metoda calculareAdversar va apela metoda dmgFunctieAdversar a celuilalt
erou, dar va trimite ca parametru o instanta la referinta curenta, astfel
incat metoda overload dmgFunctieAdversar a celuilalt erou se va apela in
functie de tipul instantei primite(fara a fi nevoie de instanceof).
- Metoda dmgFunctieAdversar calculeaza damage-ul eroului curent in functie de rasa
adversarului.
- Metoda executeTeren calculeaza damage-ul eroului curent in functie de teren,
daca este cazul.

b) Clasele Pyromancer, Knight, Rogue, Wizard:
- Toate clasele mentionate mai sus extind clasa Hero si implementeaza cele 4
tipuri de eroi din joc. Fiecare clasa are variabilele sale specifice si
implementeaza metodele abstracte ale clasei Hero. Fiecare metoda din fiecare
clasa a fost detaliata in codul sursa.

c) Clasa Cnst:
- Aceasta clasa contine constantele din program.

B. Algoritmul utilizat:

- In Main se primesc fisierele de intrare si iesire.
- Cu ajutorul clasei Parsare se initializeaza mapa, eroii, se pun eroii pe mapa,
se retine numarul de jucatori si numarul de runde(cu alte cuvinte, se parseaza
informatiile din fisierul de intrare).
- Pentru fiecare runda in parte, se apeleaza executaMiscare din clasa Miscare
pentru a realiza mutarea eroilor pe harta. Se verifica daca eroii sufera de un
efect overtime si nu au voie sa se miste. Daca se pot misca, se interpreteaza
comenzile din fisierul de input.
- Dupa ce s-au mutat toti eroii, se apeleaza metoda executaAtac. Se trece
prin fiecare locatie din mapa. Daca este un singur jucator in locatie,
calculam DoT pentru el si ii modificam viata (sau il setam ca fiind mort
daca este cazul). Daca sunt 2 jucatori intr-o locatie, se calculeaza DoT
pentru fiecare dintre ei. Daca unul moare, lupta nu mai are loc. Daca
niciunul nu moare, se calculeaza damage-ul dat de fiecare celuilalt si 
li se actualizeaza vietile. Avem 3 cazuri: au murit amandoi (se elimina de pe
harta si ii actualizam ca fiind morti), moare doar unul (cel mort se scoate de
pe harta, iar celui viu i se actualizeaza XP-ul) sau nu moare niciunul (nu se
intampla nimic, se trece mai departe).
- Dupa executarea rundelor, se trece prin vectorul care tine corespondenta
dintre indice si erou (prin indice intelegem ordinea din fisierul de intrare)
si se scriu in output caracteristicile fiecaruia, conform cerintei.

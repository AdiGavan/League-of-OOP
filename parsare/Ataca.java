// "Copyright [2017] Gavan Adrian-George, 324CA"
package parsare;

import eroi.Hero;

/**
 * Aceasta clasa realizeaza atacul dintre 2 jucatori. De asemenea, calculeaza
 * si damage-ul over time primit de fiecare jucator (indiferet daca sunt 2
 * eroi in aceeasi locatie sau e un singur jucator in locatie).
 *
 * @author Gavan Adrian-George, 324CA
 */


public final class Ataca {
  private CelulaMapa[][] mapa;
  private Hero[] corespIndJuc;
  private int m;
  private int n;

  public Ataca(final CelulaMapa[][] mapa, final Hero[] corespIndJuc,
               final int n, final int m) {
    this.mapa = mapa;
    this.corespIndJuc = corespIndJuc;
    this.m = m;
    this.n = n;
  }

  /**
   * Metoda executaAtac realizeaza atacul dintre 2 jucatori si calculeaza
   * si DoT al fiecarui jucator.
   * Algoritmul metodei:
   * 1. Se iau indicii eroilor din celula.
   * 2. Daca este doar un jucator in celula, se calculeaza damage-ul overtime
   * pentru acesta si i se actualizeaza HP-ul (gethPCurent si sethPCurent).
   * Daca eroul a murit din cauza DoT, atunci i se seteaza viata la 0 si se
   * sterge de pe harta.
   * 3. Daca avem 2 jucatori in celula, se calculeaza DoT al fiecaruia si li
   * se actualizeaza viata. Daca vreunul din jucatori moare din cauza Dot,
   * i se actualizeaza viata la 0, se elimina din mapa si lupta nu va mai
   * avea loc.
   * 4. Daca lupta are loc, se calculeaza damage-ul celor 2 adversari si li se
   * actualizeaza viata.
   * 5. Se seteaza contorul lui Wizard (folosit pentru a se sti daca wizard
   * este primul care ataca sau al doilea). Se presupune ca Wizard ataca mereu
   * primul, iar contorul se face false cand este apelat alt erou. Daca alt erou
   * este apelat primul, contorul Wizard se face false, deci Wizard este al
   * doilea.
   * 6. Daca ambii jucatori mor, niciunul nu primeste experienta, li se
   * initializeaza viata la 0 si ambii sunt scosi de pe harta.
   * 7. Daca unul din eroi moare, celalalt erou isi seteaza noul XP.
   * Eroul mort este scos din joc.
   */

  public void executaAtac() {
    for (int i = 0; i < m; i++) {
      for (int k = 0; k < n; k++) {
        int contorDead = 0;
        int damageTotal1;
        int damageTotal2;
        int dmgOvertime1 = 0;
        int dmgOvertime2 = 0;
        int indEr1 = 0;
        int indEr2 = 0;

        // Se calculeaza damage-ul overtime al fiecarui erou
        indEr1 = mapa[i][k].getIndiceErou1();
        indEr2 = mapa[i][k].getIndiceErou2();

        // Cazul cand e doar un erou in celula si se calculeaza dmgovertime pentru acesta
        if (mapa[i][k].getNrJucatori() == 1) {
          corespIndJuc[indEr1].dmgOvertime();
          dmgOvertime1 = corespIndJuc[indEr1].getDmgOvertime();
          corespIndJuc[indEr1].sethPCurent(corespIndJuc[indEr1]
                                           .gethPCurent() - dmgOvertime1);

          if (0 >= corespIndJuc[indEr1].gethPCurent()) {
            corespIndJuc[indEr1].setAlive(0);
            mapa[i][k].resetIndice(indEr1);
          }

          continue;
        }

        // Cazul cand sunt 2 luptatori in celula
        if (mapa[i][k].getNrJucatori() == 2) {
          corespIndJuc[indEr1].dmgOvertime();
          corespIndJuc[indEr2].dmgOvertime();
          dmgOvertime1 = corespIndJuc[indEr1].getDmgOvertime();
          dmgOvertime2 = corespIndJuc[indEr2].getDmgOvertime();
          corespIndJuc[indEr1].sethPCurent(corespIndJuc[indEr1]
                              .gethPCurent() - dmgOvertime1);
          corespIndJuc[indEr2].sethPCurent(corespIndJuc[indEr2]
                              .gethPCurent() - dmgOvertime2);

          // Se verifica mai intai indEr2 pentru ca reset de indice1 muta
          // indicele 2 in locul indicelui 1 (in celula mapei).
          if (0 >= corespIndJuc[indEr2].gethPCurent()) {
            corespIndJuc[indEr2].setAlive(0);
            mapa[i][k].resetIndice(indEr2);
            contorDead = 1;
          }
          if (0 >= corespIndJuc[indEr1].gethPCurent()) {
            corespIndJuc[indEr1].setAlive(0);
            mapa[i][k].resetIndice(indEr1);
            contorDead = 1;
          }
          if (contorDead == 1) {
            continue;
          }

          // Functia calculateAdversar calculeaza damage-ul in
          // functie de tipul eroului cu care lupta.
          // Functia apeleaza metoda de calcul in functie de
          // adversar cu parametrul(this), astfel incat
          // se evita folosirea instanceof.
          // Mai multe detalii in clasele eroi.
          corespIndJuc[indEr1].dmgHeroTeren(mapa[i][k].getTeren());
          corespIndJuc[indEr2].calculareAdversar(corespIndJuc[indEr1]);
          damageTotal1 = (int) (corespIndJuc[indEr1].getDamageTotal());

          corespIndJuc[indEr2].dmgHeroTeren(mapa[i][k].getTeren());
          corespIndJuc[indEr1].calculareAdversar(corespIndJuc[indEr2]);
          damageTotal2 = (int) (corespIndJuc[indEr2].getDamageTotal());

          corespIndJuc[indEr1].sethPCurent(corespIndJuc[indEr1]
                                           .gethPCurent() - damageTotal2);
          corespIndJuc[indEr2].sethPCurent(corespIndJuc[indEr2]
                                           .gethPCurent() - damageTotal1);
          corespIndJuc[indEr2].setWizardContor(true);

          // Se verifica ce jucator si daca vreun jucator este mort si se
          // fac initializarile corespunzatoare
          if ((0 >= corespIndJuc[indEr1].gethPCurent())
              && (0 >= corespIndJuc[indEr2].gethPCurent())) {
            corespIndJuc[mapa[i][k].getIndiceErou2()].setAlive(0);
            mapa[i][k].resetIndice(mapa[i][k].getIndiceErou2());
            corespIndJuc[mapa[i][k].getIndiceErou1()].setAlive(0);
            mapa[i][k].resetIndice(mapa[i][k].getIndiceErou1());
            continue;

          } else if (0 >= corespIndJuc[mapa[i][k].getIndiceErou1()]
                     .gethPCurent()) {
            corespIndJuc[mapa[i][k].getIndiceErou1()].setAlive(0);
            corespIndJuc[mapa[i][k].getIndiceErou2()]
                 .setxPCurent(corespIndJuc[mapa[i][k]
                      .getIndiceErou1()].getLevel());
            mapa[i][k].resetIndice(mapa[i][k].getIndiceErou1());

          }  else if (0 >= corespIndJuc[mapa[i][k].getIndiceErou2()]
                      .gethPCurent()) {
            corespIndJuc[mapa[i][k].getIndiceErou2()].setAlive(0);
            corespIndJuc[mapa[i][k].getIndiceErou1()]
                .setxPCurent(corespIndJuc[mapa[i][k]
                      .getIndiceErou2()].getLevel());
            mapa[i][k].resetIndice(mapa[i][k].getIndiceErou2());

          } else {
            continue;
          }
        }
      }
    }
  }

  public CelulaMapa[][] getMapa() {
    return mapa;
  }

  public Hero[] getCorespIndJuc() {
    return corespIndJuc;
  }
}

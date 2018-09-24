// "Copyright [2017] Gavan Adrian-George, 324CA"
package parsare;

import eroi.Hero;

/**
 * Clasa enum TipComanda este folosita pentru tipul de comenzi primite.
 *
 * @author Gavan Adrian-George, 324CA
 */

enum TipComanda {
  UP("U"),
  DOWN("D"),
  RIGHT("R"),
  LEFT("L");

  private final String tip;

  TipComanda(final String tipNou) {
    this.tip = tipNou;
  }

  @Override
  public String toString() {
    return tip;
  }
}


/**
 * Aceasta clasa realizeaza mutarea jucatorilor pe mapa.
 *
 * Obiectele importante din aceasta clasa (mapa, corespIndJuc, pozitieJucator)
 * au fost detaliate in clasa Main.
 *
 * Metoda ce realizeaza mutarile este metoda executaMiscare.
 *
 * @author Gavan Adrian-George, 324CA
 */

public class Miscare {
  private String linieMapa;
  private CelulaLinieColoana[] pozitieJucator;
  private CelulaMapa[][] mapa;
  private int nrPersonaje;
  private Hero[] corespIndJuc;

  public Miscare(final String linieMapa,
                 final CelulaLinieColoana[] pozitieJucator, final CelulaMapa[][]
                 mapa, final int nrPersonaje, final Hero[] corespIndJuc) {
    this.linieMapa = linieMapa;
    this.pozitieJucator = pozitieJucator;
    this.mapa = mapa;
    this.nrPersonaje = nrPersonaje;
    this.corespIndJuc = corespIndJuc;
  }

  /**
   * Metoda executaMiscare realizeaza miscarea eroilor pe mapa.
   * Aceasta metoda realizeaza urmatorii pasi:
   * 1. Se verifica pentru fiecare erou daca este sub efectul slam, paralysis
   * sau daca eroul este mort. Daca unul din cazuri este adevarat, mutarea nu
   * se efectueaza.
   * 2. Se retine vechea linie si coloana a celulei unde este eroul, se
   * interpreteaza mutarea si se actualizeaza linia si coloana veche.
   * 3. Se elimina eroul din vechea locatie si se adauga in noua locatie.
   * 4. Se actualizeaza linia si coloana celulei in care se afla respectivul
   * erou.
   */

  public void executaMiscare() {
    int lVeche = 0;
    int cVeche = 0;
    int lNoua = 0;
    int cNoua = 0;

    for (int i = 0; i < nrPersonaje; i++) {
      if (corespIndJuc[i].getSlamContor() != 0) {
        // Se decrementeaza SlamContor;
        corespIndJuc[i].decSlamContor();
        continue;
      }
      if (corespIndJuc[i].getParalysisContor() != 0) {
        // ParalysisContor se decrementeaza in functiile de calculare DoT.
        continue;
      }
      if (corespIndJuc[i].getAlive() == 0) {
          continue;
      }

      lVeche = pozitieJucator[i].getL();
      cVeche = pozitieJucator[i].getC();
      lNoua = lVeche;
      cNoua = cVeche;

      if (Character.toString(linieMapa.charAt(i)).equals("_")) {
        continue;
      } else if (Character.toString(linieMapa.charAt(i)).equals(
                  TipComanda.UP.toString())) {
        lNoua = lVeche - 1;
      } else if (Character.toString(linieMapa.charAt(i)).equals(
                  TipComanda.DOWN.toString())) {
        lNoua = lVeche + 1;
      } else if (Character.toString(linieMapa.charAt(i)).equals(
                  TipComanda.LEFT.toString())) {
        cNoua = cVeche - 1;
      } else if (Character.toString(linieMapa.charAt(i)).equals(
                  TipComanda.RIGHT.toString())) {
        cNoua = cVeche + 1;
      }

      mapa[lNoua][cNoua].setIndice(i);
      mapa[lVeche][cVeche].resetIndice(i);
      pozitieJucator[i].setL(lNoua);
      pozitieJucator[i].setC(cNoua);
    }
  }

  public final String getLinieMapa() {
    return linieMapa;
  }

  public final CelulaLinieColoana[] getPozitieJucator() {
    return pozitieJucator;
  }

  public final CelulaMapa[][] getMapa() {
    return mapa;
  }

  public final int getNrPersonaje() {
    return nrPersonaje;
  }

  public final Hero[] getCorespIndJuc() {
    return corespIndJuc;
  }
}

// "Copyright [2017] Gavan Adrian-George, 324CA"
package parsare;

/**
 * Clasa CelulaMapa reprezinta un "patratel" din harta 2D a jocului.
 * Fiecare instanta a acestei clase retine tipul terenului, indicii eroilor
 * (daca sunt eroi in aceasta locatie, altfel se retine -1) si numarul de
 * eroi din aceasta locatie.
 *
 * Aceasta clasa are 2 metode importante:
 * 1. setIndice - adauga indice jucator in celula;
 * 2. resetIndice - sterge indice jucator din celula;
 *
 * @author Gavan Adrian-George, 324CA
 */

public class CelulaMapa {
  private String teren;
  private int indiceErou1;
  private int indiceErou2;
  private int nrJucatori;

  public CelulaMapa(final String teren) {
    this.teren = teren;
    indiceErou1 = -1;
    indiceErou2 = -1;
    nrJucatori = 0;
  }

  public CelulaMapa(final String teren, final int indiceErou1,
                    final int indiceErou2, final int nrJucatori) {
    this.teren = teren;
    this.indiceErou1 = indiceErou1;
    this.indiceErou2 = indiceErou2;
    this.nrJucatori = nrJucatori;
  }

  /**
   * Metoda setIndice adauga un nou erou in locatie.
   * Daca in aceasta locatie nu este niciun jucator, atunci se pune indicele
   * eroului pe prima pozitie.
   * Daca exista deja un erou in locatie, indicele se pune pe pozitia a doua.
   * Se incrementeaza numarul de jucatori din locatie.
   *
   * @param indice
   */

  public void setIndice(final int indice) {
    if (nrJucatori == 0) {
      indiceErou1 = indice;
    } else {
      indiceErou2 = indice;
    }
    nrJucatori++;
  }

  /**
   * Metoda resetIndice sterge un erou din locatie.
   * Daca din locatie trebuie sters primul erou, atunci se suprascrie indicele
   * de la prima pozitie cu indicele eroului 2, iar a doua pozitie primeste
   * valoarea -1 (insemnand ca nu este niciun erou in acea pozitie).
   * Daca trebuie scos eroul de pe pozitia 2, doar se modifica valoarea de
   * la indiceErou2 in -1.
   * Se decrementeaza numarul de jucatori din celula.
   *
   * @param indice
   */

  public void resetIndice(final int indice) {
    if (indiceErou1 == indice) {
      indiceErou1 = indiceErou2;
      indiceErou2 = -1;
    } else if (indiceErou2 == indice) {
      indiceErou2 = -1;
    }
    nrJucatori--;
  }

  public final int getNrJucatori() {
    return nrJucatori;
  }

  public final int getIndiceErou1() {
    return indiceErou1;
  }

  public final int getIndiceErou2() {
    return indiceErou2;
  }

  public final String getTeren() {
    return teren;
  }
}

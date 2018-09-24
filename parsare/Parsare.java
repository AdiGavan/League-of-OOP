// "Copyright [2017] Gavan Adrian-George, 324CA"
package parsare;

import java.io.FileNotFoundException;
import java.io.IOException;

import eroi.Hero;
import eroi.Knight;
import eroi.Pyromancer;
import eroi.Rogue;
import eroi.Wizard;
import fileio.FileSystem;

/**
 * Aceasta clasa este responsabila de parsarea fisierului de intrare si
 * instantierea obiectelor corespunzatoare input-ului.
 * De asemenea, seteaza harta, vectorul ce tine corespondenta dintre indicele
 * eroului si eroul respectiv si vectorul ce retine linia si coloana celulei
 * in care se afla fiecare erou.
 *
 * Obiectele importante din aceasta clasa (mapa, corespIndJuc, pozitieJucator)
 * au fost detaliate in clasa Main.
 *
 * In aceasta clasa nu se face si scrierea in fisier, ci doar citirea, de asta
 * am considerat numirea instantei FileSystem ca fileReader.
 *
 * @author Gavan Adrian-George, 324CA
 *
 */

public class Parsare {
  private int m;
  private int n;
  private int nrPersonaje;
  private int numarRunde;
  private CelulaMapa[][] mapa;
  private Hero[] corespIndJuc;
  private CelulaLinieColoana[] pozitieJucator;
  private FileSystem fileReader;

  public Parsare(final FileSystem fileReader) {
    this.fileReader = fileReader;
  }

  /**
   * Aceasta metoda realizeaza urmatorii pasi:
   * 1. Se retin numarul de linii si coloane al mapei.
   * 2. Se citeste pe rand cate o linie ce contine tipul fiecarei locatii de pe
   * acea linie.
   * 3. Se parseaza fiecare linie si se initializeaza celulele din mapa conform
   * tipului corespunzator de teren.
   * 4. Se retine numarul de eroi.
   * 5. Fiecare erou se initializeaza cu tipul corespunzator si se retine
   * pozitia acestuia pe mapa.
   * 6. Se retine numarul de runde.
   *
   * @throws FileNotFoundException
   * @throws IOException
   */

  public void citireFis() throws FileNotFoundException, IOException {
    String linieMapa;
    Hero aux;
    CelulaLinieColoana aux4;
    int aux2 = 0;
    int aux3 = 0;

    m = fileReader.nextInt();
    n = fileReader.nextInt();
    mapa = new CelulaMapa[m][n];

    for (int i = 0; i < m; i++) {
      linieMapa = fileReader.nextWord();

      for (int j = 0; j < n; j++) {
        if (Character.toString(linieMapa.charAt(j)).equals(
                               TipTeren.L.toString())) {
          mapa[i][j] = new CelulaMapa(TipTeren.LAND.toString());
          continue;
        }
        if (Character.toString(linieMapa.charAt(j)).equals(
                               TipTeren.V.toString())) {
          mapa[i][j] = new CelulaMapa(TipTeren.VOLCANIC.toString());
          continue;
        }
        if (Character.toString(linieMapa.charAt(j)).equals(
                               TipTeren.D.toString())) {
          mapa[i][j] = new CelulaMapa(TipTeren.DESERT.toString());
          continue;
        }
        if (Character.toString(linieMapa.charAt(j)).equals(
                               TipTeren.W.toString())) {
          mapa[i][j] = new CelulaMapa(TipTeren.WOODS.toString());
        }
      }

    }

    nrPersonaje = fileReader.nextInt();
    corespIndJuc = new Hero[nrPersonaje];
    pozitieJucator = new CelulaLinieColoana[nrPersonaje];

    for (int i = 0; i < nrPersonaje; i++) {
      linieMapa = fileReader.nextWord();

      if (linieMapa.equals(TipEroi.PYROMANCER.toString())) {
        aux = new Pyromancer(i);
        corespIndJuc[i] = aux;
      }
      if (linieMapa.equals(TipEroi.KNIGHT.toString())) {
        aux = new Knight(i);
        corespIndJuc[i] = aux;
      }
      if (linieMapa.equals(TipEroi.WIZARD.toString())) {
        aux = new Wizard(i);
        corespIndJuc[i] = aux;
      }
      if (linieMapa.equals(TipEroi.ROGUE.toString())) {
        aux = new Rogue(i);
        corespIndJuc[i] = aux;
      }
      aux2 = fileReader.nextInt();
      aux3 = fileReader.nextInt();
      aux4 = new CelulaLinieColoana(aux2, aux3);
      pozitieJucator[i] = aux4;
      mapa[aux2][aux3].setIndice(i);

    }

    numarRunde = fileReader.nextInt();
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

  public final CelulaLinieColoana[] getPozitieJucator() {
    return pozitieJucator;
  }

  public final int getNumarRunde() {
    return numarRunde;
  }

  public final FileSystem getFileReader() {
    return fileReader;
  }

  public final int getM() {
    return m;
  }

  public final int getN() {
    return n;
  }
}

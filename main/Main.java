// "Copyright [2017] Gavan Adrian-George, 324CA"
package main;

import java.io.FileNotFoundException;
import java.io.IOException;

import eroi.Cnst;
import eroi.Hero;
import fileio.FileSystem;
import parsare.Ataca;
import parsare.CelulaLinieColoana;
import parsare.CelulaMapa;
import parsare.Miscare;
import parsare.Parsare;

/**
 * Clasa Main este entry-point-ul aplicatiei noastre.
 *
 * Se foloseste API-ul pus la dispozitie pentru a citi si scrie in fisiere.
 * Fisierul din care se va face citirea este dat ca argument 1.
 * Parsarea se va face in clasa Parsare, deci instantiem o clasa Parsare ce
 * primeste ca parametru in constructor obiectul de tip FileSystem.
 *
 * Pentru fiecare runda in parte, se apeleaza metoda executaMiscare() din clasa
 * Miscare, care realizeaza mutarea jucatorilor pe harta.
 * Apoi se apeleaza metoda executaAtac() din clasa Atac, care calculeaza
 * damage-ul overtime si realizeaza lupta dintre 2 eroi (daca este cazul).
 * La final, se trece prin toti jucatorii si se scriu in fisierul de iesire
 * caracteristicile fiecaruia dintre ei, conform enuntului. Daca eroul este
 * mort, in fisier se va scrie "dead";
 *
 * Variabile si obiecte importante utilizate:
 * 1. FileSystem fileSystem - pentru citirea si scrierea in fisiere;
 * 2. Parsare pars - Clasa in care se realizeaza parsarea informatiilor din
 * fisierul de intrare;
 * 3. Miscare executaMiscare - Clasa in care se realizeaza miscarea eroilor;
 * 4. Ataca executaAtac - Clasa in care se realizeaza atacul dintre eroi;
 * 5. CelulaMapa[][] mapa - Matrice de obiecte ce reprezinta harta 2D a jocului.
 * Fiecare celula este o Clasa in care se retin tipul terenului, indicii
 * jucatorilor si numarul de eroi din celula.
 * 6. Hero[] corespIndJuc - Vector cu elemente de tip "Hero" pentru corespondenta
 * dintre indice erou si erou;
 * 7. CelulaLinieColoana[] pozitieJucator - Vector de obiecte in care se retin
 * linia si coloana "paratelului" in care se afla eroul;
 *
 * @author Gavan Adrian-George, 324CA
 */

public final class Main {

  /* Se declara un constructor privat pentru ca Main nu
   * trebuie sa poata fi instantiata.
   */
  private Main() {
  }

  public static void main(final String[] args) throws FileNotFoundException,
                                                      IOException {
    FileSystem fileSystem = new FileSystem(args[0], args[1]);
    Parsare pars = new Parsare(fileSystem);
    pars.citireFis();
    fileSystem = pars.getFileReader();
    String linieMapa;
    String afisare;
    Miscare executaMiscare;
    Ataca executaAtac;
    CelulaMapa[][] mapa = pars.getMapa();
    Hero[] corespIndJuc = pars.getCorespIndJuc();
    CelulaLinieColoana[] pozitieJucator = pars.getPozitieJucator();
    int nrPersonaje = pars.getNrPersonaje();
    int numarRunde = pars.getNumarRunde();
    int m = pars.getM();
    int n = pars.getN();

    for (int j = 0; j < numarRunde; j++) {
      linieMapa = fileSystem.nextWord();
      executaMiscare = new Miscare(linieMapa, pozitieJucator, mapa,
                                   nrPersonaje, corespIndJuc);
      executaMiscare.executaMiscare();
      pozitieJucator = executaMiscare.getPozitieJucator();
      mapa = executaMiscare.getMapa();
      corespIndJuc = executaMiscare.getCorespIndJuc();

      executaAtac = new Ataca(mapa, corespIndJuc, n, m);
      executaAtac.executaAtac();
      mapa = executaAtac.getMapa();
      corespIndJuc = executaAtac.getCorespIndJuc();
    }

    for (int i = 0; i < nrPersonaje; i++) {
      if (corespIndJuc[i].getAlive() == 0) {
        afisare = corespIndJuc[i].getRasa() + " " + Cnst.CAZ_DEAD;
      } else {
        afisare = corespIndJuc[i].getRasa();
        afisare += " " + corespIndJuc[i].getLevel();
        afisare += " " + corespIndJuc[i].getXpCurent();
        afisare += " " + corespIndJuc[i].gethPCurent();
        afisare += " " + pozitieJucator[i].getL();
        afisare += " " + pozitieJucator[i].getC();
      }
      fileSystem.writeWord(afisare);
      fileSystem.writeNewLine();
    }

    fileSystem.close();
  }
}

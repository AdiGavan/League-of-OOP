// "Copyright [2017] Gavan Adrian-George, 324CA"
package parsare;

/**
 * In aceasta clasa se retin linia si coloana celulei in care se afla
 * eroul corespunzator.
 *
 * @author Gavan Adrian-George, 324CA
 */

public class CelulaLinieColoana {
  private int l;
  private int c;

  public CelulaLinieColoana() {
  }

  public CelulaLinieColoana(final int l, final int c) {
    this.l = l;
    this.c = c;
  }

  public final int getL() {
    return l;
  }

  public final void setL(final int l) {
    this.l = l;
  }

  public final int getC() {
    return c;
  }

  public final void setC(final int c) {
    this.c = c;
  }
}

// "Copyright [2017] Gavan Adrian-George, 324CA"
package parsare;

/**
* Clasa enum TipComanda este folosita pentru tipul de teren primit.
*
* @author Gavan Adrian-George, 324CA
*/

public enum TipTeren {
  LAND("Land"),
  VOLCANIC("Volcanic"),
  WOODS("Woods"),
  DESERT("Desert"),
  L("L"),
  V("V"),
  W("W"),
  D("D");

  private final String tip;

  TipTeren(final String tipNou) {
    this.tip = tipNou;
  }

  @Override
  public String toString() {
    return tip;
  }
}

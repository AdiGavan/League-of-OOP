package parsare;

/**
* Clasa enum TipComanda este folosita pentru tipul de erou primit.
*
* @author Gavan Adrian-George, 324CA
*/

public enum TipEroi {
  PYROMANCER("P"),
  KNIGHT("K"),
  WIZARD("W"),
  ROGUE("R");

    private final String tip;

    TipEroi(final String tipNou) {
      this.tip = tipNou;
    }

    @Override
    public String toString() {
      return tip;
    }
}

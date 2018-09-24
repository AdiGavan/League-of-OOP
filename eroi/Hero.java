// "Copyright [2017] Gavan Adrian-George, 324CA"
package eroi;;

/**
 * Aceasta clasa abstracta contine datele si metodele generale pentru fiecare
 * erou.
 *
 * @author Gavan Adrian-George, 324CA
 */

public abstract class Hero {
 /*
  *Variabile utilizate:
  * - wizardContor - folosit pentru a vedea daca primul jucator din locatie este
  * Wizard sau nu;
  * - hPMax - hp-ul maxim;
  * - hpCurent - viata curenta;
  * - xPCurent - xp-ul curent al eroului;
  * - xPLevelUp - xp necesar pentru a creste un nivel;
  * - level - nivelul eroului;
  * - alive - se vede daca eroul este in viata sau nu;
  * - indiceJucator - indicele jucatorului;
  * - igniteContor si igniteDamage - folosite in calcularea DoT al unui
  * Pyromancer;
  * - slamContor - folosit pentru a stii daca jucatorul sufera din
  * cauza abilitatii Slam;
  * - paralysisContor si paralysisDamage - folosite in calcularea DoT al unui
  * Rogue;
  * - dmgOvertime - se retine DoT al unui jucator;
  * - damageTotal - damage-ul total dat de un jucator;
  * - ultimDamage - ultimul damage dat de un jucator;
  * - rasa - folosit pentru afisare in output;
  */

  protected static boolean wizardContor;
  protected int hPMax;
  protected int hPCurent;
  protected int xPCurent;
  protected int xPLevelUp;
  protected int level;
  protected int alive;
  protected int indiceJucator;
  protected int igniteContor;
  protected int igniteDamage;
  protected int slamContor;
  protected int paralysisContor;
  protected int paralysisDamage;
  protected int dmgOvertime;
  protected float damageTotal;
  protected float ultimDamage;
  protected String rasa;

  public Hero() {
    this.xPCurent = 0;
    this.xPLevelUp = Cnst.XP_LV_UP_INITIAL;
    this.level = 0;
    this.alive = 1;
    this.igniteContor = 0;
    this.igniteDamage = 0;
    this.slamContor = 0;
    this.paralysisContor = 0;
    this.indiceJucator = -1;
    this.paralysisDamage = 0;
    wizardContor = true;
  }

  public Hero(final int indiceJucator) {
    this();
    this.indiceJucator = indiceJucator;
  }

  /**
   * Metoda setxPCurent seteaza xp-ul curent al eroului.
   * Daca se depaseste pragul de nivel, atunci se incrementeaza nivelul si se
   * seteaza noul prag de xp ce trebuie atins pentru un nou nivel.
   *
   * @param lv
   */

  public void setxPCurent(final int lv) {
    xPCurent += (int) (Math.max(0, Cnst.TERMEN1_FORMULA_XP
                               - ((level - lv) * Cnst.TERMEN2_FORMULA_XP)));
    while (xPCurent >= xPLevelUp) {
      level++;
      xPLevelUp += Cnst.PLUS_XP;
      setHp();
    }
  }

  /**
   * Metoda va actualiza noul hp cand eroul creste un nivel.
   */

  public abstract void setHp();

  /**
   * Metoda calculeaza DoT primit de un erou.
   */

  public final void dmgOvertime() {
    dmgOvertime = 0;
    if (igniteContor != 0) {
      dmgOvertime = igniteDamage;
      igniteContor--;
    }
    if (paralysisContor != 0) {
      dmgOvertime = paralysisDamage;
      paralysisContor--;
    }
  }

  /**
   * Metoda va calcula damage-ul eroului in functie de abilitatile sale.
   * De asemenea, va apela si metoda executeTeren pentru a calcula abilitatile
   * eroului in functie de tipul terenului, daca este cazul.
   *
   * @param teren
   */

  public abstract void dmgHeroTeren(String teren);

  /**
   * Metoda calculareAdversar va apela metoda dmgFunctieAdversar a celuilalt
   * erou, dar va trimite ca parametru o instanta la referinta curenta, astfel
   * incat metoda overload dmgFunctieAdversar a celuilalt erou se va apela in
   * functie de tipul instantei primite.
   *
   * Aceasta metoda este folosita pentru a nu fi nevoie de instanceof.
   * @param adversar
   */

  public abstract void calculareAdversar(Hero adversar);

  /**
   *Metoda calculeaza damage-ul eroului curent in functie de rasa Pyromancer.
   * @param pyromancer
   */

  public abstract void dmgFunctieAdversar(Pyromancer pyromancer);

  /**
   *Metoda calculeaza damage-ul eroului curent in functie de rasa Knight.
   * @param knight
   */

  public abstract void dmgFunctieAdversar(Knight knight);

  /**
   *Metoda calculeaza damage-ul eroului curent in functie de rasa Wizard.
   * @param wizard
   */

  public abstract void dmgFunctieAdversar(Wizard wizard);

  /**
   *Metoda calculeaza damage-ul eroului curent in functie de rasa Rogue.
   * @param rogue
   */

  public abstract void dmgFunctieAdversar(Rogue rogue);

  /**
   * Metoda calculeaza damage-ul eroului curent in functie de teren, daca este
   * cazul.
   */

  public abstract void executeTeren();

  public final int gethPMax() {
    return hPMax;
  }

  public final int gethPCurent() {
    return hPCurent;
  }

  public final void sethPCurent(final int hPCurent) {
    this.hPCurent = hPCurent;
  }

  public final int getLevel() {
    return level;
  }

  public final int getDmgOvertime() {
    return dmgOvertime;
  }

  public final void setAlive(final int indice) {
    alive = indice;
  }

  public final float getDamageTotal() {
    return damageTotal;
  }

  public final int getParalysisContor() {
    return paralysisContor;
  }

  public final void decSlamContor() {
    slamContor--;
  }

  public final int getSlamContor() {
    return slamContor;
  }

  public final int getAlive() {
    return alive;
  }

  public final int getXpCurent() {
    return xPCurent;
  }

  public final void setWizardContor(final boolean ind) {
    wizardContor = true;
  }

  public final boolean getWizardContor() {
    return wizardContor;
  }

  public final String getRasa() {
    return rasa;
  }

  public final float getUltimDamage() {
    return ultimDamage;
  }
}

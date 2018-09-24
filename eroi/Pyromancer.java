// "Copyright [2017] Gavan Adrian-George, 324CA"
package eroi;

import parsare.TipEroi;
import parsare.TipTeren;

/**
 * Clasa Pyromancer extinde clasa Hero si contine caracteristicile si metodele
 * specifice rasei Pyromancer.
 *
 * @author Gavan Adrian-George, 324CA
 */

public class Pyromancer extends Hero {
  /*
   * Variabilele utilizate:
   * - fireblastBaza - valoarea de baza a fireblast;
   * - fireblastCalculat - valoarea fireblast in functie de nivel si modifiers;
   * - igniteBaza - valoarea de baza a ignite;
   * - igniteCalculat- valoarea ignite in functie de nivel si modifiers;
   * - igniteRound - valoarea lui ignite overtime;
   * - teren - se retine tipul terenului pe care se desfasoara batalia;
   */

  private int fireblastBaza;
  private float fireblastCalculat;
  private int igniteBaza;
  private float igniteCalculat;
  private int igniteRound;
  private String teren;

  /**
   * Constructorul seteaza indicele jucatorului si ii atribuie viata
   * si valorile abilitatilor conform rasei.
   *
   * @param indiceJucator
   */

  public Pyromancer(final int indiceJucator) {
    super(indiceJucator);
    hPMax = Cnst.HP_INITIAL_P;
    hPCurent = Cnst.HP_INITIAL_P;
    fireblastBaza = Cnst.FIREBLAST_BAZA;
    igniteBaza = Cnst.IGNITE_BAZA;
    igniteRound = Cnst.IGNITE_ROUND;
    rasa = TipEroi.PYROMANCER.toString();
  }

  /**
   * Metoda seteaza noul HP cand eroul creste un nou nivel.
   */

  @Override
  public void setHp() {
    hPMax += Cnst.CRESTERE_HP_P;
    hPCurent = hPMax;
  }

  /**
   * Metoda dmgHeroTeren calculeaza fireblast si ignite in functie de nivelul
   * eroului si le adauga in damageTotal. Se retine si in ultimDamage suma
   * celor 2 abilitati (ultimDamage este folosita la Wizard).
   *
   * De asemenea, daca terenul este cel potrivit pentru aceasta rasa, se
   * apeleaza metoda executeTeren.
   */

  @Override
  public void dmgHeroTeren(final String terenAux) {
    wizardContor = false;
    this.teren = terenAux;
    damageTotal = 0;

    fireblastCalculat = fireblastBaza + Cnst.CRESTERE_FIREBLAST_BAZA * level;
    igniteCalculat = igniteBaza + Cnst.CRESTERE_IGNITE_BAZA * level;
    damageTotal += (int) (Math.round(fireblastCalculat + igniteCalculat));
    ultimDamage = damageTotal;

    if (terenAux.equals(TipTeren.VOLCANIC.toString())) {
      executeTeren();
    }
  }

  /**
   * Metoda este folosita pentru a transmite adversarului o referinta la
   * instanta eroului, evitandu-se astfel folosirea instanceof.
   */

  @Override
  public void calculareAdversar(final Hero adversar) {
    adversar.dmgFunctieAdversar(this);
  }

  /**
   * Metoda calculeaza damage-ul eroului in functie de rasa Pyromancer.
   * Se seteaza contorul de ignite.
   * Se calculeaza damage-ul ignite overtime care va fi primit de catre erou.
   * Se actualizeaza contorul paralysis.
   * Se calculeaza damage-ul total in functie de race modifiers.
   */

  @Override
  public void dmgFunctieAdversar(final Pyromancer pyromancer) {
    igniteContor = Cnst.CONT_IGNITE;
    igniteDamage = (int) (Math.round(Cnst.PR_IGNITE_P * (pyromancer
                          .getIgniteRound() + pyromancer.getLevel()
                          * Cnst.CRESTERE_IGNITE_ROUND)));
    if (teren.equals(TipTeren.VOLCANIC.toString())) {
      igniteDamage = (int) (Math.round(Cnst.PR_VOLCANIC * (pyromancer
                           .getIgniteRound() + pyromancer.getLevel()
                           * Cnst.CRESTERE_IGNITE_ROUND) * Cnst.PR_IGNITE_P));
    }
    paralysisContor = 0;

    fireblastCalculat = (int) (Math.round(Cnst.PR_FIREBLAST_P
                               * fireblastCalculat));
    igniteCalculat = (int) (Math.round(Cnst.PR_IGNITE_P * igniteCalculat));
    damageTotal = fireblastCalculat + igniteCalculat;
  }

  /**
   * Metoda calculeaza damage-ul eroului in functie de rasa knight.
   * Se seteaza contorul de slam.
   * Se actualizeaza contorul paralysis si ignite.
   * Se calculeaza damage-ul total in functie de race modifiers.
   */

  @Override
  public void dmgFunctieAdversar(final Knight knight) {
    slamContor = Cnst.CONT_SLAM;
    paralysisContor = 0;
    igniteContor = 0;

    fireblastCalculat = (int) (Math.round(Cnst.PR_FIREBLAST_K
                               * fireblastCalculat));
    igniteCalculat = (int) (Math.round(Cnst.PR_IGNITE_K * igniteCalculat));
    damageTotal = fireblastCalculat + igniteCalculat;
  }

  /**
   * Metoda calculeaza damage-ul eroului in functie de rasa wizard.
   * Se calculeaza damage-ul total in functie de race modifiers.
   */

  @Override
  public void dmgFunctieAdversar(final Wizard wizard) {
    fireblastCalculat = (int) (Math.round(Cnst.PR_FIREBLAST_W
                               * fireblastCalculat));
    igniteCalculat = (int) (Math.round(Cnst.PR_IGNITE_W * igniteCalculat));
    damageTotal = fireblastCalculat + igniteCalculat;
  }

  /**
   * Metoda calculeaza damage-ul eroului in functie de rasa Rogue.
   * Se seteaza contorul paralysis.
   * Se calculeaza damage-ul paralysis overtime care va fi primit de erou.
   * Se actualizeaza contorul ignite.
   * Se calculeaza damage-ul total in functie de race modifiers.
   */

  @Override
  public void dmgFunctieAdversar(final Rogue rogue) {
    paralysisContor = Cnst.CONT_PARALYSIS;
    if (teren.equals(TipTeren.WOODS.toString())) {
      paralysisContor = Cnst.CONT_PARALYSIS_WOODS;
      paralysisDamage = (int) (Math.round(Cnst.PR_WOODS * Cnst.PR_PARALYSIS_P
                              * (rogue.getParalysisBaza() + rogue.getLevel()
                              * Cnst.CRESTERE_PARALYSIS)));
    } else {
      paralysisDamage = (int) (Math.round(Cnst.PR_PARALYSIS_P * rogue
                               .getParalysisBaza() + rogue.getLevel()
                               * Cnst.CRESTERE_PARALYSIS));
    }
    igniteContor = 0;


    fireblastCalculat = (int) (Math.round(Cnst.PR_IGNITE_R
                               * fireblastCalculat));
    igniteCalculat = (int) (Math.round(Cnst.PR_FIREBLAST_R * igniteCalculat));
    damageTotal = fireblastCalculat + igniteCalculat;
  }

  /**
   * Metoda calculeaza damage-ul eroului curent in functie de land modifiers,
   * daca este cazul.
   */

  @Override
  public void executeTeren() {
    fireblastCalculat = (int) (Math.round(Cnst.PR_VOLCANIC
                               * fireblastCalculat));
    igniteCalculat = (int) (Math.round(Cnst.PR_VOLCANIC * igniteCalculat));
    damageTotal = fireblastCalculat + igniteCalculat;
    ultimDamage = damageTotal;
  }

  public final float getIgniteRound() {
      return igniteRound;
  }
}

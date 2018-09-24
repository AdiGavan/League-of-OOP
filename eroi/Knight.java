// "Copyright [2017] Gavan Adrian-George, 324CA"
package eroi;

import parsare.TipEroi;
import parsare.TipTeren;

/**
 * Clasa Knight extinde clasa Hero si contine caracteristicile si metodele
 * specifice rasei Knight.
 *
 * @author Gavan Adrian-George, 324CA
 */

public class Knight extends Hero {
 /*
  * Variabilele utilizate:
  * - executeCalculat - valoarea abilitatii execute in functie de nivel si
  * modifiers (sau in functie de HP limit, daca este cazul);
  * - hPLimitCalculat - procentele calculate in functie de nivelul eroului;
  * - slamCalculat - valoarea abilitatii slam in functie de nivel si
  * modifiers;
  * - teren - se retine tipul terenului pe care se desfasoara lupta;
  */

  private float executeCalculat;
  private int hPLimitCalculat;
  private float slamCalculat;
  private String teren;

  /**
   * Constructorul seteaza indicele jucatorului si ii atribuie viata
   * conform rasei. Se seteaza si tipul eroului (folosit la afisare).
   *
   * @param indiceJucator
   */

  public Knight(final int indiceJucator) {
    super(indiceJucator);
    hPMax = Cnst.HP_INITIAL_K;
    hPCurent = Cnst.HP_INITIAL_K;
    rasa = TipEroi.KNIGHT.toString();
  }

  /**
   * Metoda seteaza noul HP cand eroul creste un nou nivel.
   */

  public void setHp() {
    hPMax += Cnst.CRESTERE_HP_K;
    hPCurent = hPMax;
  }

  /**
   * Metoda dmgHeroTeren calculeaza execute si slam in functie de nivelul
   * eroului si le adauga in damageTotal. Se retine si in ultimDamage suma
   * celor 2 abilitati (ultimDamage este folosita la Wizard).
   *
   * De asemenea, daca terenul este cel potrivit pentru aceasta rasa, se
   * apeleaza metoda executeTeren.
   *
   * Se verifica si daca hPLimitCalculat depaseste procentul maxim admis pentru
   * HP limit.
   */

  @Override
  public void dmgHeroTeren(final String terenAux) {
    this.teren = terenAux;
    wizardContor = false;
    damageTotal = 0;

    executeCalculat = Cnst.EXECUTE_BAZA + Cnst.CRESTERE_EXECUTE * level;
    slamCalculat = Cnst.SLAM_BAZA + Cnst.CRESTERE_SLAM * level;
    damageTotal += (int) (Math.round(executeCalculat + slamCalculat));
    ultimDamage = damageTotal;

    if (terenAux.equals(TipTeren.LAND.toString())) {
      executeTeren();
    }

    hPLimitCalculat = Cnst.PR_HPLIMIT + level;
    if (hPLimitCalculat > Cnst.PROCENT_MAXIM) {
      hPLimitCalculat = Cnst.PROCENT_MAXIM;
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
    igniteDamage = (int) (Math.round(Cnst.PR_IGNITE_K * (pyromancer
                        .getIgniteRound() + pyromancer.getLevel()
                        * Cnst.CRESTERE_IGNITE_ROUND)));

    if (teren.equals(TipTeren.VOLCANIC.toString())) {
      igniteDamage = (int) (Math.round(Cnst.PR_VOLCANIC * (pyromancer
                           .getIgniteRound() + pyromancer.getLevel()
                           * Cnst.CRESTERE_IGNITE_ROUND) * Cnst.PR_IGNITE_K));
    }
    paralysisContor = 0;

    // Daca viata adversarului este mai mica decat HP limit, atunci adversarul
    // va primi damage egal cu viata lui de la abilitatea execute.
    if ((((float) (hPLimitCalculat) / Cnst.LA_SUTA) * pyromancer.gethPMax())
           > pyromancer.gethPCurent()) {
      executeCalculat = pyromancer.gethPCurent();
      ultimDamage = executeCalculat + slamCalculat;

    } else {
      executeCalculat = (int) (Math.round(Cnst.PR_EXECUTE_P * executeCalculat));
    }

    slamCalculat = (int) (Math.round(Cnst.PR_SLAM_P * slamCalculat));
    damageTotal = executeCalculat + slamCalculat;
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

    // Daca viata adversarului este mai mica decat HP limit, atunci adversarul
    // va primi damage egal cu viata lui de la abilitatea execute.
    if ((((float) (hPLimitCalculat) / Cnst.LA_SUTA) * knight.gethPMax())
            > knight.gethPCurent()) {
      executeCalculat = knight.gethPCurent();
      ultimDamage = executeCalculat + slamCalculat;

    } else {
      executeCalculat = (int) (Math.round(executeCalculat));
    }

    slamCalculat = (int) (Math.round(Cnst.PR_SLAM_K * slamCalculat));
    damageTotal = executeCalculat + slamCalculat;
  }

  /**
   * Metoda calculeaza damage-ul eroului in functie de rasa wizard.
   * Se calculeaza damage-ul total in functie de race modifiers.
   */

  @Override
  public void dmgFunctieAdversar(final Wizard wizard) {
    // Daca viata adversarului este mai mica decat HP limit, atunci adversarul
    // va primi damage egal cu viata lui de la abilitatea execute.
    if (((float) (hPLimitCalculat / Cnst.LA_SUTA) * wizard.gethPMax())
            > wizard.gethPCurent()) {
      executeCalculat = wizard.gethPCurent();
      ultimDamage = executeCalculat + slamCalculat;

    } else {
      executeCalculat = (int) (Math.round(Cnst.PR_EXECUTE_W * executeCalculat));
    }

    slamCalculat = (int) (Math.round((Cnst.PR_SLAM_W * slamCalculat)));
    damageTotal = executeCalculat + slamCalculat;
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
      paralysisDamage = (int) (Math.round(Cnst.PR_WOODS * Cnst.PR_PARALYSIS_K
                               * (rogue.getParalysisBaza() + rogue.getLevel()
                               * Cnst.CRESTERE_PARALYSIS)));

    } else {
      paralysisDamage = (int) (Math.round(Cnst.PR_PARALYSIS_K * rogue
                               .getParalysisBaza() + rogue.getLevel()
                               * Cnst.CRESTERE_PARALYSIS));
    }
    igniteContor = 0;

    // Daca viata adversarului este mai mica decat HP limit, atunci adversarul
    // va primi damage egal cu viata lui de la abilitatea execute.
    if ((((hPLimitCalculat) / Cnst.LA_SUTA) * rogue.gethPMax())
            > rogue.gethPCurent()) {
      executeCalculat = rogue.gethPCurent();
      ultimDamage = executeCalculat + slamCalculat;

    } else {
      executeCalculat = (int) (Math.round(Cnst.PR_EXECUTE_R * executeCalculat));
    }

    slamCalculat = (int) (Math.round(Cnst.PR_SLAM_R * slamCalculat));
    damageTotal = executeCalculat + slamCalculat;
  }

  /**
   * Metoda calculeaza damage-ul eroului curent in functie de land modifiers,
   * daca este cazul.
   */

  @Override
  public void executeTeren() {
    executeCalculat = (int) (Math.round((Cnst.PR_LAND * executeCalculat)));
    slamCalculat = (int) (Math.round((Cnst.PR_LAND * slamCalculat)));
    damageTotal = executeCalculat + slamCalculat;
    ultimDamage = damageTotal;
  }
}

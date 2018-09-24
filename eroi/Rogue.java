// "Copyright [2017] Gavan Adrian-George, 324CA"
package eroi;

import parsare.TipEroi;
import parsare.TipTeren;

/**
 * Clasa Pyromancer extinde clasa Hero si contine caracteristicile si metodele
 * specifice rasei Rogue.
 *
 * @author Gavan Adrian-George, 324CA
 */

public class Rogue extends Hero {
 /*
  * Variabile utilizate:
  * - backstabBaza - valoarea de baza a abilitatii backstab;
  * - backstabCurent - valoarea abilitatii backstab in functie de nivel si
  * modifiers;
  * - contorBackstab - de cate ori a executat backstab;
  * - paralysisBaza - valoarea de baza a abilitatii paralysis;
  * - paralysisCurent - valoarea abilitatii paralysis in functie de nivel si
  * modifiers;
  * - firstTimeWoods - se retine daca a fost sau nu pentru prima data pe
  * teren de tip Woods;
  * - teren - se retine tipul terenului pe care se desfasoara batalia;
  */

  private int backstabBaza;
  private float backstabCurent;
  private int contorBackstab;
  private int paralysisBaza;
  private float paralysisCurent;
  private boolean firstTimeWoods;
  private String teren;

  /**
   * Constructorul seteaza indicele jucatorului si ii atribuie viata
   * si valorile abilitatilor conform rasei.
   *
   * @param indiceJucator
   */

  public Rogue(final int indiceJucator) {
    super(indiceJucator);
    hPMax = Cnst.HP_INITIAL_R;
    hPCurent = Cnst.HP_INITIAL_R;
    backstabBaza = Cnst.BACKSTAB_BAZA;
    paralysisBaza = Cnst.PARALYSIS_BAZA;
    rasa = TipEroi.ROGUE.toString();
    firstTimeWoods = true;
  }

  /**
   * Metoda seteaza noul HP cand eroul creste un nou nivel.
   */

  @Override
  public void setHp() {
    hPMax += Cnst.CRESTERE_HP_R;
    hPCurent = hPMax;
  }

  /**
   * Metoda dmgHeroTeren calculeaza backstab si paralysis in functie de nivelul
   * eroului si le adauga in damageTotal. Se retine si in ultimDamage suma
   * celor 2 abilitati (ultimDamage este folosita la Wizard).
   *
   * Daca eroul este pentru prima oara pe teren Woods, atunci acesta va
   * da critical damage.
   *
   * De asemenea, daca terenul este cel potrivit pentru aceasta rasa, se
   * apeleaza metoda executeTeren.
   */

  @Override
  public void dmgHeroTeren(final String terenAux) {
    damageTotal = 0;
    this.teren = terenAux;
    wizardContor = false;

    // Cazul cand este pentru prima oara pe Woods.
    if (terenAux.equals(TipTeren.WOODS.toString()) && firstTimeWoods) {
      damageTotal += (int) (Math.round(Cnst.PR_CRITIC_BACK * (backstabBaza
                            + (Cnst.CRESTERE_BACKSTAB * level))));
      backstabCurent = (int) (Math.round(Cnst.PR_CRITIC_BACK * (backstabBaza
                              + (Cnst.CRESTERE_BACKSTAB  * level))));

      contorBackstab = 0;
      firstTimeWoods = false;

    // Cazul cand nu e prima oara pe Woods si nu a ajuns la numarul necesar de
    // lovituri backstab pentru a da critical damage.
    } else if (contorBackstab != Cnst.CONT_BACKSTAB) {
      damageTotal += (backstabBaza + (Cnst.CRESTERE_BACKSTAB  * level));
      backstabCurent = backstabBaza + (Cnst.CRESTERE_BACKSTAB  * level);
      contorBackstab++;

    // Cazul cand trebuie sa dea critical damage.
    } else if (contorBackstab == Cnst.CONT_BACKSTAB
               && terenAux.equals(TipTeren.WOODS.toString())) {
      damageTotal += (int) (Math.round(Cnst.PR_CRITIC_BACK * (backstabBaza
                           + (Cnst.CRESTERE_BACKSTAB  * level))));
      backstabCurent = (int) (Math.round(Cnst.PR_CRITIC_BACK * (backstabBaza
                             + (Cnst.CRESTERE_BACKSTAB  * level))));
      contorBackstab = 0;
    }

    damageTotal += (paralysisBaza + Cnst.CRESTERE_PARALYSIS * level);
    ultimDamage = damageTotal;
    paralysisCurent = paralysisBaza + Cnst.CRESTERE_PARALYSIS * level;

    if (terenAux.equals(TipTeren.WOODS.toString())) {
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
    igniteDamage = (int) (Math.round(Cnst.PR_IGNITE_R * (pyromancer
                         .getIgniteRound() + pyromancer.getLevel()
                         * Cnst.CRESTERE_IGNITE_ROUND)));
    if (teren.equals(TipTeren.VOLCANIC.toString())) {
      igniteDamage = (int) (Math.round(Cnst.PR_VOLCANIC * (pyromancer
                           .getIgniteRound() + pyromancer.getLevel()
                           * Cnst.CRESTERE_IGNITE_ROUND) * Cnst.PR_IGNITE_R));
    }
    paralysisContor = 0;

    backstabCurent = (int) (Math.round(Cnst.PR_BACKSTAB_P * backstabCurent));
    paralysisCurent = (int) (Math.round(Cnst.PR_PARALYSIS_P * paralysisCurent));
    damageTotal = backstabCurent + paralysisCurent;
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

    backstabCurent = (int) (Math.round(Cnst.PR_BACKSTAB_K * backstabCurent));
    paralysisCurent = (int) (Math.round(Cnst.PR_PARALYSIS_K * paralysisCurent));
    damageTotal = backstabCurent + paralysisCurent;
  }

  /**
   * Metoda calculeaza damage-ul eroului in functie de rasa wizard.
   * Se calculeaza damage-ul total in functie de race modifiers.
   */

  @Override
  public void dmgFunctieAdversar(final Wizard wizard) {
    backstabCurent = (int) (Math.round(Cnst.PR_BACKSTAB_W * backstabCurent));
    paralysisCurent = (int) (Math.round(Cnst.PR_PARALYSIS_W * paralysisCurent));
    damageTotal = backstabCurent + paralysisCurent;
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
      paralysisDamage = (int) (Math.round(Cnst.PR_WOODS * Cnst.PR_PARALYSIS_R
                              * (rogue.getParalysisBaza() + rogue.getLevel()
                              * Cnst.CRESTERE_PARALYSIS)));
    } else {
      paralysisDamage = (int) (Math.round(Cnst.PR_PARALYSIS_R * rogue
                              .getParalysisBaza() + rogue.getLevel()
                              * Cnst.CRESTERE_PARALYSIS));
    }
    igniteContor = 0;

    backstabCurent = (int) (Math.round(Cnst.PR_BACKSTAB_R * backstabCurent));
    paralysisCurent = (int) (Math.round(Cnst.PR_PARALYSIS_R * paralysisCurent));
    damageTotal = backstabCurent + paralysisCurent;
  }

  /**
   * Metoda calculeaza damage-ul eroului curent in functie de land modifiers,
   * daca este cazul.
   */

  @Override
  public void executeTeren() {
    backstabCurent = (int) (Math.round(Cnst.PR_WOODS * backstabCurent));
    paralysisCurent = (int) (Math.round(Cnst.PR_WOODS * paralysisCurent));
    damageTotal = backstabCurent + paralysisCurent;
    ultimDamage = damageTotal;
  }

  public final int getContorBackstab() {
    return contorBackstab;
  }

  public final void setContorBackstab(final int contorBackstab) {
    this.contorBackstab = contorBackstab;
  }

  public final int getParalysisBaza() {
    return paralysisBaza;
  }

  public final boolean getFirstTimeWoods() {
    return firstTimeWoods;
  }

  public final void setFirstTimeWoods(final boolean firstTimeWoods) {
    this.firstTimeWoods = firstTimeWoods;
  }
}

// "Copyright [2017] Gavan Adrian-George, 324CA"
package eroi;

import parsare.TipEroi;
import parsare.TipTeren;

/**
 * Clasa Wizard extinde clasa Hero si contine caracteristicile si metodele
 * specifice rasei Wizard.
 *
 * @author Gavan Adrian-George, 324CA
 */

public class Wizard extends Hero {
 /*
  * Variabile utilizate:
  * - drainProcent - procentul de baza al abilitatii drain;
  * - drain - valoarea damage-ului dat de catre abilitatea drain;
  * - deflectProcent - procentul de baza al abilitatii deflect;
  * - deflectCalculat - valoarea damage-ului dat de abilitatea deflect;
  * - teren - se retine tipul terenului pe care se desfasoara lupta;
  */

  private int drainProcent;
  private float drain;
  private int deflectProcent;
  private float deflectCalculat;
  private String teren;

  /**
   * Constructorul seteaza indicele jucatorului si ii atribuie viata
   * si valorile abilitatilor conform rasei. Se seteaza si tipul eroului
   * (folosit la afisare).
   *
   * @param indiceJucator
   */

  public Wizard(final int indiceJucator) {
    super(indiceJucator);
    hPMax = Cnst.HP_INITIAL_W;
    hPCurent = Cnst.HP_INITIAL_W;
    drainProcent = Cnst.PR_DRAIN;
    deflectProcent = Cnst.PR_DEFLECT;
    rasa = TipEroi.WIZARD.toString();
  }

  /**
   * Metoda seteaza noul HP cand eroul creste un nou nivel.
   */

  @Override
  public void setHp() {
    hPMax += Cnst.CRESTERE_HP_W;
    hPCurent = hPMax;
  }

  /**
   * In cazul eroului de tip Wizard, nu se poate calcula in aceasta functie
   * damage-ul dat de catre Wizard in functie de nivel si teren pentru
   * ca este nevoie de mai multe date despre adversar.
   *
   * In acest caz, doar se retine tipul terenului si se reinitializeaza
   * deflectCalculat, iar puterile vor fi calculate (inclusiv in functie
   * de teren si nivel) in functiile de calcul in functie de adversari
   * (dmgFunctieAdversar).
   */

  @Override
  public void dmgHeroTeren(final String terenAux) {
    this.teren = terenAux;
    deflectCalculat = 0;
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
   * Metoda calculeaza damage-ul eroului in functie de nivel, modificatorii
   * de teren si modificatorii pentru tipul Pyromancer.
   * Se seteaza contorul de ignite.
   * Se calculeaza damage-ul ignite overtime care va fi primit de catre erou.
   * Se actualizeaza contorul paralysis.
   * Se calculeaza damage-ul primit de la adversar (fara race modifiers). Daca
   * wizard este primul, atunci apelam functia dmgHeroTeren a pyromancer-ului
   * pentru a vedea care va fi damage-ul pe care il va primi Wizard. Daca
   * wizard este al 2-lea, atunci doar se ia ultimDamage.
   *
   * Se calculeaza damage-ul total in functie de nivel si teren. Apoi se
   * calculeaza damage-ul si in functie de race modifiers.
   */

  @Override
  public void dmgFunctieAdversar(final Pyromancer pyromancer) {
    float damagePyromancer = 0;
    igniteContor = Cnst.CONT_IGNITE;
    igniteDamage = (int) (Math.round(Cnst.PR_IGNITE_W * (pyromancer
                          .getIgniteRound() + pyromancer.getLevel()
                          * Cnst.CRESTERE_IGNITE_ROUND)));

    if (teren.equals(TipTeren.VOLCANIC.toString())) {
    igniteDamage = (int) (Math.round(Cnst.PR_VOLCANIC * (pyromancer
                          .getIgniteRound() + pyromancer.getLevel()
                          * Cnst.CRESTERE_IGNITE_ROUND) * Cnst.PR_IGNITE_W));
    }
    paralysisContor = 0;

    if (wizardContor) {
      pyromancer.dmgHeroTeren(teren);
      damagePyromancer = pyromancer.getUltimDamage();

    } else {
      damagePyromancer = pyromancer.getUltimDamage();
    }
    wizardContor = true;

    // Se verifica daca procentul pentru deflect depaseste valoarea maxima.
    if (deflectProcent + Cnst.CRESTERE_DEFLECT * level > Cnst.PR_DEFLECT_MAX) {
      deflectCalculat = (Cnst.PR_DEFLECT_MAX / Cnst.LA_SUTA) * damagePyromancer;

    } else {
      deflectCalculat = ((float) (deflectProcent + Cnst.CRESTERE_DEFLECT
                                  * level) / Cnst.LA_SUTA) * damagePyromancer;
    }
    drain = ((drainProcent + Cnst.CRESTERE_DRAIN * level) * Cnst.PR_DRAIN_P
            / Cnst.LA_SUTA) * (Math.min(Cnst.TERMEN_DRAINFORM
            * (pyromancer.gethPMax()), pyromancer.hPCurent));

    if (teren.equals(TipTeren.DESERT.toString())) {
      executeTeren();
    }

    deflectCalculat = Cnst.PR_DEFLECT_P * deflectCalculat;
    damageTotal = (int) (Math.round(drain + deflectCalculat));
  }

  /**
   * Metoda calculeaza damage-ul eroului in functie de nivel, modificatorii
   * de teren si modificatorii pentru tipul Knight.
   * Se seteaza contorul de slam.
   * Se reseteaza igniteContor si paralysisContor.
   * Se calculeaza damage-ul primit de la adversar (fara race modifiers). Daca
   * wizard este primul, atunci apelam functia dmgHeroTeren a knight-ului si
   * functia calculareAdversar a wizard-ului (pentru ca HP limit se verifica
   * in dmgFunctieAdversar in knight) pentru a vedea care va fi damage-ul pe
   * care il va primi Wizard. Daca wizard este al 2-lea, atunci doar se ia
   * ultimDamage.
   * Se calculeaza damage-ul total in functie de nivel si teren. Apoi se
   * calculeaza damage-ul si in functie de race modifiers.
   */

  @Override
  public void dmgFunctieAdversar(final Knight knight) {
    float damageKnight = 0;
    slamContor = Cnst.CONT_SLAM;
    igniteContor = 0;
    paralysisContor = 0;

    if (wizardContor) {
      knight.dmgHeroTeren(teren);
      calculareAdversar(knight);
      damageKnight = knight.getUltimDamage();

    } else {
      damageKnight = knight.getUltimDamage();
    }
    wizardContor = true;

    // Se verifica daca procentul pentru deflect depaseste valoarea maxima.
    if (deflectProcent + Cnst.CRESTERE_DEFLECT * level > Cnst.PR_DEFLECT_MAX) {
      deflectCalculat = (Cnst.PR_DEFLECT_MAX / Cnst.LA_SUTA) * damageKnight;

    } else {
      deflectCalculat = ((float) (deflectProcent + Cnst.CRESTERE_DEFLECT
                                 * level) / Cnst.LA_SUTA) * damageKnight;
    }
    drain = (((drainProcent + Cnst.CRESTERE_DRAIN * level) * Cnst.PR_DRAIN_K)
            / Cnst.LA_SUTA) * (Math.min(Cnst.TERMEN_DRAINFORM
            * (knight.gethPMax()), knight.hPCurent));

    if (teren.equals(TipTeren.DESERT.toString())) {
      executeTeren();
    }

    deflectCalculat = Cnst.PR_DEFLECT_K * deflectCalculat;
    damageTotal = (int) (Math.round(drain + deflectCalculat));
  }

  /**
   * Metoda calculeaza damage-ul eroului in functie de nivel, modificatorii
   * de teren si modificatorii pentru tipul Rogue.
   * Se seteaza contorul de paralysis.
   * Se calculeaza damage-ul paralysis overtime care va fi primit de catre erou.
   * Se reseteaza igniteContor.
   *
   * Se calculeaza damage-ul primit de la adversar (fara race modifiers). Daca
   * wizard este primul, atunci retinem contorul lui backstab si contorul lui
   * firstTimeWoods, apelam functia dmgHeroTeren si apoi setam inapoi contorul
   * lui backstab si al lui firstTimeWoods, pentru a aduce Rogue-ul exact cum
   * era inainte sa se "simuleze" atacul asupra lui wizard. Spun sa se simuleze
   * pentru ca wizard apeleaza o functie de calcul a damage-ului lui rogue
   * pentru a vedea care va fi damage-ul pe care il va primi, modificandu-se
   * astfel contoarele, ceea ce inseamna ca rogue-ul trebuie adus la forma
   * initiala. Daca wizard este al 2-lea, atunci doar se ia ultimDamage.
   *
   * Se calculeaza damage-ul total in functie de nivel si teren. Apoi se
   * calculeaza damage-ul si in functie de race modifiers.
   */

  @Override
  public void dmgFunctieAdversar(final Rogue rogue) {
    int retineContorBackstab;
    boolean retineFirstTimeWoods;
    float damageRogue = 0;

    paralysisContor = Cnst.CONT_PARALYSIS;
    if (teren.equals(TipTeren.WOODS.toString())) {
      paralysisContor = Cnst.CONT_PARALYSIS_WOODS;
      paralysisDamage = (int) (Math.round(Cnst.PR_WOODS * Cnst.PR_PARALYSIS_W
                              * (rogue.getParalysisBaza() + rogue.getLevel()
                              * Cnst.CRESTERE_PARALYSIS)));

    } else {
      paralysisDamage = (int) (Math.round(Cnst.PR_PARALYSIS_W
                              * rogue.getParalysisBaza() + rogue.getLevel()
                              * Cnst.CRESTERE_PARALYSIS));
    }
    igniteContor = 0;

    if (wizardContor) {
      retineContorBackstab = rogue.getContorBackstab();
      retineFirstTimeWoods = rogue.getFirstTimeWoods();
      rogue.dmgHeroTeren(teren);
      rogue.setContorBackstab(retineContorBackstab);
      rogue.setFirstTimeWoods(retineFirstTimeWoods);
      damageRogue = rogue.getUltimDamage();

    } else {
      damageRogue = rogue.getUltimDamage();
    }
    wizardContor = true;

    // Se verifica daca procentul pentru deflect depaseste valoarea maxima.
    if (deflectProcent + Cnst.CRESTERE_DEFLECT * level > Cnst.PR_DEFLECT_MAX) {
      deflectCalculat = (Cnst.PR_DEFLECT_MAX / Cnst.LA_SUTA) * damageRogue;

    } else {
      deflectCalculat = (float) (deflectProcent + Cnst.CRESTERE_DEFLECT * level)
                                / Cnst.LA_SUTA * damageRogue;
    }
    drain = (((drainProcent + Cnst.CRESTERE_DRAIN * level) * Cnst.PR_DRAIN_R)
            / Cnst.LA_SUTA) * (Math.min((Cnst.TERMEN_DRAINFORM
            * (rogue.gethPMax())), rogue.hPCurent));

    if (teren.equals(TipTeren.DESERT.toString())) {
      executeTeren();
    }

    deflectCalculat = Cnst.PR_DEFLECT_R * deflectCalculat;
    damageTotal = (int) (Math.round(drain + deflectCalculat));
  }

  /**
   * Metoda calculeaza damage-ul eroului in functie de nivel, modificatorii
   * de teren si modificatorii pentru tipul Wizard.
   *
   * Nu mai trebuie sa se calculeze damage-ul primit de la adversar pentru
   * ca wizard nu da deflect altui wizard.
   *
   * Se calculeaza damage-ul total in functie de nivel si teren. Apoi se
   * calculeaza damage-ul si in functie de race modifiers.
   */

  @Override
  public void dmgFunctieAdversar(final Wizard wizard) {
    wizardContor = true;
    drain = (((drainProcent + Cnst.CRESTERE_DRAIN * level) * Cnst.PR_DRAIN_W)
            / Cnst.LA_SUTA) * (Math.min(Cnst.TERMEN_DRAINFORM
            * (wizard.gethPMax()), wizard.hPCurent));

    if (teren.equals(TipTeren.DESERT.toString())) {
      executeTeren();
    }

    damageTotal = (int) (Math.round(drain));
  }

  /**
   * Metoda calculeaza damage-ul eroului curent in functie de land modifiers,
   * daca este cazul.
   */

  @Override
  public void executeTeren() {
    drain = Cnst.PR_DESERT * drain;
    deflectCalculat = Cnst.PR_DESERT * deflectCalculat;
    damageTotal = (int) (Math.round(deflectCalculat + drain));
    }
}

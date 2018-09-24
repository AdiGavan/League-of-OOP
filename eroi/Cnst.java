// "Copyright [2017] Gavan Adrian-George, 324CA"
package eroi;

/**
 * Aceasta clasa contine constante (static final) utilizate in clasa abstracta
 * Hero si clasele derivate din aceasta (Knight, Pyromancer, Rogue, Wizard).
 *
 * @author Gavan Adrian-George, 324CA
 */

public final class Cnst {
  private Cnst() {
  }

  // Constatna CAZ_DEAD este folosita in Main pentru scrierea in fisier.
  public static final String CAZ_DEAD = "dead";

  // Constante pentru Hero
  public static final int XP_LV_UP_INITIAL = 250;
  public static final int TERMEN1_FORMULA_XP = 200;
  public static final int TERMEN2_FORMULA_XP = 40;
  public static final int PLUS_XP = 50;
  public static final int LA_SUTA = 100;

  // Constante specifice Knight
  public static final int EXECUTE_BAZA = 200;
  public static final int PR_HPLIMIT = 20;
  public static final int SLAM_BAZA = 100;
  public static final int HP_INITIAL_K = 900;
  public static final int PROCENT_MAXIM = 40;
  public static final int CRESTERE_HP_K = 80;
  public static final int CRESTERE_EXECUTE = 30;
  public static final int CRESTERE_SLAM = 40;
  public static final float PR_LAND = 1.15f;
  public static final float PR_EXECUTE_P = 1.1f;
  public static final float PR_SLAM_P = 0.9f;
  public static final float PR_SLAM_K = 1.2f;
  public static final float PR_EXECUTE_W = 0.8f;
  public static final float PR_SLAM_W = 1.05f;
  public static final float PR_EXECUTE_R = 1.15f;
  public static final float PR_SLAM_R = 0.8f;
  public static final int CONT_SLAM = 1;

  // Constante specifice Pyromancer
  public static final int HP_INITIAL_P = 500;
  public static final int FIREBLAST_BAZA = 350;
  public static final int IGNITE_BAZA = 150;
  public static final int IGNITE_ROUND = 50;
  public static final int CRESTERE_HP_P = 50;
  public static final float CRESTERE_FIREBLAST_BAZA = 50;
  public static final float CRESTERE_IGNITE_BAZA = 20;
  public static final float CRESTERE_IGNITE_ROUND = 30;
  public static final float PR_VOLCANIC = 1.25f;
  public static final float PR_IGNITE_K = 1.2f;
  public static final float PR_IGNITE_W = 1.05f;
  public static final float PR_IGNITE_P = 0.9f;
  public static final float PR_IGNITE_R = 0.8f;
  public static final float PR_FIREBLAST_P = 0.9f;
  public static final float PR_FIREBLAST_W = 1.05f;
  public static final float PR_FIREBLAST_R = 0.8f;
  public static final float PR_FIREBLAST_K = 1.20f;
  public static final int CONT_IGNITE = 2;

  // Constante specifice Rogue
  public static final int HP_INITIAL_R = 600;
  public static final int BACKSTAB_BAZA = 200;
  public static final int PARALYSIS_BAZA = 40;
  public static final int CONT_PARALYSIS = 3;
  public static final int CONT_PARALYSIS_WOODS = 6;
  public static final int CRESTERE_PARALYSIS = 10;
  public static final int CRESTERE_HP_R = 40;
  public static final int CRESTERE_BACKSTAB = 20;
  public static final float PR_WOODS = 1.15f;
  public static final float PR_PARALYSIS_K = 0.8f;
  public static final float PR_PARALYSIS_P = 1.2f;
  public static final float PR_PARALYSIS_W = 1.25f;
  public static final float PR_PARALYSIS_R = 0.9f;
  public static final float PR_BACKSTAB_P = 1.25f;
  public static final float PR_BACKSTAB_K = 0.9f;
  public static final float PR_BACKSTAB_W = 1.25f;
  public static final float PR_BACKSTAB_R = 1.20f;
  public static final float PR_CRITIC_BACK = 1.5f;
  public static final int CONT_BACKSTAB = 3;

  // Constante specifice Wizard
  public static final int HP_INITIAL_W = 400;
  public static final int CRESTERE_HP_W = 30;
  public static final int CRESTERE_DEFLECT = 2;
  public static final int CRESTERE_DRAIN = 5;
  public static final int PR_DRAIN = 20;
  public static final int PR_DEFLECT = 35;
  public static final float PR_DEFLECT_MAX = 70f;
  public static final float PR_DEFLECT_P = 1.3f;
  public static final float PR_DEFLECT_K = 1.4f;
  public static final float PR_DEFLECT_R = 1.2f;
  public static final float PR_DRAIN_P = 0.9f;
  public static final float PR_DRAIN_K = 1.2f;
  public static final float PR_DRAIN_R = 0.8f;
  public static final float PR_DRAIN_W = 1.05f;
  public static final float PR_DESERT = 1.1f;
  public static final float TERMEN_DRAINFORM = 0.3f;
}

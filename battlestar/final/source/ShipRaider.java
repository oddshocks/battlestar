   import javax.swing.ImageIcon;

/**
 * The Cylon fighter
 * Spring 2012
 * @author Scott Gunther and David Gay
 */



   public class ShipRaider extends Ship implements BattleConstants
   {
    /**
     * ShipRaider constructor
     * @param n the name of the Raider
     * @param p the position of the Raider
     */
      public ShipRaider(String n, int p)
      {
         super();
         this.setName(n);
         this.setType(C_RAIDER);
         this.setHits(1);
         this.setPosition(p);
         this.setMoveRange(2);
         this.setAttackRange(2);
         this.setWeaponDamage(1);
        
         this.setIcon(new ImageIcon("images/raider.png"));
      }
   }

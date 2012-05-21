   import javax.swing.ImageIcon;

/**
 * The Cylon special ship
 * Spring 2012
 * @author Scott Gunther and David Gay
 */

   public class ShipHeavyRaider extends Ship implements BattleConstants
   {
    /**
     * ShipHeavyRaider constructor
     * @param n the name of the Heavy Raider
     * @param p the position of the ship
     */
      public ShipHeavyRaider(String n, int p)
      {
         super();
         this.setName(n);
         this.setType(C_HEAVY_RAIDER);
         this.setHits(3);
         this.setPosition(p);
         this.setMoveRange(1);
         this.setAttackRange(2);
         this.setWeaponDamage(1);
        		  
         this.setIcon(new ImageIcon("images/heavy_raider.jpg"));
      }
   }

   import javax.swing.ImageIcon;

/**
 * The Cylon's main ship, Basestar
 * Spring 2012
 * @author Scott Gunther and David Gay
 */

   public class ShipBasestar extends Ship implements BattleConstants
   {
   
    /**
     * ShipBasestar constructor
     * @param n the name of the ship
     * @param p the position of the ship
     */
      public ShipBasestar(String n, int p)
      {
         super();
         this.setName(n);
         this.setType(C_BASESTAR);
         this.setHits(5);
         this.setPosition(p);
         this.setMoveRange(1);
         this.setAttackRange(3);
         this.setWeaponDamage(2);
      
         this.setIcon(new ImageIcon("images/basestar.png"));
      }
   }

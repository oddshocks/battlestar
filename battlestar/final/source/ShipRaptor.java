   import javax.swing.ImageIcon;

/**
 * The human heavy ship
 * Spring 2012
 * @author Scott Gunther and David Gay
 */


   public class ShipRaptor extends Ship implements BattleConstants
   {
    /**
     * ShipRaptop constructor
     * @param n the name of the ship
     * @param p the position of the ship
     */
      public ShipRaptor(String n, int p)
      {
         super();
         this.setName(n);
         this.setType(H_RAPTOR);
         this.setHits(3);
         this.setPosition(p);
         this.setMoveRange(1);
         this.setAttackRange(2);
         this.setWeaponDamage(1);
        
         this.setIcon(new ImageIcon("images/raptor.png"));
      }
   }

   import javax.swing.ImageIcon;

/**
 * The Human fighter
 * Spring 2012
 * @author Scott Gunther and David Gay
 */



   public class ShipViper extends Ship implements BattleConstants
   {
    /**
     * ShipViper constructor
     * @param n the name of the Viper
     * @param p the position of the ship
     */
      public ShipViper(String n, int p)
      {
         super();
         this.setName(n);
         this.setType(H_VIPER);
         this.setHits(1);
         this.setPosition(p);
         this.setMoveRange(2);
         this.setAttackRange(2);
         this.setWeaponDamage(1);
        
         this.setIcon(new ImageIcon("images/viper.jpg"));
      }
   }

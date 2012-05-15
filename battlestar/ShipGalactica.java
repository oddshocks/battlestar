/**
 * The human's main ship, Galactica
 * Author: David Gay
 * Spring 2012
 */

import javax.swing.ImageIcon;

public class ShipGalactica extends Ship implements BattleConstants
{
    /**
     * ShipGalactica constructor
     * @param n the name of the ship
     * @param p the position of the ship
     */
    public ShipGalactica(String n, int p)
    {
        super();
        this.setName(n);
        this.setType(H_GALACTICA);
        this.setHits(5);
        this.setPosition(p);
		  this.setMoveRange(1);
		  this.setAttackRange(3);
		  this.setWeaponDamage(2);
		  
		  this.setIcon(new ImageIcon("images/galactica.jpg"));
    }
}

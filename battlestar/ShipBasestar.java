/**
 * The Cylon's main ship, Basestar
 * Author: David Gay
 * Spring 2012
 */

import javax.swing.ImageIcon;

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

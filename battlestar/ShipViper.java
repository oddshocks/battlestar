/**
 * The Human fighter
 * Author: David Gay
 * Spring 2012
 */

import javax.swing.ImageIcon;

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
    }
}

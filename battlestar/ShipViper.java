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
     */
    public ShipViper(String n)
    {
        super();
        this.setName(n);
        this.setType(H_VIPER);
        this.setHits(1);
    }
}

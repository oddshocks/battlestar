/**
 * The Cylon fighter
 * Author: David Gay
 * Spring 2012
 */

import javax.swing.ImageIcon;

public class ShipRaider extends Ship implements BattleConstants
{
    /**
     * ShipRaider constructor
     * @param n the name of the Raider
     */
    public ShipRaider(String n)
    {
        super();
        this.setName(n);
        this.setType(C_RAIDER);
        this.setHits(1);
    }
}

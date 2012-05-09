/**
 * The Cylon special ship
 * Author: David Gay
 * Spring 2012
 */

import javax.swing.ImageIcon;

public class ShipHeavyRaider extends Ship implements BattleConstants
{
    /**
     * ShipHeavyRaider constructor
     * @param n the name of the Heavy Raider
     */
    public ShipHeavyRaider(String n)
    {
        super();
        this.setName(n);
        this.setType(C_HEAVY_RAIDER);
        this.setHits(3);
    }
}

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
     * @param p the position of the ship
     */
    public ShipHeavyRaider(String n, int p)
    {
        super();
        this.setName(n);
        this.setType(C_HEAVY_RAIDER);
        this.setHits(3);
        this.setPosition(p);
    }
}

/**
 * The human heavy ship
 * Author: David Gay
 * Spring 2012
 */

import javax.swing.ImageIcon;

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
    }
}

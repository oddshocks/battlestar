/**
 * The Cylon's main ship, Basestar
 * Author: David Gay
 * Spring 2012
 */

public class ShipBasestar extends Ship implements BattleConstants
{
    /**
     * ShipBasestar constructor
     */
    public ShipBasestar()
    {
        super();
        this.setName("Basestar");
        this.setType(C_BASESTAR);
        this.setHits(3);
    }
}

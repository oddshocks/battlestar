/**
 * The human's main ship, Galactica
 * Author: David Gay
 * Spring 2012
 */

public class ShipGalactica extends Ship implements BattleConstants
{
    /**
     * ShipGalactica constructor
     */
    public ShipGalactica()
    {
        super();
        this.setName("Galactica");
        this.setType(H_GALACTICA);
        this.setHits(3);
    }
}

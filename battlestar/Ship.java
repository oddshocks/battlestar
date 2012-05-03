/**
 * A ship class for Battlestar
 * All ships extend this class.
 * Author: David Gay
 * Spring 2012
 */

public class Ship extends ViewObject
{
    private String name; // name of ship (randomly generate later)
    private String type; // type of ship (a constant)
    private int hits; // number of hits the ship can take

    /**
     * Ship constructor
     */
    public Ship()
    {
        name = "Shippy";
        type = null;
        hits = 1;
    }

    /**
     * Set the ship's name
     * @param n the name
     */
    public void setName(String n)
    {
        name = n;
    }
   
    /**
     * Set the ship's type
     * @param t the type
     */
    public void setType(String t)
    {
        type = t;
    }

    /**
     * Set the number of hits
     * @param h the hits
     */
    public void setHits(int h)
    {
        hits = h;
    }

    /**
     * Take damage
     * @param d the damage taken
     */
    public void takeDamage(int d)
    {
        hits -= d;
    }

    /**
     * Return the type of the ship
     * @return the ship's type
     */
    public String type()
    {
        return type;
    }

    /**
     * Return the ship's name
     * @return the ship's name
     */
    public String name()
    {
        return name;
    }

    /**
     * Return the number of hits the ship has left
     * @return hits
     */
    public int hits()
    {
        return hits;
    }
}

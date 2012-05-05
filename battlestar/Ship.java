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
    private int position; // the position of the ship
    
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
     * Ship constructor (parameterized)
     * @param _name the ship's name
     * @param _type the type of the ship (a constant)
     * @param _hits the number of hits the ship can take
     * @param _position the position of the ship
     */
    public Ship(String _name, String _type, int _hits, int _position)
    {
        name = _name;
        type = _type;
        hits = _hits;
        position = _position;
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

    /**
     * Sets the position of the ship
     * @param _position the position
     */
    public void setPosition(int _position)
    {
        position = _position;
    }

    /**
     * Get the ship's position
     * @return the position of the ship
     */
    public int position()
    {
        return position;
    }

    /**
     * Returns the Ship object as a string
     * @return ship information in String format
     */
    public String toString()
    {
        return String.format("%s,%s,%d,%d", name, type, hits, position);
    }
}

/**
 * A ship class for Battlestar
 * All ships extend this class.
 * Author: David Gay
 * Spring 2012
 */

   import javax.swing.ImageIcon;

   public class Ship extends ViewObject
   {
      private String name; // name of ship (randomly generate later)
      private String type; // type of ship (a constant)
      private int hits; // number of hits the ship can take
      private int position; // the position of the ship
      private int moveRange;
      private int attackRange;
      private int weaponDamage;
      private ImageIcon icon; // the ship's icon
    
    /**
     * Ship constructor
     */
      public Ship()
      {
         name = "Shippy";
         type = null;
         hits = 1;
         position = -1;
         icon = null;
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
		 * Return the ships move range
		 * @return the move range
		 **/
      public int getMoveRange()
      {
         return moveRange;
      }
		
		/**
		 * Set the ships move range
		 * @param _moveRange the ships move range
		 **/
		public void setMoveRange(int _moveRange)
		{
			moveRange = _moveRange;
		}
		
		/**
		 * Return the ship's attack range
		 * @return the attackRange of the ship
		 **/
		public int getAttackRange()
		{
			return attackRange;
		}
		
		/**
		 * Set the ships attack range
		 * @param _attackRange the ships attackRange
		 **/
		public void setAttackRange(int _attackRange)
		{
			attackRange = _attackRange;
		}
		
		/**
		 * Return the ship's weapon damage
		 * @return the weaponDamage of the ship
		 **/
		public int getWeaponDamage()
		{
			return weaponDamage;
		}
   	
		/**
		 * Set the ships weapon damage
		 * @param _weaponDamage the ships weaponDamage
		 **/
		public void setWeaponDamage(int _weaponDamage)
		{
			weaponDamage = _weaponDamage;
		}
    /**
     * Returns the ship's icon
     * @return the ship's ImageIcon
     */
      public ImageIcon icon()
      {
         return icon;
      }
    
    /**
     * Sets the ship's icon
     * @param _icon the ship's ImageIcon
    **/
      public void setIcon(ImageIcon _icon)
      {
         icon = _icon;
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

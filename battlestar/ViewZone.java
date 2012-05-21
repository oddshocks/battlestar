/**
 * ViewZone
 * An area on the ViewPanel that a ViewObject can be
 * Author: David Gay
 * Spring 2012
 */

   import java.awt.*;
   import java.awt.event.*;
   import java.util.Timer.*;
   import javax.swing.*;
   import javax.swing.border.*;

   public class ViewZone extends JButton
   {
      private Ship ship; // ship currently in location
      private StatPanel panelStat;
   
    /**
     * Constructor
     * @param sp the StatPanel from the client
     */
      public ViewZone(StatPanel sp)
      {
         super();

        panelStat = sp;

         this.setForeground(Color.WHITE);
         this.setBackground(Color.BLACK);
         Border line = new LineBorder(Color.BLUE);
         Border margin = new EmptyBorder(5, 15, 5, 15);
         Border compound = new CompoundBorder(line, margin);
         this.setBorder(compound);
      
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

         ship = null;
      }
   
    /**
     * Returns previously selected zone to normal
     */
    public void unselected()
    {
        this.setForeground(Color.WHITE);
    }

    /**
     * Changes foreground of selected zone
     */
    public void selected()
    {
        this.setForeground(Color.GREEN);
    }

    /**
     * Makes attacked zone flash
     */
    public void hit()
    {
        // Make the zone flash red and white with a timer 
    }

    /**
     * Return the ship in the location
     * @return the ship
     */
      public Ship ship()
      {
         return ship;
      }
   
    /**
     * Set the ship in the location
     * @param ship the ship
     */
      public void setShip(Ship _ship)
      {
         ship = _ship;
         this.setIcon(ship.icon());
      }
		
		public void clearShip()
		{
			ship = null;
			this.setIcon(null);
		}
   }

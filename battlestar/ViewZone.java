   import java.awt.*;
   import java.awt.event.*;
   import java.util.Timer.*;
   import javax.swing.*;
   import javax.swing.border.*;

/**
 * ViewZone
 * An area on the ViewPanel that a ViewObject can be
 * Author: David Gay
 * Spring 2012
 */

   
   public class ViewZone extends JButton
   {
   	/** This ship on this ViewZone **/
      private Ship ship;
   
    /**
     * Constructor
     * @param sp the StatPanel from the client
     */
      public ViewZone(StatPanel sp)
      {
         super();
      
         this.setForeground(Color.WHITE);
         this.setBackground(Color.BLACK);
         Border line = new LineBorder(Color.BLUE);
         Border margin = new EmptyBorder(5, 15, 5, 15);
         Border compound = new CompoundBorder(line, margin);
         this.setBorder(compound);
      
         ship = null;
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
     * @param _ship the ship
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

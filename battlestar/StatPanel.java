   import java.awt.*;
   import java.awt.event.*;
   import java.io.*;
   import java.text.*;
   import java.util.*;
   import javax.swing.*;

/**
 * StatPanel
 * The stat panel for Battlestar
 * Spring 2012
 * @author Scott Gunther and David Gay
 */


   public class StatPanel extends JPanel implements BattleConstants
   {
   	/** The ship to be displayed on the stat panel */
      private Ship selectedShip;
    
    	/** The text area where the ship information is displayed */
      private JTextArea taStats; 
   
    /**
     * StatPanel constructor
     */
      public StatPanel()
      {
         super();
        
         this.setLayout(new GridLayout(0, 1));
        
         this.add(new JLabel("StatPanel"));
      
         selectedShip = null;
      
         taStats = new JTextArea();
         taStats.setFont(FONT_STAT);
         taStats.setEditable(false);
      
         this.add(taStats);
      }
   
    /**
     * Update the StatPanel
     */
      public void update(ViewZone aZone)
      {
         if(aZone.ship() != null)
         {
         
            selectedShip = aZone.ship();
            taStats.setText(
               "\nSELECTED SHIP STATS\n"
               + "\n" + " Type - " + selectedShip.type()
               + "\n" + " Hits - " + selectedShip.hits());
         }
      }
   }

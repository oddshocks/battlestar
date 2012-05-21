/**
 * StatPanel
 * The stat panel for Battlestar
 * Author: David Gay
 * Spring 2012
 */

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

public class StatPanel extends JPanel implements BattleConstants
{
      private javax.swing.Timer timer; // For game time
      private JLabel lblTimer; // Label for the timer
      private int secs; // integer for seconds
      private int mins; // integer for minutes
      private int hrs; // integer for hours
		
      private int matchID; // integer to keep track of games played
      private String turn; // String to keep track of turns
   
   	  
   	  private JButton btnShip = new JButton();
   	  
   	  
      private Ship selectedShip; // instantiate a ship instance as selected ship
    
      private JTextArea taStats; // text area where stats are displayed

    /**
     * StatPanel constructor
     */
    public StatPanel()
    {
        super();
        
        this.setLayout(new GridLayout(2, 2));
        
        lblTimer = new JLabel();

        selectedShip = null;

        taStats = new JTextArea();
        taStats.setFont(FONT_STAT);
        taStats.setEditable(false);
		 
          JPanel bPanel = new JPanel();
		  bPanel.setLayout(new GridLayout(4,0));
		  JButton btnID = new JButton(ICON_ID);
		  JButton btnTurn = new JButton(ICON_TURN);
		  JButton btnType = new JButton(ICON_TYPE);
		  JButton btnAid = new JButton(ICON_AID);
		  bPanel.add(btnID);
		  bPanel.add(btnTurn);
		  bPanel.add(btnType);
		  bPanel.add(btnAid);
		  this.add(bPanel); 
		  this.add(taStats);
          this.add(lblTimer);
		  this.add(btnShip);

        /**
         * Internal Timer class
         */
        timer = new javax.swing.Timer(1000, new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    secs++;

                    if (secs == 60)
                    {
                        mins++;
                        secs = 0;
                    }

                    if (mins == 60)
                    {
                        hrs++;
                        mins = 0;
                    }

                    if (hrs == 24)
                    {
                        hrs = 0;
                        mins = 0;
                        secs = 0;
                    }

                    lblTimer.setText(String.format("Game Time - %2d:%2d:%2d",
                        hrs, mins, secs));
                }
            });
    }

    /**
     * Start the game clock
     */
    public void startClock()
    {
        timer.start();
    }

    /**
     * Stop the game clock
     */
    public void stopClock()
    {
        timer.stop();
    }
	
    /**
     * Update the StatPanel
     * @param vz the ViewZone to update from
     */
    public void update(ViewZone vz)
    {
        if (vz.ship() != null)
        {
            selectedShip = vz.ship();
            this.remove(btnShip);
            btnShip = new JButton(selectedShip.icon());
            taStats.setText("Statistics\n==========\n"
                + "Match ID - " + matchID + "\n\n"
                + "Turn - " + turn + "\n"
                + "\n-------------------------\n"
                + "\nSELECTED SHIP STATS\n"
                + "Name - " + selectedShip.name()
                + "\nType - " + selectedShip.type()
                + "\nHits - " + selectedShip.hits());
            this.add(btnShip);
        }
    }

    /**
     * Sets the turn count
     * @param _turn the turn count
     */
    public void setTurn(String _turn)
    {
        turn = _turn;
    }
    
    /**
     * Sets the currently selected ship
     * @param s the Ship
     */
    public void setShip(Ship s)
    {
        selectedShip = s;
    }

    /**
     * Sets the match ID
     * @param id the id
     */
    public void setID(int id)
    {
        matchID = id;
    }
}

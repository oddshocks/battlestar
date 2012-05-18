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
    private JLabel lblTimer;
    private int secs;
    private int mins;
    private int hrs;

    private int matchID;
    private int turn;

    private Ship selectedShip;
    
    private JTextArea taStats; // text area where stats are displayed

    /**
     * StatPanel constructor
     */
    public StatPanel()
    {
        super();
        
        this.setLayout(new GridLayout(0, 1));
        
        this.add(new JLabel("StatPanel"));
        
        lblTimer = new JLabel();

        selectedShip = null;

        taStats = new JTextArea();
        taStats.setFont(FONT_STAT);
        taStats.setEditable(false);
        
        this.update();

        this.add(taStats);
        this.add(lblTimer);

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
     * Update the StatPanel
     */
    public void update()
    {
        if (selectedShip != null)
        {
            taStats.setText("Statistics\n==========\n"
                + ICON_ID + " Match ID - " + matchID + "\n"
                + ICON_TURN + " Turn - " + turn + "\n"
                + "\n-------------------------\n"
                + "\nSELECTED SHIP STATS\n"
                + selectedShip.name()
                + "\n" + ICON_TYPE + " Type - " + selectedShip.type()
                + "\n" + ICON_AID + " Hits - " + selectedShip.hits()
                + selectedShip.icon());
        }
    }

    /**
     * Increments the turn count
     */
    public void incTurn()
    {
        turn++;
        this.update();
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

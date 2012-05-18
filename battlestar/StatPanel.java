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
    private Timer timer; // For game time
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
        taStats.setEditable(false);
        
        this.update();

        this.add(taStats);
        this.add(lblTimer);

        /**
         * Internal Timer class
         */
        timer = new Timer(1000, new ActionListener()
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

                    this.setText(String.format("Game Time - %2d:%2d:%2d",
                        hrs, mins, secs));
                }
            });
    }

    /**
     * Update the StatPanel
     */
    public void update()
    {
        taStats.setText("Statistics\n==========\n"
            + "Match ID - " + matchID + "\n"
            + "Turn - " + turn + "\n"
            + "\n-------------------------\n"
            + "\nSELECTED SHIP STATS\n"
            + selectedShip.name()
            + "\nType - " + selectedShip.type()
            + "\nHits - " + selectedShip.hits());
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
}

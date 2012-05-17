/**
 * ChatPanel
 * The chat panel for Battlestar
 * Author: David Gay
 * Spring 2012
 */

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class ChatPanel extends JPanel implements BattleConstants
{
    JTextArea ta;
    JScrollPane sp;

    /**
     * ChatPanel constructor
     */
    public ChatPanel()
    {
        super();
        this.setLayout(new BorderLayout());
        
        ta = new JTextArea(20, 35);
        ta.setEditable(false);
        ta.setBackground(BACKGROUND_COLOR);
        ta.setForeground(FONT_COLOR);
        ta.setFont(FONT_MAIN);

        sp = new JScrollPane(ta);
        this.add(sp);  
    }

    // Yigit also added a parameterized constructor to accept a race,
    // probably so the panel's color could change based on race.
    // We should probably do that a different way (if at all), since it doesn't
    // really make sense that the ChatPanel has a String race varaible.

    /**
     * Appends a message to the text field
     * @param msg the message to append
     */
    public void print(String msg)
    {
        ta.append(msg + "\n");
    }
}

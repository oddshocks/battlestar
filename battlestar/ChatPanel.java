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
    JTextArea ta; // instantiate a text area chat display
    JScrollPane sp; // instantiate a scroll pane for the text area 

    /**
     * ChatPanel default constructor
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

    /**
     * Appends a message to the text field
     * @param msg the message to append
     */
    public void print(String msg)
    {
        ta.append(msg + "\n");
    }
}

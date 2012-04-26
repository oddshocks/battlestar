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
        this.setLayout(new GridLayout(1, 1));
        ta = new JTextArea();
        ta.setEditable(false);
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

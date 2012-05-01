/**
 * ViewZone
 * An area on the ViewPanel that a ViewObject can be
 * Author: David Gay
 * Spring 2012
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class ViewZone extends JButton
{
    /**
     * Constructor
     */
    public ViewZone()
    {
        super();
        this.setForeground(Color.WHITE);
        this.setBackground(Color.BLACK);
        Border line = new LineBorder(Color.BLUE);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        this.setBorder(compound);
    }
}

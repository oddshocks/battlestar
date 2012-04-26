/**
 * ControlPanel
 * The control panel for Battlestar
 * Author: David Gay
 * Spring 2012
 */

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class ControlPanel extends JPanel implements BattleConstants
{
    /**
     * ControlPanel constructor
     */
    public ControlPanel()
    {
        super();
        this.add(new JLabel("Control panel"));
    }
}

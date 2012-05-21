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
     * ControlPanel default constructor
	 * Creates a new Label for control panel
     */
    public ControlPanel()
    {
        super();
        this.add(new JLabel("Control panel"));
    }
}

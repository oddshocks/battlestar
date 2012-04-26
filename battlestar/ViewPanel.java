/**
 * ViewPanel
 * The view panel for Battlestar
 * Author: David Gay
 * Spring 2012
 */

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class ViewPanel extends JPanel implements BattleConstants
{
    Vector<ViewZone> zones; // zones ViewObjects can be in

    /**
     * ViewPanel constructor
     */
    public ViewPanel()
    {
        super();
        this.setLayout(new GridLayout(VIEW_SIZE, VIEW_SIZE));

        zones = new Vector<ViewZone>();

        for (int i = 0; i < (VIEW_SIZE * VIEW_SIZE); i++)
        {
            zones.add(new ViewZone());
            this.add(zones.get(i));
        }
    }
}

/**
 * ViewObject
 * An object that can be placed on the ViewPanel
 * Author: David Gay
 * Spring 2012
 */

import java.awt.*;
import javax.swing.*;

public class ViewObject
{
    private ImageIcon icon;

    /**
     * Returns the object's icon
     * @return the image icon representing the object
     */
    public ImageIcon getIcon()
    {
        return icon;
    }

    /**
     * Sets the object's icon
     * @param i the icon
     */
    public void setIcon(ImageIcon i)
    {
        icon = i;
    }
}

   import java.awt.event.*;
   import java.awt.*;
   import java.util.*;
   import javax.swing.*;
	
/**
 * ViewPanel
 * The view panel for Battlestar
 * Spring 2012
 * @author Scott Gunther and David Gay
 */



   public class ViewPanel extends JPanel implements BattleConstants
   {
   	/** Holds the ViewZones that make up the board **/
      Vector<ViewZone> zones;
   	
   	/** The ViewZone that has been selected by the user **/
      ViewZone selectedZone = null;
   	
   	/** The ViewZone that has been selected by the user after taking an action **/
      ViewZone actionZone = null;
   	
   	/** The client that this panel is a part of **/
      BattleClient client = null;
    
    	/** Indicated when the ship is moving **/
      private boolean moving; 
   	
   	/** Indicates when the ship is attacking **/
      private boolean attacking;
    
    /**
     * ViewPanel constructor
     */
      public ViewPanel(BattleClient _client)
      {
         super();
      	
         client  = _client;
         this.setLayout(new GridLayout(VIEW_SIZE, VIEW_SIZE));
      
         zones = new Vector<ViewZone>();
        
         ActionListener buttonListener = 
            new ActionListener()
            {
               public void actionPerformed(ActionEvent e)
               {
               	//Select a zone to move or attack with if the client is not moving or attacking
                  if(!attacking && !moving)
                  {
                     selectedZone = (ViewZone)e.getSource();
                     client.getStatPanel().update(selectedZone);
                  }
                  //Get an actionZone if the client is moving or attacking
                  else if(attacking || moving)
                  {	
                     actionZone = (ViewZone)e.getSource();
                     if(moving)
                     {
                        client.command(client.getPW(), C_MOVE, zones.indexOf(selectedZone) + "," + zones.indexOf(actionZone));
                        setMoving(false);
                     }
                     if(attacking)
                     {
                        client.command(client.getPW(), C_ATTACK, zones.indexOf(selectedZone) + "," + zones.indexOf(actionZone));
                        setAttacking(false);
                     }
                  }
               }
            };
      
         for (int i = 0; i < (VIEW_SIZE * VIEW_SIZE); i++)
         {
            ViewZone newZone = new ViewZone(client.getStatPanel());
            newZone.addActionListener(buttonListener);
            zones.add(newZone);
            this.add(zones.get(i));
         }
      }
   
    /**
     * Set ship in ViewZone
     */
      public void setShip(int position, Ship ship)
      {
         zones.get(position).setShip(ship);
      }
    
    /**
     * Set whether client is attacking
     * @param _attacking whether the client is attacking
     **/
      public void setAttacking(boolean _attacking)
      {
         attacking = _attacking;
         if(!attacking)
         {
            actionZone = null;
            selectedZone = null;
         }
      }
    
    /**
     * Set whether the client is moving 
     * @param _moving whether the client is moving
     **/
      public void setMoving(boolean _moving)
      {
         moving = _moving;
         if(!moving)
         {
            actionZone = null;
            selectedZone = null;
         }
      }
   	
     /**
      * Get a ViewZone from the panel
   	* @param i the position of the ViewZone
   	* @return the sepcified ViewZone
     **/
      public ViewZone getZone(int i)
      {
         return zones.get(i);
      }
    
     /**
      * Get the selected ViewZone
   	* @return the selectedZone
   	**/
      public ViewZone getSelectedZone()
      {
         return selectedZone;
      }
    
     /**
      * Get the action zone
   	* @return the actionZone
   	**/
      public ViewZone getActionZone()
      {
         return actionZone;	
      }
   }

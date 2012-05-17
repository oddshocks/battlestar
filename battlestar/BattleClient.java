/**
 * Battlestar Client
 * For RIT's 4002-219 course
 * Author: David Gay and Scott Gunther
 * Spring 2012
 */

   import java.awt.*;
   import java.awt.event.*;
   import java.io.*;
   import java.net.*;
   import java.util.*;
   import javax.swing.*;

   public class BattleClient extends JFrame implements BattleConstants
   {
        ViewPanel panelView;
      StatPanel panelStat;
      ControlPanel panelControl;
      ChatPanel panelChat;
      JButton btnMove, btnAttack;
   	
   
      String handle; // client identifier
   
      final StatusBar statusBar;
      final JTextField tfInput;
   	
      PrintWriter pw = null;
      BufferedReader br = null;
  
        // Why in God's name are there two boolean varaibles
        // There are two races
        // What
        // And what the hell happened with indentation?
      private boolean human; //Is this client a human?
      private boolean cylon; //Is this client a cylon? 
      private boolean myTurn; //Is it my turn?
   
      boolean reading; // are we reading from the server?
   
    /**
     * Constructor
     * @param ip the IP address of the server
     */
      public BattleClient(String ip)
      {
        // Default attribute values
         reading = true;
         handle = "Player";
      
        // Start setting up the GUI
         this.setTitle("Battlestar");
         this.setLocation(200, 200);
         this.setSize(1024, 800);
         this.setLayout(new BorderLayout());
      
        // Menu bar
         JMenuBar menuBar = new JMenuBar();
         JMenu menuGame = new JMenu("Game");
         menuGame.setMnemonic('G');
         JMenuItem miExit = new JMenuItem("Exit");
         miExit.setMnemonic('E');
         miExit.addActionListener(
               new ActionListener()
               {
                  public void actionPerformed(ActionEvent ae)
                  {
                     quit();
                  }
               });
         menuGame.add(miExit);
         JMenu menuHelp = new JMenu("Help");
         menuHelp.setMnemonic('H');
         JMenuItem miAbout = new JMenuItem("About");
         miAbout.setMnemonic('A');
         miAbout.addActionListener(
               new ActionListener()
               {
                  public void actionPerformed(ActionEvent ae)
                  {
                     JOptionPane.showMessageDialog(null,
                        "Battlestar" +
                        "\nSome dudes" +
                        "\nFor RIT 4002-219" +
                        "\nSpring 2012",
                        "About Battlestar",
                        JOptionPane.INFORMATION_MESSAGE);
                  }
               });
         menuHelp.add(miAbout);
         menuBar.add(menuGame);
         menuBar.add(menuHelp);
      
         this.setJMenuBar(menuBar);
        
        // View panel
         panelView = new ViewPanel(this);
         this.add(panelView, BorderLayout.CENTER);
      
        // East panel (stats and chat)
         JPanel panelEast = new JPanel(new GridLayout(2, 1));
         panelStat = new StatPanel();
         panelEast.add(panelStat);
         panelChat = new ChatPanel();
         panelEast.add(panelChat);
         this.add(panelEast, BorderLayout.EAST);
      
        // Footer panel (control, input field, status bar)
         JPanel panelFooter = new JPanel();
         panelFooter.setLayout(new GridLayout(3, 1));
         panelControl = new ControlPanel();
         panelFooter.add(panelControl);
         tfInput = new JTextField();
         panelFooter.add(tfInput);
         statusBar = new StatusBar();
         panelFooter.add(statusBar);
         this.add(panelFooter, BorderLayout.SOUTH);
      
        // Finalize the window
         tfInput.requestFocusInWindow();
         statusBar.setMessage("Program started!");
         this.setVisible(true);
      	
        // Control panel buttons
         btnMove = new JButton("Move");
         btnAttack = new JButton("Attack");
         ActionListener buttonListener = 
            new ActionListener()
            {
               public void actionPerformed(ActionEvent e)
               {
                  if(e.getSource() == btnMove)
                  {
                     sendMoveCommand();
                  }
                  if(e.getSource() == btnAttack)
                  {
                     sendAttackCommand();
                  }
               }
            };
         btnMove.addActionListener(buttonListener);
         btnAttack.addActionListener(buttonListener);
			panelControl.add(btnMove);
			panelControl.add(btnAttack);

        /// START SERVER CONNECTION CODE ///
        
         try
         {
            // Connect with server
            final Socket s = new Socket(ip, GAME_PORT);
            
            // Open server I/O
            br = new BufferedReader(
                new InputStreamReader(s.getInputStream()));
            pw = new PrintWriter(
                new OutputStreamWriter(s.getOutputStream()));
         
            /// SERVER COMMAND HANDLING ///
         	
            Thread serverRead = new ServerRead();
            serverRead.start();

            /// END SERVER COMMAND HANDLING ///
            
            // Close connections if the client is closed
            this.addWindowListener(
                  new WindowAdapter()
                  {
                     public void windowClosing(WindowEvent we)
                     {
                        try
                        {
                            // Tell the server that we're quitting
                           pw.println(C_QUIT);
                           pw.flush();
                        
                            // Stop reading
                           reading = false;
                        
                            // Close connections
                           br.close();
                           pw.close();
                           s.close();
                        }
                           catch (IOException ioex)
                           {
                            // Print server error message
                              ioex.printStackTrace();
                           }
                        finally
                        {
                            // Actually quit
                           System.exit(0);
                        }
                     }
                  });
         
            /// CLIENT COMMAND HANDLING ///
         
            // Send command with ENTER button
            tfInput.addKeyListener(
                  new KeyAdapter()
                  {
                     public void keyPressed(KeyEvent ke)
                     {
                        if (ke.getKeyCode() == KeyEvent.VK_ENTER
                            && !tfInput.getText().equals(""))
                        {
                           String input = tfInput.getText();
                           String cmd; // the command
                           String arg; // the argument string
                        
                           if (input.charAt(0) != '/') // Chat message entered
                           {
                              cmd = C_CHAT;
                              arg = input;
                           }
                           else // Command entered
                           {
                              String[] msg = input.split(" ", 2);
                              cmd = msg[0];
                              arg = null;
                              if (msg.length > 1)
                              {
                                 arg = msg[1];
                              }
                           }
                        
                            // Handle different commands...
                        
                           if (cmd.equals(C_QUIT)) // Quit
                           {
                              quit();
                           }
                           else if (cmd.equals(C_CHAT)) // Chat
                           {
                              command(pw, C_CHAT, "<" + handle + "> "
                                    + arg);
                           }
                           else if (cmd.equals(C_READY)) // Ready
                           {
                              command(pw, C_READY, null);
                           }
                           else if (cmd.equals(C_HUMAN)) //Request to be human
                           {
                              command(pw, C_HUMAN, null);
                           }
                           else if (cmd.equals(C_CYLON)) //Request to be cylon
                           {
                              command(pw, C_CYLON, null);
                           }
                           else if (cmd.equals(C_MOVE)) //Move a ship
                           {
                              sendMoveCommand();                 
                           }
                           else if(cmd.equals(C_ATTACK)) //Attack a ship
                           {
										sendAttackCommand();
                           }
                            // Clear the input text field
                           tfInput.setText("");
                        }
                     }
                  });
						
						//Select race
						JOptionPane chooseRace = new JOptionPane();
						String response  = (String)chooseRace.showInputDialog(BattleClient.this,
														"Choose your race", "Choose Race",
														JOptionPane.PLAIN_MESSAGE,
														null, RACES, RACES[0]);
						
						if(response != null)
						{
							if(response.equals("Cylon"))
							{
								command(pw, C_CYLON, null);
							}
							if(response.equals("Human"))
							{
								command(pw, C_HUMAN, null);
							}
						}

						//Ready up
						JOptionPane.showMessageDialog(BattleClient.this, "Click OK when you are ready",
																"Ready Up", JOptionPane.PLAIN_MESSAGE);
						command(pw, C_READY, null);
													
         
            /// END CLIENT COMMAND HANDLING ///
         }
            catch (UnknownHostException uhex)
            {
            // Print invalid IP message
            }
            catch (IOException ioex)
            {
            // Print I/O error message
               ioex.printStackTrace();
            }
      
        /// END SERVER CONNECTION CODE ///
      }
   
    /**
     * StatPanel accessor
     * @return the StatPanel
     */
    public StatPanel getStatPanel()
    {
        return panelStat;
    }

    /**
     * Send a command to the server
     * @param pw the PrintWriter to send the command to
     * @param cmd the command
     * @param arg the argument
     */
      public void command(PrintWriter pw, String cmd, String arg)
      {
         pw.println(cmd);
         if (arg != null)
            pw.println(arg);
         pw.flush();
      }
   	
   	   	
   	
   
    /**
     * Program entry point
     * @param args the program arguments (none taken)
     */
      public static void main (String[] args)
      {
         try
         {
            BattleClient client = new BattleClient(args[0]);
         }
            catch (ArrayIndexOutOfBoundsException aioobe)
            {
               System.out.println("Syntax is: java Battlestar $server-ip");
               System.exit(0);
            }
            catch (Exception ex)
            {
               System.out.println("Error!");
               ex.printStackTrace();
            }
      
      }
   
    /**
     * Run through program quit operations, then quit
     */
      public void quit()
      {
         statusBar.setMessage("Quitting...");
         this.processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
      }
   	
		/**
		 * This method validates it is the client's turn,
		 * the selected ship belongs to the clietn, and 
		 * the selected button is not null. Then it sends
		 * and attack command to the server with two positions
		 **/
      public void sendAttackCommand()
      {
			//Validate turn
         if(!myTurn)
         {
            statusBar.setMessage("It is not your turn");	
         }
         else
         {
				//Make sure the zone is not null
            if(panelView.getSelectedZone() == null)
            {
               statusBar.setMessage("You must select a ship to move");
            }
            else
            {
					//Get the type of ship
				   String shipType = panelView.getSelectedZone().ship().type();
					
					//Validate the ship belongs to the client
               if(cylon && (shipType.equals(H_VIPER) ||
                                    				shipType.equals(H_RAPTOR) ||
                                    				shipType.equals(H_GALACTICA)))
               {
                  statusBar.setMessage("That is not your ship, you are a cylon");
               }
               else if(human && (shipType.equals(C_RAIDER) ||
                                    				shipType.equals(C_HEAVY_RAIDER) ||
                                    				shipType.equals(C_BASESTAR)))
               {
                  statusBar.setMessage("That is not your ship, you are a human");
               }
               else
               {
						//Allow the player to attack
                  panelView.setAttacking(true);
               }
            }
         }
      }
   	
		/**
		 * This method validates that it is the client's turn, 
		 * the selected ship belongs to the client, and the selected JButton
		 * is not null then sends a move command with two position
		 * to the server to validate
		 **/
      public void sendMoveCommand()
      {
			//Validate turn
         if(!myTurn)
         {
            statusBar.setMessage("It is not your turn");
         }
         else
         {
				//Make sure the zone clicked has a ship
            if(panelView.getSelectedZone().ship() == null)
            {
               statusBar.setMessage("You must select a ship to move");
            }
            else
            {
					//Get the type of ship
               String shipType = panelView.getSelectedZone().ship().type();
					
					//Validate the ship belongs to the client
               if(cylon && (shipType.equals(H_VIPER) ||
               											shipType.equals(H_RAPTOR) ||
               											shipType.equals(H_GALACTICA)))
               {
                  statusBar.setMessage("That is not your ship, you are a cylon");
               }
               else if(human && (shipType.equals(C_RAIDER) ||
               											shipType.equals(C_HEAVY_RAIDER) ||
               											shipType.equals(C_BASESTAR)))
               {
                  statusBar.setMessage("That is not your ship, you are a human");
               }
               else
               {
						//Allow the player to move
                  panelView.setMoving(true);
               }
            }
         }
      
      }
   
    /**
     * StatusBar inner class
     */
      class StatusBar extends JPanel
      {
         JLabel message;
      
         public StatusBar()
         {
            super();
            this.setLayout(new GridLayout(1, 3)); // three areas for msgs
         
            message = new JLabel();
            this.add(message);
         }
      
        /**
         * Sets the message
         * @param msg the message to set
         */
         public void setMessage(String msg)
         {
            message.setText(msg);
         }
      }
   	
		/**
		 * Gets the printwriter of the client
		 * @return the printwriter
		 **/
      public PrintWriter getPW()
      {
         return pw;
      }
   	
		/**
		 * Thread to continiously read commands in from the server
		 *
		 **/
      class ServerRead extends Thread
      {
         public void run()
         {
            while (reading)
            {
               try
               {
                  String input = br.readLine();
                  if (input.equals(S_STOP))
                  {
                     System.out.println("Got STOP command"); // DEBUG
                     statusBar.setMessage("Bye!");
                     break; // we're done here
                  }
                  else if (input.equals(S_MSG))
                  {
                     System.out.println("Got MSG command"); // DEBUG
                     input = br.readLine();
                     panelChat.print(input);
                  }
                  else if (input.equals(S_GO))
                  {
                     System.out.println("Got GO command"); // DEBUG
                     panelChat.print("The battle has begun!");
                                // start the game
                  }
                  else if (input.equals(S_SHIP))
                  {
                     System.out.println("Got SHIP command"); // DEBUG
                     input = br.readLine();
                           
                     // 0: name, 1: type, 2: hits, 3: position
                     String[] shipAttributes = input.split(",");
                           	  
                     String name = shipAttributes[0];
                     String type = shipAttributes[1];
                     int hits = Integer.parseInt(shipAttributes[2]);
                     int position = Integer.parseInt(shipAttributes[3]);
                           	  
                     Ship newShip = new Ship();
                           	  
                     if(type.equals(H_VIPER))
                     {
                        newShip = new ShipViper(name, position);
                     }
                     else if(type.equals(H_GALACTICA))
                     {
                        newShip = new ShipGalactica(name, position);
                     }
                     if(type.equals(H_RAPTOR))
                     {
                        newShip = new ShipRaptor(name, position);
                     }
                     if(type.equals(C_BASESTAR))
                     {
                        newShip = new ShipBasestar(name, position);
                     }
                     if(type.equals(C_RAIDER))
                     {
                        newShip = new ShipRaider(name, position);
                     }
                     if(type.equals(C_HEAVY_RAIDER))
                     {
                        newShip = new ShipHeavyRaider(name, position);
                     }

                     // Send the ship to the ViewZone
                     panelView.setShip(Integer.parseInt(
                                    shipAttributes[3]), newShip);
                  }
                  else if(input.equals(S_RACE)) //Set the clients race
                  {
                     input = br.readLine();
                           		
                     if (input.equals("human"))
                     {
                        human = true;
                     }
                     else if (input.equals("cylon"))
                     {
                        cylon = true;
                     }	
                  }
                  else if(input.equals(S_TURN)) //Set the clients turn
                  {
                     input = br.readLine();
                           		
                     if(input.equals("true"))
                     {
                        myTurn = true;
                     }
                     if(input.equals("false"))
                     {
                        myTurn = false;
                     }
                  }
                  else if(input.equals(S_INVALID_MOVE)) //Cannot Move to that position
                  {
                     statusBar.setMessage("You can't move there");
                     panelView.setMoving(false);
                  }
                  else if(input.equals(S_MOVE)) //Move a ship
                  {
                     input = br.readLine();
                     statusBar.setMessage("I can move the ship now");
                     String[] positions = input.split(",");
                     int shipPosition = Integer.parseInt(positions[0]);
                     int movePosition = Integer.parseInt(positions[1]);
                  	
                  	//Move the ship to new position
                     panelView.setShip(movePosition, panelView.getZone(shipPosition).ship());
							
							//Clear previous position
                     panelView.getZone(shipPosition).clearShip();
                  	
                     panelView.setMoving(false);
                  }
                  else if(input.equals(S_DESTROY)) //Destroy a ship
                  {
                     input = br.readLine();
                     int destroyPosition = Integer.parseInt(input);
                  	
                     panelView.getZone(destroyPosition).clearShip();
                  }
                  else if(input.equals(S_ATTACK)) //Attack a ship
                  {
							//Read in position and number of hits to be set to new ship
                     input = br.readLine();
                     String[] info = input.split(",");
                     int attackPosition = Integer.parseInt(info[0]);
                     int hits = Integer.parseInt(info[1]);
                  	
							//Set the hits for the ship at the attack position
                     panelView.getZone(attackPosition).ship().setHits(hits);
                  }
               }
                  catch (NullPointerException npex)
                  {
                     panelChat.print("Server down!");
                     npex.printStackTrace();
                     break;
                  }
                  catch (IOException ioex)
                  {
                     panelChat.print("I/O error");
                     ioex.printStackTrace();
                  }
            }
         }
      }
   }

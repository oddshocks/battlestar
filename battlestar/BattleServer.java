/**
 * Battlestar Server
 * For RIT's 4002-219 course
 * Author: David Gay
 * Spring 2012
 */

   import java.io.*;
   import java.net.*;
   import java.util.*;

   public class BattleServer implements BattleConstants
   {
      private Vector<Handler> clients; // vector for client threads
		
		private Vector<Ship> cylonShips; // Cylon ships
      private Vector<Ship> humanShips; // Human ships

      private int turn = 0;
    
      private boolean gameStarted; // has the game started?
   
    /**
     * BattleServer constructor
     */
      public BattleServer()
      {
        /// START NETWORKING CODE ///
        
         ServerSocket ss = null;
         clients = new Vector<Handler>();
      
         cylonShips = new Vector<Ship>();
         humanShips = new Vector<Ship>();
      
         try
         {
            ss = new ServerSocket(GAME_PORT);
            Socket cs = null; // client socket
            
            printMessage("Battlestar server started!");
         
            // Accept client connections
            while (true)
            {
               cs = ss.accept();
               printMessage("Client connecting from " + cs.getInetAddress().getHostAddress());
               if (clients.size() < 2)
               {
                  Handler h = new Handler(cs);
                  clients.add(h);
                  printMessage("Connection successful!");
                  printMessage("Starting service to client...");
                  h.start();
                  printMessage("Services started!");
               }
               else
               {
                  printMessage("Connection failed, already 2 clients.");
               }
            }
         }
            catch (BindException bex)
            {
               printMessage("Port already in use... server may already be"
                  + " started.");
            }
            catch (IOException ioex)
            {
               printMessage("I/O error");
               ioex.printStackTrace();
            }
        /// END NETWORKING CODE ///
      }
   
    /**
     * Handler class for threaded client services
     */
      class Handler extends Thread
      {
         private Socket cs; // client socket
         private BufferedReader br;
         private PrintWriter pw;
         private boolean reading; // are we reading in?
         private int id; // client 1 or 2?
			private int opponentId;
			
        
        
      
         private boolean ready; // is this client ready to play?
         private boolean human; // is this client a human?
         private boolean cylon; // is this client a cylon?
         private boolean myTurn; //is it this clients turn?
      
        /**
         * Handler constructor
         * @param _cs client socket
         */
         public Handler(Socket _cs)
         {
            cs = null;
            br = null;
            pw = null;
            cs = _cs;
            reading = true;
         
            if (clients.size() == 0)
				{
               id = 1;
					opponentId = 1;
				}
            else
				{
               id = 2;
					opponentId = 0;
				}
					
         
            ready = false;
         }
      
        /**
         * Thread run method
         */
         public void run()
         {
            String clientMsg = null; // message sent by client
         
            try
            {
               br = new BufferedReader(
                    new InputStreamReader(
                        cs.getInputStream()));
               pw = new PrintWriter(
                    new OutputStreamWriter(
                        cs.getOutputStream()));
            
               command(S_MSG, "Connected to server.", id);
               command(S_MSG, "Client " + id + " has connected!", 0);
               printMessage("Client " + id + " has connected.");
               printMessage("Connected clients : " + clients.size());
                


               while (reading) // Continously accept input from the client
               {
                    try
                    {
                        clientMsg = br.readLine();
                    }
                    catch (SocketException sex)
                    {   
                        printMessage("Client " + id + " unexpectedly"
                            + " disconnected.");
                        clients.remove(this);
                        printMessage("Connected clients : " + clients.size());
                        break;
                    }
                    /// COMMAND HANDLING ///
               
                  if (clientMsg.equals(C_QUIT)) // Quit
                  {
                        // Send quit notification'
                     command(S_MSG, "Client " + id + " has quit.", 0);
                     printMessage("Client " + id + " is quitting.");
                        // Perhaps send a message to end the thread,
                        // if that ends up making sense?
                     reading = false;
                        // Get rid of this client
                     clients.remove(this);
                     printMessage("Connected clients : " + clients.size());
                  }
                  else if (clientMsg.equals(C_CHAT)) // Chat
                  {
                     String chatMsg = br.readLine();
                     printMessage("Chat message receieved : " + chatMsg);
                     command(S_MSG, chatMsg, 0);
                  }
                  else if (clientMsg.equals(C_READY)) // Ready
                  {
                     if(gameStarted)
                     {
                        command(S_MSG, "The game has already started.", id);
                     }
                     if(!ready && !gameStarted)
                     {
                        ready = true;
                        printMessage("Client " + id + " is ready.");
                        command(S_MSG, "Player " + id + " is ready.", 0);
                     }
                     else if (ready && !gameStarted)
                     {
                        ready = false;
                        printMessage("Client " + id + " is not ready.");
                        command(S_MSG, "Player " + id + " is not ready.", 0);
                     }
                     if (clients.size() == 2 && !gameStarted)
                     {
                        if (clients.get(0).isReady()
                                && clients.get(1).isReady())
                        {
                           printMessage("Both clients ready, game starting...");
                           command(S_MSG, "Both players are ready.", 0);
                                
                        // Start the game
                           startGame();
                        		  
                           gameStarted = true;
                           turn++;
                           command(S_TURN, "true," + turn, id);
                           myTurn = true;
                        }
                     }
                  }
                  else if (clientMsg.equals(C_HUMAN)) //Request to be a human
                  {
                     int otherPlayerId = 0;
                     if(id == 1)
                        otherPlayerId = 1;
                  		
                     if(!human && !cylon && !clients.get(otherPlayerId).isHuman())
                     {
                        human = true;
                        printMessage("Client " + id + " is human");
                        command(S_RACE, "human", id);
                        command(S_MSG, "You have chosen the humans", id);
                     }
                     else //if(human || clients.get(otherPlayerId).isHuman())
                     {
                        printMessage("Deny request for client " + id + " to be a human");
                     		// command(S_RACE, "false,human", id);
                        command(S_MSG, "Sorry, that race has already been chosen", id);
                     }
                  }
                  else if (clientMsg.equals(C_CYLON)) //Request to be a human
                  {
                     int otherPlayerId = 0;
                     if (id == 1)
                        otherPlayerId = 1;
                  		
                     if(!cylon && !human && !clients.get(otherPlayerId).isCylon())
                     {
                        cylon = true;
                        printMessage("Client " + id + " is cylon");
                        command(S_RACE, "cylon", id);
                        command(S_MSG, "You have chosen the cylons", id);
                     }
                     else //if(cylon || clients.get(otherPlayerId).isCylon())
                     {
                        printMessage("Deny request for client " + id + " to be cylon");
                     		// command(S_RACE, "false,cylon", id);
                        command(S_MSG, "Sorry, that race has already been chosen", id);
                     }								
                  }
                  else if (clientMsg.equals(C_MOVE))
                  {
                     clientMsg = br.readLine();
							String[] positions = clientMsg.split(",");
							validateMove(positions);
                  }
						else if (clientMsg.equals(C_ATTACK))
						{
							clientMsg = br.readLine();
							String[] positions = clientMsg.split(",");
							validateAttack(positions);
						}
               
                    /// END COMMAND HANDLING ///
               }
            }
               catch (IOException ioex)
               {
                  System.out.println("Server error.");
                  ioex.printStackTrace();
               }
         }
			
			/**
			 * This method validates the attack and sends either a
			 * S_INVALID_ATTACK, S_DESTROY, or S_ATTACK command back to client
			 **/
			public void validateAttack(String[] positions)
			{
				//The position of the ship attacking
				int shipPosition = Integer.parseInt(positions[0]);
				
				//The position of the ship being attacked
				int attackPosition = Integer.parseInt(positions[1]);
				
				//Calculate the distance between the two positions
				double distance = calcDistance(shipPosition, attackPosition);
				
				//The ship that is attacking
				Ship attackingShip = null;
				
				//The ship that is being attacked
				Ship opponentShip = null;
				
				//Loop through the human ships array
				for(Ship aShip: humanShips)
				{
					//Look for attacking ship based on position
					if(attackPosition == aShip.position())
					{
						attackingShip = aShip;
					}
					//Look opponentShip based on position
					if(attackPosition == aShip.position())
					{
						//Invalid attack if the ship is friendly
						if(human)
						{
							command(S_INVALID_ATTACK, null, id);
							return;
						}
						else
						{
							opponentShip = aShip;
						}
					} 
				}
				
				//Loop through the human ships array
				for(Ship aShip: cylonShips)
				{
					//Look for attacking ship based on position
					if(attackPosition == aShip.position())
					{
						attackingShip = aShip;
					}
					//Look opponentShip based on position
					if(attackPosition == aShip.position())
					{
						//Invalid attack if the ship is friendly
						if(cylon)
						{
							command(S_INVALID_ATTACK, null, id);
							return;
						}
						else
						{
							opponentShip = aShip;
						}
					}
				}
				
				if(opponentShip == null)
				{
					command(S_INVALID_ATTACK, null, id);
					return;
				}
				
				//Validate distance
				if(distance < attackingShip.getAttackRange() + 1)
				{
					//Damage the ship based on race
					if(cylon)
						humanShips.get(humanShips.indexOf(opponentShip)).takeDamage(attackingShip.getWeaponDamage());
					if(human)
						cylonShips.get(cylonShips.indexOf(opponentShip)).takeDamage(attackingShip.getWeaponDamage());
					
					//Check if ship has been destoryed
					if(opponentShip.hits() <= 0)
					{
						//Send destroy command to the client
						command(S_DESTROY, attackPosition + "", 0);
						
						//Remove the ship from the array based on race
						if(human)
							cylonShips.remove(cylonShips.indexOf(opponentShip));
						if(cylon)
							humanShips.remove(humanShips.indexOf(opponentShip));
					}
					//Issue attack command
					else
					{
						command(S_ATTACK, attackPosition + "," + opponentShip.hits(), 0);
					}
					
					//Change the turn
					changeTurn();
				}
				else
				{
					command(S_INVALID_ATTACK, null, id);
				} 
				
			}
			
			public void validateMove(String[] positions)
			{
				int shipPosition = Integer.parseInt(positions[0]);
				int movePosition = Integer.parseInt(positions[1]);
				
				Ship movingShip = null;

				double distance = calcDistance(shipPosition, movePosition);
				
				//Loop through humans to validate and look for the ship solely on position 
				for (Ship aShip : humanShips)
				{
					//If moveLocation is a location of human ship then invalid move
					if(movePosition == aShip.position())
					{
						command(S_INVALID_MOVE, null, id);
						return;
					}
					//
					if(shipPosition == aShip.position())
					{
						movingShip = aShip;
					}
				}
				
				for (Ship aShip : cylonShips)
				{
					//If moveLocation is  a location of cylon ship then invalid move
					if(movePosition == aShip.position())
					{
						command(S_INVALID_MOVE, null, id);
						return;
					}
					//If the shipLoocation is the position of the 
					if(shipPosition == aShip.position())
					{
						movingShip = aShip;
					}
				}
				
				//Validate the distance
				if(distance < movingShip.getMoveRange() + 1)
				{
					//If it is a human ship then change the humanShips array
					if(movingShip instanceof ShipViper ||
						movingShip instanceof ShipRaptor ||
						movingShip instanceof ShipGalactica)
					{
						humanShips.get(humanShips.indexOf(movingShip)).setPosition(movePosition);
					}
					//Else change the cylonShips array
					else
					{
						cylonShips.get(cylonShips.indexOf(movingShip)).setPosition(movePosition);
					}
					
					//Issue move command to client
					command(S_MOVE, shipPosition + "," + movePosition, 0);
					
					//Change the turn
					changeTurn();
				}
				//If distance is not correct then it is an invalid move
				else
				{
					command(S_INVALID_MOVE, null, id);
				}

			}
			
			/**
			 * This method calculates the distance between to points
			 * @return the distance between the two pionts
			 **/
			public Double calcDistance(int _shipPosition, int _actionPosition)
			{
				//The ship that is moving or attacking
				int shipPosition = _shipPosition;
				
				//Position being attacked or moved to
				int actionPosition = _actionPosition;
				
				int moveRadius;
				
				Ship movingShip = null;
				
				//X and Y coordinates for calculating distance
				int x1, y1, x2, y2;
				
				if(shipPosition == 0)
				{
					x1 = 0;
					y1 = 0;
				}
				else
				{
					x1 = shipPosition / 12;
					y1 = shipPosition % 12;
				}
				
				if(actionPosition == 0)
				{
					x2 = 0;
					y2 = 0;
				}
				else
				{
					x2 = actionPosition / 12;
					y2 = actionPosition % 12;
				}
				
				//Calculate the distance
				double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
				
				return distance;				
			}
			
			public void changeTurn()
			{
				myTurn = false;
				clients.get(opponentId).setMyTurn(false);
                turn++;
				command(S_TURN, "false," + turn, id); 
				command(S_TURN, "true," + turn, opponentId +1);
			}
			
			public void setMyTurn(boolean _myTurn)
			{
				myTurn = _myTurn;
			}      
        /**
         * Get the PrintWriter
         * @return the PrintWriter
         */
         public PrintWriter getPW()
         {
            return pw;
         }
      
        /**
         * Is the client ready to play?
         * @return client ready or not
         */
         public boolean isReady()
         {
            return ready;
         }
        
        /**
         *Is the client a human?
      	*@ return client human or not
      	*/
         public boolean isHuman()
         {
            return human;
         }
        		  
        /**
         *Is the client a cylon?
      	*@ return client cylon or not
      	*/
         public boolean isCylon()
         {
            return cylon;
         }
      
        /**
         * Start the game by placing ships and such
         */
         public void startGame()
         {
            // Broadcast GO command
            command(S_GO, null, 0);
         
            // Create Raiders and Vipers
            for (int i = 0; i < 8; i++)
            {
               cylonShips.add(new ShipRaider("Raider " + i, 14 + i));
               humanShips.add(new ShipViper("Viper " + i, 122 + i));
            }
         
            // Create Heavy Raiders and Raptors
            for (int i = 0; i < 4; i++)
            {
               cylonShips.add(new ShipHeavyRaider("Heavy Raider " + i, 28 + i));
               humanShips.add(new ShipRaptor("Raptor " + i, 112 + i));
            }
         
            // Create "motherships"
            cylonShips.add(new ShipBasestar("Basestar", 5));
            humanShips.add(new ShipGalactica("Galactica", 137));
         
            // Send the ships to the clients in string form
            for (Ship s : humanShips)
               command(S_SHIP, s.toString(), 0);
            for (Ship s : cylonShips)
               command(S_SHIP, s.toString(), 0);
         }
      }
   
    /**
     * Send a command to a client
     * @param cmd the command
     * @param arg the argument
     * @param mode 0: broadcast, 1: client 1, 2: client 2
     */
      public void command(String cmd, String arg, int mode)
      {
         PrintWriter clientPW; // the client's PrintWriter
        
         switch (mode)
         {
            case 0:
               for (Handler c: clients)
               {
                  clientPW = c.getPW();
                  clientPW.println(cmd);
                  if (arg != null)
                     clientPW.println(arg);
                  clientPW.flush();
               }
               break;
            case 1:
               clientPW = clients.get(0).getPW();
               clientPW.println(cmd);
               if (arg != null)
                  clientPW.println(arg);
               clientPW.flush();
               break;
            case 2:
               clientPW = clients.get(1).getPW();
               clientPW.println(cmd);
               if (arg != null)
                  clientPW.println(arg);
               clientPW.flush();
               break;
         }
      }
   
   
    /**
     * Print a formatted server message
     * @param msg the message to print
     */
      public void printMessage(String msg)
      {
         System.out.println("[ ] : " + msg);
      }
    
    /**
     * Program entry point
     * @param args the program arguments (none taken)
     */
      public static void main(String[] args)
      {
         try
         {
            BattleServer server = new BattleServer();
         }
            catch (Exception ex)
            {
               System.out.println("Something terrible has happened:");
               ex.printStackTrace();
            }
      }
   }

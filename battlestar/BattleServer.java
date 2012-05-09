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

        private boolean ready; // is this client ready to play?

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
                id = 1;
            else
                id = 2;

            ready = false;
        }

        /**
         * Thread run method
         */
        public void run()
        {
            String clientMsg; // message sent by client

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
                    clientMsg = br.readLine();
                    
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
                        if (!ready)
                        {
                            ready = true;
                            printMessage("Client " + id + " is ready.");
                            command(S_MSG, "Player " + id + " is ready.", 0);
                        }
                        else
                        {
                            ready = false;
                            printMessage("Client " + id + " is not ready.");
                            command(S_MSG, "Player " + id + " is not ready.", 0);
                        }
                        if (clients.size() == 2)
                        {
                            if (clients.get(0).isReady()
                                && clients.get(1).isReady())
                            {
                                printMessage("Both clients ready, game starting...");
                                command(S_MSG, "Both players are ready.", 0);
                                
                                // Start the game
                                startGame();
                            }
                        }
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
                humanShips.add(new ShipRaptor("Raptor " + i, 122 + i));
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

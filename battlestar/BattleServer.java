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
    Vector<Handler> clients; // vector for client threads

    /**
     * BattleServer constructor
     */
    public BattleServer()
    {
        /// START NETWORKING CODE ///
        
        ServerSocket ss = null;
        clients = new Vector<Handler>();

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
                
                while (reading) // Continously accept input from the client
                {
                    clientMsg = br.readLine();

                    if (clientMsg.equals(C_QUIT))
                    {
                        // Send quit notification'
                        command(S_MSG, "Client " + id + " has quit.", 0);
                        // Perhaps send a message to end the thread,
                        // if that ends up making sense?
                        reading = false;
                        // Get rid of this client
                        clients.remove(this);
                    }
                    else if (clientMsg.equals(C_CHAT))
                    {
                        String chatMsg = br.readLine();
                        printMessage("Chat message receieved : " + chatMsg);
                        command(S_MSG, chatMsg, 0);
                    }
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
                    clientPW.println(arg);
                    clientPW.flush();
                }
                break;
            case 1:
                clientPW = clients.get(0).getPW();
                clientPW.println(cmd);
                clientPW.println(arg);
                clientPW.flush();
                break;
            case 2:
                clientPW = clients.get(1).getPW();
                clientPW.println(cmd);
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

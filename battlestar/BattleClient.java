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

    String handle; // client identifier

    final StatusBar statusBar;
    final JTextField tfInput;

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
        miExit.addActionListener(new ActionListener()
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
        miAbout.addActionListener(new ActionListener()
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
        panelView = new ViewPanel();
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
       
        /// START SERVER CONNECTION CODE ///
        
        try
        {
            // Connect with server
            final Socket s = new Socket(ip, GAME_PORT);
            
            // Open server I/O
            final BufferedReader br = new BufferedReader(
                new InputStreamReader(s.getInputStream()));
            final PrintWriter pw = new PrintWriter(
                new OutputStreamWriter(s.getOutputStream()));

            /// SERVER COMMAND HANDLING ///

            // Create a thread to continuously read from server
            final Thread serverRead = new Thread()
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
                                Ship newShip = new Ship(shipAttributes[0],
                                    shipAttributes[1],
                                    Integer.parseInt(shipAttributes[2]),
                                    Integer.parseInt(shipAttributes[3]));

                                // TODO:
                                // Instead of setting image icons here, let's
                                // have them set in their individual classes.
                                
                                // Send the ship to the ViewZone
                                panelView.setShip(Integer.parseInt(
                                    shipAttributes[3]), newShip);
                            }
                        }
                        catch (NullPointerException npex)
                        {
                            panelChat.print("Server down!");
                            break;
                        }
                        catch (IOException ioex)
                        {
                            panelChat.print("I/O error");
                            ioex.printStackTrace();
                        }
                    }
                }
            };
            serverRead.start(); // start the server reading thread

            /// END SERVER COMMAND HANDLING ///
            
            // Close connections if the client is closed
            this.addWindowListener(new WindowAdapter()
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
            tfInput.addKeyListener(new KeyAdapter()
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

                            // Clear the input text field
                            tfInput.setText("");
                        }
                    }
                });

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
}

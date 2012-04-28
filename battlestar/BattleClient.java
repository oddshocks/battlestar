/**
 * Battlestar Client
 * For RIT's 4002-219 course
 * Author: David Gay
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
        this.setTitle("Battlestar");
        this.setLocation(200, 200);
        this.setSize(1024, 800);
        this.setLayout(new BorderLayout());

        // Set handle
        handle = "Player";

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

        this.add(menuBar, BorderLayout.NORTH);
        
        // Content area
        JPanel panelContent = new JPanel();
        panelContent.setLayout(new GridLayout(1, 2));
        
            // Game area
            JPanel panelGame = new JPanel();
            panelGame.setLayout(new GridLayout(2, 2));

                // View panel
                panelView = new ViewPanel();
                panelGame.add(panelView);
            
                // Stats panel
                panelStat = new StatPanel();
                panelGame.add(panelStat);
            
                // Control panel
                panelControl = new ControlPanel();
                panelGame.add(panelControl);

                // Chat panel
                panelChat = new ChatPanel();
                panelGame.add(panelChat);

        this.add(panelGame, BorderLayout.CENTER);

        // Chat input and status bar
        JPanel panelFooter = new JPanel();
        panelFooter.setLayout(new GridLayout(2, 1));
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
                            if (input.equals("STOP"))
                            {
                                statusBar.setMessage("Bye!");
                                break; // we're done here
                            }
                            panelChat.print(input + "\n");
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
            serverRead.start();

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

            // Send message with ENTER button
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
                                String[] msg = input.split(" ", 1);
                                cmd = msg[0];
                                arg = msg[1];
                            }
                            if (cmd.equals("/quit"))
                            {
                                quit();
                            }
                            if (cmd.equals("/chat"))
                            {
                                command(pw, C_CHAT, "<" + handle + "> "
                                    + arg);
                            }
                            
                            // Clear the input text field
                            tfInput.setText("");
                        }
                    }
                });
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
     * Send a command to the server
     * @param pw the PrintWriter to send the command to
     * @param cmd the command
     * @param arg the argument
     */
    public void command(PrintWriter pw, String cmd, String arg)
    {
        pw.println(cmd);
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

/**
 * Battlestar Client
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

    final StatusBar statusBar;

    /**
     * Constructor
     * @param ip the IP address of the server
     */
    public BattleClient(String ip)
    {
        this.setTitle("Battlestar");
        this.setLocation(200, 200);
        this.setSize(640, 480);
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

        this.add(menuBar, BorderLayout.NORTH);
        
        // Content area
        JPanel panelContent = new JPanel();
        panelContent.setLayout(new GridLayout(2, 1));
        
            // View panel
            JPanel panelView = new JPanel();
            panelContent.add(panelView);
            
            // Control panel
            JPanel panelControl = new JPanel();
            panelContent.add(panelControl);

        this.add(panelContent, BorderLayout.CENTER);

        // Chat input and status bar
        JPanel panelFooter = new JPanel();
        panelFooter.setLayout(new GridLayout(2, 1));
        JTextField tfChatInput = new JTextField();
        panelFooter.add(tfChatInput);
        statusBar = new StatusBar();
        panelFooter.add(statusBar);
        this.add(panelFooter, BorderLayout.SOUTH);

        // Finalize the window
        tfChatInput.requestFocusInWindow();
        statusBar.setMessage("Program started!");
        this.setVisible(true);
       
        /// START SERVER CONNECTION CODE ///
        
        try
        {
            // Connect with server
            final Socket s = new Socket(ip, PORT);

            // Close connections if the client is closed
            this.addWindowListener(new WindowAdapter()
                {
                    public void windowClosing(WindowEvent we)
                    {
                        try
                        {
                            // Tell the server that we're quitting

                            // Stop reading
                        
                            // Close connections
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

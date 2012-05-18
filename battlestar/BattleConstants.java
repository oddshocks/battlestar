/**
 * BattleConstants
 * Holds constants used in Battlestar
 * Author: David Gay
 * Spring 2012
 */

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

public interface BattleConstants
{
    /// VALUES /// 

    final int GAME_PORT = 16789;

    /// SETTINGS ///

    final int VIEW_SIZE = 12;

    /// STYLE ///
    
    final Color FONT_COLOR = new Color(238, 229, 222);
    final Color BACKGROUND_COLOR = new Color(139, 134, 130);
    final Color BACKGROUND_HUMAN = new Color(105, 105, 105);
    final Color FOREGROUND_HUMAN = new Color(173, 216, 230);
    final Color BACKGROUND_CYLON = new Color(176, 196, 122);
    final Color FOREGROUND_CYLON = new Color(128, 128, 128);

    final Font FONT_MAIN = new Font("Verdana", Font.BOLD, 12);
    final Font FONT_STAT = new Font("Verdana", Font.BOLD, 16);

    /// RACES ///
    
    public static final String[] RACES = {"Human", "Cylon"};
    
    /// SHIPS ///

    final String H_GALACTICA = "Galactica"; // Can stealth for 1-3 turns
    final String H_VIPER = "Human Viper"; // Fire 2 ahead and behind
    final String H_RAPTOR = "Human Raptor"; // Fire all adjacent squares
    final String C_BASESTAR = "Basestar"; // Disable 1-3 Human ships for 1 turn
    final String C_RAIDER = "Cylon Raider"; // Fire 2 left and right
    final String C_HEAVY_RAIDER = "Cylon Heavy Raider"; // Fire 2 all diagonals

    /// CLIENT COMMANDS ///

    final String C_QUIT = "/quit"; // quit the game
    final String C_CHAT = "/chat"; // send a chat message
    final String C_READY = "/ready"; // ready to start
	final String C_CYLON = "/cylon"; // request to be a cylon
	final String C_HUMAN = "/human"; // request to be a human
	final String C_MOVE = "/move"; // request to move a ship
	final String C_ATTACK = "/attack"; //request to attack a ship

    /// SERVER COMMANDS ///
    
    final String S_MSG = "MSG"; // send a message
    final String S_STOP = "STOP";
    final String S_GO = "GO"; // start the game
    final String S_SHIP = "SHIP"; // send ship info
	final String S_RACE = "RACE"; // send the client their race
	final String S_TURN = "TURN"; // tell the client it is their turn
	final String S_MOVE = "MOVE"; // move the ship
	final String S_INVALID_MOVE = "INVALID MOVE"; // request to move 
	final String S_INVALID_ATTACK = "INVALID ATTACK"; //request to attack denied
	final String S_ATTACK = "ATTACK"; //attack a ship
	final String S_DESTROY = "DESTROY"; //destroy a ship 

    /// ICONS ///

    final ImageIcon ICON_TURN = new ImageIcon("images/turnicon.png");
    final ImageIcon ICON_AID = new ImageIcon("images/aidicon.gif");
    final ImageIcon ICON_ID = new ImageIcon("images/idicon.jpeg");
    final ImageIcon ICON_TYPE = new ImageIcon("images/typeicon.png");
}

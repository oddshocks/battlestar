   import java.awt.Color;
   import java.awt.Font;
   import javax.swing.*;
   
	/**
 * BattleConstants
 * Holds constants used in Battlestar
 * Spring 2012
 * @author Scott Gunther and David Gay
 */
	
   public interface BattleConstants
   {
       
   	/** Port to conduct communications **/
      final int GAME_PORT = 16789;
   
   	/** Length and width of the game board in squares **/
      final int VIEW_SIZE = 12;
    
   	/** Font color for the chat panel **/
      final Color FONT_COLOR = new Color(238, 229, 222);
   	
   	/** Background color of the chat panel **/
      final Color BACKGROUND_COLOR = new Color(139, 134, 130);
   
   	/** Font for the chat panel **/ 		
      final Font FONT_MAIN = new Font("Verdana", Font.BOLD, 12);
   	
   	/** Font for the stat panel **/
      final Font FONT_STAT = new Font("Verdana", Font.BOLD, 16);
   	
   	/** Races available to play with **/
      final String[] RACES = {"Human", "Cylon"};
   	
    /// SHIPS ///
   
   	/** The Human capital ship **/
      final String H_GALACTICA = "Galactica"; 
   	
   	/** The human light attack ship **/
      final String H_VIPER = "Human Viper";
   	 
   	/** The human medium attack ship **/
      final String H_RAPTOR = "Human Raptor"; 
   	
   	/** The cylon capital ship **/
      final String C_BASESTAR = "Basestar";
   	
   	/** The cylon light attack ship **/
      final String C_RAIDER = "Cylon Raider";
   	
   	/** The cylon medium attack ship **/
      final String C_HEAVY_RAIDER = "Cylon Heavy Raider";
   
    /// CLIENT COMMANDS ///
   
   	/** Client command to tell the server it is quitting **/
      final String C_QUIT = "/quit";
   	
   	/** Client command to tell the server it is sending a chat message **/
      final String C_CHAT = "/chat";
   	
   	/** Client command to tell the server it is ready to play the game **/
      final String C_READY = "/ready";
   	
   	/** Client command to request to be a cylon **/
      final String C_CYLON = "/cylon";
   	
   	/** Client command to request to be a human **/
      final String C_HUMAN = "/human";
   	
   	/** Client command to request to move a ship **/
      final String C_MOVE = "/move";
   	
   	/** Client command to request to attack a ship **/
      final String C_ATTACK = "/attack";
   
    /// SERVER COMMANDS ///
    
    	/** Server command to send a text message to the client's chat **/
      final String S_MSG = "MSG";
   	
   	/** Server command to tell the client to stop and close **/
      final String S_STOP = "STOP";
   	
   	/** Server command to tell the client to start the game **/
      final String S_GO = "GO";
   	
   	/** Server command to send a ship to the client in String form **/
      final String S_SHIP = "SHIP";
   	
   	/** Server command to send a client their race (cyon or human) **/
      final String S_RACE = "RACE";
   	
   	/** Server command to set the the clients turn to true or false **/
      final String S_TURN = "TURN";
   	
   	/** Server command to tell the client to move a ship **/
      final String S_MOVE = "MOVE";
   	
   	/** Server command to tell the client it has made an invalid move **/
      final String S_INVALID_MOVE = "INVALID MOVE"; 
   	
   	/** Server command to tell the client it has made an invalid attack **/
      final String S_INVALID_ATTACK = "INVALID ATTACK";
   	
   	/** Server command to tell the client a ship has been attacked **/
      final String S_ATTACK = "ATTACK";
   	
   	/** Server command to tell the client to destroy a ship **/
      final String S_DESTROY = "DESTROY";
   	
   	/** Server command to tell client it has won the game **/
      final String S_WIN = "WIN";
   	
   	/** Server command to tell client it has lost the game **/
      final String S_LOSE = "LOSE";
   	
   	/** Server command to tell client it can initialize the GUI **/
      final String S_INITIALIZE = "INITIALIZE";
   	
   	/** Server command to tell the client what its id is **/
      final String S_ID = "ID";
   
   }

/**
 * BattleConstants
 * Holds constants used in Battlestar
 * Author: David Gay
 * Spring 2012
 */

public interface BattleConstants
{
    /// VALUES /// 

    final int GAME_PORT = 16789;

    /// SETTINGS ///

    final int VIEW_SIZE = 12;

    /// CLIENT COMMANDS ///

    final String C_QUIT = "/quit"; // quit the game
    final String C_CHAT = "/chat"; // send a chat message
    final String C_READY = "/ready"; // ready to start

    /// SERVER COMMANDS ///
    
    final String S_MSG = "MSG"; // send a message
    final String S_STOP = "STOP";
    final String S_GO = "GO"; // start the game
}

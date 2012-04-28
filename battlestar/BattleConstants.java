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

    final String C_QUIT = "/quit";
    final String C_CHAT = "/chat";

    /// SERVER COMMANDS ///
    
    final String S_MSG = "MSG";
}

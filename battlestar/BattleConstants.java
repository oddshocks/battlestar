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

    /// SHIPS ///

    final String H_GALACTICA = "Galactica"; // Can stealth for 1-3 turns
    final String H_FIGHTER = "Human Fighter"; // Fire 2 ahead and behind
    final String H_BOMBER = "Human Bomber"; // Fire all adjacent squares
    final String C_BASESTAR = "Basestar"; // Disable 1-3 Human ships for 1 turn
    final String C_RAIDER = "Cylon Raider"; // Fire 2 left and right
    final String C_PULSAR = "Cylon Pulsar"; // Fire 2 all diagonals

    /// CLIENT COMMANDS ///

    final String C_QUIT = "/quit"; // quit the game
    final String C_CHAT = "/chat"; // send a chat message
    final String C_READY = "/ready"; // ready to start

    /// SERVER COMMANDS ///
    
    final String S_MSG = "MSG"; // send a message
    final String S_STOP = "STOP";
    final String S_GO = "GO"; // start the game
}

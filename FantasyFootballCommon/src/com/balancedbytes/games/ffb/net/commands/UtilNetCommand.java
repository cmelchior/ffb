package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;

/**
 * 
 * @author Kalimar
 */
public class UtilNetCommand {
  
  public static void validateCommandId(NetCommand pNetCommand, NetCommandId pReceivedId) {
    if (pNetCommand == null) {
      throw new IllegalArgumentException("Parameter netCommand must not be null.");
    }
    if (pNetCommand.getId() != pReceivedId) {
      throw new IllegalStateException("Wrong netCommand id. Expected " + pNetCommand.getId().getName() + " received " + ((pReceivedId != null) ? pReceivedId.getName() : "null"));
    }
  }

}

package com.balancedbytes.games.ffb.server;

import java.util.LinkedList;
import java.util.List;

import com.balancedbytes.games.ffb.net.commands.ServerCommand;
import com.balancedbytes.games.ffb.net.commands.ServerCommandReplay;

/**
 * 
 * @author Kalimar
 */
public class ServerReplayer implements Runnable {

  private boolean fStopped;
  private List<ServerReplay> fReplayQueue;
  private FantasyFootballServer fServer;

  public ServerReplayer(FantasyFootballServer pServer) {
    fServer = pServer;
    fReplayQueue = new LinkedList<ServerReplay>();
  }

  public void add(ServerReplay pReplay) {
    synchronized (fReplayQueue) {
      fReplayQueue.add(pReplay);
      fReplayQueue.notify();
    }
  }

  public void run() {

    ServerReplay serverReplay = null;

    while (true) {

      try {

        synchronized (fReplayQueue) {
          try {
            while (fReplayQueue.isEmpty() && !fStopped) {
              fReplayQueue.wait();
            }
          } catch (InterruptedException e) {
            break;
          }
          if (fStopped) {
            break;
          }
          if ((serverReplay == null) && !fReplayQueue.isEmpty()) {
            serverReplay = fReplayQueue.remove(0);
          }
        }

        while (serverReplay != null) {

          serverReplay.setComplete(true);

          ServerCommandReplay replayCommand = new ServerCommandReplay();

          ServerCommand[] serverCommands = serverReplay.findRelevantCommandsInLog();
          replayCommand.setTotalNrOfCommands(serverCommands.length);

          for (ServerCommand serverCommand : serverCommands) {
            replayCommand.add(serverCommand);
            if (replayCommand.getNrOfCommands() >= ServerCommandReplay.MAX_NR_OF_COMMANDS) {
              serverReplay.setComplete(false);
              break;
            }
          }

          getServer().getCommunication().send(serverReplay.getSession(), replayCommand, false);
          if (getServer().getDebugLog().isLogging(IServerLogLevel.DEBUG)) {
            StringBuilder message = new StringBuilder().append("Replay commands ").append(replayCommand.getCommandNr());
            message.append(replayCommand.findLowestCommandNr()).append(" - ").append(replayCommand.findHighestCommandNr());
            message.append(" of ").append(replayCommand.getTotalNrOfCommands()).append(" total.");
            getServer().getDebugLog().log(IServerLogLevel.DEBUG, serverReplay.getGameId(), DebugLog.COMMAND_SERVER_SPECTATOR, message.toString());
          }

          if (!serverReplay.isComplete()) {
            serverReplay.setFromCommandNr(replayCommand.findHighestCommandNr() + 1);
          } else {
            serverReplay = null;
          }

        }

      } catch (Exception pException) {
        getServer().getDebugLog().log(serverReplay.getGameId(), pException);
      }

    }

  }

  public void stop() {
    fStopped = true;
    synchronized (fReplayQueue) {
      fReplayQueue.notifyAll();
    }
  }

  public FantasyFootballServer getServer() {
    return fServer;
  }

}

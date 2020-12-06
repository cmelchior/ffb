package com.balancedbytes.games.ffb.client.net;

import java.util.TimerTask;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;

public class ClientPingTask extends TimerTask {

	private FantasyFootballClient fClient;

	public ClientPingTask(FantasyFootballClient pClient) {
		fClient = pClient;
	}

	public void run() {
		if (getClient().getCommandEndpoint().isOpen()) {
			getClient().getCommunication().sendPing(System.currentTimeMillis());
		}
	}

	public FantasyFootballClient getClient() {
		return fClient;
	}

}

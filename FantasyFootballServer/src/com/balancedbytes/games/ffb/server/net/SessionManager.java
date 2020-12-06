package com.balancedbytes.games.ffb.server.net;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jetty.websocket.api.Session;

import com.balancedbytes.games.ffb.ClientMode;

/**
 * 
 * @author Kalimar
 */
public class SessionManager {

	private Map<Long, Set<Session>> fSessionsByGameId;
	private final Map<Session, JoinedClient> fClientBySession;
	private Map<Session, Long> fLastPingBySession;

	private class JoinedClient {

		private long fGameId;
		private String fCoach;
		private ClientMode fMode;
		private boolean fHomeCoach;

		JoinedClient(long pGameId, String pCoach, ClientMode pMode, boolean pHomeCoach) {
			fGameId = pGameId;
			fCoach = pCoach;
			fMode = pMode;
			fHomeCoach = pHomeCoach;
		}

		public long getGameId() {
			return fGameId;
		}

		public String getCoach() {
			return fCoach;
		}

		public ClientMode getMode() {
			return fMode;
		}

		boolean isHomeCoach() {
			return fHomeCoach;
		}

	}

	public SessionManager() {
		fSessionsByGameId = new HashMap<>();
		fClientBySession = new HashMap<>();
		fLastPingBySession = new HashMap<>();
	}

	public long getGameIdForSession(Session pSession) {
		JoinedClient client = fClientBySession.get(pSession);
		if (client != null) {
			return client.getGameId();
		} else {
			return 0;
		}
	}

	public String getCoachForSession(Session pSession) {
		JoinedClient client = fClientBySession.get(pSession);
		if (client != null) {
			return client.getCoach();
		} else {
			return null;
		}
	}

	public ClientMode getModeForSession(Session pSession) {
		JoinedClient client = fClientBySession.get(pSession);
		if (client != null) {
			return client.getMode();
		} else {
			return null;
		}
	}

	public Session[] getSessionsForGameId(long pGameId) {
		Set<Session> sessions = fSessionsByGameId.get(pGameId);
		if (sessions != null) {
			return sessions.toArray(new Session[0]);
		} else {
			return new Session[0];
		}
	}

	public Session getSessionOfHomeCoach(long gameId) {
		Session sessionHomeCoach = null;
		Set<Session> sessions = fSessionsByGameId.get(gameId);
		if (sessions != null) {
			for (Session session : sessions) {
				JoinedClient client = fClientBySession.get(session);
				if ((client != null) && (client.getMode() == ClientMode.PLAYER) && client.isHomeCoach()) {
					sessionHomeCoach = session;
					break;
				}
			}
		}
		return sessionHomeCoach;
	}

	public boolean isHomeCoach(long gameId, String pCoach) {
		JoinedClient clientHomeCoach = fClientBySession.get(getSessionOfHomeCoach(gameId));
		return ((clientHomeCoach != null) && clientHomeCoach.getCoach().equals(pCoach));
	}

	public Session getSessionOfAwayCoach(long gameId) {
		Session sessionAwayCoach = null;
		Set<Session> sessions = fSessionsByGameId.get(gameId);
		if (sessions != null) {
			for (Session session : sessions) {
				JoinedClient client = fClientBySession.get(session);
				if ((client != null) && (client.getMode() == ClientMode.PLAYER) && !client.isHomeCoach()) {
					sessionAwayCoach = session;
					break;
				}
			}
		}
		return sessionAwayCoach;
	}

	public boolean isAwayCoach(long gameId, String pCoach) {
		JoinedClient clientAwayCoach = fClientBySession.get(getSessionOfAwayCoach(gameId));
		return ((clientAwayCoach != null) && clientAwayCoach.getCoach().equals(pCoach));
	}

	Session[] getSessionsWithoutAwayCoach(long gameId) {
		Set<Session> filteredSessions = new HashSet<>();
		Set<Session> sessions = fSessionsByGameId.get(gameId);
		if ((sessions != null) && (sessions.size() > 0)) {
			Session sessionAwayCoach = getSessionOfAwayCoach(gameId);
			for (Session session : sessions) {
				if (session != sessionAwayCoach) {
					filteredSessions.add(session);
				}
			}
		}
		return filteredSessions.toArray(new Session[0]);
	}

	Session[] getSessionsWithoutHomeCoach(long gameId) {
		Set<Session> filteredSessions = new HashSet<>();
		Set<Session> sessions = fSessionsByGameId.get(gameId);
		if ((sessions != null) && (sessions.size() > 0)) {
			Session sessionHomeCoach = getSessionOfHomeCoach(gameId);
			for (Session session : sessions) {
				if (session != sessionHomeCoach) {
					filteredSessions.add(session);
				}
			}
		}
		return filteredSessions.toArray(new Session[0]);
	}

	public Session[] getSessionsOfSpectators(long gameId) {
		Set<Session> filteredSessions = new HashSet<>();
		Set<Session> sessions = fSessionsByGameId.get(gameId);
		if ((sessions != null) && (sessions.size() > 0)) {
			Session sessionAwayCoach = getSessionOfAwayCoach(gameId);
			Session sessionHomeCoach = getSessionOfHomeCoach(gameId);
			for (Session session : sessions) {
				if ((session != sessionAwayCoach) && (session != sessionHomeCoach)) {
					filteredSessions.add(session);
				}
			}
		}
		return filteredSessions.toArray(new Session[0]);
	}

	public void addSession(Session pSession, long gameId, String pCoach, ClientMode pMode, boolean pHomeCoach) {
		JoinedClient client = new JoinedClient(gameId, pCoach, pMode, pHomeCoach);
		fClientBySession.put(pSession, client);
		Set<Session> sessions = fSessionsByGameId.computeIfAbsent(gameId, k -> new HashSet<>());
		sessions.add(pSession);
		fLastPingBySession.put(pSession, System.currentTimeMillis());
	}

	public void removeSession(Session pSession) {
		long gameId = getGameIdForSession(pSession);
		fClientBySession.remove(pSession);
		fLastPingBySession.remove(pSession);
		Set<Session> sessions = fSessionsByGameId.get(gameId);
		if (sessions != null) {
			sessions.remove(pSession);
			if (sessions.size() == 0) {
				fSessionsByGameId.remove(gameId);
			}
		}
	}

	public void setLastPing(Session pSession, long pPing) {
		fLastPingBySession.put(pSession, pPing);
	}

	public long getLastPing(Session pSession) {
		Long lastPing = fLastPingBySession.get(pSession);
		return (lastPing != null) ? lastPing : 0;
	}

	public Session[] getAllSessions() {
		synchronized (fClientBySession) {
			return fClientBySession.keySet().toArray(new Session[0]);
		}
	}

}

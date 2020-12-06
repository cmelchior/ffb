package com.balancedbytes.games.ffb.server.db.delete;

import com.balancedbytes.games.ffb.server.FantasyFootballServer;
import com.balancedbytes.games.ffb.server.db.DbStatementId;
import com.balancedbytes.games.ffb.server.db.DbUpdateStatement;

/**
 * 
 * @author Kalimar
 */
public class DbTeamSetupsDeleteParameter extends DefaultDbUpdateParameter {

	private String fTeamId;

	private String fName;

	public DbTeamSetupsDeleteParameter(String pTeamId, String pName) {
		fTeamId = pTeamId;
		fName = pName;
	}

	public String getTeamId() {
		return fTeamId;
	}

	public String getName() {
		return fName;
	}

	public DbUpdateStatement getDbUpdateStatement(FantasyFootballServer pServer) {
		return (DbUpdateStatement) pServer.getDbUpdateFactory().getStatement(DbStatementId.TEAM_SETUPS_DELETE);
	}

}

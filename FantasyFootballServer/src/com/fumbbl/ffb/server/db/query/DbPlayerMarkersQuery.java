package com.fumbbl.ffb.server.db.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fumbbl.ffb.FantasyFootballException;
import com.fumbbl.ffb.PlayerMarker;
import com.fumbbl.ffb.model.Game;
import com.fumbbl.ffb.model.Player;
import com.fumbbl.ffb.model.Team;
import com.fumbbl.ffb.server.FantasyFootballServer;
import com.fumbbl.ffb.server.GameState;
import com.fumbbl.ffb.server.db.DbStatement;
import com.fumbbl.ffb.server.db.DbStatementId;
import com.fumbbl.ffb.server.db.IDbTablePlayerMarkers;
import com.fumbbl.ffb.util.StringTool;

/**
 *
 * @author Kalimar
 */
public class DbPlayerMarkersQuery extends DbStatement {

	private PreparedStatement fStatement;

	public DbPlayerMarkersQuery(FantasyFootballServer pServer) {
		super(pServer);
	}

	public DbStatementId getId() {
		return DbStatementId.PLAYER_MARKERS_QUERY;
	}

	public void prepare(Connection pConnection) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ").append(IDbTablePlayerMarkers.COLUMN_PLAYER_ID).append(",")
					.append(IDbTablePlayerMarkers.COLUMN_TEXT).append(" FROM ").append(IDbTablePlayerMarkers.TABLE_NAME)
					.append(" WHERE (").append(IDbTablePlayerMarkers.COLUMN_TEAM_ID).append("=?)");
			fStatement = pConnection.prepareStatement(sql.toString());
		} catch (SQLException sqlE) {
			throw new FantasyFootballException(sqlE);
		}
	}

	public void execute(GameState pGameState) {
		if (pGameState == null) {
			return;
		}
		queryMarkers(pGameState, true);
		queryMarkers(pGameState, false);
	}

	private void queryMarkers(GameState pGameState, boolean pHomeTeam) {
		Game game = pGameState.getGame();
		Team team = pHomeTeam ? game.getTeamHome() : game.getTeamAway();
		if ((team == null) || !StringTool.isProvided(team.getId())) {
			return;
		}
		try {
			fStatement.setString(1, team.getId());
			try (ResultSet resultSet = fStatement.executeQuery()) {
				while (resultSet.next()) {
					String playerId = resultSet.getString(1);
					String text = resultSet.getString(2);
					Player<?> player = game.getPlayerById(playerId);
					if ((player != null) && StringTool.isProvided(text)) {
						PlayerMarker playerMarker = game.getFieldModel().getPlayerMarker(player.getId());
						if (playerMarker == null) {
							playerMarker = new PlayerMarker(player.getId());
							game.getFieldModel().add(playerMarker);
						}
						if (pHomeTeam) {
							playerMarker.setHomeText(text);
						} else {
							playerMarker.setAwayText(text);
						}
					}
				}
			}
		} catch (SQLException sqlE) {
			throw new FantasyFootballException(sqlE);
		}
	}

}

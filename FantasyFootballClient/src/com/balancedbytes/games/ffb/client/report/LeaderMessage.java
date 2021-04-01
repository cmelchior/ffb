package com.balancedbytes.games.ffb.client.report;

import com.balancedbytes.games.ffb.LeaderState;
import com.balancedbytes.games.ffb.RulesCollection;
import com.balancedbytes.games.ffb.RulesCollection.Rules;
import com.balancedbytes.games.ffb.client.StatusReport;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.ReportLeader;

@ReportMessageType(ReportId.LEADER)
@RulesCollection(Rules.COMMON)
public class LeaderMessage extends ReportMessageBase<ReportLeader> {

    public LeaderMessage(StatusReport statusReport) {
        super(statusReport);
    }

    @Override
    protected void render(ReportLeader report) {
  		StringBuilder status = new StringBuilder();
  		LeaderState leaderState = report.getLeaderState();

  		if (LeaderState.AVAILABLE.equals(leaderState)) {
  			printTeamName(game, false, report.getTeamId());
  			status.append(" gain a Leader re-roll.");
  			print(getIndent() + 1, status.toString());
  		} else {
  			status.append("Leader re-roll removed from ");
  			print(getIndent() + 1, status.toString());
  			printTeamName(game, false, report.getTeamId());
  		}
  		println(getIndent() + 1, ".");
    }
}

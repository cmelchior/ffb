package com.balancedbytes.games.ffb.client.report;

import com.balancedbytes.games.ffb.RulesCollection;
import com.balancedbytes.games.ffb.FactoryType.Factory;
import com.balancedbytes.games.ffb.RulesCollection.Rules;
import com.balancedbytes.games.ffb.client.StatusReport;
import com.balancedbytes.games.ffb.client.TextStyle;
import com.balancedbytes.games.ffb.mechanics.AgilityMechanic;
import com.balancedbytes.games.ffb.mechanics.Mechanic;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.report.ReportSkillRoll;
import com.balancedbytes.games.ffb.report.ReportId;

@ReportMessageType(ReportId.HYPNOTIC_GAZE_ROLL)
@RulesCollection(Rules.COMMON)
public class HypnoticGazeRollMessage extends ReportMessageBase<ReportSkillRoll> {

    public HypnoticGazeRollMessage(StatusReport statusReport) {
        super(statusReport);
    }

    @Override
    protected void render(ReportSkillRoll report) {
  		StringBuilder status = new StringBuilder();
  		StringBuilder neededRoll = null;
  		Player<?> player = game.getActingPlayer().getPlayer();
  		if (!report.isReRolled()) {
  			print(getIndent(), true, player);
  			print(getIndent(), TextStyle.BOLD, " gazes upon ");
  			print(getIndent(), true, game.getDefender());
  			println(getIndent(), TextStyle.BOLD, ":");
  		}
  		status.append("Hypnotic Gaze Roll [ ").append(report.getRoll()).append(" ]");
  		println(getIndent() + 1, TextStyle.ROLL, status.toString());
  		print(getIndent() + 2, false, player);
  		status = new StringBuilder();
  		if (report.isSuccessful()) {
  			status.append(" hypnotizes ").append(player.getPlayerGender().getGenitive()).append(" victim.");
  			println(getIndent() + 2, status.toString());
  			if (!report.isReRolled()) {
  				neededRoll = new StringBuilder().append("Succeeded on a roll of ").append(report.getMinimumRoll()).append("+");
  			}
  		} else {
  			status.append(" fails to affect ").append(player.getPlayerGender().getGenitive()).append(" victim.");
  			println(getIndent() + 2, status.toString());
  			if (!report.isReRolled()) {
  				neededRoll = new StringBuilder().append("Roll a ").append(report.getMinimumRoll()).append("+ to succeed");
  			}
  		}
  		if (neededRoll != null) {
  			AgilityMechanic mechanic = (AgilityMechanic) game.getRules().getFactory(Factory.MECHANIC).forName(Mechanic.Type.AGILITY.name());
  			neededRoll.append(mechanic.formatHypnoticGazeResult(report, player));
  			println(getIndent() + 2, TextStyle.NEEDED_ROLL, neededRoll.toString());
  		}
    }
}

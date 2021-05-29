package com.fumbbl.ffb.client.report.bb2020;

import com.fumbbl.ffb.PlayerState;
import com.fumbbl.ffb.RulesCollection;
import com.fumbbl.ffb.RulesCollection.Rules;
import com.fumbbl.ffb.SeriousInjury;
import com.fumbbl.ffb.client.TextStyle;
import com.fumbbl.ffb.client.report.ReportMessageBase;
import com.fumbbl.ffb.client.report.ReportMessageType;
import com.fumbbl.ffb.model.Player;
import com.fumbbl.ffb.report.ReportId;
import com.fumbbl.ffb.report.bb2020.ReportApothecaryRoll;
import com.fumbbl.ffb.util.ArrayTool;

@ReportMessageType(ReportId.APOTHECARY_ROLL)
@RulesCollection(Rules.BB2020)
public class ApothecaryRollMessage extends ReportMessageBase<ReportApothecaryRoll> {

    @Override
    protected void render(ReportApothecaryRoll report) {
  		int[] casualtyRoll = report.getCasualtyRoll();
  		if (ArrayTool.isProvided(casualtyRoll)) {
  			println(getIndent(), TextStyle.BOLD, "Apothecary used.");
  			Player<?> player = game.getPlayerById(report.getPlayerId());
  			StringBuilder status = new StringBuilder();
  			status.append("Casualty Roll [ ").append(casualtyRoll[0]).append(" ][ ").append(casualtyRoll[1]).append(" ]");
  			println(getIndent(), TextStyle.ROLL, status.toString());
  			PlayerState injury = report.getPlayerState();
  			print(getIndent() + 1, false, player);
  			status = new StringBuilder();
  			status.append(" ").append(injury.getDescription()).append(".");
  			println(getIndent() + 1, status.toString());
  			SeriousInjury seriousInjury = report.getSeriousInjury();
  			if (seriousInjury != null) {
				  if (report.getOriginalInjury() != null) {
					  status = new StringBuilder().append(player.getName()).append(" would have ")
						  .append(report.getOriginalInjury().getDescription())
						  .append(" but that stat cannot be reduced any further. So a different injury has been chosen randomly.");
					  println(getIndent() + 1, TextStyle.EXPLANATION, status.toString());
				  }
  				print(getIndent() + 1, false, player);
  				status = new StringBuilder();
  				status.append(" ").append(seriousInjury.getDescription()).append(".");
  				println(getIndent() + 1, status.toString());
  			}
  		}
    }
}
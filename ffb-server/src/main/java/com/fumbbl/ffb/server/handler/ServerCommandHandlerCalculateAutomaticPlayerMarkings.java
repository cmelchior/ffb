package com.fumbbl.ffb.server.handler;

import com.fumbbl.ffb.Pair;
import com.fumbbl.ffb.marking.AutoMarkingConfig;
import com.fumbbl.ffb.marking.MarkerGenerator;
import com.fumbbl.ffb.model.Game;
import com.fumbbl.ffb.net.NetCommandId;
import com.fumbbl.ffb.server.FantasyFootballServer;
import com.fumbbl.ffb.server.net.ReceivedCommand;
import com.fumbbl.ffb.server.net.commands.InternalServerCommandCalculateAutomaticPlayerMarkings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServerCommandHandlerCalculateAutomaticPlayerMarkings extends ServerCommandHandler {

	private final MarkerGenerator markerGenerator = new MarkerGenerator();

	protected ServerCommandHandlerCalculateAutomaticPlayerMarkings(FantasyFootballServer pServer) {
		super(pServer);
	}

	@Override
	public NetCommandId getId() {
		return NetCommandId.INTERNAL_CALCULATE_AUTOMATIC_PLAYER_MARKINGS;
	}

	@Override
	public boolean handleCommand(ReceivedCommand receivedCommand) {
		InternalServerCommandCalculateAutomaticPlayerMarkings commandCalculateAutomaticPlayerMarkings = (InternalServerCommandCalculateAutomaticPlayerMarkings) receivedCommand.getCommand();
		List<Game> games = commandCalculateAutomaticPlayerMarkings.getGames();
		AutoMarkingConfig config = commandCalculateAutomaticPlayerMarkings.getAutoMarkingConfig();

		List<Map<String, String>> markings = games.stream().map(game -> handleGame(game, config)).collect(Collectors.toList());
		getServer().getCommunication().sendMarkings(receivedCommand.getSession(), markings);
		return true;
	}

	private Map<String, String> handleGame(Game game, AutoMarkingConfig config) {
		Map<String, String> markings = new HashMap<>();
		Arrays.stream(game.getPlayers()).map(player -> new Pair<>(player.getId(), markerGenerator.generate(game, player, config, false))).forEach(pair -> markings.put(pair.getLeft(), pair.getRight()));
		return markings;
	}
}

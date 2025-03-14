package com.fumbbl.ffb.client.dialog;

import com.fumbbl.ffb.ClientMode;
import com.fumbbl.ffb.IIconProperty;
import com.fumbbl.ffb.client.FantasyFootballClient;
import com.fumbbl.ffb.client.StyleProvider;
import com.fumbbl.ffb.client.ui.swing.JButton;
import com.fumbbl.ffb.client.ui.swing.JLabel;
import com.fumbbl.ffb.dialog.DialogId;
import com.fumbbl.ffb.dialog.DialogPenaltyShootoutParameter;
import com.fumbbl.ffb.util.StringTool;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class DialogPenaltyShootout extends Dialog implements ActionListener {

	private static final Color HIGHLIGHT = Color.lightGray;

	private final JPanel rollPanel;
	private final JButton closeButton;
	private final int limit;

	private final Timer timer;
	private final DialogPenaltyShootoutParameter parameter;
	private final JPanel rootPanel;
	private int currentLimit = 1;

	private final DialogPenaltyShootoutHandler handler;

	public DialogPenaltyShootout(FantasyFootballClient pClient, DialogPenaltyShootoutParameter parameter, DialogPenaltyShootoutHandler handler) {
		super(pClient, "Penalty Shootout", false);

		this.parameter = parameter;
		this.handler = handler;

		closeButton = new JButton(dimensionProvider(), ClientMode.PLAYER.equals(pClient.getMode()) ? "Proceed to MVPs and upload" : "Close");
		closeButton.setAlignmentX(CENTER_ALIGNMENT);
		closeButton.setMargin(new Insets(3, 5, 3, 5));
		closeButton.addActionListener(this);

		rootPanel = new JPanel();
		rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));

		JPanel framedPanel = new JPanel();
		framedPanel.setLayout(new BoxLayout(framedPanel, BoxLayout.Y_AXIS));

		Border innerBorder = BorderFactory.createEmptyBorder(10, 20, 0, 20);
		Border middleBorder = BorderFactory.createLineBorder(Color.BLACK, 1, true);
		Border outerBorder = BorderFactory.createLineBorder(Color.WHITE, 5);
		framedPanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, BorderFactory.createCompoundBorder(middleBorder, innerBorder)));

		JLabel penaltyShootout = new JLabel(dimensionProvider(), "Penalty Shootout");
		penaltyShootout.setAlignmentX(CENTER_ALIGNMENT);
		framedPanel.add(penaltyShootout);
		if (ClientMode.PLAYER.equals(pClient.getMode())) {
			JLabel close = new JLabel(dimensionProvider(), "Close to continue");
			close.setAlignmentX(CENTER_ALIGNMENT);
			framedPanel.add(close);
		}

		rollPanel = new JPanel();
		framedPanel.add(rollPanel);
		rootPanel.add(framedPanel);

		getContentPane().add(rootPanel);

		timer = new Timer(2000, this);

		limit = parameter.getAwayRolls().size();

		populate();
		timer.start();
		pack();
		setLocationToCenter();
	}

	private void populate() {
		rollPanel.removeAll();
		rollPanel.setLayout(new GridLayout(currentLimit + 2, 3, 0, 5));


		List<JLabel> labels = new ArrayList<>();
		labels.add(headerLabel("Home", true));
		labels.add(new JLabel(dimensionProvider()));
		labels.add(headerLabel("Away", false));

		for (int i = 0; i < currentLimit; i++) {
			labels.addAll(rollLabels(parameter.getHomeRolls().get(i), parameter.getAwayRolls().get(i),
				parameter.getHomeWon().get(i), parameter.getDescriptions().get(i)));
		}

		if (currentLimit == this.limit) {
			timer.stop();
			labels.addAll(summaryLabels(parameter.getHomeScore(), parameter.getAwayScore(), parameter.homeTeamWins()));
			rootPanel.add(closeButton);
			rootPanel.add(Box.createVerticalStrut(5));

		} else {
			labels.add(new JLabel(dimensionProvider(), "X"));
			labels.add(new JLabel(dimensionProvider(), "Score"));
			labels.add(new JLabel(dimensionProvider(), "X"));
		}

		labels.forEach(this::addDecorated);

		pack();
		setLocationToCenter();
		if (getClient().getMode() == ClientMode.PLAYER) {
			handler.playSound(parameter.getHomeWon().get(currentLimit - 1) ? parameter.getWinningSound() : parameter.getLosingSound());
		}
		currentLimit++;
	}

	public DialogId getId() {
		return DialogId.PENALTY_SHOOTOUT;
	}

	private void addDecorated(JLabel label) {
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setOpaque(true);
		if (StringTool.isProvided(label.getText())) {
			Font font = label.getFont();
			label.setFont(new Font(font.getFamily(), Font.BOLD, font.getSize()));
		}
		rollPanel.add(label);
	}

	private JLabel headerLabel(String text, boolean home) {
		StyleProvider styleProvider = getClient().getUserInterface().getStyleProvider();
		JLabel label = new JLabel(dimensionProvider(), text);
		label.setForeground(home ? styleProvider.getHome() : styleProvider.getAway());
		return label;
	}


	private List<JLabel> summaryLabels(int homeRoll, int awayRoll, boolean homeWin) {
		List<JLabel> labels = new ArrayList<>();
		labels.add(new JLabel(dimensionProvider(), String.valueOf(homeRoll)));
		labels.add(new JLabel(dimensionProvider(), "Score"));
		labels.add(new JLabel(dimensionProvider(), String.valueOf(awayRoll)));

		JLabel loserLabel = labels.get(homeWin ? 2 : 0);
		loserLabel.setForeground(Color.LIGHT_GRAY);
		return labels;
	}

	private List<JLabel> rollLabels(int home, int away, boolean homeWin, String description) {
		List<JLabel> labels = new ArrayList<>();
		labels.add(new JLabel(dimensionProvider(), icon(home)));
		labels.add(new JLabel(dimensionProvider(), description));
		labels.add(new JLabel(dimensionProvider(), icon(away)));
		JLabel winnerLabel = labels.get(homeWin ? 0 : 2);
		winnerLabel.setBackground(HIGHLIGHT);
		winnerLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		return labels;
	}


	private Icon icon(int roll) {
		return new ImageIcon(getClient().getUserInterface().getIconCache().getIconByProperty(iconProperty(roll), dimensionProvider()));
	}

	private String iconProperty(int roll) {
		switch (roll) {
			case 1:
				return IIconProperty.PENALTY_DIE_1;
			case 2:
				return IIconProperty.PENALTY_DIE_2;
			case 3:
				return IIconProperty.PENALTY_DIE_3;
			case 4:
				return IIconProperty.PENALTY_DIE_4;
			case 5:
				return IIconProperty.PENALTY_DIE_5;
			default:
				return IIconProperty.PENALTY_DIE_6;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == closeButton) {
			if (getCloseListener() != null) {
				getCloseListener().dialogClosed(this);
			}
		} else {
			populate();
		}
	}
}

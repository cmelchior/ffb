package com.fumbbl.ffb.client.dialog;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.fumbbl.ffb.client.FantasyFootballClient;
import com.fumbbl.ffb.dialog.DialogId;
import com.fumbbl.ffb.model.Game;
import com.fumbbl.ffb.model.Roster;
import com.fumbbl.ffb.model.RosterPosition;
import com.fumbbl.ffb.util.StringTool;

/**
 * 
 * @author Kalimar
 */
public class DialogJourneymen extends Dialog implements ActionListener, KeyListener {

	private int fSlotsAvailable;
	private String[] fPositionIds;

	private List<JComboBox<String>> fBoxes;
	private int[] fSlotsSelected;

	private int fOldTeamValue;
	private int fNewTeamValue;

	private JLabel fLabelNewTeamValue;
	private JButton fButtonHire;

	public DialogJourneymen(FantasyFootballClient pClient, int pSlots, String[] pPositionIds) {

		super(pClient, "Hire Journeymen", false);

		fSlotsAvailable = pSlots;
		fPositionIds = pPositionIds;

		fSlotsSelected = new int[fPositionIds.length];

		fBoxes = new ArrayList<>();
		for (int i = 0; i < fPositionIds.length; i++) {
			fBoxes.add(new JComboBox<String>());
		}
		refreshModels();

		Game game = getClient().getGame();
		fOldTeamValue = game.getTeamHome().getTeamValue();
		fNewTeamValue = fOldTeamValue;
		Roster roster = game.getTeamHome().getRoster();

		JPanel boxPanel = new JPanel();
		boxPanel.setLayout(new GridLayout(0, 2, 5, 5));
		for (int i = 0; i < fBoxes.size(); i++) {
			RosterPosition rosterPosition = roster.getPositionById(fPositionIds[i]);
			JPanel boxLabelPanel = new JPanel();
			boxLabelPanel.setLayout(new BoxLayout(boxLabelPanel, BoxLayout.X_AXIS));
			boxLabelPanel.add(Box.createHorizontalGlue());
			boxLabelPanel
					.add(new JLabel(StringTool.isProvided(rosterPosition.getDisplayName()) ? rosterPosition.getDisplayName()
							: rosterPosition.getName()));
			boxPanel.add(boxLabelPanel);
			JPanel boxSelectPanel = new JPanel();
			boxSelectPanel.setLayout(new BoxLayout(boxSelectPanel, BoxLayout.X_AXIS));
			boxSelectPanel.add(fBoxes.get(i));
			boxSelectPanel.add(Box.createHorizontalGlue());
			boxPanel.add(boxSelectPanel);
		}

		StringBuilder oldTeamValueText = new StringBuilder();
		oldTeamValueText.append("Current Team Value is ").append(StringTool.formatThousands(fOldTeamValue / 1000))
				.append("k.");
		JLabel labelOldTeamValue = new JLabel(oldTeamValueText.toString());
		labelOldTeamValue
				.setFont(new Font(labelOldTeamValue.getFont().getName(), Font.BOLD, labelOldTeamValue.getFont().getSize()));

		JPanel oldTeamValuePanel = new JPanel();
		oldTeamValuePanel.setLayout(new BoxLayout(oldTeamValuePanel, BoxLayout.X_AXIS));
		oldTeamValuePanel.add(labelOldTeamValue);
		oldTeamValuePanel.add(Box.createHorizontalGlue());

		fLabelNewTeamValue = new JLabel();
		fLabelNewTeamValue
				.setFont(new Font(fLabelNewTeamValue.getFont().getName(), Font.BOLD, fLabelNewTeamValue.getFont().getSize()));
		updateLabelNewTeamValue();

		JPanel newTeamValuePanel = new JPanel();
		newTeamValuePanel.setLayout(new BoxLayout(newTeamValuePanel, BoxLayout.X_AXIS));
		newTeamValuePanel.add(fLabelNewTeamValue);
		newTeamValuePanel.add(Box.createHorizontalGlue());

		StringBuilder infoText = new StringBuilder();
		infoText.append("You may hire up to ").append(fSlotsAvailable).append(" Journeymen.");
		JLabel infoLabel = new JLabel(infoText.toString());

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
		infoPanel.add(infoLabel);
		infoPanel.add(Box.createHorizontalGlue());

		fButtonHire = new JButton("Hire");
		fButtonHire.addActionListener(this);
		fButtonHire.addKeyListener(this);
		fButtonHire.setMnemonic((int) 'H');

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(fButtonHire);
		buttonPanel.add(Box.createHorizontalGlue());

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.add(infoPanel);
		centerPanel.add(Box.createVerticalStrut(5));
		centerPanel.add(oldTeamValuePanel);
		centerPanel.add(Box.createVerticalStrut(5));
		centerPanel.add(newTeamValuePanel);
		centerPanel.add(Box.createVerticalStrut(5));
		centerPanel.add(boxPanel);
		centerPanel.add(Box.createVerticalStrut(5));
		centerPanel.add(buttonPanel);
		centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(centerPanel, BorderLayout.CENTER);

		pack();
		setLocationToCenter();

	}

	public DialogId getId() {
		return DialogId.JOURNEYMEN;
	}

	public int[] getSlotsSelected() {
		return fSlotsSelected;
	}

	public String[] getPositionIds() {
		return fPositionIds;
	}

	private void refreshModels() {
		int freeSlots = fSlotsAvailable;
		for (int i = 0; i < fSlotsSelected.length; i++) {
			freeSlots -= fSlotsSelected[i];
		}
		for (int i = 0; i < fBoxes.size(); i++) {
			String[] selection = new String[fSlotsSelected[i] + freeSlots + 1];
			for (int j = 0; j < selection.length; j++) {
				selection[j] = Integer.toString(j);
			}
			JComboBox<String> box = fBoxes.get(i);
			box.removeActionListener(this);
			box.setModel(new DefaultComboBoxModel<String>(selection));
			box.setSelectedIndex(fSlotsSelected[i]);
			box.setPreferredSize(box.getMinimumSize());
			box.addActionListener(this);
		}
	}

	public void actionPerformed(ActionEvent pActionEvent) {
		if (pActionEvent.getSource() == fButtonHire) {
			if (getCloseListener() != null) {
				getCloseListener().dialogClosed(this);
			}
		} else {
			for (int i = 0; i < fBoxes.size(); i++) {
				JComboBox box = fBoxes.get(i);
				if (pActionEvent.getSource() == box) {
					fSlotsSelected[i] = box.getSelectedIndex();
					break;
				}
			}
			refreshModels();
			fNewTeamValue = fOldTeamValue;
			Roster roster = getClient().getGame().getTeamHome().getRoster();
			for (int i = 0; i < fSlotsSelected.length; i++) {
				RosterPosition rosterPosition = roster.getPositionById(fPositionIds[i]);
				fNewTeamValue += (rosterPosition.getCost() * fSlotsSelected[i]);
			}
			updateLabelNewTeamValue();
		}
	}

	public void keyPressed(KeyEvent pKeyEvent) {
	}

	public void keyReleased(KeyEvent pKeyEvent) {
		if (pKeyEvent.getKeyCode() == KeyEvent.VK_H) {
			if (getCloseListener() != null) {
				getCloseListener().dialogClosed(this);
			}
		}
	}

	public void keyTyped(KeyEvent pKeyEvent) {
	}

	private void updateLabelNewTeamValue() {
		StringBuilder newTeamValueText = new StringBuilder();
		newTeamValueText.append("New Team Value is ").append(StringTool.formatThousands(fNewTeamValue / 1000)).append("k.");
		fLabelNewTeamValue.setText(newTeamValueText.toString());
	}

}

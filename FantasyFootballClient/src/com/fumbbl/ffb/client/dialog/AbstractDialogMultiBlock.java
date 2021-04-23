package com.fumbbl.ffb.client.dialog;

import com.fumbbl.ffb.client.FantasyFootballClient;
import com.fumbbl.ffb.client.IconCache;
import com.fumbbl.ffb.model.BlockRoll;
import com.fumbbl.ffb.model.Player;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDialogMultiBlock extends AbstractDialogBlock {

	protected final List<List<Integer>> keyEvents = new ArrayList<List<Integer>>() {{
		add(new ArrayList<Integer>() {{
			add(KeyEvent.VK_1);
			add(KeyEvent.VK_2);
			add(KeyEvent.VK_3);
		}});
		add(new ArrayList<Integer>() {{
			add(KeyEvent.VK_4);
			add(KeyEvent.VK_5);
			add(KeyEvent.VK_6);
		}});
	}};

	protected String selectedTarget;
	protected int selectedIndex = -1;


	public AbstractDialogMultiBlock(FantasyFootballClient pClient, String pTitle, boolean pCloseable) {
		super(pClient, pTitle, pCloseable);
	}

	protected JButton dieButton(int blockRoll) {
		IconCache iconCache = getClient().getUserInterface().getIconCache();

		JButton button = new JButton();
		button.setOpaque(false);
		button.setBounds(0, 0, 45, 45);
		button.setFocusPainted(false);
		button.setMargin(new Insets(5, 5, 5, 5));
		button.setIcon(new ImageIcon(iconCache.getDiceIcon(blockRoll)));
		return button;
	}

	protected JPanel dicePanel(BlockRoll blockRoll, boolean activeButtons, List<Integer> events) {
		JPanel panel = blockRollPanel();

		for (int i = 0; i < blockRoll.getBlockRoll().length; i++) {
			JButton dieButton = dieButton(blockRoll.getBlockRoll()[i]);

			if (activeButtons) {
				dieButton.setMnemonic(events.get(i));
				int index = i;
				dieButton.addActionListener(e -> {
					selectedTarget = blockRoll.getTargetId();
					selectedIndex = index;
					close();
				});
				dieButton.addKeyListener(new PressedKeyListener() {
					@Override
					public void keyPressed(KeyEvent e) {
						if (e.getKeyCode() == index) {
							selectedTarget = blockRoll.getTargetId();
							selectedIndex = index;
							close();
						}
					}
				});
			}
			if (!blockRoll.needsSelection()) {
				dieButton.setEnabled(i == blockRoll.getSelectedIndex());
			}
			panel.add(dieButton);
		}

		return panel;
	}

	protected JPanel namePanel(String target) {
		Player<?> defender = getClient().getGame().getPlayerById(target);
		JLabel nameLabel = new JLabel("<html>"+ defender.getName() +"</html>");
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setOpaque(false);
		JPanel panel = new JPanel();
		panel.add(nameLabel);
		panel.setAlignmentX(CENTER_ALIGNMENT);
		panel.setOpaque(false);
		return panel;
	}

	protected abstract void close();

	protected static abstract class PressedKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyReleased(KeyEvent e) {

		}
	}

}

package com.fumbbl.ffb.client.dialog;

import com.fumbbl.ffb.FantasyFootballConstants;
import com.fumbbl.ffb.IIconProperty;
import com.fumbbl.ffb.client.Component;
import com.fumbbl.ffb.client.DimensionProvider;
import com.fumbbl.ffb.client.FantasyFootballClient;
import com.fumbbl.ffb.client.ui.swing.JLabel;
import com.fumbbl.ffb.dialog.DialogId;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class DialogAbout extends Dialog {

	private static final String[] _PLAYTESTERS = {
		"Ale1972, Asharak, Avantar, Avien, Ballcrusher, Balle2000, Barre, Benjysmyth, BiggieB, Brainsaw, Calthor, Carnis, CircularLogic, Chavo, Clarkin, Cmelchior,",
		"Cyrus-Havoc, Don Tomaso, DanTitan76, DukeTyrion, Dynamo380, Ebenezer, Ehlers, Flix, Floppeditbackwards, Freak_in_a_Frock, Freppa, Gandresch, Garaygos, ",
		"Gjopie, Hangar18, Happygrue, Hitonagashi, Howlett, Janekt, JanMattys, Janzki, Jarvis_Pants, Java, JoeMalik, Koigokoro, LeBlanc, Lerysh, Lewdgrip, Razin, ",
		"Loraxwolfsbane, Louky, LoxleyAndy, Magistern, Malitrius, Mickael, Mtknight,MxFr, Nazgob, Neilwat, Nelphine, Nighteye, On1, PhrollikK, Purplegoo, RamonSalazar, Ravenmore, ",
		"RedDevilCG, Reisender, Relezite, Shadow46x2, Sl8, Stej, Steve, Stimme, Svemole, SvenS, Tarabaralla, Teluriel, Tensai, Thul, Tortured-Robot, ",
		"Treborius, Tussock, Ulrik, Ultwe, Uomotigre3, Uuni, Vesikannu, Woodstock, XZCion, Zakatan"};

	public DialogAbout(FantasyFootballClient pClient) {

		super(pClient, "About Fantasy Football", true);

		DimensionProvider dimensionProvider = pClient.getUserInterface().getUiDimensionProvider();
		JLabel aboutLabel = new JLabel(dimensionProvider, createAboutImageIcon(pClient, dimensionProvider));

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(aboutLabel, BorderLayout.CENTER);

		pack();

		setLocationToCenter();

	}

	public DialogId getId() {
		return DialogId.ABOUT;
	}

	public void internalFrameClosing(InternalFrameEvent pE) {
		if (getCloseListener() != null) {
			getCloseListener().dialogClosed(this);
		}
	}

	private ImageIcon createAboutImageIcon(FantasyFootballClient pClient, DimensionProvider dimensionProvider) {
		Dimension dimension = dimensionProvider.dimension(Component.ABOUT_DIALOG);

		BufferedImage aboutImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = aboutImage.createGraphics();
		g2d.drawImage(pClient.getUserInterface().getIconCache().getIconByProperty(IIconProperty.GAME_SPLASH_SCREEN, dimensionProvider), 0, 0,
			aboutImage.getWidth(), aboutImage.getHeight(), null);

		g2d.setColor(Color.WHITE);

		g2d.setFont(fontCache().font(Font.BOLD, 17, dimensionProvider));

		String versionInfo = getClient().getParameters().getBuild();
		if (versionInfo == null) {
			versionInfo = "Version " + FantasyFootballConstants.VERSION;
		} else {
			versionInfo = "Build " + FantasyFootballConstants.VERSION + "-" + versionInfo;
		}
		int offsetBoldLine = 24;
		int offsetLine = 19;
		Rectangle2D versionBounds = g2d.getFontMetrics().getStringBounds(versionInfo, g2d);
		g2d.drawString(versionInfo, dimension.width - dimensionProvider.scale(offsetBoldLine) - (int) versionBounds.getWidth(), dimensionProvider.scale(155));

		int y = 130;

		drawBold(g2d, dimensionProvider.scale(10), dimensionProvider.scale(y += 0), "Headcoach: BattleLore", dimensionProvider);
		drawText(g2d, dimensionProvider.scale(offsetLine), dimensionProvider.scale(y += offsetLine), "thank you for providing ideas, encouragement and the occasional kick in the butt.", dimensionProvider);

		drawBold(g2d, dimensionProvider.scale(10), dimensionProvider.scale(y += offsetBoldLine), "Assistant Coaches: WhatBall, Garion and Lakrillo", dimensionProvider);
		drawText(g2d, dimensionProvider.scale(offsetLine), dimensionProvider.scale(y += offsetLine), "thank you for helping to to pull the cart along.", dimensionProvider);

		drawBold(g2d, dimensionProvider.scale(10), dimensionProvider.scale(y += offsetBoldLine), "Sports Director: Christer", dimensionProvider);
		drawText(g2d, dimensionProvider.scale(offsetLine), dimensionProvider.scale(y += offsetLine), "thank you for the patience and energy to tackle the long road with me.", dimensionProvider);

		drawBold(g2d, dimensionProvider.scale(10), dimensionProvider.scale(y += offsetBoldLine), "Lifetime Luxury Suite Owner: SkiJunkie", dimensionProvider);
		drawText(g2d, dimensionProvider.scale(offsetLine), dimensionProvider.scale(y += offsetLine), "thank you doing it first and giving a vision to follow.", dimensionProvider);

		drawBold(g2d, dimensionProvider.scale(10), dimensionProvider.scale(y += offsetBoldLine), "Grounds Keepers: Java, Tussock", dimensionProvider);

		drawBold(g2d, dimensionProvider.scale(10), dimensionProvider.scale(y += offsetBoldLine),
			"Light Show by: Cowhead, F_alk, FreeRange, Harvestmouse, Knut_Rockie, MisterFurious and Ryanfitz", dimensionProvider);
		drawBold(g2d, dimensionProvider.scale(10), dimensionProvider.scale(y += offsetLine), "Playing the Stadium Organ: VocalVoodoo and Minenbonnie", dimensionProvider);
		drawBold(g2d, dimensionProvider.scale(10), dimensionProvider.scale(y += offsetLine), "Official supplier of game balls: Qaz", dimensionProvider);
		drawBold(g2d, dimensionProvider.scale(10), dimensionProvider.scale(y += offsetLine), "Thanks for the hats: ArrestedDevelopment", dimensionProvider);
		drawText(g2d, dimensionProvider.scale(offsetLine), dimensionProvider.scale(y += offsetLine), "thank you all for making FFB look and sound great.", dimensionProvider);

		drawBold(g2d, dimensionProvider.scale(10), dimensionProvider.scale(y += offsetBoldLine), "Cheerleaders & Pest Control:", dimensionProvider);

		y += 3;
		for (String playtester : _PLAYTESTERS) {
			drawSmallText(g2d, dimensionProvider.scale(18), dimensionProvider.scale(y += 13), playtester, dimensionProvider);
		}

		g2d.dispose();

		return new ImageIcon(aboutImage);

	}


	private void drawSizedText(Graphics2D pG2d, int pX, int pY, String pText, int size, DimensionProvider dimensionProvider) {
		pG2d.setFont(fontCache().font(Font.PLAIN, size, dimensionProvider));
		pG2d.drawString(pText, pX, pY);
	}

	private void drawText(Graphics2D pG2d, int pX, int pY, String pText, DimensionProvider dimensionProvider) {
		drawSizedText(pG2d, pX, pY, pText, 12, dimensionProvider);
	}

	private void drawSmallText(Graphics2D pG2d, int pX, int pY, String pText, DimensionProvider dimensionProvider) {
		drawSizedText(pG2d, pX, pY, pText, 10, dimensionProvider);
	}

	private void drawBold(Graphics2D pG2d, int pX, int pY, String pText, DimensionProvider dimensionProvider) {
		pG2d.setFont(fontCache().font(Font.BOLD, 12, dimensionProvider));
		pG2d.drawString(pText, pX, pY);
	}

	protected void setLocationToCenter() {
		Dimension dialogSize = getSize();
		Dimension frameSize = getClient().getUserInterface().getSize();
		Dimension menuBarSize = getClient().getUserInterface().getGameMenuBar().getSize();
		setLocation((frameSize.width - dialogSize.width) / 2,
				((frameSize.height - dialogSize.height) / 2) - menuBarSize.height);
	}

}

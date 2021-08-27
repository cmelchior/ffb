package com.fumbbl.ffb.client.ui;

import java.awt.event.MouseEvent;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;

import com.fumbbl.ffb.FantasyFootballException;
import com.fumbbl.ffb.client.ParagraphStyle;
import com.fumbbl.ffb.client.TextStyle;

/**
 * 
 * @author Kalimar
 */
public class ChatLogTextPane extends JTextPane {

	private ChatLogDocument fChatLogDocument;
	private IReplayMouseListener fReplayMouseListener;

	public ChatLogTextPane() {
		setEditable(false);
		((DefaultCaret) getCaret()).setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		detachDocument();
		attachDocument();
	}

	public ChatLogDocument getChatLogDocument() {
		return fChatLogDocument;
	}

	public void addReplayMouseListener(IReplayMouseListener pReplayMouseListener) {
		fReplayMouseListener = pReplayMouseListener;
	}

	public void removeReplayMouseListener() {
		fReplayMouseListener = null;
	}

	protected void processMouseEvent(MouseEvent pMouseEvent) {
		if (fReplayMouseListener != null) {
			if (MouseEvent.MOUSE_PRESSED == pMouseEvent.getID()) {
				int position = viewToModel(pMouseEvent.getPoint());
				fReplayMouseListener.mousePressedForReplay(position);
			}
		} else {
			super.processMouseEvent(pMouseEvent);
		}
	}

	public void detachDocument() {
		fChatLogDocument = new ChatLogDocument();
	}

	public void attachDocument() {
		setDocument(fChatLogDocument);
	}

	public void append(ParagraphStyle pTextIndent, TextStyle pStyle, String pText) {

		try {

			if (pText != null) {

				if (pStyle == null) {
					pStyle = TextStyle.NONE;
				}
				if (pTextIndent == null) {
					pTextIndent = ParagraphStyle.INDENT_0;
				}

				fChatLogDocument.setParagraphAttributes(fChatLogDocument.getLength(), 1,
						fChatLogDocument.getStyle(pTextIndent.getName()), false);
				fChatLogDocument.insertString(fChatLogDocument.getLength(), pText, fChatLogDocument.getStyle(pStyle.getName()));

			} else {
				fChatLogDocument.insertString(fChatLogDocument.getLength(), ChatLogDocument.LINE_SEPARATOR,
						fChatLogDocument.getStyle(TextStyle.NONE.getName()));
			}

		} catch (BadLocationException ex) {
			throw new FantasyFootballException(ex);
		}

	}

}

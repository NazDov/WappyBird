package com.wappybird.run;

import java.applet.Applet;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WBGameRunner {

	public static void main(String[] args) {
		Frame wbGameFrame = new Frame();
		wbGameFrame.setSize(850,580);
		final Applet wbGameView = new WBGameView();
		wbGameFrame.add(wbGameView);
		wbGameFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				wbGameView.stop();
				wbGameView.destroy();
				System.exit(0);
				super.windowClosed(e);
			}
		});
		wbGameFrame.setVisible(true);
		wbGameFrame.setResizable(false);
		wbGameView.init();
		wbGameView.start();
		
		
	}

}

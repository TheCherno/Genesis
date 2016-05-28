package com.thecherno.ld24;

import java.applet.Applet;
import java.awt.BorderLayout;

public class GameApplet extends Applet {
	private static final long serialVersionUID = 1L;

	Game game = new Game();

	public void init() {
		setLayout(new BorderLayout());
		add(game);
	}

	public void start() {
		game.start();
	}

	public void stop() {
		game.stop();
	}

}

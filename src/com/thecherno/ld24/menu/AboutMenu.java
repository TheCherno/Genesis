package com.thecherno.ld24.menu;

import com.thecherno.ld24.Game;
import com.thecherno.ld24.graphics.Screen;
import com.thecherno.ld24.input.InputHandler;
import com.thecherno.ld24.sound.Sound;

public class AboutMenu extends Menu {

	public AboutMenu(InputHandler input) {
		super(input);
	}

	int timer = 18;
	String options = "> Back <";
	int selected = 0;

	public void update() {
		if (timer > 0) timer--;

		if (selected == 0 && input.use && timer == 0) {
			input.releaseAll();
			Sound.menu.play(false);
			Game.menu = new MainMenu(input);
		}
		if (input.back) {
			Sound.menu.play(false);
			Game.menu = new MainMenu(input);
		}
	}

	public void render(Screen screen) {
		String[] text = { "Genesis is a free game made by Yan Chernikov (The Cherno), for ", //
				"Ludum Dare, a 48 hour game development competition. This game", //
				"was made in just 20 hours however, due to me finishing Year 12, my",//
				"last year of school, and having tons of work to do. The theme for the",//
				"competition was \"Evolution\". All assets were made within 48 hours.",//
				" ",//
				"This was made for the 24th Ludum Dare compo, my first ever entry for", "Ludum Dare. Any questions, you can contact me by sending an email",//
				"to y@nchernikov.com, or checking out my YouTube channel,",//
				"youtube.com/thechernoproject, for more information, and to watch the",//
				"recorded live stream of the entire game being made.", "",//
				"Thanks for playing!" };
		for (int i = 0; i < text.length; i++) {
			screen.renderText(text[i], 20 + 2, 50 + i * 30 + 2, 24, 0, 0);
			screen.renderText(text[i], 20, 50 + i * 30, 24, 0, 0xffffff);
		}
		screen.renderText(options, 350 + 3, 500 + 3, 50, 1, 0);
		screen.renderText(options, 350, 500, 50, 1, 0xffffff);
	}
}

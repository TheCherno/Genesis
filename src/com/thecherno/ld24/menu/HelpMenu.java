package com.thecherno.ld24.menu;

import com.thecherno.ld24.Game;
import com.thecherno.ld24.graphics.Screen;
import com.thecherno.ld24.input.InputHandler;
import com.thecherno.ld24.sound.Sound;

public class HelpMenu extends Menu {

	public HelpMenu(InputHandler input) {
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
		String[] text = { "Hey what is up guys, my name is The Cherno, and welcome to a game.", //
				"The game has quite a simple concept. Use the arrow keys, or WASD",//
				"keys (if that's how you roll) to move your character. Your aim is to",//
				"evolve your colony; you must find women and walk through them,", //
				"creating a baby (or two). Everytime you do this, the rate of growth that", //
				"your population will experience is increased. Which means more women.", //
				"But here's the catch: you only have five minutes to build the largest",//
				"population this world has ever seen. Also, you can't breed with the same",//
				"woman twice. Women who have a green tag on them mean they're",//
				"available for breeding, while red means you've already nailed them. ;)",//
				"Women can be hard to find, however they always spawn in a similar",//
				"location.",//
				" ", "So try and find as many women as you can, to help grow the largest",//
				"population ever, evolving your nation to the maximum!" };
		for (int i = 0; i < text.length; i++) {
			screen.renderText(text[i], 10 + 2, 30 + i * 30 + 2, 24, 0, 0);
			screen.renderText(text[i], 10, 30 + i * 30, 24, 0, 0xffffff);
		}
		screen.renderText(options, 350 + 3, 515 + 3, 50, 1, 0);
		screen.renderText(options, 350, 515, 50, 1, 0xffffff);
	}
}

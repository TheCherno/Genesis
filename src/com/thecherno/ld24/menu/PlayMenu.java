package com.thecherno.ld24.menu;

import com.thecherno.ld24.Game;
import com.thecherno.ld24.graphics.Screen;
import com.thecherno.ld24.input.InputHandler;

public class PlayMenu extends Menu {

	public PlayMenu(InputHandler input) {
		super(input);
	}

	int timer = 10;
	String[] options = { "Forest", "Snow", "Ocean", "Desert", "Random" };
	int selected = 0;
	public static int biome = -1;

	public void update() {
		if (timer > 0) timer--;

		if (input.down && selected < options.length - 1 && timer == 0) {
			selected++;
			timer = 10;
		}
		if (input.up && selected > 0 && timer == 0) {
			selected--;
			timer = 10;
		}

		if (selected < 0) selected = 0;
		if (selected > options.length - 1) selected = options.length - 1;

		if (selected == 0) {
			options[selected] = "> " + "Forest" + " <";
			if (input.use) {
				biome = 0;
			}
		} else {
			options[0] = "Forest";
		}
		if (selected == 1) {
			options[selected] = "> " + "Snow" + " <";
			if (input.use) {
				biome = 1;
			}
		} else {
			options[1] = "Snow";
		}
		if (selected == 2) {
			options[selected] = "> " + "Ocean" + " <";
			if (input.use) {
				biome = 2;
			}
		} else {
			options[2] = "Ocean ";
		}
		if (selected == 3) {
			options[selected] = "> " + "Desert" + " <";
			if (input.use) {
				biome = 3;
			}
		} else {
			options[3] = "Desert ";
		}
		if (selected == 4) {
			options[selected] = "> " + "Random" + " <";
			if (input.use) {
				biome = random.nextInt(3);
			}
		} else {
			options[4] = "Random";
		}

		if (input.back) Game.menu = new MainMenu(input);
		if (input.use && timer == 0) Game.menu = null;
	}

	public void render(Screen screen) {
		screen.renderText("Select Biome", 280 + 1, 80 + 1, 50, 1, 0xffffff);
		screen.renderText("Select Biome", 280, 80, 50, 1, 0);
		for (int i = 0; i < options.length; i++) {
			screen.renderText(options[i], 350 + 3, 250 + i * 60 + 3, 50, 1, 0);
			screen.renderText(options[i], 350, 250 + i * 60, 50, 1, 0xffffff);
		}
	}

}

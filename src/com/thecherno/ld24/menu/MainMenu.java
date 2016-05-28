package com.thecherno.ld24.menu;

import com.thecherno.ld24.Game;
import com.thecherno.ld24.graphics.Screen;
import com.thecherno.ld24.input.InputHandler;
import com.thecherno.ld24.level.Level;
import com.thecherno.ld24.sound.Sound;

public class MainMenu extends Menu {

	public MainMenu(InputHandler input) {
		super(input);
	}

	int timer = 18;
	String[] options = { "Play", "Help", "About" };
	int selected = 0;

	public void update() {
		if (timer > 0) timer--;
		if (input.down && selected < options.length && timer == 0) {
			selected++;
			Sound.menucycle.play(false);
			timer = 10;
		}
		if (input.up && selected > 0 && timer == 0) {
			selected--;
			Sound.menucycle.play(false);
			timer = 10;
		}

		if (selected < 0) selected = 0;
		if (selected > 2) selected = 2;

		if (selected == 0) {
			options[selected] = "> " + "Play" + " <";
		} else {
			options[0] = "Play";
		}
		if (selected == 1) {
			options[selected] = "> " + "Help" + " <";

			if (input.use) {
				Sound.menu.play(false);
				Game.menu = new HelpMenu(input);
			}
		} else {
			options[1] = "Help";
		}
		if (selected == 2) {
			options[selected] = "> " + "About" + " <";
			if (input.use) {
				Sound.menu.play(false);
				Game.menu = new AboutMenu(input);
			}
		} else {
			options[2] = "About ";
		}

		if (selected == 0 && input.use && timer == 0) {
			input.releaseAll();
			Sound.menutheme.stop();
			Sound.theme.play(false);
			Sound.start.play(false);
			Level.play = true;
			Game.menu = null;
			PlayMenu.biome = 2; //random.nextInt(3);
		}
	}

	public void render(Screen screen) {
		screen.renderText("Genesis", 170 + 4, 140 + 4, 120, 1, 0);
		screen.renderText("Genesis", 170, 140, 120, 1, 0xffffff);
		screen.renderText("A game by Yan Chernikov.", 250 + 2, 188 + 2, 30, 1, 0);
		screen.renderText("A game by Yan Chernikov.", 250, 188, 30, 1, 0xffffff);
		for (int i = 0; i < options.length; i++) {
			screen.renderText(options[i], 350 + 3, 350 + i * 60 + 3, 50, 1, 0);
			screen.renderText(options[i], 350, 350 + i * 60, 50, 1, 0xffffff);
		}
	}
}

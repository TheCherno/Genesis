package com.thecherno.ld24.menu;

import com.thecherno.ld24.Game;
import com.thecherno.ld24.entity.Population;
import com.thecherno.ld24.graphics.Screen;
import com.thecherno.ld24.input.InputHandler;
import com.thecherno.ld24.level.Level;
import com.thecherno.ld24.level.SnowLevel;
import com.thecherno.ld24.sound.Sound;

public class GameOverMenu extends Menu {

	public GameOverMenu(InputHandler input) {
		super(input);
		Sound.gameover.play(false);
		Game.pop = null;
	}

	int timer = 10;
	String[] options = { "Play Again?", "Main Menu" };
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
		if (selected > 1) {
			selected = 1;
		}
		if (selected == 0) {
			options[selected] = "> " + "Play Again?" + " <";
			if (input.use) {
				Sound.start.play(false);
				Game.level.destroy();
				input.releaseAll();
				Level.play = true;
				Game.menu = null;
				PlayMenu.biome = random.nextInt(3);
			}
		} else {
			options[0] = "Play Again?";
		}
		if (selected == 1) {
			options[selected] = "> " + "Main Menu" + " <";
			if (input.use) {
				Sound.menu.play(false);
				Game.menu = new MainMenu(input);
				Game.level.destroy();
				Game.setMenuLevel();
			}
		} else {
			options[1] = "Main Menu";
		}

	}

	public void render(Screen screen) {
		int col = 0xffffff;
		if (Game.level instanceof SnowLevel) col = 0;
		if (col != 0) screen.renderText("Game Over!", 240 - 20, 100 + 3, 75, 1, 0);
		screen.renderText("Game Over!", 240 - 20, 100, 75, 1, col);
		String text = "Your population: " + (long) Population.population;
		int length = text.length();
		if (col != 0) screen.renderText("Tweet your score @TheCherno!", 252 + 3, 140 + 3, 25, 1, 0);
		screen.renderText("Tweet your score @TheCherno!", 252, 140, 25, 1, col);
		if (col != 0) screen.renderText(text, 450 - length * 12 + 3, 250 + 3, 35, 1, 0);
		screen.renderText(text, 450 - length * 12, 250, 35, 1, col);
		for (int i = 0; i < options.length; i++) {
			if (col != 0) screen.renderText(options[i], 300 + 3, 350 + i * 60 + 3, 50, 1, 0);
			screen.renderText(options[i], 300, 350 + i * 60, 50, 1, col);
		}
	}
}

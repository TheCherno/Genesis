package com.thecherno.ld24.menu;

import java.util.Random;

import com.thecherno.ld24.Game;
import com.thecherno.ld24.graphics.Screen;
import com.thecherno.ld24.input.InputHandler;

public class Menu {

	protected InputHandler input;
	protected Game game;
	protected final Random random = new Random();

	public Menu(InputHandler input) {
		this.input = input;
	}

	public Menu(Game game) {
		this.game = game;
	}

	public void update() {
	}

	public void render(Screen screen) {
	}
}
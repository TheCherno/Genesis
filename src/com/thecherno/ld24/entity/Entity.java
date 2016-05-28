package com.thecherno.ld24.entity;

import java.util.Random;

import com.thecherno.ld24.graphics.Screen;
import com.thecherno.ld24.level.Level;

public class Entity {

	public int x, y;
	public boolean removed = false;
	protected Level level;
	public int health = 100;
	public boolean hurt = false;
	protected final Random random = new Random();

	public void update() {
	}

	public void render(Screen screen) {
	}

	public void remove() {
		level.remove(this);
		removed = true;
	}

	public final void init(Level level) {
		this.level = level;
	}

}

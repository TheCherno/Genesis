package com.thecherno.ld24.level.tile;

import java.util.Random;

import com.thecherno.ld24.graphics.Screen;
import com.thecherno.ld24.graphics.Sprite;

public class WaterTile extends Tile {

	static int anim = 0;
	static int s = 1;
	public static Sprite sprite;
	final Random random = new Random();

	public WaterTile(Sprite sprite) {
		super(sprite);
	}

	public static void update() {
		anim++;
		if (anim % 48 == 0) {
			s *= -1;
		}
		if (s == -1) sprite = Sprite.water;
		if (s == 1) sprite = Sprite.water2;
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, sprite);
	}

	public boolean solid() {
		return true;
	}
}

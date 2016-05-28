package com.thecherno.ld24.level.tile;

import com.thecherno.ld24.graphics.Screen;
import com.thecherno.ld24.graphics.Sprite;

public class VoidTile extends Tile {

	public VoidTile(Sprite sprite) {
		super(sprite);
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}

}

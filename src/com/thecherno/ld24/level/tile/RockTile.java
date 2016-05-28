package com.thecherno.ld24.level.tile;

import com.thecherno.ld24.graphics.Screen;
import com.thecherno.ld24.graphics.Sprite;

public class RockTile extends Tile {

	public RockTile(Sprite sprite) {
		super(sprite);
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}

	public boolean solid() {
		return true;
	}

}

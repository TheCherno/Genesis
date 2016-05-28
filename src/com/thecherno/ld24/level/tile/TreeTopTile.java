package com.thecherno.ld24.level.tile;

import com.thecherno.ld24.graphics.Screen;
import com.thecherno.ld24.graphics.Sprite;

public class TreeTopTile extends Tile {

	public int x, y;

	public TreeTopTile(int x, int y, Sprite sprite) {
		super(sprite);
		this.x = x;
		this.y = y;
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile((this.x) << 4, (this.y) << 4, Sprite.tree0);
		screen.renderTile((this.x + 1) << 4, (this.y) << 4, Sprite.tree1);
	}

	public boolean solid() {
		return true;
	}

}

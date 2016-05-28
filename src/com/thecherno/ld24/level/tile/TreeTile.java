package com.thecherno.ld24.level.tile;

import com.thecherno.ld24.graphics.Screen;
import com.thecherno.ld24.graphics.Sprite;

public class TreeTile extends Tile {

	public int x, y;

	public TreeTile(int x, int y, Sprite sprite) {
		super(sprite);
		this.x = x;
		this.y = y;
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile((this.x) << 4, (this.y + 1) << 4, Sprite.tree2);
		screen.renderTile((this.x + 1) << 4, (this.y + 1) << 4, Sprite.tree3);
	}

	public boolean solid() {
		return true;
	}

}

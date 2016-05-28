package com.thecherno.ld24.level.tile;

import java.util.ArrayList;
import java.util.List;

import com.thecherno.ld24.graphics.Screen;
import com.thecherno.ld24.level.Level;

public class LightTile {

	public int x, y;
	private Tile tile;

	public static List<LightTile> lightTiles = new ArrayList<LightTile>();

	public LightTile(int x, int y) {
		this.x = x;
		this.y = y;
		lightTiles.add(this);
	}

	public void render(int x, int y, Screen screen, Level level) {
		int tb = Level.brightness;
		if (tb < -240) tb = -240;
		if (tb > 0) tb = 0;
		for (int yy = -3; yy <= 3; yy++) {
			for (int xx = -3; xx <= 3; xx++) {
				for (int i = 0; i < 4; i++) {
					int xt = ((x << 4) + (i % 2 * 2 - 1) * 7) >> 4;
					int yt = ((y << 4) + (i / 2 * 2 - 1) * 7) >> 4;
					tile = level.getTile(xt + xx, yt + yy);
				}
				int xTile = x * 16 + xx * 16;
				int yTile = y * 16 + yy * 16;
				double r = Math.abs((Math.pow(xx * Math.PI, 2)) + Math.abs(Math.pow(yy * Math.PI, 2)));
				double intensity = r * tb * 0.00012;
				if (r < 100) screen.renderLight(xTile, yTile, tile, 62, 64, 65, intensity);
			}
		}

	}
}

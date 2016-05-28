package com.thecherno.ld24.graphics;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.thecherno.ld24.Game;
import com.thecherno.ld24.entity.Entity;
import com.thecherno.ld24.entity.mob.Female;
import com.thecherno.ld24.entity.mob.Player;
import com.thecherno.ld24.level.Level;

public class Map extends Canvas {

	int width = 128;
	int height = 128;
	int scale = 2;

	BufferedImage map = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	int[] pixels = ((DataBufferInt) map.getRaster().getDataBuffer()).getData();

	public void render(Graphics g, Level level) {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
		for (int i = 0; i < level.entities.size(); i++) {
			Entity e = level.entities.get(i);
			int x = e.x >> 4;
			int y = e.y >> 4;
			if (e instanceof Female) {
				if (!(((Female) e).canSpawnChild)) pixels[x + y * width] = 0x1D6696;
				if ((((Female) e).canSpawnChild)) pixels[x + y * width] = 0xD43977;

			} else if (e instanceof Player) {
				pixels[x + y * width] = 0xffffff;
			} else {
				pixels[x + y * width] = 0x3B961D;
			}

		}

		g.drawImage(map, Game.WIDTH * Game.SCALE - width * scale, Game.HEIGHT * Game.SCALE - height * scale, width * scale, height * scale, null);
	}

}

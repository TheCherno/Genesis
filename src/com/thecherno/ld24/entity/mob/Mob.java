package com.thecherno.ld24.entity.mob;

import java.util.List;

import com.thecherno.ld24.entity.Entity;
import com.thecherno.ld24.graphics.Sprite;
import com.thecherno.ld24.level.tile.LightTile;

public class Mob extends Entity {

	public Sprite sprite;
	protected int dir = 0;
	public double lightDist = -1;
	protected boolean walking = false;

	public void move(int xa, int ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}
		if (xa > 0) dir = 1;
		if (xa < 0) dir = 3;
		if (ya > 0) dir = 2;
		if (ya < 0) dir = 0;

		if ((!collision(xa, ya) && !collision2(xa, ya))) {
			x += xa;
			y += ya;
		}
	}

	public void update() {
	}

	private boolean collision(int xa, int ya) {
		boolean solid = false;
		for (int i = 0; i < 4; i++) {
			int xt = ((x + xa) + (i % 2 * 2) * 5) >> 4;
			int yt = ((y + ya) + (i / 2 * 2 - 4) * 2) >> 4;
			if (level.getTile(xt, yt + 1).solid()) solid = true;
		}
		return solid;
	}

	private boolean collision2(int xa, int ya) {
		boolean solid = false;
		for (int i = 0; i < 4; i++) {
			int xt = ((x + xa) + (i % 2 * 2 - 1) * 4) >> 4;
			int yt = ((y + ya) + (i / 2 * 2 - 1) * 4) >> 4;
			if (level.getTile2(xt, yt).solid()) solid = true;
		}
		return solid;
	}

	public void renderLighting(List<LightTile> lt) {
		int lightSize = 48;
		for (int i = 0; i < lt.size(); i++) {
			int lx0 = lt.get(i).x << 4;
			int ly0 = lt.get(i).y << 4;
			int lx1 = (lt.get(i).x << 4) + 16;
			int ly1 = (lt.get(i).y << 4) + 16;

			int cx = (lx0 + lx1) / 2;
			int cy = (ly0 + ly1) / 2;

			if (this.x > lx0 - lightSize && this.y > ly0 - lightSize && this.x < lx1 + lightSize && this.y < ly1 + lightSize) {
				for (int y = ly0 - lightSize; y < ly1 + lightSize; y++) {
					for (int x = lx0 - lightSize; x < lx1 + lightSize; x++) {
						boolean b1 = (this.y < ly0 - 32) && (this.x < lx0 - 16 || this.x > lx1 + 16);
						boolean b2 = (this.y < ly0 - 16) && (this.x < lx0 - 32 || this.x > lx1 + 32);
						boolean b3 = (this.y < ly0 + 32) && (this.x < lx0 - 32 || this.x > lx1 + 32);
						boolean b4 = (this.y < ly0 + 48) && (this.x < lx0 - 16 || this.x > lx1 + 16);
						if (b1 || b2 || b3 || b4) break;
						lightDist = Math.sqrt(Math.pow(Math.abs(this.x - cx), 2) + Math.pow(Math.abs(this.y - cy), 2));
					}
				}
			}
			if (lightDist > 100) lightDist = -1;
		}
	}

}

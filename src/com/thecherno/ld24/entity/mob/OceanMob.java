package com.thecherno.ld24.entity.mob;

import com.thecherno.ld24.graphics.Screen;
import com.thecherno.ld24.graphics.Sprite;
import com.thecherno.ld24.level.tile.LightTile;

public class OceanMob extends Mob {

	int spriteFlip = 0;
	int xa, ya;

	public OceanMob() {
		x = random.nextInt(20 * 16) + 54 * 16;
		y = 23 * 16;
	}

	public void update() {
		if (random.nextInt(40) == 0) {
			xa = random.nextInt(3) - 1;
			ya = random.nextInt(3) - 1;
		}
		if (xa != 0 || ya != 0) {
			walking = true;
			move(xa, ya);
		} else {
			walking = false;
		}
	}

	public void render(Screen screen) {
		if (dir == 0) {
			sprite = Sprite.player0;
			spriteFlip = 0;
		}
		if (dir == 1) {
			sprite = Sprite.player4;
			spriteFlip = 0;
		}
		if (dir == 2) {
			sprite = Sprite.player2;
			spriteFlip = 0;
		}
		if (dir == 3) {
			sprite = Sprite.player4;
			spriteFlip = 1;
		}
		screen.renderMob(x, y, this, spriteFlip, false);
		renderLighting(LightTile.lightTiles);
	}
}

package com.thecherno.ld24.entity.mob;

import java.util.Random;

import com.thecherno.ld24.graphics.Screen;
import com.thecherno.ld24.graphics.Sprite;
import com.thecherno.ld24.level.tile.LightTile;

public class Female extends Mob {
	int spriteFlip = 0;
	final Random random = new Random();
	int xa, ya;
	int anim = 0;
	public boolean canSpawnChild = true;

	public Female() {
		x = 11 * 16;
		y = 99 * 16;
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
		if (health <= 0) remove();
	}

	public void render(Screen screen) {
		anim++;
		if (dir == 0) {
			sprite = Sprite.female0;
			spriteFlip = 0;
			if (walking) {
				sprite = Sprite.female1;
				if (anim % 250 > 125) {
					spriteFlip = 0;
				} else {
					spriteFlip = 1;
				}
			}
		}
		if (dir == 1) {
			sprite = Sprite.female4;
			spriteFlip = 0;
			if (walking) {
				if (anim % 250 > 125) {
					sprite = Sprite.female4;
				} else {
					sprite = Sprite.female5;
				}
			}
		}
		if (dir == 2) {
			sprite = Sprite.female2;
			spriteFlip = 0;
			if (walking) {
				sprite = Sprite.female3;
				if (anim % 250 > 125) {
					spriteFlip = 0;
				} else {
					spriteFlip = 1;
				}
			}

		}
		if (dir == 3) {
			sprite = Sprite.female4;
			spriteFlip = 1;
			if (walking) {
				if (anim % 250 > 125) {
					sprite = Sprite.female4;
				} else {
					sprite = Sprite.female5;
				}
			}
		}
		screen.renderMob(x, y, this, spriteFlip, false);
		renderLighting(LightTile.lightTiles);
	}
}

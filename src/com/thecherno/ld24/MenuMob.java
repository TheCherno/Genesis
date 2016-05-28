package com.thecherno.ld24;

import com.thecherno.ld24.entity.mob.Mob;
import com.thecherno.ld24.graphics.Screen;
import com.thecherno.ld24.graphics.Sprite;
import com.thecherno.ld24.level.tile.LightTile;

public class MenuMob extends Mob {

	int spriteFlip = 0;
	int xa, ya;

	public MenuMob() {
		x = y = 18 * 16;
	}

	public void update() {
		if (random.nextInt(40) == 0) {
			xa = random.nextInt(3) - 1;
			ya = random.nextInt(3) - 1;
		}
		move(xa, ya);
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

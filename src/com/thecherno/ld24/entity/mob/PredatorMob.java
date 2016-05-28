package com.thecherno.ld24.entity.mob;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.thecherno.ld24.entity.Entity;
import com.thecherno.ld24.graphics.Screen;
import com.thecherno.ld24.graphics.Sprite;
import com.thecherno.ld24.level.tile.LightTile;

public class PredatorMob extends Mob {

	int spriteFlip = 0;
	final Random random = new Random();
	int walkTimer = 0;
	private List<Entity> entitiesInRange = new ArrayList<Entity>();
	int xa = 0;
	int ya = 0;

	public PredatorMob() {
		x = 111 * 16;
		// x = random.nextInt(128 * 16);
		y = 41 * 16;
	}

	public void update() {
		if (random.nextInt(500) > 180 && (findEntity().xa != 0 || findEntity().ya != 0)) {
			xa = findEntity().xa;
			ya = findEntity().ya;
		} else if (random.nextInt(40) == 0) {
			xa = random.nextInt(3) - 1;
			ya = random.nextInt(3) - 1;
		}
		move(xa, ya);
	}

	public Path findEntity() {
		int xt = x >> 4;
		int yt = y >> 4;
		entities();
		for (int i = 0; i < entitiesInRange.size(); i++) {
			Entity e = entitiesInRange.get(i);
			int xd = e.x - x;
			int yd = e.y - y;
			int r = 25600;
			if ((xd * xd + yd * yd < r)) {
				if (xd < 0) xa = -1;
				if (xd > 0) xa = +1;
				if (yd < 0) ya = -1;
				if (yd > 0) ya = +1;
			}
			if (random.nextInt(20) == 0) {
				xa *= random.nextInt(2);
				ya *= random.nextInt(2);
			}

			if (xt == (e.x >> 4) && yt == (e.y >> 4)) {
				hurt(e);
			}
		}
		entitiesInRange.clear();
		return new Path(xa, ya);
	}

	public void hurt(Entity e) {
		e.health--;
		e.hurt = true;
	}

	public void entities() {
		double od = 0;
		double nd = 0;
		boolean first = true;
		for (int i = 0; i < level.entities.size(); i++) {
			Entity e = level.entities.get(i);
			int xd = e.x - x;
			int yd = e.y - y;
			double distance = Math.sqrt(xd * xd + yd * yd);
			if (first) od = nd = distance;
			nd = distance;
			if (nd < od) break;
			nd = od;
			if (distance < 160.0 && !(e instanceof PredatorMob)) entitiesInRange.add(e);
			first = false;
		}
	}

	public void render(Screen screen) {
		if (dir == 0) {
			sprite = Sprite.predator2;
			spriteFlip = 0;
		}
		if (dir == 1) {
			sprite = Sprite.predator0;
			spriteFlip = 0;
		}
		if (dir == 2) {
			sprite = Sprite.predator1;
			spriteFlip = 0;
		}
		if (dir == 3) {
			sprite = Sprite.predator0;
			spriteFlip = 1;
		}
		screen.renderMob(x, y, this, spriteFlip, false);
		renderLighting(LightTile.lightTiles);
	}
}

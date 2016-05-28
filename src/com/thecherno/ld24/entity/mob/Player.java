package com.thecherno.ld24.entity.mob;

import java.util.Random;

import com.thecherno.ld24.Game;
import com.thecherno.ld24.entity.Entity;
import com.thecherno.ld24.entity.Population;
import com.thecherno.ld24.graphics.Screen;
import com.thecherno.ld24.graphics.Sprite;
import com.thecherno.ld24.input.InputHandler;
import com.thecherno.ld24.level.ForestLevel;
import com.thecherno.ld24.level.Level;
import com.thecherno.ld24.level.OceanLevel;
import com.thecherno.ld24.level.SnowLevel;
import com.thecherno.ld24.level.tile.LightTile;
import com.thecherno.ld24.menu.GameOverMenu;
import com.thecherno.ld24.menu.MainMenu;
import com.thecherno.ld24.sound.Sound;

public class Player extends Mob {

	private InputHandler input;
	int spriteFlip = 0;
	public boolean night = false;
	public static int px;
	public static int py;
	final Random random = new Random();

	public Player(InputHandler input) {
		this.input = input;
		sprite = Sprite.player0;
		x = 180;
		y = 130;
	}

	public Player(int x, int y, InputHandler input) {
		this.input = input;
		this.x = x;
		this.y = y;
	}

	public void update() {
		if (Game.menu instanceof GameOverMenu) return;
		px = x;
		py = y;
		if (Level.brightness < -100) {
			night = true;
		} else {
			night = false;
		}
		int xa = 0;
		int ya = 0;

		if (input.up) ya--;
		if (input.down) ya++;
		if (input.left) xa--;
		if (input.right) xa++;

		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
		if (input.back) {
			Level.play = false;
			Game.level.destroy();
			Game.menu = new MainMenu(input);
			Game.setMenuLevel();
		}
		for (int i = 0; i < level.entities.size(); i++) {
			Entity e = level.entities.get(i);
			if (e instanceof Female) {
				if ((x >> 4) == (e.x >> 4) && (y >> 4) == (e.y >> 4) && ((Female) e).canSpawnChild) {
					for (int c = 0; c < random.nextInt(4) + 1; c++) {
						Entity m = new ForestMob();
						Sound.breed.play(false);
						m.x = x;
						m.y = y;
						level.add(m, level);
					}
					Population.growth += (Population.growth / 4);
					((Female) e).canSpawnChild = false;
					if (level instanceof OceanLevel) {
						for (int fe = 0; fe < random.nextInt(2) + 1; fe++) {
							Entity f = new Female();
							f.x = spawnFemale().x;
							f.y = spawnFemale().y;
							level.add(f, level);
						}
					} else if (level instanceof SnowLevel) {
						for (int fe = 0; fe < random.nextInt(5) + 2; fe++) {
							Entity f = new Female();
							f.x = spawnFemale().x;
							f.y = spawnFemale().y;
							level.add(f, level);
						}
					} else {
						for (int fe = 0; fe < random.nextInt(4) + 1; fe++) {
							Entity f = new Female();
							f.x = spawnFemale().x;
							f.y = spawnFemale().y;
							level.add(f, level);
						}
					}
				}

			}
		}

	}

	public Spawn spawnFemale() {
		int fx = 0;
		int fy = 0;
		int ran = random.nextInt(2);
		if (ran == 0) {
			if (level instanceof ForestLevel) {
				fx = random.nextInt(128 * 16);
				fy = 41 * 16;
			}
			if (level instanceof OceanLevel) {
				fx = random.nextInt(42) + 38;
				fy = random.nextInt(5) + 76;
			}
			if (level instanceof SnowLevel) {
				fx = random.nextInt(14) + 27;
				fy = random.nextInt(23) + 12;
			}
		}
		if (ran == 1) {
			if (level instanceof ForestLevel) {
				fx = 103 * 16;
				fy = random.nextInt(128 * 16);
			}
			if (level instanceof OceanLevel) {
				fx = random.nextInt(22) + 43;
				fy = random.nextInt(14) + 57;
			}
			if (level instanceof SnowLevel) {
				fx = random.nextInt(44) + 62;
				fy = random.nextInt(18) + 95;
			}
		}
		return new Spawn(fx << 4, fy << 4);
	}

	int anim = 0;

	public void render(Screen screen) {
		anim++;
		if (dir == 0) {
			sprite = Sprite.player0;
			spriteFlip = 0;
			if (walking) {
				sprite = Sprite.player1;
				if (anim % 250 > 125) {
					spriteFlip = 0;
				} else {
					spriteFlip = 1;
				}
			}
		}
		if (dir == 1) {
			sprite = Sprite.player4;
			spriteFlip = 0;
			if (walking) {
				if (anim % 250 > 125) {
					sprite = Sprite.player4;
				} else {
					sprite = Sprite.player5;
				}
			}
		}
		if (dir == 2) {
			sprite = Sprite.player2;
			spriteFlip = 0;
			if (walking) {
				sprite = Sprite.player3;
				if (anim % 250 > 125) {
					spriteFlip = 0;
				} else {
					spriteFlip = 1;
				}
			}

		}
		if (dir == 3) {
			sprite = Sprite.player4;
			spriteFlip = 1;
			if (walking) {
				if (anim % 250 > 125) {
					sprite = Sprite.player4;
				} else {
					sprite = Sprite.player5;
				}
			}
		}
		screen.renderMob(x, y, this, spriteFlip, false);
		renderLighting(LightTile.lightTiles);
	}
}

package com.thecherno.ld24.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import com.thecherno.ld24.Game;
import com.thecherno.ld24.entity.Entity;
import com.thecherno.ld24.entity.Population;
import com.thecherno.ld24.graphics.Screen;
import com.thecherno.ld24.graphics.Sprite;
import com.thecherno.ld24.level.tile.LightTile;
import com.thecherno.ld24.level.tile.SnowTreeTile;
import com.thecherno.ld24.level.tile.SnowTreeTopTile;
import com.thecherno.ld24.level.tile.Tile;
import com.thecherno.ld24.level.tile.TorchTile;
import com.thecherno.ld24.level.tile.TreeTile;
import com.thecherno.ld24.level.tile.TreeTopTile;
import com.thecherno.ld24.level.tile.WaterTile;
import com.thecherno.ld24.menu.GameOverMenu;
import com.thecherno.ld24.sound.Sound;

public class Level {

	public int width, height;
	public int[] tiles;
	public static int brightness = 0;
	int time = 0;
	private boolean day = false, night = false;
	final Random random = new Random();
	int[] grass;
	public List<Entity> entities = new ArrayList<Entity>();
	private List<LightTile> lightTiles = new ArrayList<LightTile>();
	private List<Tile> postRender = new ArrayList<Tile>();
	private List<Tile> treetops = new ArrayList<Tile>();
	public int timerm = 5;
	public int timers = 0;
	public String timerString = "";

	public static boolean play = false;

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tiles = new int[width * height];
	}

	public Level(String path) {
		loadLevelFromFile(path);
		for (int i = 0; i < grass.length; i++) {
			grass[i] = random.nextInt(8);
		}
	}

	private void loadLevelFromFile(String path) {
		try {
			BufferedImage image = ImageIO.read(Level.class.getResource(path));
			int w = this.width = image.getWidth();
			int h = this.height = image.getHeight();
			tiles = new int[w * h];
			grass = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void generateLevel() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tiles[x + y * width] = random.nextInt(4);
			}
		}
	}

	public void update() {
		time++;
		if (time % 50 == 0) {
			time();
		}
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}
		WaterTile.update();
	}

	public void time() {
		if (brightness < -200) {
			night = true;
			day = false;
		}
		if (brightness > 20) {
			day = true;
			night = false;
		}
		if (night) {
			brightness++;
			return;
		}
		if (day) {
			brightness--;
			return;
		}
		brightness++;
	}

	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll >> 4;
		int x1 = (xScroll + screen.width + 15) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 15) >> 4;
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				Tile tile = getTile(x, y);
				tile.render(x, y, screen);
			}
		}
		for (int y = y0 - 3; y < y1 + 3; y++) {
			for (int x = x0 - 3; x < x1 + 3; x++) {
				Tile tile = getTile2(x, y);
				if (tile instanceof TorchTile) {
					lightTiles.add(new LightTile(x, y));
					postRender.add(new TorchTile(x, y, Sprite.torch));
				}
				if (tile instanceof TreeTile) {
					postRender.add(new TreeTile(x, y, null));
					treetops.add(new TreeTopTile(x, y, null));
				}
				if (tile instanceof SnowTreeTile) {
					postRender.add(new SnowTreeTile(x, y, null));
					treetops.add(new SnowTreeTopTile(x, y, null));
				}
			}
		}
		for (int i = 0; i < lightTiles.size(); i++) {
			lightTiles.get(i).render(lightTiles.get(i).x, lightTiles.get(i).y, screen, this);
		}
		for (int i = 0; i < postRender.size(); i++) {
			postRender.get(i).render(postRender.get(i).x, postRender.get(i).y, screen);
		}
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}
		for (int i = 0; i < treetops.size(); i++) {
			treetops.get(i).render(treetops.get(i).x, treetops.get(i).y, screen);
		}
		lightTiles.clear();
		postRender.clear();
		treetops.clear();
	}

	public void add(Entity e) {
		entities.add(e);
		e.init(this);
	}

	public void add(Entity e, Level level) {
		entities.add(e);
		e.init(level);
	}

	public void remove(Entity e) {
		entities.remove(e);
	}

	public Tile getTile(int x, int y) {
		if ((x < 0 || x >= width || y < 0 || y >= height) && this instanceof SnowLevel) return Tile.ice;
		if (x < 0 || x >= width || y < 0 || y >= height) return Tile.voidTile;
		if (tiles[x + y * width] == 0xffffffff && this instanceof SnowLevel) return Tile.snow;
		if ((tiles[x + y * width] == 0xffffffff || tiles[x + y * width] == 0xffFFFF00) && grass[x + y * width] == 0 && !(this instanceof SnowLevel)) return Tile.grass;
		if (tiles[x + y * width] == 0xff156B20 && this instanceof SnowLevel) return Tile.snow;
		if (tiles[x + y * width] == 0xffFFFF00 && this instanceof SnowLevel) return Tile.snow;
		if (tiles[x + y * width] == 0xff44C4FF && this instanceof SnowLevel) return Tile.snowrock;
		if (tiles[x + y * width] == 0xffffffff || tiles[x + y * width] == 0xff156B20 || tiles[x + y * width] == 0xffFFFF00) return Tile.grassGround;
		if (tiles[x + y * width] == 0xff44C4FF) return Tile.rock;
		if (tiles[x + y * width] == 0xff3A5EFF) return Tile.water;
		if (tiles[x + y * width] == 0xffB5C2FF) return Tile.ice;
		if (tiles[x + y * width] == 0xff00FF04) return Tile.flower;
		if (tiles[x + y * width] == 0xff156B20) return Tile.tree;
		return Tile.voidTile;
	}

	public Tile getTile2(int x, int y) {
		if ((x < 0 || x >= width || y < 0 || y >= height) && this instanceof ForestLevel) return Tile.tree;
		if (x < 0 || x >= width || y < 0 || y >= height) return Tile.voidTile;
		if (tiles[x + y * width] == 0xff156B20 && this instanceof SnowLevel) return Tile.snowtree;
		if (tiles[x + y * width] == 0xff156B20) return Tile.tree;
		if (tiles[x + y * width] == 0xffFFFF00) return Tile.torch;
		return Tile.voidTile;
	}

	public void updateTimer() {
		timerString = timerm + ":" + timers;
		if (timers == 0 && timerm != 0) timerString = timerm + ":" + 0 + "0";
		if (timers < 10) timerString = timerm + ":" + "0" + timers;
		if (timers <= 0 && timerm != 0) {
			timers = 60;
			timerm--;
		}

		timers--;
		if (timers <= 0 && timerm <= 0 && play) {
			play = false;
			Game.menu = new GameOverMenu(Game.input);
		}
	}

	public void destroy() {
		Sound.theme.stop();
		entities.clear();
		lightTiles.clear();
		postRender.clear();
		brightness = 0;
		Population.growth = 0.05;
		Population.population = 2;
		Game.pop = null;
	}

	public void renderTimer(Screen screen) {
		int col = 0xffffff;
		if (Game.level instanceof SnowLevel) col = 0;
		if (play) screen.renderText(timerString, 450, 525, 30, 0, col);

	}

}

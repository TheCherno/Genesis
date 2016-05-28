package com.thecherno.ld24;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.thecherno.ld24.entity.Population;
import com.thecherno.ld24.entity.mob.Female;
import com.thecherno.ld24.entity.mob.Player;
import com.thecherno.ld24.graphics.Map;
import com.thecherno.ld24.graphics.Screen;
import com.thecherno.ld24.graphics.SpriteSheet;
import com.thecherno.ld24.input.InputHandler;
import com.thecherno.ld24.level.ForestLevel;
import com.thecherno.ld24.level.Level;
import com.thecherno.ld24.level.OceanLevel;
import com.thecherno.ld24.level.SnowLevel;
import com.thecherno.ld24.level.tile.LightTile;
import com.thecherno.ld24.menu.MainMenu;
import com.thecherno.ld24.menu.Menu;
import com.thecherno.ld24.menu.PlayMenu;
import com.thecherno.ld24.sound.Sound;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public final static int WIDTH = 300;
	public final static int HEIGHT = WIDTH / 16 * 10;
	public final static String TITLE = "Genesis";
	public final static int SCALE = 3;

	private Thread thread;
	private boolean running = false;
	private boolean paused = false;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	private JFrame frame;
	private int time = 0;
	private Map map;

	private Screen screen;
	public static Menu menu;
	public static Level level;
	public static Population pop;
	public static InputHandler input;
	public static Player player;

	public Game() {
		Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);

		screen = new Screen(WIDTH, HEIGHT);
		new SpriteSheet("/sprites.png");
		input = new InputHandler();
		setMenuLevel();
		menu = new MainMenu(input);
		map = new Map();
		addKeyListener(input);
		addFocusListener(input);
	}

	public static void setMenuLevel() {
		Sound.menutheme.play(true);
		level = new Level("/levels/test.png");
		player = new Player(input);
		for (int i = 0; i < 20; i++) {
			level.add(new MenuMob());
		}
	}

	public synchronized void start() {
		if (running) return;
		running = true;
		thread = new Thread(this, "GameThread");
		thread.start();
	}

	public synchronized void stop() {
		Sound.stopAll();
		if (!running) return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		long lastTimer = System.currentTimeMillis();
		double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			if (delta >= 1) {
				if (!paused) update();
				updates++;
				delta--;
			}

			render();
			frames++;

			while (System.currentTimeMillis() - lastTimer > 1000) {
				lastTimer += 1000;
				System.out.println(updates + " ups, " + frames + " fps");
				// frame.setTitle(TITLE + "  |  " + updates + " ups, " + frames + " fps");
				level.updateTimer();
				frames = 0;
				updates = 0;
			}
		}
	}

	public void update() {
		time++;
		if (time > 65536) time = 0;
		input.update();
		if (menu != null) menu.update();
		if (pop != null) pop.update();
		level.update();

		if (PlayMenu.biome == 2) {
			level = new OceanLevel();
			Female f = new Female();
			level.add(player, level);
			level.add(f, level);
			player.x = 54 * 16;
			player.y = 23 * 16;
			f.x = 66 * 16;
			f.y = 26 * 16;
			pop = new Population(level);
			PlayMenu.biome = -1;
		}
		if (PlayMenu.biome == 0) {
			level = new ForestLevel();
			Female f = new Female();
			level.add(player, level);
			level.add(f, level);
			f.x = 21 * 16;
			f.y = 102 * 16;
			player.x = 10 * 16;
			player.y = 100 * 16;
			pop = new Population(level);
			PlayMenu.biome = -1;
		}
		if (PlayMenu.biome == 1) {
			level = new SnowLevel();
			player.x = 80 * 16;
			player.y = 100 * 16;
			level.add(player, level);
			Female f = new Female();
			f.x = 105 * 16;
			f.y = 94 * 16;
			level.add(f, level);
			pop = new Population(level);
			PlayMenu.biome = -1;
		}
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		screen.graphics(g);
		screen.clear();
		int xScroll = player.x - screen.width / 2;
		int yScroll = player.y - screen.height / 2;

		level.render(xScroll, yScroll, screen);
		LightTile.lightTiles.clear();
		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			pixels[i] = screen.pixels[i];
		}

		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		if (menu != null) menu.render(screen);
		if (pop != null) pop.render(screen);
		if (!input.focus) {
			if (time % 50 > 18) {
				int col = 0xffffff;
				if (level instanceof SnowLevel) col = 0;
				if (!((level instanceof SnowLevel))) screen.renderText("Click to Focus!", 120 + 4, 280 + 4, 80, 1, 0);
				screen.renderText("Click to Focus!", 120, 280, 80, 1, col);
			}
		}
		level.renderTimer(screen);
		// map.render(g, level);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.frame = new JFrame();
		game.frame.setResizable(false);
		game.frame.setTitle(game.TITLE);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setLocationRelativeTo(null);
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setVisible(true);

		game.start();
	}
}
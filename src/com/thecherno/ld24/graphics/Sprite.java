package com.thecherno.ld24.graphics;

public class Sprite {

	public static final int SIZE = 16;
	public int size;
	public int x, y;
	public int[] pixels;

	public static Sprite grassGround = new Sprite(0, 0, 16);
	public static Sprite stone = new Sprite(1, 0, 16);
	public static Sprite grass = new Sprite(2, 0, 16);
	public static Sprite flower = new Sprite(3, 0, 16);
	public static Sprite rock = new Sprite(4, 0, 16);
	public static Sprite snowrock = new Sprite(3, 2, 16);
	public static Sprite water = new Sprite(1, 1, 16);
	public static Sprite water2 = new Sprite(2, 1, 16);
	public static Sprite snow = new Sprite(3, 1, 16);
	public static Sprite ice = new Sprite(4, 1, 16);

	public static Sprite tree0 = new Sprite(5, 0, 16);
	public static Sprite tree1 = new Sprite(6, 0, 16);
	public static Sprite tree2 = new Sprite(5, 1, 16);
	public static Sprite tree3 = new Sprite(6, 1, 16);

	public static Sprite snowtree0 = new Sprite(7, 0, 16);
	public static Sprite snowtree1 = new Sprite(8, 0, 16);
	public static Sprite snowtree2 = new Sprite(7, 1, 16);
	public static Sprite snowtree3 = new Sprite(8, 1, 16);

	public static Sprite torch = new Sprite(0, 1, 16);

	public static Sprite player0 = new Sprite(0, 7, 16);
	public static Sprite player1 = new Sprite(1, 7, 16);
	public static Sprite player2 = new Sprite(2, 7, 16);
	public static Sprite player3 = new Sprite(3, 7, 16);
	public static Sprite player4 = new Sprite(4, 7, 16);
	public static Sprite player5 = new Sprite(5, 7, 16);

	public static Sprite female0 = new Sprite(6, 7, 16);
	public static Sprite female1 = new Sprite(7, 7, 16);
	public static Sprite female2 = new Sprite(8, 7, 16);
	public static Sprite female3 = new Sprite(9, 7, 16);
	public static Sprite female4 = new Sprite(10, 7, 16);
	public static Sprite female5 = new Sprite(11, 7, 16);

	public static Sprite predator0 = new Sprite(0, 8, 16);
	public static Sprite predator1 = new Sprite(1, 8, 16);
	public static Sprite predator2 = new Sprite(2, 8, 16);

	public static Sprite voidSprite = new Sprite(0);

	public Sprite(int x, int y, int size) {
		this.size = size;
		pixels = new int[size * size];
		this.x = x << 4;
		this.y = y << 4;
		create(size);
	}

	public Sprite(int col) {
		pixels = new int[SIZE * SIZE];
		create(16, col);
	}

	public void create(int size) {
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				pixels[x + y * size] = SpriteSheet.pixels[(x + this.x) + (y + this.y) * 256];
			}
		}
	}

	public void create(int size, int col) {
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				pixels[x + y * size] = col;
			}
		}
	}

}

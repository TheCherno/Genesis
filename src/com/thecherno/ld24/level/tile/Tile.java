package com.thecherno.ld24.level.tile;

import com.thecherno.ld24.graphics.Screen;
import com.thecherno.ld24.graphics.Sprite;

public class Tile {

	public int x, y;
	public Sprite sprite;

	public static Tile grassGround = new GrassGroundTile(Sprite.grassGround);
	public static Tile stone = new StoneTile(Sprite.stone);
	public static Tile grass = new GrassTile(Sprite.grass);
	public static Tile flower = new FlowerTile(Sprite.flower);
	public static Tile rock = new RockTile(Sprite.rock);
	public static Tile snowrock = new SnowRockTile(Sprite.snowrock);
	public static Tile water = new WaterTile(Sprite.water);
	public static Tile snow = new SnowTile(Sprite.snow);
	public static Tile ice = new IceTile(Sprite.ice);
	public static Tile tree = new TreeTile(0, 0, null);
	public static Tile snowtree = new SnowTreeTile(0, 0, null);
	public static Tile torch = new TorchTile(0, 0, Sprite.torch);

	public static Tile voidTile = new VoidTile(Sprite.voidSprite);

	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}

	public void render(int x, int y, Screen screen) {
	}

	public boolean solid() {
		return false;
	}

}

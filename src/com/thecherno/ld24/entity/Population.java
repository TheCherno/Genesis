package com.thecherno.ld24.entity;

import java.util.Random;

import com.thecherno.ld24.Game;
import com.thecherno.ld24.entity.mob.Female;
import com.thecherno.ld24.entity.mob.ForestMob;
import com.thecherno.ld24.entity.mob.OceanMob;
import com.thecherno.ld24.graphics.Screen;
import com.thecherno.ld24.level.ForestLevel;
import com.thecherno.ld24.level.Level;
import com.thecherno.ld24.level.OceanLevel;
import com.thecherno.ld24.level.SnowLevel;

public class Population extends Interface {

	public static double population = 2;
	public static double growth = 0.05;
	private Level level;
	final Random random = new Random();

	public Population(Level level) {
		this.level = level;
	}

	public void update() {
		long pop = (int) Math.floor(population);
		if (Level.play) population += growth;
		if (pop % 800 == 0) {
			if (level instanceof ForestLevel) level.add(new ForestMob());
			if (level instanceof OceanLevel) level.add(new OceanMob());
			if (level instanceof ForestLevel && random.nextInt(5) == 0) {
				level.add(new Female());
				population++;
			}
		}
	}

	public void render(Screen screen) {
		long pop = (long) Math.floor(population);
		String populationDisplay = "Population: " + pop;
		int textLength = (int) (populationDisplay.length() * 17.7);
		int col = 0xffffff;
		if (Game.level instanceof SnowLevel) col = 0;
		screen.renderText(populationDisplay, 900 - textLength, 30, 30, 0, col);
	}
}

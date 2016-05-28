package com.thecherno.ld24.sound;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {

	public static final Sound breed = new Sound("/sound/breed.wav");
	public static final Sound gameover = new Sound("/sound/gameover.wav");
	public static final Sound start = new Sound("/sound/start.wav");
	public static final Sound menu = new Sound("/sound/menu.wav");
	public static final Sound menucycle = new Sound("/sound/menucycle.wav");

	public static final Sound menutheme = new Sound("/sound/menutheme.wav");
	public static final Sound theme = new Sound("/sound/theme.wav");

	private AudioClip sound;

	private Sound(String name) {
		sound = Applet.newAudioClip(Sound.class.getResource(name));
	}

	public void play(boolean loop) {
		if (loop) {
			sound.loop();
		} else {
			sound.play();
		}
	}

	public void stop() {
		sound.stop();
	}

	public static void stopAll() {
		breed.stop();
		gameover.stop();
		start.stop();
		menu.stop();
		menucycle.stop();
		menutheme.stop();
		theme.stop();
	}

}

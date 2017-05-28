package com.aquariumsimulation.desktop;

import com.aquariumsimulation.AquariumSimulation;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 640;
		config.height = 360;
		TexturePacker.process("./textures", ".", "textures");
		new LwjglApplication(new AquariumSimulation(), config);
	}
}

package com.aquariumsimulation;

import com.aquariumsimulation.view.screens.LoadingScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

public final class AquariumSimulation extends Game {
	private final AssetManager assetManager = new AssetManager();

	public AssetManager getAssetManager() {
		return assetManager;
	}

	@Override
	public void create() {
		//assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		setScreen(new LoadingScreen(this));
	}
}

package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGame extends Game {

	public SpriteBatch spriteBatch;
	public BitmapFont fuente;

	@Override
	public void create () {

		spriteBatch = new SpriteBatch();
		fuente = new BitmapFont();
		setScreen(new MainMenuScreen(this,"Choose game")); // muestra la primera pantalla (MainMenuScreen
	}

	@Override
	public void render () {
		super.render();

	}
	
	@Override
	public void dispose () {

		spriteBatch.dispose();
		fuente.dispose();
	}
}

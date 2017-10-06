package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

/**
 * Created by tXillA on 11/02/2017.
 */

public class GameScreenDrop extends BaseScreen implements InputProcessor{

    SpriteBatch batch;
    Texture cubo,gota;
    Sound so;
    Music music;
    Camera camera;
    BitmapFont font;


    private Array<Rectangle> raindrops;
    private long lastDropTime;
    private int totalGotas = 0;
    private int totalGotasPerdidas = 0;

    ShapeRenderer shapeRenderer;

    Rectangle bucket;// esto es un rectangulo de gdx
    int direction=1;
    public GameScreenDrop(MyGame gameC)
    {
        super(gameC);
    }
    @Override
    public void show() {
        batch = new SpriteBatch();


        //batch = new SpriteBatch();


        bucket = new Rectangle();
        bucket.x = Gdx.graphics.getWidth()/2-20;
        bucket.y = 400-20;
        bucket.width = 100;
        bucket.height = 100;

        cubo= new Texture ("bucket.png");
        gota = new Texture ("droplet.png");


        // musica
        so = Gdx.audio.newSound(Gdx.files.internal("music/so.wav"));
        music = Gdx.audio.newMusic(Gdx.files.internal("music/rain.mp3"));

        music.setVolume(1.5f);
        music.setLooping(true);
        music.play();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        //camera.setToOrtho(false, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getHeight());

        //font = new BitmapFont(); //Font simple
        font = new BitmapFont(Gdx.files.internal("fontArialBlau.fnt"), Gdx.files.internal("fontArialBlau.png"),false);
        //font.setColor(Color.PINK);

        raindrops = new Array<Rectangle>();
        spawnRaindrop();



        // para que haga el movimiento
        Gdx.input.setInputProcessor(this);


    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, (Gdx.graphics.getWidth() - 64));
        raindrop.y = Gdx.graphics.getHeight() - 50;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0.4f,0.9f, 1); // color vermell, el ultimo uno es la transparencia (RGB)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer = new ShapeRenderer();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0,0.4f,0.9f ,1);
        // hago el rectangulo del bucket
        //shapeRenderer.rect(bucket.x,bucket.y,bucket.width,bucket.height);
        shapeRenderer.end();


        //pinguino y arbol
        batch.begin();
        batch.draw(cubo, bucket.x, bucket.y, bucket.width, bucket.height); // para mover la imagen
        // el metode draw el dibuixe, el batch ho dibuixa tot de cop, 100 x, 400 y
        //
        if (totalGotasPerdidas<5) {
            font.draw(batch, "Score: " + totalGotas, 100, Gdx.graphics.getHeight() - 50);
        }
        else
        {
            font.draw(batch, "GAME OVER", 100, Gdx.graphics.getHeight() - 50);
        }

        if (totalGotasPerdidas<5) {
            for (Rectangle raindrop : raindrops) {
                batch.draw(gota, raindrop.x, raindrop.y);
            }
        }


        batch.end();

        if (Gdx.input.isTouched()) {

            float nuevaPosicion = Gdx.input.getX() - (64 / 2);

            if (nuevaPosicion < 0) {
                nuevaPosicion = 0;
            }

            if (nuevaPosicion > Gdx.graphics.getWidth() - 64) {
                nuevaPosicion = Gdx.graphics.getWidth() - 64;
            }

            bucket.x = nuevaPosicion;
        }

		/*if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();
		// make sure the bucket stays within the screen bounds

		if(bucket.x < 0) bucket.x = 0;
		if(bucket.x > 800 - 64) bucket.x = 800 - 64;*/

        // check if we need to create a new raindrop
        if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();

        if (totalGotasPerdidas < 5)
        {
            Iterator<Rectangle> iter = raindrops.iterator();
            while (iter.hasNext()) {
                Rectangle raindrop = iter.next();
                raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
                if (raindrop.y + 64 < 0) {
                    totalGotasPerdidas++;
                    iter.remove();
                }
                if (raindrop.overlaps(bucket)) {
                    so.play();
                    iter.remove();
                    totalGotas++;
                }
            }
        }

        camera.update();


        // bucket movimiento (es un rectangle) (para que se mueva solo)
		/*if (direction==1) {
			bucket.x = bucket.x + 1;
		}*/


        //bucket.x=bucket.x+1*direction;

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

        batch.dispose();
        cubo.dispose();
        gota.dispose();
        so.dispose();
        music.dispose();
        shapeRenderer.dispose();
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by tXillA on 06/02/2017.
 */

public class MainMenuScreen extends BaseScreen {

    OrthographicCamera camara;
    private Stage stage;
    private String texto;

    public MainMenuScreen(MyGame gameC, String textoT)
    {
        super(gameC);
        this.texto = textoT;
        camara = new OrthographicCamera();
        camara.setToOrtho(false, 1024, 768);

        stage = new Stage(new ScreenViewport());

        Skin mySkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        Label title = new Label(texto, mySkin ,"default");
        title.setAlignment(Align.center);
        title.setFontScale(5,5);
        if (texto == "The End \n created by Arantxa Escalera")
        {
            title.setFontScale(3,3);
        }
        title.setY(Gdx.graphics.getHeight()* 2/3);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);

        TextButton playButton = new TextButton("Arcanoid",mySkin);
        playButton.setWidth(Gdx.graphics.getWidth()/2);
        playButton.setPosition(Gdx.graphics.getWidth()/2-playButton.getWidth()/2,Gdx.graphics.getHeight()/2);
        playButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameDifficultyScreen(game));
                dispose();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;

            }
        });
        stage.addActor(playButton);

        TextButton dropButton = new TextButton("Drop Game",mySkin);
        dropButton.setWidth(Gdx.graphics.getWidth()/2);
        dropButton.setPosition(Gdx.graphics.getWidth()/2-dropButton.getWidth()/2,Gdx.graphics.getHeight()/2 - dropButton.getHeight()*2);
        dropButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreenDrop(game));
                dispose();

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(dropButton);

        TextButton optionsButton = new TextButton("Exit",mySkin);
        optionsButton.setWidth(Gdx.graphics.getWidth()/2);
        optionsButton.setPosition(Gdx.graphics.getWidth()/2-optionsButton.getWidth()/2,Gdx.graphics.getHeight()/2-optionsButton.getHeight()*4);
        optionsButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                System.exit(0);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(optionsButton);


    }
    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /*camara.update();
        game.spriteBatch.setProjectionMatrix(camara.combined);

        // Muestra un menú de inicio
        game.spriteBatch.begin();
        game.fuente.draw(game.spriteBatch, "Bienvenido a Drop!!!!", 100, 150);
        game.fuente.draw(game.spriteBatch, "Pulsa para empezar", 100, 130);
        game.fuente.draw(game.spriteBatch, "Pulsa 'ESCAPE' para SALIR", 100, 110);
        game.spriteBatch.end();*/

		/*
		 * Si el usuario toca la pantalla se inicia la partida
		 */
        /*if (Gdx.input.isTouched()) { // si alguien a tocado la pantalla vuelve a hacer otra screen que va a GameScreen
            game.setScreen(new GameScreen(game)); // el juego es el Game del Drop
            dispose();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            dispose();
            System.exit(0);
        }*/

        stage.act();
        stage.draw();
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
        //stage.dispose();

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.actors.ActorBall;
import com.mygdx.game.actors.ActorBar;
import com.mygdx.game.actors.ActorLadrillo;

/**
 * Created by tXillA on 06/02/2017.
 */

public class GameScreen extends BaseScreen {

    private final int widthLadrillo=Gdx.graphics.getWidth()/14;
    private final int heightLadrillo=Gdx.graphics.getHeight()/50;
    private int dificultad;
    private Stage stage;
    private ActorBar bar;
    private Texture textureBar;

    private ActorBall ball;
    private Texture textureBall,texturePokeball;
    private TextureRegion pokeball;


    private Array<ActorLadrillo> arrayLadrillos = new Array<ActorLadrillo>();
    private Texture textureColor, textureLBlack, textureLBlue, textureLGrey, textureLGreen, textureLPurple, textureLRed, textureLWhite, textureLYellow;

    private Label labelVidas;

    private OrthographicCamera camara;
    private Integer level;

    private Music music;
    private Sound so;

    public GameScreen(MyGame gameC, int dificultadElegida, Integer levelElegido)
    {
        super(gameC);
        dificultad = dificultadElegida;
        level=levelElegido;

        textureBar = new Texture ("bar.png");
        textureBall = new Texture ("ball.png");
        texturePokeball = new Texture ("pokeballs.png");
        pokeball = new TextureRegion(texturePokeball,6,134,57,60);

        // Textures de los ladrillos
        textureLYellow = new Texture("bricks/yellow_brick.png");
        textureLGreen = new Texture("bricks/green_brick.png");
        textureLGrey = new Texture("bricks/gray_brick.png");
        textureLPurple = new Texture("bricks/purple_brick.png");
        textureLRed = new Texture("bricks/red_brick.png");
        textureLBlue = new Texture("bricks/blue_brick.png");
        textureLBlack = new Texture("bricks/black_brick.png");
        textureLWhite = new Texture("bricks/white_brick.png");

        camara = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }

    public void loadCurrentLevel() {

        FileHandle file = Gdx.files.internal("levels/level"+level+".txt");
        String levelInfo = file.readString();

        int x = 0, y = Gdx.graphics.getHeight() - heightLadrillo;
        String[] rows = levelInfo.split("\n");
        int i =0;
        ActorLadrillo ladrillo = null;
        for (String row : rows) {
            String[] brickIds = row.split(",");
            for (String brickId : brickIds) {

                if (brickId.trim().equals("x")) {
                    x += widthLadrillo;
                    continue;
                }

                String s = brickId.trim();
                if (s.equals("1")) {
                    textureColor=textureLBlack;
                } else if (s.equals("2")) {
                    textureColor = textureLBlue;
                } else if (s.equals("3")) {
                    textureColor = textureLGreen;
                } else if (s.equals("4")) {
                    textureColor = textureLGrey;
                } else if (s.equals("5")) {
                    textureColor = textureLPurple;
                } else if (s.equals("6")) {
                    textureColor = textureLRed;
                } else if (s.equals("7")) {
                    textureColor = textureLWhite;
                } else if (s.equals("8")) {
                    textureColor = textureLYellow;
                }

                ladrillo = new ActorLadrillo(textureColor);
                stage.addActor(ladrillo);
                ladrillo.setPosition(x,y);
                x += widthLadrillo;

                arrayLadrillos.add(ladrillo);
                i++;
            }

            x = 0;
            y -= heightLadrillo;
        }
    }

    @Override
    public void show() {

        stage = new Stage();
        //stage.setDebugAll(true);

        // musica
        so = Gdx.audio.newSound(Gdx.files.internal("music/so.wav"));
        so.setVolume(2,1.5f);
        music = Gdx.audio.newMusic(Gdx.files.internal("music/music.mp3"));

        music.setVolume(1.5f);
        music.setLooping(true);
        music.play();

        bar = new ActorBar(textureBar,dificultad);
        ball = new ActorBall(pokeball,dificultad);
        //ladrillo = new ActorLadrillo (textureLadrillo);
        stage.addActor(bar);
        stage.addActor(ball);
        //stage.addActor(ladrillo);
        loadCurrentLevel();
        bar.setPosition(Gdx.graphics.getWidth()/2,400);
        ball.setPosition(Gdx.graphics.getWidth()/2,500);




        Label.LabelStyle label1Style = new Label.LabelStyle();
        BitmapFont myFont = new BitmapFont(Gdx.files.internal("fontArialBlau.fnt"));
        label1Style.font = myFont;
        label1Style.fontColor = Color.CYAN;

        labelVidas = new Label(Integer.toString(bar.getLives()),label1Style);
        labelVidas.setSize(20,20);
        labelVidas.setPosition(10,Gdx.graphics.getHeight()-30*2);
        labelVidas.setAlignment(Align.left);
        stage.addActor(labelVidas);
    }

    @Override
    public void render(float delta) {
        // Pinta el fondo de la pantalla de azul oscuro (RGB + alpha)
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        // Limpia la pantalla
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camara.update();

        stage.act();

        comprobarColisions();
        comprobarColisionsLadrillo();
        // Si la bola cae se pierde una vida
        if (ball.getY() < 125) {
            bar.setLives(bar.getLives() - 1);
            ball.setPosition(300, 500);
            if (dificultad ==1) {
                ball.setSpeedX(350);
                ball.setSpeedY(350);
            }
            else
            {
                ball.setSpeedX(500);
                ball.setSpeedY(500);
            }
            labelVidas.setText(Integer.toString(bar.getLives()));
            // Cuando se terminan las vida se muestra la pantalla
            // de Game Over
            if (bar.getLives() == 0) {
                game.setScreen(new MainMenuScreen(game, "Game Over"));
            }
        }
        stage.draw();
    }
    private void comprobarColisions ()
    {
        /*
        if (ball.getY() < bar.getY() + bar.getHeight() && ball.getY() + ball.getHeight() > bar.getY())
        {
            if (bar.getX()<ball.getX() + ball.getWidth() && bar.getX()+ bar.getWidth() >ball.getX())
            {
            */

        if (hayImpacto (ball, bar))
        {
                //int velocidad = (int) Math.sqrt((double) (ball.getSpeedX() * ball.getSpeedX() + ball.getSpeedY() * ball.getSpeedY()));
                //ball.setSpeedX(velocidad);

                // Si la bola va hacia abajp, al tocar la barra, se invierte la velocidad
                // Si la bola ya va hacia arriba, se mantiene la velocidad que lleva (para evitar que al tocar
                // la barra, rebote hacia abajo)
                if (ball.getSpeedY() < 0) {
                    ball.setSpeedY(-ball.getSpeedY());
                }
            }
        //}
}

    private boolean hayImpacto (Actor a, Actor b) {
        if (a.getY() + a.getHeight() > b.getY() && a.getY() < b.getY() + b.getHeight()) {
            if (a.getX() + a.getWidth() > b.getX() && a.getX() < b.getX() + b.getWidth()) {
                return true;
            }
        }

        return false;
    }

    private void comprobarColisionsLadrillo() {
        int filas = arrayLadrillos.size;
        boolean algunoEscondido = false;
        boolean todosEscondidos = true;

        for (int i = 0; i < filas; i++) {

            final ActorLadrillo ladrillo = arrayLadrillos.get(i);

            if (!ladrillo.isEscondido()) {

                // Se comprueba si hay colision
                /*
                if (ball.getY() + ball.getHeight() > ladrillo.getY() && ball.getY() < ladrillo.getY() + ladrillo.getHeight()) {
                    if (ball.getX() + ball.getWidth() > ladrillo.getX() && ball.getX() < ladrillo.getX() + ladrillo.getWidth()) {
                    */
                if (hayImpacto (ladrillo, ball)) {
                        // SI QUE HAY COLISION, ahora hay que comprobar si es "vertical" o "horizontal"

                        arrayLadrillos.get(i).esconder();
                        algunoEscondido = true;
                    }
//                }
            }

            if (!ladrillo.isEscondido()) {
                todosEscondidos=false;
            }

        }
        if(todosEscondidos)
        {
            if (level ==2)
            {
                game.setScreen(new MainMenuScreen(game, "The End \n created by Arantxa Escalera"));
            }
            else {
                level++;
                game.setScreen(new GameScreen(game, dificultad, level)); //voy a poner tb el level para llamar a otro texto
            }
        }

        if (algunoEscondido) {
            so.play();
            ball.setSpeedY(-ball.getSpeedY());
        }
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

        stage.dispose();
        textureBar.dispose();
        music.dispose();
        so.dispose();
        textureBall.dispose();
        textureLBlack.dispose();
        textureColor.dispose();
        textureLBlue.dispose();
        textureLGreen.dispose();
        textureLGrey.dispose();
        textureLPurple.dispose();
        textureLRed.dispose();
        textureLWhite.dispose();
        textureLYellow.dispose();

    }

    @Override
    public void dispose() {

    }
}

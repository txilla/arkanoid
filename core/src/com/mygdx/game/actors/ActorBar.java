package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by tXillA on 06/02/2017.
 */

public class ActorBar extends Actor {

    private Texture bar;

    private int lives=3;
    private int speedX=350;
    private int dificultad=1;
    OrthographicCamera camara;

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public ActorBar (Texture barC, int dificultadElegida)
    {
        this.bar = barC;
        this.dificultad=dificultadElegida;
        if (dificultad==1) {
            setSize(bar.getWidth() * 3, bar.getHeight() * 2);
        }
        else
        {
            setSize(bar.getWidth() * 2, bar.getHeight() * 2);
        }
    }

    @Override
    public void act(float delta) {
        //super.act(delta);

        /*if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && getX() + bar.getWidth()<Gdx.graphics.getWidth()) {
            setX(getX() +  speedX *delta);

        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && getX()>0) {
            setX(getX() - speedX * delta);

        }*/
        camara = new OrthographicCamera();
        camara.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        if (Gdx.input.isTouched()) {

            float nuevaPosicion = Gdx.input.getX() - (getWidth()/2);

            if (nuevaPosicion < 0)
            {
                nuevaPosicion = 0;
            }

            if (nuevaPosicion > Gdx.graphics.getWidth() -  getWidth() )
            {
                nuevaPosicion = Gdx.graphics.getWidth() -  getWidth();
            }

            setX(nuevaPosicion);
/*
            Vector3 vector = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
            camara.unproject(vector.set(Gdx.input.getX(),Gdx.input.getY(),0));
            setX(vector.x);

            if (vector.x<0)
            {
                setX(0);
            }
            else if (vector.x > Gdx.input.getX() + bar.getWidth())
            {
                setX(Gdx.input.getX() - bar.getWidth());
            }
            */
        }


        camara.update();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);

        batch.draw(bar,getX(),getY(), getWidth(), getHeight());
    }
}

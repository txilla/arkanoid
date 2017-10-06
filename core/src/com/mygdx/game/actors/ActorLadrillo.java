package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by tXillA on 08/02/2017.
 */

public class ActorLadrillo extends Actor {

    private Texture ladrillo;

    public boolean isEscondido() {
        return escondido;
    }

    private boolean escondido=false;

    public ActorLadrillo (Texture ladrilloC)
    {
        this.ladrillo = ladrilloC;
        setSize(Gdx.graphics.getWidth()/14,Gdx.graphics.getHeight()/50);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);
        if (!escondido) {
            batch.draw(ladrillo, getX(), getY(), getWidth(), getHeight() );
        }
    }

    public void esconder()
    {
        escondido=true;
    }

}

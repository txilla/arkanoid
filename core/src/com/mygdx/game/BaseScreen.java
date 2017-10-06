package com.mygdx.game;

import com.badlogic.gdx.Screen;

/**
 * Created by tXillA on 06/02/2017.
 */

// abstract porque las clases que extiendan de esta implementaran su codigo

public abstract class BaseScreen implements Screen {
    // protected para que las clases que extiendan a esta puedan acceder al game
    //
    protected MyGame game;
    // Constructor
    //
    public BaseScreen(MyGame gameC)
    {
        this.game = gameC;
    }

}

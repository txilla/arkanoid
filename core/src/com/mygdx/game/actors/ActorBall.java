package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by tXillA on 06/02/2017.
 */

public class ActorBall extends Actor {

    private TextureRegion ball;
    private int dificultad;

    private int speedX=350;
    private int speedY=350;
    private boolean pause=true;

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public ActorBall (TextureRegion ballC, int dificultadElegida)
    {
        this.ball = ballC;
        this.dificultad = dificultadElegida;
        if (dificultad==2)
        {
            speedX = 500;
            speedY = 500;
        }
        // se actualiza el width i el height del actorball cogiendo las medidas de la textura
        //
        setSize(ball.getRegionWidth(),ball.getRegionHeight());

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (Gdx.input.isTouched()) {
            pause=false;
        }

        if (!pause) {
            // Actualiza posición de la bola

            setX(getX() + speedX * delta);
            setY(getY() + speedY * delta);

            // Comprueba los límites de la pantalla (
            // Rebote en parte izquierda
            if (getX() <= 0) {
                setX(0);
                speedX = -speedX;
            }

            // Rebote en parte derecha
            if ((getX() + this.getWidth()) >= Gdx.graphics.getWidth()) {
                setX(Gdx.graphics.getWidth() - this.getWidth());
                speedX = -speedX;
            }

            // Rebote en el techo
            if ((getY() + this.getWidth()) >= Gdx.graphics.getHeight()) {
                setY(Gdx.graphics.getHeight() - this.getWidth());
                speedY = -speedY;
            }

        /*// Rebote con la tabla
        Board board = spriteManager.board;
        if (board.rect.overlaps(rect)) {

            // Si la tabla está en movimiento puede alterar la dirección X de la bola
            if (board.state == Board.State.LEFT) {
                speedX = -BALL_SPEED;
            }
            if (board.state == Board.State.RIGHT) {
                speedX = BALL_SPEED;
            }

            y = spriteManager.board.y + BOARD_HEIGHT;
            speedY = -speedY;
        }*/

        /*// Rebote con ladrillos
        // FIXME Falta comprobar cómo podemos hacer que rebote de lado en un ladrillo
        for (Brick brick : spriteManager.bricks) {
            if (brick.rect.overlaps(rect)) {

                // La bola pega desde abajo
                if ((rect.y + BALL_WIDTH) <= (brick.rect.y + BRICK_HEIGHT)) {
                    speedY = -speedY;
                    y = rect.y = brick.y - BALL_WIDTH;

                }
                // La bola pega desde arriba
                else {
                    y = rect.y = brick.y + BRICK_HEIGHT;
                    speedY = -speedY;
                }
                brick.lives--;
                if (brick.lives == 0)
                    spriteManager.bricks.removeValue(brick, true);
            }
        }*/
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);

        batch.draw(ball,getX(),getY(),getWidth(),getHeight());
    }
}

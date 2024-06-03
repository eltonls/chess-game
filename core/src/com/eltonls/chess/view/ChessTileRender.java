package com.eltonls.chess.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ChessTileRender {
    private Texture tileImg;
    private Batch spriteBatch;
    private Camera camera;

    public ChessTileRender(Texture tileImg) {
        this.tileImg = tileImg;
        camera = Camera.getInstance();
        spriteBatch = new SpriteBatch();
    }

    public void renderTile(float x, float y) {
        spriteBatch.begin();

        spriteBatch.setProjectionMatrix(camera.getCamera().combined);
        camera.getCamera().update();
        spriteBatch.draw(tileImg, x, y);

        spriteBatch.end();
    }

    public void renderTile(float x, float y, String newImg) {
        Gdx.gl.glEnable(GL20.GL_COLOR_BUFFER_BIT);

        tileImg.dispose();
    }

    public void dispose() {
        tileImg.dispose();
        spriteBatch.dispose();
    }

    public Texture getTileImg() {
        return tileImg;
    }

    public void setTileImg(Texture tileImg) {
        this.tileImg = tileImg;
    }
}

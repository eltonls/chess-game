package com.eltonls.chess.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.eltonls.chess.model.chessboard.ChessBoardSquare;

public class ChessPieceRender {
    private Texture pieceImg;
    private Batch spriteBatch;
    private Camera camera;

    public ChessPieceRender(String piecePath) {
        this.pieceImg = new Texture(Gdx.files.internal(piecePath));
        camera = Camera.getInstance();
    }

    public void render(float x, float y) {
        spriteBatch = new SpriteBatch();

        spriteBatch.setProjectionMatrix(camera.getCamera().combined);
        camera.getCamera().update();

        spriteBatch.begin();
        spriteBatch.draw(pieceImg, x, y);
        spriteBatch.end();
    }

    public void dispose() {
        pieceImg.dispose();
    }

    public Texture getPieceImg() {
        return pieceImg;
    }
}

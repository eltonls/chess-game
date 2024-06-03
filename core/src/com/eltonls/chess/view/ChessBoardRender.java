package com.eltonls.chess.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.eltonls.chess.model.Color;
import com.eltonls.chess.model.Move;
import com.eltonls.chess.model.PositionPair;
import com.eltonls.chess.model.adjList.TileNode;
import com.eltonls.chess.model.chessboard.ChessBoard;

import java.util.List;

public class ChessBoardRender {
    private Rectangle[][] boardTiles;
    private ChessPieceRender[][] pieceRenders;
    private ChessTileRender[][] tileRenders;
    private ChessBoard chessBoard;
    private Rectangle board;
    private Texture lightTile;
    private Texture darkTile;
    private Texture lightHighlight;
    private Texture darkHighlight;
    private Texture tileOptimized;
    private Batch spriteBatch;
    private Camera camera;

    public ChessBoardRender(ChessBoard chessBoard) {
        boardTiles = new Rectangle[8][8];
        pieceRenders = new ChessPieceRender[8][8];
        tileRenders = new ChessTileRender[8][8];
        board = new Rectangle(800 /2 - 8 * 16 / 2, 600 / 2 - 8 * 16 / 2, 128, 128);
        lightTile = new Texture("lightTile.png");
        darkTile = new Texture("darkTile.png");
        tileOptimized = new Texture("tileOptimized.png");
        lightHighlight = new Texture("tileHighlightLight.png");
        darkHighlight = new Texture("tileHighlightDark.png");
        spriteBatch = new SpriteBatch();
        camera = Camera.getInstance();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardTiles[i][j] = new Rectangle(board.x + i * 16, board.y + j * 16, 16, 16);
                tileRenders[i][j] = new ChessTileRender(null);
                if (chessBoard.getBoard()[i][j].getColor() == Color.LIGHT) {
                    tileRenders[i][j].setTileImg(lightTile);
                } else {
                    tileRenders[i][j].setTileImg(darkTile);
                }
            }
        }
    }

    public void renderBoard(ChessBoard board) {
        camera.getInstance();
        spriteBatch.setProjectionMatrix(camera.getCamera().combined);
        spriteBatch.begin();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getBoard()[i][j].getColor() == Color.LIGHT) {
                    tileRenders[i][j].renderTile(boardTiles[i][j].x, boardTiles[i][j].y);
                } else {
                    tileRenders[i][j].renderTile(boardTiles[i][j].x, boardTiles[i][j].y);
                }
            }
        }
        spriteBatch.end();
    }

    public void renderPieces(ChessBoard board) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getBoard()[i][j].getPiece() != null) {
                    pieceRenders[i][j] = new ChessPieceRender(board.getBoard()[i][j].getPiece().toString() + ".png");
                    pieceRenders[i][j].render(boardTiles[i][j].x + 1, boardTiles[i][j].y + 1);
                }
            }
        }
    }

    // legalMoves[64][64]
    public void renderLegalMoves(ChessBoard board, List<TileNode> nodes, Move bestMove) {
        // Clear previous highlights
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getBoard()[i][j].getColor() == Color.LIGHT) {
                    tileRenders[i][j].setTileImg(lightTile);
                } else {
                    tileRenders[i][j].setTileImg(darkTile);
                }
            }
        }

        // Highlight legal moves using the nodes
        for (TileNode to : nodes) {
            PositionPair pos = to.getPosition();

            if(bestMove != null && pos.equals(bestMove.getDestination())) {
                tileRenders[pos.getRow()][pos.getCol()].setTileImg(tileOptimized);
                continue;
            }

            if ((pos.getRow() + pos.getCol()) % 2 == 0) {
                tileRenders[pos.getRow()][pos.getCol()].setTileImg(lightHighlight);
            } else {
                tileRenders[pos.getRow()][pos.getCol()].setTileImg(darkHighlight);
            }
        }
    }

    private PositionPair indexToCoordinate(int legalMoveIndex) {
        return new PositionPair(legalMoveIndex / 8, legalMoveIndex % 8);
    }

    public PositionPair getTile(float xAxisPosition, float yAxisPosition) {
        for(int i = 0; i < 8; i++)  {
            for(int j = 0; j < 8; j++) {
                if(boardTiles[i][j].contains(xAxisPosition, yAxisPosition)) {
                    return new PositionPair(i, j);
                }
            }
        }

        return null;
    }

    public void disposeTile(int x, int y) {
    }

    public void renderTile(int x, int y) {
        boardTiles[x][y] = board;
    }
    public void dispose() {
        lightTile.dispose();
        darkTile.dispose();
    }
}

package com.eltonls.chess.model.chesspiece;

import com.eltonls.chess.model.Color;
import com.eltonls.chess.model.PositionPair;
import com.eltonls.chess.model.adjList.TileNode;
import com.eltonls.chess.model.chessboard.ChessBoardSquare;

import java.util.List;

public class Bishop implements ChessPiece {
    static int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
    private Color color;

    public Bishop(Color color) {
        this.color = color;
    }
    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public List<TileNode> calculateLegalMoves(PositionPair piecePosition, Color color, ChessBoardSquare[][] boardSquares) {
        List<TileNode> legalMoves = new java.util.ArrayList<>();

        for (int[] dir : directions) {
            int x = piecePosition.getRow() + dir[0];
            int y = piecePosition.getCol() + dir[1];

            while (x >= 0 && x < 8 && y >= 0 && y < 8) {
                TileNode newNode = new TileNode(new PositionPair(x, y));
                if (boardSquares[x][y].getPiece() == null) {
                    legalMoves.add(newNode);
                } else if (boardSquares[x][y].getPiece().getColor() != color) {
                    legalMoves.add(newNode);
                    break;
                } else {
                    break;
                }

                x += dir[0];
                y += dir[1];
            }
        }


        return legalMoves;
    }

    @Override
    public String toString() {
        return color.toString().toLowerCase() + "Bishop";
    }
}

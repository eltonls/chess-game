package com.eltonls.chess.model.chesspiece;

import com.eltonls.chess.model.Color;
import com.eltonls.chess.model.PositionPair;
import com.eltonls.chess.model.adjList.TileNode;
import com.eltonls.chess.model.chessboard.ChessBoardSquare;

import java.util.List;

public class Pawn implements ChessPiece {
    private Color color;
    public Pawn(Color color) {
        this.color = color;
    }
    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public List<TileNode> calculateLegalMoves(PositionPair position, Color color, ChessBoardSquare[][] boardSquares) {
        List<TileNode> legalMovesList = new java.util.ArrayList<>();
        int direction = color == Color.LIGHT ? 1 : -1;

        if (position.getCol() + direction >= 0 && position.getCol() + direction < 8) {
            if (boardSquares[position.getRow()][position.getCol() + direction].getPiece() == null) {
                legalMovesList.add(new TileNode(new PositionPair(position.getRow(), position.getCol() + direction)));
                if ((position.getCol() == 1 && color == Color.LIGHT) || (position.getCol() == 6 && color == Color.DARK)) {
                    if (boardSquares[position.getRow()][position.getCol() + 2 * direction].getPiece() == null) {
                        legalMovesList.add(new TileNode(new PositionPair(position.getRow(), position.getCol() + 2 * direction)));
                    }
                }
            }
        }

        return legalMovesList;
    }

    private boolean isWithinBounds(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    @Override
    public String toString() {
        return color.toString().toLowerCase() + "Pawn";
    }
}

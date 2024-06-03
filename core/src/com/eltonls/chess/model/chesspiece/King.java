package com.eltonls.chess.model.chesspiece;

import com.eltonls.chess.model.Color;
import com.eltonls.chess.model.PositionPair;
import com.eltonls.chess.model.adjList.TileNode;
import com.eltonls.chess.model.chessboard.ChessBoardSquare;

import java.util.List;

public class King implements ChessPiece {
    private Color color;

    public King(Color color) {
        this.color = color;
    }
    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public List<TileNode> calculateLegalMoves(PositionPair position, Color color, ChessBoardSquare[][] boardSquares) {
        List<TileNode> legalMoves = new java.util.ArrayList<>();

        return legalMoves;
    }

    @Override
    public String toString() {
        return color.toString().toLowerCase() + "King";
    }
}

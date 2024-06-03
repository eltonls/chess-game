package com.eltonls.chess.model.chessboard;

import com.eltonls.chess.model.Color;
import com.eltonls.chess.model.chesspiece.ChessPiece;

public class ChessBoardSquare {
    private Color color;
    private ChessPiece piece;

    public ChessBoardSquare(Color color, ChessPiece piece) {
        this.color = color;
        this.piece = piece;
    }
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ChessPiece getPiece() {
        return piece;
    }

    public void setPiece(ChessPiece piece) {
        this.piece = piece;
    }
}

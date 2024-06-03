package com.eltonls.chess.model;

import com.eltonls.chess.model.chesspiece.ChessPiece;

public class Move {
    private PositionPair origin;
    private PositionPair destination;
    private ChessPiece pieceMoved;
    private ChessPiece pieceCaptured;
    private boolean isOptimal;

    public Move(PositionPair origin, PositionPair destination, ChessPiece pieceMoved, ChessPiece pieceCaptured) {
        this.origin = origin;
        this.destination = destination;
        this.pieceMoved = pieceMoved;
        this.pieceCaptured = pieceCaptured;
    }

    public PositionPair getOrigin() {
        return origin;
    }

    public PositionPair getDestination() {
        return destination;
    }

    public ChessPiece getPieceMoved() {
        return pieceMoved;
    }

    public ChessPiece getPieceCaptured() {
        return pieceCaptured;
    }

    public boolean isCapture() {
        return pieceCaptured != null;
    }

    public boolean getOptimal() {
        return isOptimal;
    }
    public void setOptimal(boolean optimal) {
        isOptimal = optimal;
    }
}

package com.eltonls.chess.model.adjList;

import com.eltonls.chess.model.PositionPair;
import com.eltonls.chess.model.chesspiece.ChessPiece;

import java.util.Objects;

public class TileNode{
    private PositionPair position;
    private ChessPiece piece;

    public TileNode(PositionPair position, ChessPiece piece) {
        this.position = position;
        this.piece = piece;
    }

    public TileNode(PositionPair position) {
        this.position = position;
    }

    public PositionPair getPosition() {
        return position;
    }

    public void setPosition(PositionPair position) {
        this.position = position;
    }

    public ChessPiece getPiece() {
        return piece;
    }
    public void setPiece(ChessPiece piece) {
        this.piece = piece;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TileNode tileNode = (TileNode) o;
        return position.getRow() == tileNode.getPosition().getRow() && position.getCol() == tileNode.getPosition().getCol();
    }

    @Override
    public int hashCode() {
        return Objects.hash(position.getRow(), position.getCol());
    }

    @Override
    public String toString() {
        return "TileNode{" +
                "row=" + position.getRow() +
                ", col=" + position.getCol() +
                '}';

    }
}

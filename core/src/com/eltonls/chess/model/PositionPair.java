package com.eltonls.chess.model;

public class PositionPair implements Comparable<PositionPair> {
    public int row;
    public int col;

    public PositionPair(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    @Override
    public int compareTo(PositionPair o) {
        if (this.row == o.row) {
            return Integer.compare(this.col, o.col);
        }
        return Integer.compare(this.row, o.row);
    }
}

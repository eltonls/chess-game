package com.eltonls.chess.model.chesspiece;

import com.eltonls.chess.model.Color;
import com.eltonls.chess.model.PositionPair;
import com.eltonls.chess.model.adjList.TileNode;
import com.eltonls.chess.model.chessboard.ChessBoardSquare;

import java.util.List;

public class Knight implements ChessPiece {
    private Color color;

    public Knight(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public List<TileNode> calculateLegalMoves(PositionPair position, Color color, ChessBoardSquare[][] boardSquares) {
        List<TileNode> legalMovesList = new java.util.ArrayList<>();
        int pieceIndex = position.getRow() * 8 + position.getCol();

        int n = boardSquares.length;
        int x = position.getRow();
        int y = position.getCol();

        int[] dx = {1, 2, 2, 1, -1, -2, -2, -1};
        int[] dy = {2, 1, -1, -2, -2, -1, 1, 2};

        for(int i = 0; i < 8; i++) {
            int x2 = x + dx[i];
            int y2 = y + dy[i];

            if(x2 >= 0 && x2 < n && y2 >= 0 && y2 < n) {
                int index = x2 * 8 + y2;
                if(boardSquares[x2][y2].getPiece() == null) {
                    legalMovesList.add(new TileNode(new PositionPair(x2, y2)));
                } else if(boardSquares[x2][y2].getPiece().getColor() != color) {
                    legalMovesList.add(new TileNode(new PositionPair(x2, y2), boardSquares[x2][y2].getPiece()));
                }
            }
        }

        return legalMovesList;
    }

    @Override
    public String toString() {
        return color.toString().toLowerCase() + "Knight";
    }
}

package com.eltonls.chess.controller;

import com.eltonls.chess.model.Color;
import com.eltonls.chess.model.PositionPair;
import com.eltonls.chess.model.chessboard.ChessBoard;
import com.eltonls.chess.model.chessboard.ChessBoardState;
import com.eltonls.chess.model.chesspiece.*;

import javax.swing.text.Position;

public class BoardEvaluator {
    private static final int PAWN_VALUE = 100;
    private static final int KNIGHT_VALUE = 320;
    private static final int BISHOP_VALUE = 330;
    private static final int ROOK_VALUE = 500;
    private static final int QUEEN_VALUE = 900;
    private static final int KING_VALUE = 20000;

    private final static int[][] KING_SAFETY_TABLE = {
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-10, -20, -20, -20, -20, -20, -20, 10},
            {20, 20, 0, 0, 0, 0, 20, 20},
            {20, 30, 10, 0, 0, 10, 30, 20}
    };

    public int evaluate(ChessBoardState boardState) {
        return evaluateBoardCenter(boardState) + evaluateKingSafety(boardState);
    }

    public int evaluateBoardCenter(ChessBoardState boardState) {
        int score = 0;

        for (int i = 2; i < 6; i++) {
            for (int j = 2; j < 6; j++) {
                ChessPiece piece = boardState.getPiece(new PositionPair(i, j));
                if (piece != null) {
                    if (piece.getColor() == Color.LIGHT) {
                        score += getPieceValue(piece);
                    } else {
                        score -= getPieceValue(piece);
                    }
                }
            }
        }

        return score;
    }

    public int evaluateKingSafety(ChessBoardState boardState) {
        int score = 0;

        score += evaluateKingSafetyForColor(boardState, Color.LIGHT);
        score -= evaluateKingSafetyForColor(boardState, Color.DARK);

        return score;
    }

    private int evaluateKingSafetyForColor(ChessBoardState boardState, Color color) {
        int score = 0;
        PositionPair kingPosition = findKingPosition(boardState, color);
        if (kingPosition == null) return 0;

        score += KING_SAFETY_TABLE[kingPosition.getRow()][kingPosition.getCol()];
        score += evaluateKingDefenders(boardState, kingPosition, color);

        return score;
    }

    private PositionPair findKingPosition(ChessBoardState boardState, Color color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = boardState.getPiece(new PositionPair(i, j));
                if (piece instanceof King && piece.getColor() == color) {
                    return new PositionPair(i, j);
                }
            }
        }
        return null;
    }

    private int evaluateKingDefenders(ChessBoardState boardState, PositionPair kingPosition, Color color) {
        int score = 0;
        int[][] directions = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},           {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };

        for (int[] direction : directions) {
            int row = kingPosition.getRow() + direction[0];
            int col = kingPosition.getCol() + direction[1];
            if (isWithinBounds(row, col)) {
                ChessPiece piece = boardState.getPiece(new PositionPair(row, col));
                if (piece != null && piece.getColor() == color) {
                    if (piece instanceof Pawn) {
                        score += PAWN_VALUE / 2;
                    } else if (piece instanceof Knight || piece instanceof Bishop) {
                        score += KNIGHT_VALUE / 4;
                    } else if (piece instanceof Rook) {
                        score += ROOK_VALUE / 4;
                    } else if (piece instanceof Queen) {
                        score += QUEEN_VALUE / 4;
                    }
                }
            }
        }

        return score;
    }

    private boolean isWithinBounds(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    private int getPieceValue(ChessPiece piece) {
        if (piece instanceof Pawn) {
            return PAWN_VALUE;
        } else if (piece instanceof Knight) {
            return KNIGHT_VALUE;
        } else if (piece instanceof Bishop) {
            return BISHOP_VALUE;
        } else if (piece instanceof Rook) {
            return ROOK_VALUE;
        } else if (piece instanceof Queen) {
            return QUEEN_VALUE;
        } else if (piece instanceof King) {
            return KING_VALUE;
        } else {
            return 0;
        }
    }
}

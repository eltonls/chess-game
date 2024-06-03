package com.eltonls.chess.controller;

import com.eltonls.chess.model.Move;
import com.eltonls.chess.model.chessboard.ChessBoardState;

import java.util.List;

public class NegaMax {
    private final int MAX_DEPTH = 4;
    private final int INFINITY = 1000000;
    private BoardEvaluator boardEvaluator;

    public NegaMax() {
        this.boardEvaluator = new BoardEvaluator();
    }

    public int negaMax(ChessBoardState boardState, int depth, int alpha, int beta) {
        if (depth == 0) {
            return boardEvaluator.evaluate(boardState);
        }

        List<Move> allLegalMoves = boardState.calculateAllLegalMoves();
        int bestValue = -INFINITY;

        for (Move move : allLegalMoves) {
            ChessBoardState newState = new ChessBoardState(boardState);
            newState.makeMove(move);
            int value = -negaMax(newState, depth - 1, -beta, -alpha);
            bestValue = Math.max(bestValue, value);
            alpha = Math.max(alpha, value);
            if (alpha >= beta) {
                break;
            }
        }

        return bestValue;
    }
}

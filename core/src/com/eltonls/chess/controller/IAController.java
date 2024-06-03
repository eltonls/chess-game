package com.eltonls.chess.controller;

import com.eltonls.chess.model.Color;
import com.eltonls.chess.model.Move;
import com.eltonls.chess.model.chessboard.ChessBoard;
import com.eltonls.chess.model.chessboard.ChessBoardState;

import java.util.List;

public class IAController {
    private final NegaMax negaMax;

    public IAController() {
        this.negaMax = new NegaMax();
    }

    public Move play(ChessBoardState chessBoardState, Color aiColor)  {
        ChessBoardState boardState = new ChessBoardState(chessBoardState);
        int bestValue = -1000000;
        Move bestMove = null;

        List<Move> allLegalMoves = boardState.calculateAllLegalMoves();

        for (Move move : allLegalMoves) {
            ChessBoardState newState = new ChessBoardState(boardState);
            newState.makeMove(move);
            int value = -negaMax.negaMax(newState, 4, -1000000, 1000000);
            if (value > bestValue) {
                bestValue = value;
                bestMove = move;
            }
        }

        return bestMove;
    }
}

package com.eltonls.chess.model.chesspiece;

import com.eltonls.chess.model.Color;
import com.eltonls.chess.model.PositionPair;
import com.eltonls.chess.model.adjList.TileNode;
import com.eltonls.chess.model.chessboard.ChessBoardSquare;

import java.util.List;

public interface ChessPiece {

    Color getColor();
    List<TileNode> calculateLegalMoves(PositionPair position, Color color, ChessBoardSquare[][] boardSquares);
}

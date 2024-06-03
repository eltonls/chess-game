package com.eltonls.chess.model.chessboard;

import com.eltonls.chess.model.Color;
import com.eltonls.chess.model.Move;
import com.eltonls.chess.model.PositionPair;
import com.eltonls.chess.model.adjList.Graph;
import com.eltonls.chess.model.adjList.TileNode;
import com.eltonls.chess.model.chesspiece.ChessPiece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChessBoardState {
    private final ChessBoardSquare[][] board;
    private List<Move> allLegalMoves;
    private int BOARD_SIZE = 8;

    public ChessBoardState(ChessBoard board) {
        this.board = board.getBoard().clone();
        this.allLegalMoves = board.getAllLegalMoves();
    }

    public ChessBoardState(ChessBoardState state) {
        this.board = state.getBoard().clone();
        this.allLegalMoves = state.getAllLegalMoves();
    }

    public ChessBoardState() {
        board = new ChessBoardSquare[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if(i % 2 == j % 2) {
                    board[i][j] = new ChessBoardSquare(Color.DARK, null);
                } else {
                    board[i][j] = new ChessBoardSquare(Color.LIGHT, null);
                }
            }
        }
    }

    public List<Move> calculateAllLegalMoves() {
        Graph legalMoves = new Graph(new HashMap<>());
        List<Move> allLegalMoves = new ArrayList<>();

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j].getPiece() != null) {
                    List<TileNode> legalMovesList = board[i][j].getPiece().calculateLegalMoves(new PositionPair(i, j), board[i][j].getPiece().getColor(), board);
                    TileNode vertex = new TileNode(new PositionPair(i, j));
                    legalMoves.addVertex(vertex);

                    for(TileNode node : legalMovesList) {
                        legalMoves.addEdge(vertex, node);
                    }
                }
            }
        }

        for(TileNode from : legalMoves.getVertices()) {
            for (TileNode to : legalMoves.getEdges(from)) {
                Move move = new Move(from.getPosition(), to.getPosition(), board[from.getPosition().getRow()][from.getPosition().getCol()].getPiece(), board[to.getPosition().getRow()][to.getPosition().getCol()].getPiece());
                allLegalMoves.add(move);
            }
        }

        this.allLegalMoves = allLegalMoves;
        return allLegalMoves;
    }

    public ChessBoardSquare[][] getBoard() {
        return board;
    }

    public List<Move> getAllLegalMoves() {
        return allLegalMoves;
    }

    public void setAllLegalMoves(List<Move> allLegalMoves) {
        this.allLegalMoves = allLegalMoves;
    }

    public void makeMove(Move move) {
        board[move.getDestination().getRow()][move.getDestination().getCol()].setPiece(move.getPieceMoved());
        board[move.getOrigin().getRow()][move.getOrigin().getCol()].setPiece(null);
    }

    public void undoMove(Move move) {
        board[move.getOrigin().getRow()][move.getOrigin().getCol()].setPiece(move.getPieceMoved());
        board[move.getDestination().getRow()][move.getDestination().getCol()].setPiece(move.getPieceCaptured());
    }

    public ChessPiece getPiece(PositionPair position) {
        if(board[position.getRow()][position.getCol()].getPiece() != null)  {
            return board[position.getRow()][position.getCol()].getPiece();
        }

        return null;
    }
}

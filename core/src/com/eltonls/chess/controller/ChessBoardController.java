package com.eltonls.chess.controller;

import com.eltonls.chess.model.Color;
import com.eltonls.chess.model.Move;
import com.eltonls.chess.model.PositionPair;
import com.eltonls.chess.model.adjList.Graph;
import com.eltonls.chess.model.adjList.TileNode;
import com.eltonls.chess.model.chessboard.ChessBoard;
import com.eltonls.chess.model.chessboard.ChessBoardSquare;
import com.eltonls.chess.model.chessboard.ChessBoardState;
import com.eltonls.chess.model.chesspiece.*;

import java.util.*;
import java.util.stream.Collectors;

public class ChessBoardController {
    private ChessBoard chessBoard;
    private Graph allLegalMoves;
    private BoardEvaluator boardEvaluator;
    public ChessBoardController() {
        chessBoard = ChessBoard.getInstance();
    }

    public Graph getAllLegalMoves() {
        return allLegalMoves;
    }

    public Move bestMoveUsingAStar(PositionPair start, int depth) {
        PriorityQueue<PositionPair> openSet = new PriorityQueue<>();
        Map<PositionPair, PositionPair> cameFrom = new HashMap<>();
        Map<PositionPair, Integer> gScore = new HashMap<>();
        Map<PositionPair, Integer> fScore = new HashMap<>();

        openSet.add(start);
        gScore.put(start, 0);
        fScore.put(start, score(start, start));

        while (!openSet.isEmpty() && depth >= 0)  {
            PositionPair current = openSet.poll();

            // Caminha pelos vizinhos do nó atual dando notas baseado na avaliação do tabuleiro
            for (PositionPair neighbor : getNeighbors(current)) {
                int tentativeGScore = gScore.get(current) + 1;

                if (!gScore.containsKey(neighbor) || tentativeGScore < gScore.get(neighbor)) {
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeGScore);
                    fScore.put(neighbor, tentativeGScore + score(start, neighbor));
                    openSet.add(neighbor);

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        // Encontra a melhor posição final
        PositionPair bestPosition = Collections.max(fScore.entrySet(), Map.Entry.comparingByValue()).getKey();
        PositionPair bestMoveEnd = reconstructPath(cameFrom, bestPosition).get(1);

        // Cria um movimento otimizado
        return new Move(start, bestMoveEnd, chessBoard.getBoard()[start.getRow()][start.getCol()].getPiece(), chessBoard.getBoard()[bestMoveEnd.getRow()][bestMoveEnd.getCol()].getPiece());
    }

    private int score(PositionPair start, PositionPair goal) {
        // Criar um novo board State
        ChessBoardState boardState = new ChessBoardState(chessBoard);

        // Cria um novo movimento
        Move move = new Move(start, goal, chessBoard.getBoard()[start.getRow()][start.getCol()].getPiece(), chessBoard.getBoard()[goal.getRow()][goal.getCol()].getPiece());

        // Faz o movimento no board state criado
        boardState.makeMove(move);

        // Avalia o estado da board e retorna o score
        if(boardState.getPiece(start).getColor() == Color.LIGHT) {
            return boardEvaluator.evaluate(boardState);
        } else {
            return boardEvaluator.evaluate(boardState) * -1;
        }
    }

    private List<PositionPair> getNeighbors(PositionPair position) {
        return allLegalMoves.getEdges(new TileNode(position)).stream()
                .map(TileNode::getPosition)
                .collect(Collectors.toList());
    }

    private List<PositionPair> reconstructPath(Map<PositionPair, PositionPair> cameFrom, PositionPair current) {
        List<PositionPair> path = new ArrayList<>();
        path.add(current);

        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(0, current);
        }

        return path;
    }

    public List<Move> calculateAllLegalMoves() {
        this.allLegalMoves = new Graph(new HashMap<>());
        List<Move> legalMoves = new ArrayList<>();

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(chessBoard.getBoard()[i][j].getPiece() != null) {
                    List<TileNode> legalMovesList = chessBoard.getBoard()[i][j].getPiece().calculateLegalMoves(new PositionPair(i, j), chessBoard.getBoard()[i][j].getPiece().getColor(), chessBoard.getBoard());
                    TileNode vertex = new TileNode(new PositionPair(i, j));
                    allLegalMoves.addVertex(vertex);

                    for(TileNode node : legalMovesList) {
                        allLegalMoves.addEdge(vertex, node);
                    }
                }
            }
        }

        for(TileNode from : allLegalMoves.getVertices()) {
            for (TileNode to : allLegalMoves.getEdges(from)) {
                Move move = new Move(from.getPosition(), to.getPosition(), chessBoard.getBoard()[from.getPosition().getRow()][from.getPosition().getCol()].getPiece(), chessBoard.getBoard()[to.getPosition().getRow()][to.getPosition().getCol()].getPiece());
                legalMoves.add(move);
            }
        }

        this.chessBoard.setAllLegalMoves(legalMoves);
        return legalMoves;
    }

    public List<TileNode> getLegalMovesOfPiece(PositionPair position) {
        TileNode node = new TileNode(position);

        return allLegalMoves.getEdges(node);
    }

    public void initializeBoard() {
        // Pawn
        for (int i = 0; i < 8; i++) {
            chessBoard.getBoard()[i][1].setPiece(new Pawn(Color.LIGHT));
            chessBoard.getBoard()[i][6].setPiece(new Pawn(Color.DARK));
        }

        // BISHOP
        chessBoard.getBoard()[2][0].setPiece(new Bishop(Color.LIGHT));
        chessBoard.getBoard()[5][0].setPiece(new Bishop(Color.LIGHT));
        chessBoard.getBoard()[2][7].setPiece(new Bishop(Color.DARK));
        chessBoard.getBoard()[5][7].setPiece(new Bishop(Color.DARK));

        // KING
        chessBoard.getBoard()[3][0].setPiece(new King(Color.LIGHT));
        chessBoard.getBoard()[3][7].setPiece(new King(Color.DARK));

        // KNIGHT
        chessBoard.getBoard()[1][0].setPiece(new Knight(Color.LIGHT));
        chessBoard.getBoard()[6][0].setPiece(new Knight(Color.LIGHT));
        chessBoard.getBoard()[1][7].setPiece(new Knight(Color.DARK));
        chessBoard.getBoard()[6][7].setPiece(new Knight(Color.DARK));

        // QUEEN
        chessBoard.getBoard()[4][0].setPiece(new Queen(Color.LIGHT));
        chessBoard.getBoard()[4][7].setPiece(new Queen(Color.DARK));

        // ROOK
        chessBoard.getBoard()[0][0].setPiece(new Rook(Color.LIGHT));
        chessBoard.getBoard()[7][0].setPiece(new Rook(Color.LIGHT));
        chessBoard.getBoard()[0][7].setPiece(new Rook(Color.DARK));
        chessBoard.getBoard()[7][7].setPiece(new Rook(Color.DARK));
    }

    public boolean movePiece(PositionPair from, PositionPair to) {
        int toIndex = to.getRow() * 8 + to.getCol();
        int fromIndex = from.getRow() * 8 + from.getCol();

        if(allLegalMoves.getEdges(new TileNode(from)).contains(new TileNode(to))) {
            chessBoard.getBoard()[to.getRow()][to.getCol()].setPiece(chessBoard.getBoard()[from.getRow()][from.getCol()].getPiece());
            chessBoard.getBoard()[from.getRow()][from.getCol()].setPiece(null);
            return true;
        }

        return false;
    }

    public void showBoard() {
        ChessBoardSquare[][] board = chessBoard.getBoard();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j].getColor() + " ");
            }
            System.out.println();
        }
    }

    private PositionPair indexToPosition(int index) {
        return new PositionPair(index % 8, index / 8);
    }

    private int positionToIndex(PositionPair position) {
        return position.getRow() * 8 + position.getCol();
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }
}

package com.eltonls.chess;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.eltonls.chess.controller.ChessBoardController;
import com.eltonls.chess.controller.IAController;
import com.eltonls.chess.model.Color;
import com.eltonls.chess.model.Move;
import com.eltonls.chess.model.PositionPair;
import com.eltonls.chess.model.adjList.TileNode;
import com.eltonls.chess.model.chessboard.ChessBoard;
import com.eltonls.chess.model.chessboard.ChessBoardState;
import com.eltonls.chess.model.chesspiece.ChessPiece;
import com.eltonls.chess.view.ChessBoardRender;
import com.eltonls.chess.view.ChessPieceRender;

import java.util.List;

public class ChessGame extends ApplicationAdapter {
	private ChessBoardController chessBoardController;
	private IAController iaController;
	private ChessBoardRender chessBoardRender;
	private OrthographicCamera camera;
	private boolean isPieceTouched;
	private PositionPair pieceTouchedPosition;
	private ChessPiece pieceTouched;
	private Color playerTurn = Color.LIGHT;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
		chessBoardController = new ChessBoardController();
		chessBoardRender = new ChessBoardRender(chessBoardController.getChessBoard());
		chessBoardController.initializeBoard();
		chessBoardController.calculateAllLegalMoves();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		chessBoardRender.renderBoard(chessBoardController.getChessBoard());
		chessBoardRender.renderPieces(chessBoardController.getChessBoard());

		if(isPieceTouched && Gdx.input.justTouched() && pieceTouched.getColor() == playerTurn) {
			clickMove();

			// COISA DE IA QUE EU DESISTI
			/*
			playerTurn = playerTurn == Color.LIGHT ? Color.DARK : Color.LIGHT;
			iaController = new IAController();

			ChessBoardState state = new ChessBoardState(chessBoardController.getChessBoard());
			Move iaMove = iaController.play(state, Color.DARK);

			if(iaMove != null) {
				chessBoardController.movePiece(iaMove.getOrigin(), iaMove.getDestination());
				chessBoardController.calculateAllLegalMoves();
			} */

			// pass turn back to player
			playerTurn = playerTurn == Color.LIGHT ? Color.DARK : Color.LIGHT;
		}

		if(Gdx.input.justTouched()) {
			Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touch);
			PositionPair tile = chessBoardRender.getTile(touch.x, touch.y);
			ChessPiece piece = tile != null ? chessBoardController.getChessBoard().getBoard()[tile.getRow()][tile.getCol()].getPiece() : null;

			if(tile != null && piece != null && piece.getColor() == playerTurn) {
				this.pieceTouchedPosition = tile;
				isPieceTouched = true;
				this.pieceTouched = chessBoardController.getChessBoard().getBoard()[tile.getRow()][tile.getCol()].getPiece();
				selectPiece();
			}
		}
	}

	public void selectPiece() {
		Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(touch);
		PositionPair tile = chessBoardRender.getTile(touch.x, touch.y);

		if(tile != null && chessBoardController.getChessBoard().getBoard()[tile.getRow()][tile.getCol()].getPiece() != null) {
			this.pieceTouchedPosition = tile;
			isPieceTouched = true;
			this.pieceTouched = chessBoardController.getChessBoard().getBoard()[tile.getRow()][tile.getCol()].getPiece();
			List<TileNode> legals = chessBoardController.getLegalMovesOfPiece(pieceTouchedPosition);
			Move bestMove = chessBoardController.bestMoveUsingAStar(pieceTouchedPosition, 3);

			chessBoardRender.renderLegalMoves(chessBoardController.getChessBoard(), legals, bestMove);
		}
	}

	public void clickMove() {
		Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(touch);
		PositionPair tile = chessBoardRender.getTile(touch.x, touch.y);
		// Get legal moves
		List<TileNode> legals = chessBoardController.getLegalMovesOfPiece(pieceTouchedPosition);

		if(tile != null && legals.contains(new TileNode(tile))) {
			Move move = new Move(pieceTouchedPosition, tile, pieceTouched, chessBoardController.getChessBoard().getBoard()[tile.getRow()][tile.getCol()].getPiece());

			if(chessBoardController.getChessBoard().getBoard()[tile.getRow()][tile.getCol()].getPiece() != null) {
				chessBoardController.getChessBoard().getBoard()[tile.getRow()][tile.getCol()].setPiece(null);
			}

			chessBoardController.movePiece(move.getOrigin(), move.getDestination());
			chessBoardController.calculateAllLegalMoves();
			isPieceTouched = false;
		}
	}
	
	@Override
	public void dispose () {
		chessBoardRender.dispose();
	}

}

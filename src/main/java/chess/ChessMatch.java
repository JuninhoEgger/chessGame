package chess;

import boardgame.Board;
import boardgame.position.Position;
import chess.pieces.king.King;
import chess.pieces.rook.Rook;

import static chess.colorenum.Color.BLACK;
import static chess.colorenum.Color.WHITE;

public class ChessMatch {

    private Board board;

    public ChessMatch() {
        board = new Board(8, 8);
        initialSetup();
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] matrix = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                matrix[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return matrix;
    }

    private void initialSetup() {
        board.placePiece(new Rook(board, WHITE), new Position(2, 1));
        board.placePiece(new King(board, BLACK), new Position(0, 4));
        board.placePiece(new King(board, WHITE), new Position(7, 4));
    }


}

package chess;

import boardgame.Board;
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

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }

    private void initialSetup() {
        placeNewPiece('b', 6, new Rook(board, WHITE));
        placeNewPiece('e', 8, new King(board, BLACK));
        placeNewPiece('e', 1, new King(board, WHITE));
    }


}

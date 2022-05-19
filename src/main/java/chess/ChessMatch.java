package chess;

import boardgame.Board;
import boardgame.piece.Piece;
import boardgame.position.Position;
import chess.exception.ChessException;
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

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();

        validateSourcePosition(source);
        Piece capturedPiece = makeMove(source, target);

        return (ChessPiece) capturedPiece;
    }

    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("THERE IS NO PIECE ON SOURCE POSITION");
        }
        if (!board.piece(position).isThereAnyPossibleMove()) {
            throw new ChessException("THERE IS NO POSSIBLE MOVES FOR THE CHOSEN PIECE");
        }
    }

    private Piece makeMove(Position source, Position target) {
        Piece p = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);
        return capturedPiece;
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }

    private void initialSetup() {
        placeNewPiece('c', 1, new Rook(board, WHITE));
        placeNewPiece('c', 2, new Rook(board, WHITE));
        placeNewPiece('d', 2, new Rook(board, WHITE));
        placeNewPiece('e', 2, new Rook(board, WHITE));
        placeNewPiece('e', 1, new Rook(board, WHITE));
        placeNewPiece('d', 1, new King(board, WHITE));

        placeNewPiece('c', 7, new Rook(board, BLACK));
        placeNewPiece('c', 8, new Rook(board, BLACK));
        placeNewPiece('d', 7, new Rook(board, BLACK));
        placeNewPiece('e', 7, new Rook(board, BLACK));
        placeNewPiece('e', 8, new Rook(board, BLACK));
        placeNewPiece('d', 8, new King(board, BLACK));
    }

}

package chess;

import boardgame.Board;
import boardgame.piece.Piece;
import boardgame.position.Position;
import chess.enums.Color;
import chess.exception.ChessException;
import chess.pieces.king.King;
import chess.pieces.pawn.Pawn;
import chess.pieces.rook.Rook;

import java.util.ArrayList;
import java.util.List;

import static chess.enums.Color.BLACK;
import static chess.enums.Color.WHITE;
import static java.util.stream.Collectors.toList;

public class ChessMatch {

    private int turn;
    private Color currentPlayer;
    private Board board;
    private boolean check;
    private boolean checkMate;

    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = WHITE;
        initialSetup();
    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isCheck() {
        return check;
    }

    public boolean isCheckMate() {
        return checkMate;
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

    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();

        validateSourcePosition(source);
        validateTargetPosition(source, target);

        Piece capturedPiece = makeMove(source, target);
        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("YOU CAN'T PUT YOURSELF IN CHECK");
        }

        check = testCheck(opponent(currentPlayer));

        if (testCheckMate(opponent(currentPlayer))) {
            checkMate = true;
        } else {
            nextTurn();
        }
        return (ChessPiece) capturedPiece;
    }

    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("THERE IS NO PIECE ON SOURCE POSITION");
        }
        if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
            throw new ChessException("THE CHOSEN PIECE IS NOT YOURS");
        }
        if (!board.piece(position).isThereAnyPossibleMove()) {
            throw new ChessException("THERE IS NO POSSIBLE MOVES FOR THE CHOSEN PIECE");
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).possibleMove(target)) {
            throw new ChessException("THE CHOSEN PIECE CAN'T MOVE TO TARGET POSITION");
        }
    }

    private void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == WHITE) ? BLACK : WHITE;
    }

    private Piece makeMove(Position source, Position target) {
        ChessPiece p = (ChessPiece) board.removePiece(source);
        p.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target);
        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }
        board.placePiece(p, target);
        return capturedPiece;
    }

    private void undoMove(Position source, Position target, Piece capturedPiece) {
        ChessPiece p = (ChessPiece) board.removePiece(target);
        p.decreaseMoveCount();
        board.placePiece(p, source);
        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }
    }

    private Color opponent(Color color) {
        return (color == WHITE) ? BLACK : WHITE;
    }

    private ChessPiece king(Color color) {
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color).collect(toList());
        for (Piece p : list) {
            if (p instanceof King) {
                return (ChessPiece) p;
            }
        }
        throw new IllegalStateException("THERE IS NO " + color + " KING ON THE BOARD");
    }

    private boolean testCheck(Color color) {
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(toList());
        for (Piece p : opponentPieces) {
            boolean[][] matrix = p.possibleMoves();
            if (matrix[kingPosition.getRow()][kingPosition.getColumn()]) {
                return true;
            }
        }
        return false;
    }

    private boolean testCheckMate(Color color) {
        if (!testCheck(color)) {
            return false;
        }
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color).collect(toList());
        for (Piece p : list) {
            boolean[][] mat = p.possibleMoves();
            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getColumns(); j++) {
                    if (mat[i][j]) {
                        Position source = ((ChessPiece) p).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source, target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);
                        if (!testCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    private void initialSetup() {
        placeNewPiece('a', 1, new Rook(board, WHITE));
        placeNewPiece('e', 1, new King(board, WHITE));
        placeNewPiece('h', 1, new Rook(board, WHITE));
        placeNewPiece('a', 2, new Pawn(board, WHITE));
        placeNewPiece('b', 2, new Pawn(board, WHITE));
        placeNewPiece('c', 2, new Pawn(board, WHITE));
        placeNewPiece('d', 2, new Pawn(board, WHITE));
        placeNewPiece('e', 2, new Pawn(board, WHITE));
        placeNewPiece('f', 2, new Pawn(board, WHITE));
        placeNewPiece('g', 2, new Pawn(board, WHITE));
        placeNewPiece('h', 2, new Pawn(board, WHITE));

        placeNewPiece('a', 8, new Rook(board, BLACK));
        placeNewPiece('e', 8, new King(board, BLACK));
        placeNewPiece('h', 8, new Rook(board, BLACK));
        placeNewPiece('a', 7, new Pawn(board, BLACK));
        placeNewPiece('b', 7, new Pawn(board, BLACK));
        placeNewPiece('c', 7, new Pawn(board, BLACK));
        placeNewPiece('d', 7, new Pawn(board, BLACK));
        placeNewPiece('e', 7, new Pawn(board, BLACK));
        placeNewPiece('f', 7, new Pawn(board, BLACK));
        placeNewPiece('g', 7, new Pawn(board, BLACK));
        placeNewPiece('h', 7, new Pawn(board, BLACK));
    }

}

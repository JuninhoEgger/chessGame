package chess;

import boardgame.Board;
import boardgame.piece.Piece;
import boardgame.position.Position;
import chess.enums.Color;

import static chess.ChessPosition.fromPosition;

public abstract class ChessPiece extends Piece {

    private Color color;

    protected ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public ChessPosition getChessPosition() {
        return fromPosition(position);
    }

    protected boolean isThereOpponentPiece(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piece(position);
        return p != null && p.getColor() != color;
    }
}

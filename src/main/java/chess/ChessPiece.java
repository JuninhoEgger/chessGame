package chess;

import boardgame.Board;
import boardgame.piece.Piece;
import boardgame.position.Position;
import chess.enums.Color;

import static chess.ChessPosition.fromPosition;

public abstract class ChessPiece extends Piece {

    private Color color;
    private int moveCount;

    protected ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void increaseMoveCount() {
        moveCount++;
    }

    public void decreaseMoveCount() {
        moveCount--;
    }

    public ChessPosition getChessPosition() {
        return fromPosition(position);
    }

    protected boolean isThereOpponentPiece(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piece(position);
        return p != null && p.getColor() != color;
    }
}

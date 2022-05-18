package chess;

import boardgame.Board;
import boardgame.piece.Piece;
import chess.colorenum.Color;

public class ChessPiece extends Piece {

    private Color color;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
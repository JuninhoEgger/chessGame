package chess.pieces.king;

import boardgame.Board;
import chess.ChessPiece;
import chess.colorenum.Color;

public class King extends ChessPiece {

    public King(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "K";
    }

}

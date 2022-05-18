package boardgame.piece;

import boardgame.Board;
import boardgame.position.Position;

public class Piece {

    public Position position;
    private Board board;

    public Piece(Board board) {
        this.board = board;
        position = null;
    }

    public Board getBoard() {
        return board;
    }
}

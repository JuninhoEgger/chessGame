package main;

import chess.ChessMatch;

import static ui.UI.printBoard;

public class Main {
    public static void main(String[] args) {

        ChessMatch chessMatch = new ChessMatch();
        printBoard(chessMatch.getPieces());
    }
}

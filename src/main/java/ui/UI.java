package ui;

import chess.ChessPiece;

import static java.lang.System.out;

public class UI {

    private UI() {
        //CONSTRUTOR VAZIO APONTADO PELO SONAR
    }

    public static void printBoard(ChessPiece[][] pieces) {
        for (int i = 0; i < pieces.length; i++) {
            out.print((8 - i) + " ");
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j]);
            }
            out.println();
        }
        out.println("  a b c d e f g h");
    }

    private static void printPiece(ChessPiece piece) {
        if (piece == null) {
            out.print("-");
        } else {
            out.print(piece);
        }
        out.print(" ");
    }

}

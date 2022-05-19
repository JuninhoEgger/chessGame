package ui;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static chess.enums.Color.BLACK;
import static chess.enums.Color.WHITE;
import static java.lang.Integer.parseInt;
import static java.lang.System.out;
import static java.util.stream.Collectors.toList;

public class UI {

    private UI() {
        //CONSTRUTOR VAZIO APONTADO PELO SONAR
    }

    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void clearScreen() {
        out.print("\033[H\033[2J");
        out.flush();
    }

    public static ChessPosition readChessPosition(Scanner scanner) {
        try {
            String s = scanner.nextLine();
            char column = s.charAt(0);
            int row = parseInt(s.substring(1));
            return new ChessPosition(column, row);
        } catch (RuntimeException e) {
            throw new InputMismatchException("ERROR READING ChessPosition. Valid values are from a1 to h8.");
        }
    }

    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
        printBoard(chessMatch.getPieces());
        out.println();

        printCapturedPieces(captured);
        out.println();

        out.println("TURN: " + chessMatch.getTurn());
        if (!chessMatch.isCheckMate()) {
            out.println("WAITING PLAYER: " + chessMatch.getCurrentPlayer());

            if (chessMatch.isCheck()) {
                out.println("CHECK!");
            }
        } else {
            out.println("CHECKMATE!");
            out.println("WINNER: " + chessMatch.getCurrentPlayer());
        }
    }

    public static void printBoard(ChessPiece[][] pieces) {
        for (int i = 0; i < pieces.length; i++) {
            out.print((8 - i) + " ");
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j], false);
            }
            out.println();
        }
        out.println("  a b c d e f g h");
    }

    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
        for (int i = 0; i < pieces.length; i++) {
            out.print((8 - i) + " ");
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j], possibleMoves[i][j]);
            }
            out.println();
        }
        out.println("  a b c d e f g h");
    }

    private static void printPiece(ChessPiece piece, boolean background) {
        if (background) {
            out.print(ANSI_BLACK_BACKGROUND);
        }
        if (piece == null) {
            out.print("-" + ANSI_RESET);
        } else {
            if (piece.getColor() == WHITE) {
                out.print(ANSI_WHITE + piece + ANSI_RESET);
            } else {
                out.print(ANSI_YELLOW + piece + ANSI_RESET);
            }
        }
        out.print(" ");
    }

    private static void printCapturedPieces(List<ChessPiece> captured) {
        List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == WHITE).collect(toList());
        List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == BLACK).collect(toList());

        out.println("CAPTURED PIECES");

        out.print("WHITE: ");
        out.print(ANSI_WHITE);
        out.println(Arrays.toString(white.toArray()));
        out.println(ANSI_RESET);

        out.print("BLACK: ");
        out.print(ANSI_YELLOW);
        out.println(Arrays.toString(black.toArray()));
        out.println(ANSI_RESET);
    }

}

package main;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.exception.ChessException;

import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;
import static ui.UI.*;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(in);
        ChessMatch chessMatch = new ChessMatch();

        while (true) {
            try {
                clearScreen();
                printBoard(chessMatch.getPieces());
                out.println();
                out.print("SOURCE: ");
                ChessPosition source = readChessPosition(scanner);
                out.println();
                out.print("TARGET: ");
                ChessPosition target = readChessPosition(scanner);

                ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
            } catch (ChessException | InputMismatchException e) {
                out.println(e.getMessage());
                scanner.nextLine();
            }
        }

    }
}

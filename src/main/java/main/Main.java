package main;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.exception.ChessException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;
import static ui.UI.*;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(in);
        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> captured = new ArrayList<>();

        while (!chessMatch.isCheckMate()) {
            try {
                clearScreen();
                printMatch(chessMatch, captured);
                out.println();
                out.print("SOURCE: ");
                ChessPosition source = readChessPosition(scanner);

                boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                clearScreen();
                printBoard(chessMatch.getPieces(), possibleMoves);

                out.println();
                out.print("TARGET: ");
                ChessPosition target = readChessPosition(scanner);

                ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
                if (capturedPiece != null) {
                    captured.add(capturedPiece);
                }

                if (chessMatch.getPromoted() != null) {
                    out.print("Enter piece for promotion (B/N/R/Q): ");
                    String type = scanner.nextLine();
                    chessMatch.replacePromotedPiece(type);
                }

            } catch (ChessException | InputMismatchException e) {
                out.println(e.getMessage());
                scanner.nextLine();
            }
        }

        clearScreen();
        printMatch(chessMatch, captured);

    }
}

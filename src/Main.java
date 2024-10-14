package src;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BitwiseUtility game = new BitwiseUtility();
        Scanner scanner = new Scanner(System.in);
        boolean isPlayer1Turn = true;

        // Initialize the board
        game.initializeBoard();

        while (true) {
            // Print the board
            game.printBoard();
            System.out.println(isPlayer1Turn ? "Player 1's turn" : "Player 2's turn");

            // Get player input for the move
            System.out.print("Enter from position (0-63): ");
            int fromPos = scanner.nextInt();
            System.out.print("Enter to position (0-63): ");
            int toPos = scanner.nextInt();

            // Handle the move or report invalid moves
            if (game.isLegalMove(fromPos, toPos, isPlayer1Turn)) {
                game.movePiece(fromPos, toPos, isPlayer1Turn);

                // Switch turns after a valid move
                isPlayer1Turn = !isPlayer1Turn;
            } else {
                System.out.println("Invalid move, try again.");
            }
        }
    }
}

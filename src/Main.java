package src;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BitwiseUtility game = new BitwiseUtility();
        Scanner scanner = new Scanner(System.in);
        boolean isPlayer1Turn = true;

        // Initialize the board at the start
        game.initializeBoard();

        while (true) {
            game.printBoard();  // Display the current board state

            System.out.println(isPlayer1Turn ? "Player 1's turn" : "Player 2's turn");

            System.out.print("Enter from position (0-63): ");
            int fromPos = scanner.nextInt();

            System.out.print("Enter to position (0-63): ");
            int toPos = scanner.nextInt();

            // Check if the move is legal
            if (game.isLegalMove(fromPos, toPos, isPlayer1Turn)) {
                game.movePiece(fromPos, toPos, isPlayer1Turn);

                
                // game.capturePiece(capturePosition, isPlayer1Turn);

                // Update the game state
                game.updateGameState(isPlayer1Turn);

                // Switch turns
                isPlayer1Turn = !isPlayer1Turn;
            } else {
                System.out.println("Invalid move, try again.");
            }
        }
    }
}

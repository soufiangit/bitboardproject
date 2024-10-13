package src;

public class BitwiseUtility {

    private long player1Board;
    private long player2Board;

    // Initialize the board as per the sheet instructions
    public void initializeBoard() {
        player1Board = 0x00000000000FFF00L;  // Player 1 Pieces
        player2Board = 0xFFF0000000000000L;  // Player 2 Pieces
    }

    // Move a piece from one position to another
    public void movePiece(int fromPos, int toPos, boolean isPlayer1) {
        long fromBit = 1L << fromPos;
        long toBit = 1L << toPos;

        if (isPlayer1) {
            player1Board &= ~fromBit;  // Clear the start position
            player1Board |= toBit;     // Set the end position
        } else {
            player2Board &= ~fromBit;  // Clear the start position
            player2Board |= toBit;     // Set the end position
        }
    }

    // Check if a move is legal 
    public boolean isLegalMove(int fromPos, int toPos, boolean isPlayer1) {
        int diff = Math.abs(fromPos - toPos);
        long toBit = 1L << toPos;

        if (diff == 7 || diff == 9) {
            if (isPlayer1 && toPos > fromPos && !isPositionOccupied(toPos)) {
                return true;  // Player 1 moves downwards
            } else if (!isPlayer1 && toPos < fromPos && !isPositionOccupied(toPos)) {
                return true;  // Player 2 moves upwards
            }
        }
        return false;
    }

    // Check if the position is occupied by either player
    public boolean isPositionOccupied(int position) {
        long posBit = 1L << position;
        return (player1Board & posBit) != 0 || (player2Board & posBit) != 0;
    }

    // Capture a piece from the opponent's board
    public void capturePiece(int position, boolean isPlayer1) {
        long posBit = 1L << position;

        if (isPlayer1) {
            player2Board &= ~posBit;  // Remove the opponent's piece
        } else {
            player1Board &= ~posBit;  // Remove the opponent's piece
        }
    }

    // Print the current state of the board 
    public void printBoard() {
        System.out.println("Player 1's pieces:");
        printBitboard(player1Board);
        System.out.println("Player 2's pieces:");
        printBitboard(player2Board);
    }

    // Helper method to print a bitboard
    private void printBitboard(long bitboard) {
        for (int i = 63; i >= 0; i--) {
            System.out.print(((bitboard >> i) & 1) + " ");
            if (i % 8 == 0) {
                System.out.println();
            }
        }
    }

    // Update the game state, including switching turns
    public void updateGameState(boolean isPlayer1Turn) {
    }
}

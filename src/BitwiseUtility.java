package src;

public class BitwiseUtility {

    private long player1Board;
    private long player2Board;
    private long kingBoard; // To track kings for both players

    // Initialize the board with pieces and kings 
    public void initializeBoard() {
        player1Board = 0x00000000000FFF00L;  // Player 1 Pieces
        player2Board = 0xFFF0000000000000L;  // Player 2 Pieces
        kingBoard = 0L;  // No kings at the start
    }

    // Getters for player1Board, player2Board, and kingBoard
    public long getPlayer1Board() {
        return player1Board;
    }

    public long getPlayer2Board() {
        return player2Board;
    }

    public long getKingBoard() {
        return kingBoard;
    }

    // Move a piece from one position to another, also handle kings
    public void movePiece(int fromPos, int toPos, boolean isPlayer1) {
        long fromBit = 1L << fromPos;
        long toBit = 1L << toPos;

        // Move the piece for the correct player
        if (isPlayer1) {
            player1Board &= ~fromBit;  // Clear the start position
            player1Board |= toBit;     // Set the end position
        } else {
            player2Board &= ~fromBit;  // Clear the start position
            player2Board |= toBit;     // Set the end position
        }

        // Handle kings promotion if the piece reaches the other side
        if (isPlayer1 && toPos >= 56) {
            kingBoard |= toBit;  // Promote Player 1 piece to king
        } else if (!isPlayer1 && toPos <= 7) {
            kingBoard |= toBit;  // Promote Player 2 piece to king
        }
    }


    
    // Check if a move is legal (diagonal, valid squares,kings,  and respecting boundaries)
    public boolean isLegalMove(int fromPos, int toPos, boolean isPlayer1) {
        // Ensure moves are on dark (playable) squares
        if ((fromPos % 8 + toPos % 8) % 2 == 0) {
            System.out.println("Invalid move: You can only move on dark squares.");
            return false;
        }

        // Check if the destination is occupied
        if (isPositionOccupied(toPos)) {
            System.out.println("Invalid move: The destination is already occupied.");
            return false;
        }

        int rowDiff = (toPos / 8) - (fromPos / 8);
        int colDiff = Math.abs(toPos % 8 - fromPos % 8);

        // Move must be diagonal
        if (colDiff != 1) {
            System.out.println("Invalid move: You must move diagonally.");
            return false;
        }

        // Player 1 can only move down (to a higher row)
        if (isPlayer1 && rowDiff != 1) {
            System.out.println("Invalid move: Player 1 can only move downward.");
            return false;
        }

        // Player 2 can only move up (to a lower row)
        if (!isPlayer1 && rowDiff != -1) {
            System.out.println("Invalid move: Player 2 can only move upward.");
            return false;
        }

        return true;
    }

    // Handle jumping (capturing) moves
    public boolean canCapture(int fromPos, int toPos, boolean isPlayer1) {
        int diff = Math.abs(fromPos - toPos);
        if (diff == 14 || diff == 18) {  // Jumping over a piece
            int middlePos = (fromPos + toPos) / 2;
            long middleBit = 1L << middlePos;

            // Ensure the middle position contains the opponent's piece
            if (isPlayer1 && (player2Board & middleBit) != 0) {
                capturePiece(middlePos, true);
                return true;
            } else if (!isPlayer1 && (player1Board & middleBit) != 0) {
                capturePiece(middlePos, false);
                return true;
            }
        }
        return false;
    }

    // Capture a piece
    public void capturePiece(int position, boolean isPlayer1) {
        long posBit = 1L << position;
        if (isPlayer1) {
            player2Board &= ~posBit;  // Remove Player 2 piece
        } else {
            player1Board &= ~posBit;  // Remove Player 1 piece
        }
        kingBoard &= ~posBit;  // Remove the king status if it was a king
    }

    // Check if the position is occupied
    public boolean isPositionOccupied(int position) {
        long posBit = 1L << position;
        return (player1Board & posBit) != 0 || (player2Board & posBit) != 0;
    }
    public void undo(long prevPlayer1Board, long prevPlayer2Board, long prevKingBoard) {
        player1Board = prevPlayer1Board;
        player2Board = prevPlayer2Board;
        kingBoard = prevKingBoard;
    }

    // Check if one player has won (no pieces or no legal moves)
    public boolean hasPlayerWon(boolean isPlayer1) {
        if (isPlayer1 && player2Board == 0) {
            return true;  // Player 1 wins if Player 2 has no pieces
        } else if (!isPlayer1 && player1Board == 0) {
            return true;  // Player 2 wins if Player 1 has no pieces
        }
        return false;
    }

    // Print the board with an improved visualization
    public void printBoard() {
        System.out.println("Checkers Board:");
        for (int i = 0; i < 8; i++) {  // Iterate over the rows
            for (int j = 0; j < 8; j++) {  // Iterate over the columns
                int pos = i * 8 + j;  // Calculate the bit position for the current square

                if ((i + j) % 2 == 1) {  // Only display pieces on playable squares (dark squares)
                    long bitMask = 1L << pos;

                    // Check if there's a piece in the current position
                    if ((player1Board & bitMask) != 0) {
                        if ((kingBoard & bitMask) != 0) {
                            System.out.print(" K1 ");  // King for Player 1
                        } else {
                            System.out.print(" P1 ");  // Regular piece for Player 1
                        }
                    } else if ((player2Board & bitMask) != 0) {
                        if ((kingBoard & bitMask) != 0) {
                            System.out.print(" K2 ");  // King for Player 2
                        } else {
                            System.out.print(" P2 ");  // Regular piece for Player 2
                        }
                    } else {
                        System.out.print(" .  ");  // Empty playable square
                    }
                } else {
                    System.out.print(" ## ");  // Non-playable square (light squares)
                }
            }
            System.out.println();  // Newline after each row
        }
    } }

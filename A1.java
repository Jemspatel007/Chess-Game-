import java.io.*;

public class A1 {
    public static void main(String[] args) {

        // Create an instance of the src.FolowChess class.
        FollowChess chessGame = new FollowChess();

        // Initialize buffered readers for reading input from two files.
        BufferedReader boardConfig;
        BufferedReader moveSequence;
        try {

            /*
             * Initialize buffered readers for reading the initial board configuration and
             * move sequence.
             */
            boardConfig = new BufferedReader(new FileReader("board.txt"));
            moveSequence = new BufferedReader(
                    new FileReader("Movesequence.txt"));
                  

            // Load the initial chessboard configuration from the file.
            if (chessGame.loadBoard(boardConfig)) {

                System.out.println("The starting configuration of the chessboard:");
                // Print the initial board configuration.
                chessGame.printBoard(new PrintWriter(System.out));

                // Apply the move sequence to the chessboard
                if (chessGame.applyMoveSequence(moveSequence)) {

                    // Print the updated board configuration after applying the move sequence
                    chessGame.printBoard(new PrintWriter(System.out));
                  
                } else {
                    
                    System.out.println("Final Board Configuration:");
                    // Print an error message for an invalid move sequence
                    chessGame.printBoard(new PrintWriter(System.out));
                }

            } else {

            }

        } catch (Exception e) {
            return;

        }
    }

}
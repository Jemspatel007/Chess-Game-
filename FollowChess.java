import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FollowChess {

    // create the 2D array to store the chessBoard
    private char[][] chessBoard;
    // Initialize the variable to find the length and width of the chessboard
    int boardLength;
    int boardWidth;

    private boolean whitePieceMove = true;

    // Create two arraylist to store the pieces that are captured by white and black
    // player
    public List<Character> captureOrderForWhite;
    public List<Character> captureOrderForBlack;

    // initialize the validpieces list in which i stored all the pieces character
    public List<Character> validPieces;

    /*
     * constructor for asssigning the chessboard to a empty 2D array and empty
     * arraylists which
     * are used for storing the captured pieces for both white and black players.
     */
    public FollowChess() {

        chessBoard = new char[0][0];
        captureOrderForWhite = new ArrayList<Character>();
        captureOrderForBlack = new ArrayList<Character>();
        validPieces = Arrays.asList('K', 'k', 'Q', 'q', 'R', 'r', 'B', 'b', 'N', 'n', 'P', 'p', ' ', '.');
    }

    /**
     * Loads the initial configuration of the chessboard from a BufferedReader.
     * Each line from the BufferedReader represents a row on the chessboard.
     *
     * @param boardStream BufferedReader containing the initial configuration of the
     *                    chessboard.
     * @return True if the chessboard is successfully loaded, false otherwise.
     * @throws IOException If an IOException occurs while reading the boardStream.
     */

    public boolean loadBoard(BufferedReader boardStream) {

        // Print the file that contains the initial configuration of the chess board and
        // store into one string
        try {

            if (boardStream == null) {
                // Handle the case where boardStream is null

                return false;
            }
            // initialize a list to store the each line of the chessboard
            List<String> chessBoardRows = new ArrayList<>();
            //
            String data = "";
            // read every line from the bufferreader using for loop and add into the the
            // chessBoardRows list
            for (data = boardStream.readLine(); data != null; data = boardStream.readLine()) {
                if (data.trim().isEmpty()) {
                    return true;
                }
                chessBoardRows.add(data);
            }
            // first of all we check wheather chessBoardRows is null or not
            if ((!chessBoardRows.isEmpty()) && (chessBoardRows != null)) {

                // find the rowcount and columncount for further use
                int rowCount = chessBoardRows.size();
                int colCount = chessBoardRows.get(0).length();

                int countOfWhiteKing = 0, countOfBlackKing = 0;

                /*
                 * Ensure that the chessboard has the king of both colors by making a loop.
                 * If it doesn't, then it's not a good idea.
                 */
                for (String element : chessBoardRows) {
                    if (element.contains("K"))
                        countOfBlackKing++;
                    if (element.contains("k"))
                        countOfWhiteKing++;
                }
                // to ensure the board configuration as it is a valid board or not.
                if ((rowCount > 0) && (colCount > 0) && (countOfBlackKing == 1) && (countOfWhiteKing == 1)) {

                    // now,assign the chessboard 2D array by using the count of row an column.
                    chessBoard = new char[rowCount][colCount];

                    // calculate the length and width of the chessboard
                    boardLength = (chessBoard.length);
                    boardWidth = chessBoard[0].length;
                    // System.out.println(boardLength);

                    // fill the chessBoard 2D array by the characters from chessBoardRows that we
                    // are
                    // assigned above.
                    for (int j = 0; j < rowCount; j++) {
                        String row = chessBoardRows.get(j);

                        // System.out.println(row.length() + " " + colCount);
                        for (int p = 0; p < colCount; p++) {
                            if ((row.length() < colCount) || (row.length() > colCount) || (row.length() == 0)) {
                                // Return false if the row has fewer columns than expected
                                return false;

                            }
                            if (validPieces.contains(row.charAt(p))) {
                                chessBoard[j][p] = row.charAt(p);
                            } else {
                                return false;
                            }
                        }
                    }
                    // chessboard is loaded successfully.
                    return true;
                }

                else {
                    // chessboard configuration is invalid
                    return false;
                }
            }
        }
        // handling the IOException.
        catch (Exception e) {

        }
        return false;
    }

    /**
     * Prints the current configuration of the chessboard to the provided
     * PrintWriter.
     * Each row of the board is encoded in a string, and the entire board is flushed
     * to the PrintWriter.
     *
     * @param outstream PrintWriter to which the chessboard configuration will be
     *                  printed.
     * @return True if the board is printed successfully, otherwise returns false.
     */
    public boolean printBoard(PrintWriter outstream) {

        // check wheather the current chessboard array and outstream is null or not
        if (chessBoard != null && outstream != null) {

            for (char[] chessRows : chessBoard) {
                // now, convert the character of the array into the string and print it into the
                // printwriter
                outstream.print(new String(chessRows) + "\n");

            }
            // Make sure the entire board is printed by flushing the printewriter.
            outstream.flush();

            // chessboard is printed successfully
            return true;

        }
        // failed to print the chessboard
        return false;
    }

    /*
     * The BufferedReader moveStream is utilized to apply a series of chess moves.
     * Each move is expressed as a string in the format "a 1 a 2",
     * where "a 1" denotes the starting position and "a 2" represents the ending
     * position.
     */

    /**
     * Executes a sequence of chess moves from the provided BufferedReader.
     *
     * @param moveStream BufferedReader containing the sequence of chess moves to be
     *                   executed.
     * @return True if the move sequence is successfully executed, otherwise returns
     *         false.
     */

    public boolean applyMoveSequence(BufferedReader moveStream) {
        try {

            // The flag denotes whether it is presently the white pieces' turn to make a
            // move.

            // Iterate through each line of the chess move in the moveStream.
            for (String line = moveStream.readLine(); line != null; line = moveStream.readLine()) {

                // Use an array to divide the line into separate move components (for example,
                // "a 1 a 2" becomes ["a", "1", "b", "2"]).
                String[] move = line.split(" ");

                // ensure that the move complies with the correct format, including both the
                // initial position and the final position.

                if (move.length == 4) {

                    /*
                     * get the value of the start and end row as well as start and end column using
                     * the array in which character are stored.
                     * substract the row from the total length of chessboard to reverse the order of
                     * the row according to the constraints.
                     * convert the column index into the integer by substracting with 'a'.
                     */
                    int startRow = (boardLength - (Character.getNumericValue(move[1].charAt(0))));
                    int startCol = move[0].charAt(0) - 'a';
                    int endRow = (boardLength - (Character.getNumericValue(move[3].charAt(0))));
                    int endCol = move[2].charAt(0) - 'a';

                    // return false if the row and col are not valid.
                    if (startRow < 0 || startRow >= boardLength || startCol < 0 || startCol >= chessBoard[0].length
                            || endRow < 0 || endRow >= boardLength || endCol < 0 || endCol >= chessBoard[0].length) {

                        return false;
                    }

                    char positionOfPiece = chessBoard[startRow][startCol];

                    if ((whitePieceMove && Character.isLowerCase(positionOfPiece))
                            || (!whitePieceMove && Character.isUpperCase(positionOfPiece))) {

                        // If the move is valid, proceed with its execution and switch the current turn.
                        if (validmove(startRow, startCol, endRow, endCol)) {
                            // if the moove us valid then execute it.
                            makeMove(startRow, startCol, endRow, endCol);

                            whitePieceMove = !whitePieceMove;
                        } else {
                            System.out.println("Move is invalid");
                        }
                    } else {
                        System.out.println("Invalid piece for the current player's turn");
                        return false;
                    }
                }

                else {
                    System.out.println("Invalid move format: " + line);
                    return false;
                }
            }
        }

        catch (Exception e) {

        }

        return false;
    }

    /**
     * This function generates and displays a list of captured pieces by a player.
     *
     * @param player The designated player (0 for black, 1 for white).
     * @return A list of captured pieces in the specified order.
     */
    List<Character> captureOrder(int player) {

        // Check the player and print the corresponding capture order
        if (player == 0) {
            return captureOrderForWhite;
        }

        if (player == 1) {
            return captureOrderForBlack;
        } else {
            return null;
        }

    }

    /**
     * Determines whether the player's king is in check.
     *
     * @param player The designated player for whom the king will be checked. (0 for
     *               black, 1 for white).
     * @return True if the player's king is in check, false otherwise.
     */

    public boolean inCheck(int player) {

        /*
         * Variables are to be initialized for the purpose of storing the position of
         * the king.
         */

        int kingRow = 0;
        int kingCol = 0;

        // "Assign the king of which color player based on the player's selection.
        char whichKing = (player == 0) ? 'k' : 'K';

        // Perform an iteration process across the chessboard to determine the precise
        // location of the king.

        for (int i = 0; i < chessBoard.length; i++) {
            // Iterate through the entire board to check for opponent's pieces
            for (int j = 0; j < chessBoard[0].length; j++) {
                // Check If any of the piece colors match with the color of the king.
                if (chessBoard[i][j] == whichKing) {
                    /*
                     * If a match is found, the value of "i" will be assigned to the king's row and
                     * the value of "j" will be assigned to the king's column.
                     */
                    kingRow = i;
                    kingCol = j;
                    // Iterate through the entire board to check for opponent's pieces.
                    for (int k = 0; k < boardLength; k++) {
                        for (int l = 0; l < chessBoard[0].length; l++) {

                            // Check if the piece belongs to the opponent and can capture the king
                            if ((player == 0 && Character.isUpperCase(chessBoard[k][l]))
                                    || (player == 1 && Character.isLowerCase(chessBoard[k][l])))

                                if (validmove(k, l, kingRow, kingCol)) {
                                    return true; // The king is in check
                                }

                        }
                    }

                }
            }
        }
        // the king is not in the check.
        return false;

    }

    

    /*
     * This function validates and executes a chess move based on the specified
     * initial and final positions.
     * It ensures that the move complies with the rules of the piece at its starting
     * location and makes appropriate adjustments to the chessboard.
     * Each chess piece has specific rules governing its movements.
     */

    /**
     * Executes a chess move from the specified initial position to the designated
     * final position.
     *
     * @param startRow Row index of the piece's starting location.
     * @param startCol Column index of the piece's starting location.
     * @param endRow   Row index of the intended destination for the move.
     * @param endCol   Column index of the intended destination for the move.
     * @return True if the move is executed successfully, false otherwise.
     */

    private boolean validmove(int startRow, int startCol, int endRow, int endCol) {

        if (startRow < 0 || startRow >= chessBoard.length || startCol < 0 || startCol >= chessBoard[0].length ||
                endRow < 0 || endRow >= chessBoard.length || endCol < 0 || endCol >= chessBoard[0].length) {
            return false;
        }

        // get the chesspiece as a string using start row and col.
        String startPiece = String.valueOf(chessBoard[startRow][startCol]);

        // Compute the distances and differences for validating the proposed move.
        int rowDistance = Math.abs(startRow - endRow);
        int colDistance = Math.abs(startCol - endCol);
        int rowDifference = Math.abs(endRow - startRow);
        int colDifferene = Math.abs(endCol - startCol);

        // Verify the movement regulations according to the type of chess piece.

        // check for the king's move
        if ((startPiece.equals("k"))) {

            // "Verify whether the piece at the initial position is either an uppercase
            // letter (indicating a black piece) or a blank square (represented by '.')".
            if ((Character.isUpperCase(chessBoard[endRow][endCol]) || (chessBoard[endRow][endCol]) == '.')) {

                /*
                 * The king has the ability to move one square either vertically or horizontally
                 * in any direction and
                 * for that, we execute it weather the staring and ending row or starting col
                 * and ending col are same or not.
                 */

                if (((startRow == endRow) || (startCol == endCol))) {

                    // Compute the direction for the row and column's step.
                    int rowDirection = (rowDifference == 0) ? 0 : ((endRow > startRow) ? 1 : -1);
                    int colDirection = (colDifferene == 0) ? 0 : ((endCol > startCol) ? 1 : -1);

                    // Iterate through the positions within the specified start and end range.
                    for (int i = 1; i < Math.max(rowDifference, colDifferene); i++) {
                        int checkRow = startRow + i * rowDirection;
                        int checkCol = startCol + i * colDirection;

                        // Please ensure that the path is clear of any obstacles or not.
                        if (chessBoard[checkRow][checkCol] != '.') {
                            // An obstacle is present, making the move invalid and return false.
                            return false;
                        }
                    }
                    // the move is valid.
                    return true;
                }

                /*
                 * king also can move diagonaly and we can execute it by the row difference and
                 * col difference as both of there
                 * must be less than or equal to 1.
                 */
                else if (((rowDifference <= 1) && (colDifferene <= 1))) {
                    int rowDirection = (rowDifference == 0) ? 0 : ((endRow > startRow) ? 1 : -1);
                    int colDirection = (colDifferene == 0) ? 0 : ((endCol > startCol) ? 1 : -1);

                    // Iterate through the positions within the specified start and end range.
                    for (int i = 1; i < Math.max(rowDifference, colDifferene); i++) {
                        int checkRow = startRow + i * rowDirection;
                        int checkCol = startCol + i * colDirection;

                        // Please ensure that the path is clear of any obstacles or not.
                        if (chessBoard[checkRow][checkCol] != '.') {
                            // An obstacle is present, making the move invalid and return false.
                            return false;
                        }
                    }

                    // the move is valid.
                    return true;
                }
            } else {
                // The element at the initial position is invalid or the movement does not
                // follow a horizontal or vertical direction.
                return false;

            }

        }

        // check for the king's move
        else if ((startPiece.equals("K"))) {

            // "Verify whether the piece at the initial position is either an Lowercase
            // letter (indicating a white piece) or a blank square (represented by '.')".

            if ((Character.isLowerCase(chessBoard[endRow][endCol]) || (chessBoard[endRow][endCol]) == '.')) {
                /*
                 * The king has the ability to move one square either vertically or horizontally
                 * in any direction and
                 * for that, we execute it weather the staring and ending row or starting col
                 * and ending col are same or not.
                 */

                if ((startRow == endRow) || (startCol == endCol)) {
                    // Compute the direction for the row and column's step.
                    int rowDirection = (rowDifference == 0) ? 0 : ((endRow > startRow) ? 1 : -1);
                    int colDirection = (colDifferene == 0) ? 0 : ((endCol > startCol) ? 1 : -1);

                    // Iterate through the positions within the specified start and end range.
                    for (int i = 1; i < Math.max(rowDifference, colDifferene); i++) {
                        int checkRow = startRow + i * rowDirection;
                        int checkCol = startCol + i * colDirection;

                        // Please ensure that the path is clear of any obstacles or not.
                        if (chessBoard[checkRow][checkCol] != '.') {

                            return false;
                        }

                    }
                    // the move is valid.
                    return true;

                }
                /*
                 * king also can move diagonaly and we can execute it by the row difference and
                 * col difference as both of there
                 * must be less than or equal to 1.
                 */
                else if ((rowDifference <= 1) && (colDifferene <= 1)) {

                    // Compute the direction for the row and column's step.
                    int rowDirection = (rowDifference == 0) ? 0 : ((endRow > startRow) ? 1 : -1);
                    int colDirection = (colDifferene == 0) ? 0 : ((endCol > startCol) ? 1 : -1);

                    // Iterate through the positions within the specified start and end range.
                    for (int i = 1; i < Math.max(rowDifference, colDifferene); i++) {
                        int checkRow = startRow + i * rowDirection;
                        int checkCol = startCol + i * colDirection;

                        // Please ensure that the path is clear of any obstacles or not.
                        if (chessBoard[checkRow][checkCol] != '.') {
                            // An obstacle is present, making the move invalid and return false.
                            return false;
                        }

                    }
                    // the move is valid.
                    return true;

                }
            } else {
                // The element at the initial position is invalid or the movement does not
                // follow a horizontal or vertical direction.
                return false;
            }

        }

        // check for rook's move
        else if ((startPiece.equals("R"))) {

            // "Verify whether the piece at the initial position is either an uppercase
            // letter (indicating a black piece) or a blank square (represented by '.')".
            if (Character.isLowerCase(chessBoard[endRow][endCol]) || ((chessBoard[endRow][endCol]) == '.')) {

                /*
                 * The rook has the only ability to move any number of squares either vertically
                 * or horizontally in any direction and
                 * by cheking wheather staring row and ending row as well as staring col and
                 * ending col are same or not., it can move.
                 */
                if (((startRow == endRow) || (startCol == endCol))) {
                    int rowDirection = (rowDifference == 0) ? 0 : ((endRow > startRow) ? 1 : -1);
                    int colDirection = (colDifferene == 0) ? 0 : ((endCol > startCol) ? 1 : -1);

                    for (int i = 1; i < Math.max(rowDifference, colDifferene); i++) {
                        int checkRow = startRow + i * rowDirection;
                        int checkCol = startCol + i * colDirection;

                        // Check if there is an obstacle in the path
                        if (chessBoard[checkRow][checkCol] != '.') {
                            return false;
                        }
                    }

                    return true;
                }

            } else {

                return false;
            }

        }

        else if ((startPiece.equals("r"))) {

            // "Verify whether the piece at the initial position is either an uppercase
            // letter (indicating a black piece) or a blank square (represented by '.')".

            if ((Character.isUpperCase(chessBoard[endRow][endCol]) || (chessBoard[endRow][endCol]) == '.')) {

                if ((startRow == endRow) || (startCol == endCol)) {
                    int rowDirection = (rowDifference == 0) ? 0 : ((endRow > startRow) ? 1 : -1);
                    int colDirection = (colDifferene == 0) ? 0 : ((endCol > startCol) ? 1 : -1);

                    for (int i = 1; i < Math.max(rowDifference, colDifferene); i++) {
                        int checkRow = startRow + i * rowDirection;
                        int checkCol = startCol + i * colDirection;

                        // Check if there is an obstacle in the path
                        if (chessBoard[checkRow][checkCol] != '.') {
                            return false;
                        }
                    }
                    return true;
                }
            }

            else {
                return false;
            }

        }

        // check for bishop's move
        else if ((startPiece.equals("b"))) {

            // "Verify whether the piece at the initial position is either an uppercase
            // letter (indicating a black piece) or a blank square (represented by '.')".
            if ((Character.isUpperCase(chessBoard[endRow][endCol]) || (chessBoard[endRow][endCol]) == '.')) {

                /*
                 * Bishops are only capable of moving diagonally across any number of squares
                 * and
                 * if the row difference and col difference are same then it can move.
                 */

                if ((rowDistance == colDistance)) {
                    int rowDirection = (rowDifference == 0) ? 0 : ((endRow > startRow) ? 1 : -1);
                    int colDirection = (colDifferene == 0) ? 0 : ((endCol > startCol) ? 1 : -1);

                    for (int i = 1; i < Math.max(rowDifference, colDifferene); i++) {
                        int checkRow = startRow + i * rowDirection;
                        int checkCol = startCol + i * colDirection;

                        // Check if there is an obstacle in the path
                        if (chessBoard[checkRow][checkCol] != '.') {
                            return false;
                        }
                    }

                    return true;

                }
            } else {
                return false;
            }

        }

        else if ((startPiece.equals("B"))) {

            if ((Character.isLowerCase(chessBoard[endRow][endCol]) || (chessBoard[endRow][endCol]) == '.')) {

                /*
                 * Bishops are only capable of moving diagonally across any number of squares
                 * and
                 * if the row difference and col difference are same then it can move.
                 */

                if ((rowDistance == colDistance)) {
                    int rowDirection = (rowDifference == 0) ? 0 : ((endRow > startRow) ? 1 : -1);
                    int colDirection = (colDifferene == 0) ? 0 : ((endCol > startCol) ? 1 : -1);

                    for (int i = 1; i < Math.max(rowDifference, colDifferene); i++) {
                        int checkRow = startRow + i * rowDirection;
                        int checkCol = startCol + i * colDirection;

                        // Check if there is an obstacle in the path
                        if (chessBoard[checkRow][checkCol] != '.') {
                            return false;
                        }
                    }
                    return true;

                }
            } else {
                return false;
            }

        }

        // check for the queen's move
        else if (startPiece.equals("q")) {

            // "Verify whether the piece at the initial position is either an uppercase
            // letter (indicating a black piece) or a blank square (represented by '.')".
            if ((Character.isUpperCase(chessBoard[endRow][endCol]) || (chessBoard[endRow][endCol]) == '.')) {

                /*
                 * queen can move vertically, horizontally and diagonally across any number of
                 * squares and
                 * we can execute it if starting row and ending row as well as staring col and
                 * ending col are same and
                 * the row difference and col difference must be same.
                 */
                if (((startRow == endRow) || (startCol == endCol)
                        || (rowDistance == colDistance))) {

                    int rowDirection = (rowDifference == 0) ? 0 : ((endRow > startRow) ? 1 : -1);
                    int colDirection = (colDifferene == 0) ? 0 : ((endCol > startCol) ? 1 : -1);

                    for (int i = 1; i < Math.max(rowDifference, colDifferene); i++) {
                        int checkRow = startRow + i * rowDirection;
                        int checkCol = startCol + i * colDirection;

                        // Check if there is an obstacle in the path
                        if (chessBoard[checkRow][checkCol] != '.') {
                            return false;
                        }
                    }
                    return true;

                }
            } else {
                return false;
            }

        }

        else if (startPiece.equals("Q")) {

            if (Character.isLowerCase(chessBoard[endRow][endCol]) || (chessBoard[endRow][endCol]) == '.') {
                /*
                 * queen can move vertically, horizontally and diagonally across any number of
                 * squares and
                 * we can execute it if starting row and ending row as well as staring col and
                 * ending col are same and
                 * the row difference and col difference must be same.
                 */

                if (((startRow == endRow) || (startCol == endCol)
                        || (rowDistance == colDistance))) {

                    int rowDirection = (rowDifference == 0) ? 0 : ((endRow > startRow) ? 1 : -1);
                    int colDirection = (colDifferene == 0) ? 0 : ((endCol > startCol) ? 1 : -1);

                    for (int i = 1; i < Math.max(rowDifference, colDifferene); i++) {
                        int checkRow = startRow + i * rowDirection;
                        int checkCol = startCol + i * colDirection;

                        // Check if there is an obstacle in the path
                        if (chessBoard[checkRow][checkCol] != '.') {
                            return false;
                        }
                    }
                    return true;

                }
            } else {
                return false;
            }

        }

        // check for knight's move
        else if (startPiece.equals("n")) {

            // "Verify whether the piece at the initial position is either an uppercase
            // letter (indicating a black piece) or a blank square (represented by '.')".
            if ((Character.isUpperCase(chessBoard[endRow][endCol]) || (chessBoard[endRow][endCol]) == '.')) {
                /*
                 * The movement pattern of a Knight in chess is characterized by an "L" shape,
                 * where it moves two squares in one direction and one square in a perpendicular
                 * direction.
                 * we can execute it if the row difference is 2 and col difference is 1 or
                 * row difference is 1 and coldifference is 2.
                 */

                if ((rowDifference == 2 && colDifferene == 1) || (rowDifference == 1 && colDifferene == 2)) {
                    return true;
                }
            } else {
                return false;
            }

        }

        // check for knight's move
        else if (startPiece.equals("N")) {

            if ((Character.isLowerCase(chessBoard[endRow][endCol]) || (chessBoard[endRow][endCol]) == '.')) {
                /*
                 * The movement pattern of a Knight in chess is characterized by an "L" shape,
                 * where it moves two squares in one direction and one square in a perpendicular
                 * direction.
                 * we can execute it if the row difference is 2 and col difference is 1 or
                 * row difference is 1 and coldifference is 2.
                 */

                if ((rowDifference == 2 && colDifferene == 1) || (rowDifference == 1 && colDifferene == 2)) {
                    return true;
                }
            } else {
                return false;
            }

        }

        // check for pawn's move

        // Here, i divide the pawm's move by the player color

        // first, we check for white color
        else if ((startPiece.equals("p"))) {

            /*
             * basically, pawn can move horizontally and diagonally if there is any piece
             * from opponent.
             * in this case, we must need to check that pawn doesn't move back side as like
             * other pieces.
             */

            /*
             * in this condition, pawn can move horizontally if the col difference is 0, row
             * difference is 1,
             * start row is greater than end row for checking not to move back side.
             */

            /*
             * Here, we check for the diagonal move and for that first we need to make
             * sure
             * that on the capture position, there must be a oponent piece.
             * Apart from this, if the start row is greater than end row, row and col
             * difference is 1 and there is no '.' at the position that piece want to
             * capture.
             */

            if ((startRow > endRow) && (rowDifference == 1) && (colDifferene == 0)
                    && (chessBoard[endRow][endCol] == '.')) {
                return true; // Valid non-capturing move (one step ahead)
            } else if (startRow > endRow && rowDifference == 1 && Math.abs(colDifferene) == 1
                    && chessBoard[endRow][endCol] != '.' && Character.isUpperCase(chessBoard[endRow][endCol])) {
                return true; // Valid capturing move diagonally only if there is an opponent piece
            }
            return false;

        }

        // now, we can check for black color
        else if (startPiece.equals("P")) {

            /*
             * Here, we check for the diagonal move and for that first we need to make
             * sure
             * that on the capture position, there must be a oponent piece.
             * Apart from this, if the start row is smaller than end row, row and col
             * difference is 1 and there is no '.' at the position that piece want to
             * capture.
             */

            if ((startRow < endRow) && (rowDifference == 1) && (colDifferene == 0)
                    && (chessBoard[endRow][endCol] == '.')) {
                return true; // Valid non-capturing move (one step ahead)
            } else if (startRow < endRow && rowDifference == 1 && Math.abs(colDifferene) == 1
                    && chessBoard[endRow][endCol] != '.' && Character.isLowerCase(chessBoard[endRow][endCol])) {
                return true; // Valid capturing move diagonally only if there is an opponent piece
            }
            return false;
        }
        // move is valid and processed successfully.
        return false;
    }

    /**
     * Executes a chess move from the specified initial position to the designated
     * final position.
     *
     * @param startRow The row of the piece's starting location.
     * @param startCol The column of the piece's starting location.
     * @param endRow   The row of the intended destination for the move.
     * @param endCol   The column of the intended destination for the move.
     */

    public void makeMove(int startRow, int startCol, int endRow, int endCol) {

        /*
         * Here, I find the starting postion and tha position that piece want to capture
         * using the start row and col as well as end row and end col
         */
        char sPiece = chessBoard[startRow][startCol];
        char ePiece = chessBoard[endRow][endCol];

        /*
         * "I insert the captured piece into the created lists in the constructor, based
         * on its corresponding color.
         * This allows for easy retrieval of the piece using the end row and column.
         * This step is crucial for later in capturing the orders."
         */

        if (Character.isUpperCase(ePiece)) {
            captureOrderForWhite.add(ePiece);

        }

        if (Character.isLowerCase(ePiece)) {

            captureOrderForBlack.add(ePiece);
        }

        /*
         * "The primary algorithm for executing a move involves using a swapping method,
         * which begins by storing the character in function as an epiece.
         * The epiece is then reassigned to its intended ending position."
         */
        chessBoard[startRow][startCol] = '.';
        chessBoard[endRow][endCol] = sPiece;

    }

    /**
     * Determines whether the chess piece at the given board position is capable of
     * making a valid move.
     *
     * @param boardPosition The position of the chess piece in algebraic notation
     *                      (e.g., "a 1").
     * @return True if the specified piece can make a valid move, false otherwise.
     */

    boolean pieceCanMove(String boardPosition) {

        // Split the board position into its components and store in array called
        // canMove.
        String[] canMove = boardPosition.split(" ");

        // return false if the length of the canMove array is less than or greater than
        // 2.
        if (canMove.length != 2) {
            System.out.println("input is invalid");
            return false;
        }

        // find the row and col of piece.
        int pieceRow = (boardLength - (Character.getNumericValue(canMove[1].charAt(0))));
        int pieceCol = canMove[0].charAt(0) - 'a';

        // return false if the row or col is not valid.
        if (pieceRow < 0 || pieceRow >= boardLength || pieceCol < 0 || pieceCol >= chessBoard[0].length) {
            return false;

        }

        // Retrieve the piece at the specified position
        char piece = chessBoard[pieceRow][pieceCol];

        // if the piece is . than return false.
        if (piece == '.') {
            return false;
        }

        // initialize the endrow and endcol and assign it to start row and col.
        int eRow = pieceRow;
        int eCol = pieceCol;

        // Verify the piece's type and access specific methods for movement validation.
        if ((piece == 'K') || (piece == 'k')) {
            // king can move vertically, horizontally or diagonally
            if ((canMoveVertically(pieceRow, pieceCol, eRow, eCol))
                    || (canMoveHorizontally(pieceRow, pieceCol, eRow, eCol))
                    || (canMoveDiagonally(pieceRow, pieceCol, eRow, eCol))) {
                return true;
            } else {
                return false;
            }
        } else if ((piece == 'R') || (piece == 'r')) {
            // rook can move vertically or horizontally.
            if ((canMoveVertically(pieceRow, pieceCol, eRow, eCol))
                    || (canMoveHorizontally(pieceRow, pieceCol, eRow, eCol))) {

                return true;
            } else {
                return false;
            }
        } else if ((piece == 'B') || (piece == 'b')) {
            // Bishoop can move diagonally.
            if ((canMoveDiagonally(pieceRow, pieceCol, eRow, eCol))) {
                return true;
            } else {
                return false;
            }
        } else if ((piece == 'Q') || (piece == 'q')) {
            // queen can move vertically, horizontally and diagonally.
            if ((canMoveVertically(pieceRow, pieceCol, eRow, eCol))
                    || (canMoveHorizontally(pieceRow, pieceCol, eRow, eCol))
                    || (canMoveDiagonally(pieceRow, pieceCol, eRow, eCol))) {
                return true;
            } else {
                return false;
            }
        } else if ((piece == 'N') || (piece == 'n')) {
            // Knight can move only in L-shape.
            if ((canMoveInLShape(pieceRow, pieceCol, eRow, eCol))) {
                return true;
            } else {
                return false;
            }

        } else if ((piece == 'P') || (piece == 'p')) {
            // Pawn can move based on specific rules that i created later in function.
            if ((canMovePawn(pieceRow, pieceCol, eRow, eCol))) {
                return true;
            } else {
                return false;
            }
        }
        // Default case: the piece is not recognized
        return false;
    }

    /**
     * Determines the validity of a potential move for a pawn at the specified
     * position.
     * I have examined multiple scenarios for the pawn's movement and have developed
     * distinct logical frameworks to handle each of these scenarios.
     *
     * @param pieceRow The row index of the current position of the pawn.
     * @param pieceCol The colu mn index of the current position of the pawn.
     * @param eRow     The row index of the target position.
     * @param eCol     The column index of the target position.
     * @returns True if the move is considered valid, false otherwise.
     */

    public boolean canMovePawn(int pieceRow, int pieceCol, int eRow, int eCol) {
        if (Character.isLowerCase(chessBoard[pieceRow][pieceCol])) {
            if (validmove(pieceRow, pieceCol, eRow - 1, eCol + 1)) {
                if (Character.isUpperCase(chessBoard[eRow - 1][eCol + 1])) {
                    return true;
                } else {
                    return false;
                }

            }

            if (validmove(pieceRow, pieceCol, eRow - 1, eCol - 1)) {
                if (Character.isUpperCase(chessBoard[eRow - 1][eCol - 1])) {
                    return true;
                } else {
                    return false;
                }

            }

            if (validmove(pieceRow, pieceCol, eRow - 1, eCol)) {
                if (canWhiteCapture(pieceRow, pieceCol, eRow - 1, eCol)) {
                    return true;

                }

            }

        } else if (Character.isUpperCase(chessBoard[pieceRow][pieceCol]))

        {
            if (validmove(pieceRow, pieceCol, eRow + 1, eCol + 1)) {
                if (canBlackCapture(pieceRow, pieceCol, eRow + 1, eCol + 1)) {
                    if (chessBoard[eRow + 1][eCol + 1] == '.') {
                        return false;
                    }
                    return true;

                }

            }
            if (validmove(pieceRow, pieceCol, eRow + 1, eCol - 1)) {
                if (canBlackCapture(pieceRow, pieceCol, eRow + 1, eCol - 1)) {
                    if (chessBoard[eRow + 1][eCol + 1] == '.') {
                        return false;
                    }
                    return true;

                }

            }
            if (validmove(pieceRow, pieceCol, eRow + 1, eCol)) {
                if (canBlackCapture(pieceRow, pieceCol, eRow + 1, eCol)) {
                    return true;

                }

            }

        }
        return false;
    }

    /**
     * Determines the validity of a potential move for a knight at the specified
     * position.
     * I have examined multiple scenarios for the knight's movement and have
     * developed
     * distinct logical frameworks to handle each of these scenarios.
     *
     * @param pieceRow The row index of the current position of the pawn.
     * @param pieceCol The colu mn index of the current position of the pawn.
     * @param eRow     The row index of the target position.
     * @param eCol     The column index of the target position.
     * @returns True if the move is considered valid, false otherwise.
     */

    public boolean canMoveInLShape(int pieceRow, int pieceCol, int eRow, int eCol) {
        if (validmove(pieceRow, pieceCol, eRow - 1, eCol - 2)) {
            if (Character.isLowerCase(chessBoard[pieceRow][pieceCol])) {
                if (canWhiteCapture(pieceRow, pieceCol, eRow - 1, eCol - 2)) {
                    return true;
                }
            } else {
                if (canBlackCapture(pieceRow, pieceCol, eRow - 1, eCol - 2)) {
                    return true;

                }
            }
        }

        if (validmove(pieceRow, pieceCol, eRow - 1, eCol + 2)) {
            if (Character.isLowerCase(chessBoard[pieceRow][pieceCol])) {
                if (canWhiteCapture(pieceRow, pieceCol, eRow - 1, eCol + 2)) {
                    return true;
                }
            } else {
                if (canBlackCapture(pieceRow, pieceCol, eRow - 1, eCol + 2)) {
                    return true;

                }
            }
        }

        if (validmove(pieceRow, pieceCol, eRow + 1, eCol - 2)) {
            if (Character.isLowerCase(chessBoard[pieceRow][pieceCol])) {
                if (canWhiteCapture(pieceRow, pieceCol, eRow + 1, eCol - 2)) {
                    return true;
                }
            } else {
                if (canBlackCapture(pieceRow, pieceCol, eRow + 1, eCol - 2)) {
                    return true;

                }
            }
        }
        if (validmove(pieceRow, pieceCol, eRow + 1, eCol + 2)) {
            if (Character.isLowerCase(chessBoard[pieceRow][pieceCol])) {
                if (canWhiteCapture(pieceRow, pieceCol, eRow + 1, eCol + 2)) {
                    return true;
                }
            } else {
                if (canBlackCapture(pieceRow, pieceCol, eRow + 1, eCol + 2)) {
                    return true;

                }
            }
        }
        if (validmove(pieceRow, pieceCol, eRow - 2, eCol - 1)) {
            if (Character.isLowerCase(chessBoard[pieceRow][pieceCol])) {
                if (canWhiteCapture(pieceRow, pieceCol, eRow - 2, eCol - 1)) {
                    return true;
                }
            } else {
                if (canBlackCapture(pieceRow, pieceCol, eRow - 2, eCol - 1)) {
                    return true;

                }
            }
        }

        if (validmove(pieceRow, pieceCol, eRow - 2, eCol + 1)) {
            if (Character.isLowerCase(chessBoard[pieceRow][pieceCol])) {
                if (canWhiteCapture(pieceRow, pieceCol, eRow - 2, eCol + 1)) {
                    return true;
                }
            } else {
                if (canBlackCapture(pieceRow, pieceCol, eRow - 2, eCol + 1)) {
                    return true;

                }
            }
        }

        if (validmove(pieceRow, pieceCol, eRow + 2, eCol - 1)) {
            if (Character.isLowerCase(chessBoard[pieceRow][pieceCol])) {
                if (canWhiteCapture(pieceRow, pieceCol, eRow + 2, eCol - 1)) {
                    return true;
                }
            } else {
                if (canBlackCapture(pieceRow, pieceCol, eRow + 2, eCol - 1)) {
                    return true;

                }
            }
        }

        if (validmove(pieceRow, pieceCol, eRow + 2, eCol + 1)) {
            if (Character.isLowerCase(chessBoard[pieceRow][pieceCol])) {
                if (canWhiteCapture(pieceRow, pieceCol, eRow + 2, eCol + 1)) {
                    return true;
                }
            } else {
                if (canBlackCapture(pieceRow, pieceCol, eRow + 2, eCol + 1)) {
                    return true;

                }
            }
        }

        return false;
    }

    /**
     * Checks if a piece at the specified position can make a valid vertical move.
     * I have examined multiple scenarios for vertical movement for different pieces
     * and have developed
     * distinct logical frameworks to handle each of these scenarios.
     *
     * @param pieceRow The row index of the piece's current position.
     * @param pieceCol The column index of the piece's current position.
     * @param eRow     The row index of the target position.
     * @param eCol     The column index of the target position.
     * @return True if the piece can make a valid vertical move to the target
     *         position, false otherwise.
     */
    public boolean canMoveVertically(int pieceRow, int pieceCol, int eRow, int eCol) {

        if (validmove(pieceRow, pieceCol, eRow + 1, eCol)) {
            if (Character.isLowerCase(chessBoard[pieceRow][pieceCol])) {
                if (canWhiteCapture(pieceRow, pieceCol, eRow + 1, eCol)) {
                    return true;
                }
            } else {
                if (canBlackCapture(pieceRow, pieceCol, eRow + 1, eCol)) {
                    return true;

                }
            }
        }

        if (validmove(pieceRow, pieceCol, eRow - 1, eCol)) {
            if (Character.isLowerCase(chessBoard[pieceRow][pieceCol])) {
                if (canWhiteCapture(pieceRow, pieceCol, eRow - 1, eCol)) {
                    return true;
                }
            } else {
                if (canBlackCapture(pieceRow, pieceCol, eRow - 1, eCol)) {
                    return true;

                }
            }
        }

        return false;
    }

    /**
     * Checks if a piece at the specified position can make a valid horizontal move.
     * I have examined multiple scenarios for horizontal movement for different
     * pieces and have developed distinct logical frameworks to handle each of these
     * scenarios.
     *
     * @param pieceRow The row index of the piece's current position.
     * @param pieceCol The column index of the piece's current position.
     * @param eRow     The row index of the target position.
     * @param eCol     The column index of the target position.
     * @return True if the piece can make a valid vertical move to the target
     *         position, false otherwise.
     */
    public boolean canMoveHorizontally(int pieceRow, int pieceCol, int eRow, int eCol) {

        if (validmove(pieceRow, pieceCol, eRow, eCol + 1)) {
            if (Character.isLowerCase(chessBoard[pieceRow][pieceCol])) {
                if (canWhiteCapture(pieceRow, pieceCol, eRow, eCol + 1)) {
                    return true;
                }
            } else {
                if (canBlackCapture(pieceRow, pieceCol, eRow, eCol + 1)) {
                    return true;

                }
            }
        }

        if (validmove(pieceRow, pieceCol, eRow, eCol - 1)) {
            if (Character.isLowerCase(chessBoard[pieceRow][pieceCol])) {
                if (canWhiteCapture(pieceRow, pieceCol, eRow, eCol - 1)) {
                    return true;
                }
            } else {
                if (canBlackCapture(pieceRow, pieceCol, eRow, eCol - 1)) {
                    return true;

                }
            }
        }

        return false;
    }

    /**
     * Checks if a piece at the specified position can make a valid Diagonal move.
     * I have examined multiple scenarios for diagonal movement for different pieces
     * and have developed
     * distinct logical frameworks to handle each of these scenarios.
     *
     * @param pieceRow The row index of the piece's current position.
     * @param pieceCol The column index of the piece's current position.
     * @param eRow     The row index of the target position.
     * @param eCol     The column index of the target position.
     * @return True if the piece can make a valid vertical move to the target
     *         position, false otherwise.
     */
    public boolean canMoveDiagonally(int pieceRow, int pieceCol, int eRow, int eCol) {
        if (validmove(pieceRow, pieceCol, eRow + 1, eCol + 1)) {
            if (Character.isLowerCase(chessBoard[pieceRow][pieceCol])) {
                if (canWhiteCapture(pieceRow, pieceCol, eRow + 1, eCol + 1)) {
                    return true;
                }
            } else if (Character.isUpperCase(chessBoard[pieceRow][pieceCol])) {
                if (canBlackCapture(pieceRow, pieceCol, eRow + 1, eCol + 1)) {
                    return true;

                }
            }
        }
        if (validmove(pieceRow, pieceCol, eRow - 1, eCol - 1)) {
            if (Character.isLowerCase(chessBoard[pieceRow][pieceCol])) {
                if (canWhiteCapture(pieceRow, pieceCol, eRow - 1, eCol - 1)) {
                    return true;
                }
            } else {
                if (canBlackCapture(pieceRow, pieceCol, eRow - 1, eCol - 1)) {
                    return true;

                }
            }
        }

        if (validmove(pieceRow, pieceCol, eRow - 1, eCol + 1)) {
            System.out.println("ayuuu3");
            if (Character.isLowerCase(chessBoard[pieceRow][pieceCol])) {
                if (canWhiteCapture(pieceRow, pieceCol, eRow - 1, eCol + 1)) {
                    return true;
                }
            } else if (Character.isUpperCase(chessBoard[pieceRow][pieceCol])) {
                if (canBlackCapture(pieceRow, pieceCol, eRow - 1, eCol + 1)) {
                    return true;

                }
            }
        }

        if (validmove(pieceRow, pieceCol, eRow + 1, eCol - 1)) {
            if (Character.isLowerCase(chessBoard[pieceRow][pieceCol])) {
                if (canWhiteCapture(pieceRow, pieceCol, eRow + 1, eCol - 1)) {
                    return true;
                }
            } else {
                if (canBlackCapture(pieceRow, pieceCol, eRow + 1, eCol - 1)) {
                    return true;

                }
            }
        }

        return false;
    }

    /**
     * Checks if a white piece at the specified position can capture an opponent's
     * piece or move to an empty square.
     *
     * @param pieceRow The row index of the white piece's current position.
     * @param pieceCol The column index of the white piece's current position.
     * @param eRow     The row index of the target position.
     * @param eCol     The column index of the target position.
     * @return True if the white piece can capture an opponent's piece or move to an
     *         empty square at the target position, false otherwise.
     */
    public boolean canWhiteCapture(int pieceRow, int pieceCol, int eRow, int eCol) {

        if ((Character.isUpperCase(chessBoard[eRow][eCol])) || (chessBoard[eRow][eCol] == '.')) {

            return true;
        }

        return false;
    }

    /**
     * Checks if a black piece at the specified position can capture an opponent's
     * piece or move to an empty square.
     *
     * @param pieceRow The row index of the white piece's current position.
     * @param pieceCol The column index of the white piece's current position.
     * @param eRow     The row index of the target position.
     * @param eCol     The column index of the target position.
     * @return True if the white piece can capture an opponent's piece or move to an
     *         empty square at the target position, false otherwise.
     */
    public boolean canBlackCapture(int pieceRow, int pieceCol, int eRow, int eCol) {

        if (Character.isLowerCase(chessBoard[eRow][eCol]) || (chessBoard[eRow][eCol] == '.')) {
            return true;
        }
        return false;
    }
}

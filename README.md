# Chess-Game-
This is a basic chessgame in java language in which two players can play game by applying the move sequences in one file and importing a chessboard configuration file that holds the chessboard.

Overview
In this problem of the chess game, to ensure that the chess pieces move according to the original rules, I attempt to add a few functions in the chess problem. A text file (.txt) containing a series of moves for both black and white in alternating turns is how the user enters the data. The logic of few functions is defined in a way that determines whether a move is valid using certain mathematical operations before returning true or false. An additional input is the board configuration, which can be a N*M rectangle with fewer than 26 columns in any size.
Subsequently, the problem is primarily separated into three stages. The board configuration file is read in the first phase, the file is printed in the second if the format complies with the requirements, and the final phase verifies that the move sequence is correct in accordance with chess rules before making the move. Either true or false is returned in each of the three phases. Other than this, there aren't many features to save the captured pieces for both the white and black pieces, or to determine if the king is under control.
While the code is running, the limitations of the problem must be handled. Managing appropriate inputs for the move sequence and board layout is a crucial aspect that needs to be considered. A small number of approximations are made to ensure that the problem stays within its intended scope and that the results overlap with what was anticipated.

Files and external data
The task at hand is to implementing a Java class called ‘FollowChess’ to control the chessboard setup and offers different kind of methods for performing a different operation. The ‘A1’ class is a derived class of the FollowChess which illustrates the usage of the ‘FollowChess’ class. Moreover, there are also two files called board.txt and Movesequence.txt
‘FollowChess’ class contains the methods to load the board after taking the input and print it. Moreover, in this class, there is a method for applying the move sequences for playing chess one by one after validating weather the move is valid or invalid. It has also the methods for storing the pieces in the order in which they are captured by both white and black player. The ‘inCheck’ method in this class is check whether the white and black king is in the check or not.
‘A1’ class demonstrates the usage of the ‘followChess’ class for loading the board, applying the move sequences and printing the final configuration.  In short, in the ‘A1’ class we have the main method to call the methods which are in the ‘followChess’.
‘board.txt’ is an input file for storing the initial board configuration in the teams of row and column and it pass through the function loadboard which read the whole file and load it into the chessboard if the input file is valid otherwise return false.
In ‘Movesquence.txt” file, the moves that user want to apply in the chessgame are stored line by line and it pass into the function ‘applymovesequence’ to check whether the move is valid or not.check the alternative play by color and this is responsible for checking whether the moves that user want to execute is the valid or not and execute it if it is valid. When the move is valid, it applies into the chessboard. 
 
(fig1. Flowchart of my working implementation)



Data Structures and their relations to each other
The solution does not demand time or space efficiency. As a result, we select the most straightforward data structures to do the job.
In the ‘followchess’ class, I use the array data structure to implement the whole code. First of all, I have used the 2D array to display the chessboard as it is rectangular in the shape and take input from the input file and convert into the 2D array and each piece in the chessboard has their location in the form of the row and column. The reason behind why I choose this data structures is because it is very easy to manipulate the data into the 2D array and I have good experience in the manipulation of the data into the array so I thought that it is very beneficial for me to solve this problem.
I also use list data structure to store the order of capturing pieces for both the players white and black and it is very convenient it just because of having dynamic nature and the data that will be stored it must be in the sorted order which is very useful to store the pieces that are captured by the payers in the order in which they are captured.

Assumptions
•	Pawn don’t move twice in the first move.
•	The board configuration file that we take as an input must be a rectangle and it must have only one king for both the black and white player.
•	The first move should be the white player move and second one is for black player and both of them are playing alternatively.
•	While applying the move, at the destination, it must be an opponent player or ‘.’.
•	In the capture order, the order of the captured piece should be in the same order in which they are captured.
•	Knight moves without checking if the path is clear or not from the source to destination.

Choices
•	The chosen data structure 2D array helps to easy access, manipulation and validate the input.
•	The pieces that are captured by the white player must be stored in the capture list for the white player and for black player it must be stored in the capture list of the black player. 
•	The move sequence file must be containing the moves in the format of “a 1 a 2” and the input into the piece can move function must be in the format of “a 1”and the moves should be in the valid boundary of the chessboard.
•	I have used the different lists to store the capture list for white and black.


Key Algorithms and design elements 

•	boolean loadBoard( BufferedReader boardStream ):
-	loadboard method reads the initial chessboard configuration from a Bufferedreader and validates the chessboard by checking is it rectangular or not and it must contain at least one king for both the color. After checking it, it populates the 2D array chessboard with the characters that are representing the different pieces.

•	boolean printBoard( PrintWriter outstream )
-	Loadboard function print the current chessboard configuration to a printwriter and it iterates through the whole rows of the chessboard and print each row and display it.

•	aboolean applyMoveSequence( BufferedReader moveStream )
-	applymovesequence method executes the chess moves line by line from the bufferedreader. Basically, this function first of all check whether the format of the move is valid or not and switch the player once the one move execute successfully. In this method I called the validmove function and if it returns true than move applied to the chessboard and update the chessboard. 

•	private boolean validmove(int startRow, int startCol, int endRow, int endCol)
-	This method checks if the move is within the bound or not and validates that the starting point is belonging to the current player or not. This function also, check whether the move is valid or invalid for each piece as it contains the logic for all the all pieces. Apart from that, it checks that the destination piece must be an opponent or ‘.’. Moreover, this method ensures that the move doesn’t go through the other pieces except knight.

•	public void makeMove(int startRow, int startCol, int endRow, int endCol)
-	This function basically updates the chessboard by swapping the starting piece with the ending piece using temp variable. After each capturing, it stores the captured piece into the list of capture order for both white and black player.

•	List<Character> captureOrder( int player )
-	the capture order function simply returns the capturing order for both white and black player.

•	boolean inCheck( int player ):
-	This function first of all find the position of the particular color’s king in the current chessboard and after getting the row and col of the king position and check for each and every position in the chessboard by calling the function validmove and giving the parameters of the king as a ending position and each position of array as a starting position with the help of the for loop. 


•	boolean pieceCanMove(String boardPosition):
-	This method checks weather piece has at least one valid move in the current chessboard and I have created different functions for each piece for an example, canMoveVertically and canMoveHorizontally checks if any piece can move in that direction or not and this function return true or false.

•	Design elements:
-	The code uses the object-oriented approach and developed different functions for different logic. This code follows the modular approach as each function responsible for performing the specific tasks. Certain operations, such loading the board, printing the board, and applying move sequences, are accomplished by different methods.
Limitations
•	This implementation doesn’t allow the checkmate configuration.
•	In this solution, I don’t implement the logic for doing undo after executing the move if any player wants to change the move as most of the online games provide it.
•	These implementations don’t handle some advance chess rules like castling, pawn promotions and en-passant capture.
References

Behler, Christian. “Finding All Legal Chess Moves.” Medium, 2 Mar. 2021, levelup.gitconnected.com/finding-all-legal-chess-moves-2cb872d05bc6. Accessed 20 Jan. 2024.
“How to Determine If a Path Is Free of Obstacles in Chess?” Stack Overflow, stackoverflow.com/questions/4305205/how-to-determine-if-a-path-is-free-of-obstacles-in-chess. Accessed 22 Jan. 2024.


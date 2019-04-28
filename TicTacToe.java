import java.util.Scanner;
import java.io.IOException;
/**
 * Tic-Tac-Toe: Two-player console, non-graphics, non-OO version.
 * All variables/methods are declared as static (belong to the class)
 *  in the non-OO version.
 */
public class TicTacToe {
   // Name-constants to represent the seeds and cell contents
   public static final int EMPTY = 0;
   public static final int CROSS = 1;
   public static final int NOUGHT = 2;
 
   // Name-constants to represent the various states of the game
   public static final int PLAYING = 0;
   public static final int DRAW = 1;
   public static final int CROSS_WON = 2;
   public static final int NOUGHT_WON = 3;
 
   // The game board and the game status
   
   /** [AUSHAF][1] Enhance the size of the board to be adjustable - START */
   //public static final int ROWS = 3, COLS = 3; // number of rows and columns
   public static final int board_limit = 100; // limit of the board size, we can set whatever we like
   public static int[][] board = new int[board_limit][board_limit]; // game board in 2D array
                                                      //  containing (EMPTY, CROSS, NOUGHT)
   public static int BOARD_SIZE; // number of board size
   public static int ROWS, COLS; // number of rows and columns that will follow the board size
   /** [1] Enhance the size of the board to be adjustable - END */
   
   /** [AUSHAF][2] Initiate a new system to check who has won and is it a draw - START */
   public static final int NOT_POSSIBLE = 4; // A new name-constants to represent the seeds and cell contents
   
   public static int[] rowsLineStatus = new int[board_limit]; // an array that represents status of every row
   public static int[] columnsLineStatus = new int[board_limit]; // an array that represents status of every column
   public static int firstDiagonal, secondDiagonal; // status of both diagonal
   
   public static int possibleLineCount; // a number that shows how many lines possible to be formed currently
   
   public static int[] isAnyRowsWon = new int[board_limit]; // an array that shows number of characters in every row
   public static int[] isAnyColumnsWon = new int[board_limit]; // an array that shows number of characters in every column
   public static int isFirstDiagonalWon, isSecondDiagonalWon; // number of characters in both diagonal
   /** [2] Initiate a new system to check who has won and is it a draw - END */
   
   public static int currentState;  // the current state of the game
                                    // (PLAYING, DRAW, CROSS_WON, NOUGHT_WON)
   public static int currentPlayer; // the current player (CROSS or NOUGHT)
   public static int currntRow, currentCol; // current seed's row and column
 
   public static Scanner in = new Scanner(System.in); // the input Scanner
 
   /** The entry main method (the program starts here) */
   public static void main(String[] args) {
	   // Initialize the game-board and current status
	   
	   /** [AUSHAF][1] Input the size of the board - START */
	   try { // clear screen function
		   new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	   } catch (IOException | InterruptedException ex) {
		   ex.printStackTrace();
	   }
	   System.out.print("What is the size of the board that you like ? (x^2) ");
	   BOARD_SIZE = in.nextInt(); // user inputing the size of the board
	   ROWS = BOARD_SIZE; COLS = BOARD_SIZE; // adjusting the size of the rows and columns
	   /** [1] Input the size of the board - END */
	   
	   initGame();
	   // Play the game once
	   do {
		   playerMove(currentPlayer); // update currentRow and currentCol
		   updateGame(currentPlayer, currntRow, currentCol); // update currentState
		   printBoard();
		   // Print message if game-over
		   if (currentState == CROSS_WON) {
			   System.out.println("'X' won! Bye!");
		   } else if (currentState == NOUGHT_WON) {
			   System.out.println("'O' won! Bye!");
		   } else if (currentState == DRAW) {
			   System.out.println("It's a Draw! Bye!");
		   }
		   // Switch player
		   currentPlayer = (currentPlayer == CROSS) ? NOUGHT : CROSS;
	   } while (currentState == PLAYING); // repeat if not game-over
   }
 
   /** Initialize the game-board contents and the current states */
   public static void initGame() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            board[row][col] = EMPTY;  // all cells empty
         }
      }
      
      /** [AUSHAF][2] Initiate a new system to check who has won and is it a draw - START */
      for (int i = 0; i < BOARD_SIZE; ++i) {
    	  rowsLineStatus[i] = EMPTY;  // all cells empty
          columnsLineStatus[i] = EMPTY;  // all cells empty
    	  isAnyRowsWon[i] = EMPTY;  // set all rows characters into 0
          isAnyColumnsWon[i] = EMPTY;  // set all columns characters into 0
      }
      firstDiagonal = EMPTY; secondDiagonal = EMPTY; // initiate the status of both diagonal
      isFirstDiagonalWon = EMPTY; isSecondDiagonalWon = EMPTY; // set all diagonals characters into 0
      
      possibleLineCount = (BOARD_SIZE * 2) + 2; // initiate the possible lines formed
      /** [2] Initiate a new system to check who has won and is it a draw - END */
      
      currentState = PLAYING; // ready to play
      currentPlayer = CROSS;  // cross plays first
   }
 
   /** Player with the "theSeed" makes one move, with input validation.
       Update global variables "currentRow" and "currentCol". */
   public static void playerMove(int theSeed) {
      boolean validInput = false;  // for input validation
      do {
         if (theSeed == CROSS) {
            System.out.print("Player 'X', enter your move (row[1-" + BOARD_SIZE + "] column[1-" + BOARD_SIZE + "]): ");
         } else {
            System.out.print("Player 'O', enter your move (row[1-" + BOARD_SIZE + "] column[1-" + BOARD_SIZE + "]): ");
         }
         int row = in.nextInt() - 1;  // array index starts at 0 instead of 1
         int col = in.nextInt() - 1;
         if (row >= 0 && row < ROWS && col >= 0 && col < COLS && board[row][col] == EMPTY) {
            currntRow = row;
            currentCol = col;
            board[currntRow][currentCol] = theSeed;  // update game-board content
            validInput = true;  // input okay, exit loop
            
            /** [AUSHAF][2] Initiate a new system to check who has won and is it a draw - START */
            if (rowsLineStatus[row] == EMPTY) { //set the status and amount of characters in every row
            	rowsLineStatus[row] = theSeed;
            	isAnyRowsWon[row]++;
            } else if (rowsLineStatus[row] == theSeed) {
            	isAnyRowsWon[row]++;
            } else if (rowsLineStatus[row] != NOT_POSSIBLE) {
            	rowsLineStatus[row] = NOT_POSSIBLE;
            	possibleLineCount--;
            }
            
            if (columnsLineStatus[col] == EMPTY) { //set the status and amount of characters in every column
            	columnsLineStatus[col] = theSeed;
            	isAnyColumnsWon[col]++;
            } else if (columnsLineStatus[col] == theSeed) {
            	isAnyColumnsWon[col]++;
            } else if (columnsLineStatus[col] != NOT_POSSIBLE) {
            	columnsLineStatus[col] = NOT_POSSIBLE;
            	possibleLineCount--;
            }
            
            if (row == col) { //set the status and amount of characters for the first diagonal
            	if (firstDiagonal == EMPTY) {
            		firstDiagonal = theSeed;
            		isFirstDiagonalWon++;
            	} else if (firstDiagonal == theSeed) {
            		isFirstDiagonalWon++;
            	} else if (firstDiagonal != NOT_POSSIBLE) {
	            	firstDiagonal = NOT_POSSIBLE;
	                possibleLineCount--;
	            }
            }
            
            if (row + col + 1 == BOARD_SIZE) { //set the status and amount of characters for the second diagonal
            	if (secondDiagonal == EMPTY) {
            		secondDiagonal = theSeed;
            		isSecondDiagonalWon++;
            	} else if (secondDiagonal == theSeed) {
            		isSecondDiagonalWon++;
            	} else if (secondDiagonal != theSeed && secondDiagonal != NOT_POSSIBLE) {
	            	secondDiagonal = NOT_POSSIBLE;
	                possibleLineCount--;
	            }
            }
            /** [2] Initiate a new system to check who has won and is it a draw - END */
         } else {
            System.out.println("This move at (" + (row + 1) + "," + (col + 1)
                  + ") is not valid. Try again...");
         }
      } while (!validInput);  // repeat until input is valid
   }
 
   /** Update the "currentState" after the player with "theSeed" has placed on
       (currentRow, currentCol). */
   public static void updateGame(int theSeed, int currentRow, int currentCol) {
      if (hasWon(theSeed, currentRow, currentCol)) {  // check if winning move
         currentState = (theSeed == CROSS) ? CROSS_WON : NOUGHT_WON;
      } else if (isDraw()) {  // check for draw
         currentState = DRAW;
      }
      // Otherwise, no change to currentState (still PLAYING).
   }
 
   /** Return true if it is a draw (no more empty cell) */
   // TODO: Shall declare draw if no player can "possibly" win
   public static boolean isDraw() {
	   
	   /** [AUSHAF][2] Check if is it a draw - START */
	   if (possibleLineCount == 0) return true; // no possible line to be formed, it's a draw
	   else return false;
	   /** [2] Check if is it a draw - END */
	   
//      for (int row = 0; row < ROWS; ++row) {
//         for (int col = 0; col < COLS; ++col) {
//            if (board[row][col] == EMPTY) {
//               return false;  // an empty cell found, not draw, exit
//            }
//         }
//      }
//      return true;  // no empty cell, it's a draw
   }
 
   /** Return true if the player with "theSeed" has won after placing at
       (currentRow, currentCol) */
   public static boolean hasWon(int theSeed, int currentRow, int currentCol) {
	   
	   /** [AUSHAF][2] Initiate a new system to check who has won and is it a draw - START */
	   if (isAnyRowsWon[currentRow] == BOARD_SIZE) return true; // if current row is full
	   if (isAnyColumnsWon[currentCol] == BOARD_SIZE) return true; // if current column is full
	   if (isFirstDiagonalWon == BOARD_SIZE) return true; // if first diagonal is full
	   if (isSecondDiagonalWon == BOARD_SIZE) return true; // if second diagonal is full
	   return false; // if no one has won
	   /** [2] Initiate a new system to check who has won and is it a draw - END */
		   
//      return (board[currentRow][0] == theSeed         // 3-in-the-row
//                   && board[currentRow][1] == theSeed
//                   && board[currentRow][2] == theSeed
//              || board[0][currentCol] == theSeed      // 3-in-the-column
//                   && board[1][currentCol] == theSeed
//                   && board[2][currentCol] == theSeed
//              || currentRow == currentCol            // 3-in-the-diagonal
//                   && board[0][0] == theSeed
//                   && board[1][1] == theSeed
//                   && board[2][2] == theSeed
//              || currentRow + currentCol == 2  // 3-in-the-opposite-diagonal
//                   && board[0][2] == theSeed
//                   && board[1][1] == theSeed
//                   && board[2][0] == theSeed);
	   
	   /** [AUSHAF][1] Enhance the hasWon function to be flexible - START */
//	   boolean isLine = true;
//	   for (int i = 0; i < BOARD_SIZE; i++) {			// check line in the row
//		   if (board[currentRow][i] != theSeed) {
//			   isLine = false;
//			   break;
//		   } 
//	   }
//	   if (isLine) return true; else isLine = true;
//	   
//	   for (int i = 0; i < BOARD_SIZE; i++) {			// check line in the column
//		   if (board[i][currentCol] != theSeed) {
//			   isLine = false;
//			   break;
//		   } 
//	   }
//	   if (isLine) return true; else isLine = true;
//	   
//	   for (int i = 0; i < BOARD_SIZE; i++) {			// check the first diagonal
//		   if (board[i][i] != theSeed) {
//			   isLine = false;
//			   break;
//		   } 
//	   }
//	   if (isLine) return true; else isLine = true;
//	   
//	   for (int i = 0; i < BOARD_SIZE; i++) {			// check the second diagonal
//		   if (board[i][BOARD_SIZE - 1 - i] != theSeed) {
//			   isLine = false;
//			   break;
//		   } 
//	   }
//	   return isLine;
	   /** [1] Enhance the hasWon function to be flexible - END */
   }
 
   /** Print the game board */
   public static void printBoard() {
	  /** [AUSHAF][3] Enhance the game display - START */
	  try { // clear screen function
		  new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	  } catch (IOException | InterruptedException ex) {
		  ex.printStackTrace();
	  }
	  System.out.println();
	  System.out.println();
	  System.out.println();
	  /** [3] Enhance the game display - END */
      for (int row = 0; row < ROWS; ++row) {
    	 System.out.print("       ");
         for (int col = 0; col < COLS; ++col) {
            printCell(board[row][col]); // print each of the cells
            if (col != COLS - 1) {
               System.out.print("|");   // print vertical partition
            }
         }
         System.out.println();
         if (row != ROWS - 1) {
        	 System.out.print("       ");
			 for (int i = 0; i < BOARD_SIZE; i++) {
				 if (i == BOARD_SIZE - 1) System.out.println("---");
				 else System.out.print("----"); // print horizontal partition 
			 }
         }
      }
	  System.out.println();
	  System.out.println();
	  System.out.println();
   }
 
   /** Print a cell with the specified "content" */
   public static void printCell(int content) {
      switch (content) {
         case EMPTY:  System.out.print("   "); break;
         case NOUGHT: System.out.print(" O "); break;
         case CROSS:  System.out.print(" X "); break;
      }
   }
}
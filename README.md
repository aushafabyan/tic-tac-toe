# tic-tac-toe
This is a java code for tic tac toe game. Feel free to give feedback.



There are some important marks in the code with format [AUSHAF][xxx],
below is some brief explanations of the enhancement:

[1] Enhancement to make the size of the tictactoe board adjustable

    The original tictactoe is a 3 x 3 board game with 2 players. 
    Now, players can input their desired size of board, whether it's 4 x 4, 5 x 5, or even more than that.
    To be able to do that, I enhanced the input in the beginning of the game and the hasWon function.
    Although, after that I don't use the enhancement anymore due to new system to determine who has won and is it a draw.
    
[2] Enhancement for new sistem to determine who has won and is it a draw.

    The original codes will do checking for all cells in the board to check who has won or when the game is draw.
    For the original 3 x 3 board it seeems very viable, 
    but as I implemented the new adjustable size of board, it will take a long time to check all cells for every turn.
    Thus, we need a new method to do the checking.
    
    Because of that reason above, I use Dynamic Programming technique to store the status of every line possible, 
    and use it for the checking in every turns.
    
    The advantages of this new system are:
    1. It needs shorter runtime than checking all cells which is n x n every turns.
    2. It can tell whether the game is still winnable for both side or not.
       In other words, if there is no possibility for both players to win the game do to the current condition, 
       the game will ends with 'draw' notification
       
    The idea of the method:
    1. Declare two array to store the status of every rows and columns, and two other variables for both of the diagonals.
    2. Declare two array to store the amount of characters in every rows and columns, and two other variables for both of the diagonals.
    3. Declare a variable to store POSSIBLE_LINES, for a BOARD_SIZE board, the possible lines are BOARD_SIZE + BOARD_SIZE + 2.
    3. On the beginning, initiate all variables above with 0 or EMPTY status.
    4. Let say, we play 3 x 3, and 'X' player put the move in [2 2]. What we do is :
        * check if row 2 status is EMPTY, if yes, put 'X' in the status of row 2
        * check if column 2 status is EMPTY, if yes, put 'X' in the status column 2
        * check if [2 2] belong to a diagonal, if yes, put 'X' in the corresponding diagonal
       now, let say 'O' player, then he put the move in [1 2]. What we do is :
        * check if row 1 status is EMPTY, if yes, put 'X' in the status of row 1
        * because of column 2 status is 'X' and now is 'O' move, it means that column 2 will never be a "line", then change the status become 'NOT_POSSIBLE'
        * the amount of POSSIBLE_LINES minus 1
    5. We also increase amount of every characters in a row, column, or diagonal everytime when players put their move.
    
    The checking:
    1. It is a draw when, the POSSIBLE_LINES variable equal to 0.
    2. One of the players win if there is a row, column, or diagonal that become a 'line'.
       In this case is when the amount of one characters in a row, column, or diagonal equal to BOARD_SIZE
       
[3] The display of the game

    It's hard for me to see the condition of the board with so many boards from the previous turns.
    So, I implemented clear screen for the new display.

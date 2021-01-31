 /*  Name: Allison Chen
  *  PennKey: allchen
  *  Recitation: 209
  *
  *  A class that represents the Connect 4 board.
  */

public class Board {

    private static final int ROWS = 6; // number of rows on the board.
    private static final int COLUMNS = 7; // number of columns on the board.

    private Disc[][] board; // 2D array of Disc objects to represent the board

    // whether the game is currently listening for the player's mouse input
    private boolean mouseListeningMode;

    // tells if user was pressing the mouse in the previous update call.
    private boolean mouseWasPressedLastUpdate;

    private int playerMode; // 2 player or 1 player game
    private int turn; // which player's turn it is
    private char player; // R player and Y player

   /*
    * Description: constructor for Board object.
    *              initializes all cells to an empty disc.
    *              R is the first player.
    *              sets initial conditions for
    *              mouseListeningMode (mouse is listening).
    * Input:       which board (2 or 1 player) that we want to play
    * Output:      n/a
    */
    public Board(int playerMode) {

        mouseListeningMode = true;
        mouseWasPressedLastUpdate = false;

        //first player will always be R
        player = 'R';
        turn = 0;
        this.playerMode = playerMode;

        board = new Disc [ROWS][COLUMNS];

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                board[row][col] = new Disc();
            }
        }
    }


   /*
    * Description: getter method for disc objects.
    * Input:       the row and column of the disc we want.
    * Output:      the disc object.
    */
    public Disc getDisc(int row, int col) {
        return board[row][col];
    }


   /*
    * Description: method that drops the disc.
    *              if the column isn't full we drop the disc
    *              into the next available row.
    * Input:       the column we are dropping the disc into
    *              the disc value (player) that we are dropping
    * Output:      n/a
    */
    public void drop(int column, char discVal) {
        int row = 0;

        if (!isFull(column)) {

            //checking the next available (empty) row we can drop into
            for (int rowNext = 0; rowNext < ROWS; rowNext++) {
                if (board[rowNext][column].getDiscVal() == ' ') {
                    row = rowNext; 
                    break; //breaks at first empty row found
                }
            }
            board[row][column].setDiscVal(discVal);
            board[row][column].textPlayer();
            board[row][column].draw(column, row);
        }
    }


   /*
    * Description: method that checks if the column is full.
    * Input:       the column we are checking.
    * Output:      returns true if full; false otherwise.
    */
    private boolean isFull(int column) {

        // if it never returns false then it must be full
        for (int row = 0; row < ROWS; row++) {
            if (board[row][column].getDiscVal() == ' ') {
                return false;
            }
        }
        return true;
    }


   /*
    * Description: method that draws the board.
    * Input:       n/a
    * Output:      n/a
    */
    public void draw() {

        PennDraw.clear(10, 44, 104);

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) { 
                board[row][col].draw(col, row);
            }
        }
    }


   /*
    * Description: method that updates the board game.
    *              it will drop the discs, updating the player.
    * Input:       n/a
    * Output:      n/a
    */
    public void update() {
        
        // for 1 player mode the first player is randomized
        randomizeFirst();

        // will be true when the game is in effect (no winner/draw)
        while (!checkWinner() && !checkDraw()) {
            if (mouseListeningMode) {
                if (PennDraw.mousePressed()) {
                    mouseListeningMode = false;
                    mouseWasPressedLastUpdate = true;
                }
                else {
                    if (mouseWasPressedLastUpdate) {

                        // for 2 player mode
                        if (playerMode == 2) {

                            // because we always start with R, even turns will be R
                            if (turn % 2 == 0) { 
                                drop((int) PennDraw.mouseX(), 'R'); 
                                player = 'R';
                            } 
                            else {
                                if (turn % 2 == 1) {
                                    drop((int) PennDraw.mouseX(), 'Y'); 
                                    player = 'Y';
                                }
                            }
                            turn++;
                        }

                        // for 1 player mode
                        if (playerMode == 1) {
                            drop((int) PennDraw.mouseX(), 'R');
                            player = 'Y';
                            PennDraw.advance();

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            drop((int) (Math.random() * 7), 'Y');
                            player = 'R';
                        }

                        // prints player mode of game
                        PennDraw.setPenColor(PennDraw.WHITE);
                        PennDraw.text(2, 6.2, "PLAYER MODE: " + playerMode);

                        // if there is a winner
                        if (checkWinner()) {
                            PennDraw.setPenColor(PennDraw.BLACK);
                            PennDraw.setFontSize(100);
                            PennDraw.text(3.5, 3,  player + " WINS!");
                        }

                        // if its a draw
                        if (checkDraw()) {
                            PennDraw.setPenColor(PennDraw.BLACK);
                            PennDraw.setFontSize(100);
                            PennDraw.text(3.5, 3, "DRAW");
                        }

                        mouseWasPressedLastUpdate = false;
                        mouseListeningMode = true;
                    }
                }
            } else mouseListeningMode = true;

            PennDraw.advance(); 
        }
    }


   /*
    * Description: randomizes the first move in
    *              one player mode. 
    *              50% chance the computer starts the game.
    * Input:       n/a
    * Output:      n/a
    */
    public void randomizeFirst() {
        if (playerMode == 1) { 

            // there is an equal chance that random will be 0 or 1
            int random = (int) StdRandom.uniform(0, 2);

            if (random == 0) {
                player = 'R';
            }
            else {
                if (random == 1) {
                    drop((int) (Math.random() * 7), 'Y');
                    random = 0; //setting random back to 0 so that player R goes
                }
            }
        }
    }


   /*
    * Description: method checks the horizontal winning condition.
    * Input:       n/a
    * Output:      true if fulfills winning condition.
    */
    private boolean checkHorizontal() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS - 3; col++) {
                if (board[row][col].getDiscVal() != ' ') {
                    if (board[row][col + 3].getDiscVal() == 'R' && 
                        board[row][col + 2].getDiscVal() == 'R' &&
                        board[row][col + 1].getDiscVal() == 'R' && 
                        board[row][col].getDiscVal() == 'R') {
                        return true;
                    }
                    else {
                        if (board[row][col + 3].getDiscVal() == 'Y' &&
                            board[row][col + 2].getDiscVal() == 'Y' &&
                            board[row][col + 1].getDiscVal() == 'Y' &&
                            board[row][col].getDiscVal() == 'Y') {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


   /*
    * Description: method checks the vertical winning condition.
    * Input:       n/a
    * Output:      true if fulfills winning condition.
    */
    private boolean checkVertical() {
        for (int col = 0; col < COLUMNS; col++) {
            for (int row = 0; row < ROWS - 3; row++) {
                if (board[row][col].getDiscVal() != ' ') {
                    if (board[row + 3][col].getDiscVal() == 'R' &&
                        board[row + 2][col].getDiscVal() == 'R' &&
                        board[row + 1][col].getDiscVal() == 'R' &&
                        board[row][col].getDiscVal() == 'R') {
                        return true;
                    }
                    else {
                        if (board[row + 3][col].getDiscVal() == 'Y' &&
                            board[row + 2][col].getDiscVal() == 'Y' &&
                            board[row + 1][col].getDiscVal() ==  'Y' &&
                            board[row][col].getDiscVal() == 'Y') {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


   /*
    * Description: method checks the decreasing diagonal
    *              (diagonal points downwards) winning condition.
    * Input:       n/a
    * Output:      true if fulfills winning condition.
    */
    private boolean checkDecreasingDiagonal() {
        for (int row =  ROWS - 3; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS - 3; col++) {
                if (board[row][col].getDiscVal() != ' ') {
                    if (board[row - 3][col + 3].getDiscVal() == 'R' &&
                        board[row - 2][col + 2].getDiscVal() == 'R' &&
                        board[row - 1][col + 1].getDiscVal() == 'R' &&
                        board[row][col].getDiscVal() == 'R') {
                        return true;
                    }
                    else {
                        if (board[row - 3][col + 3].getDiscVal() == 'Y' &&
                            board[row - 2 ][col + 2].getDiscVal() == 'Y' &&
                            board[row - 1][col + 1].getDiscVal() == 'Y' &&
                            board[row][col].getDiscVal() == 'Y') {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


   /*
    * Description: method checks the increasing diagonal
    *              (diagonal points upwards) winning condition.
    * Input:       n/a
    * Output:      true if fulfills winning condition.
    */
    private boolean checkIncreasingDiagonal() {
        for (int row = 0; row < ROWS - 3; row++) {
            for (int col = 0; col < COLUMNS - 3; col++) {
                if (board[row][col].getDiscVal() != ' ') {
                    if (board[row + 3][col + 3].getDiscVal() == 'R' &&
                        board[row + 2][col + 2].getDiscVal() == 'R' &&
                        board[row + 1][col + 1].getDiscVal() == 'R' &&
                        board[row][col].getDiscVal() == 'R') {
                        return true;
                    }
                    else {
                        if (board[row + 3][col + 3].getDiscVal() == 'Y' &&
                            board[row + 2][col + 2].getDiscVal() == 'Y' &&
                            board[row + 1][col + 1].getDiscVal() == 'Y' &&
                            board[row][col].getDiscVal() == 'Y') {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


   /*
    * Description: method that checks the draw condition.
    * Input:       n/a
    * Output:      true if no one wins and the board is full.
    */
    private boolean checkDraw() {
        for (int col = 0; col < COLUMNS; col++) {
            if (!isFull(col)) {
                return false;
            }
        }
        return true;
    }


   /*
    * Description: method that checks if there is a winner/draw.
    * Input:       n/a
    * Output:      true if there is a winner or the game ends in draw.
    */
    public boolean checkWinner() {
        return checkDecreasingDiagonal() || checkIncreasingDiagonal() ||
               checkHorizontal() || checkVertical();
    }
}
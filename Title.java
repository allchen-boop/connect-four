/*  Name: Allison Chen
 *  PennKey: allchen
 *  Recitation: 209
 *
 *  A class that represents to title screen for the Connect 4 game.
 */

public class Title {

    private Board board;
    private int playerMode;
    private boolean mouseListeningMode;

   /*
    * Description: constructor for Title object.
    *              initializes a new Board,
    *              passing through the argument of
    *              the player mode the player
    *              selects from this title screen.
    * Input:       n/a
    * Output:      n/a
    */
    public Title() {
        board  = new Board(playerMode);
        mouseListeningMode = true;
    }

   /*
    * Description: getter method for the player mode.
    * Input:       n/a
    * Output:      the player mode
    */
    public int getPlayerMode() {
        return playerMode;
    }

   /*
    * Description: draws the title screen.
    *              the player mode will be 
    *              determined by the user's
    *              mouse click.
    * Input:       n/a
    * Output:      n/a
    */
    public void draw() {

        PennDraw.clear(10, 44, 104);
        PennDraw.setPenColor(PennDraw.WHITE);

        PennDraw.setFontSize(70);
        PennDraw.text(3.5, 3.5, "CONNECT 4");

        PennDraw.setFontSize(12);
        PennDraw.text(3.5, 3, "click to select player mode");

        PennDraw.setFontSize(20);
        PennDraw.text(3.5, 5, "2 PLAYER MODE");
        PennDraw.text(3.5, 1.5, "1 PLAYER MODE");

        PennDraw.enableAnimation(60);

        while (mouseListeningMode) {
            if (PennDraw.mousePressed()) {

                if (PennDraw.mouseY() > 3.5) {
                    playerMode = 2;
                }
                else if (PennDraw.mouseY() < 3) {
                    playerMode = 1;
                }

                // makes computer wait before launching board screen
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                board.draw();
                mouseListeningMode = false;
            }
            //PennDraw.advance();
        }
    }
}
 /*  Name: Allison Chen
  *  PennKey: allchen
  *  Recitation: 209
  *
  *  A class that runs the Connect 4 game.
  */

public class ConnectFour {
     public static void main(String[] args) {

         // infinite while loop so when the game ends it will restart again
         while (true) {
             PennDraw.setCanvasSize(700, 650);
             PennDraw.setXscale(0, 7);
             PennDraw.setYscale(0, 6.5);

             PennDraw.enableAnimation(60);

             Title title = new Title();
             title.draw();

             Board board = new Board(title.getPlayerMode());
             board.update();

             try {
                 Thread.sleep(2000);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }

             PennDraw.advance();
        }
    }
}
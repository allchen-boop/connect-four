 /*  Name: Allison Chen
  *  PennKey: allchen
  *  Recitation: 209
  *
  *  A class that represents each disc in the connect 4 game.
  */

public class Disc {

    // each player is represented by a char
    // ('R', 'Y', or ' ' when empty)
    private char player; 

   /*
    * Description: constructor for a Disc object.
    *              initializes the player as 
    *              the empty slot.
    * Input:       n/a
    * Output:      n/a
    */
    public Disc() {
        player = ' ';
    }

   /*
    * Description: generates the text that
    *              tells which player is up next.
    * Input:       n/a
    * Output:      n/a
    */
    public void textPlayer() {
        PennDraw.setPenColor(10, 44, 104);
        PennDraw.filledRectangle(3.2, 6.2, 2, .1);
        PennDraw.setPenColor(PennDraw.WHITE);
        PennDraw.setFontSize(18);

        if (getDiscVal() == 'R') {
            PennDraw.text(4.4, 6.2, "NEXT PLAYER: Y");
        } else PennDraw.text(4.4, 6.2, "NEXT PLAYER: R");
    }

   /*
    * Description: draws each disc given its
    *              location (row and column).
    * Input:       row and column of disc we are drawing.
    * Output:      n/a
    */
    public void draw(int row, int col) {

        if (player == 'R') {
            PennDraw.setPenColor(236, 55, 55);
        }

        else if (player == 'Y') {
            PennDraw.setPenColor(255, 214, 6);
        }

        else if (player == ' ') {
            PennDraw.setPenColor(PennDraw.WHITE);
        }

        PennDraw.filledCircle(row + .5, col + .5, .4);
    }

   /*
    * Description: setter function that sets
    *              the "value" of the disc.
    * Input:       player ("value") of disc.
    * Output:      n/a
    */
    public void setDiscVal(char discVal) {
        player = discVal; 
    }

   /*
    * Description: getter function that gets
    *              the "value" of the disc.
    * Input:       n/a
    * Output:      the char representing the player value.
    */
    public char getDiscVal() {
        return player;
    }
}
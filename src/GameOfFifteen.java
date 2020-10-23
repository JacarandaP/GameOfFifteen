import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GameOfFifteen extends JFrame implements ActionListener {

    private Brick[][] buttons = new Brick [4][4];
    private Brick emptyButton;

    GameOfFifteen() {

        setLayout(new GridLayout(4, 4));
        //createButtons(); activate this, remove the line below and everthing will go back to where we left.
        shuffleArray(createButtons());
        setSize(300, 300);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /* I wanted to use the suffle method using the array as parameter. To do that, I think the cleanest way
    is to make createButtons return the array buttons. Then ha createButtons as parameter to suffleArray. As
    Brick [][] array was created instead of int [][], the expected return is therefore Brick.
     */
    public Brick[][] createButtons() {
        for (int x = 0; x < 4; x++)
            for (int y = 0; y < 4; y++) {

                buttons[x][y] = new Brick(x,y);
                buttons[x][y].setText(x * 4 + y + 1 + "");
                buttons[x][y].addActionListener(this);
                add(buttons[x][y]);
            }

        buttons[3][3].setText("");
        buttons[3][3].setEnabled(false);
        emptyButton =buttons [3][3];
        return buttons;
    }
/* I don't understand what's wrong. I've check and this method is right, this is how we shuffle the
elements of the array. I think the problem is "empty button" that is fixed or something like that, but I don't
know. I'm sure it will need a really really small fix.
 */
    public void shuffleArray(Brick[][] buttons)
    {
        Random rnd = ThreadLocalRandom.current();
        for (int i = 3; i > 0; i--)
            for (int j = 3; j > 0; j--){

                int indexX = rnd.nextInt(i + 1);
                int indexY = rnd.nextInt(j + 1);

                Brick ab = buttons[indexX][indexY];
                buttons[indexX][indexY] = buttons[i][j];
                buttons[indexX][indexY] = buttons[i][j];
                buttons[i][j] = ab;
        }
        refresh();
    }

    public void moveButton (Brick selectedButton) {
        int tempX= selectedButton.getXPos();
        int tempY= selectedButton.getYPos();

        selectedButton.setXPos(emptyButton.getXPos());
        selectedButton.setYPos(emptyButton.getYPos());

        emptyButton.setXPos(tempX);
        emptyButton.setYPos(tempY);
        buttons[emptyButton.getXPos()][emptyButton.getYPos()] = emptyButton;
        buttons[selectedButton.getXPos()][selectedButton.getYPos()] = selectedButton;

    }

    public void refresh(){
        for (int x = 0; x < 4; x++)
            for (int y = 0; y < 4; y++) {
                remove(buttons[x][y]);
            }
        for (int x = 0; x < 4; x++)
            for (int y = 0; y < 4; y++) {
                add(buttons[x][y]);
            }
        revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Brick selectedButton = (Brick) ae.getSource();
        if (selectedButton.isNextTo(emptyButton)) {
            moveButton(selectedButton);
            refresh();
            //TODO: check if the game is completed successfully.
            //TODO: if not completed sucessfully, refresh into a new game (insert button "play again")
        }

    }
    public static void main(String[] args) {
	GameOfFifteen GameOfFifteenDemo = new GameOfFifteen();
    }


}

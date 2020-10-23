import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GameOfFifteen extends JFrame implements ActionListener {

    private Brick[][] buttons;
    private Brick emptyButton;
    int xGrid = 4;
    int yGrid = 4;
    int gridSize = xGrid * yGrid;

    GameOfFifteen() {


        setLayout(new GridLayout(xGrid, yGrid));
        createButtons(); //activate this, remove the line below and everthing will go back to where we left.
        //shuffleArray(createButtons());
        setSize(300, 300);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /* I wanted to use the suffle method using the array as parameter. To do that, I think the cleanest way
    is to make createButtons return the array buttons. Then ha createButtons as parameter to suffleArray. As
    Brick [][] array was created instead of int [][], the expected return is therefore Brick.
     */
    public Brick[][] createButtons() {
        buttons = new Brick[xGrid][yGrid];

        for (int x = 0; x < xGrid; x++)
            for (int y = 0; y < yGrid; y++) {
                buttons[x][y] = new Brick(x, y);
                buttons[x][y].setText( String.valueOf(x * xGrid + y + 1));
                buttons[x][y].addActionListener(this);
                add(buttons[x][y]);
            }

        buttons[xGrid - 1][yGrid - 1].setText("");
        buttons[xGrid - 1][yGrid - 1].setEnabled(false);
        emptyButton = buttons[xGrid - 1][yGrid - 1];
        return buttons;
    }

    /* I don't understand what's wrong. I've check and this method is right, this is how we shuffle the
    elements of the array. I think the problem is "empty button" that is fixed or something like that, but I don't
    know. I'm sure it will need a really really small fix.
     */
    public void shuffleArray(Brick[][] buttons) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = 3; i > 0; i--)
            for (int j = 3; j > 0; j--) {

                int indexX = rnd.nextInt(i + 1);
                int indexY = rnd.nextInt(j + 1);

                Brick ab = buttons[indexX][indexY];
                buttons[indexX][indexY] = buttons[i][j];
                buttons[indexX][indexY] = buttons[i][j];
                buttons[i][j] = ab;
            }
        refresh();
    }

    public void moveButton(Brick selectedButton) {
        int tempX = selectedButton.getXPos();
        int tempY = selectedButton.getYPos();

        selectedButton.setXPos(emptyButton.getXPos());
        selectedButton.setYPos(emptyButton.getYPos());

        emptyButton.setXPos(tempX);
        emptyButton.setYPos(tempY);
        buttons[emptyButton.getXPos()][emptyButton.getYPos()] = emptyButton;
        buttons[selectedButton.getXPos()][selectedButton.getYPos()] = selectedButton;

    }

    // TODO remove harcode size Look create Buttons
    public void refresh() {
        for (int x = 0; x < xGrid; x++)
            for (int y = 0; y < yGrid; y++) {
                remove(buttons[x][y]);
            }
        for (int x = 0; x < xGrid; x++)
            for (int y = 0; y < yGrid; y++) {
                add(buttons[x][y]);
            }
        revalidate();
    }

    private boolean isCompleted() {
        boolean isSuccess = true;
        if (emptyButton.getXPos() == xGrid - 1 && emptyButton.getYPos() == yGrid - 1) {
            int count = 0;
            for (int x = 0; x < xGrid; x++)
            {
                for (int y = 0; y < yGrid; y++) {
                    count++;
                    int buttonNumber = buttons[x][y].getText() == "" ?  gridSize : Integer.valueOf(buttons[x][y].getText());
                    if ( buttonNumber != count) {
                        isSuccess = false;
                        break;
                    }
                }
            }
        } else
            isSuccess = false;

        System.out.println(isSuccess);
        return isSuccess;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Brick selectedButton = (Brick) ae.getSource();
        if (selectedButton.isNextTo(emptyButton)) {
            moveButton(selectedButton);
              if (isCompleted())
                  System.out.println("suuu");
            refresh();
            //TODO: check if the game is completed successfully.
            //TODO: if not completed sucessfully, refresh into a new game (insert button "play again")
        }


    }

    public static void main(String[] args) {
        GameOfFifteen GameOfFifteenDemo = new GameOfFifteen();
    }


}

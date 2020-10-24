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
    JPanel gameButtonsPanel = new JPanel();
    JPanel sidePanel = new JPanel();
    JButton playButton = new JButton("play again");
    JPanel mainPanel = new JPanel();

    GameOfFifteen() {

        gameButtonsPanel.setLayout(new GridLayout(xGrid, yGrid));
        shuffleArray(createButtons());
        mainPanel.setLayout(new BorderLayout());
        sidePanel.add(playButton);
        mainPanel.add(gameButtonsPanel, BorderLayout.CENTER);
        mainPanel.add(sidePanel, BorderLayout.SOUTH);
        add(mainPanel);
        setVisible(true);
        setSize(300, 300);
        setTitle("Game of 15");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public Brick[][] createButtons() {
        buttons = new Brick[xGrid][yGrid];

        for (int x = 0; x < xGrid; x++)
            for (int y = 0; y < yGrid; y++) {
                buttons[x][y] = new Brick(x, y);
                buttons[x][y].setText( String.valueOf(x * xGrid + y + 1));
                buttons[x][y].addActionListener(this);
                gameButtonsPanel.add(buttons[x][y]);
            }

        buttons[xGrid - 1][yGrid - 1].setText("");
        buttons[xGrid - 1][yGrid - 1].setEnabled(false);
        emptyButton = buttons[xGrid - 1][yGrid - 1];
        return buttons;
    }

    public void shuffleArray(Brick[][] buttons) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = buttons.length-1; i > 0; i--)
            for (int j = buttons[i].length-1; j > 0; j--) {

                int indexX = rnd.nextInt(i + 1);
                int indexY = rnd.nextInt(j + 1);

                exchangePlace(buttons[indexX][indexY], buttons[i][j]);

            }
        refresh();
    }

    public void moveButton(Brick selectedButton) {
        exchangePlace(emptyButton, selectedButton);

    }

    public void exchangePlace(Brick button1, Brick button2) {
        int tempX = button1.getXPos();
        int tempY = button1.getYPos();

        button1.setXPos(button2.getXPos());
        button1.setYPos(button2.getYPos());

        button2.setXPos(tempX);
        button2.setYPos(tempY);
        buttons[button2.getXPos()][button2.getYPos()] = button2;
        buttons[button1.getXPos()][button1.getYPos()] = button1;

    }

    public void refresh() {
        for (int x = 0; x < xGrid; x++)
            for (int y = 0; y < yGrid; y++) {
                gameButtonsPanel.remove(buttons[x][y]);
            }
        for (int x = 0; x < xGrid; x++)
            for (int y = 0; y < yGrid; y++) {
                gameButtonsPanel.add(buttons[x][y]);
            }
        revalidate();
    }

    private boolean isCompleted() {
        boolean isSuccess = true;
        if (emptyButton.getXPos() == xGrid - 1 && emptyButton.getYPos() == yGrid - 1) {
            int expectedValue = 0;
            for (int x = 0; x < xGrid; x++)
            {
                for (int y = 0; y < yGrid; y++) {
                    expectedValue++;
                    int buttonNumber = buttons[x][y].getText().isEmpty() ?  gridSize : Integer.valueOf(buttons[x][y].getText());
                    if ( buttonNumber != expectedValue) {
                        isSuccess = false;
                        break;
                    }
                }
            }
        } else
            isSuccess = false;
//TODO remove sout, we know the method works! :)
        System.out.println(isSuccess);
        return isSuccess;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Brick selectedButton = (Brick) ae.getSource();
        if (selectedButton.isNextTo(emptyButton)) {
            moveButton(selectedButton);
              if (isCompleted())
                  JOptionPane.showMessageDialog(null, "You've won!");
            refresh();

            //TODO: make a ActionEvent med play again button and call shuffle method when the button is pressed.
        }


    }

    public static void main(String[] args) {
        GameOfFifteen GameOfFifteenDemo = new GameOfFifteen();
    }


}

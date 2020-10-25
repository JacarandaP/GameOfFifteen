import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GameOfFifteen extends JFrame implements ActionListener {

    private Brick[][] buttons;
    private Brick emptyButton;
    int xGrid;
    int yGrid;
    int gridSize;
    JPanel gameButtonsPanel = new JPanel();
    JPanel sidePanel = new JPanel();
    JButton playButton;
    JPanel mainPanel = new JPanel();
    //JPanel imagePanel =  new JPanel();
    //String path = "C:\\Users\\Ashkan\\Pictures\\Saved Pictures\\Ashkan.jpg";


    GameOfFifteen() {
        int rowSize = creatWelcomeDialog();
        xGrid = rowSize;
        yGrid = rowSize;
        gridSize = xGrid * yGrid;
        createPanelGame();
    }

    private int creatWelcomeDialog() {
        int n = 0;

        try {
            Integer[] options = {2, 3, 4, 5, 6, 7, 8, 9};
            n = (Integer) JOptionPane.showInputDialog(null, "Choose which size you want to start playing!" +
                            "\nFor example, if you choose 4 it will be " +
                            "\nPuzzle-15 " +
                            "\nIf you want a more difficult challenge,you can choose a larger number",
                    "GAME OF FIFTEEN", JOptionPane.QUESTION_MESSAGE, null, options, options[2]);

        } catch (Exception e) {
            System.exit(0);
        }
        return n;
    }

    private void createPanelGame() {
        gameButtonsPanel.setLayout(new GridLayout(xGrid, yGrid));
        playButton = new JButton("Play again");
        playButton.addActionListener(this);
        createButtons();
        shuffleArray();
        mainPanel.setLayout(new BorderLayout());
        sidePanel.add(playButton);
        mainPanel.add(gameButtonsPanel, BorderLayout.CENTER);
        mainPanel.add(sidePanel, BorderLayout.SOUTH);
        // mainPanel.add(createImage());
        this.add(mainPanel);
        this.setVisible(true);
        this.setSize(xGrid * 100, yGrid * 100);
        this.setLocation(300, 150);
        this.setTitle("Game of 15");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public Brick[][] createButtons() {
        buttons = new Brick[xGrid][yGrid];

        for (int x = 0; x < xGrid; x++)
            for (int y = 0; y < yGrid; y++) {
                buttons[x][y] = new Brick(x, y);
                buttons[x][y].setText(String.valueOf(x * xGrid + y + 1));
                buttons[x][y].addActionListener(this);
                gameButtonsPanel.add(buttons[x][y]);
            }

        buttons[xGrid - 1][yGrid - 1].setText("");
        buttons[xGrid - 1][yGrid - 1].setEnabled(false);
        emptyButton = buttons[xGrid - 1][yGrid - 1];
        return buttons;
    }

    public void shuffleArray() {
        Random rnd = ThreadLocalRandom.current();
        for (int i = buttons.length - 1; i > 0; i--)
            for (int j = buttons[i].length - 1; j > 0; j--) {

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
            for (int x = 0; x < xGrid; x++) {
                for (int y = 0; y < yGrid; y++) {
                    expectedValue++;
                    int buttonNumber = buttons[x][y].getText().isEmpty() ?  gridSize : Integer.parseInt(buttons[x][y].getText());
                    if ( buttonNumber != expectedValue) {
                        isSuccess = false;
                        break;
                    }
                }
            }
        } else
            isSuccess = false;
        return isSuccess;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().getClass() == Brick.class) {
            Brick selectedButton = (Brick) ae.getSource();
            if (selectedButton.isNextTo(emptyButton)) {
                moveButton(selectedButton);
                refresh();
                if (isCompleted())
                    JOptionPane.showMessageDialog(null, "You've won!");
            }
        } else if (ae.getSource().getClass() == JButton.class) {
            shuffleArray();
        }
    }

    public static void main(String[] args) {
        GameOfFifteen GameOfFifteenDemo = new GameOfFifteen();
    }
/*
    private JPanel createImage() {
        ImageIcon icon = new ImageIcon(path);
        Image image = icon.getImage();

        JLabel label1 = new JLabel(icon);
        3
        imagePanel.add(label1);
        return imagePanel;
    }*/

}

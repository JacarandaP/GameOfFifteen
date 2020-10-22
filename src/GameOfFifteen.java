import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOfFifteen extends JFrame implements ActionListener {

    private Brick[][] buttons = new Brick [4][4];
    private Brick emptyButton;

    GameOfFifteen() {

        setLayout(new GridLayout(4, 4));
        createButtons();
        setSize(300, 300);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void createButtons() {
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

    //TODO: make the order of buttons random
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

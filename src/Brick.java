import javax.swing.*;

/**
 * Created by Jacaranda Perez
 * Date: 2020-10-22
 * Project: GameOfFifteen
 */

public class Brick extends JButton {
    private int x;
    private int y;

    public Brick(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getXPos() {
        return x;
    }

    public void setXPos(int x) {

        this.x = x;
    }

    public int getYPos() {
        return y;
    }

    public void setYPos(int y) {
        this.y = y;
    }

    public boolean isNextTo(Brick button) {

        if (Math.abs(button.x - x) + Math.abs(button.y - y) == 1)
            return true;

        else
            return false;
    }
}

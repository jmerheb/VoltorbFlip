package src;/*
 * This class provides the window the game appears in and handles
 * game start and stop. It provides the main() method.
 */
//import java.awt.GridLayout;
import javax.swing.JFrame;
import java.awt.image.BufferedImage;
import src.VoltorbBox;
import src.VoltorbFlipGame;

public class VoltorbFlip extends JFrame {
    public static VoltorbFlipGame screen;
    public static JFrame frame;

    public static void main(String[] args) {
        screen = new VoltorbFlipGame();
        BufferedImage frameWidth = screen.gameBoard;
        BufferedImage frameHeight = screen.gameBoard;

        frame = new JFrame("Voltorb Flip");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameWidth.getWidth() / 2, frameHeight.getHeight() / 2 + 28 /*hard # are for tab frame*/ );
        frame.setResizable(false);
        frame.add(screen);
        frame.setVisible(true);
    }
}

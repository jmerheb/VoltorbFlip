package src;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
//import javax.swing.SwingUtilities;
//import javax.swing.JFrame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.awt.Image;
import java.util.Random;
import java.awt.Font;
import java.text.NumberFormat;
import java.awt.Color;
import java.awt.Rectangle;
import src.VoltorbBox;

/* Temp
import java.awt.Graphics2D;
import java.awt.BasicStroke;

checklist:
- clean up
    - possibly make boxes myself
    - flip all when you win
    - fix click before gameover (?)
    - hardcoded #s (?)
*/

public class VoltorbFlipGame extends JPanel {
    VoltorbBox[][] boxes;
    int[][] note;
    int[][] values;
    int[] counters1, counters2, vcount1, vcount2;
    int level;
    int score, levelScore, tempScore;
    int twoThreeCount;
    boolean[][] notes;
    boolean notesOn;
    boolean flipClicked, noteClicked;
    boolean gameover;
    Image flipImg;
    Image voltorb;
    Image volNote;
    Image flipWin;
    public BufferedImage gameBoard;
    Rectangle rect1, rect2, rect3, rect4;

    public VoltorbFlipGame() {
        try {
            gameBoard = ImageIO.read(new File("src/PNGs/VoltorbBoard.png"));
            flipImg = ImageIO.read(new File ("src/PNGs/VoltorbFlipped.png"));
            voltorb = ImageIO.read(new File ("src/PNGs/Voltorb.png"));
            volNote = ImageIO.read(new File ("src/PNGs/Note.png"));
            flipWin = ImageIO.read(new File ("src/PNGs/VoltorbWindow.png"));
        } catch(IOException e){e.printStackTrace();}

        notesOn = false;
        level = 1;
        score = 0;

        setUp();
        repaint();
        addMouseListener(new FlipMouseAdapter());
    }

    public void setUp() {
        setRanValues();
        gameover = false;
        notes = new boolean[5][5];

        boxes = new VoltorbBox[5][5];
        int w = 68;
        int h = 69;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                notes[i][j] = false;

                w = 68;
                h = 69;
                double xspace = 11.5;
                double yspace = 10.8;

                if (i > 0) {
                    w = w + (int)(xspace);
                }
                if (j > 0) {
                    h = h + (int)(yspace);
                }

                boxes[i][j] = new VoltorbBox(w * i + 17, h * j + 58, values[i][j], notes[i][j]);
            }
        }

        counters1 = new int[5];
        counters2 = new int[5];
        //xaxis point counter
        for (int i = 0; i < 5; i++) {
            counters1[i] = 0;
            for (int j = 0; j < 5; j++) {
                counters1[i] += values[i][j];
            }
        }
        //yaxis point counter
        for (int j = 0; j < 5; j++) {
            counters2[j] = 0;
            for (int i = 0; i < 5; i++) {
                counters2[j] += values[i][j];
            }
        }

        vcount1 = new int[5];
        vcount2 = new int[5];
        //voltorb xaxis counter
        for (int i = 0; i < 5; i++) {
            vcount1[i] = 0;
            for (int j = 0; j < 5; j++) {
                if (values[i][j] == 0) {
                    vcount1[i]++;
                }
            }
        }
        //voltorb yaxis counter
        for (int j = 0; j < 5; j++) {
            vcount2[j] = 0;
            for (int i = 0; i < 5; i++) {
                if (values[i][j] == 0) {
                    vcount2[j]++;
                }
            }
        }

        //rect1 and 2 set up coordinates and import Rectangle
        rect1 = new Rectangle(414, 455, 68, 33);
        rect2 = new Rectangle(414, 490, 68, 33);

        //set up yes and no rects
        rect3 = new Rectangle(140, 325, 60, 35);
        rect4 = new Rectangle(280, 325, 60, 35);
    }

    public void setRanValues() {
        values = new int[5][5];
        Random ran = new Random();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                values[i][j] = ran.nextInt(4);
            }
        }

        twoThreeCount = 0;
        levelScore = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                levelScore += values[i][j];
                if (values[i][j] == 2 || values[i][j] == 3) {
                    twoThreeCount++;
                }
            }
        }
    }

    public void paint(Graphics g) {
        Font font1 = new Font("Al Bayan", 1, 23);
        Font font2 = new Font("Al Bayan", 1, 15);
        Font font3 = new Font("Al Bayan", 1, 20);
        Font font4 = new Font("Al Bayan", 1, 35);

        if (gameover == false) {
            g.drawImage(gameBoard, 0, 0, gameBoard.getWidth()/2, gameBoard.getHeight()/2, null);

        /*TEMP
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(1.0f));
        Color customColor5 = new Color(255,255,0);
        g2d.setColor(customColor5);
        int[] mxa = new int[] {414, 482, 482 , 414, 414};
        int[] mya= new int[] {490, 490, 523, 523, 490};
        g2d.drawPolyline(mxa, mya, mxa.length);
        */

            g.setFont(font1);
            Color customColor = new Color(40, 40, 40);
            g.setColor(customColor);
            tempScore = 0;

            int twoThreeCheck = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (boxes[i][j].getNoteClicked()) {
                        notes[i][j] = true;
                    }

                    if (boxes[i][j].getFlipClicked()) {
                        int xplace = boxes[i][j].getX();
                        int yplace = boxes[i][j].getY();
                        g.drawImage(flipImg, xplace, yplace, 68, 69, null);
                        if (values[i][j] == 0) {
                            g.drawImage(voltorb, xplace + 13, yplace + 13, 43, 43, null);
                            gameover = true;
                        }
                        else if (values[i][j] == 1) {
                            g.drawString(Integer.toString(values[i][j]) , xplace + 27, yplace + 42);
                            tempScore += values[i][j];
                        }
                        else {
                            g.drawString(Integer.toString(values[i][j]) , xplace + 27, yplace + 42);
                            tempScore += values[i][j];

                            twoThreeCheck++;
                            if (twoThreeCount == twoThreeCheck) {
                                win();
                            }
                        }
                    }
                }
            }

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (notes[i][j] == true && boxes[i][j].getFlipClicked() == false && boxes[i][j].getNoteClicked()) {
                        int xplace = boxes[i][j].getX();
                        int yplace = boxes[i][j].getY();
                        g.drawImage(volNote, xplace + 50, yplace + 6, 16, 16, null);
                    }
                }
            }

            //axis point printers
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMinimumIntegerDigits(2);
            int xAx = 79;
            final int XAY = 478;
            final int YAX = 434;
            int yAy = 79;
            for (int x = 0; x < 5; x++) {
                g.drawString(nf.format(counters1[x]) , xAx*x + 38, XAY);
                g.drawString(nf.format(counters2[x]) , YAX, yAy*x + 81);
            }

            //voltorb axis counters
            int vxAx = 79;
            final int vXAY = 514;
            final int vYAX = 459;
            int vyAy = 79;
            for (int x = 0; x < 5; x++) {
                g.drawString(Integer.toString(vcount1[x]) , vxAx*x + 63, vXAY);
                g.drawString(Integer.toString(vcount2[x]) , vYAX, vyAy*x + 119);
            }

            //level and score printers
            g.setFont(font2);
            final int xLEVEL = 100;
            final int yLEVEL = 30;
            final int xSCORE = 265;
            final int ySCORE = 30;
            g.drawString("Level: " + Integer.toString(level), xLEVEL, yLEVEL);
            g.drawString("Score: " + Integer.toString(score + tempScore), xSCORE, ySCORE);

            //Flip and Note buttons
            g.setFont(font3);
            final int xFLIP = 420;
            final int yFLIP = 480;
            final int xNOTE = 420;
            final int yNOTE = 515;
            g.drawString("Flip", xFLIP, yFLIP);
            g.drawString("Note", xNOTE, yNOTE);
        }
        else {
            g.setFont(font4);
            g.drawImage(flipWin, 0, 0, 500, 528, null);
            g.drawString("GAMEOVER", 130, 185);
            g.setFont(font3);
            g.drawString("Level: " + level + "    Score: " + (score + tempScore), 135, 250);
            g.drawString("Would you like to play again?", 80, 300);
            g.drawString("Yes               No", 150, 350);
        }
    }

    public void win() {
        level++;
        score += levelScore;
        setUp();
        repaint();
    }

    class FlipMouseAdapter extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            int clickx = e.getX();
            int clicky = e.getY();
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (boxes[i][j].isClicked(clickx, clicky) && notesOn) {
                        if (boxes[i][j].getNoteClicked()) {
                            boxes[i][j].setNoteClicked(false);
                        }
                        else {
                            boxes[i][j].setNoteClicked(true);
                        }
                    }
                    else if (boxes[i][j].isClicked(clickx, clicky) && notesOn == false) {
                        boxes[i][j].setFlipClicked(true);
                    }
                    if (boxes[i][j].getFlipClicked() && boxes[i][j].getNoteClicked()) {
                        boxes[i][j].setFlipClicked(true);
                        boxes[i][j].setNoteClicked(false);
                    }
                    repaint();
                }
            }

            if (rect1.contains(clickx, clicky)) {
                notesOn = false;
            }
            else if (rect2.contains(clickx, clicky)) {
                notesOn = true;
            }
            if (rect3.contains(clickx, clicky) && gameover) {
                gameover = false;
                level = 1;
                score = 0;
                setUp();
                repaint();
            }
            else if (rect4.contains(clickx, clicky) && gameover) {
                System.exit(0);
            }
        }
    }
}

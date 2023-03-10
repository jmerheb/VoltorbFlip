package src;

import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

//
public class VoltorbBox{
    int x;
    int y;
    int value;
    Image flipImg;
    boolean flipClicked, noteClicked;
    boolean cornerNote;

    //class created to make Voltorb box objects
    public VoltorbBox(int intx, int inty, int value, boolean cornerNote) {
        x = intx;
        y = inty;
        this.value = value;
        this.cornerNote = cornerNote;

        try {
            flipImg = ImageIO.read(new File ("src/PNGs/VoltorbFlipped.png"));
        } catch(IOException e){e.printStackTrace();}

        noteClicked = false;
        flipClicked = false;
    }

    //method to check if something is clicked in the correct hotbox
    public boolean isClicked(int clickx, int clicky) {
        return clickx >= x
                && clicky >= y
                && clickx < x + flipImg.getWidth(null)/2
                && clicky < y + flipImg.getHeight(null)/2;
    }

    //set and get methods for class variables
    public void setFlipClicked(boolean flipClicked) {
        this.flipClicked = flipClicked;
    }

    public void setNoteClicked(boolean noteClicked) {
        this.noteClicked = noteClicked;
    }

    public boolean getNoteClicked() {
        return noteClicked;
    }

    public boolean getFlipClicked() {
        return flipClicked;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

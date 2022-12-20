package src;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class VoltorbBox{
    int x;
    int y;
    int value;
    Image flipImg;
    boolean flipClicked, noteClicked;
    boolean cornerNote;

    public VoltorbBox(int intx, int inty, int value, boolean cornerNote) {
        x = intx;
        y = inty;
        this.value = value;
        this.cornerNote = cornerNote;

        try {
            flipImg = ImageIO.read(new File ("VoltorbFlipped.png"));
        } catch(IOException e){e.printStackTrace();}

        noteClicked = false;
        flipClicked = false;
    }

    public boolean isClicked(int clickx, int clicky) {
        return clickx >= x
                && clicky >= y
                && clickx < x + flipImg.getWidth(null)/2
                && clicky < y + flipImg.getHeight(null)/2;
    }
}
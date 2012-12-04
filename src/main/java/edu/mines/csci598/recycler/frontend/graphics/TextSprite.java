package edu.mines.csci598.recycler.frontend.graphics;

import java.awt.*;

import org.apache.log4j.Logger;

/**
 * Super simple text sprite class basically holds everything you would want to know to draw a string
 *  * User: jzeimen
 * Date: 11/23/12
 * Time: 10:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class TextSprite {
    private static final Logger logger = Logger.getLogger(TextSprite.class);

    private Font font;
    private int x, y;
    private String string;
    private Color color;

    public TextSprite(String s,Font f, Color c, int x, int y){
        string = s;
        font = f;
        color = c;

        this.x = x;
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int setX(){
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public String getMessage(){
        return string;
    }
    public void setMessage(String string){
        this.string = string;
    }

}

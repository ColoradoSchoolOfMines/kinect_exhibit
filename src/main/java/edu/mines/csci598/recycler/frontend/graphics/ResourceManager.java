package edu.mines.csci598.recycler.frontend.graphics;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * Simple use of a Hash Map that remembers the images so we won't have to keep reading them from disk and scaling
 *
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 11/14/12
 * Time: 6:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResourceManager {
    private static final Logger logger = Logger.getLogger(ResourceManager.class);

    private static ResourceManager instance;
    private HashMap<String,Image> imageMap;

    private ResourceManager() {
        imageMap = new HashMap<String, Image>();
    }

    public static ResourceManager getInstance() {
        if(instance == null){
            instance = new ResourceManager();
        }
        return instance;
    }


    /**
     * Put in the file name as the key and it will either go find you the image, or give you its cached one.
     * @param key
     * @return
     */
    public Image getImage(String key) {
        //If we don't have the image go create it and put it here.
        if(!imageMap.containsKey(key)) {
            BufferedImage img;
            try {
                img = ImageIO.read(new File(key));
            } catch (IOException e) {
                throw new RuntimeException("File error: " + key);
            }
            //Calculate the rounded scaled height
            int newHeight = (int) Math.round(img.getHeight() * GraphicsConstants.SCALE_FACTOR);
            int newWidth = (int) Math.round(img.getWidth() * GraphicsConstants.SCALE_FACTOR);
            //scale the image
            Image image = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
            imageMap.put(key, image);
        }
        return imageMap.get(key);
    }

}

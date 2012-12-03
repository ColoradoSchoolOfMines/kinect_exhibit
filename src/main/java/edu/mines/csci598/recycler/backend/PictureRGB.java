package edu.mines.csci598.recycler.backend;

import org.OpenNI.*;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.nio.ByteBuffer;

import java.nio.ShortBuffer;
import java.awt.*;
import java.awt.image.*;

public class PictureRGB {
    private static final long serialVersionUID = 1L;
    private OutArg<ScriptNode> scriptNode;
    private Context context;
    private ImageGenerator imageGen;
    private BufferedImage bimg;
    int width, height;

    private final String SAMPLE_XML_FILE = "openni_config.xml";

    public BufferedImage getPicture() {
        try {
            context.waitOneUpdateAll(imageGen);
            updateImage();
            return bimg;
        }
        catch(StatusException ex) {
            System.exit(1);
            return null;
        }
    }

    public PictureRGB() {
        configOpenNI();
    }

    private BufferedImage bufToImage(ByteBuffer pixelsRGB)
           {
               int[] pixelInts = new int[width * height];
               int rowStart = 0;
               int bbIdx;
               int i = 0;
               int rowLen = width * 3;
               for (int row = 0; row < height; row++) {
                   bbIdx = rowStart;
                   for (int col = 0; col < width; col++) {
                       int pixR = pixelsRGB.get(bbIdx++);
                       int pixG = pixelsRGB.get(bbIdx++);
                       int pixB = pixelsRGB.get(bbIdx++);
                       pixelInts[i++] =
                           0xFF000000 | ((pixR & 0xFF) << 16) |
                           ((pixG & 0xFF) << 8) | (pixB & 0xFF);
                   }
                   rowStart += rowLen;
               }
               BufferedImage im = new BufferedImage(width, height,
                       BufferedImage.TYPE_INT_ARGB);
               im.setRGB(0, 0, width, height, pixelInts, 0, width);
               return im;
           }

    private void configOpenNI()
        // create context and image generator
    {
        try {
            scriptNode = new OutArg<ScriptNode>();
            context = Context.createFromXmlFile(SAMPLE_XML_FILE, scriptNode);
 //           context = new Context();
            imageGen = ImageGenerator.create(context);

            context.startGeneratingAll();
            System.out.println("Started context generating...");

            ImageMetaData imageMD = imageGen.getMetaData();
            width = imageMD.getFullXRes();
            height = imageMD.getFullYRes();
        }
        catch (Exception e) {
            System.out.println(e);
            System.exit(1);
        }
    }  // end of configOpenNI()

    private void updateImage()
        // get image data as bytes; convert to an image
    {
        try {
            ByteBuffer imageBB = imageGen.getImageMap().createByteBuffer();
            bimg = bufToImage(imageBB);
        }
        catch (GeneralException e) {
            System.out.println(e);
        }
    }  // end of updateImage()

}

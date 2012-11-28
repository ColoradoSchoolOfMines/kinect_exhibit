package edu.mines.csci598.recycler.splashscreen.highscores;


import javax.swing.*;

public class SavePlayer {
/*    private byte[] imgbytes;
    int width, height;
    private BufferedImage bimg;*/

    public void submitPlayerScore(long score) {

        ImageIcon image = takePhoto();
        String initials = getInitials();

        //create object with score
        PlayerHighScoreInformation playerHighScoreInformation = new PlayerHighScoreInformation(initials, score, image);

        SerializePlayerInformation.savePlayerHighScoreInformation(playerHighScoreInformation);

        startSplashProcess();
    }

    private ImageIcon takePhoto() {  
        /*try {
            scriptNode = new OutArg<ScriptNode>();        

            context = Context.createFromXmlFile(SAMPLE_XML_FILE, scriptNode);

            depthGen = DepthGenerator.create(context);
            DepthMetaData depthMD = depthGen.getMetaData();

            width = depthMD.getFullXRes();
            height = depthMD.getFullYRes();

            DataBufferByte dataBuffer = new DataBufferByte(imgbytes, width*height);
            Raster raster = Raster.createPackedRaster(dataBuffer, width, height, 8, null);
            bimg.setData(raster);
        } catch (GeneralException e) {
            e.printStackTrace();
            System.exit(1);
        }*/
        
        return new ImageIcon();
    }

    private String getInitials() {
        return "";
    }

    private void startSplashProcess() {

    }
}

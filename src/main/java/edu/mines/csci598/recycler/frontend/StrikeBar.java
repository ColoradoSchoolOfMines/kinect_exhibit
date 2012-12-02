package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.frontend.graphics.Coordinate;
import edu.mines.csci598.recycler.frontend.graphics.Line;
import edu.mines.csci598.recycler.frontend.graphics.Path;

/**
 * This class keeps track of how many strikes the user has had and keeps a drawing on the screen.
 * <p/>
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 10/20/12
 * Time: 9:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class StrikeBar {

    private final int gameOverValue = 4;
    private GameOver gameOver;

    private Coordinate positionOne;
    private Coordinate positionTwo;
    private Coordinate positionThree;
    private Coordinate positionFour;
    private Coordinate positionFive;

    private final double transitionSpeed = 1;

    private int realLength;
    private Side side;
    private static Recyclable[] recyclables;
    private GameStatusDisplay gameStatusDisplay;

    /**
     * The recyclable is the offending strike so it can be displayed in the bar
     *
     * @paramrecyclable
     */


    public StrikeBar(GameStatusDisplay gameStatusDisplay){
        this.gameStatusDisplay = gameStatusDisplay;
        side = gameStatusDisplay.getSide();
        gameOver = new GameOver(side);
        recyclables = new Recyclable[gameOverValue];
        realLength = -1;
        if(side == Side.LEFT){
            positionOne = new Coordinate(845, 360);
            positionTwo = new Coordinate(845,460);
            positionThree = new Coordinate(845,560);
            positionFour = new Coordinate(845,660);
            positionFive = new Coordinate(845, 760);
        }else{
            positionOne = new Coordinate(975, 360);
            positionTwo = new Coordinate(975, 460);
            positionThree = new Coordinate(975,560);
            positionFour = new Coordinate(975, 660);
            positionFive = new Coordinate(975, 760);
        }
    }


    public void addStrike(Recyclable recyclable) {

        if ((realLength <= gameOverValue)){
            realLength++;
        }

        if(realLength == 0){
            recyclables[realLength] = recyclable;
            Path p = recyclables[realLength].getPath();
            p.addLine(new Line(recyclables[realLength].getPosition(), positionFive, transitionSpeed));
            recyclables[realLength].setPath(p);
        }else if(realLength == 1){
            recyclables[realLength] = recyclable;
            Path p = recyclables[realLength].getPath();
            p.addLine(new Line(recyclables[realLength].getPosition(), positionFour, transitionSpeed));
            recyclables[realLength].setPath(p);
        }else if(realLength == 2){
            recyclables[realLength] = recyclable;
            Path p = recyclables[realLength].getPath();
            p.addLine(new Line(recyclables[realLength].getPosition(), positionThree, transitionSpeed));
            recyclables[realLength].setPath(p);
        }else if(realLength == 3){
            recyclables[realLength] = recyclable;
            Path p = recyclables[realLength].getPath();
            p.addLine(new Line(recyclables[realLength].getPosition(), positionTwo, transitionSpeed));
            recyclables[realLength].setPath(p);
        }else if(realLength == 4){
            Path p = recyclable.getPath();
            p.addLine(new Line(recyclable.getPosition(), positionOne, transitionSpeed));
            recyclable.setPath(p);
            gameOver.setGameOver(gameStatusDisplay);
            realLength++;
        }else if (realLength > gameOverValue){
            recyclable.setRemove(true);
        }else{
            //Nothing else to do the bar is full.
        }
    }

    public Recyclable getRecyclable(){
        return recyclables[realLength];
    }


    public void removeStrike() {
        System.out.println("Remove length: " + realLength);
        if((realLength >= 0) && (realLength <= gameOverValue)){

        recyclables[realLength].setRemove(true);
        recyclables[realLength] = null;
        realLength--;

        }

    }

}

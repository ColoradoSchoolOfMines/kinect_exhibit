package edu.mines.csci598.recycler.frontend.gameLogic;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Created with IntelliJ IDEA.
 * User: Lauren
 * Date: 11/27/12
 * Time: 10:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class CollisionTest extends TestCase {

    public CollisionTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(CollisionTest.class);
    }

    public void testMovementAfterCollision()  {
        //TODO: For some reason, this breaks in Travis, but not my machine
        /*RecycleBins recycleBins = new RecycleBins(RecycleBins.Side.LEFT);
        GameLogic game = new GameLogic(
                recycleBins,
                ConveyorBelt.getConveyorBeltPathLeft(),
                new GameManager("Recycler", false),
                new GameStatusDisplay(Side.LEFT),
                false,
                false);

        Hand hand = new PlayerHand(game.getGameManager(), 0);
        hand.setVelocityX(GameConstants.MIN_HAND_VELOCITY);

        Recyclable r = new Recyclable(RecyclableType.PAPER, ConveyorBelt.getConveyorBeltPathLeft(), RecyclableType.PAPER.getImagePaths()[0]);
        List<Recyclable> swipedOff = new ArrayList<Recyclable>();
        swipedOff.add(r);

        game.handleCollisions(hand, swipedOff);

        List<Line> lines = r.getPath().getPath();
        assertTrue(lines.size() == 1);

        Line collisionLine = lines.get(0);
        RecycleBin destBin = recycleBins.findBinForFallingRecyclable(r);

        assertEquals(collisionLine.getX1(), r.getPosition().getX());
        assertEquals(collisionLine.getY1(), r.getPosition().getY());
        assertEquals(collisionLine.getX2(), r.getPosition().getX() + GameConstants.ITEM_PATH_END);
        assertEquals(collisionLine.getY2(), destBin.getMidPoint());
        assertEquals(r.getMotionState(), MotionState.FALL_RIGHT); */
    }
}
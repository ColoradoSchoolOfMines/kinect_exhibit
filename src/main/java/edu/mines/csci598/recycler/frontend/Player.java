package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.backend.GameManager;

/**
 * A player class contains the players hands and in the future will keep track of
 * other player specific things.
 *
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 10/20/12
 * Time: 9:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class Player {
    Hand primary;
    Hand auxiliary;

    public Player(GameManager manager) {
        primary = new Hand(manager);
        auxiliary = new Hand(manager);
    }

}

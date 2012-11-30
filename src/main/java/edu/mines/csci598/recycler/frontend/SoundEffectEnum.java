package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.bettyCrocker.SoundEffect;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 11/30/12
 * Time: 1:18 PM
 * To change this template use File | Settings | File Templates.
 */
public enum SoundEffectEnum {

    CORRECT( "src/main/resources/Sounds/correct.wav"),
    EXPLODE("src/main/resources/Sounds/explode.wav"),
    GLASS_HITS_BIN("src/main/resources/Sounds/glass_bin.wav"),
    HIT_GLASS("src/main/resources/Sounds/glass_hit"),
    ITEM_EXIT_CHUTE("src/main/resources/Sounds/item_exit_chute.wav"),
    MISS("src/main/resources/Sounds/miss.wav"),
    NUCLEAR_BIN("src/main/resources/Sounds/nuclear_bin.wav"),
    OTHER_HIT("src/main/resources/Sounds/other_hit"),
    PLASTIC_OR_PAPER_HITS_BIN("src/main/resources/Sounds/plastic_paper_bin.wav"),
    PLASTIC_OR_PAPER_HIT("src/main/resources/Sounds/plastic_paper_hit.wav"),
    SLOW_DOWN("src/main/resources/Sounds/slow_down.wav"),
    SPEED_UP("src/main/resources/Sounds/speed_up.wav"),
    TRASH_BIN("src/main/resources/Sounds/trash_bin.wav"),
    WRONG("src/main/resources/Sounds/wrong.wav");


    private String soundPath;
    private SoundEffect soundEffect;
    private static final Logger logger = Logger.getLogger(SoundEffectEnum.class);

    private SoundEffectEnum(String soundPath){
        this.soundPath = this.soundPath;
        this.soundEffect = new SoundEffect(soundPath);
    }

    public String getSoundPath() {
        return soundPath;
    }

    public SoundEffect getSoundEffect(){
        return soundEffect;
    }

    public void playSound(){
        soundEffect.play();
    }


}

package pocket.system;

import java.io.*;

public class Ghost implements Serializable {

    boolean paused;
    
    int tileIndex, creatureIndex, itemIndex;

    String nickname;
    int hp, hunger, number, creatureCounterStep, creatureColorIndex;
    
}

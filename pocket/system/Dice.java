package pocket.system;

import java.util.Random;

public class Dice {
    Random random = new Random();

    int sides;

    public Dice(int sides){
        this.sides = sides;
    }

    public int roll(int times){
        int total = 0;

        for(int i = 0; i < times; i++){
            total += random.nextInt(sides) + 1;
        }

        return total;
    }
}

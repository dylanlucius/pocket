package pocket.system;


public class Counter {
    public int step, steps;
    public boolean trigger;


    public Counter(int steps){
        step = 0;
        this.steps = steps;
        trigger = false;
    }

    public void update(){
        if(step < steps){
            step++;
            trigger = false;
        }
        else {
            step = 0;
            trigger = true;
        }
    }

}

package io.techPublica.threadSync;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IntroThread {




    public static void main(String[] args) {

        Bathroom bath = new Bathroom();

        Thread c1 = createConsumerThread(RollColor.BLUE, bath);
        Thread c2 = createConsumerThread(RollColor.GREEN, bath);
        Thread c3 = createConsumerThread(RollColor.RED, bath);
        Thread p1 = createProducerThread(bath);

        c1.start();
        c2.start();
        c3.start();

        p1.start();




    }


    private static Thread createConsumerThread(RollColor color, Bathroom bath) {
        Random dice = new Random();
        Thread c = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(dice.nextInt(10_000));
                    bath.consumeRoll(color);
                } catch (InterruptedException e) {
                    return;
                }
            }
        });
        c.setName("CONSUMER " + color);
        return c;
    }

    private static Thread createProducerThread(Bathroom bath) {
        List<Roll> rolls = new ArrayList<Roll>();
        for(var color : RollColor.values()) {
            for(int i = 0; i < 10; i++) {
                rolls.add(new Roll(color));
            }
        }
        Random dice = new Random();
        Thread c = new Thread(() -> {
            while(!rolls.isEmpty()) {
                try {
                    Thread.sleep(dice.nextInt(10_000));
                    Roll roll = rolls.remove(dice.nextInt(rolls.size()));
                    bath.addRoll(roll);
                } catch (InterruptedException e) {
                    return;
                }
            }
        });
        return c;
    }


}
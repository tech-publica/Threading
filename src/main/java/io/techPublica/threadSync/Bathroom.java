package io.techPublica.threadSync;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Bathroom {
    public List<Roll> rolls = new ArrayList<>();

    public void addRoll(Roll roll) {
        synchronized (rolls) {
            rolls.add(roll);
            System.out.println(Thread.currentThread().getName() + " added a roll of color " + roll.getColor()
                     + "now about to call notifyAll");
            rolls.notifyAll();
        }
    }

    public void consumeRoll(RollColor color) {
        synchronized (rolls) {
            Optional<Roll> optionalRoll = null;
            while((optionalRoll = rolls.stream().filter(r -> r.getColor() == color ).findFirst()).isEmpty()) {
                try {
                    System.out.println(Thread.currentThread().getName() + " did not find any roll, going in wait ");
                    rolls.wait();
                    System.out.println(Thread.currentThread().getName() + " woke up, checking if there are any rolls.. ");
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println(Thread.currentThread().getName() + " got lock and found roll, about to remove");
            rolls.remove(optionalRoll.get());

        }
    }




}

package pack;

import enums.Gender;

import java.time.LocalDateTime;
import java.util.Random;

public class Fish extends Thread {

    private static final Random random = new Random();
    private static int sequence = 0;
    private final Integer id;

    private final Gender gender;

    private final LocalDateTime birthDateTime;

    private final LocalDateTime deathDateTime;
    private LocalDateTime reproducingDateTime;
    private Integer x;
    private Integer y;
    private boolean running;
    Aquarium aquarium;

    public Fish(Integer x, Integer y, Aquarium aquarium) {
        this.id = sequence++;
        this.x = x;
        this.y = y;
        this.gender = random.nextBoolean() ? Gender.MALE : Gender.FEMALE;
        this.birthDateTime = LocalDateTime.now();
        this.reproducingDateTime = LocalDateTime.now();
        this.deathDateTime = LocalDateTime.now().plusSeconds(random.nextLong(20, 500));
        this.aquarium = aquarium;
    }

    @Override
    public void run() {
        running = true;

        do {
            switch (random.nextInt(4)) {
                case 0:
                    if (this.y != 19) this.y++;
                    else continue;
                case 1:
                    if (this.y != 0) this.y--;
                    else continue;
                case 2:
                    if (this.x != 0) this.x--;
                    else continue;
                case 3:
                    if (this.x != 9) this.x++;
                    else continue;
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            checkFishes();
        } while (running && deathDateTime.isAfter(LocalDateTime.now()));
        aquarium.getFishes().remove(this);
        System.out.println("Fish died. " + this + "\nFish size: " + aquarium.getFishes().size());
    }

    public static synchronized int getSequence() {
        return sequence;
    }

    private void checkFishes() {
        for(int i =0;i<aquarium.getFishes().size();i++){
            Fish fish = aquarium.getFishes().get(i);
            if (this.x.equals(fish.x) && this.y.equals(fish.y) && this.gender != fish.gender && this.birthDateTime.plusSeconds(10).isBefore(LocalDateTime.now()) && fish.birthDateTime.plusSeconds(10).isBefore(LocalDateTime.now()) && this.getReproducingDateTime().plusSeconds(5).isBefore(LocalDateTime.now()) && fish.getReproducingDateTime().plusSeconds(5).isBefore(LocalDateTime.now())) {
                if (aquarium.getFishes().size() < aquarium.getMaxSize()) {
                    Fish newFish = new Fish(this.x, this.y, this.aquarium);
                    newFish.start();
                    this.aquarium.getFishes().add(newFish);
                    System.out.println("New fish was born. " + newFish);
                    this.reproducingDateTime = LocalDateTime.now();
                    fish.reproducingDateTime = LocalDateTime.now();

                } else {
                    stopAll();
                    return;
                }
                break;
            }
        }
    }

    private static synchronized void stopAll(){
        System.out.println("Aquarium ran out of space\nBye!");
        System.exit(0);
    }
    private synchronized LocalDateTime getReproducingDateTime() {
        return reproducingDateTime;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public LocalDateTime getBirthDateTime() {
        return birthDateTime;
    }

    public LocalDateTime getDeathDateTime() {
        return deathDateTime;
    }

    public Gender getGender() {
        return gender;
    }

    public boolean isLiving() {
        return this.deathDateTime.isAfter(LocalDateTime.now());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        Fish fish = (Fish) o;
        return this.id.equals(fish.id);
    }

    @Override
    public String toString() {
        return "Fish " +
                "id=" + id +
                ", gender=" + gender +
                ", x=" + x +
                ", y=" + y +
                ", birthDateTime=" + birthDateTime +
                ", deathDateTime=" + deathDateTime;
    }

}

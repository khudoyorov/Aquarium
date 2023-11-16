package pack;

import java.util.Random;
import java.util.Vector;

import static enums.Gender.MALE;

public class Aquarium {
    private final Random random = new Random();

    private final Vector<Fish> fishes;

    private final Integer maxSize;

    public Aquarium() {
        this.fishes = new Vector<>(50);
        this.maxSize = 500;
    }


//    public void start() {
//        putFish();
//        while (!fishes.isEmpty()) {
//            for (int i = 0; i < fishes.size(); i++) {
//                Fish fish1 = fishes.get(i);
//                if (!fish1.isLiving()) {
//                    killFish(fish1);
//                }
//                for (int j = i + 1; j < fishes.size(); j++) {
//                    Fish fish2 = fishes.get(j);
//                    if (!fish2.isLiving()) {
//                        killFish(fish2);
//                    }
//                    if (fish1.getGender() != fish2.getGender() && fish1.getBirthDateTime().plusSeconds(10).isBefore(LocalDateTime.now()) && fish2.getBirthDateTime().plusSeconds(10).isBefore(LocalDateTime.now()) && fish1.getX().equals(fish2.getX()) && fish1.getY().equals(fish2.getY())) {
//                        Fish newFish = new Fish(fish1.getX(), fish2.getY());
//                        newFish.start();
//                        fishes.add(newFish);
//                        System.out.println("New fish was born\n" + newFish);
//                        System.out.println("pack.Fish size = " + fishes.size());
//                        if (fishes.size() == maxSize) {
//                            System.out.println("pack.Aquarium ran out of space");
//                            while (fishes.size() != 0) {
//                                killFish(fishes.get(0));
//                            }
//                        }
//                    }
//                }
//            }
//
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        System.out.println("Bye!");
//        System.exit(0);
//
//    }
    public void putFish() {
        int size = random.nextInt(4, maxSize / 2);
        int females = 0, males = 0;
        for (int i = 0; i < size; i++) {
            Fish fish = new Fish(random.nextInt(10), random.nextInt(20), this);
            if (fish.getGender().equals(MALE)) males++;
            else females++;
            fish.start();
            fishes.add(fish);

        }
        System.out.println("Male fish size = " + males + "\nFemale fish size = " + females);
        System.out.println(fishes);
    }


    public synchronized Vector<Fish> getFishes() {
        return fishes;
    }

    public Integer getMaxSize() {
        return maxSize;
    }
}

package se.iths.felix.tamagotchi2d;

public class TamagotchiMethods {
    boolean alive = true;
    int food = 5;
    int happiness = 10;
    int money = 0;

    public TamagotchiMethods() {
        this.alive = alive;
        this.food = 5;
        this.happiness = 5;
        this.money = 0;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getFood() {
        return food;
    }

    public int getHappiness() {
        return happiness;
    }

    public int getMoney() {
        return money;
    }

    public void feed() {
        System.out.println("You feed your tamagotchi");
        this.food += 4;
    }

    public void play() {
        System.out.println("You play with your tamagotchi");
        this.happiness += 2;
    }

    public static int[] gamble(int money, int food, int happiness) {
        float mult = (float) (Math.random() * 5);
        float tMoney = money * mult;
        money = Math.round(tMoney);
        if (mult > 1) {
            happiness++;
            System.out.println("You have rolled a " + mult + "x money multiplier and gain happiness :)");
        } else if (mult < 1) {
            System.out.println("You have rolled a " + mult + "x money multiplier and become sad ):");
            happiness--;
        }
        food--;
        int[] updateValues = new int[3];
        updateValues[0] = money;
        updateValues[1] = food;
        updateValues[2] = happiness;

        return updateValues;
    }

    public static int[] work(int money, String job, int happiness, int jobMult, boolean hasWork) {
        if (hasWork) {
            System.out.println("You work as a " + job);
            money = (int) (money + (5 * jobMult));
            happiness = happiness - 2;
        }

        int[] updateValues = new int[3];
        updateValues[0] = money;
        updateValues[2] = happiness;

        return updateValues;
    }
//    public static int[] findJob(int job,String[] jobs, boolean hasWork) {
//        System.out.println("You go to find a job");
//        int jobChance = (int) (Math.random() * 100);
//        if (jobChance < 25) {
//            int findJobInt = (int) (Math.random() * 4);
//            job = jobs[findJobInt];
//            jobMult = jobsMult[(int) (Math.random() * 4)];
//        System.out.println("You become a " + job);
//        hasWork = true;
//    }
}
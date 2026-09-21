package se.iths.felix.tamagotchi2d;

import static org.lwjgl.nanovg.NanoVG.nvgText;

public class TamagotchiMethods {
    String name;
    String latestAction;
    boolean alive = true;
    int food;
    int happiness;
    int money;
    static String job;
    static double jobMult;
    static boolean hasWork;
    static String[] jobs = {"Javautvecklare", "Zooskötare", "Sotare", "Professional Street Pimp", "Booeing Factory Worker", "FF CEO"};
    static double[] jobsMult = {1.5, 0.8, 2.3, 5, 10, 100};


    public TamagotchiMethods() {
        this.name = "Bichard";
        this.alive = alive;
        this.food = 5;
        this.happiness = 5;
        this.money = 0;
        this.job = "none";
        this.jobMult = 1;
        this.hasWork = false;
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

    public String getJob(){
        return this.job;
    }

    public boolean getAlive(){
        return alive;
    }

    public void setAliveFalse(){
        this.alive = false;
    }

    public void feed() {
        System.out.println("You feed your tamagotchi");
        this.food += 4;
        this.happiness --;
    }

    public void play() {
        System.out.println("You play with your tamagotchi");
        this.happiness += 2;
    }


    public void findJob() {
        System.out.println("You go to find a job");
        int jobChance = (int) (Math.random() * 100);
        if (jobChance < 25) {
            int findJobInt = (int) (Math.random() * 4);
            job = jobs[findJobInt];
            jobMult = jobsMult[(int) (Math.random() * 4)];
        }
        hasWork = true;
    }

    public void gamble() {
        float mult = (float) (Math.random() * 5);
        float tMoney = money * mult;
        money = Math.round(tMoney);
        if (mult > 1) {
            happiness++;
        } else if (mult < 1) {
            happiness--;
        }
        food--;
    }

    public void work() {
        if (hasWork) {
            money = (int) (this.money + (5 * jobMult));
            happiness -= 2;
        }else {
            findJob();
        }
    }


    public void checkIfAlive(){
        if(food <= 0 || happiness <= 0 || money <= -500){
            this.alive = false;
        }
    }

    public void latestAction(String action){
        this.latestAction = action;
    }

    @Override
    public String toString() {
        return
                "name: " + name +
                ", food: " + food +
                ", happiness: " + happiness +
                ", money:" + money +
                ", Job: " + job +
                ", money mult: " + jobMult;
    }
}
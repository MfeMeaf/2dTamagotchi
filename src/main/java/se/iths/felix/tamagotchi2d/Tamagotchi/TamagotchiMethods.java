package se.iths.felix.tamagotchi2d.Tamagotchi;

import se.iths.felix.tamagotchi2d.Tamagotchi.jobStuff.Job;
import se.iths.felix.tamagotchi2d.Tamagotchi.jobStuff.JobType;

import static org.lwjgl.nanovg.NanoVG.nvgText;

public class TamagotchiMethods {
    public String name;
    public String latestAction;
    boolean alive;
    int food;
    int happiness;
    int money;
    Job job;
    double jobMult;
    boolean hasWork;
//    static String[] jobs = {"Javautvecklare", "Zooskötare", "Sotare", "Professional Street Pimp", "Booeing Factory Worker", "FF CEO"};
//    static double[] jobsMult = {1.5, 0.8, 2.3, 5, 10, 100};


    public TamagotchiMethods() {
        this.name = "Bichard";
        this.alive = true;
        this.food = 5;
        this.happiness = 5;
        this.money = 0;
        this.job = Job.UNEMPLOYMENT;
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
        return this.job.getName();
    }

    public boolean getAlive(){
        return alive;
    }

    public void setAliveFalse(){
        this.alive = false;
    }

    public void feed() {
        System.out.println("You feed your tamagotchi");
        if(this.money < 10){
            this.latestAction = "You don't have enough money to buy food ):";
        }
        else {
            this.food += 4;
            this.money -= 10;
            this.happiness --;
            this.latestAction = "you feed " + this.name;
        }
    }

    public void play() {
        System.out.println("You play with your tamagotchi");
        this.happiness += 2;
        this.food --;
        this.latestAction = "you play with " + this.name;

    }

    public void findJob() {
        int jobChance = (int) (Math.random() * 100);
        Job randomJob = Job.randomJob();
        if (jobChance < 25 && !randomJob.getName().equals("Unemployed")) {
            this.job = randomJob;
            this.latestAction = "You found a job!!!!, you now work as a " + job.getName();
        }
    }

    public void gamble() {
        float mult = (float) (Math.random() * 1.5);
        float tMoney = money * mult;
        money = Math.round(tMoney);
        if (mult > 1) {
            happiness++;
        } else if (mult < 1) {
            happiness--;
        }
        this.latestAction = "You multiply your money by " + mult + "x";
        food--;
    }

    public void work() {
        if (!job.getName().equals("Unemployed")) {
            money = (int) (this.money + (5 * job.getSalary()));
            happiness -= 2;
            this.latestAction = "You go to work as a " + this.job.getName();
        }else {
            this.latestAction = "You don't have a job and go to look for one";
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
package se.iths.felix.tamagotchi2d.Tamagotchi.jobStuff;

import java.util.Random;

public enum Job {
    UNEMPLOYMENT("Unemployed",0, JobType.UNEMPLOYED),

    KEBAB_ENGINEER("Kebab Engineer", 50, JobType.REAL),
    THE_CEO("The CEO", 75, JobType.REAL),

    DISCORD_MOD("Discord Moderator", -10, JobType.SCAM),

    LAB_SACRIFICE("Lab Sacrifice", 67, JobType.ILLEGAL),
    CHICKEN_NUGGET_SCIENTIST("Chicken Nugget Scientist", 6 , JobType.ILLEGAL);

    private final String name;
    private final int salary;
    private final JobType jobType;

    Job(String name, int salary, JobType jobType) {
        this.name = name;
        this.salary = salary;
        this.jobType = jobType;
    }

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    public JobType getJobtype() {
        return jobType;
    }

    public static Job randomJob(){
        int pick = new Random().nextInt(Job.values().length);
        return Job.values()[pick];
    }

    @Override
    public String toString() {
        return name + " salary: " + salary;
    }
}

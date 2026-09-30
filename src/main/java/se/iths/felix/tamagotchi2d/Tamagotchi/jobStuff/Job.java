package se.iths.felix.tamagotchi2d.Tamagotchi.jobStuff;

import java.util.Random;

public enum Job {
    UNEMPLOYMENT("Unemployed",0, JobType.UNEMPLOYED),
    KEBAB_ENGINEER("Kebab Engineer", 10, JobType.REAL);


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

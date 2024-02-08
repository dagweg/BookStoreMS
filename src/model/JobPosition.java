package model;

public enum JobPosition{
    JANITOR("Janitor"),
    CASHIER("Cashier"),
    SECURITY("Security");
    
    private final String jobTitle;
    JobPosition(String jobTitle){ this.jobTitle = jobTitle; }
    public String getJobTitle() { return this.jobTitle; }
}
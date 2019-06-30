package model;


public class JobReport {
    private String jobDescription;
    private String state;

    public JobReport(String jobDescription, String state) {
        this.jobDescription = jobDescription;
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public String getJobDescription() {
        return jobDescription;
    }

}
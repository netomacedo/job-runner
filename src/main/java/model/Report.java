package model;

public class Report {

    private String jobDescription;
    private String state;

    public Report (String jobDescription, String state){
        this.jobDescription = jobDescription;
        this.state = state;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }
}

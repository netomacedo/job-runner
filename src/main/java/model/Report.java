package model;

import java.util.ArrayList;
import java.util.List;

public class Report {

    private List<JobReport> jobReports = new ArrayList<>();

   public void addJobReport(JobReport jobReport) {
       this.jobReports.add(jobReport);
   }

    public List<JobReport> getJobReports() {
        return jobReports;
    }
}
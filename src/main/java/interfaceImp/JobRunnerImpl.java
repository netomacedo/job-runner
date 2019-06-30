package interfaceImp;

import interfaces.Job;
import interfaces.JobQueue;
import interfaces.JobRunner;
import model.JobReport;
import model.Report;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of JobRunner to process Jobs
 *
 * @author Francisco Neto
 * @since 06-2019
 */

public class JobRunnerImpl implements JobRunner {

    private static Logger LOG = LoggerFactory.getLogger(JobRunnerImpl.class);

    private String version = "536543A4-4077-4672-B501-3520A49549E6";

    public String version() {
        return this.version;
    }

    private List<Thread> listThread = new ArrayList<>();


    /**
     * run and process Job queue .
     *
     * @param jobQueue, jobCount
     * @return void.
     */
    @Override
    public void runner(JobQueue jobQueue, long jobCount) {
        try {
            //initializing Job
            Job job = jobQueue.pop();
            jobCount--;

            while (job != null && jobCount >= 0) {
                final Job currentJob = job;

                //pop jobs from the queue
                job = jobQueue.pop();
                jobCount--;

                // call execute method
                Thread thread = new Thread(() ->
                        currentJob.execute()
                );
                thread.start();
                listThread.add(thread);
            }
            // wait until all jobs finish
            listThread.forEach((Thread t) -> {
                try{
                    t.join();
                }catch (Exception e){
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Implementation to get details from jobs.
     *
     * @param jobQueue, jobCount
     * @return void.
     *
     * I'm not using these parameters, I did not see the necessity to use them to get
     * information about the threads
     */
    public Report reportingRunner(JobQueue jobQueue, long jobCount) {
        Report report = new Report();
        listThread.forEach((Thread t) -> {
            try{
                report.addJobReport(new JobReport(t.getName(), t.getState().toString()));
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        return report;
    }

}

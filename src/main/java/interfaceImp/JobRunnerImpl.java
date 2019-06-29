package interfaceImp;

import interfaces.Job;
import interfaces.JobQueue;
import interfaces.JobRunner;
import model.Report;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class JobRunnerImpl implements JobRunner {

    private static Logger LOG = LoggerFactory.getLogger(JobRunnerImpl.class);

    private String version = "536543A4-4077-4672-B501-3520A49549E6";

    private Report report;

    public String version() {
        return this.version;
    }

    @Override
    public void runner(JobQueue jobQueue, long jobCount) {
        try {
            //consuming messages from the queue
            Job job = jobQueue.pop();
            jobCount--;
            List<Thread> listThread = new ArrayList<>();

            while (job != null && jobCount >= 0) {
                final Job currentJob = job;
                // call execute method
                Thread thread = new Thread(() ->
                        currentJob.execute()
                );
                thread.start();
                listThread.add(thread);

                //pop jobs from the queue
                job = jobQueue.pop();
                jobCount--;
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

    public Report reportingRunner(JobQueue jobQueue, long jobCount) {
        return null;
    }

}

import interfaceImp.JobQueueImp;
import interfaceImp.JobRunnerImpl;
import interfaces.Job;
import model.Report;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static org.junit.Assert.assertEquals;

public class JobTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    JobRunnerImpl jobRunner;
    JobQueueImp jobQueue;
    BlockingQueue<Job> queue;
    int queueCapacity = 100;

    @Before
    public void init() {
        queue = new ArrayBlockingQueue<>(queueCapacity);
        jobRunner = new JobRunnerImpl();
        System.setOut(new PrintStream((outContent)));
    }

    @After
    public void restore() {
        System.setOut(originalOut);
    }

    @Test
    public void jobExecutedToBeTheSame() throws InterruptedException {
        Job job1 = newJob(1, 1, 1000, "Job1 executed");
        queue.put(job1);

        Job job2 = newJob(2, 2, 1000, "Job2 executed");
        queue.put(job2);
        jobQueue = new JobQueueImp(queue);

        try {
            jobRunner.runner(jobQueue, 1);// impl internal async
            assertEquals("Job1 executed\r\n", outContent.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void jobExecutedToBeTwoThreads() throws InterruptedException {
        Job job1 = newJob(1, 1, 1000, "Job1 executed");
        queue.put(job1);

        Job job2 = newJob(2, 2, 1000, "Job2 executed");
        queue.put(job2);
        jobQueue = new JobQueueImp(queue);

        try {
            jobRunner.runner(jobQueue, 2);// impl internal async
            assertEquals("Job1 executed\r\nJob2 executed\r\n", outContent.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getThreadStateTerminated() throws InterruptedException {
        Job job1 = newJob(1, 1, 1000, "Job1 executed");
        queue.put(job1);
        jobQueue = new JobQueueImp(queue);

        jobRunner.runner(jobQueue, 1);
        Report report = jobRunner.reportingRunner(jobQueue, 1);
        assertEquals(1, report.getJobReports().size());
        assertEquals("TERMINATED", report.getJobReports().get(0).getState());
    }


    public static Job newJob(long customerId, long uniqueId, int duration, String execute) {
        return new Job() {
            @Override
            public long customerId() {
                return customerId;
            }

            @Override
            public long uniqueId() {
                return uniqueId;
            }

            @Override
            public int duration() {
                return duration;
            }

            @Override
            public void execute() {
                System.out.println(execute);
            }
        };
    }

}

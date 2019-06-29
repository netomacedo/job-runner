package interfaceImp;

import interfaces.Job;
import interfaces.JobQueue;
import java.util.concurrent.BlockingQueue;

public class JobQueueImp implements JobQueue {

    private BlockingQueue<Job> queue;

    public JobQueueImp(BlockingQueue<Job> queue){
        this.queue = queue;
    }

    public Job pop() {
        Job jobRemoved = null;
        if(queue.size() == 0){
            return jobRemoved;
        }
        try {
            jobRemoved = queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return jobRemoved;
    }
}

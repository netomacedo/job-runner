package interfaceImp;

import interfaces.Job;
import interfaces.JobQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Implementation of JobQueue to pop elements from the queue
 *
 * @author Francisco Neto
 * @since 06-2019
 */

public class JobQueueImp implements JobQueue {

    private BlockingQueue<Job> queue;

    public JobQueueImp(BlockingQueue<Job> queue){
        this.queue = queue;
    }

    /**
     * pop jobs from the queue.
     *
     * @param
     * @return the Job founded.
     */
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

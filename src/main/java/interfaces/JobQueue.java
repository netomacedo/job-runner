package interfaces;

public interface JobQueue {
    // Remove a job from the queue. If the queue has been drained,
    // this call will block until a new job becomes available
    Job pop();
}

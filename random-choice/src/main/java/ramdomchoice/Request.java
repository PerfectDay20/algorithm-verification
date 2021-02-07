package ramdomchoice;

public class Request {

    private static final int PROCESS_TIME = 8;
    private final int createdTime;
    private int startedTime = -1;

    public Request(int createdTime) {
        this.createdTime = createdTime;
    }

    public void start(int startedTime) {
        this.startedTime = startedTime;
    }

    /**
     * Must be called after start()
     */
    public boolean isFinish(int currentTime) {
        if (startedTime == -1) {
            throw new IllegalStateException("Task is not started yet");
        }
        int processedTime = currentTime - startedTime;
        if (processedTime > PROCESS_TIME) {
            throw new IllegalStateException("Task is finished, but not checked");
        }
        return processedTime == PROCESS_TIME;
    }

    /**
     * Must be called after start()
     */
    public int computeQueuedTime() {
        if (startedTime == -1) {
            throw new IllegalStateException("Task is not started yet");
        }
        return startedTime - createdTime;
    }
}

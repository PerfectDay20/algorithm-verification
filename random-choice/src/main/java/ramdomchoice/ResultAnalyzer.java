package ramdomchoice;

import java.util.List;

public class ResultAnalyzer {

    private int totalRequest;
    private int totalRequestQueuedTime;

    public void addAll(List<Request> finishedRequests) {
        totalRequest += finishedRequests.size();
        finishedRequests.forEach(request -> totalRequestQueuedTime += request.computeQueuedTime());
    }

    public double averageQueuedTime() {
        return ((double) totalRequestQueuedTime) / totalRequest;
    }
}

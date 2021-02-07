package ramdomchoice;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Cluster {

    private final List<Queue<Request>> queues;
    private final int size;

    public Cluster(int size) {
        queues = new ArrayList<>();
        this.size = size;
        for (int i = 0; i < size; i++) {
            queues.add(new ArrayDeque<>());
        }
    }

    public void insertQueue(int queueNumber, Request request, int currentTime) {
        Queue<Request> queue = queues.get(queueNumber);
        if (queue.isEmpty()) {
            request.start(currentTime);
        }
        queue.add(request);
    }


    public CacheInfo createCacheInfo(int currentTime, int expireTime) {
        Map<Integer, Integer> queueIndexToSize = new LinkedHashMap<>();
        for (int i = 0; i < queues.size(); i++) {
            queueIndexToSize.put(i, queues.get(i).size());
        }
        return new CacheInfo(currentTime, queueIndexToSize, expireTime);
    }

    public int getSize() {
        return size;
    }

    public List<Request> finishRequest(int currentTime) {
        List<Request> finishedRequests = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Queue<Request> queue = queues.get(i);
            if (!queue.isEmpty()) {
                Request headRequest = queue.peek();
                if (headRequest.isFinish(currentTime)) {
                    finishedRequests.add(queue.remove());
                    if (!queue.isEmpty()) {
                        queue.peek().start(currentTime);
                    }
                }
            }
        }
        return finishedRequests;
    }

}

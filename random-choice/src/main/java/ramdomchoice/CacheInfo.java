package ramdomchoice;

import java.util.Collections;
import java.util.Map;

public class CacheInfo {
    private final Map<Integer, Integer> queueIndexToSize;
    private final int createTime;
    private final int expireTime;

    public CacheInfo(int createTime, Map<Integer, Integer> queueIndexToSize, int expireTime) {
        this.createTime = createTime;
        this.queueIndexToSize = Collections.unmodifiableMap(queueIndexToSize);
        this.expireTime = expireTime;
    }

    public boolean shouldExpire(int currentTime) {
        int elapsedTime = currentTime - createTime;
        if (elapsedTime > expireTime) {
            throw new IllegalStateException("CacheInfo is expired without checked");
        }
        return elapsedTime == expireTime;
    }

    public Map<Integer, Integer> getQueueIndexToSize() {
        return queueIndexToSize;
    }
}

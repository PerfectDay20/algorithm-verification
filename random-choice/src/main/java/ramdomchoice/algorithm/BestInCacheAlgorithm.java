package ramdomchoice.algorithm;

import ramdomchoice.CacheInfo;
import ramdomchoice.Cluster;

import java.util.Map;

/**
 * Choose queue with minimum size
 */
public class BestInCacheAlgorithm implements Algorithm{
    @Override
    public int chooseQueue(Cluster cluster, CacheInfo cacheInfo) {
        int index = -1;
        int size = Integer.MAX_VALUE;
        for (Map.Entry<Integer, Integer> entry : cacheInfo.getQueueIndexToSize().entrySet()) {
            if (entry.getValue() < size) {
                size = entry.getValue();
                index = entry.getKey();
            }
        }
        return index;
    }
}

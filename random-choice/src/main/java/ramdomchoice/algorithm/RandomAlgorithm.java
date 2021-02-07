package ramdomchoice.algorithm;

import ramdomchoice.CacheInfo;
import ramdomchoice.Cluster;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Choose a random queue from cluster
 */
public class RandomAlgorithm implements Algorithm {

    @Override
    public int chooseQueue(Cluster cluster, CacheInfo cacheInfo) {
        int size = cluster.getSize();
        return ThreadLocalRandom.current().nextInt(size);
    }
}

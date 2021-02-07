package ramdomchoice.algorithm;

import ramdomchoice.CacheInfo;
import ramdomchoice.Cluster;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Choose 2 random queue, then use the best one
 */
public class BestOf2RandomAlgorithm implements Algorithm {
    @Override
    public int chooseQueue(Cluster cluster, CacheInfo cacheInfo) {
        List<Integer> list = ThreadLocalRandom.current()
                .ints(0, cluster.getSize())
                .distinct()
                .limit(2)
                .boxed()
                .collect(Collectors.toList());

        Integer choice1 = list.get(0);
        Integer choice2 = list.get(1);

        Map<Integer, Integer> queueIndexToSize = cacheInfo.getQueueIndexToSize();
        if (queueIndexToSize.get(choice1) <= queueIndexToSize.get(choice2)) {
            return choice1;
        } else {
            return choice2;
        }
    }
}

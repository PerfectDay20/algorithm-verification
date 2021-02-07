package ramdomchoice.algorithm;

import ramdomchoice.CacheInfo;
import ramdomchoice.Cluster;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Choose 3 random queue, then use the best one
 */
public class BestOf3RandomAlgorithm implements Algorithm {
    @Override
    public int chooseQueue(Cluster cluster, CacheInfo cacheInfo) {
        List<Integer> list = ThreadLocalRandom.current()
                .ints(0, cluster.getSize())
                .distinct()
                .limit(3)
                .boxed()
                .collect(Collectors.toList());

        Integer choice1 = list.get(0);
        Integer choice2 = list.get(1);
        Integer choice3 = list.get(2);

        Map<Integer, Integer> queueIndexToSize = cacheInfo.getQueueIndexToSize();
        Integer size1 = queueIndexToSize.get(choice1);
        Integer size2 = queueIndexToSize.get(choice2);
        Integer size3 = queueIndexToSize.get(choice3);

        if (size1 <= size2 && size1 <= size3) {
            return choice1;
        } else if (size2 <= size1 && size2 <= size3) {
            return choice2;
        } else {
            return choice3;
        }
    }
}

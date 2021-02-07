package ramdomchoice;

import ramdomchoice.algorithm.*;

import java.util.List;


public class Main {

    public static void main(String[] args) {
        System.out.println("cacheExpireTime\trandom\tbestOf2Random\tbestOf3Random\tbestInCache");
        for (int cacheExpireTime = 1; cacheExpireTime <= 50; cacheExpireTime++) {
            ResultAnalyzer random = test(new RandomAlgorithm(), cacheExpireTime);
            ResultAnalyzer bestOf2Random = test(new BestOf2RandomAlgorithm(), cacheExpireTime);
            ResultAnalyzer bestOf3Random = test(new BestOf3RandomAlgorithm(), cacheExpireTime);
            ResultAnalyzer bestInCache = test(new BestInCacheAlgorithm(), cacheExpireTime);
            System.out.printf("%s\t%s\t%s\t%s\t%s%n",
                    cacheExpireTime,
                    random.averageQueuedTime(),
                    bestOf2Random.averageQueuedTime(),
                    bestOf3Random.averageQueuedTime(),
                    bestInCache.averageQueuedTime());
        }
    }

    private static ResultAnalyzer test(Algorithm algorithm, int cacheExpireTime) {
        Cluster cluster = new Cluster(10);
        ResultAnalyzer resultAnalyzer = new ResultAnalyzer();
        CacheInfo cacheInfo = cluster.createCacheInfo(0, cacheExpireTime);

        for (int time = 0; time < 100_000; time++) {
            List<Request> finishedRequests = cluster.finishRequest(time);
            resultAnalyzer.addAll(finishedRequests);
            if (cacheInfo.shouldExpire(time)) {
                cacheInfo = cluster.createCacheInfo(time, cacheExpireTime);
            }

            int queueIndex = algorithm.chooseQueue(cluster, cacheInfo);
            cluster.insertQueue(queueIndex, new Request(time), time);
        }

        return resultAnalyzer;
    }


}

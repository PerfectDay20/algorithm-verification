package ramdomchoice.algorithm;

import ramdomchoice.CacheInfo;
import ramdomchoice.Cluster;

public interface Algorithm {
    int chooseQueue(Cluster cluster, CacheInfo cacheInfo);
}

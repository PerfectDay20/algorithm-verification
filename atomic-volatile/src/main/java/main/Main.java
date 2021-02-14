package main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntSupplier;

public class Main {
    private static final int THREAD_NUM = 3;
    private static final int EACH_THREAD_COUNT = 50_000_000;

    public static void main(String[] args) {
        measureTime(Main::nonVolatileFunc, "non_volatile");
        measureTime(Main::volatileFunc, "volatile");
        measureTime(Main::atomicFunc, "atomic");
        measureTime(Main::nonVolatileNonContentionFunc, "non_volatile_non_contention");
        measureTime(Main::volatileNonContentionFunc, "volatile_non_contention");
        measureTime(Main::atomicNonContentionFunc, "atomic_non_contention");
    }

    private static void measureTime(IntSupplier intSupplier, String desc) {
        long startTime = System.currentTimeMillis();
        int value = intSupplier.getAsInt();
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.printf("%s time: %sms, value %s %n", desc, elapsedTime, value);
    }

    private static int nonVolatileCount = 0;

    private static int nonVolatileFunc() {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < THREAD_NUM; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < EACH_THREAD_COUNT; j++) {
                    nonVolatileCount++;
                }
            });
            threads.add(thread);
        }
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return nonVolatileCount;
    }


    private static volatile int volatileCount = 0;

    private static int volatileFunc() {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < THREAD_NUM; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < EACH_THREAD_COUNT; j++) {
                    volatileCount++;
                }
            });
            threads.add(thread);
        }
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return volatileCount;
    }

    private static AtomicInteger atomicCount = new AtomicInteger(0);

    private static int atomicFunc() {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < THREAD_NUM; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < EACH_THREAD_COUNT; j++) {
                    atomicCount.incrementAndGet();
                }
            });
            threads.add(thread);
        }
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return atomicCount.get();
    }

    private static int nonVolatileNonContentionCount = 0;

    private static int nonVolatileNonContentionFunc() {
        for (int i = 0; i < THREAD_NUM; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < EACH_THREAD_COUNT; j++) {
                    nonVolatileNonContentionCount++;
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return nonVolatileNonContentionCount;
    }

    private static int volatileNonContentionCount = 0;

    private static int volatileNonContentionFunc() {
        for (int i = 0; i < THREAD_NUM; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < EACH_THREAD_COUNT; j++) {
                    volatileNonContentionCount++;
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return volatileNonContentionCount;
    }

    private static AtomicInteger atomicNonContentionCount = new AtomicInteger(0);

    private static int atomicNonContentionFunc() {
        for (int i = 0; i < THREAD_NUM; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < EACH_THREAD_COUNT; j++) {
                    atomicNonContentionCount.incrementAndGet();
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return atomicNonContentionCount.get();
    }
}

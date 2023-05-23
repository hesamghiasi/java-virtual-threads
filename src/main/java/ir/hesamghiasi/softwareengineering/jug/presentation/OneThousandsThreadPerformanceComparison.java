package ir.hesamghiasi.softwareengineering.jug.presentation;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class OneThousandsThreadPerformanceComparison {
    public static void main(String[] args) {

        // runnable to execute
        final AtomicInteger atomicInteger = new AtomicInteger();
        Runnable runnable = () -> {
            try {
                Thread.sleep(Duration.ofSeconds(1));
            } catch(Exception e) {
                System.out.println(e);
            }
            atomicInteger.incrementAndGet();
//            System.out.println("Work Done - " + atomicInteger.incrementAndGet());
        };

        // OS thread pool
        Instant start_1 = Instant.now();
        try (var executor = Executors.newFixedThreadPool(100)) {
            for(int i = 0; i < 1_000; i++) {
                executor.submit(runnable);
            }
        }
        Instant finish_1 = Instant.now();
        long timeElapsed_1 = Duration.between(start_1, finish_1).toMillis();
        System.out.println("Total elapsed time for OS thread pool: " + timeElapsed_1);

        // virtual thread
        Instant start_2 = Instant.now();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for(int i = 0; i < 1_000; i++) {
                executor.submit(runnable);
            }
        }
        Instant finish_2 = Instant.now();
        long timeElapsed_2 = Duration.between(start_2, finish_2).toMillis();
        System.out.println("Total elapsed time for virtual thread: " + timeElapsed_2);
    }
}
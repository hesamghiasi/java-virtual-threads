package ir.hesamghiasi.softwareengineering.jug.presentation;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class VirtualThreadCreator {
    public static void main(String[] args) {
//        useThreadStartVirtualThread();
        useThreadBuilder();
//        useExecutorService();
//        platformThreadBuilder();

    }

    private static void useExecutorService() {
        System.out.println("******** Thread Creation Using Executor ********");
        Instant start_1 = Instant.now();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 10_000).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    return i;
                });
            });
        }
        Instant finish_1 = Instant.now();
        long timeElapsed_1 = Duration.between(start_1, finish_1).toMillis();
        System.out.println("Total elapsed time for virtual thread pool: " + timeElapsed_1);
    }


    private static void useThreadStartVirtualThread() {
        System.out.println("******** Thread Creation Using startVirtualThread ********");
        Runnable runnable = () -> {
            System.out.println(
                    "thread id is: " + String.valueOf(Thread.currentThread().threadId()) +
                            ", isVirtual: " + Thread.currentThread().isVirtual() +
                            ", thread name: " + Thread.currentThread().getName() +
                            ", thread group: " + Thread.currentThread().getThreadGroup().getName() +
                            ", isDaemon: " + Thread.currentThread().isDaemon() +
                            ", thread priority: " + Thread.currentThread().getPriority());
        };
        Thread thread = Thread.startVirtualThread(runnable);
        Thread.startVirtualThread(() -> {
            System.out.println("Inside Runnable");
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void useThreadBuilder() {
        System.out.println("******** Thread Creation Using Builder ********");
        Runnable runnable = () -> {
            System.out.println(
                    "thread id is: " + String.valueOf(Thread.currentThread().threadId()) +
                            ", isVirtual: " + Thread.currentThread().isVirtual() +
                            ", thread name: " + Thread.currentThread().getName() +
                            ", thread group: " + Thread.currentThread().getThreadGroup().getName() +
                            ", isDaemon: " + Thread.currentThread().isDaemon() +
                            ", thread priority: " + Thread.currentThread().getPriority());
        };
        Thread.Builder builder = Thread.ofVirtual().name("create-by-builder");
        Thread t1 = builder.start(runnable);
        Thread t2 = builder.start(runnable);
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }

    private static void platformThreadBuilder() {
        System.out.println("******** Platform Thread Creation Using Builder ********");
        Runnable runnable = () -> {
            System.out.println(
                            "thread id is: " + String.valueOf(Thread.currentThread().threadId()) +
                            ", isVirtual: " + Thread.currentThread().isVirtual() +
                            ", thread name: " + Thread.currentThread().getName() +
                            ", thread group: " + Thread.currentThread().getThreadGroup().getName() +
                            ", isDaemon: " + Thread.currentThread().isDaemon() +
                            ", thread priority: " + Thread.currentThread().getPriority());
        };
        ThreadGroup threadGroup = new ThreadGroup("platform-thread-group");
        Thread.Builder builder = Thread.ofPlatform().daemon(true).name("Platform-Thread");
        Thread t1 = builder.start(runnable);
        Thread t2 = builder.start(runnable);
    }

}
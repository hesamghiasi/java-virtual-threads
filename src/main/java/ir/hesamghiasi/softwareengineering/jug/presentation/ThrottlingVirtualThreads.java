package ir.hesamghiasi.softwareengineering.jug.presentation;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

public class ThrottlingVirtualThreads {

    private static final Semaphore SEMAPHORE = new Semaphore(3);
    public static void main(String[] args) {

        Runnable runnable = () -> {
            try {
                SEMAPHORE.acquire();
                System.out.println(
                        "thread id is: " + String.valueOf(Thread.currentThread().threadId()) +
                                ", isVirtual: " + Thread.currentThread().isVirtual() +
                                ", thread name: " + Thread.currentThread().getName() +
                                ", thread group: " + Thread.currentThread().getThreadGroup().getName() +
                                ", isDaemon: " + Thread.currentThread().isDaemon() +
                                ", thread priority: " + Thread.currentThread().getPriority());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                SEMAPHORE.release();
            }
        };

        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        for (int i = 0; i < 4; i++){
            executorService.submit(runnable);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
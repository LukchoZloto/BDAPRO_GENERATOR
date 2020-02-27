import com.google.common.util.concurrent.RateLimiter;

import java.util.ArrayList;

public class Generator {

    final static int NUMBER_OF_THREADS = 1;
    final static int REQUEST_RATE = 300000; // Number of requests per second
    final static ArrayList<Thread> THREAD_POOL = new ArrayList<Thread>();

    public static void main(String[] args){
        for(int i = 0 ; i < NUMBER_OF_THREADS ; i++){
            RateLimiter rateLimiter = RateLimiter.create(REQUEST_RATE);
            Thread currentThread = new GeneratorThread(rateLimiter);
            THREAD_POOL.add(currentThread);
            currentThread.start();

        }
    }

}
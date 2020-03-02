import com.google.common.util.concurrent.RateLimiter;

import java.util.ArrayList;

public class Generator {

    static private int NUMBER_OF_THREADS;
    static private int MAX_EVENT_RATE; // Number of requests per second
    private static String KAFKA_CLIENT_NAME;
    private static String KAFKA_IP;
    private static String KAFKA_PORT;
    private static int NUMBER_OF_EVENTS = 5000000;
    final static ArrayList<Thread> THREAD_POOL = new ArrayList<Thread>();

    public static void main(String[] args){


        if(args.length < 5 ){
            System.out.println("Please enter Client Name, IP, Port, #Threads, Max. Event Rate, (optional)#Events");
            return;
        }

        // Init config variables
        KAFKA_CLIENT_NAME = args[0];
        KAFKA_IP = args[1];
        KAFKA_PORT = args[2];
        NUMBER_OF_THREADS = Integer.parseInt(args[3]);
        MAX_EVENT_RATE = Integer.parseInt(args[4]);
        NUMBER_OF_EVENTS = args.length < 6 ? NUMBER_OF_EVENTS : Integer.parseInt(args[5]); // Use default value if no parameter is passed

        System.out.printf("\nYou are running a generator with the following configuration\n" +
                 "Kafka Client Name: %s\n" +
                "Kafka Broker IP: %s\n" +
                "Kafka Broker Port: %s\n" +
                "Number of Threads: %d\n" +
                "Max. Event Rate: %d\n" +
                "Number of Events: %d\n" +
                "####################\n"
                ,KAFKA_CLIENT_NAME, KAFKA_IP, KAFKA_PORT, NUMBER_OF_THREADS, MAX_EVENT_RATE, NUMBER_OF_EVENTS);

        // Start generator threads
        for(int i = 0 ; i < NUMBER_OF_THREADS ; i++){
            RateLimiter rateLimiter = RateLimiter.create(MAX_EVENT_RATE);
            Thread currentThread = new GeneratorThread(rateLimiter,KAFKA_CLIENT_NAME, KAFKA_IP, KAFKA_PORT, NUMBER_OF_EVENTS);
            THREAD_POOL.add(currentThread);
            currentThread.start();

        }
    }

}
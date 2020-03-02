import com.google.common.util.concurrent.RateLimiter;
import models.ClickedEvent;
import models.WatchedEvent;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

class GeneratorThread extends Thread {

    private static String KAFKA_CLIENT_NAME;
    private static String KAFKA_IP;
    private static String KAFKA_PORT;
    private static int NUMBER_OF_EVENTS;

    EventPropertiesGenerator EPGenerator = EventPropertiesGenerator.getInstance();
    private final static double  CLICKED_EVENT_PROBABILITY = 0.024;
    private static volatile int COUNTER = 0;
    private static long TIMESTAMP_START;
    private static long TIMESTAMP_END;
    private static RateLimiter rateLimiterRef;

    public GeneratorThread(RateLimiter rateLimiterRef, String kafkaClientName, String kafkaIp, String kafkaPort, int numberOfEvents){
        this.rateLimiterRef = rateLimiterRef;
        this.KAFKA_CLIENT_NAME = kafkaClientName;
        this.KAFKA_IP = kafkaIp;
        this.KAFKA_PORT = kafkaPort;
        this.NUMBER_OF_EVENTS = numberOfEvents;
    }

    public void run() {
        Producer<String, String> producer = createProducer();

        // We have roughly 55 000 watched events per second in Youtube based on statistics

        // Timestamp the beginning of sending events
        TIMESTAMP_START = System.currentTimeMillis();

        int j = NUMBER_OF_EVENTS;

        while (j > 0) {
            j--;
            // Measure multithreaded latency
            //COUNTER++;
            //if(COUNTER==100){
            //    TIMESTAMP_END = System.currentTimeMillis();
            //    System.out.println(TIMESTAMP_END-TIMESTAMP_START);
            //}

            //Generate new watched event
            long newTimestamp = EPGenerator.generateTimestamp();
            String newAdID = EPGenerator.generateAdId();
            String newCategory = EPGenerator.generateCategory();
            String newUserID = EPGenerator.generateUserId();
            double newWatchedPercentage = EPGenerator.generateWatchedPercentage();
            WatchedEvent currentWatchedEvent = new WatchedEvent(newTimestamp, newAdID, newCategory, newUserID, newWatchedPercentage);

            // Create Kafka watch event record and send it to Kafka
            ProducerRecord<String, String> watched_record = new ProducerRecord<String, String>(KafkaContants.TOPIC_NAME, currentWatchedEvent.getAdID(), currentWatchedEvent.toString());
            rateLimiterRef.acquire();
            producer.send(watched_record);

            // Check if we should generate a click event or not
            double randomNumber = Math.random();
            ClickedEvent currentClickEvent = null;

            // For every watched event, generate clicked event with 0.24% probability
            if (randomNumber < CLICKED_EVENT_PROBABILITY) {
                // Generate clicked event
                long clickedTimestamp = EPGenerator.generateTimestamp();
                currentClickEvent = new ClickedEvent(clickedTimestamp, newAdID, newCategory, newUserID);

                // Create Kafka click event record and send it to Kafka
                ProducerRecord<String, String> clicked_record = new ProducerRecord<String, String>(KafkaContants.TOPIC_NAME, currentClickEvent.getAdID(), currentClickEvent.toString());
                rateLimiterRef.acquire();
                producer.send(clicked_record);
            }
//            try {
//                sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }

        // Calculate Throughput
        TIMESTAMP_END = System.currentTimeMillis();
        long elapsedTime = (TIMESTAMP_END-TIMESTAMP_START)/1000;
        elapsedTime = elapsedTime == 0 ? 1 : elapsedTime; // If the experiment runs very fast we don't want to divide by zero on the next line
        long throughput = NUMBER_OF_EVENTS /elapsedTime;
        System.out.println("THROUGHPUT: "+ throughput);

        producer.close();
    }


    public static Producer<String, String> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_IP+":"+KAFKA_PORT);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, KAFKA_CLIENT_NAME);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new KafkaProducer(props);
    }
}

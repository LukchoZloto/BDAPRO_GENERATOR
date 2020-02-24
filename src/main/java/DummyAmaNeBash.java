import com.sun.jmx.snmp.ThreadContext;
import models.ClickedEvent;
import models.WatchedEvent;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

class DummyAmaNeBash {

    static EventPropertiesGenerator EPGenerator = EventPropertiesGenerator.getInstance();
    private final static double  CLICKED_EVENT_PROBABILITY = 0.024;


    public static void main(String[] args){
        Producer<String, String> producer = createProducer();

        // We have roughly 55 000 watched events per second in Youtube based on statistics
        int j = 3;
        while (j > 0) {
            j--;

            //Generate new watched event
            long newTimestamp = EPGenerator.generateTimestamp();
            String newVideoID = EPGenerator.generateVideoId();
            String newCategory = EPGenerator.generateCategory();
            String newUserID = EPGenerator.generateUserId();
            double newWatchedPercentage = EPGenerator.generateWatchedPercentage();
            WatchedEvent currentWatchedEvent = new WatchedEvent(newTimestamp, newVideoID, newCategory, newUserID, newWatchedPercentage);

            // Create Kafka watch event record and send it to Kafka
            ProducerRecord<String, String> watched_record = new ProducerRecord<String, String>(KafkaContants.TOPIC_NAME, currentWatchedEvent.getVideoID(), currentWatchedEvent.toString());
            producer.send(watched_record);

            System.out.println("POD SENDA NE vUV SENDA");

            // Check if we should generate a click event or not
            double randomNumber = Math.random();
            ClickedEvent currentClickEvent = null;

            // For every watched event, generate clicked event with 0.24% probability
            if (randomNumber < CLICKED_EVENT_PROBABILITY) {
                // Generate clicked event
                long clickedTimestamp = EPGenerator.generateTimestamp();
                currentClickEvent = new ClickedEvent(clickedTimestamp, newVideoID, newCategory, newUserID);

                // Create Kafka click event record and send it to Kafka
                ProducerRecord<String, String> clicked_record = new ProducerRecord<String, String>(KafkaContants.TOPIC_NAME, currentClickEvent.getVideoID(), currentClickEvent.toString());
                producer.send(clicked_record);
            }
        }

        producer.close();
    }


    public static Producer<String, String> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaContants.KAFKA_BROKERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, KafkaContants.CLIENT_ID);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new KafkaProducer(props);
    }
}

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class EventPropertiesGenerator {

    private static EventPropertiesGenerator instance;
    private static String[] categories = new String[10];
    private static ArrayList<String> videoIDs = new ArrayList<String>();
    private static final int VIDEOS_COUNT = 10000;
    private static final Random rnd = new Random();

    private EventPropertiesGenerator() {

        // Generates N videoIDs and stores them in videoIDs array
        for(int i = 0 ; i < VIDEOS_COUNT; i++){
            String newVideoID = generateId();
            videoIDs.add(newVideoID);
        }
    }

    public static EventPropertiesGenerator getInstance() {
        if (instance == null) {
            instance = new EventPropertiesGenerator();
        }
            return instance;
    }

    public long generateTimestamp(){
        return new Date().getTime();
    }

    public String generateId(){
        return UUID.randomUUID().toString();
    }

    public String generateVideoId(){
        int randomArrayIndex = rnd.nextInt(VIDEOS_COUNT);
        return videoIDs.get(randomArrayIndex);
    }

    public String generateCategory(){
        int randomArrayIndex = rnd.nextInt(categories.length);
        return categories[randomArrayIndex];
    }

    public String generateUserId(){
        return generateId();
    }

    public double generateWatchedPercentage(){
        // On average 30% watched time (derived by Avg. ad length = 15 sec and minimum watch time is usually 5 seconds)
        // Suitable standard deviation 9-10%, mean 30%
        return (rnd.nextGaussian()+3.33)/10;
    }

}

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class EventPropertiesGenerator {

    private static EventPropertiesGenerator instance;
    private static int NUMBER_OF_CATEGORIES = 2;
    private static String[] adCategories = new String[NUMBER_OF_CATEGORIES];
    private static ArrayList<String> videoIDs = new ArrayList<String>();
    private static final int VIDEOS_COUNT = 100;
    private static final Random rnd = new Random();

    private EventPropertiesGenerator() {

        //Initialize categories
        adCategories[0] = "FOOD";
        adCategories[1] = "ELECTRONICS";
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
        return Integer.toString(rnd.nextInt(VIDEOS_COUNT));
    }

    public String generateCategory(){
        int randomArrayIndex = rnd.nextInt(NUMBER_OF_CATEGORIES);
        return adCategories[randomArrayIndex];
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

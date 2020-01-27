import models.ClickedEvent;
import models.WatchedEvent;

import java.util.Date;
import java.util.Random;

public class Generator {

    static EventPropertiesGenerator EPGenerator = EventPropertiesGenerator.getInstance();
    private final static double  CLICKED_EVENT_PROBABILITY = 0.03;

    public static void main(String[] args){


        // 1. For every watched event, generate clicked event with 0.24%
        int j = 10000;
        while(j>0){
            j--;
            long newTimestamp = EPGenerator.generateTimestamp();
            String newVideoID =  EPGenerator.generateVideoId();
            String newCategory = EPGenerator.generateCategory();
            String newUserID = EPGenerator.generateUserId();
            double newWatchedPercentage = EPGenerator.generateWatchedPercentage();
            WatchedEvent currentWatchedEvent = new WatchedEvent(newTimestamp,newVideoID,newCategory, newUserID, newWatchedPercentage);

            // Check if we should generate a click event or not
            double randomNumber = Math.random();
            ClickedEvent currentClickEvent = null;
            if (randomNumber < CLICKED_EVENT_PROBABILITY){
                // Generate clicked event
                long clickedTimestamp = EPGenerator.generateTimestamp();
                currentClickEvent = new ClickedEvent(clickedTimestamp, newVideoID,newCategory,newUserID);
                System.out.println(currentClickEvent.toString());
            }
            System.out.println(currentWatchedEvent.toString());
        }

        // 2. On average 30% watched time (derived by Avg. ad length = 15 sec and minimum watch time is usually 5 seconds)
        // Suitable standard deviation 9-10%, mean 30%

        // 3. We have roughly 55 000 watched events per second

    }
}

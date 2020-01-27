package models;

public class WatchedEvent {

    private long timestamp;
    private String videoID;
    private String adCategory;
    private String userID;
    private double watchedPercentage;

    public WatchedEvent(long timestamp, String videoID, String adCategory, String userID, double watchedPercentage) {
        this.timestamp = timestamp;
        this.videoID = videoID;
        this.adCategory = adCategory;
        this.userID = userID;
        this.watchedPercentage = watchedPercentage;
    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    public String getAdCategory() {
        return adCategory;
    }

    public void setAdCategory(String adCategory) {
        this.adCategory = adCategory;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public double getWatchedPercentage() {
        return watchedPercentage;
    }

    public void setWatchedPercentage(float watchedPercentage) {
        this.watchedPercentage = watchedPercentage;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

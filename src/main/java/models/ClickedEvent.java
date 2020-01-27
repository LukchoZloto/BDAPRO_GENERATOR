package models;

public class ClickedEvent {
    private long timestamp;
    private String videoID;
    private String adCategory;
    private String userID;


    public ClickedEvent(long timestamp, String videoID, String adCategory, String userID) {
        this.timestamp = timestamp;
        this.videoID = videoID;
        this.adCategory = adCategory;
        this.userID = userID;

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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

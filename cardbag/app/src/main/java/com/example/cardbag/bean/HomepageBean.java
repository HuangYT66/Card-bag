package com.example.cardbag.bean;
public class HomepageBean {
    private String id;                  //默认的id
    private String homepageName;   //记录的内容
    private String homepageID; //记录的ID
    private String homepageNote; //卡的NOTE
    private String homepageType; //卡的type
    private String homepageImage;//保存的图片
    private String homepageDueTime; //到期时间Due time
    private String homepageTime; //保存记录的时间
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getHomepageName() {
        return homepageName;
    }
    public void setHomepageName(String homepageName) {
        this.homepageName = homepageName;
    }

    public String getHomepageCardID() {return homepageID;}
    public void setHomepageCardID(String homepageID) {
        this.homepageID = homepageID;
    }

    public String getHomepageNote() {
        return homepageNote;
    }
    public void setHomepageNote(String homepageNote) {
        this.homepageNote = homepageNote;
    }

    public String getHomepageCardTime() {
        return homepageDueTime;
    }
    public void setHomepageCardTime(String homepageDueTime) {
        this.homepageDueTime = homepageDueTime;
    }
    public String getHomepageCardType() {
        return homepageType;
    }
    public void setHomepageCardType(String homepageType) {
        this.homepageType = homepageType;
    }

    public String getHomepageCardImage() {
        return homepageImage;
    }
    public void setHomepageCardImage(String homepageImage) {
        this.homepageImage = homepageImage;
    }

    public String getHomepageTime() {
        return homepageTime;
    }
    public void setHomepageTime(String homepageTime) {
        this.homepageTime = homepageTime;
    }
}

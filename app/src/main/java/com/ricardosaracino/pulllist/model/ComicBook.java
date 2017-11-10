package com.ricardosaracino.pulllist.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ComicBook implements Parcelable {

    public static final Parcelable.Creator<ComicBook> CREATOR = new Parcelable.Creator<ComicBook>() {
        public ComicBook createFromParcel(Parcel in) {
            return new ComicBook(in);
        }

        public ComicBook[] newArray(int size) {
            return new ComicBook[size];
        }
    };
    private int id;
    private int digitalId;
    private String title = "";
    private int issueNumber;
    private String variantDescription = "";
    private String description = "";
    private String format = "";
    private String imagePath = "";

    public ComicBook() {
    }

    private ComicBook(Parcel in) {
        id = in.readInt();
        digitalId = in.readInt();
        title = in.readString();
        issueNumber = in.readInt();
        variantDescription = in.readString();
        description = in.readString();
        format = in.readString();
        imagePath = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDigitalId() {
        return digitalId;
    }

    public void setDigitalId(int digitalId) {
        this.digitalId = digitalId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(int issueNumber) {
        this.issueNumber = issueNumber;
    }

    public String getVariantDescription() {
        return variantDescription;
    }

    public void setVariantDescription(String variantDescription) {
        this.variantDescription = variantDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }


    // Parcelable

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeInt(digitalId);
        out.writeString(title);
        out.writeInt(issueNumber);
        out.writeString(variantDescription);
        out.writeString(description);
        out.writeString(format);
        out.writeString(imagePath);
    }


}

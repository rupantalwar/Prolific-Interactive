////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                        //
//  Result.java - Prolific                                                                //
//  (Source file containing Result class(POJO) used for containing  book's parameters     //
//   in a single listView item)                                                           //
//                                                                                        //
//  Language:        Java                                                                 //
//  Platform:        Android SDK                                                          //
//  Author:          Rupan Talwar, Email:rupantalwar@gmail.com, Phone: 315 751-2860       //
//  Created On:      1/7/2015                                                             //
////////////////////////////////////////////////////////////////////////////////////////////


package com.rupantalwar.prolific;

public class Result {

    public String author;
    public String categories;
    public String lastCheckedOut;
    public String lastCheckedOutBy;
    public String publisher;
    public String title;
    public String url;
    public int id;

    public Result(String title, String author, String publisher, String categories,String lastCheckedOut,String lastCheckedOutBy) {
        this.title=title;
        this.author=author;
        this.publisher=publisher;
        this.categories=categories;
        this.lastCheckedOut=lastCheckedOut;
        this.lastCheckedOutBy=lastCheckedOutBy;

    }



    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getLastCheckedOut() {
        return lastCheckedOut;
    }

    public void setLastCheckedOut(String lastCheckedOut) {
        this.lastCheckedOut = lastCheckedOut;
    }

    public String getLastCheckedOutBy() {
        return lastCheckedOutBy;
    }

    public void setLastCheckedOutBy(String lastCheckedOutBy) {
        this.lastCheckedOutBy = lastCheckedOutBy;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

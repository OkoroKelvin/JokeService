package com.transition.jokemysql.data.inputDto;

public class PageInfo {
    private int noOfRecords;
    private int noOfPages;
    private int count;

    public PageInfo() {
    }


    public PageInfo(int noOfRecords, int noOfPages, int count) {
        this.noOfRecords = noOfRecords;
        this.noOfPages = noOfPages;
        this.count = count;
    }

    public int getNoOfRecords() {
        return noOfRecords;
    }

    public void setNoOfRecords(int noOfRecords) {
        this.noOfRecords = noOfRecords;
    }

    public int getNoOfPages() {
        return noOfPages;
    }

    public void setNoOfPages(int noOfPages) {
        this.noOfPages = noOfPages;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

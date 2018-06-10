package com.qidai.bean;

//图片信息bean
/**
 * 8  是时间戳  规定哪些片段是来自同一个图片的
 * 1  是一共被切成了多少片段的总数量
 * 1  此片段是第几个
 * 4  是接下来的数据有多少
 * 剩下的全部就是数据的空间   49KB
 */
public class ImageBean {
    private long timeStamp;
    private int imageCount;
    private int imageNo;
    private int dataLen;
    private byte[] datas;

    public byte[] getDatas() {
        return datas;
    }

    public void setDatas(byte[] datas) {
        this.datas = datas;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getImageCount() {
        return imageCount;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }

    public int getImageNo() {
        return imageNo;
    }

    public void setImageNo(int imageNo) {
        this.imageNo = imageNo;
    }

    public int getDataLen() {
        return dataLen;
    }

    public void setDataLen(int dataLen) {
        this.dataLen = dataLen;
    }
}

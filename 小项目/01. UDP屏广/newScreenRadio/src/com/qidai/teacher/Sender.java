package com.qidai.teacher;

import com.qidai.bean.ImageBean;
import com.qidai.utils.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Sender extends Thread{

    public static void main(String[] args) {
        new Sender().start();
    }
    public static final int MAX_UDP_SEND_DATA = 63 * 1024 ;
    private static final int MAX_DATA_LEN = 49 * 1024 ;
    private DatagramSocket socket;
    private DatagramPacket pack ;
    private InetSocketAddress address = new InetSocketAddress("172.16.100.255",8888);

    {
        try {
            socket = new DatagramSocket(9999);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    public void run() {
        while (true){
            sendOncScreen();
        }
    }

    private void sendOncScreen() {
        //截图
        byte[] allImageData = screenShot();
        //把截图切片
        List<ImageBean> splitImages = splitImage(allImageData);
        splitImages.forEach((oneImage) -> {
            //将分片组报文
            byte[] oneImageMess = assembleMess(oneImage);
            //发送一个分片
            sendOneImageMess(oneImageMess);
        });
    }

    private void sendOneImageMess(byte[] oneImage) {
        try {
            pack = new DatagramPacket(oneImage,oneImage.length);
            pack.setSocketAddress(address);
            socket.send(pack);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] assembleMess(ImageBean oneImage) {
        byte[] bytes = new byte[MAX_UDP_SEND_DATA];
        byte[] timeStamp = Utils.long2Bytes(oneImage.getTimeStamp());
        System.arraycopy(timeStamp,0,bytes,0,8);
        bytes[8] = (byte)oneImage.getImageCount();
        bytes[9] = (byte)oneImage.getImageNo();

        byte[] dataLen = Utils.int2Bytes(oneImage.getDataLen());
        System.arraycopy(dataLen,0,bytes,10,4);
        System.arraycopy(oneImage.getDatas(),0,bytes,14,oneImage.getDataLen());
        return bytes;
    }

    private List splitImage(byte[] allImageData) {
        List<ImageBean> images = new ArrayList<>();
        int splitImageCount = 0 ;
        if (allImageData.length % MAX_DATA_LEN == 0){
            //代表可以正好分切
            splitImageCount = allImageData.length / MAX_DATA_LEN ;
        }else {
            //不可以正好分切
            splitImageCount = allImageData.length / MAX_DATA_LEN + 1;
        }
        ImageBean image = null;
        long millis = System.currentTimeMillis();
        for (int i = 0; i < splitImageCount; i++) {
            image = new ImageBean();
            image.setTimeStamp(millis);
            image.setImageNo(i);
            image.setImageCount(splitImageCount);
            if (i == splitImageCount -1 ){
                //代表是最后一个切片
                if (allImageData.length % MAX_DATA_LEN == 0){
                    //代表最后一个切片的长度可以正好切分
                    byte[] datas = new byte[MAX_DATA_LEN];
                    System.arraycopy(allImageData,i * MAX_DATA_LEN,datas,0,MAX_DATA_LEN );
                    image.setDataLen(MAX_DATA_LEN);
                    image.setDatas(datas);
                }else {
                    //代表最后一个切片的长度不可以正好切分
                    byte[] datas = new byte[allImageData.length % MAX_DATA_LEN];
                    System.arraycopy(allImageData,i * MAX_DATA_LEN,datas,0,allImageData.length % MAX_DATA_LEN);
                    image.setDataLen(allImageData.length % MAX_DATA_LEN);
                    image.setDatas(datas);
                }
            }else {
                //所有前面的切片
                byte[] datas = new byte[MAX_DATA_LEN];
                System.arraycopy(allImageData, i * MAX_DATA_LEN,datas,0,MAX_DATA_LEN);
                image.setDataLen(MAX_DATA_LEN);
                image.setDatas(datas);
            }
            images.add(image);
        }
        return images;
    }

    //截图
    private byte[] screenShot(){
        try {
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(0,0,1920,1080);
            BufferedImage screenCapture = robot.createScreenCapture(screenRect);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(screenCapture,"jpg",bos);
            return bos.toByteArray();
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

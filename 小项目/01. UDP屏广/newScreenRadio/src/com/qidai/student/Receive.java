package com.qidai.student;

import com.qidai.bean.ImageBean;
import com.qidai.teacher.Sender;
import com.qidai.utils.Utils;
import org.omg.CORBA.IMP_LIMIT;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;

public class Receive extends Thread {

    private DatagramSocket socket;
    private DatagramPacket pack;
    private StudentUI stduentUI;
    private byte[] datas = new byte[Sender.MAX_UDP_SEND_DATA];
    private Map<Integer,ImageBean> allImageData = new HashMap<>();
    private long currentTimeStamp = 0;
    public Receive(StudentUI stduentUI) {
        try {
            this.stduentUI = stduentUI;
            socket = new DatagramSocket(8888);
            pack = new DatagramPacket(datas,datas.length);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true){
                receiveOneSpiltImage();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void receiveOneSpiltImage() throws IOException {
        //接收
        socket.receive(pack);
        int length = pack.getLength();
        //组合
        ImageBean image = accembleImage(length);

        if (image.getTimeStamp() == currentTimeStamp){
            allImageData.put(image.getImageNo(),image);
        }else if (image.getTimeStamp() > currentTimeStamp){
            currentTimeStamp = image.getTimeStamp();
            allImageData.clear();
            allImageData.put(image.getImageNo(),image);
        }
        //判断是够集齐
        process();
    }

    private void process() {
        try {
            int count = allImageData.values().iterator().next().getImageCount();
            if (allImageData.size() == count){
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                for (int i = 0; i < count; i++) {
                    bos.write(allImageData.get(i).getDatas());
                }
                allImageData.clear();
                stduentUI.updateIcon(bos.toByteArray());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private ImageBean accembleImage(int length) {
        ImageBean image = new ImageBean();
        image.setTimeStamp(Utils.byte2Long(datas));
        image.setImageCount(datas[8]);
        image.setImageNo(datas[9]);
        image.setDataLen(Utils.byte2Int(datas,10));
        byte[] datas = new byte[image.getDataLen()];
        System.arraycopy(this.datas,14,datas,0,image.getDataLen());
        image.setDatas(datas);
        return image;
    }
}

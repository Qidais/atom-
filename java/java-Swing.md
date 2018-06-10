分辨率
int screenWidth= (Toolkit.getDefaultToolkit().getScreenSize().width);
   int screenHeight = (Toolkit.getDefaultToolkit().getScreenSize().height);



   private void init() {
        this.setLayout(null);//绝对布局
        this.setSize(500 ,250);//设置窗体大小
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//在点击关闭后直接退出程序
        //设置窗体的显示位置
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation( (int) (width - this.getWidth()) / 2,(int) (height - this.getHeight()) / 2);
        //添加地址框
        field = new JTextField("请输入广播地址");
        field.setSize(200,30);
        field.setLocation(this.getWidth()/2-100,this.getHeight()/2-100);
        field.setFont(new Font(null,Font.PLAIN,15));
        this.add(field);
        //添加按钮
        button = new JButton("开启广播");
        button.setFont(new Font(null,Font.BOLD,20));//字体大小
        button.setBackground(Color.GREEN);
        button.setLocation(this.getWidth()/2-100,this.getHeight()/2-50);
        button.setSize(200,100);
        this.add(button);
        this.setVisible(true);//让组件显示
        listen();
    }

    package com.qidai.inter;

import javax.swing.*;
import java.awt.Toolkit;

public interface Rule {
    //屏幕分辨率
    double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    //图标
    Icon startIcon  = new ImageIcon("start.jpg");
    Icon joinIcon = new ImageIcon("join.jpg");


}


    @Test//截图
       public void screenShot() throws AWTException, IOException {
           Robot robot = new Robot();
           Rectangle screenRect = new Rectangle(0,0,1920,1080);
           BufferedImage screenCapture = robot.createScreenCapture(screenRect);
           ImageIO.write(screenCapture,"jpg",new FileOutputStream(new File("/usr/data/test/x.jpg")));
       }

       @Test//分辨率
   public void t(){
       int screenWidth=((int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().width);
       int screenHeight = ((int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().height);
       System.out.println(screenHeight);
       Syste


       @Test//UDP最多发送多少
   public void tt() throws IOException {
       DatagramSocket socket = new DatagramSocket(9999);
       byte[] bytes = new byte[1024 * 63];
       DatagramPacket pack = new DatagramPacket(bytes,bytes.length);
       pack.setSocketAddress(new InetSocketAddress("localhost",8888));
       socket.send(pack);
   }
}
    //UDP最多发送63字节数据  那么发送报文规则如下：
    /**
     * 8  是时间戳  规定哪些片段是来自同一个图片的
     * 1  是一共被切成了多少片段的总数量
     * 1  此片段是第几个
     * 4  是接下来的数据有多少
     * 剩下的全部就是数据的空间   49KB
     */

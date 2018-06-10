package com.qidai.student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class StudentUI {
    private JFrame frame;
    private JDesktopPane desktopPane;
    private JLabel backLabel;
    private ImageIcon  icon;
    private boolean flag = true;
    public StudentUI()
    {
        frame = new JFrame("主面板");
        desktopPane = new JDesktopPane();		//虚拟桌面
        backLabel = new JLabel();				//背景图标
        icon = new ImageIcon("/home/qidai/图片/123.jpg");
        frame.setBounds(0,0,800,600);//初始大小

        frame.addComponentListener(new ComponentAdapter(){		//为主面板添加窗口监听器
            @Override
            public void componentResized(ComponentEvent e)
            {
                //利用java代码进行实现
                icon.setImage(icon.getImage().getScaledInstance(frame.getWidth(),frame.getHeight(),Image.SCALE_DEFAULT));
                backLabel.setIcon(icon);
                backLabel.setSize(frame.getWidth(),frame.getHeight());
            }
        });

        desktopPane.add(backLabel,new Integer(Integer.MIN_VALUE));
        frame.add(desktopPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

 public void updateIcon(byte[] bytes) {
        icon = new ImageIcon(bytes);
        if (flag){
            frame.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
            flag = false;
        }
        backLabel.setIcon(icon);
		frame.invalidate();
		frame.repaint();
		frame.setVisible(true);
    }
}
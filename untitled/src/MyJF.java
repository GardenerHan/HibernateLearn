import javax.swing.*;
import java.awt.*;

public class MyJF {
    public static JLabel lb = new JLabel("此处显示鼠标右键点击后的坐标");
    public static JButton jb = new JButton("跳跃");
    static JPanel jPanel = new JPanel();
    public static JFrame created(){
        JFrame f=new JFrame("跳一跳外挂");
        f.setLayout(null);
        f.setUndecorated(true);
        f.setBackground(new Color(0,0,0, (float) 0.5));
        f.setSize(600,800);
        f.setLocation(300,50);

        lb.setForeground(Color.CYAN);
        Font fnt =  new Font("Serief",Font.BOLD,28);
        jb.setFont(fnt);
        jb.setBounds(100, 0, 100, 50);
        lb.setBounds(0,0,100,50);
        f.add(lb);
        f.add(jb);
        f.setVisible(true);
        return f;
    }
}

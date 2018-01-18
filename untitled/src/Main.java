import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Main {
    static float x1,y1,x2,y2;
    static boolean flag = false;
    public static void main(String[] args) {
        JFrame jFrame = MyJF.created();
        jFrame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton()==e.BUTTON3){       //  判断获取的按钮是否为鼠标的右击
                    if(!Main.flag) {
                        System.out.println("记录第一个点");
                        x1 = e.getX();
                        y1 = e.getY();
                        MyJF.lb.setText("x:"+x1+","+"y:"+y1);         // 获得鼠标点击位置的坐标并发送到标签的文字上
                        Main.flag = true;
                    }else {
                        System.out.println("记录第二个点");
                        x2 = e.getX();
                        y2 = e.getY();
                        MyJF.lb.setText("x:"+x2+","+"y:"+y2);
                        Main.flag = false;
                        if(!Main.flag){
                            float a = (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1);
                            a = (float) Math.sqrt(a);
                            System.out.println(a);
                            int time = (int) (a * 3);
                            System.out.println(time);
                            String commd = "adb shell input swipe 30 30 30 30 "+time;
                            ADB.exeCmd(commd);
                        }else {
                            System.out.println("确认第二次");
                        }
                    }

                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        MyJF.jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
    }
}

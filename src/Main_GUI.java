import javax.swing.*;
import java.awt.*;

public class Main_GUI {
    public static void main(String[] args)
    {
        //对于鼠标绘图程序的main。
        JFrame f=new JFrame();
        f.setLayout(null);
        f.setResizable(true);
        f.setSize(1280,1024);

        JPanel p=new JPanel();
        p.setSize(800,600);
        p.setPreferredSize(new Dimension(800,600));
        p.setBackground(Color.WHITE);
        f.add(p,BorderLayout.CENTER);

        f.setVisible(true);
        Graphics g=p.getGraphics();

        //设置监听
        //Main -> MyMouseListener ->MyGraphics
        MyGraphics mg=new MyGraphics();
        mg.init(g,p,f);
        MyMouseListener mml=new MyMouseListener();
        mml.init(mg);
        p.addMouseListener(mml);
    }
}

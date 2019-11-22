import javax.swing.*;
import java.awt.*;

public class Main_GUI {
    public static void main(String[] args)
    {
        config.x_bias=64;
        config.y_bias=0;
        //对于鼠标绘图程序的main。
        JFrame f=new JFrame();
        f.setTitle("伪 · 画图");
        f.setLayout(null);
        f.setResizable(true);
        f.setSize(1280,1024);
        //f.setBackground(Color.PINK);
        f.setLayout(new BorderLayout());


        //初始化界面
        LayoutInitializer li=new LayoutInitializer(f);

        JPanel p=new JPanel();
        //p.setSize(1280,800);
        p.setPreferredSize(new Dimension(1280,700));
        p.setBackground(Color.WHITE);
        f.add(p,BorderLayout.NORTH);

        f.setVisible(true);
        Graphics g=p.getGraphics();

        //设置监听
        //Main -> MyMouseListener ->MyGraphics
        MyGraphics mg=new MyGraphics();
        mg.init(g,p,f);
        MyMouseListener mml=new MyMouseListener();
        mml.init(mg);
        p.addMouseListener(mml);
        p.addMouseMotionListener(mml);
        li.setListeners(mml);
    }
}

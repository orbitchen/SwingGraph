import javax.swing.*;
import java.awt.*;

public class Main_GUI {
    public static String CURVE_ALGORITHM="Bspline";
    public static final String BSPLINE_ALGORITHM="Matrix";
    public static void main(String[] args)
    {
        config.x_bias=64;
        config.y_bias=0;
        //对于鼠标绘图程序的main。
        JFrame f=new JFrame();
        f.setTitle("伪 · 画图");
        f.setLayout(null);
        f.setResizable(true);
        f.setSize(1280,768);
        f.setMinimumSize(new Dimension(800,600));
        f.setMaximumSize(new Dimension(3840,2160));//最大4k
        //f.setBackground(Color.PINK);
        //f.setLayout(new BorderLayout());
        f.setLayout(new BorderLayout());

        //初始化界面
        LayoutInitializer li=new LayoutInitializer(f);

        JPanel p=new JPanel();
        //p.setSize(1280,800);
        //p.setMinimumSize(new Dimension(800,600-64));
        //p.setPreferredSize(new Dimension(800,600));
        p.setBackground(Color.WHITE);
        f.add(p,BorderLayout.CENTER);

        //f.pack();
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

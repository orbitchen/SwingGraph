import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Main {
    public static void main(String[] args)
    {
        //简单布局
        JFrame f=new JFrame();
        f.setLayout(null);
        f.setResizable(false);
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

        //设置打开文件+读取命令+解析完成命令
        mml.inputCommands("input.txt");

    }
}

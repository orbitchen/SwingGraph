import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.ButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class LayoutInitializer {
    private JFrame frame;

    private JButton readButton,writeButton;
    private JButton penPencil,penLine,penCircle,penRectangle,penDelete,penCurve;
    private JButton buttonColor,buttonWhite,buttonBlack,buttonRed,buttonGreen,buttonBlue,buttonSelectColor;

    private JPanel controller;

    private final Color globalColor=Color.GRAY;

    public LayoutInitializer(JFrame f)
    {
        frame=f;
        handleFrame();
    }

    private void handleFrame()
    {
        controller=new JPanel();
        controller.setPreferredSize(new Dimension(0,64));
        controller.setBackground(globalColor);
        controller.setLayout(new FlowLayout());

        readButton=new JButton();
        readButton.setText("读取文件");
        readButton.setSize(100,48);
        readButton.setPreferredSize(new Dimension(100,48));
        readButton.setBorderPainted(false);
        controller.add(readButton);

        writeButton=new JButton();
        writeButton.setText("保存为");
        writeButton.setSize(100,48);
        writeButton.setPreferredSize(new Dimension(100,48));
        writeButton.setBorderPainted(false);
        controller.add(writeButton);

        JButton blank0=new JButton();
        blank0.setSize(30,48);
        blank0.setPreferredSize(new Dimension(30,48));
        blank0.setBackground(globalColor);
        blank0.setBorderPainted(false);
        blank0.setEnabled(false);
        controller.add(blank0);

        penPencil=new JButton();
        penPencil.setText("铅笔");
        penPencil.setSize(60,48);
        penPencil.setPreferredSize(new Dimension(60,48));
        penPencil.setBorderPainted(false);
        controller.add(penPencil);

        penLine=new JButton();
        penLine.setText("直线");
        penLine.setSize(60,48);
        penLine.setPreferredSize(new Dimension(60,48));
        penLine.setBorderPainted(false);
        controller.add(penLine);

        penCircle=new JButton();
        penCircle.setText("椭圆");
        penCircle.setSize(60,48);
        penCircle.setPreferredSize(new Dimension(60,48));
        penCircle.setBorderPainted(false);
        controller.add(penCircle);

        penRectangle=new JButton();
        penRectangle.setText("矩形");
        penRectangle.setSize(60,48);
        penRectangle.setPreferredSize(new Dimension(60,48));
        penRectangle.setBorderPainted(false);
        controller.add(penRectangle);

        penDelete=new JButton();
        penDelete.setText("橡皮");
        penDelete.setSize(60,48);
        penDelete.setPreferredSize(new Dimension(60,48));
        penDelete.setBorderPainted(false);
        controller.add(penDelete);

        penCurve=new JButton();
        penCurve.setText("曲线");
        penCurve.setSize(60,48);
        penCurve.setPreferredSize(new Dimension(60,48));
        penCurve.setBorderPainted(false);
        controller.add(penCurve);

        JButton blank1=new JButton();
        blank1.setSize(30,48);
        blank1.setPreferredSize(new Dimension(30,48));
        blank1.setBackground(globalColor);
        blank1.setBorderPainted(false);
        blank1.setEnabled(false);
        controller.add(blank1);

        buttonColor=new JButton();
        buttonColor.setSize(30,30);
        buttonColor.setPreferredSize(new Dimension(30,30));
        buttonColor.setBackground(Color.BLACK);
        buttonColor.setEnabled(false);
        buttonColor.setBorderPainted(true);
        controller.add(buttonColor);

        JButton blank2=new JButton();
        blank2.setSize(30,48);
        blank2.setPreferredSize(new Dimension(30,48));
        blank2.setBackground(globalColor);
        blank2.setBorderPainted(false);
        blank2.setEnabled(false);
        controller.add(blank2);

        buttonBlack=new JButton();
        buttonWhite=new JButton();
        buttonBlue=new JButton();
        buttonRed=new JButton();
        buttonGreen=new JButton();
        initButtonColor(buttonBlack,Color.BLACK,controller);
        initButtonColor(buttonWhite,Color.WHITE,controller);
        initButtonColor(buttonBlue,Color.BLUE,controller);
        initButtonColor(buttonRed,Color.RED,controller);
        initButtonColor(buttonGreen,Color.GREEN,controller);

        JButton blank3=new JButton();
        blank3.setSize(30,48);
        blank3.setPreferredSize(new Dimension(30,48));
        blank3.setBackground(globalColor);
        blank3.setBorderPainted(false);
        blank3.setEnabled(false);
        controller.add(blank3);

        buttonSelectColor=new JButton();
        buttonSelectColor.setText("自选颜色");
        buttonSelectColor.setSize(100,48);
        buttonSelectColor.setPreferredSize(new Dimension(100,48));
        buttonSelectColor.setBorderPainted(false);
        controller.add(buttonSelectColor);


        frame.add(controller, BorderLayout.CENTER);
    }

    private void initButtonColor(JButton b,Color c,JPanel controller)
    {
        //b=new JButton();
        b.setBackground(c);
        b.setBorderPainted(false);
        if(c==globalColor)
            b.setBorderPainted(true);
        b.setSize(30,30);
        b.setPreferredSize(new Dimension(30,30));
        controller.add(b);
    }

    public void setListeners(MyMouseListener mml)
    {

        buttonGreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mml.mg.setColor(0,255,0);
                buttonColor.setBackground(Color.GREEN);
            }
        });

        buttonRed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mml.mg.setColor(255,0,0);
                buttonColor.setBackground(Color.RED);
            }
        });

        buttonBlue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mml.mg.setColor(0,0,255);
                buttonColor.setBackground(Color.BLUE);
            }
        });

        buttonBlack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mml.mg.setColor(0,0,0);
                buttonColor.setBackground(Color.BLACK);
            }
        });

        buttonWhite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mml.mg.setColor(255,255,255);
                buttonColor.setBackground(Color.WHITE);
            }
        });

        penPencil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("pencil");
                mml.setPenMode(MyMouseListener.MODE_PENCIL);
            }
        });

        penLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mml.setPenMode(MyMouseListener.MODE_LINE);
            }
        });

        penRectangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mml.setPenMode(MyMouseListener.MODE_RECTANGLE);
            }
        });

        penCircle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mml.setPenMode(MyMouseListener.MODE_CIRCLE);
            }
        });

        penDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mml.setPenMode(MyMouseListener.MODE_DELETE);
            }
        });

        penCurve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) { mml.setPenMode(MyMouseListener.MODE_CURVE);}
        });

        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser jf=new JFileChooser();
                jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jf.setMultiSelectionEnabled(false);
                jf.showOpenDialog(mml.mg.frame);
                File f=jf.getSelectedFile();
                if(f!=null)
                {
                    String fs=f.getName();
                    /*
                    if(!"bmp".equals(fs.split("\\.")[1]))
                    {
                        System.out.println("选择了非支持的图片文件");
                    }
                     */
                    //读取所有可能支持的文件格式
                    String[] formats=ImageIO.getReaderFormatNames();
                    boolean formatSupport=false;
                    for(int i=0;i<formats.length;i++)
                    {
                        if(formats[i].equals(fs.split("\\.")[1]))
                            formatSupport=true;
                    }
                    if(!formatSupport)
                    {
                       //选择了不支持的文件格式
                        JOptionPane.showConfirmDialog(mml.mg.frame,"选择了不支持的文件格式。","错误",JOptionPane.DEFAULT_OPTION);
                        return;
                    }

                    try{
                        BufferedImage bi=ImageIO.read(f);
                        mml.mg.panel.setSize(bi.getWidth(),bi.getHeight());
                        mml.mg.resize(bi.getWidth(),bi.getHeight());
                        mml.mg.setImage(bi);
                        mml.mg.panelSync();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                }

            }
        });

        writeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser jf=new JFileChooser();
                jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jf.setMultiSelectionEnabled(false);
                jf.showSaveDialog(mml.mg.frame);
                File f=jf.getSelectedFile();
                try
                {
                    if(f!=null)
                    {
                        String fs=f.getName();
                        String[] formats=ImageIO.getWriterFormatNames();
                        boolean formatSupport=false;
                        for(int i=0;i<formats.length;i++)
                        {
                            if(formats[i].equals(fs.split("\\.")[1])) {
                                formatSupport = true;
                                break;
                            }
                        }
                        if(!formatSupport)
                        {
                            //选择了不支持的文件格式
                            JOptionPane.showConfirmDialog(mml.mg.frame,"选择了不支持的文件格式。","错误",JOptionPane.DEFAULT_OPTION);
                            return;
                        }

                        /*BufferedImage bi=ImageIO.read(f);
                        mml.mg.panel.setSize(bi.getWidth(),bi.getHeight());
                        mml.mg.resize(bi.getWidth(),bi.getHeight());
                        mml.mg.setImage(bi);
                        mml.mg.panelSync();*/
                        mml.mg.save(f.getAbsolutePath(),fs.split("\\.")[1]);
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();

                }
            }
        });
    }
}

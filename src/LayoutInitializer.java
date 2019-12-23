import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class LayoutInitializer {
    private JFrame frame;

    private JButton readButton,writeButton;
    private JButton penPencil,penLine,penCircle,penRectangle,penDelete,penCurve;
    private JButton buttonColor,buttonWhite,buttonBlack,buttonRed,buttonGreen,buttonBlue;
    private JTextField textRed,textGreen,textBlue;

    private JPanel controller;

    private final Color globalColor=new Color(200,220,240);

    public LayoutInitializer(JFrame f)
    {
        frame=f;
        handleFrame();
    }

    private String getProperString(String a)
    {
        //发现UTF8其实是跨平台的。
        return a;
    }

    private void handleFrame()
    {
        controller=new JPanel();
        controller.setBorder(BorderFactory.createEtchedBorder());
        controller.setPreferredSize(new Dimension(0,64));
        //controller.setMinimumSize(new Dimension(0,64));
        //controller.setMaximumSize(new Dimension(0,64));
        //controller.setSize(0,64);
        controller.setBackground(globalColor);
        controller.setLayout(new FlowLayout());

        readButton=new JButton();
        readButton.setText(getProperString("读取文件"));
        readButton.setSize(100,48);
        readButton.setPreferredSize(new Dimension(100,48));
        readButton.setBorderPainted(false);
        controller.add(readButton);

        writeButton=new JButton();
        writeButton.setText(getProperString("写入文件"));
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
        penPencil.setText(getProperString("铅笔"));
        penPencil.setSize(60,48);
        penPencil.setPreferredSize(new Dimension(60,48));
        penPencil.setBorderPainted(false);
        controller.add(penPencil);

        penLine=new JButton();
        penLine.setText(getProperString("直线"));
        penLine.setSize(60,48);
        penLine.setPreferredSize(new Dimension(60,48));
        penLine.setBorderPainted(false);
        controller.add(penLine);

        penCircle=new JButton();
        penCircle.setText(getProperString("椭圆"));
        penCircle.setSize(60,48);
        penCircle.setPreferredSize(new Dimension(60,48));
        penCircle.setBorderPainted(false);
        controller.add(penCircle);

        penRectangle=new JButton();
        penRectangle.setText(getProperString("矩形"));
        penRectangle.setSize(60,48);
        penRectangle.setPreferredSize(new Dimension(60,48));
        penRectangle.setBorderPainted(false);
        controller.add(penRectangle);

        penDelete=new JButton();
        penDelete.setText(getProperString("橡皮"));
        penDelete.setSize(60,48);
        penDelete.setPreferredSize(new Dimension(60,48));
        penDelete.setBorderPainted(false);
        controller.add(penDelete);

        penCurve=new JButton();
        penCurve.setText(getProperString("曲线"));
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

        JLabel labelRed = new JLabel();
        labelRed.setText("R:");
        labelRed.setForeground(Color.BLACK);
        JLabel labelGreen = new JLabel();
        labelGreen.setText("G:");
        labelGreen.setForeground(Color.BLACK);
        JLabel labelBlue = new JLabel();
        labelBlue.setText("B");
        labelBlue.setForeground(Color.BLACK);
        textRed=new JTextField();textRed.setPreferredSize(new Dimension(40,20));textRed.setText("0");
        textBlue=new JTextField();textBlue.setPreferredSize(new Dimension(40,20));textBlue.setText("0");
        textGreen=new JTextField();textGreen.setPreferredSize(new Dimension(40,20));textGreen.setText("0");

        controller.add(labelRed);controller.add(textRed);
        controller.add(labelGreen);controller.add(textGreen);
        controller.add(labelBlue);controller.add(textBlue);

        /*
        buttonSelectColor=new JButton();
        buttonSelectColor.setText("自选颜色");
        buttonSelectColor.setSize(100,48);
        buttonSelectColor.setPreferredSize(new Dimension(100,48));
        buttonSelectColor.setBorderPainted(false);
        controller.add(buttonSelectColor);
         */


        frame.add(controller,BorderLayout.SOUTH);
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
                textRed.setText("0");textGreen.setText("255");textBlue.setText("0");
            }
        });

        buttonRed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mml.mg.setColor(255,0,0);
                buttonColor.setBackground(Color.RED);
                textRed.setText("255");textGreen.setText("0");textBlue.setText("0");
            }
        });

        buttonBlue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mml.mg.setColor(0,0,255);
                buttonColor.setBackground(Color.BLUE);
                textRed.setText("0");textGreen.setText("0");textBlue.setText("255");
            }
        });

        buttonBlack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mml.mg.setColor(0,0,0);
                buttonColor.setBackground(Color.BLACK);
                textRed.setText("0");textGreen.setText("0");textBlue.setText("0");
            }
        });

        buttonWhite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mml.mg.setColor(255,255,255);
                buttonColor.setBackground(Color.WHITE);
                textRed.setText("255");textGreen.setText("255");textBlue.setText("255");
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

        JTextField[] texts=new JTextField[3];
        texts[0]=textRed;texts[1]=textGreen;texts[2]=textBlue;
        KeyAdapter textKeyAdapter=new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyChar()==KeyEvent.VK_ENTER)
                {
                    for(int i=0;i<3;i++)
                    {
                        if(!isNumber(texts[i].getText()))
                            texts[i].setText("0");
                    }

                    for(int i=0;i<3;i++)
                    {
                        if(Integer.parseInt(texts[i].getText())<0)
                            texts[i].setText("0");
                        if(Integer.parseInt(texts[i].getText())>255)
                            texts[i].setText("255");
                    }

                    mml.mg.setColor(Integer.parseInt(textRed.getText()),Integer.parseInt(textGreen.getText()),Integer.parseInt(textBlue.getText()));
                    buttonColor.setBackground(new Color(Integer.parseInt(textRed.getText()),Integer.parseInt(textGreen.getText()),Integer.parseInt(textBlue.getText())));
                }
            }
        };

        FocusListener textFocusListener=new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {

            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                for(int i=0;i<3;i++)
                {
                    if(!isNumber(texts[i].getText()))
                        texts[i].setText("0");
                }

                for(int i=0;i<3;i++)
                {
                    if(Integer.parseInt(texts[i].getText())<0)
                        texts[i].setText("0");
                    if(Integer.parseInt(texts[i].getText())>255)
                        texts[i].setText("255");
                }

                mml.mg.setColor(Integer.parseInt(textRed.getText()),Integer.parseInt(textGreen.getText()),Integer.parseInt(textBlue.getText()));
                buttonColor.setBackground(new Color(Integer.parseInt(textRed.getText()),Integer.parseInt(textGreen.getText()),Integer.parseInt(textBlue.getText())));
            }
        };

        for(int i=0;i<3;i++)
        {
            texts[i].addFocusListener(textFocusListener);
            texts[i].addKeyListener(textKeyAdapter);
        }

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
                        if(fs.split("\\.").length<2)
                            break;
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
                            if(fs.split("\\.").length<2)
                                break;
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

    private boolean isNumber(String a)
    {
        if(a.isEmpty()) return false;
       for(char c:a.toCharArray())
           if(!Character.isDigit(c))
               return false;
           return true;
    }
}

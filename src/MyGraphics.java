import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Vector;

/*
注意事项：
1. 在每种方法中sync而不是在画像素点时sync。
2. 先不实现算法，将外围工作完成之后实现算法。
* */

public class MyGraphics
{
    //说明某一形状是更小的元素，不添加至shapes
    public static final int ELEMENT=-1;

    public Graphics originalGraphics;
    public JPanel panel;
    public JFrame frame;
    private BufferedImage img;

    //保存现有的所有形状
    private HashMap<Integer,Shape> shapes=new HashMap<Integer, Shape>();

    public void __debugOutput()
    {
        System.out.println("panel size："+panel.getWidth()+","+panel.getHeight());
        System.out.println("img size："+img.getWidth()+","+img.getHeight());
        System.out.println("Frame size："+frame.getWidth()+","+frame.getHeight());
        System.out.println();
    }

    public BufferedImage getImage()
    {

        BufferedImage returnVal=new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_RGB);
        for(int i=0;i<returnVal.getWidth();i++)
            for(int j=0;j<returnVal.getHeight();j++)
                returnVal.setRGB(i,j,img.getRGB(i,j));
        return returnVal;
    }

    public void setImage(BufferedImage bi)
    {
        for(int i=0;i<img.getWidth();i++)
            for(int j=0;j<img.getHeight();j++)
                img.setRGB(i,j,bi.getRGB(i,j));
    }

    private void print(String a)
    {
        System.out.println(a+"\n");
    }

    public void init(Graphics g,JPanel p,JFrame f)
    {
        originalGraphics=g;
        panel=p;
        frame=f;
        //一张RGB图片
        img=new BufferedImage(panel.getWidth(),panel.getHeight(),BufferedImage.TYPE_INT_RGB);
        clear();

        frame.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                resize(panel.getWidth(),panel.getHeight());
            }

            @Override
            public void componentMoved(ComponentEvent componentEvent) {

            }

            @Override
            public void componentShown(ComponentEvent componentEvent) {

            }

            @Override
            public void componentHidden(ComponentEvent componentEvent) {

            }
        });
    }

    public void resize(int width,int height)
    {
        print("resize");
        //panel.resize(width,height);
        panel.setSize(new Dimension(width,height));
        BufferedImage saved = getImage();
        img = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
        clear();
        int min_width = Math.min(saved.getWidth(), img.getWidth());
        int min_height = Math.min(saved.getHeight(), img.getHeight());
        for (int i = 0; i < min_width; i++) {
            for (int j = 0; j < min_height; j++) {
                img.setRGB(i, j, saved.getRGB(i, j));}}
        originalGraphics=panel.getGraphics();
        panelSync();
    }

    public void clear()
    {
        if(img.getHeight()!=panel.getHeight()||img.getWidth()!=panel.getWidth())
            System.out.println("Error @ clear @ MyGraphics: incompatible height or width.\n");

        for(int x=0;x<panel.getWidth();x++)
            for(int y=0;y<panel.getHeight();y++)
                img.setRGB(x,y,0xffffff);
    }

    private void panelSync() {originalGraphics.drawImage(img,0,0,null);}

    private void drawPixel(int x,int y)
    {
        int rgb_int=originalGraphics.getColor().getRGB();
        img.setRGB(x,y,rgb_int);
        //panelSync();
    }

    public void drawPixelWrapper(int x,int y)
    {
        Point p=resizeEnd(x,y);
        drawPixel(p.x,p.y);
        panelSync();
    }

    //abandoned
    public void __test__drawLine(int x)
    {
        for(int y=0;y<panel.getHeight();y++)
            drawPixel(x,y);
        panelSync();
        save("temp");
    }

    public void save(String name)
    {
        print("save");
        //保存为bmp格式
        try{
            ImageIO.write(img,"bmp",new File(name+".bmp"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void drawLineDDA(int id,int x1,int y1,int x2,int y2)
    {
        print("drawLineDDA");
        if(id!=ELEMENT)
        {
            Shape s=new Shape();
            s.initLine(id,originalGraphics.getColor(),x1,y1,x2,y2,"DDA");
            shapes.put(id,s);
        }

        Point p_=resizeEnd(x2,y2);
        x2=p_.x;y2=p_.y;

        Point p2=resizeEnd(x1,y1);
        x1=p2.x;y1=p2.y;

        //TODO:DONE

        //斜率
        double k=((double)y1-(double)y2)/((double)x1-(double)x2);
        double k_abs=Math.abs(k);

        if(k_abs<=1)
        {
            int x_L,y_L,x_R,y_R;
            if(x1<x2)
            {
                x_L=x1;
                y_L=y1;
                x_R=x2;
                y_R=y2;
            }
            else
            {
                x_L=x2;
                y_L=y2;
                x_R=x1;
                y_R=y1;
            }
            int x_k=x_L;
            double y_k=y_L;
            for(;x_k<=x_R;x_k++)
            {
                drawPixel(x_k,(int)Math.round(y_k));
                y_k=y_k+k;
            }

        }
        else
        {
            int x_up,y_up,x_down,y_down;
            if(y1<y2)
            {
                x_up=x1;
                y_up=y1;
                x_down=x2;
                y_down=y2;

            }
            else
            {
                x_up=x2;
                y_up=y2;
                x_down=x1;
                y_down=y1;
            }
            int y_k=y_up;
            double x_k=x_up;
            for(;y_k<=y_down;y_k++)
            {
                drawPixel((int)Math.round(x_k),y_k);
                x_k=x_k+1/k;
            }
        }
        panelSync();

    }

    public void drawLineBresenham(int id,int x1,int y1,int x2,int y2)
    {

        if(id!=ELEMENT)
        {
            Shape s=new Shape();
            s.initLine(id,originalGraphics.getColor(),x1,y1,x2,y2,"Bresenham");
            shapes.put(id,s);
        }

        Point p_=resizeEnd(x2,y2);
        x2=p_.x;y2=p_.y;

        Point p2=resizeEnd(x1,y1);
        x1=p2.x;y1=p2.y;

        System.out.println("drawLineBresenham:"+x2+","+y2);

        //TODO:DONE
        int dx,dy,sx,sy,p,dx_2,dy_2;
        dx=Math.abs(x2-x1);dy=Math.abs(y2-y1);
        sx=(x2>x1)?1:-1;
        sy=(y2>y1)?1:-1;
        dx_2=dx*2;dy_2=dy*2;
        if(x1==x2)
        {
            int y_min=Math.min(y1,y2),y_max=Math.max(y1,y2);
            for(int i=y_min;i<=y_max;i++)
                drawPixel(x1,i);
        }
        else if(y1==y2)
        {
            int x_min=Math.min(x1,x2),x_max=Math.max(x1,x2);
            for(int i=x_min;i<=x_max;i++)
                drawPixel(i,y1);
        }
        else if(Math.abs(dx)>Math.abs(dy))
        {
            //p=-dx;//存在偏差
            p=2*sx*dy-dx;
            int x=x1,y=y1;
            for(;x!=x2;x+=sx)
            {
                drawPixel(x,y);
                if(p<=0)
                    p=p+dy_2;
                else{
                    p=p+dy_2-dx_2;
                    y+=sy;
                }
            }
            drawPixel(x2,y2);
        }
        else
        {
            //p=-dy;
            p=2*sy*dx-dy;
            int x=x1,y=y1;
            for(;y!=y2;y+=sy)
            {
                drawPixel(x,y);
                if(p<0){
                    p=p+dx_2;
                } else{
                    p=p+dx_2-dy_2;
                    x+=sx;
                }
            }
            drawPixel(x2,y2);//收尾
        }

        panelSync();
    }

    private Point resizeEnd(int x2,int y2)
    {
        //重整边缘的点,
        //TODO:目前的方法不太好。
        Point p=new Point();
        int x=x2,y=y2;
        if(x2<0)
            x=0;
        else if(x2>=panel.getWidth())
            x=panel.getWidth()-1;

        if(y2<0)
            y=0;
        else if(y2>=panel.getHeight())
            y=panel.getHeight()-1;

        p.setPoint(x,y);
        return p;
    }

    public void drawPolygonDDA(int id,Point[] p)
    {
        print("drawPolygonDDA");
        if(id!=ELEMENT) {
            Shape s = new Shape();
            s.initPolygon(id, originalGraphics.getColor(), p,"DDA");
            shapes.put(id, s);
        }

        for(int i=0;i<p.length;i++)
        {
            int t1=i;
            int t2=(i+1)%p.length;
            drawLineDDA(ELEMENT,p[t1].x,p[t1].y,p[t2].x,p[t2].y);
        }

    }

    public void drawPolygonBresenham(int id,Point[] p)
    {
        print("drawPolygonBresenham");
        if(id!=ELEMENT) {
            Shape s = new Shape();
            s.initPolygon(id, originalGraphics.getColor(), p,"Bresenham");
            shapes.put(id, s);
        }

        for(int i=0;i<p.length;i++)
        {
            int t1=i;
            int t2=(i+1)%p.length;
            drawLineBresenham(ELEMENT,p[t1].x,p[t1].y,p[t2].x,p[t2].y);
        }

    }

    public void drawRectangle(int x1,int y1,int x2,int y2)
    {
        Point p1,p2;
        p1=resizeEnd(x1,y1);p2=resizeEnd(x2,y2);
        x1=p1.x;y1=p1.y;x2=p2.x;y2=p2.y;
        drawLineBresenham(ELEMENT,x1,y1,x1,y2);
        drawLineBresenham(ELEMENT,x1,y1,x2,y1);
        drawLineBresenham(ELEMENT,x2,y2,x1,y2);
        drawLineBresenham(ELEMENT,x2,y2,x2,y1);
    }

    public void drawDeleteRectangle(int x,int y)
    {
        int x1,y1,x2,y2;
        x1=x-32;y1=y-32;
        x2=x+32;y2=y+32;
        Point p1,p2;
        p1=resizeEnd(x1,y1);p2=resizeEnd(x2,y2);
        x1=p1.x;y1=p1.y;x2=p2.x;y2=p2.y;
        drawLineBresenham(ELEMENT,x1,y1,x1,y2);
        drawLineBresenham(ELEMENT,x1,y1,x2,y1);
        drawLineBresenham(ELEMENT,x2,y2,x1,y2);
        drawLineBresenham(ELEMENT,x2,y2,x2,y1);
        for(int i=x1;i<=x2;i++)
        {
            for(int j=y1;j<=y2;j++)
                img.setRGB(i,j,0xffffff);
        }
        panelSync();
    }

    private void draw4Points(long x,long y,long x1,long y1)
    {
        //针对画椭圆的算法
        /*
        drawPixel(x1,y1);
        int dx=Math.abs(x-x1);
        int dy=Math.abs(y-y1);
        drawPixel(x+dx,y+dy);
        drawPixel(x-dx,y+dy);
        drawPixel(x+dx,y-dy);
        drawPixel(x-dx,y-dy);
        */
         drawPixel((int)(x+x1),(int)(y+y1));
         drawPixel((int)(x+x1),(int)(y-y1));
         drawPixel((int)(x-x1),(int)(y+y1));
         drawPixel((int)(x-x1),(int)(y-y1));
    }

    public void drawOval(int id,int x,int y,int a,int b)
    {
        print("drawOval");
        System.out.println("Oval:"+x+","+y+","+a+","+b);
        //x对应a y对应b
        //同时也是画圆的算法
        if(id!=ELEMENT) {
            Shape s = new Shape();
            s.initOval(id, originalGraphics.getColor(), x, y, a, b);
            shapes.put(id, s);
        }

        //TODO:完成椭圆绘制算法。
        //判断参数 b*b*xt>=a*a*yt
        long xk,yk;
        long rx,ry;
        rx=a;ry=b;xk=0;yk=ry;
        long rx2,ry2;
        rx2=rx*rx;ry2=ry*ry;
        long p=ry2-rx2*ry+rx2/4;
        while(ry2*xk<rx2*yk)
        {
            draw4Points(x,y,xk,yk);
            if(p<0) {
                p=p+2*ry2*xk+3*ry2;
                xk++;
            } else{
                p=p+2*ry2*xk-2*rx2*yk+2*rx2+3*ry2;
                xk++;yk--;
            }
            //panelSync();
        }
        p=(long)(ry2*(xk+0.5)*(xk+0.5)+rx2*(yk-1)*(yk-1)-rx2*ry2);
        while(yk!=0)
        {
            draw4Points(x,y,xk,yk);
            if(p>0){
                p=p-2*rx2*yk+3*rx2;
                yk--;
            } else {
                p=p+2*ry2*xk-2*rx2*yk+2*ry2+3*rx2;
                yk--;xk++;
            }
        }
        //补上最后一个点
        draw4Points(x,y,rx,0);
        panelSync();
    }


    public void drawOvalWrapper(int x1,int y1,int x2,int y2)
    {
        System.out.println("drawOvalWrapper");
        Point p1,p2;
        p1=resizeEnd(x1,y1);p2=resizeEnd(x2,y2);
        x1=p1.x;y1=p1.y;x2=p2.x;y2=p2.y;
        int x,y,a,b;
        x=(x1+x2)/2;
        y=(y1+y2)/2;
        a=Math.abs(x1-x2)/2;
        b=Math.abs(y1-y2)/2;
        drawOval(ELEMENT,x,y,a,b);

    }

    public void drawCurveBezier(int id,Point[] p)
    {
        print("drawCurveBezier");
        if(id!=ELEMENT)
        {
            Shape s = new Shape();
            s.initCurve(id,originalGraphics.getColor(),p,"Bezier");
            shapes.put(id, s);
        }

        //TODO

    }

    public void drawCurveBezierWrapper_Vector(Vector<Point> v)
    {
        Point[] ps=new Point[v.size()];
        for(int i=0;i<v.size();i++)
            ps[i]=v.get(i);
        drawCurveBezier(ELEMENT,ps);
    }

    public void drawCurveBezierWrapper_Vector_Point(Vector<Point> v,Point p)
    {
        Point[] ps=new Point[v.size()+1];
        for(int i=0;i<v.size();i++)
            ps[i]=v.get(i);
        ps[v.size()]=p;
        drawCurveBezier(ELEMENT,ps);
    }

    public void drawCurveBspline(int id,Point[] p)
    {
        print("drawCurveBspline");
        if(id!=ELEMENT)
        {
            Shape s = new Shape();
            s.initCurve(id,originalGraphics.getColor(),p,"B-spline");
            shapes.put(id, s);
        }

        //TODO

    }

    //除了某个id之外所有图形重绘
    private void redrawExcept(int id)
    {
        Color preColor=originalGraphics.getColor();

        for (java.util.Map.Entry<Integer, Shape> integerShapeEntry : shapes.entrySet()) {
            HashMap.Entry entry = (HashMap.Entry) integerShapeEntry;

            Shape s = (Shape) entry.getValue();
            if (s.id != id) {
                originalGraphics.setColor(s.color);
                if (s.type == Shape.LINE) {
                    if (s.algorithm.equals("DDA"))
                        drawLineDDA(ELEMENT, s.location[0].x, s.location[0].y, s.location[1].x, s.location[1].y);
                    else
                        drawLineBresenham(ELEMENT, s.location[0].x, s.location[0].y, s.location[1].x, s.location[1].y);
                } else if (s.type == Shape.POLYGON) {
                    if (s.algorithm.equals("DDA"))
                        drawPolygonDDA(ELEMENT, s.location);
                    else
                        drawPolygonBresenham(ELEMENT, s.location);
                } else if (s.type == Shape.OVAL)
                    drawOval(ELEMENT, s.location[0].x, s.location[0].y, s.location[1].x, s.location[1].y);
                else if (s.type == Shape.CURVE) {
                    if (s.algorithm.equals("Bezier"))
                        drawCurveBezier(ELEMENT, s.location);
                    else
                        drawCurveBspline(ELEMENT, s.location);
                } else
                    System.out.println("Error @ redrawExcept @ MyGraphics: invalid type.\n");
            }
        }


        originalGraphics.setColor(preColor);
    }

    public void translate(int id,int dx,int dy)
    {
        print("translate");
        //先清空，重绘其他部分，然后画移动后的
        clear();
        redrawExcept(id);

        //TODO:平移，之后要更改原坐标。
        //所有的图元都要支持平移
        Shape s=shapes.get(id);

        if(s.type==Shape.LINE) {
            s.location[0].x+=dx;
            s.location[1].x+=dx;
            s.location[0].y+=dy;
            s.location[1].y+=dy;
            if(s.algorithm.equals("DDA"))
                drawLineDDA(ELEMENT, s.location[0].x, s.location[0].y, s.location[1].x, s.location[1].y);
            else
                drawLineBresenham(ELEMENT, s.location[0].x, s.location[0].y, s.location[1].x, s.location[1].y);
        }
        else if(s.type==Shape.POLYGON) {
            for(int i=0;i<s.location.length;i++)
            {
                s.location[i].x+=dx;
                s.location[i].y+=dy;
            }
            if(s.algorithm.equals("DDA"))
                drawPolygonDDA(ELEMENT, s.location);
            else
                drawPolygonBresenham(ELEMENT, s.location);
        }
        else if(s.type==Shape.OVAL) {
            s.location[0].x+=dx;
            s.location[0].y+=dy;
            drawOval(ELEMENT, s.location[0].x, s.location[0].y, s.location[1].x, s.location[1].y);
        }
        else if(s.type==Shape.CURVE) {
            for(int i=0;i<s.location.length;i++)
            {
                s.location[i].x+=dx;
                s.location[i].y+=dy;
            }
            if(s.algorithm.equals("Bezier"))
                drawCurveBezier(ELEMENT, s.location);
            else
                drawCurveBspline(ELEMENT, s.location);
        }
    }

    private void rotatePoint(int x, int y, double arc, Point p)
    {
        int x0=p.x;
        int y0=p.y;
        p.x=(int)(x+(x0-x)*Math.cos(arc)-(y0-y)*Math.sin(arc));
        p.y=(int)(y+(x0-x)*Math.sin(arc)+(y0-y)*Math.cos(arc));
    }

    public void rotate(int id,int x,int y,int r)
    {
        print("rotate");
        clear();
        redrawExcept(id);

        //TODO：旋转，之后要更改原坐标。
        //除了椭圆之外都要支持旋转
        //x y为旋转中心，r为顺时针旋转角度
        Shape s=shapes.get(id);
        if(s==null)
        {
            System.out.println("旋转时发生错误：图元不存在。");
            return;
        }

        double arc=(Math.PI/180)*r;

        if(s.type==Shape.LINE)
        {
            rotatePoint(x,y,arc,s.location[0]);
            rotatePoint(x,y,arc,s.location[1]);
            if(s.algorithm.equals("DDA"))
                drawLineDDA(ELEMENT,s.location[0].x,s.location[0].y,s.location[1].x,s.location[1].y);
            else
                drawLineBresenham(ELEMENT,s.location[0].x,s.location[0].y,s.location[1].x,s.location[1].y);
        }
        else if(s.type==Shape.CURVE)
        {
            for(int i=0;i<s.location.length;i++)
                rotatePoint(x,y,arc,s.location[i]);
            if(s.algorithm.equals("Bezier"))
                drawCurveBezier(ELEMENT,s.location);
            else
                drawCurveBspline(ELEMENT,s.location);
        }
        else if(s.type==Shape.OVAL)
        {
            System.out.println("试图对椭圆进行旋转。");
        }
        else if(s.type==Shape.POLYGON)
        {
            for(int i=0;i<s.location.length;i++)
                rotatePoint(x,y,arc,s.location[0]);
            if(s.algorithm.equals("DDA"))
                drawPolygonDDA(ELEMENT,s.location);
            else
                drawPolygonBresenham(ELEMENT,s.location);
        }
    }

    private void scalePoint(int x,int y,float ratio,Point p)
    {
        p.x=(int)(p.x*ratio+x*(1-ratio));
        p.y=(int)(p.y*ratio+y*(1-ratio));
    }

    public void scale(int id,int x,int y,float ratio)
    {
        print("scale");
        clear();
        redrawExcept(id);

        //TODO:缩放，之后要更改原坐标。
        //所有的图元都要支持缩放
        Shape s=shapes.get(id);
        if(s==null){System.out.println("缩放时发生错误：图元不存在。");}

        if(s.type==Shape.LINE)
        {
            scalePoint(x,y,ratio,s.location[0]);
            scalePoint(x,y,ratio,s.location[1]);
            if(s.algorithm.equals("DDA"))
                drawLineDDA(ELEMENT,s.location[0].x,s.location[0].y,s.location[1].x,s.location[1].y);
            else
                drawLineBresenham(ELEMENT,s.location[0].x,s.location[0].y,s.location[1].x,s.location[1].y);
        }
        else if(s.type==Shape.CURVE)
        {
            for(int i=0;i<s.location.length;i++)
                scalePoint(x,y,ratio,s.location[i]);
            if(s.algorithm.equals("Bezier"))
                drawCurveBezier(ELEMENT,s.location);
            else
                drawCurveBspline(ELEMENT,s.location);
        }
        else if(s.type==Shape.OVAL)
        {
            scalePoint(x,y,ratio,s.location[0]);
            drawOval(ELEMENT,s.location[0].x,s.location[0].y,s.location[1].x,s.location[1].y);
        }
        else if(s.type==Shape.POLYGON)
        {
            for(int i=0;i<s.location.length;i++)
                scalePoint(x,y,ratio,s.location[0]);
            if(s.algorithm.equals("DDA"))
                drawPolygonDDA(ELEMENT,s.location);
            else
                drawPolygonBresenham(ELEMENT,s.location);
        }

    }


    public void clipCohenSutherland(int id,int x_min,int y_min,int x_max,int y_max)
    {
        print("clipCohenSutherland");

        //TODO:学习算法之后完成。
        Shape s=shapes.get(id);
        if(s==null){System.out.println("裁剪：无法找到图元");}
        if(s.type!=Shape.LINE){System.out.println("裁剪：试图对非线段进行裁剪");}
        clear();
        redrawExcept(id);

        int up,down,left,right,in;
        up=1;down=2;left=4;right=8;in=0;

        int code[]=new int[2];
        code[1] = 0;code[0]=0;

        for(int i=0;i<=1;i++)
        {
            if(s.location[i].x<x_min)
                code[i]|=left;
            else if(s.location[i].x>x_max)
                code[i]|=right;

            if(s.location[i].y<y_min)
                code[i]|=down;
            else if(s.location[i].y>y_max)
                code[i]|=up;
        }

        while(true)
        {
            if(code[0]==0&&code[1]==0)
                break;

            int code_and=code[0]&code[1];
            if(code_and!=0)
            {
                //完全在界外
                s.location[0].x=s.location[1].y=s.location[0].y=s.location[1].x=0;
                break;
            }

            //有可能两个都在界外，也有可能只有一个在界外
            for(int i=0;i<2;i++)
            {
                if(code[i]!=0)
                {
                    if((code[i]&up)!=0){
                        s.location[i].y=y_max;
                        s.location[i].x=s.location[0].x+(s.location[1].x-s.location[0].x)*(y_max-s.location[0].y)/(s.location[1].y-s.location[0].y);

                    }
                    else if((code[i]&down)!=0){
                        s.location[i].y=y_min;
                        s.location[i].x=s.location[0].x+(s.location[1].x-s.location[0].x)*(y_min-s.location[0].y)/(s.location[1].y-s.location[0].y);

                    }

                    if((code[i]&right)!=0){
                        s.location[i].x=x_max;
                        s.location[i].y=s.location[0].y+(s.location[1].y-s.location[0].y)*(x_max-s.location[0].x)/(s.location[1].x-s.location[0].x);

                    }
                    else if((code[i]&left)!=0){
                        s.location[i].x=x_min;
                        s.location[i].y=s.location[0].y+(s.location[1].y-s.location[0].y)*(x_min-s.location[0].x)/(s.location[1].x-s.location[0].x);

                    }
                }
            }

            break;
        }
        if(s.algorithm.equals("DDA"))
            drawLineDDA(ELEMENT,s.location[0].x,s.location[0].y,s.location[1].x,s.location[1].y);
        else
            drawLineBresenham(ELEMENT,s.location[0].x,s.location[0].y,s.location[1].x,s.location[1].y);
    }

    public void clipLiangBarsky(int id,int x_min,int y_min,int x_max,int y_max)
    {
        print("clipLiangBarsky");
        //TODO:学习算法之后完成。

        Shape s=shapes.get(id);
        if(s==null){System.out.println("裁剪：无法找到图元");}
        if(s.type!=Shape.LINE){System.out.println("裁剪：试图对非线段进行裁剪");}
        clear();
        redrawExcept(id);

        int p[]=new int[4];
        int q[]=new int[4];
        p[0]=s.location[0].x-s.location[1].x;//-delta x
        p[1]=-p[0];//delta x
        p[2]=s.location[0].y-s.location[1].y;//-delta y
        p[3]=-p[2];//delta y

        //0  1  2  3
        //左右下上

        q[0]=s.location[0].x-x_min;
        q[1]=x_max-s.location[0].x;
        q[2]=s.location[0].y-y_min;
        q[3]=y_max-s.location[0].y;

        double u,u0,u1;
        u0=0;u1=1;
        boolean isOut=false;

        for(int i=0;i<4;i++)
        {
            u=(double)q[i]/(double)p[i];
            if(p[i]<0)//从外部到内部
            {
                u0=Math.max(u0,u);
                if(u0>u1)
                    isOut=true;//任何情况下只要出现这种情况即出界
            }
            else if(p[i]>0)//从内部到外部
            {
                u1=Math.max(u1,u);
                if(u0>u1)
                    isOut=true;
            }
            else if(q[i]<0)
            {
                //完全在界外
                isOut=true;
            }
        }

        if(isOut){
            s.location[0].x=s.location[0].y=s.location[1].x=s.location[1].y=0;
        } else {
            int x0,y0,delta_x,delta_y;
            x0=s.location[0].x;y0=s.location[0].y;
            delta_x=p[1];delta_y=p[3];
            s.location[0].x=(int)(x0+u0*delta_x);
            s.location[0].y=(int)(y0+u0*delta_y);
            s.location[1].x=(int)(x0+u1*delta_x);
            s.location[1].y=(int)(y0+u1*delta_y);
        }

        if(s.algorithm.equals("DDA"))
            drawLineDDA(ELEMENT,s.location[0].x,s.location[0].y,s.location[1].x,s.location[1].y);
        else
            drawLineBresenham(ELEMENT,s.location[0].x,s.location[0].y,s.location[1].x,s.location[1].y);

    }

    public void setColor(int r,int g,int b)
    {
        print("setColor");
        originalGraphics.setColor(new Color(r,g,b));
    }
}

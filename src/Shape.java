import java.awt.*;

public class Shape {
    //线段 多边形 椭圆 曲线
    public static final int LINE = 0;
    public static final int POLYGON = 1;
    public static final int OVAL = 2;
    public static final int CURVE = 3;

    //需要保存的信息：id，位置信息，颜色信息（将来可能需要重绘）
    public int id;
    public int type;
    public Point[] location;
    public Color color;
    public String algorithm;

    public Shape()
    {
        id=type=0;
        color=null;
        location=null;
    }

    public void initLine(int id,Color color,int x1,int y1,int x2,int y2,String algorithm)
    {
        this.id=id;
        this.color=color;
        this.type=LINE;
        this.location=new Point[2];
        location[0]=new Point();
        location[1]=new Point();
        location[0].setPoint(x1,y1);
        location[1].setPoint(x2,y2);
        this.algorithm=algorithm;
    }

    public void initPolygon(int id,Color color,Point[] _p,String algorithm)
    {
        this.id=id;
        this.location=_p;
        this.color=color;
        this.type=POLYGON;
        this.algorithm=algorithm;

    }

    public void initOval(int id,Color color,int x,int y,int a,int b)
    {
        this.id=id;
        this.location=new Point[2];
        location[0]=new Point();
        location[1]=new Point();
        location[0].setPoint(x,y);
        location[1].setPoint(a,b);
        this.color=color;
        this.type=OVAL;
    }

    public void initCurve(int id,Color color,Point[] p,String algorithm)
    {
        this.id=id;
        this.color=color;
        this.location=p;
        this.type=CURVE;
        this.algorithm=algorithm;
    }
}

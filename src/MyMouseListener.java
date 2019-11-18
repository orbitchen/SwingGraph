import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class MyMouseListener implements MouseListener {

    MyGraphics mg;

    //外围用换行符分割，内部用空格分隔即可。
    List<String> commands=new LinkedList<String>();

    public void init(MyGraphics m) {mg=m;}

    public void inputCommands(String path)
    {
        try {
            BufferedReader in=new BufferedReader(new FileReader(path));
            String str;
            while((str=in.readLine())!=null)
            {
                commands.add(str);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        resolveCommands();
    }

    private void printError(String[] token,int len,int line)
    {
        try {
            if (token.length != len) {
                System.out.println("Error @ MyMouseListener @ resolveCommands: invalid command @ line " + String.valueOf(line + 1) + ".\n");
                throw (new Exception());
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private void resolveCommands()
    {
        //TODO:命令解析。
        int size=commands.size();
        for(int i=0;i<size;i++)
        {
            String[] token=commands.get(i).split(" ");
            if(token[0].equals("resetCanvas"))
            {
                printError(token,3,i);
                mg.resize(Integer.parseInt(token[1]),Integer.parseInt(token[2]));
            }
            else if(token[0].equals("saveCanvas"))
            {
                printError(token,2,i);
                mg.save(token[1]);
            }
            else if(token[0].equals("setColor"))
            {
                printError(token,4,i);
                mg.setColor(Integer.parseInt(token[1]),Integer.parseInt(token[2]),Integer.parseInt(token[3]));
            }
            else if(token[0].equals("drawLine"))
            {
                printError(token,7,i);
                if(token[6].equals("DDA"))
                {
                    int[] vals=new int[5];
                    for(int j=0;j<5;j++)
                        vals[j]=Integer.parseInt(token[j+1]);
                    mg.drawLineDDA(vals[0],vals[1],vals[2],vals[3],vals[4]);
                }
                else if(token[6].equals("Bresenham"))
                {
                    int[] vals=new int[5];
                    for(int j=0;j<5;j++)
                        vals[j]=Integer.parseInt(token[j+1]);
                    mg.drawLineBresenham(vals[0],vals[1],vals[2],vals[3],vals[4]);
                }
                else
                {
                    System.out.println("Error @ resolveCommands @ MyMouseListener: invalid algorithm @ line: "+String.valueOf(i+1)+".\n");
                }

            }
            else if(token[0].equals("drawPolygon"))
            {
                printError(token,4,i);
                int id=Integer.parseInt(token[1]);
                int n=Integer.parseInt(token[2]);
                String algorithm=token[3];
                Point[] p=new Point[n];
                for(int j=0;j<n;j++)
                    p[j]=new Point();
                i++;
                //切换到下一行
                token=commands.get(i).split(" ");
                //检查错误
                printError(token,2*n,i);
                for(int j=0;j<n;j++)
                    p[j].setPoint(Integer.parseInt(token[2*j]),Integer.parseInt(token[2*j+1]));
                if(algorithm.equals("DDA"))
                {
                    mg.drawPolygonDDA(id,p);

                }
                else if(algorithm.equals("Bresenham"))
                {
                    mg.drawPolygonBresenham(id,p);
                }
                else
                {
                    System.out.println("Error @ resolveCommands @ MyMouseListener: invalid algorithm @ line: "+String.valueOf(i+1)+".\n");
                }
            }
            else if(token[0].equals("drawEllipse"))
            {
                printError(token,6,i);
                int id,x,y,a,b;
                id=Integer.parseInt(token[1]);
                x=Integer.parseInt(token[2]);
                y=Integer.parseInt(token[3]);
                a=Integer.parseInt(token[4]);
                b=Integer.parseInt(token[5]);
                mg.drawOval(id,x,y,a,b);
            }
            else if(token[0].equals("drawCurve"))
            {
                printError(token,4,i);
                int id,n;
                String algorithm;
                id=Integer.parseInt(token[1]);
                n=Integer.parseInt(token[2]);
                algorithm=token[3];
                Point[] p=new Point[n];
                for(int j=0;j<n;j++)
                    p[j]=new Point();
                //切换到下一行
                i++;
                token=commands.get(i).split(" ");
                printError(token,2*n,i);
                for(int j=0;j<n;j++)
                    p[j].setPoint(Integer.parseInt(token[2*j]),Integer.parseInt(token[2*j+1]));
                if(algorithm.equals("Bezier"))
                {
                    mg.drawCurveBezier(id,p);
                }
                else if(algorithm.equals("B-spline"))
                {
                    mg.drawCurveBspline(id,p);
                }
                else
                {
                    System.out.println("Error @ resolveCommands @ MyMouseListener: invalid algorithm @ line: "+String.valueOf(i+1)+".\n");
                }
            }
            else if(token[0].equals("translate"))
            {
                printError(token,4,i);
                mg.translate(Integer.parseInt(token[1]),Integer.parseInt(token[2]),Integer.parseInt(token[3]));

            }
            else if(token[0].equals("rotate"))
            {
                printError(token,5,i);
                mg.rotate(Integer.parseInt(token[1]),Integer.parseInt(token[2]),Integer.parseInt(token[3]),Integer.parseInt(token[4]));
            }
            else if(token[0].equals("scale"))
            {
                printError(token,5,i);
                mg.scale(Integer.parseInt(token[1]),Integer.parseInt(token[2]),Integer.parseInt(token[3]),Integer.parseInt(token[4]));

            }
            else if(token[0].equals("clip"))
            {
                printError(token,7,i);
                String algorithm=token[6];
                int id,x1,y1,x2,y2;
                id=Integer.parseInt(token[1]);
                x1=Integer.parseInt(token[2]);
                y1=Integer.parseInt(token[3]);
                x2=Integer.parseInt(token[4]);
                y2=Integer.parseInt(token[5]);

                if(algorithm.equals("Cohen-Sutherland"))
                {
                    mg.clipCohenSutherland(id,x1,y1,x2,y2);
                }
                else if(algorithm.equals("Liang-Barsky"))
                {
                    mg.clipLiangBarsky(id,x1,y1,x2,y2);
                }
                else
                {
                    System.out.println("Error @ resolveCommands @ MyMouseListener: invalid algorithm @ line: "+String.valueOf(i+1)+".\n");
                }
            }
        }

        System.out.println("resolve done");
    }


    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}

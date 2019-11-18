public class RGB {
    //RGB和int互转
    public int R,G,B;
    public RGB()
    {
        R=G=B=0;
    }
    public RGB(int r,int g,int b)
    {
        R=r;
        G=g;
        B=b;
    }

    public static int RGB2int(RGB rgb)
    {
        return rgb.R<<4+rgb.G<<2+rgb.B;
    }

    public static RGB int2RGB(int i)
    {
        RGB retVal=new RGB();
        retVal.R=i&0x00ff0000;
        retVal.G=i&0x0000ff00;
        retVal.B=i&0x000000ff;
        return retVal;
    }

}

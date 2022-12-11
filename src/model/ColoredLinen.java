package model;


public class ColoredLinen extends Linen
{
    public My_color mycolor;
    
    public ColoredLinen()
    {
    	this.tWashing = 40;
    	this.tIroning = 30;
        this.mycolor = My_color.light;
    }
   
    public ColoredLinen(int tWashing, int tIroning, My_color mycolor)
    {
    	this.tWashing = tWashing;
    	this.tIroning = tIroning;
    	this.mycolor = mycolor;
    }


    public My_color getColor() {
        return this.mycolor;
    }
    public String toShortStrnig(){
        return String.valueOf(tWashing) + "|" + String.valueOf(tIroning) + "|" + mycolor.getColor().toString();
    }
    @Override
    public String toString()
    {
        return "Temperature of washing:\t" + String.valueOf(this.tWashing)
        + "\nTemperature of ironing:\t" + String.valueOf(this.tIroning) + "\nColor:\t" + mycolor + '\n';
    }
}

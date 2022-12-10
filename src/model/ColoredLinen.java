package model;


public class ColoredLinen extends Linen
{
    public Color color;
    
    public ColoredLinen()
    {
    	this.tWashing = 40;
    	this.tIroning = 30;
        this.color = Color.light;
    }
   
    public ColoredLinen(int tWashing, int tIroning, Color color)
    {
    	this.tWashing = tWashing;
    	this.tIroning = tIroning;
    	this.color = color;
    }


    public Color getColor() {
        return this.color;
    }
    public String toShortStrnig(){
        return String.valueOf(tWashing) + "|" + String.valueOf(tIroning) + "|" + color.getColor().toString();
    }
    @Override
    public String toString()
    {
        return "Temperature of washing:\t" + String.valueOf(this.tWashing)
        + "\nTemperature of ironing:\t" + String.valueOf(this.tIroning) + "\nColor:\t" + color + '\n';
    }
}

package model;

public enum My_color {
	light("light"),
	dark("dark"),
	colored("colored");
	
	private String color;
	
	My_color(String color)
	{
       this.color = color;
	}
	
	public String getColor(){
		return color;
   }
}

package model;

import java.util.ArrayList;
import java.util.List;

public class WashingMachine {
    public int temperature;
    public String powdery;
    public String conditioner;
    boolean flag = false;
    public List<ColoredLinen> linens = new ArrayList<ColoredLinen>();
    
    public WashingMachine() {
        this.temperature = 30;
        this.powdery = "Mif";
        this.conditioner = "Same";
    }
    public WashingMachine(int temperature, String powdery, String conditioner) {
        this.temperature = temperature;
        this.powdery = powdery;
        this.conditioner = conditioner;
    }

    public void Load(ColoredLinen linen)
    {
    	linens.add(linen);
    }
    public void Loads(List<ColoredLinen> linens_list) {
        for (ColoredLinen linen : linens_list){
            linens.add(linen);
        }
    }

    public String toShortString(){
        return String.valueOf(temperature) + "|" + powdery + "|" + conditioner;
    }

    public String toString() {
        String ret = "Machine:\n";
        ret += "Temperature:" + Integer.toString(temperature) +'\n';
        ret += "Powdertype:" + powdery + '\n';
        ret += "conditioner:" + conditioner + '\n';
        return ret;
    }
    public String ToString() {
    	String ret = "Machine:\n";
    	ret += "Temperature:" + Integer.toString(temperature) +'\n';
    	ret += "Powdertype:" + powdery + '\n';
    	ret += "conditioner:" + conditioner + '\n';
    	for (ColoredLinen linen : linens) {
    		ret += linen.toString();
    	}
    	return ret;
    }
}

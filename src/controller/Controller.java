package controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import model.*;
import view.View;

public class Controller {
	private static Random random = new Random();
	public static Model model;
	public static View view;
	public static String generateString(int length) {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		char[] text = new char[length];
		for (int i = 0; i < length; i++)
		{
			text[i] = characters.charAt(random.nextInt(characters.length()));
		}
		return new String(text);
	}
	public static Linen generateLinen() {
		int tmptW = random.nextInt(30, 100);
		int tmptI = random.nextInt(30, 100);
		return new Linen(tmptW, tmptI);
	}
	public static Color inputColor() throws java.lang.IllegalArgumentException {
		while (true) {
			Scanner sc = new Scanner(System.in);
			System.out.println("Input color of linen: dark / light / colored\n");
		    try{
		    	return Color.valueOf(sc.nextLine());
		    }
		    catch (IllegalArgumentException exc){
		    	System.out.println("Incorrect input");
		    }
		    finally {
		    	}
		    }
	}
	public static String inputString(String msg) {
		while (true)
		{
			Scanner sc = new Scanner(System.in);
			System.out.print(msg);
			if (sc.hasNext())
			{
				return sc.next();
			}
		}
	}
	public static int inputInt(String mes) {
		System.out.println(mes);
	        while (true){
	            Scanner sc = new Scanner(System.in);
	        if (sc.hasNextInt()){
	            return sc.nextInt();
	        }
	    }
	}
	public static int choice(String mes, int start, int end){
		System.out.println(mes);
		while (true){
			Scanner sc = new Scanner(System.in);
			if (sc.hasNextInt()){
				int next = sc.nextInt();
				if (next >= start && next <= end){
					return next;
				}
			}
		}
	}
	public static ColoredLinen inputLinen() {
	    System.out.println("=========================================\n");
	    int tempW = inputInt("Input temperature of washing\n");
	    int tempI = inputInt("Input temperature of ironing\n");
	    Color color = inputColor();
	    ColoredLinen ans = new ColoredLinen(tempW, tempI, color);
	    return  ans;
	}
	public static WashingMachine inputWS() {
	    int temp = inputInt("Input temperature of machine");
	    WashingMachine machine = new WashingMachine(temp, "Mif", "Ariel");
	    return machine;
	}
}

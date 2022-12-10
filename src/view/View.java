package view;

import model.*;
import controller.*;

public class View {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    public static void regMenu() {
        System.out.println(ANSI_BLUE + "==============================" + ANSI_RESET);
        System.out.println("1) Login,");
        System.out.println("2) Registration,");
        System.out.println("3) Exit,");
        System.out.println(ANSI_BLUE + "==============================" + ANSI_RESET);
    }
    public static void adminMenu() {
        System.out.println(ANSI_BLUE + "==============================" + ANSI_RESET);
        System.out.println("1) Show laundry information,");
        System.out.println("2) Add machine,");
        System.out.println("3) Autotests,");
        System.out.println("4) Debug,");
        System.out.println("5) Log out,");
        System.out.println(ANSI_BLUE + "==============================" + ANSI_RESET);
    }
    public static void userMenu() {
        System.out.println(ANSI_BLUE + "==============================" + ANSI_RESET);
        System.out.println("1) Load linens,");
        System.out.println("2) Get out linens");
        System.out.println("3) Log out,");
        System.out.println(ANSI_BLUE + "==============================" + ANSI_RESET);
    }
    public static void machinesMenu() {
        System.out.println(ANSI_BLUE + "==============================" + ANSI_RESET);
        System.out.println("1) View all machines,");
        System.out.println("2) View empty machines,");
        System.out.println("3) Back");
        System.out.println(ANSI_BLUE + "==============================" + ANSI_RESET);
    }

    public static void printAll() {
        for (WashingMachine machine : Model.machines) {
            System.out.println(machine.ToString());
        }
    }
    public static void printEmpty() {
        for (WashingMachine machine : Model.machines){
            if (machine.linens.isEmpty()){
                System.out.println(machine.toString());
            }
        }
    }

    public static void send_text(String message) {
        System.out.println(message);
    }

    public static void send_info(String message) {
        System.out.println(ANSI_GREEN + "INFO" + " : " + message + ANSI_RESET);
    }

    public static void send_error(String message) {
        System.out.println(ANSI_RED + "ERROR" + " : " + message + ANSI_RESET);
    }

    public static void send_debug(String message) {
        System.out.println(ANSI_BLUE + "DEBUG" + " : " + message + ANSI_RESET);
    }

    public static void send_warning(String message) {
        System.out.println(ANSI_YELLOW + "WARNING" + " : " + message + ANSI_RESET);
    }

}

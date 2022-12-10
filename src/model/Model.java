package model;

import controller.Controller;
import view.View;

import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Model {
    static public String cuttentLogin;
    static public String currentPassword;
    static public Group currentGroup;
    public static String SAVE_PATH = "./saves/machinesSaves.txt";
    public static String USERS_FILE = "./saves/Users.txt";
    public static Logging logging;
    private static boolean debug;
    private static boolean autotests;

    private Properties props = new Properties();
    public static List<WashingMachine> machines = new ArrayList<WashingMachine>();
    public static Map<String, String> dataUsers = new HashMap<String, String>();
    public static Map<String, Group> configUsers = new HashMap<String, Group>();

    public Model(int choice) throws Exception {
        loadUsers();
        loadMachines();
        Autotests();
        regMenu(choice);
    }

    public static void login(String tmpLog, String tmpPass) throws IOException {
        if (dataUsers.containsKey(tmpLog) && dataUsers.containsValue(tmpPass)){
            cuttentLogin = tmpLog;
            currentPassword = tmpPass;
            currentGroup = configUsers.get(cuttentLogin);
            Logging.log("Log in user: " + cuttentLogin);
        } else if (!dataUsers.containsKey(tmpLog)) {
            System.out.println("Invalid login");
            System.out.println("Type yes if you want to create an account or no");
            String choice = Controller.inputString("");
            if (choice.equals("yes")){
                registration();
            }
            else{ regMenu(0); }
        }
        else {
            System.out.println("Invalid password, try again");
            login(tmpLog, Controller.inputString("Input password"));
        }
    }
    public static void registration() {
        try{
            cuttentLogin = Controller.inputString("Input your login\n");
            currentPassword = Controller.inputString("Input your password\n");
            currentGroup = Group.valueOf(Controller.inputString("Input your group\n"));
            dataUsers.put(cuttentLogin, currentPassword);
            configUsers.put(cuttentLogin, currentGroup);
            Logging.log("Created a user: " + cuttentLogin);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static void regMenu(int choice) {
        try {
            View.regMenu();
            choice = Controller.choice("Input number\n", 1, 3);
            if (choice == 1) {

                login(Controller.inputString("Input login\n"), Controller.inputString("Input password\n"));
            }
            else if (choice == 2){
                registration();
            }
            else{
                saveUsers();
                saveMachines();
                System.exit(0); }
            Logging.log("Model created");
            cross(choice);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static void cross(int choice) throws IOException {
        if (currentGroup.equals(Group.admin)){ adminMenu(choice); }
        else { usersMenu(choice); }
    }
    public static void adminMenu(int choice) throws IOException {
        if (Model.currentGroup.equals(Group.admin)){
            View.adminMenu();
            choice = Controller.choice("", 1, 5);
            switch (choice){
                case 1:
                    machinesMenu(choice);
                    break;
                case 2:
                    addMachine();
                    adminMenu(choice);
                    break;
                case 3:
                    Autotests();
                    break;
                case 4:
                    Debug();
                    break;
                case 5:
                    regMenu(choice);
                    break;
            }
        }
        else {
            View.userMenu();
            choice = Controller.choice("", 1, 3);
        }
    }
    public static void usersMenu(int choice) {
        View.userMenu();
        choice = Controller.choice("", 1, 3);
        switch (choice){
            case 1:
                addlinens();
                usersMenu(0);
                break;
            case 2:
                getLinensfromMachine();
                usersMenu(0);
                break;
            case 3:
                regMenu(0);
                break;
        }
    }
    public static void addlinens() {
        List<ColoredLinen> linens = new ArrayList<ColoredLinen>();
        boolean flag = false;
        int count = Controller.inputInt("Input number of linens\n");
        for (int i = 0; i < count; i++){
            linens.add(Controller.inputLinen());
        }
        for (WashingMachine machine : machines){
            if (machine.linens.isEmpty()){
                machine.Loads(linens);
                flag = true;
            }
        }
        if (!flag){
            System.out.println("All machines are loaded");
        }
    }
    public static void getLinensfromMachine() {
        List<ColoredLinen> linens = new ArrayList<ColoredLinen>();
        boolean flag = false;
        int count = Controller.inputInt("Input number of your linens\n");
        for (int i = 0; i < count; i++){
            linens.add(Controller.inputLinen());
        }
        for (WashingMachine machine : machines){
            if (machine.linens.equals(linens)){
                machine.linens = new ArrayList<ColoredLinen>();
                flag = true;
            }
        }
        if (!flag){
            System.out.println("You are trying get other people's linens!");
        }
    }
    public static void machinesMenu(int choice) throws IOException {
        View.machinesMenu();
        choice = Controller.choice("", 1, 3);
        switch (choice){
            case 1:
                View.printAll();
                machinesMenu(choice);
                break;
            case 2:
                View.printEmpty();
                machinesMenu(choice);
                break;
            case 3:
                if (currentGroup.equals(Group.admin)){ adminMenu(choice); }
                else { usersMenu(choice); }
        }
    }
    static void addMachine() {
        machines.add(Controller.inputWS());
    }
    static void saveUsers() {
        for (String login : dataUsers.keySet()) {
            try {
                FileWriter fw = new FileWriter(USERS_FILE);
                fw.append(login + "|" + dataUsers.get(login) + "|" + configUsers.get(login) + '\n');
                fw.flush();
                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    static void loadUsers() throws FileNotFoundException {
        FileReader fr = new FileReader(USERS_FILE);
        BufferedReader br = new BufferedReader(fr);
        try{
            String line;
            while ((line = br.readLine()) != null){
                String[] tmp = line.split("\\|");
                dataUsers.put(tmp[0], tmp[1]);
                configUsers.put(tmp[0], Group.valueOf(tmp[2]));
            }
            fr.close();
            Logging.log( "Load users");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static public void saveMachines() throws IOException {
        try {
            File saveFile = new File(SAVE_PATH);
            if (!saveFile.exists()) {
                saveFile.createNewFile();
            }
            PrintWriter printWriter = new PrintWriter(SAVE_PATH);
            for (WashingMachine machine : machines) {
                printWriter.println(machine.toShortString());
            }
            printWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void loadMachines() {
        try {
            String line;
            List<WashingMachine> ret = new ArrayList<WashingMachine>();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(SAVE_PATH));
            while ((line = bufferedReader.readLine()) != null) {
                WashingMachine tmp = new WashingMachine();
                String[] splitted = line.split("\\|");
                tmp.temperature = Integer.valueOf(splitted[0]);
                tmp.powdery = splitted[1];
                tmp.conditioner = splitted[2];
                machines.add(tmp);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void Debug() throws IOException {
        debug = true;
        Logging.log("Debug");
    }
    static void Autotests() throws IOException {
        ArrayList<Linen> linenArrayList = new ArrayList<Linen>();
        Map<String, Linen> linenHashMap = new HashMap<String, Linen>();
        Tests testCore = new Tests();
        autotests = true;

        long startTime = System.currentTimeMillis();
        testCore.testAddArrayList(10, "app.log.1", linenArrayList);
        long endTime = System.currentTimeMillis();
        System.out.println("1 | 10 добавлений     | " + (endTime - startTime) + " milliseconds");
        removeLinenList(10, linenArrayList);

//        startTime = System.currentTimeMillis();
//        testCore.testAddArrayList(100, "app.log.2", linenArrayList);
//        endTime = System.currentTimeMillis();
//        System.out.println("2 | 100 добавлений    | " + (endTime - startTime) + " milliseconds");
//        removeLinenList(100, linenArrayList);

//        startTime = System.currentTimeMillis();
//        testCore.testAddArrayList(1000, "app.log.3", linenArrayList);
//        endTime = System.currentTimeMillis();
//        System.out.println("3 | 1000 добавлений   | " + (endTime - startTime) + " milliseconds");
//        removeLinenList(1000, linenArrayList);
//
//        startTime = System.currentTimeMillis();
//        testCore.testAddArrayList(10000, "app.log.4", linenArrayList);
//        endTime = System.currentTimeMillis();
//        System.out.println("3 | 10000 добавлений   | " + (endTime - startTime) + " milliseconds");
//        removeLinenList(10000, linenArrayList);
//
        startTime = System.currentTimeMillis();
        testCore.testAddArrayList(100000, "app.log.5", linenArrayList);
        endTime = System.currentTimeMillis();
        System.out.println("3 | 100000 добавлений   | " + (endTime - startTime) + " milliseconds");
        removeLinenList(100000, linenArrayList);

        // ====================== HashMap ============================

//        startTime = System.currentTimeMillis();
//        testCore.testAddHashMap(10, "app.log.6", linenHashMap);
//        endTime = System.currentTimeMillis();
//        System.out.println("1 | 10 добавлений     | " + (endTime - startTime) + " milliseconds");
//        removeLinenMap(10, linenHashMap);
//
//        startTime = System.currentTimeMillis();
//        testCore.testAddHashMap(100, "app.log.7", linenHashMap);
//        endTime = System.currentTimeMillis();
//        System.out.println("2 | 100 добавлений    | " + (endTime - startTime) + " milliseconds");
//        removeLinenMap(100, linenHashMap);
//
//        startTime = System.currentTimeMillis();
//        testCore.testAddHashMap(1000, "app.log.8", linenHashMap);
//        endTime = System.currentTimeMillis();
//        System.out.println("3 | 1000 добавлений   | " + (endTime - startTime) + " milliseconds");
//        removeLinenMap(1000, linenHashMap);
//
//        startTime = System.currentTimeMillis();
//        testCore.testAddHashMap(10000, "app.log.9", linenHashMap);
//        endTime = System.currentTimeMillis();
//        System.out.println("4 | 10000 добавлений  | " + (endTime - startTime) + " milliseconds");
//        removeLinenMap(10000, linenHashMap);
//
        startTime = System.currentTimeMillis();
        testCore.testAddHashMap(100000, "app.log.10", linenHashMap);
        endTime = System.currentTimeMillis();
        System.out.println("5 | 100000 добавлений | " + (endTime - startTime) + " milliseconds");
        removeLinenMap(100000, linenHashMap);

        System.out.println("Тесты для HashMap успешно пройдены!");
        Logging.log("Tests for ArrayList were successfully passed!");
    }

    private static void removeLinenList(int count, ArrayList<Linen> linenArrayList) {
        Random random = new Random();
        String nums = "";
        String times = "";

        for (int i = 0; i < Math.round(count * 0.1); i++) {
            int num = random.nextInt(0, count - i - 1);
            long startTime = System.nanoTime();
            linenArrayList.remove(num);
            long time = System.nanoTime() - startTime;
            if (count == 100000) {
                try(FileWriter writer = new FileWriter("11.txt", true))
                {
                    writer.write(String.valueOf(num) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
                try(FileWriter writer = new FileWriter("12.txt", true))
                {
                    writer.write(String.valueOf(time) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
    private static void removeLinenMap(int count, Map<String, Linen> linenHashMap) {
        Random random = new Random();
        for (int i = 0; i < Math.round(count * 0.1); i++) {
            int num = random.nextInt(0, count - i - 1);
            long startTime = System.nanoTime();
            linenHashMap.remove(num);
            long time = System.nanoTime() - startTime;
            if (count == 100000) {
                try(FileWriter writer = new FileWriter("13.txt", true))
                {
                    writer.write(String.valueOf(num) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
                try(FileWriter writer = new FileWriter("14.txt", true))
                {
                    writer.write(String.valueOf(time) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
}

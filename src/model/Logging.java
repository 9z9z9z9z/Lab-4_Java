package model;

import java.io.*;
import java.util.*;
import java.util.logging.*;

public class Logging {
    private static ArrayList<Exception> errList = new ArrayList<Exception>();
    private static Logger logger = null;

    public Logging(){
        errList = new ArrayList<Exception>();
        try {
            LogManager.getLogManager().readConfiguration(Logging.class.getResourceAsStream("logging.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger = Logger.getLogger(Logging.class.getName());
    }
    public static void log(String msg) throws IOException {
        logger = Logger.getLogger(Level.INFO.getClass().getName());
        logger.setUseParentHandlers(false);
        FileHandler fh;
        fh = new FileHandler("app.log", true);
        try {
            logger.addHandler(fh);
            fh.setFormatter(new SimpleFormatter() {
                private static final String format = "[%1$tF %1$tT] [%2$-7s] %3$s %n";

                @Override
                public synchronized String format(LogRecord lr) {
                    return String.format(Level.INFO.getClass().getName().toUpperCase() + " | " + format,
                            new Date(lr.getMillis()),
                            lr.getLevel().getLocalizedName(),
                            lr.getMessage()
                    );
                }
            });

        } catch (SecurityException e) {
            e.printStackTrace();
        }

        logger.log(Level.INFO, "\n\t->"+msg);
        fh.close();
    }
    public static void log(String file, String msg) {
        logger.setUseParentHandlers(false);
        FileHandler fh = null;
        try {
            fh = new FileHandler(file, true);
            logger.addHandler(fh);
            fh.setFormatter(new SimpleFormatter() {
                private static final String format = "[%1$tF %1$tT] [%2$-7s] %3$s %n";
                @Override
                public synchronized String format(LogRecord lr) {
                    return String.format(format, new Date(lr.getMillis()),
                            lr.getLevel().getLocalizedName(), lr.getMessage());
                }
            });
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.log(Level.INFO, "\nADD\t>>\t"+msg);
        fh.close();
    }
    public static void log_test(String file, String msg) throws IOException {
        FileWriter fw = new FileWriter(file, true);
        fw.append(msg + '\n');
        fw.flush();
    }

    public int addErr(Exception e) {
        errList.add(e);
        return errList.size();
    }

    public int addErrWithLog(Exception e) {
        errList.add(e);
        //Код записи сообщения в лог
        logger.log(Level.FINE, e.getMessage());
        return errList.size();
    }

    public int getErrCount() {
        return errList.size();
    }

    public void showErrText(Exception e) {
        System.err.println(e.getMessage());
    }

    public String makeErr(Exception e) {
        addErr(e);
        addErrWithLog(e);
        return new Exception(e).getMessage();
    }
}

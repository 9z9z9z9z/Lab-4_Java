package model;

import controller.Controller;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Tests {
    public void testAddArrayList(int count, String file, List<Linen> linens) throws IOException {
        Linen tmp = Controller.generateLinen();
        for (int i = 0; i < count; i++) {
            long startTime = System.nanoTime();
            //System.out.println(Math.round(i * 100 / count) + "%");
            linens.add(tmp);
            long endTime = System.nanoTime();
            Logging.log_test(file, String.valueOf(endTime - startTime));
        }
        System.gc();
    }
    public void testAddHashMap(int count, String file, Map<String, Linen> linenHashMap) throws IOException {
        Linen testLinen = Controller.generateLinen();
        for (int i = 0; i < count; i++) {
            long startTime = System.nanoTime();
            linenHashMap.put(String.valueOf(i), testLinen);
            //System.out.println(Math.round(i * 100 / count) + "%");
            long endTime = System.nanoTime();
            Logging.log_test(file, String.valueOf(endTime - startTime));
        }
        System.gc();
    }
}

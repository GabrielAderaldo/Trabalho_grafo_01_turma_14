package tests;

import app.FacebookGraph;
import utils.DataExporter;
import java.util.*;

public class DataExporterTest {
    public static void main(String[] args) {
        // Testa se o utilitário consegue gravar os tipos básicos
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("v", 10);
        DataExporter.toJSON("test_util", metrics);

        List<String> rows = new ArrayList<>();
        rows.add("0,5");
        DataExporter.toCSV("test_util", "V,G", rows);

        System.out.println(">>> DataExporterTest PASSED!");
    }
}

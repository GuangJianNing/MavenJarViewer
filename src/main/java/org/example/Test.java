package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Test {
    @org.junit.Test
    public void test(){
        Object[][] data=null;
        System.out.println(data);
    }
    @org.junit.Test
    public void testData(){
        DataDao dataDao = new DataDao();
        String path = "D:\\maven\\repository";
        File file = dataDao.getFile(path);

        long time1 = System.currentTimeMillis();
        List<String> jarFileNames = new ArrayList<>();
        jarFileNames = dataDao.getJar2(jarFileNames, file);
        long time2 = System.currentTimeMillis();
        System.out.println(time2 - time1 + "ms");
//      搜索

//        jarFileNames.parallelStream().filter(string -> string.contains("mysql")).forEach(System.out::println);
        for (String s : jarFileNames) {
            System.out.println(s);
        }
    }
}

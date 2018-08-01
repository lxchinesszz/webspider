package org.spider.util;

import java.io.*;

/**
 * @author liuxin
 * @version Id: FileTools.java, v 0.1 2018/7/30 下午2:47
 */
public class FileTools {
    public static String fileContent(String filePath) {
        File file = new File(filePath);
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            while ((s = br.readLine()) != null) {
                result.append(System.lineSeparator() + s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}

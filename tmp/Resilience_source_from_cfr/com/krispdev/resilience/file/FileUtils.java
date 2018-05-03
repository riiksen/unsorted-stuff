/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.file;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.logger.ResilienceLogger;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;

public final class FileUtils {
    public static final int CASE_SENSITIVE = 0;
    public static final int CASE_INSENSITIVE = 1;

    public static void print(ArrayList<String> lines, File dir, boolean inform) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(dir));
            for (String s : lines) {
                bw.write(String.valueOf(s) + "\r\n");
            }
            bw.close();
            if (inform) {
                Resilience.getInstance().getLogger().info("Printed data to " + dir.getName());
            }
        }
        catch (IOException e) {
            if (inform) {
                Resilience.getInstance().getLogger().warning("Error writing to " + dir.getName());
            }
            e.printStackTrace();
        }
    }

    public static ArrayList<String> read(File dir, boolean inform) {
        ArrayList<String> lines = new ArrayList<String>();
        try {
            String curLine;
            BufferedReader br = new BufferedReader(new FileReader(dir));
            while ((curLine = br.readLine()) != null && !curLine.startsWith("#")) {
                lines.add(curLine);
            }
            br.close();
            if (inform) {
                Resilience.getInstance().getLogger().info("Read data from file " + dir.getName());
            }
        }
        catch (IOException e) {
            if (inform) {
                Resilience.getInstance().getLogger().warning("Error reading file " + dir.getName());
            }
            e.printStackTrace();
        }
        return lines;
    }

    public static void addToFile(File file, String text) {
        ArrayList<String> lines = FileUtils.read(file, false);
        ArrayList<String> print = new ArrayList<String>();
        if (lines == null) {
            print.add(text);
            FileUtils.print(print, file, false);
        } else {
            for (String s : lines) {
                print.add(s);
            }
            print.add(text);
            FileUtils.print(print, file, false);
        }
    }

    public static boolean containsString(File file, String string, int mode) {
        ArrayList<String> lines = FileUtils.read(file, false);
        for (String s : lines) {
            if (!(mode == 0 ? s.equals(string) : mode == 1 && s.equalsIgnoreCase(string))) continue;
            return true;
        }
        return false;
    }
}


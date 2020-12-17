package spendingtracker;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DataWrite {
    FileWriter fw;
    PrintWriter pw;
    public DataWrite(String file, Boolean b) throws IOException {
        fw = new FileWriter(file, b);
        pw = new PrintWriter(fw);

    }
    public void writeUser(String s) {
        pw.println(s);
    }

    public void flush() {
        pw.flush();
    }
    public void close() {
        pw.close();
    }
}

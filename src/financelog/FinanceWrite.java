package financelog;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FinanceWrite {
    FileWriter fw;
    PrintWriter pw;
    public FinanceWrite(String file, Boolean b) throws IOException {
        fw = new FileWriter(file, b);
        pw = new PrintWriter(fw);

    }
    public void writeUser(String name) {
        pw.println(name);
    }

    public void flush() {
        pw.flush();
    }
    public void close() {
        pw.close();
    }
}

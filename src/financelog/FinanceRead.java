package financelog;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FinanceRead {
    Scanner scan_data;
    LinkedListQueue user_data = new LinkedListQueue();
    public FinanceRead(String file) throws FileNotFoundException {
        Scanner scan_data = new Scanner(new File(file));
        this.scan_data = scan_data;

    }
    public void readLine(int next_line_iter) {
        for (int i = 0; i < next_line_iter; i++) {
            user_data.enqueue(scan_data.nextLine());
        }
    }

    public void readAll() {
        while(scan_data.hasNext())
        user_data.enqueue(scan_data.nextLine());
    }
}

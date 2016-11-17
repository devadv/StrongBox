package strongbox.test;

import strongbox.model.RecordManager;

public class MainTest{
	
	
	public static void main(String[] args) {
        RecordManager test = new RecordManager();
        test.readFile();
        test.makeRecords();
        System.out.println("" + test.recordListSize()); // 2 expected
	}
	
}
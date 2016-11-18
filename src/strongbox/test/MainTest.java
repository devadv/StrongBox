package strongbox.test;

import strongbox.model.TextFileIO;

public class MainTest{
	
	
	public static void main(String[] args) {
        TextFileIO test = new TextFileIO();
        test.readFile();
        test.makeRecords();
        System.out.println("" + test.recordListSize()); // 2 expected
	}
	
}
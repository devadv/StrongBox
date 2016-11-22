package strongbox.model;

import java.util.ArrayList;
import java.util.HashSet;

public class Model implements iModel {

	@Override
	public void writeRecordsToFile(ArrayList<Record> records) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addRecord(String title, String address, String username,
			String password, String Info, String Folder, String Note) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readRecordsFromFile() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Record> getRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getRecords(String foldername) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getRecord(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashSet<String> getFolders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Record record) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMasterPassword(String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPassfraseEncryption(String passfrase) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Record> search(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

}

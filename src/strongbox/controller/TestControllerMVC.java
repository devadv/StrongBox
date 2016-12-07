package strongbox.controller;

import strongbox.model.Model;
import strongbox.model.Record;
import strongbox.view.GUI;

import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @version 04-12-2016
 */
public class TestControllerMVC {

	private Model model;
	private GUI view;
	
    private DefaultListModel<String> folderModel = new DefaultListModel<>();
    private DefaultListModel<String> recordModel = new DefaultListModel<>();
	
    /**
     * Constructor
     */
	public TestControllerMVC() {
		
		model = new Model();
		view = new GUI();
		createTestRecords();
		makeFolderList();
		makeRecordList();

	}

	/**
	 * Create or update folderList and define it's behavior.
	 */
    public void makeFolderList() {
    	
        for (String folderName: model.getFolders()) {
            folderModel.addElement(folderName);
        }

    	view.getFolderList().setModel(folderModel);

        view.getFolderList().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        recordModel.clear();
                    	for (String recordTitle: model.getTitlesByFolder(view.getFolderList().getSelectedValue())) {
                            recordModel.addElement(recordTitle);
                        }
                    }
                }
            }
        );
    }
    
    /**
     * Define behavior of the recordList.
     */
    public void makeRecordList() {
    	
    	view.getRecordList().setModel(recordModel);
    	
    	view.getRecordList().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
    		    public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                    	Record record = model.getRecord(view.getRecordList().getSelectedValue());
                    	String[] fields = getRecordFields(record);
                    	for (int i = 0; i < 6; i++) {
                    		view.getFields().get(i).setText(fields[i]);
                    	}                    	
                    }
                }
            }
        );
    }
    
    /**
     * Create and return an array of strings representing the values of the 
     * fields from a record.
     * @param record   The record to get the fields from.
     * @return An array with the values of the fields.
     */
    public String[] getRecordFields(Record record) {
    	return new String[] {record.getTitle(), record.getAddress(), 
    			record.getUserName(), record.getPassword(), 
    			record.getFolder(), record.getNote()};
    }
    
	public void createTestRecords() {
		model.createNewRecord("Telfort", "telfort.nl", "ikke", "1234567", "Providers", "telefoon en internet");
		model.createNewRecord("KPN", "kpn.nl", "gebruiker", "123kpn", "Providers", "mobiel");
		model.createNewRecord("Bol", "bol.com", "mij", "krentebol", "Webwinkels", "groot aanbod boeken");
		model.createNewRecord("GitHub", "github.com", "OctoPussy", "pwGood", "Programmeren", "versie-control en teamwork");
	}
	
	public static void main(String[] args) {

		TestControllerMVC tosti = new TestControllerMVC();

	}
}
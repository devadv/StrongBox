package strongbox.controller;

import strongbox.model.Model;
import strongbox.model.Record;
import strongbox.encryption.Encryption;
import strongbox.view.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.jasypt.util.text.StrongTextEncryptor;

/**
 * @version 12-12-2016
 */
public class TestControllerMVC {

	private Model model;
	private GUI view;
	
	private Record record; // The currently selected or last selected record.
	
    private DefaultListModel<String> folderData = new DefaultListModel<>();
    private DefaultListModel<String> recordData = new DefaultListModel<>();
	
    /**
     * Constructor
     */
	public TestControllerMVC() {
			
		model = new Model();
		model.setMasterPassword("12345"); // Password from login controller
		model.readProperties();
		StrongTextEncryptor enc = new StrongTextEncryptor();
		enc.setPassword(model.getMasterpassword());
		Encryption encryption = new Encryption(enc.decrypt(model.getPassphrase()));
		model.readRecordsFromFile();
		
		view = new GUI();
		
		//createTestRecords();
		
		initializeFolderData();
		
    	view.getFolderView().setModel(folderData);
    	view.getRecordView().setModel(recordData);
		
		addFolderListener();
		addRecordListener();
		
		addRecordCreationListener();
		addSaveListener();
		addDeleteListener();
		addSearchListener();

	}

	/**
	 * Initialize (or update) the folderData ListModel by retrieving each unique
	 * folder name from the model class and adding it to the folders' ListModel.
	 */
	public void initializeFolderData() {
		folderData.clear();
		for (String folderName: model.getFolders()) {
			folderData.addElement(folderName);
		}
	}
	
	/**
	 * Add the ListSelectionListener to the folderView and define it's behavior.
	 */
    public void addFolderListener() {

        view.getFolderView().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                    	recordData.clear();
                    	for (String recordTitle: model.getTitlesByFolder(view.getFolderView().getSelectedValue())) {
                    		recordData.addElement(recordTitle);
                        }
                    }
                }
        }
        );
    }
    
	/**
	 * Add the ListSelectionListener to the recordView and define it's behavior.
	 */
    public void addRecordListener() {

    	view.getRecordView().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
    		public void valueChanged(ListSelectionEvent e) {
    			if (!e.getValueIsAdjusting()) {
    				try {
    					record = model.getRecord(view.getRecordView().getSelectedValue());
    					String[] fields = model.getRecordFields(record);
    					for (int i = 0; i < 6; i++) {
    						view.getFields().get(i).setText(fields[i]);
    					}
    				}
    				catch (NullPointerException exc) {
    					for (int i = 0; i < 6; i++) {
    						view.getFields().get(i).setText("No record selected");
    					}
    				}
    			}
    		}
    	}
    	);
    }

    /**
     * Add an ActionListener to the 'Create new record' button.
     */
    public void addRecordCreationListener() {
    	view.getNewRecordButton().addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			view.showMessageDialog("Please enter the details of the new " + 
    					"record in the textfields to the right \n" +
    					"A new folder will be created if needed.");
    			view.getFolderView().setEnabled(false);
    			view.getRecordView().setEnabled(false);
    			view.getSearchBox().setEnabled(false);
    			ArrayList<JTextField> fields = view.getFields();
    			for (JTextField field: fields) {
    				field.setEditable(true);
    				field.setText("");
       			}
    			fields.get(0).grabFocus();
    		}
    	}
    	);
    }
    
    /**
     * Add an ActionListener to the 'Save' button so a new record can be saved
     * or to save changes to an existing record.
     */
    // TODO HIER GEBLEVEN (12-12-2016) NOG NIET VOLLEDIG AF EN GETEST
    public void addSaveListener() {
    	view.getSaveButton().addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			String[] fieldValues = new String[6];
    			for (int i = 0; i < fieldValues.length; i++) {
    				fieldValues[i] = view.getFields().get(i).getText();
    			}
			 	String titleFromField = fieldValues[0].trim().toLowerCase();
    			
    			boolean existingRecord = false;
    			
    			for (Record rec: model.getRecordList()) {
    			 	String recordTitle = rec.getTitle().trim().toLowerCase();
    				if (recordTitle.equals(titleFromField)) {
    					existingRecord = true;
    				}
    			}
    			
    			if (existingRecord) {
    				model.setRecordFields(record, fieldValues);
    			}
    			else { // New record
        			model.createNewRecord(fieldValues[0], fieldValues[1], 
        					fieldValues[2], fieldValues[3], fieldValues[4], 
        					fieldValues[5]);
    			}
    			
    			for (JTextField field: view.getFields()) {
    				field.setEditable(false);
       			}
    			view.getFolderView().setEnabled(true);
    			view.getRecordView().setEnabled(true);
    			view.getSearchBox().setEnabled(true);
    			
    			initializeFolderData();
    		}
    	}
    	);
    }
    
    /**
     * Add an ActionListener to the 'delete' button so selected records can
     * be deleted (ask user for confirmation) from the records-list.
     */
    public void addDeleteListener() {
    	view.getDeleteButton().addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			if (view.getRecordView().getSelectedValue() != null) {
    				String title = view.getRecordView().getSelectedValue();
    				if (view.showConfirmDialog("Are you sure you want to" + 
    						" delete the following record: " + title + " ?")) {
    					record = model.getRecord(title);
    					String folder = record.getFolder();
    					model.delete(record);
    					initializeFolderData();
    					recordData.clear();
    					for (String recordTitle: model.getTitlesByFolder(folder)) {
    						recordData.addElement(recordTitle);
    					}
    					if (folderData.indexOf(folder) != -1) {
    						view.getFolderView().setSelectedIndex(folderData.indexOf(folder));
    					}
    				}
    			}
    			else {
    				view.showMessageDialog("No record selected");
    			}
    		}
    	}
    	);
    }
    
    /**
     * Add a DocumentListener to the search field and define it's behavior.
     */
    public void addSearchListener() {
    	final PlainDocument doc = new PlainDocument();
    	view.getSearchBox().setDocument(doc);
    	doc.addDocumentListener(new DocumentListener() {
    		public void changedUpdate(DocumentEvent e) {
    			recordData.clear();
    			for (String recordTitle: model.search(getDocumentText(doc))) {
    				recordData.addElement(recordTitle);
    			}
    		}
    		public void insertUpdate(DocumentEvent e) {
    			recordData.clear();
    			for (String recordTitle: model.search(getDocumentText(doc))) {
    				recordData.addElement(recordTitle);
    			}
    		}
    		public void removeUpdate(DocumentEvent e) {
    			recordData.clear();
    			for (String recordTitle: model.search(getDocumentText(doc))) {
    				recordData.addElement(recordTitle);
    			}
    		}
    	}
    	);
    }
    
    /**
     * Fetches the text contained within a document.
     * @param doc  The document to get the text from.
     * @throws BadLocationException if a portion of the given range was not 
     *         a valid part of the document.
     */
    private String getDocumentText(PlainDocument doc) {
		String s = "";
		try {
			s = doc.getText(0, doc.getLength());
		}
		catch (BadLocationException exc) {
			exc.printStackTrace();
		}
		return s;
    }
    
	public void createTestRecords() {
		model.createNewRecord("Telfort", "telfort.nl", "ikke", "1234567", "Providers", "telefoon en internet");
		model.createNewRecord("KPN", "kpn.nl", "gebruiker", "123kpn", "Providers", "mobiel");
		model.createNewRecord("Bol", "bol.com", "mij", "krenteBOL", "Webwinkels", "groot aanbod boeken");
		model.createNewRecord("GitHub", "github.com", "octopussy", "pwGood", "Programmeren", "versie-control en teamwork");
	}
	
	public static void main(String[] args) {

		TestControllerMVC tosti = new TestControllerMVC();

	}
}
package strongbox.controller;

import strongbox.model.Model;
import strongbox.model.Record;
import strongbox.encryption.Encryption;
import strongbox.view.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.jasypt.util.text.StrongTextEncryptor;

/**
 * @version 16-12-2016
 */
public class TestControllerMVC {

	private Model model;
	private GUI view;
	
	private Record record; // The currently selected or last selected record.
	private boolean edit = false; // True if we are editing an existing record.
	
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

		addSearchListener();
		
		addRecordCreationListener();
		addEditListener();
		addCancelListener();
		addSaveListener();
		addDeleteListener();
		addDeleteAllListener();
		
		testButtonPrintList();

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
    	view.getButton(0).addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			view.showMessageDialog("Please enter the details of the new " + 
    					"record in the textfields to the right. \n" +
    					"A new folder will be created if needed. " +
    					"Click \"Save\" once you are done.");
    			edit = false;
    			view.getFolderView().setEnabled(false);
    			view.getRecordView().setEnabled(false);
    			view.getSearchBox().setEnabled(false);
    			for (JTextField field: view.getFields()) {
    				field.setEditable(true);
    				field.setText("");
       			}
    			view.getFields().get(0).grabFocus();
    		}
    	}
    	);
    }
    
    /**
     * Add an ActionListener to the 'Edit' button.
     */
    public void addEditListener() {
    	view.getButton(1).addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			if (view.getRecordView().getSelectedValue() != null) {
    				view.showMessageDialog("Please enter the changes you wish " + 
    						"to make in the textfields to the right. \n" +
    						"A new folder will be created if needed. " +
    						"Click \"Save\" once you are done.");
    				edit = true;
    				view.getFolderView().setEnabled(false);
    				view.getRecordView().setEnabled(false);
    				view.getSearchBox().setEnabled(false);
    				for (JTextField field: view.getFields()) {
    					field.setEditable(true);
    				}
    				view.getFields().get(0).grabFocus();
    			}
        		else {
        			view.showMessageDialog("No record selected");
        		}
    		}
    	}
        );
    }
    
    /**
     * Add an ActionListener to the 'Cancel' button so changes being made to an
     * existing record can be discarded or to cancel the creation of a new
     * record which is about to be made.
     */
    public void addCancelListener() {
    	view.getButton(2).addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			if (view.showConfirmDialog("Discard changes?")) {
    				
        			for (JTextField field: view.getFields()) {
        				field.setEditable(false);
           			}        			
        			view.getFolderView().setEnabled(true);
        			view.getRecordView().setEnabled(true);
        			view.getSearchBox().setEnabled(true);
        			
        			view.getRecordView().setSelectedValue(record.getTitle(), true);
					String[] fields = model.getRecordFields(record);
					for (int i = 0; i < 6; i++) {
						view.getFields().get(i).setText(fields[i]);
					}
    			}
    			else {
    				// Do nothing
    			}
    		}
    	}
    	);
    }
    
    /**
     * Add an ActionListener to the 'Save' button so a new record can be saved
     * or to save changes to an existing record.
     */
    public void addSaveListener() {
    	view.getButton(3).addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			try {
    				String[] fieldValues = new String[6];
    				for (int i = 0; i < fieldValues.length; i++) {
    					fieldValues[i] = view.getFields().get(i).getText();
    				}

    				if (edit) { // This is an existing record
    					model.setRecordFields(record, fieldValues);
    				}
    				else {  // New record
    					model.createNewRecord(fieldValues[0], fieldValues[1], 
    							fieldValues[2], fieldValues[3], fieldValues[4], 
    							fieldValues[5]);
    				}

    				view.showMessageDialog("Record Saved");

    				for (JTextField field: view.getFields()) {
    					field.setEditable(false);
    				}
    				view.getFolderView().setEnabled(true);
    				view.getRecordView().setEnabled(true);
    				view.getSearchBox().setEnabled(true);

    				initializeFolderData();
    				view.getFolderView().setSelectedValue(fieldValues[4], true);
    				view.getRecordView().setSelectedValue(fieldValues[0], true);
    			}
    			catch (IllegalArgumentException exc) {
    				view.showMessageDialog("There was a problem with the record's" +
    						" properties you entered in the textfields. \n" +
    						"Most likely cause of the problem: You entered a " +
    						"title that is already in use \n" + 
    						"or you left one of the fields blank (you can " +
    						"leave \"note\" blank).", "Illegal Arguments", JOptionPane.ERROR_MESSAGE);
    			}
    		}
    	}
    	);
    }
    
    /**
     * Add an ActionListener to the 'delete' button so selected records can
     * be deleted (ask user for confirmation) from the records-list.
     */
    public void addDeleteListener() {
    	view.getButton(4).addActionListener(new ActionListener() {
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
     * Add an ActionListener to the 'delete ALL records' button so all records 
     * can be deleted with one click on a button (but ask user for confirmation)
     */
    public void addDeleteAllListener() {
    	view.getButton(5).addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
				if (view.showConfirmDialog("Are you sure you want to" + 
						" delete ALL records?")) {
					model.deleteAll();
					initializeFolderData();
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
    
    public void testButtonPrintList() {
    	view.getButton(8).addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    	    	for (Record record: model.getRecordList()) {
    	        	System.out.println(record.toString());
    	        	System.out.println();
    	        	}
    		}
    	}
    	);
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
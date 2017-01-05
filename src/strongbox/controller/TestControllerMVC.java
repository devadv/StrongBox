package strongbox.controller;

import strongbox.login.PropertiesModel;
import strongbox.model.Model;
import strongbox.model.Record;
import strongbox.encryption.Encryption;
import strongbox.view.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.jasypt.util.text.StrongTextEncryptor;

/**
 * @version 03-01-2017
 */
public class TestControllerMVC {

	private Model model;
	private GUI view;
	
    private DefaultListModel<String> folderData = new DefaultListModel<>();
    private DefaultListModel<String> recordData = new DefaultListModel<>();
	
	private Record record; // The currently selected or last selected record.
	private boolean edit = false; // True if we are editing an existing record.
	
	private boolean showPassword = false;
	private char echoChar;
    
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
		
		addEyeButtonListener();
		addDiceButtonListener();
		
		addDiceButtonListener();
		
		addInfoButtonListener();
		
		echoChar = ((JPasswordField)view.getFields().get(3)).getEchoChar();

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
    						view.setDarkGrayColor(view.getFields().get(i));
    						view.getFields().get(i).setText(fields[i]);
    					}
    				}
    				catch (NullPointerException exc) {
    					for (int i = 0; i < 6; i++) {
    						view.setDullGrayColor(view.getFields().get(i));
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
    	view.getIconLabel(0).addMouseListener(new MouseListener() {
    		public void mouseClicked(MouseEvent e) {
    			view.showMessageDialog("Please enter the details of the new " + 
    					"record in the textfields to the right. \n" +
    					"A new folder will be created if needed. " +
    					"Click \"Save\" once you are done.");
    			edit = false;
    			view.getFolderView().setEnabled(false);
    			view.getRecordView().setEnabled(false);
    			view.getSearchBox().setEnabled(false);
    			view.setDullGrayColor(view.getSearchLabel());
    			for (JTextField field: view.getFields()) {
    				field.setEditable(true);   
    				field.setText("");
    				view.setDarkGrayColor(field);
       			}
    			view.getFields().get(0).grabFocus();
    		}
    		public void mouseEntered(MouseEvent e) {
    			view.setDullGrayColor(view.getIconLabelTexts().get(0));
    		}
    		public void mouseExited(MouseEvent e) {
    			view.setDarkGrayColor(view.getIconLabelTexts().get(0));
    		}
    		public void mousePressed(MouseEvent e) {

    		}
    		public void mouseReleased(MouseEvent e) {

    		}
    	}
    	);
    }
    
    /**
     * Add an ActionListener to the 'Edit' button.
     */
    public void addEditListener() {
    	view.getIconLabel(1).addMouseListener(new MouseListener() {
    		public void mouseClicked(MouseEvent e) {
    			if (view.getRecordView().getSelectedValue() != null) {
    				view.showMessageDialog("Please enter the changes you wish " + 
    						"to make in the textfields to the right. \n" +
    						"A new folder will be created if needed. " +
    						"Click \"Save\" once you are done.");
    				edit = true;
    				view.getFolderView().setEnabled(false);
    				view.getRecordView().setEnabled(false);
    				view.getSearchBox().setEnabled(false);
    				view.setDullGrayColor(view.getSearchLabel());
    				for (JTextField field: view.getFields()) {
    					field.setEditable(true);
    				}
    				view.getFields().get(0).grabFocus();
    			}
        		else {
        			view.showMessageDialog("No record selected");
        		}
    		}
    		public void mouseEntered(MouseEvent e) {
    			view.setDullGrayColor(view.getIconLabelTexts().get(1));
    		}
    		public void mouseExited(MouseEvent e) {
    			view.setDarkGrayColor(view.getIconLabelTexts().get(1));
    		}
    		public void mousePressed(MouseEvent e) {

    		}
    		public void mouseReleased(MouseEvent e) {

    		}
    	}
        );
    }
    
    /**
     * Add an ActionListener to the 'Save' button so a new record can be saved
     * or to save changes to an existing record.
     * @throws IllegalArgumentException if user typed comma's into one of the fields.
     */
    public void addSaveListener() {
    	view.getButton(0).addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {

    			boolean commaFound = false;

    			String[] fieldValues = new String[6];
    			for (int i = 0; i < fieldValues.length; i++) {
    				fieldValues[i] = view.getFields().get(i).getText();
    				if (fieldValues[i].contains(",")) {
    					commaFound = true;
    				}
    			}

    			try {
    				if (commaFound) {
    					throw new IllegalArgumentException();
    				}

    				if (edit) { // This is an existing record
    					model.setRecordFields(record, fieldValues);
    				}
    				else {  // New record
    					model.createNewRecord(fieldValues[0], fieldValues[1], 
    							fieldValues[2], fieldValues[3], fieldValues[4], 
    							fieldValues[5]);
    				}

    				// Save to data.csv here

    				view.showMessageDialog("Record Saved");

    				for (JTextField field: view.getFields()) {
    					field.setEditable(false);
    				}
    				view.getFolderView().setEnabled(true);
    				view.getRecordView().setEnabled(true);
    				view.getSearchBox().setEnabled(true);
    				view.setDarkGrayColor(view.getSearchLabel());

    				initializeFolderData();
    				view.getFolderView().setSelectedValue(fieldValues[4], true);
    				view.getRecordView().setSelectedValue(fieldValues[0], true);
    			}
    			catch (IllegalArgumentException exc) {
    				view.showMessageDialog("There was a problem with the data" +
    						" you entered. \n\n" + "Most likely cause of the " + 
    						"problem: \n\n-You entered a " +
    						"title that is already in use (when making a new record)\n" + 
    						"-You left one of the fields blank (you may opt to " +
    						"leave \"note\" blank)\n" +
    						"-You used comma's", "Illegal Arguments", JOptionPane.ERROR_MESSAGE);
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
    	view.getButton(1).addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			if (view.showConfirmDialog("Discard changes?")) {

    				for (JTextField field: view.getFields()) {
    					field.setEditable(false);
    				}        			
    				view.getFolderView().setEnabled(true);
    				view.getRecordView().setEnabled(true);
    				view.getSearchBox().setEnabled(true);
    				view.setDarkGrayColor(view.getSearchLabel());

    				try {
    					view.getRecordView().setSelectedValue(record.getTitle(), true);
    					String[] fields = model.getRecordFields(record);
    					for (int i = 0; i < 6; i++) {
    						view.getFields().get(i).setText(fields[i]);
    					}
    				}
    				catch (NullPointerException exc) {
    					for (int i = 0; i < 6; i++) {
    						view.setDullGrayColor(view.getFields().get(i));
    						view.getFields().get(i).setText("No record selected");
    					}
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
     * Add an ActionListener to the 'delete' button so selected records can
     * be deleted (ask user for confirmation) from the records-list.
     */
    public void addDeleteListener() {
    	view.getIconLabel(2).addMouseListener(new MouseListener() {
    		public void mouseClicked(MouseEvent e) {
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
    		public void mouseEntered(MouseEvent e) {
    			view.setDullGrayColor(view.getIconLabelTexts().get(2));
    		}
    		public void mouseExited(MouseEvent e) {
    			view.setDarkGrayColor(view.getIconLabelTexts().get(2));
    		}
    		public void mousePressed(MouseEvent e) {

    		}
    		public void mouseReleased(MouseEvent e) {

    		}
    	}
    	);
    }
    
    /**
     * Add an ActionListener to the 'delete ALL records' button so all records 
     * can be deleted with one click on a button (but ask user for confirmation)
     */
    public void addDeleteAllListener() {
    	view.getIconLabel(3).addMouseListener(new MouseListener() {
    		public void mouseClicked(MouseEvent e) {
				if (view.showConfirmDialog("Are you sure you want to" + 
						" delete ALL records?")) {
					model.deleteAll();
					initializeFolderData();
				}
    		}
    		public void mouseEntered(MouseEvent e) {
    			view.setDullGrayColor(view.getIconLabelTexts().get(3));
    		}
    		public void mouseExited(MouseEvent e) {
    			view.setDarkGrayColor(view.getIconLabelTexts().get(3));
    		}
    		public void mousePressed(MouseEvent e) {

    		}
    		public void mouseReleased(MouseEvent e) {

    		}
    	}
    	);
    }
    
    /**
     * Add a MouseListener to the 'Eye' button so the user can show or hide
     * the password. Bullet symbols are used to mask the password.
     */
    public void addEyeButtonListener() {
    	view.getIconLabel(4).addMouseListener(new MouseListener() {
    		public void mouseClicked(MouseEvent e) {
    			view.getIconLabel(4).setIcon(view.getIcon(7));
				JPasswordField pwField = (JPasswordField)view.getFields().get(3);
				if (!showPassword) {
					pwField.setEchoChar((char)0);
					showPassword = true;
				}
				else {
					pwField.setEchoChar(echoChar);
					showPassword = false;
				}
    		}
    		public void mouseEntered(MouseEvent e) {
    			view.getIconLabel(4).setIcon(view.getIcon(8));
    		}
    		public void mouseExited(MouseEvent e) {
    			view.getIconLabel(4).setIcon(view.getIcon(7));
    		}
    		public void mousePressed(MouseEvent e) {
    			view.getIconLabel(4).setIcon(view.getIcon(8));
    		}
    		public void mouseReleased(MouseEvent e) {
    			view.getIconLabel(4).setIcon(view.getIcon(7));
    		}
    	}
    	);
    }
    
    /**
     * Add a MouseListener to the 'Dice' button so a random password can be
     * generated. Also show the slider which can be used to set the length.
     */
    
    public void addDiceButtonListener() {
    	view.getIconLabel(5).addMouseListener(new MouseListener() {
    		public void mouseClicked(MouseEvent e) {
    			view.getIconLabel(5).setIcon(view.getIcon(9));
    			JPasswordField pwField = (JPasswordField)view.getFields().get(3);
    			//  static method generatePassphrase form PropertiesModel
    			String passwd = PropertiesModel.generatePassphrase(12);
    			pwField.setText(passwd);
    			view.showDialog("Just put 12 random characters in password field");
    		}
    		public void mouseEntered(MouseEvent e) {
    			view.getIconLabel(5).setIcon(view.getIcon(10));
    		}
    		public void mouseExited(MouseEvent e) {
    			view.getIconLabel(5).setIcon(view.getIcon(9));
    		}
    		public void mousePressed(MouseEvent e) {
    			view.getIconLabel(5).setIcon(view.getIcon(10));
    		}
    		public void mouseReleased(MouseEvent e) {
    			view.getIconLabel(5).setIcon(view.getIcon(9));
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
    
    /**
     * Listener for 'Info' button.
     */
    public void addInfoButtonListener() {
    	view.getIconLabel(6).addMouseListener(new MouseListener() {
    		public void mouseClicked(MouseEvent e) {
    			view.getIconLabel(6).setIcon(view.getIcon(4));
    			view.showMessageDialog("StrongBox v1.0\n" +
    					"Made by Ben Ansems De Vries and Thomas Timmermans\n" +
    					"www.github.com/devadv/StrongBox\n\n" +
    					"verhaaltje over jasypt, connectie met google drive.");
    		}
    		public void mouseEntered(MouseEvent e) {
    			view.getIconLabel(6).setIcon(view.getIcon(5));
    		}
    		public void mouseExited(MouseEvent e) {
    			view.getIconLabel(6).setIcon(view.getIcon(4));
    		}
    		public void mousePressed(MouseEvent e) {
    			view.getIconLabel(6).setIcon(view.getIcon(6));
    		}
    		public void mouseReleased(MouseEvent e) {
    			view.getIconLabel(6).setIcon(view.getIcon(4));
    		}
    	}
    	);
    }
	
	public static void main(String[] args) {

		TestControllerMVC tosti = new TestControllerMVC();

	}
}

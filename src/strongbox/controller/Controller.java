package strongbox.controller;

import strongbox.model.Messages;
import strongbox.model.Model;
import strongbox.model.Record;
import strongbox.encryption.Encryption;
import strongbox.util.ColorAnim;
import strongbox.util.PasswordSafe;
import strongbox.view.GUI;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import org.jasypt.util.text.BasicTextEncryptor;
import org.jasypt.util.text.StrongTextEncryptor;

/**
 * @version 07-02-2017
 */
public class Controller {

	private Model model;
	private GUI view;
	
    private DefaultListModel<String> folderData = new DefaultListModel<>();
    private DefaultListModel<String> recordData = new DefaultListModel<>();
	
	private Record record; // The currently selected or last selected record.

	// editMode is true if the user is creating a new record or editing one.
	private boolean editMode = false;
	
	private boolean edit = false; // True if we are editing an existing record.
	
	private boolean showPassword = false;
	private char echoChar;
	
	private Messages messages;
	private ColorAnim anim;
    
    /**
     * Constructor
     */
	public Controller(Model model) {
		
		System.out.println(record);
		
		this.model = model;
		
		//use masterpasswd for encryption	
		Encryption enMaster = new Encryption(model.getMasterpassword());
		//read properties from config.properties
		model.readProperties();
		//decrypt passphrase
		String decryptedpassphrase = Encryption.decrypt(model.getPassphrase());
		//use passphrase
		Encryption enPassphrase = new Encryption(decryptedpassphrase);
		
		model.readRecordsFromFile();
				
		view = new GUI();
		
		messages = new Messages();
		
		anim = new ColorAnim();
		anim.setLabel(view.getStatusLabel());
		
		initializeFolderData();
		
    	view.getFolderView().setModel(folderData);
    	view.getRecordView().setModel(recordData);
		
		addFolderListener();
		addRecordListener();

		addSaveListener();
		addCancelListener();
		
		addEyeButtonListener();
		//TODO dice button show only in editmode
		addDiceButtonListener();

		addSearchListener();
		
		addSearchFocusListener();
		
		searchBoxMouseListener();
		
        initSearchBox();
        
        copyPasswordToClipboard();
		
		echoChar = ((JPasswordField)view.getFields().get(3)).getEchoChar();
		
		((AbstractDocument) view.getFields().get(3).getDocument()).setDocumentFilter(new PasswordFilter(view.getFields().get(3)));
		 
		view.getFolderView().grabFocus();
		
		setEnableNormalMode(true);
		setEnableEditMode(false);
		
		// Next few lines prevent buggy, glitchy, inexplicable visual errors 
		// that SOMETIMES happen for these icons when they're rendered in linux
        view.getIconButton(5).repaint();
        view.getIconButton(6).repaint();
        view.getSearchLabel().repaint();

	}
	  
	public void copyPasswordToClipboard(){
		view.getFields().get(3).addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				super.mouseClicked(e);
				if (SwingUtilities.isRightMouseButton(e) && record != null) {
					String pw = view.getFields().get(3).getText();
					System.out.println(pw);
					view.getStatusLabel().setText(messages.getStatus(14));
					anim.slowFade();
					StringSelection stringSelection = new StringSelection(pw);
					Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
					clpbrd.setContents(stringSelection, null);
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				
				if (record != null) {
					super.mouseEntered(e);
					view.getStatusLabel().setText(messages.getStatus(13));
					anim.slowFade();
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if (! view.getStatusLabel().getText().equals(messages.getStatus(14))) {
				super.mouseExited(e);
				anim.fastFade();
				}
			}
			
		});

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
                    	initSearchBox();
                    	recordData.clear();
                    	for (String recordTitle: model.getTitlesByFolder(view.getFolderView().getSelectedValue())) {
                    		recordData.addElement(recordTitle);
                    		view.getRecordView().setSelectedValue(recordData.firstElement(), true);                    		
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
						view.getStrengthScoreLabel().setText("" + PasswordSafe.getScore(record.getPassword()));
    				}
    				catch (NullPointerException exc) {
    					for (int i = 0; i < 6; i++) {
    						view.setPlainGrayColor(view.getFields().get(i));
    						view.getFields().get(i).setText("No record selected");
    					}
						view.getStrengthScoreLabel().setText("N/A");
    				}
    			}
    		}
    	}
    	);
    }
    
    /**
     * Enable or disable the scrollpanes with the JLists, and the 5 buttons
     * plus searchbox at the top. Toggle icon- and color-state as well.
     * @param b  If true these components are enabled, if false they are disabled.
     *           Also toggle icons and colors accordingly.
     */
    public void setEnableNormalMode(boolean b) {
    	
    	view.getFolderScrollPane().setEnabled(b);
    	view.getRecordScrollPane().setEnabled(b);
    	view.getFolderView().setEnabled(b);
    	view.getRecordView().setEnabled(b);
    	view.getSearchBox().setEnabled(b);
    	if (b) {
    		addRecordCreationListener();
    		addEditListener();
    		addDeleteListener();
    		addDeleteAllListener();
    		addAboutButtonListener();
        	for (int i = 0; i < 5; i++) {
        		view.getIconButton(i).setIcon(view.getIcon(i));
        	}
    	}
    	else {
        	for (int i = 0; i < 5; i++) {
        		view.getIconButton(i).removeMouseListener(view.getIconButton(i).getMouseListeners()[0]);
        		view.getIconButton(i).setIcon(view.getIcon(i + 9));
        	}
    	}    	
    }  

    /**
     * Enable or disable the "dice" button, the save and cancel buttons,
     * the textfields and toggle the text color under the buttons.
     * @param b  If true these components are enabled, if false they are disabled.
     *           Also toggle icons and colors accordingly.
     */
    public void setEnableEditMode(boolean b) {
    	
    	editMode = b;
    	
    	view.getButton(0).setVisible(b);
    	view.getButton(0).setEnabled(b);
    	view.getButton(1).setVisible(b);
    	view.getButton(1).setEnabled(b);
    	
    	for (JTextField field: view.getFields()) {
    		field.setEditable(b);   
    		view.setDarkGrayColor(field);
    	}
    	if (b) {
    		addDiceButtonListener();
    		//view.getIconButton(6).setIcon(view.getIcon(7));
    		for (int i = 0; i < view.getIconLabelTexts().size(); i++) {
    			view.setDullGrayColor(view.getIconLabelTexts().get(i));
    		}
    		view.getFields().get(0).grabFocus();
        	view.getSearchLabel().setIcon(view.getIcon(15));
    	}
    	else {
        	view.getIconButton(6).removeMouseListener(view.getIconButton(6).getMouseListeners()[0]);
        	//view.getIconButton(6).setIcon(view.getIcon(8));
    		for (int i = 0; i < view.getIconLabelTexts().size(); i++) {
    			view.setDarkGrayColor(view.getIconLabelTexts().get(i));
    		}
        	view.getSearchLabel().setIcon(view.getIcon(14));
    	}
    	view.getBlackLabel().setVisible(b);
    	view.repaintFrame();
    }

    /**
     * Add an ActionListener to the 'Create new record' button.
     */
    public void addRecordCreationListener() {
    	view.getIconButton(0).addMouseListener(new MouseListener() {
    		public void mouseClicked(MouseEvent e) {

    			view.getStatusLabel().setText(messages.getStatus(7));
    			anim.slowFade();
    			
    			edit = false;
    			
    			setEnableNormalMode(false);
    			setEnableEditMode(true);
    			
    			for (JTextField field: view.getFields()) {   
    				field.setText("");
       			}
    		}
    		public void mouseEntered(MouseEvent e) {
    			view.setDullGrayColor(view.getIconLabelTexts().get(0));
    			view.getStatusLabel().setText(messages.getStatus(0));
    			anim.slowFade();
    		}
    		public void mouseExited(MouseEvent e) {
    			view.setDarkGrayColor(view.getIconLabelTexts().get(0));
    			anim.fastFade();
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
    	view.getIconButton(1).addMouseListener(new MouseListener() {
    		public void mouseClicked(MouseEvent e) {
    			if (view.getRecordView().getSelectedValue() != null) {

    				view.getStatusLabel().setText(messages.getStatus(7));
    				anim.slowFade();
    				
    				edit = true;
    				
        			setEnableNormalMode(false);
        			setEnableEditMode(true);

    			}
        		else {
        			view.showMessageDialog("No record selected");
        		}
    		}
    		public void mouseEntered(MouseEvent e) {
    			view.setDullGrayColor(view.getIconLabelTexts().get(1));
    			if (record != null) {
    				view.getStatusLabel().setText(messages.getStatus(1));
    			}
    			else {
    				view.getStatusLabel().setText(messages.getStatus(18));
    			}
    			anim.slowFade();
    		}
    		public void mouseExited(MouseEvent e) {
    			view.setDarkGrayColor(view.getIconLabelTexts().get(1));
    			anim.fastFade();
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

    				model.writeRecordsToFile();

    				view.getStatusLabel().setText(messages.getStatus(8));
    				anim.slowFade();
    				
        			setEnableNormalMode(true);
        			setEnableEditMode(false);

    				initializeFolderData();
    				initSearchBox();
    				view.getFolderView().setSelectedValue(fieldValues[4], true);
    				view.getRecordView().grabFocus();
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

        			setEnableNormalMode(true);
        			setEnableEditMode(false);

    				try {
    					int j = view.getRecordView().getSelectedIndex();
    					String[] fields = model.getRecordFields(record);
    					for (int i = 0; i < 6; i++) {
    						view.getFields().get(i).setText(fields[i]);
    					}
    					if (edit) {
    						view.getStatusLabel().setText(messages.getStatus(9));
    						anim.slowFade();
    					}
    					else {
    						view.getStatusLabel().setText(messages.getStatus(10));
    						anim.slowFade();
    					}
        				initSearchBox();
        				view.getRecordView().grabFocus();    					
                    	recordData.clear();
                    	for (String recordTitle: model.getTitlesByFolder(view.getFolderView().getSelectedValue())) {
                    		recordData.addElement(recordTitle);
                        }
                		view.getRecordView().setSelectedIndex(j);
        				
    				}
    				catch (NullPointerException exc) {
    					for (int i = 0; i < 6; i++) {
    						view.setPlainGrayColor(view.getFields().get(i));
    						view.getFields().get(i).setText("No record selected");
    					}
        				initSearchBox();
        				view.getFolderView().grabFocus();
        				
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
    	view.getIconButton(2).addMouseListener(new MouseListener() {
    		public void mouseClicked(MouseEvent e) {
    			if (view.getRecordView().getSelectedValue() != null) {
    				String title = view.getRecordView().getSelectedValue();
    				if (view.showConfirmDialog("Are you sure you want to" + 
    						" delete the following record: " + title + " ?")) {
    					record = model.getRecord(title);
    					String folder = record.getFolder();
    					model.delete(record);
    					model.writeRecordsToFile();
    					view.getStatusLabel().setText(messages.getStatus(5));
    					anim.slowFade();
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
    			if (record != null) {
    				view.getStatusLabel().setText(messages.getStatus(2));
    			}
    			else {
    				view.getStatusLabel().setText(messages.getStatus(19));
    			}
    			anim.slowFade();
    		}
    		public void mouseExited(MouseEvent e) {
    			view.setDarkGrayColor(view.getIconLabelTexts().get(2));
    			if (! view.getStatusLabel().getText().equals(messages.getStatus(5))) {
    				anim.fastFade();
    			}
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
    	view.getIconButton(3).addMouseListener(new MouseListener() {
    		public void mouseClicked(MouseEvent e) {
				if (view.showConfirmDialog("Are you sure you want to" + 
						" delete ALL records?")) {
					model.deleteAll();
					model.writeRecordsToFile();
					view.getStatusLabel().setText(messages.getStatus(6));
					anim.slowFade();
					initializeFolderData();
				}
    		}
    		public void mouseEntered(MouseEvent e) {
    			view.setDullGrayColor(view.getIconLabelTexts().get(3));
    			view.getStatusLabel().setText(messages.getStatus(3));
    			anim.slowFade();
    		}
    		public void mouseExited(MouseEvent e) {
    			view.setDarkGrayColor(view.getIconLabelTexts().get(3));
    			if (! view.getStatusLabel().getText().equals(messages.getStatus(6))) {
    				anim.fastFade();
    			}
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
    	view.getIconButton(5).addMouseListener(new MouseListener() {
    		public void mouseClicked(MouseEvent e) {
    			view.getIconButton(5).setIcon(view.getIcon(5));
				JPasswordField pwField = (JPasswordField)view.getFields().get(3);
				if (!showPassword) {
					pwField.setEchoChar((char)0);
					showPassword = true;
					view.getStrengthTextLabel().setVisible(true);
					view.getStrengthScoreLabel().setVisible(true);
					view.getStatusLabel().setText(messages.getStatus(12));
					anim.slowFade();
				}
				else {
					pwField.setEchoChar(echoChar);
					showPassword = false;
					view.getStrengthTextLabel().setVisible(false);
					view.getStrengthScoreLabel().setVisible(false);
					view.getStatusLabel().setText(messages.getStatus(11));
					anim.slowFade();
				}
    		}
    		public void mouseEntered(MouseEvent e) {
    			view.getIconButton(5).setIcon(view.getIcon(6));
    			if (!showPassword) {
    				view.getStatusLabel().setText(messages.getStatus(11));
    				anim.slowFade();
    			}
    			else {
    				view.getStatusLabel().setText(messages.getStatus(12));
    				anim.slowFade();
    			}
    		}
    		public void mouseExited(MouseEvent e) {
    			view.getIconButton(5).setIcon(view.getIcon(5));
    			anim.fastFade();
    		}
    		public void mousePressed(MouseEvent e) {
    			view.getIconButton(5).setIcon(view.getIcon(6));
    		}
    		public void mouseReleased(MouseEvent e) {
    			view.getIconButton(5).setIcon(view.getIcon(5));
    		}
    	}
    	);
    }
    
    /**
     * Add a MouseListener to the 'Dice' button so a random password can be
     * generated. Also show the slider which can be used to set the length.
     */    
    public void addDiceButtonListener() {
    	view.getIconButton(6).addMouseListener(new MouseListener() {
    		public void mouseClicked(MouseEvent e) {
    			view.getIconButton(6).setIcon(view.getIcon(7));
    			view.getFields().get(3).setText(PasswordSafe.generatePassphrase(12));
    			view.getStrengthScoreLabel().setText("" + PasswordSafe.getScore(view.getFields().get(3).getText()));
    		}
    		public void mouseEntered(MouseEvent e) {
    			view.getIconButton(6).setIcon(view.getIcon(8));
    			if (editMode) {
    				view.getStatusLabel().setText(messages.getStatus(15));
    				anim.slowFade();
    			}
    			else {
    				view.getStatusLabel().setText(messages.getStatus(16));
    				anim.slowFade();
    			}
    		}
    		public void mouseExited(MouseEvent e) {
    			view.getIconButton(6).setIcon(view.getIcon(7));
    			anim.fastFade();
    		}
    		public void mousePressed(MouseEvent e) {
    			view.getIconButton(6).setIcon(view.getIcon(8));
    		}
    		public void mouseReleased(MouseEvent e) {
    			view.getIconButton(6).setIcon(view.getIcon(7));
    		}
    	}
    	);
    }
    
    /**
     * Listener for 'About' button.
     */
    public void addAboutButtonListener() {
    	view.getIconButton(4).addMouseListener(new MouseListener() {
    		public void mouseClicked(MouseEvent e) {
    			view.showMessageDialog("StrongBox v1.0\n" +
    					"Made by Ben Ansems De Vries and Thomas Timmermans\n" +
    					"www.github.com/devadv/StrongBox\n\n" +
    					"blablabla, uitleg programma en zo..");
    		}
    		public void mouseEntered(MouseEvent e) {
    			view.setDullGrayColor(view.getIconLabelTexts().get(4));
    			view.getStatusLabel().setText(messages.getStatus(4));
    			anim.slowFade();
    		}
    		public void mouseExited(MouseEvent e) {
    			view.setDarkGrayColor(view.getIconLabelTexts().get(4));
    			anim.fastFade();
    		}
    		public void mousePressed(MouseEvent e) {
    			
    		}
    		public void mouseReleased(MouseEvent e) {
    			
    		}
    	}
    	);
    }
    
    /**
     * Add a DocumentListener to the search field and implement the methods.
     */
    public void addSearchListener() {
    	final PlainDocument doc = new PlainDocument();
    	view.getSearchBox().setDocument(doc);
    	doc.addDocumentListener(new DocumentListener() {
    		public void changedUpdate(DocumentEvent e) {
    			goSearch(doc);
    		}
    		public void insertUpdate(DocumentEvent e) {
    			goSearch(doc);
    		}
    		public void removeUpdate(DocumentEvent e) {
    			goSearch(doc);
    		}
    	}
    	);
    }
    
    /**
     * Implementation of the search process used by the "SearchListener" above.
     * Also handles updating of the JLists' view if matches are found.
     * @param doc  The document of which it's content will be used to search 
     *             the list with records.
     */
    private void goSearch(Document doc) {
    	recordData.clear();
    	if (doc.getLength() > 0) {
    		for (String recordTitle: model.search(getDocumentText(doc))) {
    			recordData.addElement(recordTitle);
    		}
    		view.getRecordView().setSelectedIndex(0);
    	}
    }
    
    /**
     * 
     */
    public void addSearchFocusListener() {
    	view.getSearchBox().addFocusListener(new FocusListener() {
    		public void focusGained(FocusEvent e) {
    			if(view.getSearchBox().getText().trim().equals("Search")
    					&& view.getSearchBox().getForeground().getRed() == 153
    					&& view.getSearchBox().getForeground().getGreen() == 153
    					&& view.getSearchBox().getForeground().getBlue() == 153) {
    				view.setDarkGrayColor(view.getSearchBox());
    				view.getSearchBox().setText("");
    			}
    		}

    		public void focusLost(FocusEvent e) {

    		}
    	}
    	);
    }
    
    /**
     * 
     */
    public void searchBoxMouseListener() {
    	view.getSearchBox().addMouseListener(new MouseAdapter() {

    		@Override
    		public void mouseEntered(MouseEvent e) {

    			if (! editMode) {
    			super.mouseEntered(e);
    			view.getStatusLabel().setText(messages.getStatus(17));
    			anim.slowFade();
    			}
    		}
    		@Override
    		public void mouseExited(MouseEvent e) {

    			if (! editMode) {
    			super.mouseExited(e);
    			anim.fastFade();
    			}
    		}
    	}
    	);
    }
    
    /**
     * Initialize the SearchBox by clearing it's contents if needed and setting 
     * the default text and color. 
     */
    public void initSearchBox() {
    	Document doc = view.getSearchBox().getDocument();
    	view.setDullGrayColor(view.getSearchBox());
    	try {
    		if (doc.getLength() > 0) {
    			doc.remove(0, doc.getLength());
    		}
    		doc.insertString(0, "Search", null);
    	}
    	catch (BadLocationException exc) {
    		exc.printStackTrace();
    	}
    }
    
    /**
     * Fetches the text contained within a document.
     * @param doc  The document to get the text from.
     * @throws BadLocationException if a portion of the given range was not 
     *         a valid part of the document.
     */
    public static String getDocumentText(Document doc) {
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
     * Use the characters contained in the PasswordField's Document to calculate 
     * the password safety score.
     * Keep the text from the pwStrength Label up-to-date with this score and 
     * also assign an appropriate description and color to the label based
     * on the score.
     */
    public class PasswordFilter extends DocumentFilter {
    	
    	private JTextField field;
    	private Document doc;
    	private int pwScore;
    	
        public PasswordFilter(JTextField field) {
            this.field = field;
            this.doc = field.getDocument();
            this.pwScore = PasswordSafe.getScore(Controller.getDocumentText(doc));
        }
        
        public void setStrengthLabel() {
        	view.getStrengthScoreLabel().setForeground(PasswordSafe.getScoreColor(pwScore));
        	view.getStrengthScoreLabel().setText("" + pwScore);
        }
            	
        @Override
        public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
            super.insertString(fb, offset, text, attr);
        	pwScore = PasswordSafe.getScore(Controller.getDocumentText(doc));
        	setStrengthLabel();
        }
        
        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);
        	pwScore = PasswordSafe.getScore(Controller.getDocumentText(doc));
        	setStrengthLabel();
        }
        
        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        	super.replace(fb, offset, length, text, attrs);
        	pwScore = PasswordSafe.getScore(Controller.getDocumentText(doc));
        	setStrengthLabel();
        }
    }
    
}
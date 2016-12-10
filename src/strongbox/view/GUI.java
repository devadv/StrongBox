package strongbox.view;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 * Write a description of class GUI here.
 * 
 * @version 10-12-2016
 */
public class GUI extends JFrame 
{
    private JList<String> folderView = new JList<String>();
    private JList<String> recordView = new JList<String>();
    private JScrollPane scrollPane;

    private String[] labels = new String[] {"Title", "Website Address", 
    		"Email or User Name", "Password", "Folder Name", "Add Note"};
    
    private ArrayList<JTextField> fields = new ArrayList<>();
    private JTextField field;
    
    private JTextField searchBox;
    
    private JButton eyeButton;
    private JButton deleteButton;

    /**
     * Constructor for objects of class GUI
     */
    public GUI()
    {
        super("StrongBox");
        JPanel framePanel = (JPanel)getContentPane();
        framePanel.setLayout(new GridBagLayout());

        JPanel mainPanel = new JPanel(new FlowLayout());
        framePanel.add(mainPanel);

        JPanel folderPanel = new JPanel(new BorderLayout()); // panel for the folders
        JPanel recordPanel = new JPanel(new BorderLayout()); // panel for the records
        JPanel detailPanel = new JPanel(new BorderLayout()); // panel for the record's details

        mainPanel.add(folderPanel);
        mainPanel.add(recordPanel);
        mainPanel.add(detailPanel);

        scrollPane = new JScrollPane(folderView, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
        		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        folderPanel.add(scrollPane, BorderLayout.CENTER);

        scrollPane = new JScrollPane(recordView, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        recordPanel.add(scrollPane, BorderLayout.CENTER);

        // search box
        JPanel searchPanel = new JPanel(new FlowLayout());
        recordPanel.add(searchPanel, BorderLayout.NORTH);
        searchPanel.add(new JLabel("Search: "));
        searchBox = new JTextField(12);
        searchPanel.add(searchBox);
        
        recordPanel.add(makeDeleteButton(), BorderLayout.SOUTH);
        
        // record details panel
        JPanel fieldBox = new JPanel();
        fieldBox.setLayout(new BoxLayout(fieldBox, BoxLayout.Y_AXIS));
        for (int i = 0; i < 6; i++) {
            JPanel flowPanel = new JPanel();
            flowPanel.add(makeLabel(labels[i]));
            flowPanel.add(makeField());
            fields.add(field);
            if (i == 3) {
            	flowPanel.add(makeEyeButton());
            }
            fieldBox.add(flowPanel);
        }
        detailPanel.add(fieldBox, BorderLayout.CENTER);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(300, 320);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    public JList<String> getFolderView() {
    	return folderView;
    }
    
    public JList<String> getRecordView() {
    	return recordView;
    }
    
    public ArrayList<JTextField> getFields() {
    	return fields;
    }

    public JLabel makeLabel(String labelText) {
        return new JLabel(labelText);
    }
    
    public JTextField getSearchBox() {
    	return searchBox;
    }
    
    public JButton getDeleteButton() {
    	return deleteButton;
    }

    public JTextField makeField() {
        field = new JTextField("Test field with enough space for long string");
        field.setEditable(false);
        return field;
    }
    
    public JButton makeEyeButton() {
        eyeButton = new JButton("oog");
        return eyeButton;
    }
    
    public JButton makeDeleteButton() {
        deleteButton = new JButton("Delete selected record");
        return deleteButton;
    }
    
    public void showNothingSelected() {
    	JOptionPane.showMessageDialog(this, "No record selected");
    }
    
    public boolean showConfirmDialog(String recordTitle) {
    	boolean delete = false;
    	int n = JOptionPane.showConfirmDialog(this, 
    			"Are you sure you want to delete the following record: " + 
    					recordTitle + " ?" , 
    					"Select an Option", JOptionPane.YES_NO_CANCEL_OPTION);

    	if(n == JOptionPane.YES_OPTION) {
    		 // Yes - delete record
    		delete = true;
    	}
    	else if (n == JOptionPane.NO_OPTION) {
    		 // No - don't delete record
    	}     
    	else {
    		 // Cancel - don't delete record
    	}
    	return delete;
    }

}
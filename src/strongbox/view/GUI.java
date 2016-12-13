package strongbox.view;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 * Write a description of class GUI here.
 * 
 * @version 13-12-2016
 */
public class GUI extends JFrame 
{
    private JList<String> folderView = new JList<String>();
    private JList<String> recordView = new JList<String>();
    private JScrollPane scrollPane;

    private JButton button;
    private ArrayList<JButton> buttons = new ArrayList<>();
    
    private JTextField field;
    private ArrayList<JTextField> fields = new ArrayList<>();
    
    private JTextField searchBox;
    
    private String[] labels = new String[] {"Title", "Website Address", 
    		"Email or User Name", "Password", "Folder Name", "Add Note"};

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

        JPanel temp = new JPanel(new GridLayout(0, 1));
        detailPanel.add(temp, BorderLayout.SOUTH);
        temp.add(makeButton("Create new record"));         // buttons (0)
        temp.add(makeButton("Edit selected record"));      // buttons (1)
        temp.add(makeButton("Cancel / Discard Changes"));  // buttons (2)
        temp.add(makeButton("Save record"));               // buttons (3)
        temp.add(makeButton("Delete selected record"));    // buttons (4)
        temp.add(makeButton("Delete ALL records"));        // buttons (5)

        // record details panel
        JPanel fieldBox = new JPanel();
        fieldBox.setLayout(new BoxLayout(fieldBox, BoxLayout.Y_AXIS));
        for (int i = 0; i < 6; i++) {
            JPanel flowPanel = new JPanel();
            flowPanel.add(makeLabel(labels[i]));
            flowPanel.add(makeField());
            fields.add(field);
            if (i == 3) {
            	flowPanel.add(makeButton("oog"));          // buttons (6)
            	flowPanel.add(makeButton("dice"));         // buttons (7)
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

    public JButton makeButton(String text) {
    	button = new JButton(text);
    	buttons.add(button);
    	return button;
    }

    public JLabel makeLabel(String labelText) {
        return new JLabel(labelText);
    }
    
    public JTextField makeField() {
        field = new JTextField("Test field with enough space for long string");
        field.setEditable(false);
        return field;
    }
        
    public void showMessageDialog(String message) {
    	JOptionPane.showMessageDialog(this, message);
    }
    
    public boolean showConfirmDialog(String dialogText) {
    	boolean confirm = false;
    	int n = JOptionPane.showConfirmDialog(this, dialogText , 
    			"Select an Option", JOptionPane.YES_NO_OPTION);

    	if(n == JOptionPane.YES_OPTION) {
    		// Yes
    		confirm = true;
    	}
    	else {
    		// No
    	}
    	return confirm;
    }
    
    // Getters to make components available for controller class
    public JList<String> getFolderView() {
    	return folderView;
    }
    
    public JList<String> getRecordView() {
    	return recordView;
    }
    
    public ArrayList<JTextField> getFields() {
    	return fields;
    }
    
    public JButton getButton(int index) {
    	return buttons.get(index);
    }
    
    public JTextField getSearchBox() {
    	return searchBox;
    }
    
}
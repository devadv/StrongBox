package strongbox.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.ArrayList;

/**
 * Write a description of class GUI here.
 * 
 * @version 19-12-2016
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
    		"Email or User Name", "Password", "Folder Name", "Note"};

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
        recordPanel.add(temp, BorderLayout.SOUTH);
        temp.add(makeButton("Create new record"));         // buttons (0)
        temp.add(makeButton("Edit selected record"));      // buttons (1)
        temp.add(makeButton("Delete selected record"));    // buttons (2)
        temp.add(makeButton("Delete ALL records"));        // buttons (3)
        
        // record details panel
        JPanel flowContainer = new JPanel(new FlowLayout());
        JPanel fieldPanel = new JPanel(new GridLayout(0, 1));
        fieldPanel.setBorder(new MatteBorder(3, 3, 3, 3, Color.GREEN));
        for (int i = 0; i < 6; i++) {
        	// TRY component.setMaximumSize(new Dimension(x, y)); WITH BOX-LAYOUT [19-12-2016]
        	JPanel flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
          	flowPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLUE));
        	//flowPanel.setAlignmentY(JComponent.BOTTOM_ALIGNMENT);
        	flowPanel.add(makeLabel(labels[i]));
        	fieldPanel.add(flowPanel);
        	flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        	flowPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.ORANGE));
        	if (i == 3) {
        		flowPanel.add(makePasswordField());
        		flowPanel.add(makeButton("oog"));          // buttons (4)
        		flowPanel.add(makeButton("dice"));         // buttons (5)
        	}
        	else {
        		flowPanel.add(makeField());
        	}
        	fieldPanel.add(flowPanel);
        	if (i == 5) {
        		flowPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        		flowPanel.add(makeButton("Save record"));               // buttons (6)
        		flowPanel.add(makeButton("Cancel / Discard Changes"));  // buttons (7)
        		fieldPanel.add(flowPanel);
        	}
        }
        flowContainer.add(fieldPanel);
        detailPanel.add(flowContainer, BorderLayout.CENTER);
        
        folderPanel.add(makeButton("Print ArrayList: records"), BorderLayout.SOUTH);// buttons(8)
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(300, 320);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JButton makeButton(String text) {
    	button = new JButton(text);
    	buttons.add(button);
    	return button;
    }

    public JLabel makeLabel(String labelText) {
    	JLabel label = new JLabel(labelText);
    	label.setBorder(new MatteBorder(18, 2, 2, 2, Color.RED));
    	//label.setVerticalAlignment(SwingConstants.BOTTOM);
        return label;
    }
    
    public JTextField makeField() {
        field = new JTextField("Test field with enough space for long string");
        field.setEditable(false);
        field.setBorder(new MatteBorder(2, 2, 2, 2, Color.MAGENTA));
    	fields.add(field);
        return field;
    }
    
    public JTextField makePasswordField() {
        field = new JPasswordField("Password test field with lots of space");
        field.setEditable(false);
        field.setBorder(new MatteBorder(2, 2, 2, 2, Color.MAGENTA));
    	fields.add(field);
        return field;
    }
        
    public void showMessageDialog(String message) {
    	JOptionPane.showMessageDialog(this, message);
    }
    
    public void showMessageDialog(String message, String title, int messageType) {
    	JOptionPane.showMessageDialog(this, message, title, messageType);
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
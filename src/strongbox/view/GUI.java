package strongbox.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.ArrayList;

/**
 * Write a description of class GUI here.
 * 
 * @version 26-12-2016
 */
public class GUI extends JFrame {
	
    private JList<String> folderView = new JList<String>();
    private JList<String> recordView = new JList<String>();
    private JScrollPane scrollPane;

    private ArrayList<JButton> buttons = new ArrayList<>();
    
    private ArrayList<ImageIcon> icons = new ArrayList<>();
    private ArrayList<JLabel> iconLabels = new ArrayList<>();
    
    private JTextField field;
    private ArrayList<JTextField> fields = new ArrayList<>();
    
    private JLabel searchLabel = new JLabel("Search: ");
    private JTextField searchBox;

    private String[] labels = new String[] {"Title", "Website Address", 
    		"Email or User Name", "Password", "Folder Name", "Note"};
    
//    private JLabel pwStrength1 = new JLabel("Strength: ");
//    private JLabel pwStrength2 = new JLabel("Average");
    
    //JSlider slider = new JSlider(1, 48, 16);

    /**
     * Constructor for objects of class GUI
     */
    public GUI()
    {
        super("StrongBox");
        JPanel framePanel = (JPanel)getContentPane();
        framePanel.setLayout(new GridBagLayout());
        
        JPanel mainBorderPanel = new JPanel(new BorderLayout());
        framePanel.add(mainBorderPanel);
      
        JPanel mainPanel = new JPanel(new GridLayout(1, 0));
        mainBorderPanel.add(mainPanel, BorderLayout.CENTER);
        
        JPanel flowPanelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        mainBorderPanel.add(flowPanelTop, BorderLayout.NORTH);

        JPanel folderPanel = new JPanel(new BorderLayout()); // panel for the folders
        JPanel recordPanel = new JPanel(new BorderLayout()); // panel for the records
        JPanel detailPanel = new JPanel(new BorderLayout()); // panel for the record's details
        
        folderPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.RED));
        recordPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLUE));
        detailPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.GREEN));
        
        JPanel leftBorderPanel = new JPanel(new BorderLayout());
        JPanel leftGridPanel = new JPanel(new GridLayout(1, 0));
        leftBorderPanel.add(leftGridPanel, BorderLayout.CENTER);
        
        leftGridPanel.add(folderPanel);
        leftGridPanel.add(recordPanel);
        
        mainPanel.add(leftBorderPanel);
        mainPanel.add(detailPanel);
        
        scrollPane = new JScrollPane(folderView, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
        		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        folderPanel.add(scrollPane, BorderLayout.CENTER);

        scrollPane = new JScrollPane(recordView, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        recordPanel.add(scrollPane, BorderLayout.CENTER);

        flowPanelTop.add(makeButton("Create new record"));        // buttons (0)
        flowPanelTop.add(makeButton("Edit selected record"));     // buttons (1)
        flowPanelTop.add(makeButton("Delete selected record"));   // buttons (2)
        flowPanelTop.add(makeButton("Delete ALL records"));       // buttons (3)
        
        // search box
        JPanel searchPanel = new JPanel(new FlowLayout());
        flowPanelTop.add(searchPanel, BorderLayout.NORTH);
        searchPanel.add(searchLabel);
        searchBox = new JTextField(12);
        searchPanel.add(searchBox);
        searchPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.RED));
        
        makeIcons();
        
         // record details panel
        JPanel boxPanel = new JPanel();
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));
        boxPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        for (int i = 0; i < 6; i++) {
        	JPanel flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
          	flowPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLUE));
        	flowPanel.add(makeLabel(labels[i]));
        	boxPanel.add(flowPanel);
        	flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        	flowPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.ORANGE));
        	if (i == 3) {
        		flowPanel.add(makePasswordField());
        		flowPanel.add(makeIconLabel(getIcon(0)));     // iconLabels (0) "eye-button"
        		flowPanel.add(makeIconLabel(getIcon(2)));     // iconLabels (1) "dice-button"
        	}
        	else {
        		flowPanel.add(makeField());
        	}
        	boxPanel.add(flowPanel);
        	if (i == 5) {
        		flowPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        		flowPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.RED));
        		flowPanel.add(makeButton("Save record"));               // buttons (4)
        		flowPanel.add(makeButton("Cancel / Discard Changes"));  // buttons (5)
        		boxPanel.add(flowPanel);
        	}
        }
        detailPanel.add(boxPanel, BorderLayout.CENTER);
        
        enlargeFont(folderView);
        enlargeFont(recordView);
        
        flowPanelTop.add(makeIconLabel(getIcon(4)));          // iconLabels (2) "info-button"
        //infoLabel.setToolTipText("About StrongBox");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(300, 320);
        //setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JButton makeButton(String text) {
    	JButton button = new JButton(text);
    	buttons.add(button);
    	return button;
    }

    public JLabel makeLabel(String labelText) {
    	JLabel label = new JLabel(labelText);
    	label.setBorder(new MatteBorder(19, 2, 2, 2, Color.RED));
        return label;
    }
    
    public JLabel makeIconLabel(ImageIcon icon) {
    	JLabel label = new JLabel(icon);
    	label.setBorder(new MatteBorder(2, 2, 2, 2, Color.RED));
    	iconLabels.add(label);
        return label;
    }
    
    public JTextField makeField() {
        field = new JTextField("34_Charactersssssssszzzzzzzzzzzzzz", 29);
        field.setEditable(false);
        enlargeFont(field);
        field.setBorder(new MatteBorder(2, 2, 2, 2, Color.MAGENTA));
    	fields.add(field);
        return field;
    }
    
    public JTextField makePasswordField() {
        field = new JPasswordField("34_Charactersssssssszzzzzzzzzzzzzz", 29);
        field.setEditable(false);
        enlargeFont(field);
        field.setBorder(new MatteBorder(2, 2, 2, 2, Color.MAGENTA));
    	fields.add(field);
        return field;
    }
    
    private void makeIcons() {
    	icons.add(new ImageIcon("res/eye-01.png"));  // icons (0)  "black eye"
    	icons.add(new ImageIcon("res/eye-02.png"));  // icons (1)  "gray eye"
    	icons.add(new ImageIcon("res/dice-01.png")); // icons (2)  "black dice"
		icons.add(new ImageIcon("res/dice-02.png")); // icons (3)  "gray dice"		
    	icons.add(new ImageIcon("res/info-01.png")); // icons (4)  "info button normal"
		icons.add(new ImageIcon("res/info-02.png")); // icons (5)  "info button on mouse-over"
    	icons.add(new ImageIcon("res/info-03.png")); // icons (6)  "info button when clicked"
    }
    
    private void enlargeFont(JComponent comp) {
     	Font changedFont = comp.getFont().deriveFont(Font.BOLD, (float)13.5);
     	comp.setFont(changedFont);
    }
    
    public void setDarkGrayColor(JComponent comp) {
    	comp.setForeground(new Color(51, 51, 51));
    }
    
    public void setDullGrayColor(JComponent comp) {
    	comp.setForeground(new Color(153, 153, 153));
    }
    
    /////////////////////////////////////////////////
    public void showMessageDialogButtons(String message) {
    	JOptionPane.showMessageDialog(fields.get(4), message);
    }
    
    public void showDialog() {
    	JDialog dialog = new JDialog(this, "Hello", true);
    	JLabel label = new JLabel("Roll the Dice");
    	label.setBorder(new MatteBorder(28, 28, 28, 28, Color.RED));
    	dialog.add(label);
    	dialog.pack();
    	dialog.setLocationRelativeTo(null);
    	dialog.setVisible(true);
    }
    /////////////////////////////////////////////////
    
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
    
    public JLabel getIconLabel(int index) {
    	return iconLabels.get(index);
    }
    
    public ImageIcon getIcon(int index) {
    	return icons.get(index);
    }
    
    public JLabel getSearchLabel() {
    	return searchLabel;
    }
    
}
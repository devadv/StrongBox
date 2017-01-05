package strongbox.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.ArrayList;

/**
 * Write a description of class GUI here.
 * 
 * @version 03-01-2017
 */
public class GUI extends JFrame {
	
    private JList<String> folderView = new JList<String>();
    private JList<String> recordView = new JList<String>();
    private JScrollPane scrollPane;

    private ArrayList<JButton> buttons = new ArrayList<>();
    
    private ArrayList<ImageIcon> icons = new ArrayList<>();
    private ArrayList<JLabel> iconLabels = new ArrayList<>();
    private ArrayList<JLabel> iconLabelTexts = new ArrayList<JLabel>();
    
    private JTextField field;
    private ArrayList<JTextField> fields = new ArrayList<>();
    
    private JLabel searchLabel = new JLabel("Search: ");
    private JTextField searchBox;

    private String[] fieldLabels = new String[] {"Title", "Website Address",
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
        
        JPanel mainPanel = new JPanel(/*new FlowLayout(FlowLayout.LEFT, 25, 25)*/);
        mainBorderPanel.add(mainPanel, BorderLayout.CENTER);
        
        JPanel flowPanelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        mainBorderPanel.add(flowPanelTop, BorderLayout.NORTH);

        JPanel folderPanel = new JPanel(new BorderLayout()); // panel for the folders
        JPanel recordPanel = new JPanel(new BorderLayout()); // panel for the records
        JPanel detailPanel = new JPanel(new BorderLayout()); // panel for the record's details
        
        folderPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.RED));
        recordPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLUE));
        detailPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.GREEN));

        JPanel leftGridPanel = new JPanel(new GridLayout(1, 0/*, 25, 25*/));
        
        leftGridPanel.add(folderPanel);
        leftGridPanel.add(recordPanel);

        mainPanel.add(leftGridPanel);
        mainPanel.add(detailPanel);
        
        scrollPane = new JScrollPane(folderView, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
        		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(224, 418));
        folderPanel.add(scrollPane, BorderLayout.CENTER);


        scrollPane = new JScrollPane(recordView, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(224, 418));
        recordPanel.add(scrollPane, BorderLayout.CENTER);

        makeIcons();
        
        //////////////////////////////////
        
        JPanel buttonGrid = new JPanel(new GridLayout(1, 4));

        String[] iconLabelStrings = {"New", "Edit", "Delete", "Delete ALL"};

        for (int i = 0; i < 4; i++) {
        	JPanel container = new JPanel(new BorderLayout());
        	JPanel flowPanel = new JPanel();
        	flowPanel.add(makeIconLabel(getIcon(i)));
        	container.add(flowPanel, BorderLayout.CENTER);
        	flowPanel = new JPanel();
        	JLabel label = new JLabel(iconLabelStrings[i]);
        	iconLabelTexts.add(label);
        	flowPanel.add(label);
        	container.add(flowPanel, BorderLayout.SOUTH);
        	buttonGrid.add(container);
        }
        
        flowPanelTop.add(buttonGrid);
        
        // iconLabelTexts (0) "New"
        // iconLabelTexts (1) "Edit"
        // iconLabelTexts (2) "Delete"
        // iconLabelTexts (3) "Delete ALL"
        
//        flowPanelTop.add(makeIconLabel(getIcon(0)));  // iconLabels (0)   "Create new record"
//        flowPanelTop.add(makeIconLabel(getIcon(1)));  // iconLabels (1)   "Edit selected record"
//        flowPanelTop.add(makeIconLabel(getIcon(2)));  // iconLabels (2)   "Delete selected record"
//        flowPanelTop.add(makeIconLabel(getIcon(3)));  // iconLabels (3)   "Delete ALL records"
        
        //////////////////////////////////
        
        // search box
        JPanel searchPanel = new JPanel(new FlowLayout());
        flowPanelTop.add(searchPanel, BorderLayout.NORTH);
        searchPanel.add(searchLabel);
        searchBox = new JTextField(12);
        searchPanel.add(searchBox);
        searchPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.RED));
                
        // record details panel
        JPanel boxPanel = new JPanel();
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));
        boxPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        for (int i = 0; i < 6; i++) {
        	JPanel flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));        	
          	flowPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLUE));
        	flowPanel.add(makeLabel(fieldLabels[i]));
        	boxPanel.add(flowPanel);
        	flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));        	
        	if (i == 3) {
            	flowPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.ORANGE));
        		flowPanel.add(makePasswordField());
        		flowPanel.add(makeIconLabel(getIcon(7)));     // iconLabels (4) "eye-button"
        		flowPanel.add(makeIconLabel(getIcon(9)));     // iconLabels (5) "dice-button"
        	}
        	else {
            	flowPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.ORANGE));
        		flowPanel.add(makeField());
        	}
        	boxPanel.add(flowPanel);
        	if (i == 5) {
        		flowPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));            	
        		flowPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.RED));
        		flowPanel.add(makeTextButton("Save record"));                // buttons (0)
        		flowPanel.add(makeTextButton("Cancel / Discard Changes"));   // buttons (1)
        		boxPanel.add(flowPanel);
        	}
        }
        detailPanel.add(boxPanel, BorderLayout.CENTER);
        
        enlargeFont(folderView);
        enlargeFont(recordView);
        
        flowPanelTop.add(makeIconLabel(getIcon(4)));          // iconLabels (6) "info-button"
        //infoLabel.setToolTipText("About StrongBox");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(300, 320);
        //setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /* Currently (03-01-2017) not in use!
    public JButton makeIconButton(ImageIcon icon) {
    	JButton button = new JButton(icon);
    	buttons.add(button);
    	return button;
    }
    */
    
    public JButton makeTextButton(String text) {
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
    	//label.setBorder(new MatteBorder(2, 2, 2, 2, Color.RED));
    	iconLabels.add(label);
        return label;
    }
    
    public JTextField makeField() {
        field = new JTextField("34_Charactersssssssszzzzzzzzzzzzzz", 29);
        field.setEditable(false);
        enlargeFont(field);
        field.setBorder(new MatteBorder(0, 0, 2, 0, Color.MAGENTA));
    	fields.add(field);
        return field;
    }
    
    public JTextField makePasswordField() {
        field = new JPasswordField("34_Charactersssssssszzzzzzzzzzzzzz", 29);
        field.setEditable(false);
        enlargeFont(field);
        field.setBorder(new MatteBorder(0, 0, 2, 0, Color.MAGENTA));
    	fields.add(field);
        return field;
    }
    
    private void makeIcons() {
    	icons.add(new ImageIcon("res/new.png"));            // icons (0)  "new record"
    	icons.add(new ImageIcon("res/edit.png"));           // icons (1)  "edit record"
    	icons.add(new ImageIcon("res/delete.png"));         // icons (2)  "delete record"
    	icons.add(new ImageIcon("res/trash.png"));          // icons (3)  "delete all records"		
    	icons.add(new ImageIcon("res/info-01.png"));        // icons (4)  "info button normal"
		icons.add(new ImageIcon("res/info-02.png"));        // icons (5)  "info button on mouse-over"
    	icons.add(new ImageIcon("res/info-03.png"));        // icons (6)  "info button when clicked"    	
    	icons.add(new ImageIcon("res/eye-01.png"));         // icons (7)  "black eye"
    	icons.add(new ImageIcon("res/eye-02.png"));         // icons (8)  "gray eye"
    	icons.add(new ImageIcon("res/dice-01.png"));        // icons (9)  "black dice"
		icons.add(new ImageIcon("res/dice-02.png"));        // icons (10)  "gray dice"
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
    
    public void showDialog(String message) {
    	JDialog dialog = new JDialog(this, "Let's Roll the Dice!", true);
    	JLabel label = new JLabel(message);
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
    
    public ArrayList<JLabel> getIconLabelTexts() {
    	return iconLabelTexts;
    }
    
    public JLabel getSearchLabel() {
    	return searchLabel;
    }
    
}
package strongbox.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.ArrayList;

/**
 * Write a description of class GUI here.
 * 
 * @version 15-01-2017
 */
public class GUI extends JFrame {
	
    private JList<String> folderView = new JList<String>();
    private JList<String> recordView = new JList<String>();
    private JScrollPane folderScrollPane;
    private JScrollPane recordScrollPane;

    private ArrayList<JButton> buttons = new ArrayList<>();
    
    private ArrayList<ImageIcon> icons = new ArrayList<>();
    private ArrayList<JLabel> iconButtons = new ArrayList<>();
    private ArrayList<JLabel> iconLabelTexts = new ArrayList<JLabel>();
    
    private JTextField field;
    private ArrayList<JTextField> fields = new ArrayList<>();

    private JTextField searchBox;
    private JLabel searchLabel;

    private String[] fieldLabels = new String[] {"Title", "Website Address",
    		"Email or User Name", "Password", "Folder Name", "Note"};
    
    private JLabel strength;
    private JLabel pwStrength;
    
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
        
        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        mainBorderPanel.add(mainPanel, BorderLayout.CENTER);
        
        JPanel flowPanelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        //flowPanelTop.setBorder(new MatteBorder(16, 2, 0, 2, Color.ORANGE));
        flowPanelTop.setBorder(new EmptyBorder(16, 2, 0, 2));
        mainBorderPanel.add(flowPanelTop, BorderLayout.NORTH);

        JPanel folderPanel = new JPanel(new BorderLayout()); // panel for the folders
        JPanel recordPanel = new JPanel(new BorderLayout()); // panel for the records
        JPanel detailPanel = new JPanel(new BorderLayout()); // panel for the record's details
        
        //folderPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.RED));
        folderPanel.setBorder(new EmptyBorder(2, 2, 2, 2));
        //recordPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLUE));
        recordPanel.setBorder(new EmptyBorder(2, 2, 2, 2));
        //detailPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.GREEN));
        detailPanel.setBorder(new EmptyBorder(2, 2, 2, 2));

        JPanel leftGridPanel = new JPanel(new GridLayout(1, 0, 10, 10));
        
        leftGridPanel.add(folderPanel);
        leftGridPanel.add(recordPanel);

        mainPanel.add(leftGridPanel);
        mainPanel.add(detailPanel);
        
        folderScrollPane = new JScrollPane(folderView, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
        		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        folderPanel.add(folderScrollPane, BorderLayout.CENTER);

        recordScrollPane = new JScrollPane(recordView, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        recordPanel.add(recordScrollPane, BorderLayout.CENTER);

        makeIconList();
        
        /// top panel
        JPanel buttonGrid = new JPanel(new GridLayout(1, 4));

        String[] iconLabelStrings = {"New", "Edit", "Delete", "Delete ALL"};

        for (int i = 0; i < 4; i++) {
        	JPanel container = new JPanel(new BorderLayout());
        	JPanel flowPanel = new JPanel();
        	flowPanel.add(makeIconButton(getIcon(i)));
        	container.add(flowPanel, BorderLayout.CENTER);
        	flowPanel = new JPanel();
        	JLabel label = new JLabel(iconLabelStrings[i]);
        	iconLabelTexts.add(label);
        	flowPanel.add(label);
        	container.add(flowPanel, BorderLayout.SOUTH);
        	buttonGrid.add(container);
        }
        
        flowPanelTop.add(buttonGrid);
        
        // iconButtons (0)   "Create new record"
        // iconButtons (1)   "Edit selected record"
        // iconButtons (2)   "Delete selected record"
        // iconButtons (3)   "Delete ALL records"
        
        // iconLabelTexts (0) "New"
        // iconLabelTexts (1) "Edit"
        // iconLabelTexts (2) "Delete"
        // iconLabelTexts (3) "Delete ALL"
        
        /// search box
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        flowPanelTop.add(searchPanel, BorderLayout.NORTH);
        searchLabel = new JLabel(getIcon(14));
        searchPanel.add(searchLabel);
        searchPanel.add(Box.createHorizontalStrut(5));
        searchBox = new JTextField(12);
        searchPanel.add(searchBox);
        searchPanel.setBorder(new EmptyBorder(8, 152, 22, 184));
        //searchPanel.setBorder(new MatteBorder(8, 152, 22, 184, Color.RED));
        
        /// "About" iconButton and textLabel
        JPanel aboutGrid = new JPanel(new GridLayout(1, 1));
        JPanel container = new JPanel(new BorderLayout());
        JPanel flowPanel = new JPanel();
        flowPanel.add(makeIconButton(getIcon(4)));           // iconButtons (4) "about-button"
        //getIconButton(4).setToolTipText("About StrongBox");
        container.add(flowPanel, BorderLayout.CENTER);
        flowPanel = new JPanel();
        JLabel textLabelAbout = new JLabel("About");             
        iconLabelTexts.add(textLabelAbout);               // iconLabelTexts (4) "About"
        flowPanel.add(textLabelAbout);
        container.add(flowPanel, BorderLayout.SOUTH);
        aboutGrid.add(container);
        flowPanelTop.add(aboutGrid);
        
        
        /// record details panel
        JPanel boxPanel = new JPanel();
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));
        boxPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        for (int i = 0; i < 6; i++) {
        	flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
            flowPanel.setBackground(Color.WHITE);
        	flowPanel.setBorder(new EmptyBorder(2, 2, 2, 2));
          	//flowPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLUE));
        	flowPanel.add(Box.createHorizontalStrut(2));
        	flowPanel.add(makeLabel(fieldLabels[i]));
        	if (i == 3) {
        		strength = makeLabel("Strength:");
        		flowPanel.add(strength);
        		pwStrength = makeLabel("N/A");
        		flowPanel.add(pwStrength);
        	}
        	boxPanel.add(flowPanel);
        	flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
            flowPanel.setBackground(Color.WHITE);
    		flowPanel.setBorder(new EmptyBorder(2, 2, 2, 2));
        	//flowPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.ORANGE));
        	flowPanel.add(Box.createHorizontalStrut(2));
        	if (i == 3) {
        		flowPanel.add(makePasswordField());
        		flowPanel.add(Box.createHorizontalStrut(5));
        		flowPanel.add(makeIconButton(getIcon(5)));    // iconButtons (5) "eye-button"
        		flowPanel.add(Box.createHorizontalStrut(4));
        		flowPanel.add(makeIconButton(getIcon(7)));    // iconButtons (6) "dice-button"
        		flowPanel.add(Box.createHorizontalStrut(3));
        	}
        	else {
        		flowPanel.add(makeField());
        	}
        	boxPanel.add(flowPanel);
        	if (i == 5) {
        		flowPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
                flowPanel.setBackground(Color.WHITE);
        		flowPanel.setBorder(new EmptyBorder(7, 2, 2, 2));
        		//flowPanel.setBorder(new MatteBorder(7, 2, 2, 2, Color.RED));
        		JPanel saveCancelGrid = new JPanel(new GridLayout(1, 2, 6, 0));
        		saveCancelGrid.add(makeTextButton("Save"));       // buttons (0) "save"
        		getButton(0).setToolTipText("Save Record");
        		saveCancelGrid.add(makeTextButton("Cancel"));     // buttons (1) "cancel"
        		getButton(1).setToolTipText("Cancel or Discard Changes");
        		flowPanel.add(saveCancelGrid);
        		boxPanel.add(flowPanel);
        	}
        }
        detailPanel.add(boxPanel, BorderLayout.CENTER);
        
        enlargeFont(folderView);
        enlargeFont(recordView);
        	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(300, 320);
        //setResizable(false);
        pack();
        folderScrollPane.setPreferredSize(new Dimension(224, boxPanel.getHeight()));
        recordScrollPane.setPreferredSize(new Dimension(224, boxPanel.getHeight()));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public JButton makeTextButton(String text) {
    	JButton button = new JButton(text);
    	buttons.add(button);
    	return button;
    }

    public JLabel makeLabel(String labelText) {
    	JLabel label = new JLabel(labelText);
    	label.setBorder(new EmptyBorder(19, 0, 2, 0));
    	//label.setBorder(new MatteBorder(19, 0, 2, 0, Color.RED));
        return label;
    }
    
    public JLabel makeIconButton(ImageIcon icon) {
    	JLabel label = new JLabel(icon);
    	iconButtons.add(label);
        return label;
    }
    
    public JTextField makeField() {
        field = new JTextField("", 26);
        field.setEditable(false);
        enlargeFont(field);
        field.setBackground(Color.WHITE);
        field.setBorder(new MatteBorder(0, 0, 2, 0, new Color(51, 51, 51)));
        //field.setBorder(new MatteBorder(0, 0, 2, 0, Color.MAGENTA));
    	fields.add(field);
        return field;
    }
    
    public JTextField makePasswordField() {
        field = new JPasswordField("", 26);
        field.setEditable(false);
        enlargeFont(field);
        field.setBackground(Color.WHITE);
        field.setBorder(new MatteBorder(0, 0, 2, 0, new Color(51, 51, 51)));
        //field.setBorder(new MatteBorder(0, 0, 2, 0, Color.MAGENTA));
    	fields.add(field);
        return field;
    }
    
    private void makeIconList() {
    	icons.add(new ImageIcon("res/new.png"));            // icons (0)  "new record normal"
    	icons.add(new ImageIcon("res/edit.png"));           // icons (1)  "edit record normal"
    	icons.add(new ImageIcon("res/delete.png"));         // icons (2)  "delete record normal"
    	icons.add(new ImageIcon("res/trash.png"));          // icons (3)  "delete ALL records normal"		
    	icons.add(new ImageIcon("res/about.png"));          // icons (4)  "about button normal" 	
    	icons.add(new ImageIcon("res/eye-01.png"));         // icons (5)  "black eye"
    	icons.add(new ImageIcon("res/eye-02.png"));         // icons (6)  "gray eye"
    	icons.add(new ImageIcon("res/dice-01.png"));        // icons (7)  "black dice"
		icons.add(new ImageIcon("res/dice-02.png"));        // icons (8)  "gray dice"		
    	icons.add(new ImageIcon("res/new-gray.png"));       // icons (9)  "new record grayscale"
    	icons.add(new ImageIcon("res/edit-gray.png"));      // icons (10) "edit record grayscale"
    	icons.add(new ImageIcon("res/delete-gray.png"));    // icons (11) "delete record grayscale"
    	icons.add(new ImageIcon("res/trash-gray.png"));     // icons (12) "delete ALL records grayscale"		
    	icons.add(new ImageIcon("res/about-gray.png"));     // icons (13) "about button grayscale"
    	icons.add(new ImageIcon("res/magnifier-01.png"));   // icons (14) "black magnifier icon"
    	icons.add(new ImageIcon("res/magnifier-02.png"));   // icons (15) "gray magnifier icon"
    }
    
    private void enlargeFont(JComponent comp) {
     	Font changedFont = comp.getFont().deriveFont(Font.BOLD, (float)13.5);
     	comp.setFont(changedFont);
    }
    
    public void setDarkGrayColor(JComponent comp) {
    	comp.setForeground(new Color(51, 51, 51));
    }

    public void setPlainGrayColor(JComponent comp) {
    	comp.setForeground(Color.GRAY); // 128, 128, 128
    }
    
    public void setDullGrayColor(JComponent comp) {
    	comp.setForeground(new Color(153, 153, 153));
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
    
    public JLabel getIconButton(int index) {
    	return iconButtons.get(index);
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
    
    public JLabel getStrengthLabel() {
    	return pwStrength;
    }

    public JScrollPane getFolderScrollPane() {
    	return folderScrollPane;
    }
    
    public JScrollPane getRecordScrollPane() {
    	return recordScrollPane;
    }
    
}

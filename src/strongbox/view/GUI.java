package strongbox.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import java.util.ArrayList;

/**
 * Write a description of class GUI here.
 * 
 * @version 31-01-2017
 */
public class GUI {
	
	private JFrame frame;
	
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
    
    private JLabel statusLabel;
    
    private JLabel strength;
    private JLabel pwStrength;
    
    private JLayeredPane layeredPane;
    
    private ArrayList<JPanel> blackLayers = new ArrayList<>();
    
    //JSlider slider = new JSlider(1, 48, 16);

    /**
     * Constructor for objects of class GUI
     */
    public GUI()
    {
    	frame = new JFrame("StrongBox");
        frame.setLayout(new GridBagLayout());
        
        layeredPane = frame.getLayeredPane();
        
        JPanel mainBorderPanel = new JPanel(new BorderLayout());
        layeredPane.add(mainBorderPanel, 0);
        for (int i = 0; i < 4; i++) {
        JPanel blackLayer = new JPanel();
        layeredPane.add(blackLayer, 1);
        blackLayers.add(blackLayer);
        }
        
        frame.add(mainBorderPanel);
        
        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        //mainPanel.setBorder(new MatteBorder(12, 2, 12, 2, Color.MAGENTA));
        mainPanel.setBorder(new EmptyBorder(12, 2, 12, 2));
        mainBorderPanel.add(mainPanel, BorderLayout.CENTER);
        
        JPanel boxPanelTop = new JPanel();
        boxPanelTop.setLayout(new BoxLayout(boxPanelTop, BoxLayout.X_AXIS));
        //boxPanelTop.setBorder(new MatteBorder(16, 3, 3, 3, Color.ORANGE));
        boxPanelTop.setBorder(new EmptyBorder(16, 3, 3, 3));
        mainBorderPanel.add(boxPanelTop, BorderLayout.NORTH);

        JPanel folderPanel = new JPanel(new BorderLayout()); // panel for the folders
        JPanel recordPanel = new JPanel(new BorderLayout()); // panel for the records
        JPanel detailPanel = new JPanel(new BorderLayout()); // panel for the record's details
        
        //folderPanel.setBorder(new MatteBorder(2, 2, 0, 2, Color.RED));
        folderPanel.setBorder(new EmptyBorder(2, 2, 0, 2));
        //recordPanel.setBorder(new MatteBorder(2, 2, 0, 2, Color.BLUE));
        recordPanel.setBorder(new EmptyBorder(2, 2, 0, 2));
        //detailPanel.setBorder(new MatteBorder(2, 2, 0, 2, Color.GREEN));
        detailPanel.setBorder(new EmptyBorder(2, 2, 0, 2));

        JPanel leftGridPanel = new JPanel(new GridLayout(1, 0, 10, 10));
        
        leftGridPanel.add(folderPanel);
        leftGridPanel.add(recordPanel);

        mainPanel.add(leftGridPanel);
        mainPanel.add(detailPanel);
        
        folderScrollPane = new JScrollPane(folderView, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
        		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        folderScrollPane.setPreferredSize(new Dimension(224, 420));

        folderPanel.add(folderScrollPane, BorderLayout.CENTER);
        recordScrollPane = new JScrollPane(recordView, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        recordScrollPane.setPreferredSize(new Dimension(224, 420));
        recordPanel.add(recordScrollPane, BorderLayout.CENTER);

        makeIconList();
        
        /// top panel
        JPanel flowContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel buttonGrid = new JPanel(new GridLayout(1, 4, 36, 0));
        //buttonGrid.setBorder(new MatteBorder(2, 2, 2, 2, Color.GREEN));

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
        
        flowContainer.add(buttonGrid);
        boxPanelTop.add(flowContainer);
        
        // iconButtons (0)   "Create new record"
        // iconButtons (1)   "Edit selected record"
        // iconButtons (2)   "Delete selected record"
        // iconButtons (3)   "Delete ALL records"
        
        // iconLabelTexts (0) "New"
        // iconLabelTexts (1) "Edit"
        // iconLabelTexts (2) "Delete"
        // iconLabelTexts (3) "Delete ALL"
        
        /// search box
        JPanel rightTopPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        //rightTopPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLUE));
        JPanel searchPanel = new JPanel(new FlowLayout());
        rightTopPanel.add(searchPanel);
        searchLabel = new JLabel(getIcon(14));
        searchPanel.add(searchLabel);
        searchBox = new JTextField(12);
     	Font newFont = searchBox.getFont().deriveFont(Font.PLAIN, (float)12.9);
     	searchBox.setFont(newFont);
        searchPanel.add(searchBox);
        searchPanel.setBorder(new EmptyBorder(8, 2, 22, 164));
        //searchPanel.setBorder(new MatteBorder(8, 2, 22, 164, Color.RED));
        
        /// "About" iconButton and textLabel
        JPanel aboutGrid = new JPanel(new GridLayout(1, 1));
        //aboutGrid.setBorder(new MatteBorder(5, 5, 5, 10, Color.MAGENTA));
        aboutGrid.setBorder(new EmptyBorder(5, 5, 5, 10));
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
        rightTopPanel.add(aboutGrid);
        
        boxPanelTop.add(rightTopPanel);
        
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
        		strength.setVisible(false);
        		flowPanel.add(strength);
        		pwStrength = makeLabel("N/A");
        		pwStrength.setVisible(false);
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
        		JPanel xBoxPanel = new JPanel();
        		xBoxPanel.setLayout(new BoxLayout(xBoxPanel, BoxLayout.X_AXIS));
        	    xBoxPanel.setBackground(Color.WHITE);
        		xBoxPanel.setBorder(new EmptyBorder(7, 2, 2, 2));
        		//xBoxPanel.setBorder(new MatteBorder(7, 2, 2, 2, Color.RED));
        		
        		xBoxPanel.add(Box.createHorizontalStrut(11));
 
        		statusLabel = new JLabel("", SwingConstants.LEFT);
        		//statusLabel2.setBorder(new MatteBorder(1, 1, 1, 6, Color.MAGENTA));
        		statusLabel.setBorder(new EmptyBorder(1, 1, 1, 6));
        		xBoxPanel.add(statusLabel, BorderLayout.WEST);
        		
        		JPanel rightFlowPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        	    rightFlowPanel.setBackground(Color.WHITE);
        		//rightFlowPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.GREEN));
        		JPanel saveCancelGrid = new JPanel(new GridLayout(1, 2, 6, 0));
        		saveCancelGrid.setBackground(Color.WHITE);
        		//saveCancelGrid.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLUE));
        		saveCancelGrid.add(makeTextButton("Save"));       // buttons (0) "save"
        		getButton(0).setToolTipText("Save Record");
        		saveCancelGrid.add(makeTextButton("Cancel"));     // buttons (1) "cancel"
        		getButton(1).setToolTipText("Cancel or Discard Changes");
        		rightFlowPanel.add(saveCancelGrid);
        		xBoxPanel.add(rightFlowPanel);
        		
        		boxPanel.add(xBoxPanel);
        	}
        }
        detailPanel.add(boxPanel, BorderLayout.CENTER);
        
        enlargeFont(folderView);
        enlargeFont(recordView);
        	
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        frame.pack(); // additional pack here is necessary for rendering
        
        folderScrollPane.setPreferredSize(new Dimension(224, boxPanel.getHeight()));
        recordScrollPane.setPreferredSize(new Dimension(224, boxPanel.getHeight()));        
        
        // Setting the bounds of the 4 black "layers" (which are JPanels)
        blackLayers.get(0).setBounds(
        		0, 
        		0, 
        		frame.getWidth(), 
        		boxPanelTop.getSize().height + 15);
        
        blackLayers.get(1).setBounds(
        		0, 
        		boxPanelTop.getSize().height + 15,
        		frame.getWidth() - boxPanel.getSize().width - 24, 
        		frame.getHeight() - boxPanelTop.getSize().height + 15);
        
        blackLayers.get(2).setBounds(
        		frame.getWidth() - boxPanel.getSize().width - 24,
        		boxPanelTop.getSize().height + 13 + boxPanel.getSize().height,
        		boxPanel.getSize().width - 1,
        		frame.getHeight() - boxPanelTop.getSize().height + 15 - boxPanel.getSize().height);
        
        blackLayers.get(3).setBounds(
        		boxPanel.getSize().width + 2 * folderScrollPane.getSize().width + 41,
        		boxPanelTop.getSize().height + 15,
        		15,
        		frame.getHeight() - boxPanelTop.getSize().height + 14);
        
        setBlackLayerProperties();
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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
        //field.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
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
        //field.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
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
    	JOptionPane.showMessageDialog(frame, message);
    }
    
    public void showMessageDialog(String message, String title, int messageType) {
    	JOptionPane.showMessageDialog(frame, message, title, messageType);
    }
    
    public boolean showConfirmDialog(String dialogText) {
    	boolean confirm = false;
    	int n = JOptionPane.showConfirmDialog(frame, dialogText , 
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
    
    public JLabel getStatusLabel() {
    	return statusLabel;
    }
    
    public JLabel getStrengthScoreLabel() {
    	return pwStrength;
    }
    
    public JLabel getStrengthTextLabel() {
    	return strength;
    }

    public JScrollPane getFolderScrollPane() {
    	return folderScrollPane;
    }
    
    public JScrollPane getRecordScrollPane() {
    	return recordScrollPane;
    }
    
    public ArrayList<JPanel> getBlackLayers() {
    	return blackLayers;
    }
    
    public void setBlackLayerProperties() {

    	for (JPanel blackLayer: blackLayers) {
    		blackLayer.setBackground(new Color(0, 0, 0, 112));
    		blackLayer.setVisible(false);
    	}

    }
    
    public void repaintFrame() {
    	frame.repaint();
    	for (JPanel blackLayer: blackLayers) {
    		blackLayer.repaint();
    	}
    }
    
}
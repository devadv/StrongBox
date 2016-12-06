package strongbox.view;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 * Write a description of class GUI here.
 * 
 * @author Thomas Timmermans
 * @version 05-12-2016
 */
public class GUI extends JFrame 
{
    private JList<String> folderList = new JList<String>();
    private JList<String> recordList = new JList<String>();
    private JScrollPane scrollPane;

    private String[] labels = new String[] {"Title", "Website Address", 
    		"Email or User Name", "Password", "Folder Name", "Add Note"};
    
    private ArrayList<JTextField> fields = new ArrayList<>();
    private JTextField field;

    /**
     * Constructor for objects of class GUI
     */
    public GUI()
    {

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

        scrollPane = new JScrollPane(folderList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
        		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        folderPanel.add(scrollPane, BorderLayout.CENTER);

        scrollPane = new JScrollPane(recordList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        recordPanel.add(scrollPane, BorderLayout.CENTER);

        // construct details panel
        JPanel fieldBox = new JPanel();
        fieldBox.setLayout(new BoxLayout(fieldBox, BoxLayout.Y_AXIS));
        for (int i = 0; i < 6; i++) {
            JPanel flowPanel = new JPanel();
            flowPanel.add(makeTestLabel(labels[i]));
            flowPanel.add(makeTestField());
            fields.add(field);
            fieldBox.add(flowPanel);
        }
        detailPanel.add(fieldBox, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(300, 320);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    public JList<String> getFolderList() {
    	return folderList;
    }
    
    public JList<String> getRecordList() {
    	return recordList;
    }
    
    public ArrayList<JTextField> getFields() {
    	return fields;
    }

    public JLabel makeTestLabel(String labelText) {
        return new JLabel(labelText);
    }

    public JTextField makeTestField() {
        field = new JTextField("Test field with enough space for long string");
        field.setEditable(false);
        return field;
    }

}
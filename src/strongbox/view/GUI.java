package strongbox.view;

import java.awt.*;
import javax.swing.*;

/**
 * Write a description of class GUI here.
 * 
 * @author Thomas Timmermans
 * @version 04-12-2016
 */
public class GUI extends JFrame 
{
    private JList<String> folderList = new JList<String>();
    private JList<String> recordList = new JList<String>();
    private JScrollPane scrollPane;
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
            flowPanel.add(makeTestLabel());
            flowPanel.add(makeTestField());
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

    public JLabel makeTestLabel() {
        return new JLabel("TestLabel");
    }

    public JTextField makeTestField() {
        field = new JTextField("TestField");
        field.setEditable(false);
        return field;
    }

}

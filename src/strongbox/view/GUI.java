package strongbox.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI extends JFrame {
    JList<String> list;
    JButton button;
    JScrollPane scrollPane;
    String[] folders = {"folder1", "folder2", "folder3"};
    String[] records = {"record1", "record2", "record3"};

    /**
     * Constructor
     */
    public GUI() {

        JPanel mainPanel = (JPanel)getContentPane();
        mainPanel.setLayout(new FlowLayout());

        JPanel folderPanel = new JPanel(new BorderLayout());
        JPanel recordListPanel = new JPanel(new BorderLayout());
        JPanel recordDetailPanel = new JPanel(new BorderLayout());

        mainPanel.add(folderPanel);
        mainPanel.add(recordListPanel);
        mainPanel.add(recordDetailPanel);

        newListScrollPane(folders);
        newButton("Get Folder", list);
        folderPanel.add(scrollPane, BorderLayout.CENTER);
        folderPanel.add(button, BorderLayout.SOUTH);

        newListScrollPane(records);
        newButton("Get Record", list);
        recordListPanel.add(scrollPane, BorderLayout.CENTER);
        recordListPanel.add(button, BorderLayout.SOUTH);

        // record-details panel
        JPanel octaBox = new JPanel();
        octaBox.setLayout(new BoxLayout(octaBox, BoxLayout.Y_AXIS));
        for (int i = 0; i < 8; i++) {
            JPanel flowPanel = new JPanel();
            flowPanel.add(makeTestLabel());
            flowPanel.add(makeTestField());
            octaBox.add(flowPanel);
        }
        recordDetailPanel.add(octaBox, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(300, 320);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    public void newListScrollPane(String[] arr) {
        list = new JList<String>(arr);

        scrollPane = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    public void newButton(String text, final JList<String> list) {
        button = new JButton(text);
        button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (list.getSelectedValue() == null) {
                        JOptionPane.showMessageDialog(returnSelf(), "Nothing selected");
                    }
                    else {
                        JOptionPane.showMessageDialog(returnSelf(), list.getSelectedValue());
                    }
                }
            }
        );
    }

    public GUI returnSelf() {
        return this;
    }

    public JLabel makeTestLabel() {
        return new JLabel("TestLabel");
    }

    public JTextField makeTestField() {
        return new JTextField("TestField");
    }
}

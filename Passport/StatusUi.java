package Passport;

import javax.swing.*;
import java.awt.*;

public class StatusUi extends JFrame {
    private Status status = new Status();

    private JTextField searchTextField;
    private JButton searchButton;
    private JButton uploadAgainButton;
    private JButton backButton;
    private JTextArea outputTextArea;

    public StatusUi() {
        setTitle("Verification Status Output");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        searchTextField = new JTextField();
        searchButton = new JButton("Search");
        uploadAgainButton = new JButton("Upload Again");
        backButton = new JButton("Back");
        outputTextArea = new JTextArea();

        searchTextField.setPreferredSize(new Dimension(150, 25));

        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Enter User NIC:"));
        searchPanel.add(searchTextField);
        searchPanel.add(searchButton);
        searchPanel.add(uploadAgainButton);
        searchPanel.add(backButton);

        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(outputTextArea), BorderLayout.CENTER);

        searchButton.addActionListener(e -> searchUserData());
        uploadAgainButton.addActionListener(e -> uploadAgainUserData());
        backButton.addActionListener(e -> goBack());
    }

    private void searchUserData() {
        String userNic = searchTextField.getText().trim();
        if (!userNic.isEmpty()) {
            outputTextArea.setText("");
            status.SearchData(userNic, outputTextArea);
        } else {
            JOptionPane.showMessageDialog(this, "Please enter User NIC for search.");
        }
    }

    private void uploadAgainUserData() {
        dispose();
        new UploadAgaiUI().setVisible(true);
    }

    private void goBack() {
        dispose();
        new Home().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StatusUi().setVisible(true));
    }
}
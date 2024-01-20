import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.Color;


public class RSAGUI extends JFrame {
    private RSA rsa;

    private JTextArea pField, qField, nField, phiNField, eField, dField;
    private JTextArea inputField, outputField;
    private JButton encryptButton, decryptButton;

    public RSAGUI() {
        rsa = new RSA();

        setTitle("RSA Encryption/Decryption");
        setSize(1400, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeComponents();

        // Use GridLayout for the top part
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(3, 2));

        addLabelAndField(topPanel, "p:", pField);
        addLabelAndField(topPanel, "q:", qField);
        addLabelAndField(topPanel, "n:", nField);
        addLabelAndField(topPanel, "phi(n):", phiNField);
        addLabelAndField(topPanel, "e:", eField);
        addLabelAndField(topPanel, "d:", dField);

        // Use another layout for the bottom part
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(4, 2));

        addLabelAndField(bottomPanel, "Enter plaintext/ciphertext:", inputField);

        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String plaintext = inputField.getText();
                String[] words = plaintext.split("\\s+");
                if (words.length < 20) {
                    // Display a dialog if the condition is not met
                    JOptionPane.showMessageDialog(RSAGUI.this, "Plaintext length should be greater or equal to 20. Got: " + words.length, "Error", JOptionPane.ERROR_MESSAGE);
                } else if(plaintext.length() > 125){
                    JOptionPane.showMessageDialog(RSAGUI.this, "Plaintext's character length should not be greater than 125. Got: " + plaintext.length(), "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String ciphertext = rsa.getCiphertext(plaintext);
                    outputField.setText("Encrypted message: " + ciphertext);
                }
            }
        });
        bottomPanel.add(encryptButton);

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ciphertext = inputField.getText();
                String decryptedText = rsa.getPlaintext(ciphertext);
                outputField.setText(String.valueOf(decryptedText));
            }
        });
        bottomPanel.add(decryptButton);

        addLabelAndField(bottomPanel, "Output:", outputField);

        // Use BoxLayout for the main content pane
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Add the top and bottom panels to the content pane
        add(topPanel);
        add(bottomPanel);

        updateFields();

        setVisible(true);
    }

    private void addLabelAndField(JPanel panel, String labelText, JTextArea textArea) {
    JLabel label = new JLabel(labelText);
    label.setHorizontalAlignment(SwingConstants.CENTER);
    
    // Dark border for the label
    label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

    panel.add(label);

    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);

    // Dark border for the text area
    textArea.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

    panel.add(textArea);
    }


    private void initializeComponents() {
        pField = createTextArea();
        qField = createTextArea();
        nField = createTextArea();
        phiNField = createTextArea();
        eField = createTextArea();
        dField = createTextArea();
        inputField = createTextArea();
        outputField = createTextArea();
        encryptButton = new JButton("Encrypt");
        decryptButton = new JButton("Decrypt");

        // Set the fields to be non-editable
        pField.setEditable(false);
        qField.setEditable(false);
        nField.setEditable(false);
        phiNField.setEditable(false);
        eField.setEditable(false);
        dField.setEditable(false);
        outputField.setEditable(false);
    }

    private JTextArea createTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        return textArea;
    }

    private void updateFields() {
        pField.setText(rsa.p.toString());
        qField.setText(rsa.q.toString());
        nField.setText(rsa.n.toString());
        phiNField.setText(rsa.phi.toString());
        eField.setText(rsa.e.toString());
        dField.setText(rsa.d.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RSAGUI();
            }
        });
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class OnlineTrainTicketReservationSystem {
    private JFrame frame;
    private JButton reserveButton;
    private JButton cancelButton;
    private JTextField nameField;
    private JPasswordField passwordField;
    private JTextField trainNumberField;
    private JTextField trainNameField;
    private JTextField journeyDateField;
    private JTextField fromField;
    private JTextField toField;
    private JTextField cancelPNRField;
    private HashMap<String, TicketDetails> ticketDetailsMap;
    private HashMap<String, String> users;

    public class TicketDetails {
        private String name;
        private String trainNumber;
        private String trainName;
        private String journeyDate;
        private String from;
        private String to;

        public TicketDetails(String name, String trainNumber, String trainName, String journeyDate, String from, String to) {
            this.name = name;
            this.trainNumber = trainNumber;
            this.trainName = trainName;
            this.journeyDate = journeyDate;
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return "Name: " + name + "\nTrain Number: " + trainNumber + "\nTrain Name: " + trainName + "\nJourney Date: " + journeyDate + "\nFrom: " + from + "\nTo: " + to;
        }
    }

    public OnlineTrainTicketReservationSystem() {
        frame = new JFrame("Train Ticket Reservation System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(800, 600); // Initial size
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel headerLabel = new JLabel("Welcome to Train Ticket Reservation System");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel);
        frame.add(headerPanel, BorderLayout.NORTH);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("train.jpg");
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        frame.add(panel, BorderLayout.CENTER);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components

        placeComponents(panel, gbc);

        ticketDetailsMap = new HashMap<>();
        users = new HashMap<>();
        // Add sample users (username, password)
        users.put("user1", "password1");
        users.put("user2", "password2");

        // Set the font size for JOptionPane dialogs
         UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 16));
         UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 16));

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel, GridBagConstraints gbc) {
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 18)); 
        userLabel.setForeground(Color.WHITE); // Set font color to white
        panel.add(userLabel, createConstraints(gbc, 0, 0, GridBagConstraints.EAST));

        JTextField userText = new JTextField(10);
        userText.setFont(new Font("Arial", Font.PLAIN, 18)); 
        panel.add(userText, createConstraints(gbc, 1, 0, GridBagConstraints.WEST));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordLabel.setForeground(Color.WHITE); // Set font color to white
        panel.add(passwordLabel, createConstraints(gbc, 0, 1, GridBagConstraints.EAST));

        passwordField = new JPasswordField(10);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18)); 
        panel.add(passwordField, createConstraints(gbc, 1, 1, GridBagConstraints.WEST));

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 18)); 
        panel.add(loginButton, createConstraints(gbc, 1, 2, GridBagConstraints.CENTER));

        reserveButton = new JButton("Reserve Ticket");
        reserveButton.setVisible(false);
        reserveButton.setFont(new Font("Arial", Font.PLAIN, 18)); 
        panel.add(reserveButton, createConstraints(gbc, 1, 3, GridBagConstraints.CENTER));

        cancelButton = new JButton("Cancel Ticket");
        cancelButton.setVisible(false);
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 18)); 
        panel.add(cancelButton, createConstraints(gbc, 1, 4, GridBagConstraints.CENTER));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordField.getPassword());
                if (isValidUser(username, password)) {
                    JOptionPane.showMessageDialog(null, "Login successful.");
                    reserveButton.setVisible(true);
                    cancelButton.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password.");
                }
            }
        });

        reserveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReservationForm();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCancelTicketForm();
            }
        });
    }

    private GridBagConstraints createConstraints(GridBagConstraints gbc, int gridx, int gridy, int anchor) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.anchor = anchor;
        return gbc;
    }


    private boolean isValidUser(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }

    private void showReservationForm() {
        JFrame reservationFrame = new JFrame("Reservation Form");
        reservationFrame.setSize(400, 300);
        reservationFrame.setLayout(new GridLayout(7, 2));

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 18)); 
        reservationFrame.add(nameLabel);
        nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 18)); 
        reservationFrame.add(nameField);

        JLabel trainNumberLabel = new JLabel("Train Number:");
        trainNumberLabel.setFont(new Font("Arial", Font.PLAIN, 18)); 
        reservationFrame.add(trainNumberLabel);
        trainNumberField = new JTextField();
        trainNumberField.setFont(new Font("Arial", Font.PLAIN, 18)); 
        reservationFrame.add(trainNumberField);

        JLabel trainNameLabel = new JLabel("Train Name:");
        trainNameLabel.setFont(new Font("Arial", Font.PLAIN, 18)); 
        reservationFrame.add(trainNameLabel);
        trainNameField = new JTextField();
        trainNameField.setFont(new Font("Arial", Font.PLAIN, 18)); 
        reservationFrame.add(trainNameField);

        JLabel journeyDateLabel = new JLabel("Journey Date:");
        journeyDateLabel.setFont(new Font("Arial", Font.PLAIN, 18)); 
        reservationFrame.add(journeyDateLabel);
        journeyDateField = new JTextField();
        journeyDateField.setFont(new Font("Arial", Font.PLAIN, 18)); 
        reservationFrame.add(journeyDateField);

        JLabel fromLabel = new JLabel("From:");
        fromLabel.setFont(new Font("Arial", Font.PLAIN, 18)); 
        reservationFrame.add(fromLabel);
        fromField = new JTextField();
        fromField.setFont(new Font("Arial", Font.PLAIN, 18)); 
        reservationFrame.add(fromField);

        JLabel toLabel = new JLabel("To:");
        toLabel.setFont(new Font("Arial", Font.PLAIN, 18)); 
        reservationFrame.add(toLabel);
        toField = new JTextField();
        toField.setFont(new Font("Arial", Font.PLAIN, 18)); 
        reservationFrame.add(toField);

        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Arial", Font.PLAIN, 18)); 
        reservationFrame.add(okButton);

        // Center the reservation form on the screen
        reservationFrame.setLocationRelativeTo(null);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String trainNumber = trainNumberField.getText();
                String trainName = trainNameField.getText();
                String journeyDate = journeyDateField.getText();
                String from = fromField.getText();
                String to = toField.getText();

                if (!name.isEmpty() && !trainNumber.isEmpty() && !trainName.isEmpty() && !journeyDate.isEmpty() && !from.isEmpty() && !to.isEmpty()) {
                    String pnr = generatePNR();
                    ticketDetailsMap.put(pnr, new TicketDetails(name, trainNumber, trainName, journeyDate, from, to));
                    JOptionPane.showMessageDialog(null, "Ticket reserved successfully!\nPNR: " + pnr);
                    reservationFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                }
            }
        });

        reservationFrame.setVisible(true);
    }

    private void showCancelTicketForm() {
        JFrame cancelFrame = new JFrame("Cancel Ticket");
        cancelFrame.setSize(400, 150);
        cancelFrame.setLayout(new FlowLayout());

        JLabel pnrLabel = new JLabel("Enter PNR to Cancel:");
        pnrLabel.setFont(new Font("Arial", Font.PLAIN, 18)); 
        cancelFrame.add(pnrLabel);
        cancelPNRField = new JTextField(15);
        cancelPNRField.setFont(new Font("Arial", Font.PLAIN, 18)); 
        cancelFrame.add(cancelPNRField);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 18)); 
        cancelFrame.add(cancelButton);

        // Center the cancel ticket form on the screen
        cancelFrame.setLocationRelativeTo(null);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pnr = cancelPNRField.getText();
                if (!pnr.isEmpty()) {
                    TicketDetails ticketDetails = ticketDetailsMap.get(pnr);
                    if (ticketDetails != null) {
                        int choice = JOptionPane.showConfirmDialog(null, "Ticket Details:\n" + ticketDetails.toString() + "\nAre you sure you want to cancel this ticket?", "Confirm Cancellation", JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_OPTION) {
                            ticketDetailsMap.remove(pnr);
                            JOptionPane.showMessageDialog(null, "Ticket with PNR " + pnr + " has been cancelled.");
                            cancelFrame.dispose(); // Close the cancellation form page
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Ticket with PNR " + pnr + " not found.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid PNR.");
                }
            }
        });

        cancelFrame.setVisible(true);
    }

    private String generatePNR() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String datePart = sdf.format(new Date());
        return datePart + (int) (Math.random() * 1000);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
          new OnlineTrainTicketReservationSystem();
            }
        });
    }
}

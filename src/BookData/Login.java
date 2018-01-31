package BookData;

/**
 * This class is for the main menu of the library system. This will 
 * display a GUI for the user to interact with the system. It will
 * have a JFrame with 3 labels for a picture and field with StudentID and Password.
 * It will have 2 textfields for the user to input data into.
 * It will have 3 buttons for the user to login if the correct data entered.
 * A cancel button if they want to clear the textfields.
 * A reg button if they have not registered with the system and wish to do so. 
 */

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.sql.*;

@SuppressWarnings("serial")
public class Login extends JFrame implements ActionListener {// start class
	JPanel panel;
	JButton login, reg, cancel, test;
	JLabel user, pass, title, msg;
	JTextField studentID;
	JPasswordField password;
	sqlEngine dbEngine = new sqlEngine();
	String passText = "";
	String userText = "";

	public static void main(String[] args) {// start main method

		Login Login = new Login();
		Login.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}// end main

	// Constructor for JFrame
	public Login() {
		super("Login Screen");
		buildGUI();
		pack();
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		dbEngine.connect();

	}

	/**
	 * This method is used to create the JFrame and add the various different
	 * components. Set the layout of the GUI and set the colors and font family
	 * of the text.
	 */
	private void buildGUI() {
		this.getContentPane().setLayout(new GridBagLayout());
		Color grey = Color.decode("#c2c2c2");
		this.getContentPane().setBackground(grey);
		GridBagConstraints c = new GridBagConstraints();

		ImageIcon image = new ImageIcon("itblogo.gif");
		title = new JLabel(image);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 0, 0);
		c.gridx = 5;
		c.gridy = 3;
		this.getContentPane().add(title, c);

		Color green = Color.decode("#107c10");
		user = new JLabel("StudentID : ");
		user.setFont(new Font("Serif", Font.BOLD, 30));
		user.setForeground(green);
		c.gridx = 4;
		c.gridy = 5;
		c.insets = new Insets(20, 20, 20, 20);
		this.getContentPane().add(user, c);

		studentID = new JTextField();
		c.gridx = 5;
		c.gridy = 5;
		studentID.setPreferredSize(new Dimension(130, 30));
		this.getContentPane().add(studentID, c);

		pass = new JLabel("Password : ");
		pass.setFont(new Font("Serif", Font.BOLD, 30));
		pass.setForeground(green);
		c.insets = new Insets(20, 20, 20, 20);
		c.gridx = 4;
		c.gridy = 6;
		this.getContentPane().add(pass, c);

		password = new JPasswordField();
		c.insets = new Insets(20, 20, 20, 20);
		c.gridx = 5;
		c.gridy = 6;
		password.setPreferredSize(new Dimension(130, 30));
		this.getContentPane().add(password, c);

		login = new JButton("Login");
		c.gridx = 5;
		c.gridy = 8;
		login.addActionListener(this);
		this.getContentPane().add(login, c);

		reg = new JButton("Register");
		c.gridx = 6;
		c.gridy = 10;
		reg.addActionListener(this);
		this.getContentPane().add(reg, c);

		cancel = new JButton("Cancel");
		c.gridx = 5;
		c.gridy = 9;
		cancel.addActionListener(this);
		this.getContentPane().add(cancel, c);

		msg = new JLabel("Are you registered with the system if not please do so ");
		msg.setFont(new Font("Serif", Font.BOLD, 16));
		c.gridx = 5;
		c.gridy = 10;
		this.getContentPane().add(msg, c);

	}// end buildGUI

	/**
	 * This method is for the handling of the button actions. When the user
	 * clicks the various buttons of the GUI. It executes these actions. When
	 * the user clicks the login button it will get the text from the textfields
	 * and assign them to variables which will then be passed to the
	 * getStudentInfo method. When the reg button is clicked it will bring the
	 * user to another screen so they can register with the system sets the
	 * current JFrame false and displys the new one called.
	 */
	public void actionPerformed(ActionEvent e) {
		// int count = 0;
		// ResultSet result;
		if (e.getSource() == login) {
			userText = studentID.getText();
			passText = new String(password.getPassword());
			while (userText.equals("") || (passText.equals(""))) {
				int reply = JOptionPane.showConfirmDialog(null, "You must enter valid inforation in all textfields",
						null, JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					String tempi = JOptionPane.showInputDialog("Please Enter a valid StudentID");
					String tempp = JOptionPane.showInputDialog("Please Enter a valid Passowrd");
					studentID.setText(tempi);
					password.setText(tempp);
					userText = studentID.getText();
					passText = new String(password.getPassword());
					if (!studentID.equals("") && !passText.equals("")) {
						break;
					}
				}
				if (reply == JOptionPane.NO_OPTION) {
					int reply2 = JOptionPane.showConfirmDialog(null, "You are sure you would like to exit", null,
							JOptionPane.YES_NO_OPTION);
					if (reply2 == JOptionPane.YES_OPTION) {
						JOptionPane.showMessageDialog(null, "You are exiting the system");
						System.exit(0);
					}
				}
			}
			getStudentInfo(userText, passText);
		} else if (e.getSource() == cancel) {
			studentID.setText("");
			password.setText("");
		} else if (e.getSource() == reg) {
			createRegFrame();
		}

	}

	/**
	 * This class is for the search of a student in the database. It takes two
	 * parameters both string which are StudentID and Password. This is where
	 * the GUI will interact with the database it will pass the two parameters
	 * to the database and if they find a match will bring you into the system.
	 * It handles the closing of the database connection also.
	 * 
	 * @param ID
	 *            String for the StudentID input
	 * @param pass
	 *            String for the Password input
	 */

	private void getStudentInfo(String ID, String pass) {
		ResultSet rs = null;
		String tempi = "";
		String tempp = "";
		int tempRole = 0;
		try {
			rs = dbEngine.searchUserQuery(ID, pass);
			while (rs.next()) {
				tempi = rs.getString("StudentID");
				tempp = rs.getString("Pass");
				tempRole = rs.getInt("Role");
				System.out.println(rs.getInt("Role"));
			}
			if (ID.equals(tempi) && (pass.equals(tempp))) {
				if (tempRole == 1) {
					createStudent();
					JOptionPane.showMessageDialog(null, "Welcome to ITB library system");
				} else {
					createAdmin();
					JOptionPane.showMessageDialog(null, "Welcome to ITB library system");
				}

			} else {
				JOptionPane.showMessageDialog(null, "Error Student not found try again");
				studentID.setText("");
				password.setText("");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "SQLException Error" + e.getMessage());
		} // end catch

	}// end getStudentInfo

	/**
	 * This method is for to call the new JFrame when the user enters the
	 * correct information.
	 */
	private void createStudent() {
		GuiForBook gui = new GuiForBook("ITB Books");
		gui.pack();
		gui.setVisible(true);
		dbEngine.closeConnection();
		dispose();
	}

	/**
	 * This method is for to call the new JFrame when the user has the correct
	 * role to enter this admin role.
	 */
	private void createAdmin() {
		new SystemAdmin();
		dbEngine.closeConnection();
		dispose();
	}

	/**
	 * This method is for to call the new JFrame when the user selects the reg
	 * button.
	 */
	private void createRegFrame() {
		new Register();
		dbEngine.closeConnection();
		dispose();
	}

}// end class

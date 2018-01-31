package BookData;
import javax.swing.JOptionPane;
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

public class Register extends JFrame implements ActionListener {
	JLabel ID, Username, Pass, title;
	JTextField id, user;
	JPasswordField pass;
	ImageIcon image;
	JButton reg, cancel;
	sqlEngine dbEngine = new sqlEngine();
	String userText = "", passText = "";
	int tempid = 0;
	static int count = 3;

	public static void main(String[] args) {// start main method

		Register reg = new Register();
		reg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}// end main method
	
	//Constructor for JFrame
	public Register() {
		super("Register Screen");
		buildGUI();
		setVisible(true);
		pack();
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

		image = new ImageIcon("itblogo.gif");
		title = new JLabel(image);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 0, 0);
		c.gridx = 5;
		c.gridy = 3;
		this.getContentPane().add(title, c);

		Color green = Color.decode("#107c10");

		Username = new JLabel("Username : ");
		Username.setFont(new Font("Serif", Font.BOLD, 30));
		Username.setForeground(green);
		c.gridx = 4;
		c.gridy = 6;
		c.insets = new Insets(20, 20, 20, 20);
		this.getContentPane().add(Username, c);

		user = new JTextField();
		c.gridx = 5;
		c.gridy = 6;
		user.setPreferredSize(new Dimension(130, 30));
		this.getContentPane().add(user, c);

		Pass = new JLabel("Password : ");
		Pass.setFont(new Font("Serif", Font.BOLD, 30));
		Pass.setForeground(green);
		c.insets = new Insets(20, 20, 20, 20);
		c.gridx = 4;
		c.gridy = 7;
		this.getContentPane().add(Pass, c);

		pass = new JPasswordField();
		c.insets = new Insets(20, 20, 20, 20);
		c.gridx = 5;
		c.gridy = 7;
		pass.setPreferredSize(new Dimension(130, 30));
		this.getContentPane().add(pass, c);

		reg = new JButton("Register");
		c.gridx = 5;
		c.gridy = 8;
		reg.addActionListener(this);
		this.getContentPane().add(reg, c);

		cancel = new JButton("Cancel");
		c.gridx = 5;
		c.gridy = 9;
		cancel.addActionListener(this);
		this.getContentPane().add(cancel, c);

	}// end buildGUI
	
	/**
	 * This method is for the handling of the button actions. When the user
	 * clicks the various buttons of the GUI. It executes these actions. When
	 * the user clicks the reg button and the correct information been entered
	 * in the textfields. It takes this information and write to the database.
	 * The cancel button when clicked will clear the text in the JTextfield and JPassfield.
	 * The error checking will be handled by JOptionPane for the user to recover from
	 * errors.  
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == reg) {
			while (user.getText().isEmpty() || pass.getPassword().equals("")) {
				int reply = JOptionPane.showConfirmDialog(null, "You must enter valid inforation in all textfields",
						null, JOptionPane.YES_NO_OPTION);
				reg.setVisible(false);
				cancel.setVisible(false);
				if (reply == JOptionPane.YES_OPTION || user.getText().isEmpty() || pass.getPassword().equals("")) {
					user.setText("");
					pass.setText("");
					String tempu = JOptionPane.showInputDialog("Please Enter a valid Username");
					String tempp = JOptionPane.showInputDialog("Please Enter a valid Passowrd");
					user.setText(tempu);
					pass.setText(tempp);
					// populate the variables to be sent to the query if not
					// null

					userText = user.getText();
					passText = new String(pass.getPassword());
					System.out.println(passText + userText);
					if ((!userText.equals("")) && (!passText.equals(""))) {
						break;
					}

				} else {
					JOptionPane.showMessageDialog(null, "You Choose to exit the system");
					System.exit(0);
				}

			} // end while
			userText = user.getText();
			passText = new String(pass.getPassword());
			// query method with the variables

			insertStudentInfo(userText, passText);
			tempid = getStudentID(tempid);
			JOptionPane.showMessageDialog(null, "Thank You for registering your StudentID is " + tempid);
			createFrame();

		} else if (e.getSource() == cancel) {
			user.setText("");
			pass.setText("");
		}

	}// actionPerformed
	
	/**
	 * This method receives two parameters which will then be 
	 * sent to the database class which will add the user to
	 * the datase.
	 * @param user String value taken from the user input
	 * @param pass String value taken from the user input
	 */

	private void insertStudentInfo(String user, String pass) {
		try {
			dbEngine.insertQuery(user, pass);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "SQLException Error" + e.getMessage());
		} // end catch
	}// end getStudentInfo
	
	
	/**
	 * This method receives a parameter which will then be 
	 * sent to the database class which will search for the
	 * users inputed StudentID.
	 * @param i int value taken from the user input
	 */
	private int getStudentID(int i) {
		// String tempId = id,tempUser = user,tempPass = pass
		ResultSet rs = null;
		int returnedID = 0;
		try {
			rs = dbEngine.searchStudentID(i);
			while (rs.next()) {
				returnedID = rs.getInt("StudentID");
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "SQLException Error" + e.getMessage());

		} // end catch
		dbEngine.closeConnection();
		return returnedID;
	}// end getStudentInfo
	
	/**
	 * This method will be called when the user
	 * has registered a user or wishes to by exiting.
	 */
	private void createFrame() {
		new Login();
		dispose();

	}

}// end class

package BookData;

import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

public class SystemAdminSearchBook extends JFrame implements ActionListener {// start class
	JButton search, select,cancel;
	JLabel title, ISBN, genre, holder, holderTitle;
	JTextField titleText, genreText, isbnText;
	String Qtitle = "", Qisbn = "", Qgenre = "";
	sqlEngine dbEngine = new sqlEngine();
	int tempisbn = 0;
	int reply;
	String strisbn = "";
	static int count = 1;

	public static void main(String[] args) {// start main method
		SystemAdminSearchBook admin = new SystemAdminSearchBook();
		admin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}// end main method
	
	//Constructor for JFrame
	public SystemAdminSearchBook() {
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

		holderTitle = new JLabel("Search Book Menu");
		holderTitle.setFont(new Font("Serif", Font.BOLD, 30));
		c.gridx = 5;
		c.gridy = 4;
		this.getContentPane().add(holderTitle, c);

		ISBN = new JLabel("ISBN : ");
		ISBN.setFont(new Font("Serif", Font.BOLD, 30));
		ISBN.setForeground(green);
		c.gridx = 4;
		c.gridy = 5;
		c.insets = new Insets(20, 20, 20, 20);
		this.getContentPane().add(ISBN, c);

		isbnText = new JTextField();
		c.gridx = 5;
		c.gridy = 5;
		isbnText.setPreferredSize(new Dimension(130, 30));
		this.getContentPane().add(isbnText, c);

		title = new JLabel("Title : ");
		title.setFont(new Font("Serif", Font.BOLD, 30));
		title.setForeground(green);
		c.gridx = 4;
		c.gridy = 6;
		c.insets = new Insets(20, 20, 20, 20);
		title.setVisible(false);
		this.getContentPane().add(title, c);

		titleText = new JTextField();
		c.gridx = 5;
		c.gridy = 6;
		titleText.setPreferredSize(new Dimension(130, 30));
		titleText.setVisible(false);
		this.getContentPane().add(titleText, c);

		genre = new JLabel("Genre : ");
		genre.setFont(new Font("Serif", Font.BOLD, 30));
		genre.setForeground(green);
		c.insets = new Insets(20, 20, 20, 20);
		c.gridx = 4;
		c.gridy = 7;
		genre.setVisible(false);
		this.getContentPane().add(genre, c);

		genreText = new JTextField();
		c.insets = new Insets(20, 20, 20, 20);
		c.gridx = 5;
		c.gridy = 7;
		genreText.setPreferredSize(new Dimension(130, 30));
		genreText.setVisible(false);
		this.getContentPane().add(genreText, c);

		search = new JButton("Search");
		c.gridx = 5;
		c.gridy = 8;
		search.addActionListener(this);
		this.getContentPane().add(search, c);
		
		cancel = new JButton("Cancel");
		c.gridx = 5;
		c.gridy = 9;
		cancel.addActionListener(this);
		this.getContentPane().add(cancel, c);

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

		if (e.getSource() == search) {
			Qisbn = isbnText.getText();
			while (Qisbn.equals("") || !Qisbn.equals("d++")){
				reply = JOptionPane.showConfirmDialog(null,
						"Error you must enter a valid ISBN would you like to try again", null,
						JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION){
					String tempi = JOptionPane.showInputDialog("Please Enter a valid ISBN");
					isbnText.setText(tempi);
					Qisbn = isbnText.getText();
					if (!Qisbn.equals("")){
						break;
					}
				}
				if (reply == JOptionPane.NO_OPTION) {
					int reply2 = JOptionPane.showConfirmDialog(null, "You are sure you would like to exit", null,
							JOptionPane.YES_NO_OPTION);
					if (reply2 == JOptionPane.YES_OPTION) {
						JOptionPane.showMessageDialog(null, "Thank you for using the ITB Library system");
						System.exit(0);
					}
				}
			}
			tempisbn = Integer.parseInt(Qisbn);
			searchBook(tempisbn);
		}
		else if(e.getSource() == cancel){
			isbnText.setText("");
		}

	}


	/**
	 * This class is for the search of a book in the database. It takes one
	 * parameter of int which is tempisbn . This is where
	 * the GUI will interact with the database it will pass the parameter
	 * to the database and if they find a match will display the searched book.
	 * It handles the closing of the database connection also.
	 * 
	 * @param ID int for the tempisbn input
	 * 
	 */

	private void searchBook(int i) {
		boolean flag = true;
		ResultSet rs = null;
		try {
			rs = dbEngine.searchBookQuery(i);

			while (rs.next()) {
				tempisbn = rs.getInt("ISBN");
				Qtitle = rs.getString("Title");
				Qgenre = rs.getString("Genre");
				title.setVisible(true);
				titleText.setVisible(true);
				genre.setVisible(true);
				genreText.setVisible(true);
				strisbn = Integer.toString(tempisbn);
				isbnText.setText(strisbn);
				titleText.setText(Qtitle);
				genreText.setText(Qgenre);
				count++;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "SQLException Error" + e.getMessage());
			e.printStackTrace();
		} // end catch

		if (count > 1) {
			int reply2 = JOptionPane.showConfirmDialog(null, "Would you like to search for a book", null,
					JOptionPane.YES_NO_OPTION);
			search.setVisible(false);
			cancel.setVisible(false);
			if(reply2 == JOptionPane.NO_OPTION){
				flag = false;
				endFrame();
				dispose();
			}
		}
		while (flag) {
			int tempid = 0;
			String tempi = JOptionPane.showInputDialog("Please Enter a valid ISBN");
			search.setVisible(false);
			cancel.setVisible(false);
			if (tempi == null) {
				endFrame();
				break;
			}
			tempid = Integer.parseInt(tempi);
			i = tempid;
			try {
				rs = dbEngine.searchBookQuery(i);

				while (rs.next()) {
					tempisbn = rs.getInt("ISBN");
					Qtitle = rs.getString("Title");
					Qgenre = rs.getString("Genre");
					title.setVisible(true);
					titleText.setVisible(true);
					genre.setVisible(true);
					genreText.setVisible(true);
					strisbn = Integer.toString(tempisbn);
					isbnText.setText(strisbn);
					titleText.setText(Qtitle);
					genreText.setText(Qgenre);
					count++;
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "SQLException Error" + e.getMessage());
				e.printStackTrace();
			} // end catch
		}

	}// end getStudentInfo
	
	/**
	 * This method is for to bring the user back to 
	 * the main menu
	 */
	private void endFrame() {
		new SystemAdmin();
		dbEngine.closeConnection();
		dispose();
	}

}// end class

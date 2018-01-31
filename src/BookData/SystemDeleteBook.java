package BookData;

import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

public class SystemDeleteBook extends JFrame implements ActionListener {// start class
	JButton cancel, deleteBook;
	JLabel title, ISBN, holder;
	JTextField isbnText;
	String Qisbn = "";
	sqlEngine dbEngine = new sqlEngine();
	int tempisbn;

	public static void main(String[] args) {// start main method
		SystemDeleteBook admin = new SystemDeleteBook();
		admin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}// end main method

	// Constructor for JFrame
	public SystemDeleteBook() {
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
		Color green = Color.decode("#107c10");

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 0, 0);
		c.gridx = 5;
		c.gridy = 3;
		this.getContentPane().add(title, c);

		holder = new JLabel("Delete Book Menu");
		holder.setFont(new Font("Serif", Font.BOLD, 30));
		c.gridx = 5;
		c.gridy = 4;
		this.getContentPane().add(holder, c);

		ISBN = new JLabel("ISBN : ");
		ISBN.setFont(new Font("Serif", Font.BOLD, 30));
		ISBN.setForeground(green);
		c.gridx = 4;
		c.gridy = 6;
		c.insets = new Insets(20, 20, 20, 20);
		this.getContentPane().add(ISBN, c);

		isbnText = new JTextField();
		c.gridx = 5;
		c.gridy = 6;
		isbnText.setPreferredSize(new Dimension(130, 30));
		this.getContentPane().add(isbnText, c);

		deleteBook = new JButton("Delete Book");
		c.gridx = 5;
		c.gridy = 7;
		deleteBook.addActionListener(this);
		this.getContentPane().add(deleteBook, c);

		cancel = new JButton("Cancel");
		c.gridx = 5;
		c.gridy = 8;
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
	 * current JFrame false and displys the new one called. The error checking
	 * will be handled by JOptionPane for the user to recover from errors.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == deleteBook) {
			Qisbn = isbnText.getText();
			while (Qisbn.equals("") || !Qisbn.equals("d++")) {
				int reply = JOptionPane.showConfirmDialog(null, "You must enter valid inforation in all textfields",
						null, JOptionPane.YES_NO_OPTION);
				deleteBook.setVisible(false);
				cancel.setVisible(false);
				if (reply == JOptionPane.YES_OPTION || isbnText.getText().isEmpty()) {
					isbnText.setText("");
					String tempi = JOptionPane.showInputDialog("Please Enter a valid ISBN");
					isbnText.setText(tempi);

					// populate the variables to be sent to the query if not
					// null
					Qisbn = isbnText.getText();

					if (!Qisbn.equals("") || Qisbn.equals("d++")) {
						break;
					}

				} else {
					JOptionPane.showMessageDialog(null, "You Choose to exit the system");
					System.exit(0);
				}

			} // end while
			Qisbn = isbnText.getText();
			tempisbn = Integer.parseInt(Qisbn);
			deleteBook(tempisbn);
		} else if (e.getSource() == cancel) {
			isbnText.setText("");
		}

	}

	/**
	 * This method is used to send a parameter to a database to delete that book
	 * that matches the ISBN entered.
	 * 
	 * @param isbn
	 */
	private void deleteBook(int isbn) {
		int searchIsbn;
		
		try {
			dbEngine.deleteBookQuery(isbn);
			searchIsbn = dbEngine.getDelete();
			if(searchIsbn == 1){
				JOptionPane.showMessageDialog(null, "Book with the ISBN " + isbn + " deleted");
				endFrame();
			}
			if(searchIsbn == 0){
				JOptionPane.showMessageDialog(null, "ISBN not found");
				endFrame();
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "SQLException Error" + e.getMessage());
		} // end catch
	}// end deleteBook

	/**
	 * This method is used for to call the Jframe if the user wishes to exit the
	 * insert menu.
	 */
	private void endFrame() {
		new SystemAdmin();
		dbEngine.closeConnection();
		dispose();
	}

}// end class

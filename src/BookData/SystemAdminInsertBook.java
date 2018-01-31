package BookData;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.util.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SystemAdminInsertBook extends JFrame implements ActionListener {
	JLabel Title,genre,holder,author;
	JTextField titleField, genreField,authorField;
	ImageIcon image;
	JButton insert, cancel;
	sqlEngine dbEngine = new sqlEngine();
	String titleText = "", genreText = "",authorText ="";
	int tempid = 0;
	public static void main(String[] args) {// start main method

		SystemAdminInsertBook reg = new SystemAdminInsertBook();
		reg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}// end main method
	
	//constructor for the JFrame
	public SystemAdminInsertBook() {
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
		holder = new JLabel(image);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 0, 0);
		c.gridx = 5;
		c.gridy = 3;
		this.getContentPane().add(holder, c);

		Color green = Color.decode("#107c10");
		
		holder = new JLabel("Insert Book Menu");
		holder.setFont(new Font("Serif", Font.BOLD, 30));
		c.gridx = 5;
		c.gridy = 4;
		this.getContentPane().add(holder, c);

		Title = new JLabel("Title : ");
		Title.setFont(new Font("Serif", Font.BOLD, 30));
		Title.setForeground(green);
		c.gridx = 4;
		c.gridy = 6;
		c.insets = new Insets(20, 20, 20, 20);
		this.getContentPane().add(Title, c);

		titleField = new JTextField();
		c.gridx = 5;
		c.gridy = 6;
		titleField.setPreferredSize(new Dimension(130, 30));
		this.getContentPane().add(titleField, c);

		genre = new JLabel("Genre: ");
		genre.setFont(new Font("Serif", Font.BOLD, 30));
		genre.setForeground(green);
		c.insets = new Insets(20, 20, 20, 20);
		c.gridx = 4;
		c.gridy = 7;
		this.getContentPane().add(genre, c);

		genreField = new JTextField();
		c.insets = new Insets(20, 20, 20, 20);
		c.gridx = 5;
		c.gridy = 7;
		genreField.setPreferredSize(new Dimension(130, 30));
		this.getContentPane().add(genreField, c);
		
		
		author = new JLabel("Author: ");
		author.setFont(new Font("Serif", Font.BOLD, 30));
		author.setForeground(green);
		c.insets = new Insets(20, 20, 20, 20);
		c.gridx = 4;
		c.gridy = 8;
		this.getContentPane().add(author, c);

		authorField = new JTextField();
		c.insets = new Insets(20, 20, 20, 20);
		c.gridx = 5;
		c.gridy = 8;
		authorField.setPreferredSize(new Dimension(130, 30));
		this.getContentPane().add(authorField, c);

		insert = new JButton("Insert");
		c.gridx = 5;
		c.gridy = 9;
		insert.addActionListener(this);
		this.getContentPane().add(insert, c);

		cancel = new JButton("Cancel");
		c.gridx = 5;
		c.gridy = 10;
		cancel.addActionListener(this);
		this.getContentPane().add(cancel, c);

	}// end buildGUI
	
	/**
	 * This method handles all the actions from the user when they select
	 * the buutons on the GUI. If they select the insert button it will 
	 * take two parameters of String which will send to a method. To insert into
	 * a database. If the user selects the cancel button it will clear the textfields.
	 * The error checking will be handled by JOptionPane for the user to recover from
	 * errors.  
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == insert) {
			while (titleField.getText().isEmpty() || genreField.getText().equals("")) {
				int reply = JOptionPane.showConfirmDialog(null, "You must enter valid inforation in all textfields",
				null, JOptionPane.YES_NO_OPTION);
				insert.setVisible(false);
				cancel.setVisible(false);
				if (reply == JOptionPane.YES_OPTION || titleField.getText().isEmpty()|| genreField.getText().equals("")) {
					titleField.setText("");
					genreField.setText("");
					String tempt = JOptionPane.showInputDialog("Please Enter a valid Title");
					String tempg = JOptionPane.showInputDialog("Please Enter a valid Genre");
					titleField.setText(tempt);
					genreField.setText(tempg);
					// populate the variables to be sent to the query if not
					// null
					
					titleText = titleField.getText();
					genreText = genreField.getText();
					System.out.println(titleText + genreText);
					if ((!titleText.equals("")) && (!genreText.equals(""))) {
						break;
					}

				} else {
					JOptionPane.showMessageDialog(null, "You Choose to exit the system");
					System.exit(0);
				}

			} // end while
			
			titleText = titleField.getText();
			genreText = genreField.getText();
			authorText = authorField.getText();
			// query method with the variables
			
			insertBook(titleText, genreText);
			tempid = getISBN();
			JOptionPane.showMessageDialog(null, "Thank You for registering a book the ISBN is " + tempid);
			createFrame();

		} else if (e.getSource() == cancel) {
			titleField.setText("");
			genreField.setText("");
		}

	}// actionPerformed

	private void insertBook(String title, String genre) {
		try {
			dbEngine.insertBookQuery(title, genre);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "SQLException Error" + e.getMessage());
		} // end catch
	}// end getStudentInfo
	
	/**
	 * This method is used to search for the ISBN and return that value.
	 * @return int value for the ISBN.
	 */
	private int getISBN() {
		ResultSet rs = null;
		int returnedID = 0;
		try {
			rs = dbEngine.searchISBN();
			while(rs.next()){
				returnedID = rs.getInt("ISBN");	
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "SQLException Error" + e.getMessage());

		} // end catch
		dbEngine.closeConnection();
		return returnedID;
	}// end getStudentInfo
	
	/**
	 * This method is used for to call the 
	 * Jframe if the user wishes to exit the insert menu.
	 */
	private void createFrame() {
		new SystemAdmin();
		dispose();

	}

}// end class

package BookData;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;
import java.awt.event.ActionListener;

import java.awt.Color;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.Insets;
import java.awt.event.ActionEvent;

public class SystemAdmin extends JFrame implements ActionListener {// start class
	JButton addBook, deleteBook, search,main;
	JLabel title;
	sqlEngine dbEngine = new sqlEngine();

	public static void main(String[] args) {// start main method
		SystemAdmin admin = new SystemAdmin();
		admin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}// end main method
	
	//Constructor for JFrame
	public SystemAdmin() {
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

		search = new JButton("Search Book");
		c.gridx = 5;
		c.gridy = 7;
		search.addActionListener(this);
		this.getContentPane().add(search, c);
		
		addBook = new JButton("Insert Book");
		c.gridx = 5;
		c.gridy = 8;
		addBook.addActionListener(this);
		this.getContentPane().add(addBook, c);


		deleteBook = new JButton("Delete Book");
		c.gridx = 5;
		c.gridy = 9;
		deleteBook.addActionListener(this);
		this.getContentPane().add(deleteBook, c);
		
		main = new JButton("Main Menu");
		c.gridx = 5;
		c.gridy = 10;
		main.addActionListener(this);
		this.getContentPane().add(main, c);

	}// end buildGUI

	/**
	 * This method is for the handling of the button actions. When the user
	 * clicks the various buttons of the GUI. It executes these actions. When
	 * the user clicks the search button it will bring the user to the menu 
	 * for searching for books. The addBook will allow the user to insert a 
	 * book into the database. The delete button will remove that book from 
	 * the database. The error checking will be handled by JOptionPane for the 
	 * user to recover from errors.  
	 */
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == search) {
			new SystemAdminSearchBook();
			dispose();
		}
		else if(e.getSource() == addBook){
			new SystemAdminInsertBook();
			dispose();
		}
		else if(e.getSource() == deleteBook ){
			new SystemDeleteBook();
			dispose();
		}
		else if(e.getSource() == main){
			new Login();
			dispose();
		}

	}

	


}// end class

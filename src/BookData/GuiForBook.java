package BookData;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import javax.swing.table.*;

/**
 * this class is a GUI Font end for a mySql database It takes a book database
 * and will display the database in a JTable
 * 
 * @author manue
 *
 */
public class GuiForBook extends JFrame implements ListSelectionListener {
	
	// table for book details
	JTable bookTable = null;
	// scroll pane for bookTable
	JScrollPane scrollPane;
	// database engine for creating DB connection
	sqlEngine dbEngine = new sqlEngine();
	ResultSet rs;
	ResultSetMetaData rsmd = null;
	int colCount = 0;
	String[] colNames = null;
	
	/**
	 * Constructor for GUIBook JFrame calls constructor in super class
	 * 
	 * @param title
	 *            The title of the GuiBook JFrame
	 */
	public GuiForBook(String title) {
		super(title);
		init();
		Container cp = getContentPane();
		JPanel panel = new JPanel(new GridBagLayout());
		JLabel label = new JLabel(" Choose From Available Books: ");

		panel.setBorder(new EmptyBorder(10,10,10,10));
		label.setFont(new Font("Serif", Font.BOLD, 32));
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
	    panel.add(label, c);
	    c.ipady = 200;  
	    c.ipadx = 300;
	    c.gridy = 1;

	    
	    panel.add(bookTable, c);
		panel.setBackground(Color.magenta);
		cp.add(panel);
		setLocation(550,50);	
	}

	/**
	 * Create connection to the DB and call {@link #getBookDetail()
	 * getBookDetail}. this call will be triggered by a user in real
	 */
	public void init() {
		dbEngine.connect();
		getBookDetail();
	}

	/**
	 * method to retrieve book details from the database and display them in a
	 * JTable.
	 */
	public void getBookDetail() {

		// select everything from book table
		// get the column names from the ResultSet metadata
		try {
			rs = dbEngine.searchBook();
			rsmd = rs.getMetaData();
			colCount = rsmd.getColumnCount();
			colNames = new String[colCount];
			for (int i = 1; i <= colCount; i++) {
				colNames[i-1] = rsmd.getColumnName(i);
			}
			// JTables have a view class and a Model class, the view class
			// handles the drawing of the JTable, the Model class handles the
			// properties and the data
			// Create a table model (used for controlling a JTable)
			DefaultTableModel model = new DefaultTableModel(colNames, 0);
			bookTable = new JTable(model);
			
			// ListSelectionModel represents the current state of
			// the selection for components (like JTables)
			DefaultListSelectionModel dlsm = new DefaultListSelectionModel();
			// allow single selection only from bookTable
			dlsm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			bookTable.setSelectionModel(dlsm);
			dlsm.addListSelectionListener(this); // inner anonymous class
			String[] currentRow = new String[colCount];// array to hold the row
														// data
			while (rs.next()) { // move the rs pointer on to the next record
								// (starts before the 1st)
				for (int i = 1; i <= colCount; i++) {
					currentRow[i - 1] = rs.getString(i);
				}
				model.addRow(currentRow); // add the row to the table through
											// the table model
			}
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		}
		scrollPane = new JScrollPane(bookTable);// add the table to a scroll
												// pane
		this.getContentPane().add(scrollPane);
	}

	/**
	 * Main class instantiate the frame GUIForBook
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		GuiForBook gui = new GuiForBook("ITB Books");
		gui.pack();
		gui.setVisible(true);
	}

	/**
	 * ListSelectionEvent will loop through book table and return the String
	 * selectedData
	 * 
	 * @param selectedData
	 *            String that has the value selected from table with mouse click
	 *            and used in option dialog and message dialog to notify user
	 *            about his choice
	 */

	public void valueChanged(ListSelectionEvent e) {

		String selectedData = null;

		int []selectedRow = bookTable.getSelectedRows();
		int[] selectedColumns = bookTable.getSelectedColumns();

		for (int i = 0; i < selectedRow.length; i++) {
			for(int j=0; j<selectedColumns.length;j++){
				selectedData = (String) bookTable.getValueAt(selectedRow[i],selectedColumns[j]);			
			}
		}
		Object[] options = { "Yes", "No" };
		int n = JOptionPane.showOptionDialog(bookTable, "Reserve Book ?", selectedData, JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		// return choice
		if (n == 0) {
			JOptionPane.showMessageDialog(bookTable, "You Reserved " + selectedData, "Your Reserved Book",
					JOptionPane.PLAIN_MESSAGE, null);
			System.exit(0);
		} else {
			JOptionPane.showMessageDialog(bookTable, "Try Another", "Message", JOptionPane.PLAIN_MESSAGE, null);
		}
	}
}

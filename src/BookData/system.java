package BookData;
import javax.swing.*;

public class system extends JFrame {//start class

	public static void main(String[] args) {//start main method
		system frame = new system();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}//end main method
	
	public system(){
		setSize(300,300);
		setVisible(true);
		JButton b = new JButton("Welcome");
		getContentPane().add(b);
	}

}//end class

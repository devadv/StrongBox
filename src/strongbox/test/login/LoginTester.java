package strongbox.test.login;

import javax.swing.JFrame;

public class LoginTester {

	public static void main(String[] args) {
		JFrame frame = new JFrame("StrongBox");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 100);
		frame.setLocation(800, 300);
		frame.add(new LoginPanel());
		frame.setVisible(true);

	}

}

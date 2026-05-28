package kakeiboo;
import javax.swing.JFrame;
public class Main {

	public static void main(String[] args) {
		JFrame frame = new JFrame("家計簿");
		
		UI ui;
		try {
			ui = new UI();
		
		
		frame.add(ui);
		
		frame.setSize(900,700);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

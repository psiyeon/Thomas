import javax.swing.JFrame;

public class main extends JFrame {
	public static void main(String[] args) {
		String sample[]= {"대구,영천,12월,20일,18:00,9009,3,A5"}; //출발지, 행선지, 월, 일, 출발시간, 도착시간, 열차번호, 좌석번호 
	
		
//		File_IO file_object = new File_IO();
//		file_object.file_read("train.csv");
//		file_object.file_write(sample,"reservation.csv");
		GUI display = new GUI();
		display.set_content();
		
	}
}

import javax.swing.JFrame;

public class main extends JFrame {
	public static void main(String[] args) {
		String sample[]= {"�뱸,��õ,12��,20��,18:00,9009,3,A5"}; //�����, �༱��, ��, ��, ��߽ð�, �����ð�, ������ȣ, �¼���ȣ 
	
		
//		File_IO file_object = new File_IO();
//		file_object.file_read("train.csv");
//		file_object.file_write(sample,"reservation.csv");
		GUI display = new GUI();
		display.set_content();
		
	}
}

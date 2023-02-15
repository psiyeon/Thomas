
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.ChangedCharSetException;

public class GUI extends JFrame {
	public GUI() {


	}

	public String res_in_progress[]=new String[11];
	public int repalce_value;
	public int col_index;
	public ArrayList<String[]> readByID_data= new ArrayList<String[]>();
	public int tiket_amount=0;
	public Search_Panel search_panel = null;
	public Current_info current_info = null;
	public Train_list train_list = null;
	public Seat_list seat_list = null;
	public Login_panel login_panel  =null;
	public Join_panel join_panel  =null;
	

	private File_IO file_object= new File_IO();
	private base_Panel panelName = null;

	public int get_class = 0;

	void change(GUI win, base_Panel panelName) {

		getContentPane().removeAll();
		if (get_class == 1) {
			win.panelName = new Current_info(win);
		} else if (get_class == 2) {
			win.panelName = new Train_list(win);
		} else if (get_class == 3) {
			win.panelName = new Seat_list(win);
		} else if (get_class == 4) {
			win.panelName = new Search_Panel(win);
		}else if (get_class == 5) {
			win.panelName = new Login_panel(win);
		}else if (get_class == 6) {
			win.panelName = new Join_panel(win);
		}

		win.getContentPane().add(win.panelName);
		revalidate();
		repaint();
	}

	void set_content() {
		GUI gui_frame = new GUI();
		gui_frame.setTitle("철도 예약 시스템");
		gui_frame.login_panel = new Login_panel(gui_frame);

		gui_frame.getContentPane().add(gui_frame.login_panel);
		gui_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		gui_frame.setSize(350, 450);
		gui_frame.setVisible(true);
		
	}
	static String convertString(String[] strArr, String delimiter) {
		StringBuilder sb = new StringBuilder();
		for(String str : strArr)
			sb.append(str).append(delimiter);
		return sb.substring(0, sb.length()-1);
	}

	void merge_reservation() {
		String ret[] = new String[1];
		String s=null;
		s = convertString(res_in_progress, ",");
		ret[0]=s;
		file_object.file_write(ret, "reservation.csv");
		readByID_data =file_object.readById("reservation.csv", res_in_progress[9]);

	}
	void merge_train(GUI win,String[] key_line,int col_index,int change_value) {
		int row_index = 0;
		int s = 0;
		ArrayList<String[]> origindata = file_object.file_read("train.csv");
		for(int i =0; i<origindata.size();i++) {
			for(int j=0; j<5;j++) {
				if (key_line[0].equals(origindata.get(i)[0]) && key_line[1].equals(origindata.get(i)[1]) &&key_line[2].equals(origindata.get(i)[2]) && key_line[3].equals(origindata.get(i)[3]) && key_line[4].equals(origindata.get(i)[4])) {
					s=(Integer.parseInt(origindata.get(i)[col_index]) + change_value);
					row_index = i;
				}
			}
		}
		String repalce_value= Integer.toString(s);
		file_object.file_point_replace("train.csv",row_index,col_index, repalce_value);
		readByID_data=file_object.readById("reservation.csv",res_in_progress[9]);
	}
}

class base_Panel extends JPanel {
	private GUI win;
	protected JButton before_btn = new JButton("◀");
	private JButton current_info_btn = new JButton("예매 내역보기");
	protected JLabel chulbal = new JLabel("출발");
	protected JLabel dochak = new JLabel("도착");
	protected JLabel right_arrow = new JLabel("▶");

	protected JPanel[] panels = new JPanel[10];
	protected JPanel mainMenu = new JPanel();
	protected JPanel bottomMenu = new JPanel();
	protected JPanel border_panel = new JPanel();

	protected GridBagConstraints gbc = new GridBagConstraints();
	protected GridBagConstraints bottom_gbc = new GridBagConstraints();
	protected GridBagConstraints notfill_gbc = new GridBagConstraints();
	protected ArrayList<String[]> tlist= new ArrayList<String[]>();
	protected ArrayList<String[]> reservation_lsit = new ArrayList<String[]>();

	protected File_IO file_object = new File_IO();


	static String contry_list[] = {"서대전", "논산", "익산", "광주", "목포","서울", "대전", "구미", "대구", "부산"};
	static String contry_list_honam[] = { "서울", "대전", "구미", "대구", "부산"};
	static String contry_list_guengbu[] = {"서대전", "논산", "익산", "광주", "목포"};

	static String month_list[] = { "12월" };
	static String day_list[] = { "15일","16일","17일" };
	String time_list[] = { "12시" };

	void CommonStyle(GUI win) {

		this.win = win;
		win.setLayout(null);
		win.setLayout(new GridBagLayout());
		border_panel.setBorder(new TitledBorder(new LineBorder(Color.black), "info"));

		for (int i = 0; i < panels.length; i++) {
			panels[i] = new JPanel(new GridBagLayout());
			gbc.fill = GridBagConstraints.BOTH;
			gbc.weightx = 1;
			gbc.weighty = 1;
			gbc.gridy = i;
			gbc.insets = new Insets(5, 15, 5, 15);
			win.add(panels[i], gbc);
		}
		bottom_gbc.insets = new Insets(0, 15, 0, 0);
		bottom_gbc.fill = GridBagConstraints.HORIZONTAL;
		bottom_gbc.weightx = 1;
		bottom_gbc.weighty = 1;
		panels[9].add(before_btn, bottom_gbc);
		bottom_gbc.weightx = 3;
		panels[9].add(current_info_btn, bottom_gbc);
		current_info_btn.addActionListener(new current_info_ActionLister());
	}

	class current_info_ActionLister implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			win.get_class = 1;
			win.change(win, win.current_info);
		}
	}

}

class Login_panel extends base_Panel{
	ArrayList<String[]> logindata =new ArrayList<String[]>();
	private JTextField ID = new JTextField();
	private JTextField PW = new JTextField();
	private JButton login_btn = new JButton("로그인");
	private JButton join_btn = new JButton("회원가입");
	private File_IO file_object= new File_IO();
	private GUI win;

	public Login_panel(GUI win){
		this.win = win;

		CommonStyle(win);
		panels[2].add(new JLabel("ID:"),gbc);
		panels[2].add(ID,gbc);
		panels[3].add(new JLabel("Password:"),gbc);
		panels[3].add(PW,gbc);
		panels[4].add(login_btn,gbc);
		panels[5].add(join_btn,gbc);
		login_btn.addActionListener(new Login());
		join_btn.addActionListener(new Join());


	}
	class Login implements ActionListener { 
		@Override
		public void actionPerformed(ActionEvent e) {
			logindata = file_object.file_read("Login.csv");
			for(int i=0; i<logindata.size(); i++) {
				if(logindata.get(i)[0].equals(ID.getText())) {
					if(logindata.get(i)[1].equals(PW.getText())) {
						win.res_in_progress[9] = logindata.get(i)[0];
						win.res_in_progress[10] = "N"; 
						//매진석 예약의 기본값을 N으로 두고 , 매진석 자동예약시 reservation에 Y로 표기하여 저장. 누가 그열차 반환시 인덱스가 Y인것 찾아서 N으로 바꿔주면 예약완료(현재예약 내역에 N/Y로 반영시킬 예정)
						win.readByID_data=file_object.readById("reservation.csv",win.res_in_progress[9]); //[추가] 
						System.out.println(win.readByID_data);
						System.out.println(win.res_in_progress[9]);

						win.get_class = 4;
						win.change(win, win.search_panel);
					}
				}
			}
		}
	}
	class Join implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			win.get_class = 6;
			win.change(win, win.join_panel);
		}
	}
}

class Join_panel extends base_Panel{
	private File_IO file_object= new File_IO();
	private GUI win;
	private JTextField ID = new JTextField();
	private JTextField PW = new JTextField();
	private JButton new_join = new JButton("가입하기");
	private JButton go_login_panel = new JButton("로그인 화면");
	ArrayList<String[]> logindata =new ArrayList<String[]>();


	public Join_panel(GUI win) {
		this.win =win;
		CommonStyle(win);
		panels[2].add(new JLabel("ID:"),gbc);
		panels[2].add(ID,gbc);
		panels[2].add(new JLabel("password:"),gbc);
		panels[2].add(PW,gbc);
		panels[4].add(new_join, gbc);
		panels[5].add(go_login_panel,gbc);
		new_join.addActionListener(new new_join_action());
		go_login_panel.addActionListener(new go_login());
	}
	class new_join_action implements ActionListener{//박시연 :중복체크, 빈칸체크 만들어주세요

		@Override
		public void actionPerformed(ActionEvent e) {
			if (ID.getText().isEmpty()||PW.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "모든 정보를 입력해주세요.");
			}
			else {
				logindata = file_object.file_read("Login.csv");
				boolean tf=false;
				for(int i=0; i<logindata.size(); i++) {
					if(logindata.get(i)[0].equals(ID.getText())) {
						JOptionPane.showMessageDialog(null, "이미 존재하는 ID입니다.");
						tf=false;
					}
					else{
						tf=true;
					}
				}
				if(tf) {
					String user_info[] = {ID.getText()+","+PW.getText()};
					file_object.file_write(user_info,"Login.csv");
					JOptionPane.showMessageDialog(null,"회원 가입이 완료되었습니다.");
				}
			}
		}
	}
	class go_login implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			win.get_class = 5;
			win.change(win, win.login_panel);
		}

	}
}

class Search_Panel extends base_Panel {

	private GUI win;
	private JButton Lookup_btn = new JButton("조회하기");
	private File_IO file_object = new File_IO();
	private static JComboBox start_CB = new JComboBox(contry_list);
	private static JComboBox end_CB = new JComboBox();

	private static JComboBox month_CB = new JComboBox(month_list);
	private static JComboBox day_CB = new JComboBox(day_list);
	private JComboBox time_CB = new JComboBox(time_list);
	private JTextField poeple = new JTextField(10);

	Search_Panel() {}

	public static String getStart() {
		Object s = start_CB.getSelectedItem();
		return (String) s;
	}
	public static String getEnd() {
		Object e = end_CB.getSelectedItem();
		return (String) e;
	}
	public static String getMonth() {
		Object m = month_CB.getSelectedItem();
		return (String) m;
	}
	public static String getDay() {
		Object d = day_CB.getSelectedItem();
		return (String) d;
	}



	public Search_Panel(GUI win) {


		this.win = win;
		CommonStyle(win);

		chulbal.setHorizontalAlignment(JLabel.CENTER);
		dochak.setHorizontalAlignment(JLabel.CENTER);
		notfill_gbc.weightx = 1;
		panels[0].add(chulbal, notfill_gbc);
		panels[0].add(dochak, notfill_gbc);
		panels[1].add(start_CB, notfill_gbc);
		notfill_gbc.weightx = 0.1;

		panels[1].add(right_arrow, notfill_gbc);
		notfill_gbc.weightx = 1;
		panels[1].add(end_CB, notfill_gbc);

		panels[2].add(new JLabel("출발시간"), notfill_gbc);
		panels[2].add(month_CB, notfill_gbc);
		panels[2].add(day_CB, notfill_gbc);
		panels[2].add(time_CB, notfill_gbc);
		panels[3].setLayout(new FlowLayout(FlowLayout.LEFT, 17, 0));

		panels[3].add(new JLabel("승객수"));
		panels[3].add(poeple);
		panels[3].add(new JLabel("명"));
		Lookup_btn.addActionListener(new go_listpanel());
		panels[8].add(Lookup_btn, bottom_gbc);
		start_CB.addActionListener(new route_select());
	}

	class go_listpanel implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (poeple.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "모든 정보를 입력해주세요.");
			}
			int num = Integer.parseInt(poeple.getText());
			if ((num+win.readByID_data.size()) >= 10) { //reservation파일의 예매내역 + 현재 예매할려는 매표수 계산
				JOptionPane.showMessageDialog(null, "10장 이상의 표는 예약하실 수 없습니다.");
			} else {
				win.tiket_amount=num;
				win.get_class = 2;
				win.change(win, win.train_list);
			}
		}
	} //end go_listpanel

	class route_select implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			end_CB.removeAllItems();
			for(int i =0; i< contry_list_guengbu.length; i++) {
				if(getStart().toString() == contry_list_guengbu[i]) {
					for(String s : contry_list_guengbu) {
						end_CB.addItem(s); 						
					}
				}
				else if(getStart().toString() == contry_list_honam[i]){
					for(String s : contry_list_honam) {
						end_CB.addItem(s); 						
					}
				}
			}
		}
	} //end route_select
}

class Current_info extends base_Panel {
	private GUI win;
	private int i = 0;
	private JLabel[] start_contry = new JLabel[10];
	private JLabel[] end_contry = new JLabel[10];
	private JLabel[] month = new JLabel[10];
	private JLabel[] day = new JLabel[10];
	private JLabel[] start_time = new JLabel[10];
	private JLabel[] end_time = new JLabel[10];
	private JLabel[] train_num= new JLabel[10];
	private JLabel[] comp_num = new JLabel[10];
	private JLabel[] seat_num = new JLabel[10];
	private JButton[]  return_tiket= new JButton[10];

	public Current_info(GUI win) {
		this.win = win;
		CommonStyle(win);
		for (int i=0; i<win.readByID_data.size(); i++) {
			panels[i].setLayout(new GridLayout(2,4,5,5));
			panels[i].add(start_contry[i] = new JLabel(win.readByID_data.get(i)[0]+" ▶ "+win.readByID_data.get(i)[1]),notfill_gbc);
			panels[i].add(month[i] = new JLabel(win.readByID_data.get(i)[2]+" "+win.readByID_data.get(i)[3]),notfill_gbc);
			panels[i].add(start_time[i] = new JLabel(win.readByID_data.get(i)[4]+" ▶ "+win.readByID_data.get(i)[5]),notfill_gbc);
			panels[i].add(new JLabel(" "),notfill_gbc);
			panels[i].add(train_num[i] = new JLabel(win.readByID_data.get(i)[6]),notfill_gbc);
			panels[i].add(comp_num[i] = new JLabel("칸: "+win.readByID_data.get(i)[7]),notfill_gbc);
			panels[i].add(seat_num[i] = new JLabel("좌석: "+win.readByID_data.get(i)[8]),notfill_gbc);
			panels[i].add(return_tiket[i] = new JButton("반환"),notfill_gbc);
			return_tiket[i].addActionListener(new re_tiket());

		}

		before_btn.addActionListener(new before_btn_ActionListener());
	}
	public class re_tiket implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {

			ArrayList<String[]> alldata =file_object.file_read("reservation.csv");

			String[] ret = new String[11];
			for(int i =0; i<return_tiket.length;i++) {
				if(e.getSource()==return_tiket[i]) {
					for(int j=0; j<11;j++) {
						ret[j]=win.readByID_data.get(i)[j];
					}
				}
			}

			//			
			int cnt=0;
			int col_index=0;
			int change_value=0;
			String[] ret_temp= Arrays.copyOfRange(ret, 0, 6);
			for (String[] newlist : alldata) {//먼저 자동 예약자가 있으면 예약을 해준뒤 delete해야한다.
				String[] newlist_temp = Arrays.copyOfRange(newlist, 0, 6);
				
				if(Arrays.toString(ret_temp).equals(Arrays.toString(newlist_temp)) && newlist[10].equals("Y")){
					file_object.file_point_replace("reservation.csv", cnt, 7,ret[7]);
					file_object.file_point_replace("reservation.csv", cnt, 8,ret[8]);
					file_object.file_point_replace("reservation.csv", cnt, 10,ret[10]);
					System.out.println("goal!");
					change_value =change_value-1;
					break;
				}cnt++;
			}
			cnt=0;
			for (String[] newlist : alldata) {
				if(Arrays.toString(newlist).equals(Arrays.toString(ret))) {
					file_object.file_delete_reservation("reservation.csv", cnt); 
					if(ret[10].equals("Y")) {
						change_value= change_value -1;
						col_index = 7;
					}
					else if(ret[10].equals("YS")) {
						col_index = 8;//입석 
					}else {
						col_index = 7;//일반좌석
					}


					win.merge_train(win, ret, col_index, change_value+1);
					win.get_class = 4;
					win.change(win, win.search_panel);
				}cnt++;
			}
		}
	}

	public class before_btn_ActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			win.get_class = 4;
			win.change(win, win.search_panel);
		}
	}
}

class Train_list extends base_Panel {
	private GUI win;
	private JButton[] seatSelect = new JButton[5];
	private ArrayList<String[]> temp = new ArrayList<String[]>();
	Search_Panel sp = new Search_Panel();

	public Train_list(GUI win) {
		this.win = win;

		CommonStyle(win);
		win.res_in_progress[10] = "N"; 
		notfill_gbc.weightx = 1;
		panels[0].add(new JLabel(sp.getStart()), notfill_gbc);
		panels[0].add(right_arrow, notfill_gbc);
		panels[0].add(new JLabel(sp.getEnd()), notfill_gbc);
		win.remove(panels[1]);
		gbc.weighty =1.0;
		gbc.gridx = 0;
		gbc.gridy = 1;
		win.add(panels[1], gbc);

		panels[1].add(new JLabel(sp.getMonth()+" "+sp.getDay()), notfill_gbc);

		before_btn.addActionListener(new before_btn_ActionListener());
		tlist=file_object.file_read("train.csv");
		//tlist 출[0] 도[1] 월[2] 일[3] 시간[4]
		int j=0;


		for (int i = 0; i < tlist.size(); i++) {
			if (tlist.get(i)[0].contentEquals(sp.getStart()) && tlist.get(i)[1].contentEquals(sp.getEnd())&&tlist.get(i)[2].contentEquals(sp.getMonth())&&tlist.get(i)[3].contentEquals(sp.getDay())) {
				temp.add(tlist.get(i));

				panels[j + 2].add(new JLabel("열차번호 " + tlist.get(i)[6]+" / "), notfill_gbc);
				panels[j + 2].add(new JLabel("출발  " + tlist.get(i)[4]+" / "), notfill_gbc);
				panels[j + 2].add(new JLabel("도착   " + tlist.get(i)[4]), notfill_gbc);

				if(tlist.get(i)[7].contentEquals("0")) {
					panels[j + 2].add(seatSelect[j] = new JButton("매진"), notfill_gbc);
					//					win.tlist_point = i; 
				}else
					panels[j + 2].add(seatSelect[j] = new JButton("선택"), notfill_gbc);

				if(seatSelect[j].getText()=="매진")
					seatSelect[j].addActionListener(new sold_out());            				
				else
					seatSelect[j].addActionListener(new go_seatlist());
				j++;
				for(int a=0; a<4; a++) {
					win.res_in_progress[a]=tlist.get(i)[a];
				}
			}
		}
	}
	class go_seatlist implements ActionListener {
		int flag=0;
		@Override
		public void actionPerformed(ActionEvent e) {
			
			for(int i=0; i<seatSelect.length; i++) {
				if(e.getSource()==seatSelect[i]) {
					win.res_in_progress[4]=temp.get(i)[4];
					win.res_in_progress[5]=temp.get(i)[5];
					win.res_in_progress[6]=temp.get(i)[6];
					win.res_in_progress[7]="1";
				}
			}
			win.get_class = 3;
			win.change(win, win.seat_list);
		}
	}

	class sold_out implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			for(int i=0; i<seatSelect.length; i++) {
				if(e.getSource()==seatSelect[i]) {
					win.res_in_progress[4]=temp.get(i)[4];
					win.res_in_progress[5]=temp.get(i)[5];
					win.res_in_progress[6]=temp.get(i)[6];
					if(temp.get(i)[8].equals("0")) {
						win.res_in_progress[10]="NS";
					}
				}
			}

			JD soldout=new JD(win);
			soldout.setVisible(true);

		}
	}

	public class before_btn_ActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			win.get_class = 4;
			win.change(win, win.search_panel);
		}
	}
}


class Seat_list extends base_Panel {
	private GUI win;
	private String[] traing_comp_number = { "1호차", "2호차", "3호차" };
	private String[][][] seat_position = new String[3][4][5];
	private JButton[] seat_button = new JButton[20];
	private JComboBox train_comp_number = new JComboBox(traing_comp_number);
	private JPanel[] seat_panel= new JPanel[3];
	private int flag=0;
	private ArrayList<String[]> origindata = file_object.file_read("reservation.csv");
	
	public Seat_list(GUI win) {
		this.win = win;
		CommonStyle(win);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panels[0].add(train_comp_number, gbc);
		panels[1].add(new JLabel("△:순방향   ▽:역방향   ▲▼:매진  "), notfill_gbc);
		panels[1].add(new JLabel(" "), notfill_gbc);
		panels[1].add(new JLabel(" "), notfill_gbc);
		for(int i=2;i<8;i++) 
			win.remove(panels[i]);

		gbc.gridx=0;
		gbc.gridy=2;
		gbc.weighty= 10000;
		gbc.fill = GridBagConstraints.BOTH;
		panels[2].setBorder(new TitledBorder(new LineBorder(Color.black), "좌석"));
		panels[2].setLayout(new GridBagLayout());
		win.add(panels[2], gbc);

		GridBagConstraints seat_gbc = new GridBagConstraints();
		for(int i=0; i<seat_panel.length;i++) {
			seat_panel[i]= new JPanel(new GridLayout(5,2,2,15));// row, column, 좌 우margin, 상 하margin
			seat_gbc.fill = GridBagConstraints.BOTH;
			seat_gbc.weightx= 1;
			seat_gbc.gridx = i;
			seat_gbc.gridy = 0;
			seat_gbc.insets = new Insets(5, 0, 5, 0);
			panels[2].add(seat_panel[i],seat_gbc);
			if(i != 1) { //to not add button to panel[1]
				for(int j=0; j<(seat_button.length/2); j++) {
					seat_panel[i].add(seat_button[j+flag] = new JButton(""+((j/2)+1)));
					seat_button[j+flag].addActionListener(new seat_button_action()); 
					
				}
				flag =10;
			}
		}
		int index=0;
		String s= Arrays.toString(Arrays.copyOfRange(win.res_in_progress,0,8));
		System.out.println(s);
		for(String[] newline :origindata) {
			if(s.equals(Arrays.toString(Arrays.copyOfRange(newline , 0, 8)))) {
				seat_button[Integer.parseInt(origindata.get(index)[8])].setEnabled(false);
			}
			index++;
		}

		train_comp_number.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				win.res_in_progress[7] =Integer.toString(train_comp_number.getSelectedIndex()+1) ;
				win.change(win, win.seat_list);
				win.get_class = 3;
				
			}
		});


		before_btn.addActionListener(new before_btn_ActionListener());
	}
	public class before_btn_ActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			win.get_class = 2;
			win.change(win, win.train_list);
		}
	}
	public class seat_button_action implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			for(int i=0; i<seat_button.length; i++) {
				if(e.getSource() == seat_button[i]) 
					win.res_in_progress[8]=""+i ;
			}

			win.merge_reservation();
			win.merge_train(win, win.res_in_progress,7,-1);
			win.change(win,win.seat_list);
			win.get_class=3;
		}
	}


}

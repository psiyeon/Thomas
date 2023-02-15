import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class JD extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private GUI win = new GUI();
	private File_IO file_object = new File_IO();

	/**
	 * Launch the application.
	 */


	
	
	
	/**
	 * Create the dialog.
	 */
	public JD(GUI win) {
		this.win =win;
		setBounds(100, 100, 334, 194);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JButton btnNewButton = new JButton("�ڵ� ����");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				win.res_in_progress[7]="������";
				win.res_in_progress[8]="������";
				win.res_in_progress[10]="Y";
				win.merge_reservation();
				dispose();
			}
		});
		btnNewButton.setBounds(115, 97, 91, 23);
		contentPanel.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("�� �¼��� ������ �����Դϴ�.");
		lblNewLabel.setFont(new Font("����", Font.BOLD, 15));
		lblNewLabel.setBounds(39, 45, 228, 23);
		contentPanel.add(lblNewLabel);
		
		JButton btnNewButton_1 = new JButton("�Լ� ����");
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(win.res_in_progress[10].equals("NS")) {
					System.out.println(win.res_in_progress[10]);
					JOptionPane.showMessageDialog(null, "���� �Լ�Ƽ���� �����ϴ�.");

				}else {
					win.res_in_progress[7]="�Լ�";
					win.res_in_progress[8]="�Լ�";
					win.res_in_progress[10]="YS";
					win.merge_reservation();
					win.merge_train(win, win.res_in_progress,8,-1);
					dispose();
				}
			}
		});
		btnNewButton_1.setBounds(12, 97, 91, 23);
		contentPanel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("���");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton_2.setBounds(218, 97, 91, 23);
		contentPanel.add(btnNewButton_2);
	}
}

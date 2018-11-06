package linage;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class LinageMR extends JFrame {

	private JPanel contentPane;
	private JTextField txtShellInputTap;
	private JTextField textField_1;
	private JTextField txtstoragesdcard;
	private JTextField txtAdb;
	
	private JList list;
	private JLabel lblNewLabel_4;

	public Timer m_timer = null;
	public TimerTask m_task = null;
	
	public int num = 0;
	public int preNum = 0;
	
	public boolean f_flag = false;
	public boolean flag_first = true;
	public boolean flag_end = false;
	public boolean flag_tread = false;
	public boolean flag_tread_time1 = false;
	public boolean flag_tread_time2 = false;
	public boolean flag_tread_time3 = false;
	public boolean flag_tread_time4 = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)throws
    UnknownHostException, IOException, InterruptedException  {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LinageMR frame = new LinageMR();
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
	
	private JTextField txtstoragesdcardapplogtxt;
	private JTextField txtShellInputTap_1;
	private JTextField txtstoragesdcardapplogtxt_1;
	private JTextField txtShellInputTap_2;
	private JTextField txtstoragesdcardapplogtxt_2;
	private JTextField txtShellInputTap_3;
	private JTextField textField;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	
	public ArrayList<String> getDevices() {
		ArrayList<String> returnValue = new ArrayList<>();
		try {
			String command = txtAdb.getText()+" "+"devices";
			String[] commands = new String[] { command };
			for (String cmd : commands) {
				Process process = Runtime.getRuntime().exec(cmd);
				InputStream inputStream = process.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String inputString = null;
				int i=0;
				while ((inputString = bufferedReader.readLine()) != null) {
					if(inputString.contains("device")) {
						System.out.println(i+"::"+inputString.split("device")[0]);
						if(i>0)
						returnValue.add(inputString.split("device")[0].trim());
					}
					i++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		System.out.println(returnValue);
		return returnValue;
	}
	
	public String getIpaddr(String e_n) {
		String returnValue = "";
		try {
			String command = txtAdb.getText()+" "+"-s "+e_n+" shell ip addr show wlan0";
			String[] commands = new String[] { command };
			for (String cmd : commands) {
				Process process = Runtime.getRuntime().exec(cmd);
				InputStream inputStream = process.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String inputString = null;
				int i=0;
				while ((inputString = bufferedReader.readLine()) != null) {
					System.out.println(i+"::"+inputString);
					try {
						if(inputString.contains("inet")) {
							returnValue = inputString.split(" ")[5].split("\\/")[0];
							break;
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					i++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		System.out.println(returnValue);
		return returnValue;
	}
	
	public void setPort(String e_n) {
		try {
			String command = txtAdb.getText()+" "+"-s "+e_n+" tcpip 5555";
			Runtime.getRuntime().exec(command);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	public void setConnect(String e_n) {
		try {
			String command = txtAdb.getText()+" "+"connect "+getIpaddr(e_n);
			Runtime.getRuntime().exec(command);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	public void start1(String devices, String shell) {
		if(flag_tread_time1) {
			return;
		}
		flag_tread_time1 = true;
		Timer mti = new Timer();
		TimerTask mta = new TimerTask() {
			@Override
			public void run() {
				flag_tread_time1 = false;
				System.out.println("실행:flag_tread_time1:"+flag_tread_time1);
			}
		};
		mti.schedule(mta, 1500);
		String command = txtAdb.getText();
		command += " "+"-s"+" "+devices;
		command += " "+shell;
		String[] commands = new String[] { command };
		excuteCmd(commands, false);
	}
	
	public void start2(String devices, String shell) {
		if(flag_tread_time2) {
			return;
		}
		flag_tread_time2 = true;
		Timer mti = new Timer();
		TimerTask mta = new TimerTask() {
			@Override
			public void run() {
				flag_tread_time2 = false;
				System.out.println("실행:flag_tread_time2:"+flag_tread_time2);
			}
		};
		mti.schedule(mta, 1500);
		String command = txtAdb.getText();
		command += " "+"-s"+" "+devices;
		command += " "+shell;
		String[] commands = new String[] { command };
		excuteCmd(commands, false);
	}
	
	public void start3(String devices, String shell) {
		if(flag_tread_time3) {
			return;
		}
		flag_tread_time3 = true;
		Timer mti = new Timer();
		TimerTask mta = new TimerTask() {
			@Override
			public void run() {
				flag_tread_time3 = false;
				System.out.println("실행:flag_tread_time3:"+flag_tread_time3);
			}
		};
		mti.schedule(mta, 1500);
		String command = txtAdb.getText();
		command += " "+"-s"+" "+devices;
		command += " "+shell;
		String[] commands = new String[] { command };
		excuteCmd(commands, false);
	}
	
	public void start4(String devices, String shell) {
		if(flag_tread_time4) {
			return;
		}
		flag_tread_time4 = true;
		Timer mti = new Timer();
		TimerTask mta = new TimerTask() {
			@Override
			public void run() {
				flag_tread_time4 = false;
				System.out.println("실행:flag_tread_time4:"+flag_tread_time4);
			}
		};
		mti.schedule(mta, 1500);
		String command = txtAdb.getText();
		command += " "+"-s"+" "+devices;
		command += " "+shell;
		String[] commands = new String[] { command };
		excuteCmd(commands, false);
	}
	
	public void close() {
		try {
			System.out.println("종료");
			lblNewLabel_4.setText("종료");
			f_flag = false;
			if(m_task != null) {
				m_task.cancel();
				m_task = null;
			}
			if(m_timer != null) {
				m_timer.cancel();
				m_timer.purge();
				m_timer = null;
			}
			flag_first = true;
			flag_end = true;
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public String excuteCmd(String[] commands, boolean returnValueFlag) {
		String returnValue = "";
		Process process = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		try {
			if(returnValueFlag) {
				for (String cmd : commands) {
					Date today = new Date();
				    SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
					System.out.println(date.format(today)+" "+cmd);
					process = Runtime.getRuntime().exec(cmd);
					inputStream = process.getInputStream();
					inputStreamReader = new InputStreamReader(inputStream);
					bufferedReader = new BufferedReader(inputStreamReader);
					String inputString = null;
					while ((inputString = bufferedReader.readLine()) != null) {
						returnValue = inputString;
					}
				}
			} else {
				for (String cmd : commands) {
					Date today = new Date();
				    SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
					System.out.println(date.format(today)+" "+cmd);
					Runtime.getRuntime().exec(cmd);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(bufferedReader!=null) {
				try {
					bufferedReader.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(inputStreamReader!=null) {
				try {
					inputStreamReader.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(inputStream!=null) {
				try {
					inputStream.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(process!=null) {
				try {
					process.destroy();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return returnValue;
	}

	/**
	 * Create the frame.
	 */
	public LinageMR() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 510, 479);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("포트설정");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null!=list.getSelectedValue()) {
					if(!list.getSelectedValue().toString().contains(":")) {
						setPort(list.getSelectedValue().toString());
					}
				}
				System.out.println(list.getSelectedValue());
			}
		});
		btnNewButton.setBounds(169, 94, 97, 23);
		contentPane.add(btnNewButton);
		
		JButton button = new JButton("연결");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null!=list.getSelectedValue()) {
					if(!list.getSelectedValue().toString().contains(":")) {
						setConnect(list.getSelectedValue().toString());
					}
				}
				System.out.println(list.getSelectedValue());
			}
		});
		button.setBounds(278, 63, 97, 54);
		contentPane.add(button);
		
		txtShellInputTap = new JTextField();
		txtShellInputTap.setText("shell input tap 750 650");
		txtShellInputTap.setBounds(68, 170, 198, 21);
		contentPane.add(txtShellInputTap);
		txtShellInputTap.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("시작");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					flag_end = false;
					lblNewLabel_4.setText("실행중");
					if(m_timer == null) {
						m_timer = new Timer();
						m_task = new TimerTask() {
							@Override
							public void run() {
								System.out.println("실행중");
								// TODO Auto-generated method stub
								String returnValue = "";
								try {
									if("".equals(txtAdb.getText())) {
										close();
									} else if("".equals(textField_1.getText())) {
										close();
									} else if("".equals(txtstoragesdcard.getText())) {
										close();
									} else if("".equals(textField_1.getText())) {
										close();
									} else if("".equals(txtShellInputTap.getText())) {
										close();
									}
									String command = txtAdb.getText();
									command += " "+"-s"+" "+textField_1.getText();
									command += " "+"shell cat"+" "+txtstoragesdcard.getText();
									String[] commands = new String[] { command };
									returnValue = excuteCmd(commands, true);
									num = Integer.parseInt(returnValue.split("::")[2]);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									lblNewLabel_4.setText("에러 : " + returnValue);
								}
								if(!f_flag) {
									f_flag = true;
									System.out.println("정지");
								} else {
									if(preNum < num) {
										start1(textField.getText(),txtShellInputTap.getText());
									}
								}
								preNum = num;
							}
						};
						m_timer.schedule(m_task, 1000, 100);
					}
					flag_tread = true;
				} catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(278, 146, 97, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("종료");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		btnNewButton_2.setBounds(381, 146, 97, 23);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("새로고침");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				list.setListData(getDevices().toArray());
			}
		});
		btnNewButton_3.setBounds(169, 63, 97, 23);
		contentPane.add(btnNewButton_3);
		
		textField_1 = new JTextField();
		textField_1.setBounds(68, 32, 198, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("파일");
		lblNewLabel.setBounds(12, 150, 44, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("명령어");
		lblNewLabel_1.setBounds(12, 173, 44, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("디바이스");
		lblNewLabel_2.setBounds(12, 35, 57, 15);
		contentPane.add(lblNewLabel_2);
		
		txtstoragesdcard = new JTextField();
		txtstoragesdcard.setText("/storage/sdcard0/app_log.txt");
		txtstoragesdcard.setBounds(68, 147, 198, 21);
		contentPane.add(txtstoragesdcard);
		txtstoragesdcard.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("ADB");
		lblNewLabel_3.setBounds(12, 10, 44, 15);
		contentPane.add(lblNewLabel_3);
		
		txtAdb = new JTextField();
		txtAdb.setText("adb");
		txtAdb.setBounds(68, 7, 307, 21);
		contentPane.add(txtAdb);
		txtAdb.setColumns(10);
		
		JButton btnNewButton_4 = new JButton("선택");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null!=list.getSelectedValue()) {
					textField_1.setText(list.getSelectedValue().toString());
				}
				System.out.println(list.getSelectedValue());
			}
		});
		btnNewButton_4.setBounds(278, 31, 97, 23);
		contentPane.add(btnNewButton_4);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 63, 145, 54);
		contentPane.add(scrollPane);
		
		list = new JList();
		scrollPane.setViewportView(list);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setListData(getDevices().toArray());
		
		JButton btnNewButton_5 = new JButton("실행");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!"".equals(textField.getText())) {
					start1(textField.getText(),txtShellInputTap.getText());
					try {
						flag_first = true;
						flag_end = true;
					} catch (Exception e1) {
					}
				}
				System.out.println(textField.getText());
			}
		});
		btnNewButton_5.setBounds(278, 169, 97, 23);
		contentPane.add(btnNewButton_5);
		
		lblNewLabel_4 = new JLabel("종료");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(381, 170, 97, 21);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("파일");
		lblNewLabel_5.setBounds(12, 227, 44, 15);
		contentPane.add(lblNewLabel_5);
		
		JLabel label = new JLabel("명령어");
		label.setBounds(12, 250, 44, 15);
		contentPane.add(label);
		
		JLabel lblNewLabel_6 = new JLabel("파일");
		lblNewLabel_6.setBounds(12, 305, 44, 15);
		contentPane.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("명령어");
		lblNewLabel_7.setBounds(12, 328, 44, 15);
		contentPane.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("파일");
		lblNewLabel_8.setBounds(12, 383, 44, 15);
		contentPane.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("명령어");
		lblNewLabel_9.setBounds(12, 406, 44, 15);
		contentPane.add(lblNewLabel_9);
		
		txtstoragesdcardapplogtxt = new JTextField();
		txtstoragesdcardapplogtxt.setText("/storage/sdcard0/app_log_2.txt");
		txtstoragesdcardapplogtxt.setBounds(68, 224, 198, 21);
		contentPane.add(txtstoragesdcardapplogtxt);
		txtstoragesdcardapplogtxt.setColumns(10);
		
		txtShellInputTap_1 = new JTextField();
		txtShellInputTap_1.setText("shell input tap 750 650");
		txtShellInputTap_1.setBounds(68, 247, 198, 21);
		contentPane.add(txtShellInputTap_1);
		txtShellInputTap_1.setColumns(10);
		
		txtstoragesdcardapplogtxt_1 = new JTextField();
		txtstoragesdcardapplogtxt_1.setText("/storage/sdcard0/app_log_3.txt");
		txtstoragesdcardapplogtxt_1.setBounds(68, 302, 198, 21);
		contentPane.add(txtstoragesdcardapplogtxt_1);
		txtstoragesdcardapplogtxt_1.setColumns(10);
		
		txtShellInputTap_2 = new JTextField();
		txtShellInputTap_2.setText("shell input tap 750 650");
		txtShellInputTap_2.setBounds(68, 325, 198, 21);
		contentPane.add(txtShellInputTap_2);
		txtShellInputTap_2.setColumns(10);
		
		txtstoragesdcardapplogtxt_2 = new JTextField();
		txtstoragesdcardapplogtxt_2.setText("/storage/sdcard0/app_log_4.txt");
		txtstoragesdcardapplogtxt_2.setBounds(68, 380, 198, 21);
		contentPane.add(txtstoragesdcardapplogtxt_2);
		txtstoragesdcardapplogtxt_2.setColumns(10);
		
		txtShellInputTap_3 = new JTextField();
		txtShellInputTap_3.setText("shell input tap 700 650");
		txtShellInputTap_3.setBounds(68, 403, 198, 21);
		contentPane.add(txtShellInputTap_3);
		txtShellInputTap_3.setColumns(10);
		
		JButton btnNewButton_6 = new JButton("시작");
		btnNewButton_6.setBounds(278, 223, 97, 23);
		contentPane.add(btnNewButton_6);
		
		JButton btnNewButton_7 = new JButton("실행");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!"".equals(textField_2.getText())) {
					start2(textField_2.getText(), txtShellInputTap_1.getText());
					try {
						flag_first = true;
						flag_end = true;
					} catch (Exception e1) {
					}
				}
				System.out.println(textField_2.getText());
			}
		});
		btnNewButton_7.setBounds(278, 246, 97, 23);
		contentPane.add(btnNewButton_7);
		
		JButton btnNewButton_8 = new JButton("종료");
		btnNewButton_8.setBounds(381, 223, 97, 23);
		contentPane.add(btnNewButton_8);
		
		JButton btnNewButton_9 = new JButton("시작");
		btnNewButton_9.setBounds(278, 301, 97, 23);
		contentPane.add(btnNewButton_9);
		
		JButton btnNewButton_10 = new JButton("종료");
		btnNewButton_10.setBounds(381, 301, 97, 23);
		contentPane.add(btnNewButton_10);
		
		JButton btnNewButton_11 = new JButton("실행");
		btnNewButton_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!"".equals(textField_3.getText())) {
					start3(textField_3.getText(),txtShellInputTap_2.getText());
					try {
						flag_first = true;
						flag_end = true;
					} catch (Exception e1) {
					}
				}
				System.out.println(textField_3.getText());
			}
		});
		btnNewButton_11.setBounds(278, 324, 97, 23);
		contentPane.add(btnNewButton_11);
		
		JButton btnNewButton_12 = new JButton("시작");
		btnNewButton_12.setBounds(278, 379, 97, 23);
		contentPane.add(btnNewButton_12);
		
		JButton btnNewButton_13 = new JButton("종료");
		btnNewButton_13.setBounds(381, 379, 97, 23);
		contentPane.add(btnNewButton_13);
		
		JButton btnNewButton_14 = new JButton("실행");
		btnNewButton_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!"".equals(textField_4.getText())) {
					start4(textField_4.getText(),txtShellInputTap_3.getText());
					try {
						flag_first = true;
						flag_end = true;
					} catch (Exception e1) {
					}
				}
				System.out.println(textField_4.getText());
			}
		});
		btnNewButton_14.setBounds(278, 402, 97, 23);
		contentPane.add(btnNewButton_14);
		
		JLabel lblNewLabel_10 = new JLabel("종료");
		lblNewLabel_10.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_10.setBounds(381, 247, 97, 21);
		contentPane.add(lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("종료");
		lblNewLabel_11.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_11.setBounds(381, 324, 97, 23);
		contentPane.add(lblNewLabel_11);
		
		JLabel label_1 = new JLabel("종료");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(381, 404, 97, 18);
		contentPane.add(label_1);
		
		JLabel lblNewLabel_12 = new JLabel("L1");
		lblNewLabel_12.setBounds(12, 127, 44, 15);
		contentPane.add(lblNewLabel_12);
		
		textField = new JTextField();
		textField.setBounds(68, 124, 198, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton_15 = new JButton("선택");
		btnNewButton_15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null!=list.getSelectedValue()) {
					textField.setText(list.getSelectedValue().toString());
				}
				System.out.println(list.getSelectedValue());
			}
		});
		btnNewButton_15.setBounds(278, 123, 97, 23);
		contentPane.add(btnNewButton_15);
		
		textField_2 = new JTextField();
		textField_2.setBounds(68, 201, 198, 21);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_13 = new JLabel("L2");
		lblNewLabel_13.setBounds(12, 204, 44, 15);
		contentPane.add(lblNewLabel_13);
		
		JButton btnNewButton_16 = new JButton("선택");
		btnNewButton_16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null!=list.getSelectedValue()) {
					textField_2.setText(list.getSelectedValue().toString());
				}
				System.out.println(list.getSelectedValue());
			}
		});
		btnNewButton_16.setBounds(278, 200, 97, 23);
		contentPane.add(btnNewButton_16);
		
		JButton btnNewButton_17 = new JButton("선택");
		btnNewButton_17.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null!=list.getSelectedValue()) {
					textField_3.setText(list.getSelectedValue().toString());
				}
				System.out.println(list.getSelectedValue());
			}
		});
		btnNewButton_17.setBounds(278, 278, 97, 23);
		contentPane.add(btnNewButton_17);
		
		textField_3 = new JTextField();
		textField_3.setBounds(68, 279, 198, 21);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel_14 = new JLabel("L3");
		lblNewLabel_14.setBounds(12, 282, 44, 15);
		contentPane.add(lblNewLabel_14);
		
		JButton btnNewButton_18 = new JButton("선택");
		btnNewButton_18.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null!=list.getSelectedValue()) {
					textField_4.setText(list.getSelectedValue().toString());
				}
				System.out.println(list.getSelectedValue());
			}
		});
		btnNewButton_18.setBounds(278, 356, 97, 23);
		contentPane.add(btnNewButton_18);
		
		textField_4 = new JTextField();
		textField_4.setBounds(68, 357, 198, 21);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblL = new JLabel("L4");
		lblL.setBounds(12, 360, 44, 15);
		contentPane.add(lblL);
		
		JButton btnNewButton_19 = new JButton("소켓시작");
		btnNewButton_19.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				soket();
				new Thread(new Runnable() {
			        @Override
			        public void run() {
						try {
							if(server==null) {
					            server = new ServerSocket(9999);
					            Socket socket = null;
					            System.out.println("Server Opend");
					            while ((socket = server.accept()) != null) {
									String ip = (((InetSocketAddress) socket.getRemoteSocketAddress()).getAddress()).toString().replace("/", "");
									endClient(socket);
					            	System.out.println("접속 ip > "+ip);
					                new ServerThread(socket).start();
					            }
					            server.close();
							}
				        } catch (Exception e1) {
				        }
			        }
				}).start();
			}
		});
		btnNewButton_19.setBounds(381, 63, 97, 23);
		contentPane.add(btnNewButton_19);
		
		JButton btnNewButton_20 = new JButton("소켓종료");
		btnNewButton_20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
						if(server!=null) {
							System.out.println("close complate");
							server.close();
							server = null;
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		});
		btnNewButton_20.setBounds(381, 94, 97, 23);
		contentPane.add(btnNewButton_20);
		
		JButton button_1 = new JButton("캡쳐");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						excuteCp();
					}
				}).start();
			}
		});
		button_1.setBounds(381, 31, 97, 23);
		contentPane.add(button_1);
	}
	
	public void excuteCp() {
		while(true) {
			if(!"".equals(textField_1.getText())) {
				String command = txtAdb.getText();
				command += " "+"-s"+" "+textField_1.getText();
//				command += " "+"shell screenrecord --time-limit 1 /sdcard/screenrecord-sample.mp4";
				command += " "+"shell screencap -p /sdcard/screen.png";
				String[] commands = new String[] { command };
				excuteCmd(commands, true);
			} else {
				break;
			}
		}
	}
	
	public void excuteCpOne() {
		if(!"".equals(textField_1.getText())) {
			String command = txtAdb.getText();
			command += " "+"-s"+" "+textField_1.getText();
//			command += " "+"shell screenrecord --time-limit 1 /sdcard/screenrecord-sample.mp4";
			command += " "+"shell screencap -p /sdcard/screen.png";
			String[] commands = new String[] { command };
			excuteCmd(commands, true);
		}
	}
	
	ServerSocket server;
	
	static List<ConnectionToClient> clients = new ArrayList<>();

	public class ServerThread extends Thread {
        Socket socket;
        ConnectionToClient conToClient;
        ServerThread(Socket socket) {
            this.socket = socket;
             conToClient = new ConnectionToClient(socket);
             clients.add(conToClient); 
        }
        public void run() {
            try {
                String input = "";
                while( (input = conToClient.read())!=null){
                	if(!input.contains("app")) {
						System.out.println("생성 시작"+" "+input.trim());
						excuteCpOne();
					}
					Lstart(input.trim());
					if(input.contains("app")) {
						System.out.println("빈값");
//						out.println("");
					} else {
						System.out.println("파싱 시작");
//						out.println("파싱 시작");
					}
    				String ip = (((InetSocketAddress) socket.getRemoteSocketAddress()).getAddress()).toString().replace("/", "");
                    System.out.println(ip+" > "+input);
                    conToClient.write(input);
                }
            } catch (Exception e) {
            }
        }
        public void sendToAll(String message){
            for(ConnectionToClient client :clients){
                client.write(message);
            }
        }
    }
    
    static void endClient(Socket socket) {
    	try {
    		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//    		System.out.println("--out.checkError():"+out.checkError());
//			System.out.println("--socket.isConnected():"+socket.isConnected());
//			System.out.println("--socket.getInputStream().read():"+socket.getInputStream().read());
    		String ip = (((InetSocketAddress) socket.getRemoteSocketAddress()).getAddress()).toString().replace("/", "");
//    		System.out.println("clients.size():"+clients.size());
        	for (int i = 0; i < clients.size(); i++) {
        		ConnectionToClient client = (ConnectionToClient)clients.get(i);
        		boolean connected = client.socket.isConnected() && ! client.socket.isClosed();
        		String ip_sub = (((InetSocketAddress) client.socket.getRemoteSocketAddress()).getAddress()).toString().replace("/", "");
            	out = new PrintWriter(client.socket.getOutputStream(), true);
//            	System.out.println("out.checkError():"+out.checkError());
//				System.out.println("client.socket.isConnected():"+client.socket.isConnected());
//				System.out.println("client.socket.getInputStream().read():"+client.socket.getInputStream().read());
//        		if(client.socket.getInputStream().read()==-1) {
    			if(ip.equals(ip_sub)) {
            		if(connected) {
            			try {
        					client.socket.close();
        					client.socket = null;
        				} catch (IOException e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				}
            		}
        		}
    		}
        	for (int i = clients.size()-1; i >= 0; i--) {
        		ConnectionToClient client = (ConnectionToClient)clients.get(i);
        		if(client.socket==null) {
        			clients.remove(i);
        			continue;
        		}
        		System.out.println(client.socket);
        	}
        	if(clients.size()==0) {
        		System.out.println(socket);
        	}
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    static class ConnectionToClient {
        Socket socket;
        BufferedReader br;
        ObjectOutputStream oos;

        ConnectionToClient(Socket socket) {
            this.socket = socket;
            try {
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                oos = new ObjectOutputStream(socket.getOutputStream());
            } catch (Exception e) {
            }
        }
        public String read(){
            try{
                return br.readLine();
            }catch(Exception e){
            	return null;
            } finally {
            	if(br!=null) {
            		/*try {
						br.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
            	}
			}
        }
        public void write(Object obj) {
            try {
            	System.out.println("전송:"+obj);
                oos.writeObject(obj);
                oos.flush();
            } catch (Exception e) {
            } finally {
				if(oos!=null) {
					/*try {
						oos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
				}
			}
        }
    }
	
	public void soket() {
		
		if(server==null) {

			new Thread(new Runnable() {

		        @Override
		        public void run() {
		        	
		    		try {

			    		//자동 close
			        	server = new ServerSocket();
		    			
		    			// 서버 초기화
		    			InetSocketAddress ipep = new InetSocketAddress(9999);
		    			server.bind(ipep);

		    			System.out.println("Initialize complate");
		    			
		    			while (true) {

		    				// LISTEN 대기
		    				Socket s = server.accept();

		    				System.out.println("Connection");
		    				
		    				InputStream in = s.getInputStream();
		                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
		                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);
		                    try {
		        				String line = null;
		        				while((line = br.readLine()) != null){
		        					if(!line.contains("app")) {
		        						System.out.println("생성 시작"+" "+line.trim());
		        						excuteCpOne();
		        					}
		        					Lstart(line.trim());
		        					if(line.contains("app")) {
		        						System.out.println("빈값");
		        						out.println("");
		        					} else {
		        						System.out.println("파싱 시작");
		        						out.println("파싱 시작");
		        					}
		        				}
		        			} catch(InterruptedIOException e) {
		           	      		e.printStackTrace();
		        			} catch(Exception e){
		        				e.printStackTrace();
		        			} finally {
		        				if (in != null) {
		        					in.close();
		        					in = null;
		        				}
		        				if (br != null) {
		        					br.close();
		        					br = null;
		        				}
		        				if (out != null) {
		        					out.close();
		        					out = null;
		        				}
		        	            if (s != null) {
		        	            	s.close();
		        	            	s = null;
		        	            }
		        			}
		    				
		    			}
		    			
		    		} catch (Throwable e) {
		    			e.printStackTrace();
		    		}
		    		
		        }
		        
		    }).start();
			
		}
		
	}
	
	public void Lstart(String msg) {
		if("app_log_1".equals(msg)) {
			if(!"".equals(textField.getText())&&!"".equals(txtShellInputTap.getText())) {
				start1(textField.getText(),txtShellInputTap.getText());
			}
		} else if("app_log_2".equals(msg)) {
			if(!"".equals(textField_2.getText())&&!"".equals(txtShellInputTap_1.getText())) {
				start2(textField_2.getText(),txtShellInputTap_1.getText());
			}
		} else if("app_log_3".equals(msg)) {
			if(!"".equals(textField_3.getText())&&!"".equals(txtShellInputTap_2.getText())) {
				start3(textField_3.getText(),txtShellInputTap_2.getText());
			}
		} else if("app_log_4".equals(msg)) {
			if(!"".equals(textField_4.getText())&&!"".equals(txtShellInputTap_3.getText())) {
				start4(textField_4.getText(),txtShellInputTap_3.getText());
			}
		}
	}
}

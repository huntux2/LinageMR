package linage;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
	
	public ArrayList<String> getDevices() {
		ArrayList<String> returnValue = new ArrayList<>();
		try {
			String command = "ADB devices";
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
			String command = "ADB -s "+e_n+" shell ip addr show wlan0";
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
			String command = "ADB -s "+e_n+" tcpip 5555";
			Runtime.getRuntime().exec(command);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	public void setConnect(String e_n) {
		try {
			String command = "ADB connect "+getIpaddr(e_n);
			Runtime.getRuntime().exec(command);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	public void start() {
		System.out.println("실행");
		String command = txtAdb.getText();
		command += " "+"-s"+" "+list.getSelectedValue().toString();
		command += " "+txtShellInputTap.getText();
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
		try {
			if(returnValueFlag) {
				for (String cmd : commands) {
					System.out.println(cmd);
					Process process = Runtime.getRuntime().exec(cmd);
					InputStream inputStream = process.getInputStream();
					InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
					BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
					String inputString = null;
					while ((inputString = bufferedReader.readLine()) != null) {
						returnValue = inputString;
					}
				}
			} else {
				for (String cmd : commands) {
					Runtime.getRuntime().exec(cmd);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		return returnValue;
	}

	/**
	 * Create the frame.
	 */
	public LinageMR() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 404, 346);
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
		btnNewButton.setBounds(169, 41, 97, 23);
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
		button.setBounds(278, 10, 97, 54);
		contentPane.add(button);
		
		txtShellInputTap = new JTextField();
		txtShellInputTap.setText("shell input tap 750 650");
		txtShellInputTap.setBounds(81, 226, 294, 21);
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
									} else if(null==list.getSelectedValue()) {
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
//									e.printStackTrace();
									lblNewLabel_4.setText("에러 : " + returnValue);
								}
								if(!f_flag) {
									f_flag = true;
									System.out.println("정지");
								} else {
									if(preNum < num) {
										start();
//										close();
//										JOptionPane.showMessageDialog(null, "완료");
									}
								}
								preNum = num;
							}
						};
						m_timer.schedule(m_task, 1000, 100);
					}
					/*
					if(m_timer == null) {
						m_timer = new Timer();
						m_task = new TimerTask() {
							String file_name = "C:\\Users\\YDH\\Downloads\\카카오토1.762\\LOG\\"+t1.getText();
							File file = new File(file_name);
							@Override
							public void run() {
//								file = file.listFiles()[file.listFiles().length - 1];
//								t1.setText(file.getName());
								// TODO Auto-generated method stub
								try {
									while(true) {
										in = new BufferedReader(new InputStreamReader(new FileInputStream(file_name),"UTF-8"));
										if(length<file.length()) {
											if(!flag_first) {
												System.out.println("귀환");
												start();
											} else {
												System.out.println("실행");
											}
											length = file.length();
											flag_first = false;
										}
										in.close();
										if(flag_end) {
											length = 0;
											flag_tread = false;
											System.out.println("완전종료");
											break;
										}
										try {
											Thread.sleep(500);
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						};
					}
					*/
//					m_timer.schedule(m_task, 1000, 100);
					flag_tread = true;
				} catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(22, 267, 97, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("종료");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		btnNewButton_2.setBounds(147, 266, 97, 23);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("새로고침");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				list.setListData(getDevices().toArray());
			}
		});
		btnNewButton_3.setBounds(169, 10, 97, 23);
		contentPane.add(btnNewButton_3);
		
		textField_1 = new JTextField();
		textField_1.setBounds(81, 85, 185, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("파일");
		lblNewLabel.setBounds(12, 194, 57, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("명령어");
		lblNewLabel_1.setBounds(12, 229, 57, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("디바이스");
		lblNewLabel_2.setBounds(12, 88, 57, 15);
		contentPane.add(lblNewLabel_2);
		
		txtstoragesdcard = new JTextField();
		txtstoragesdcard.setText("/storage/sdcard0/app_log_2.txt");
		txtstoragesdcard.setBounds(81, 191, 294, 21);
		contentPane.add(txtstoragesdcard);
		txtstoragesdcard.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("ADB");
		lblNewLabel_3.setBounds(12, 158, 57, 15);
		contentPane.add(lblNewLabel_3);
		
		txtAdb = new JTextField();
		txtAdb.setText("adb");
		txtAdb.setBounds(81, 155, 294, 21);
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
		btnNewButton_4.setBounds(278, 84, 97, 23);
		contentPane.add(btnNewButton_4);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 10, 145, 54);
		contentPane.add(scrollPane);
		
		list = new JList();
		scrollPane.setViewportView(list);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setListData(getDevices().toArray());
		
		JButton btnNewButton_5 = new JButton("실행");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null!=list.getSelectedValue()) {
					start();
					try {
						flag_first = true;
						flag_end = true;
					} catch (Exception e1) {
					}
				}
				System.out.println(list.getSelectedValue());
			}
		});
		btnNewButton_5.setBounds(268, 267, 97, 23);
		contentPane.add(btnNewButton_5);
		
		lblNewLabel_4 = new JLabel("종료");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(114, 116, 152, 29);
		contentPane.add(lblNewLabel_4);
	}
}

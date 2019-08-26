package linage;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Transparency;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import utils.IntegerDocument;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JRadioButton;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

public class LinageMR_20181219 extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JLabel lblNewLabel_5;
	private JTextField txtAdb;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_12;
	private JTextField textField_14;
	private JTextField textField_16;
	private JTextField textField_X;
	private JTextField textField_Y;
	private JTextField textField_S;
	private JTextField textField_RGB_D;
	private JButton button;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JButton btnNewButton_4;
	private JButton btnNewButton_5;
	private JButton btnNewButton_6;
	private JButton btnNewButton_7;
	private JButton btnNewButton_11;
	private JButton btnNewButton_10;
	private JButton button_1;
	private JButton button_2_1;
	private JButton button_4;
	private JList list;
	private JList list_1;
	private boolean execute = false;
	private NewWindow newWindow;
	private String ip = "";
	private String port = "9999";
	private String configFilePath = "";
	private String configFileName = "명령어.txt";
	
	public static ServerSocket server;
	public List<ConnectionToClient> clients = new ArrayList<>();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LinageMR_20181219 frame = new LinageMR_20181219();
					frame.setVisible(true);
					frame.addWindowListener(new WindowAdapter() {
				        @Override 
				        public void windowClosing(WindowEvent e) {
				        	close_soket();
				            System.exit(0);
				        }
				    });
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public LinageMR_20181219() {
		setTitle("YDH 편한 세상");
		
		try {
			//현재 파일 위치 세팅
			configFilePath = new File(LinageMR_20181219.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().toString()+"\\";
		} catch (URISyntaxException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 799, 616);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		JLabel lblAdb = new JLabel("ADB");
		lblAdb.setBounds(15, 95, 34, 20);
		contentPane.add(lblAdb);
		
		txtAdb = new JTextField();
		txtAdb.setText("adb");
		txtAdb.setBounds(50, 95, 620, 20);
		txtAdb.setColumns(10);
		contentPane.add(txtAdb);
		
		button = new JButton("파일찾기");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser();
				jfc.setCurrentDirectory(new File(configFilePath));
				jfc.setFileFilter(new FileNameExtensionFilter("EXE File","exe"));
                jfc.setMultiSelectionEnabled(false);
                if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                	if(jfc.getSelectedFile().toString().endsWith("adb.exe")) {
    					// showopendialog 열기 창을 열고 확인 버튼을 눌렀는지 확인
                    	txtAdb.setText(jfc.getSelectedFile().toString());
                	} else {
                		JOptionPane.showMessageDialog(null, "ADB 파일을 선택해주세요.");
                	}
				}
			}
		});
		button.setBounds(675, 95, 90, 20);
		contentPane.add(button);
		
		JButton btnNewButton = new JButton("포트설정");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(get_basic_check()) {
					setPort(list.getSelectedValue().toString());
				}
			}
		});
		btnNewButton.setBounds(300, 155, 90, 20);
		btnNewButton.setVisible(false);
		contentPane.add(btnNewButton);
		
		btnNewButton_1 = new JButton("새로고침");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(get_adb_check()) {
					list.setListData(getDevices().toArray());
				}
			}
		});
		btnNewButton_1.setBounds(200, 215, 90, 20);
		contentPane.add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("무선연결");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(get_basic_check()) {
					setPort(list.getSelectedValue().toString());
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					setConnect(list.getSelectedValue().toString());
				}
			}
		});
		btnNewButton_2.setBounds(200, 155, 90, 20);
		contentPane.add(btnNewButton_2);
		
		btnNewButton_11 = new JButton("무선해제");
		btnNewButton_11.setBounds(200, 185, 90, 20);
		btnNewButton_11.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(get_dis_basic_check()) {
					setDisConnect(list.getSelectedValue().toString());
				}
			}
		});
		contentPane.add(btnNewButton_11);
		
		JLabel lblNewLabel = new JLabel("디바이스 목록");
		lblNewLabel.setBounds(15, 130, 100, 20);
		contentPane.add(lblNewLabel);
		
		btnNewButton_3 = new JButton("캡쳐확인");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(get_adb_check()&&get_empty_check()) {
					JFileChooser jfc = new JFileChooser();
	                jfc.setMultiSelectionEnabled(false);
	                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
	                if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
//                    	excuteDeviceScreenCapImgPull(list.getSelectedValue().toString(), jfc.getSelectedFile().toString());
					}
				}
			}
		});
		btnNewButton_3.setBounds(300, 185, 90, 20);
		btnNewButton_3.setVisible(false);
		contentPane.add(btnNewButton_3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 155, 170, 80);
		contentPane.add(scrollPane);
		
		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list);
		
		list.setListData(getDevices().toArray());
		
		JLabel lblAdb_1 = new JLabel("*** ADB 명령을 환경변수에 지정 안했을 경우 파일위치 지정 필요 ***");
		lblAdb_1.setBounds(15, 65, 465, 20);
		contentPane.add(lblAdb_1);
		
		JLabel label = new JLabel("서버 아이피 :");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(15, 15, 90, 20);
		contentPane.add(label);
		
		setServierIp();
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(115, 15, 120, 20);
		lblNewLabel_1.setText(ip);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("서버 포트 :");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setBounds(15, 40, 90, 20);
		contentPane.add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setDocument(new IntegerDocument());
		textField.setText(port);
		textField.setBounds(115, 40, 40, 20);
		textField.setColumns(10);
		contentPane.add(textField);
		
		btnNewButton_4 = new JButton("실행");
		btnNewButton_4.setBounds(15, 510, 80, 20);
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(get_adb_check()&&get_empty_check()) {
					popupClose();
			    	excuteAllEnabled(false);
			    	execute = true;
					excuteStartServerSocket(false);
				}
			}
		});
		contentPane.add(btnNewButton_4);
		
		btnNewButton_5 = new JButton("종료");
		btnNewButton_5.setBounds(15, 535, 80, 20);
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excuteAllEnabled(true);
				close_soket();
			}
		});
		contentPane.add(btnNewButton_5);
		
		textField_1 = new JTextField();
		textField_1.setDocument(new IntegerDocument());
		textField_1.setText("0");
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setBounds(670, 155, 35, 20);
		textField_1.setColumns(10);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setDocument(new IntegerDocument());
		textField_2.setText("0");
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_2.setBounds(730, 155, 35, 20);
		textField_2.setColumns(10);
		contentPane.add(textField_2);
		
		btnNewButton_6 = new JButton("RGB 체크");
		btnNewButton_6.setBounds(545, 155, 100, 20);
		btnNewButton_6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(get_adb_check()&&get_empty_check()) {
					JFileChooser jfc = new JFileChooser();
					jfc.setCurrentDirectory(new File(configFilePath));
	                jfc.setMultiSelectionEnabled(false);
	                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	                if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	                	popupClose();
	                	excuteDeviceScreenCapImgPull(list.getSelectedValue().toString(), jfc.getSelectedFile().toString());
					}
				}
			}
		});
		contentPane.add(btnNewButton_6);
		
		JLabel lblX = new JLabel("X");
		lblX.setHorizontalAlignment(SwingConstants.CENTER);
		lblX.setBounds(650, 155, 15, 20);
		contentPane.add(lblX);
		
		JLabel lblNewLabel_6 = new JLabel("Y");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setBounds(710, 155, 15, 20);
		contentPane.add(lblNewLabel_6);
		
		lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setBackground(Color.WHITE);
		lblNewLabel_5.setBounds(545, 185, 45, 20);
		contentPane.add(lblNewLabel_5);
		
		createExcutePanel();

		setContentPane(contentPane);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(15, 275, 170, 230);
		contentPane.add(scrollPane_1);
		
		list_1 = new JList();
		scrollPane_1.setViewportView(list_1);
		setExecuteListData(false);
		
		JLabel lblNewLabel_10 = new JLabel("명령어 목록");
		lblNewLabel_10.setBounds(15, 250, 70, 20);
		contentPane.add(lblNewLabel_10);
		
		button_1 = new JButton("삭제");
		button_1.setBounds(105, 535, 80, 20);
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(list_1.getModel().getSize()>0) {
					delExecuteList();
				} else {
					JOptionPane.showMessageDialog(null, "삭제할 명령어가 없습니다.");
				}
			}
		});
		contentPane.add(button_1);
		
		JLabel lblNewLabel_9_1 = new JLabel("명령어");
		lblNewLabel_9_1.setBounds(200, 250, 45, 20);
		contentPane.add(lblNewLabel_9_1);
		
		btnNewButton_10 = new JButton("선택");
		btnNewButton_10.setBounds(105, 510, 80, 20);
		btnNewButton_10.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String fullValue = (String)list_1.getSelectedValue();
				if(fullValue!=null&&!"".equals(fullValue)) {
					getExecute();
				} else {
					JOptionPane.showMessageDialog(null, "데이터를 선택해주세요.");
				}
			}
		});
		contentPane.add(btnNewButton_10);
		
		JLabel label_3 = new JLabel("R");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(595, 185, 15, 20);
		contentPane.add(label_3);
		
		textField_12 = new JTextField();
		textField_12.setEditable(false);
		textField_12.setHorizontalAlignment(SwingConstants.CENTER);
		textField_12.setColumns(10);
		textField_12.setBounds(610, 185, 35, 20);
		contentPane.add(textField_12);
		
		JLabel label_4 = new JLabel("G");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(650, 185, 15, 20);
		contentPane.add(label_4);
		
		textField_14 = new JTextField();
		textField_14.setEditable(false);
		textField_14.setHorizontalAlignment(SwingConstants.CENTER);
		textField_14.setColumns(10);
		textField_14.setBounds(670, 185, 35, 20);
		contentPane.add(textField_14);
		
		JLabel label_5 = new JLabel("B");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setBounds(710, 185, 15, 20);
		contentPane.add(label_5);
		
		textField_16 = new JTextField();
		textField_16.setEditable(false);
		textField_16.setHorizontalAlignment(SwingConstants.CENTER);
		textField_16.setColumns(10);
		textField_16.setBounds(730, 185, 35, 20);
		contentPane.add(textField_16);
		
		JButton btnNewButton_12 = new JButton("↑");
		btnNewButton_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String fullValue = (String)list_1.getSelectedValue();
				if(fullValue!=null&&!"".equals(fullValue)) {
					int index = list_1.getSelectedIndex();
					if(index<=0) {
						return;
					}
					DefaultListModel defaultListModel = (DefaultListModel<String>)list_1.getModel();
					List list = new ArrayList<String>();
					for (int i = 0; i < defaultListModel.getSize(); i++) {
						String data = defaultListModel.get(i).toString();
						list.add(data);
					}
					Collections.swap(list, index, index-1);
					DefaultListModel listModel = new DefaultListModel();
					for (int i = 0; i < list.size(); i++) {
						String data = list.get(i).toString();
						listModel.addElement(data);
					}
					list_1.setModel(listModel);
					list_1.setSelectedIndex(index-1);  //
					list_1.ensureIndexIsVisible(index-1);
					BufferedReader bufReader = null;
					BufferedWriter bufWriter = null;
					try {
						// 파일 객체 생성
						File file = new File(configFilePath+configFileName);
						bufWriter = new BufferedWriter(new FileWriter(file));
						// 파일안에 문자열 쓰기
						for (int i = 0; i < listModel.size(); i++) {
							if(i!=0) {
								bufWriter.write("\n");
							}
							bufWriter.write(listModel.get(i).toString());
						}
						bufWriter.flush();
						// 객체 닫기
						bufWriter.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						try {
							if(bufReader!=null)
								bufReader.close();
							if(bufWriter!=null)
								bufWriter.close();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "이동할 명령어가 없습니다.");
				}
			}
		});
		btnNewButton_12.setBounds(90, 250, 45, 20);
		contentPane.add(btnNewButton_12);
		
		JButton button_3 = new JButton("↓");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String fullValue = (String)list_1.getSelectedValue();
				if(fullValue!=null&&!"".equals(fullValue)) {
					int index = list_1.getSelectedIndex();
					DefaultListModel defaultListModel = (DefaultListModel<String>)list_1.getModel();
					if(index>=defaultListModel.getSize()-1) {
						return;
					}
					List list = new ArrayList<String>();
					for (int i = 0; i < defaultListModel.getSize(); i++) {
						String data = defaultListModel.get(i).toString();
						list.add(data);
					}
					Collections.swap(list, index, index+1);
					DefaultListModel listModel = new DefaultListModel();
					for (int i = 0; i < list.size(); i++) {
						String data = list.get(i).toString();
						listModel.addElement(data);
					}
					list_1.setModel(listModel);
					list_1.setSelectedIndex(index+1);  //
					list_1.ensureIndexIsVisible(index+1);
					BufferedReader bufReader = null;
					BufferedWriter bufWriter = null;
					try {
						// 파일 객체 생성
						File file = new File(configFilePath+configFileName);
						bufWriter = new BufferedWriter(new FileWriter(file)); 
						// 파일안에 문자열 쓰기
						for (int i = 0; i < listModel.size(); i++) {
							if(i!=0) {
								bufWriter.write("\n");
							}
							bufWriter.write(listModel.get(i).toString());
						}
						bufWriter.flush();
						// 객체 닫기
						bufWriter.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						try {
							if(bufReader!=null)
								bufReader.close();
							if(bufWriter!=null)
								bufWriter.close();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "이동할 명령어가 없습니다.");
				}
			}
		});
		button_3.setBounds(140, 250, 45, 20);
		contentPane.add(button_3);
		
		textField_8 = new JTextField();
		textField_8.setEditable(false);
		textField_8.setText(configFileName);
		textField_8.setBounds(425, 15, 150, 20);
		contentPane.add(textField_8);
		textField_8.setColumns(10);
		
		button_4 = new JButton("불러오기");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser();
				jfc.setCurrentDirectory(new File(configFilePath));
				jfc.setFileFilter(new FileNameExtensionFilter("TEXT FILES", "txt", "text"));
                jfc.setMultiSelectionEnabled(false);
                if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            		textField_8.setText(jfc.getSelectedFile().getName());
					setExecuteListData(true);
				}
			}
		});
		button_4.setBounds(580, 15, 90, 20);
		contentPane.add(button_4);
		
		textField_9 = new JTextField();
		textField_9.setEditable(false);
		textField_9.setColumns(10);
		textField_9.setBounds(200, 15, 150, 20);
		contentPane.add(textField_9);
		
		JButton button_5 = new JButton("파일생성");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser();
				jfc.setCurrentDirectory(new File(configFilePath));
				jfc.setFileFilter(new FileNameExtensionFilter("TEXT FILES", "txt"));
                jfc.setMultiSelectionEnabled(false);
                if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                	if(jfc.getSelectedFile().getName().endsWith(".txt")||jfc.getSelectedFile().getName().endsWith(".text")) {
                		File file = new File(configFilePath+jfc.getSelectedFile().getName());
                		if(!file.isFile()) {
            		        try {
								file.createNewFile();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
            		        textField_8.setText(jfc.getSelectedFile().getName());
            		        setExecuteListData(true);
            	        } else {
                    		JOptionPane.showMessageDialog(null, "이미 존재하는 파일입니다.");
            	        }
                	} else {
                		JOptionPane.showMessageDialog(null, "txt 파일만 생성 가능합니다.");
                	}
				}
			}
		});
		button_5.setBounds(675, 15, 90, 20);
		contentPane.add(button_5);
	}
	
	public void popupClose() {
		if(newWindow!=null) {
			newWindow.dispose();
			newWindow = null;
		}
	}

	public void createExcutePanel() {
		panel = new JPanel();
		panel.setBounds(200, 275, 567, 280);
		panel.setLayout(null);
		
		JPanel excutePanel = new JPanel();
		excutePanel.setBounds(0, 55, 567, 20);
		excutePanel.setLayout(null);
		panel.add(excutePanel);
		
		JLabel lblNewLabel_8_1 = new JLabel("클릭");
		lblNewLabel_8_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_8_1.setBounds(0, 0, 35, 20);
		excutePanel.add(lblNewLabel_8_1);
		
		contentPane.add(panel);
		contentPane.updateUI();
		
		ArrayList<Object> createExcuteSubPanelList = new ArrayList<>();
		createExecuteSubPanel(panel, excutePanel, createExcuteSubPanelList);
		
		JLabel label = new JLabel("X");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(40, 0, 15, 20);
		excutePanel.add(label);
		
		textField_X = new JTextField();
		textField_X.setDocument(new IntegerDocument());
		textField_X.setText("0");
		textField_X.setHorizontalAlignment(SwingConstants.CENTER);
		textField_X.setColumns(10);
		textField_X.setBounds(60, 0, 35, 20);
		excutePanel.add(textField_X);
		
		JLabel label_3 = new JLabel("Y");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(100, 0, 15, 20);
		excutePanel.add(label_3);
		
		textField_Y = new JTextField();
		textField_Y.setDocument(new IntegerDocument());
		textField_Y.setText("0");
		textField_Y.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Y.setColumns(10);
		textField_Y.setBounds(115, 0, 35, 20);
		excutePanel.add(textField_Y);
		
		JLabel label_RGB_D = new JLabel("RGB 편차");
		label_RGB_D.setBounds(170, 0, 65, 20);
		label_RGB_D.setHorizontalAlignment(SwingConstants.CENTER);
		excutePanel.add(label_RGB_D);
		
		textField_RGB_D = new JTextField();
		textField_RGB_D.setDocument(new IntegerDocument());
		textField_RGB_D.setText("0");
		textField_RGB_D.setBounds(245, 0, 35, 20);
		textField_RGB_D.setHorizontalAlignment(SwingConstants.CENTER);
		textField_RGB_D.setColumns(10);
		excutePanel.add(textField_RGB_D);
		
		JButton btnNewButton_9 = new JButton("실행");
		btnNewButton_9.setBounds(507, 0, 60, 20);
		btnNewButton_9.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(get_adb_check()&&get_empty_check()) {
					execute = false;
					excuteStartServerSocket(false);
		        	excuteScreencap(list.getSelectedValue().toString());
		        	excuteStartServiceOne(list.getSelectedValue().toString());
				}
			}
		});
		excutePanel.add(btnNewButton_9);
		
		button_2_1 = new JButton("조건추가");
		button_2_1.setBounds(477, 5, 90, 20);
		button_2_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createExecuteSubPanel(panel, excutePanel, createExcuteSubPanelList);
			}
		});
		panel.add(button_2_1);
		
		textField_S = new JTextField();
		textField_S.setToolTipText("명령어 제목");
		textField_S.setBounds(0, 5, 150, 20);
		textField_S.setColumns(10);
		panel.add(textField_S);
		
		btnNewButton_7 = new JButton("목록추가");
		btnNewButton_7.setBounds(155, 5, 90, 20);
		btnNewButton_7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addExecuteList();
			}
		});
		panel.add(btnNewButton_7);
		
		JButton button_3 = new JButton("목록수정");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String fullValue = (String)list_1.getSelectedValue();
				System.out.println(fullValue);
				if(fullValue!=null&&!"".equals(fullValue)) {
					editeExecuteList();
				} else {
					JOptionPane.showMessageDialog(null, "수정할 명령어가 없습니다.");
				}
			}
		});
		button_3.setBounds(250, 5, 90, 20);
		panel.add(button_3);
		
	}
	
	public void getExecute() {
		String fullValue = (String)list_1.getSelectedValue();
		String S = "";
		String CX = "";
		String CY = "";
		String R = "";
		String G = "";
		String B = "";
		String C = "";
		String X = "";
		String Y = "";
		String RGB_D = "";
		if(fullValue.split("[|]").length==10) {
			S = fullValue.split("[|]")[0];
			CX = fullValue.split("[|]")[1];
			CY = fullValue.split("[|]")[2];
			R = fullValue.split("[|]")[3];
			G = fullValue.split("[|]")[4];
			B = fullValue.split("[|]")[5];
			C = fullValue.split("[|]")[6];
			X = fullValue.split("[|]")[7];
			Y = fullValue.split("[|]")[8];
			RGB_D = fullValue.split("[|]")[9];
		}
		int cnt = R.split(",").length;
		int del_cnt = cnt;
		int total_cnt = 0;
		Component[] children = panel.getComponents();
		for (int i=children.length-1;i>=0;i--) {
			if (children[i] instanceof JPanel) {
				if("panelA".equals(((JPanel)children[i]).getName())) {
					total_cnt++;
				}
			}
		}
		for (int i=children.length-1;i>=0;i--) {
			if (children[i] instanceof JPanel) {
				if("panelA".equals(((JPanel)children[i]).getName())) {
					System.out.println(">>>>>>>>>>>");
					Component[] childrens = ((JPanel)children[i]).getComponents();
					if(childrens[childrens.length-1] instanceof JButton) {
						System.out.println("del_cnt:"+del_cnt);
						System.out.println("total_cnt:"+total_cnt);
						if(total_cnt>1) {
							JButton jb = (JButton)childrens[childrens.length-1];
							jb.doClick();
							del_cnt--;
							total_cnt--;
						}
					}
				}
			}
		}
		for (int i = 0; i < cnt-1; i++) {
			button_2_1.doClick();
		}
		System.out.println("S:"+S);
		System.out.println("CX:"+CX);
		System.out.println("CY:"+CY);
		System.out.println("R:"+R);
		System.out.println("G:"+G);
		System.out.println("B:"+B);
		System.out.println("C:"+C);
		System.out.println("X:"+X);
		System.out.println("Y:"+Y);
		System.out.println("RGB_D:"+RGB_D);
		System.out.println("fullValue:"+fullValue);
		textField_S.setText(S);
		children = panel.getComponents();
		int cntA = 0;
		for (int i=0;i<children.length;i++) {
		    if (children[i] instanceof JPanel) {
		    	Component[] children_A = ((JPanel)children[i]).getComponents();
	    		System.out.println(children_A.length);
				if(children_A.length==14) {
					if (children_A[2] instanceof JTextField) {
						((JTextField)children_A[2]).setText(CX.split(",")[cntA]);
					}
					if (children_A[4] instanceof JTextField) {
						((JTextField)children_A[4]).setText(CY.split(",")[cntA]);
					}
					if (children_A[6] instanceof JTextField) {
						((JTextField)children_A[6]).setText(R.split(",")[cntA]);
					}
					if (children_A[8] instanceof JTextField) {
						((JTextField)children_A[8]).setText(G.split(",")[cntA]);
					}
					if (children_A[10] instanceof JTextField) {
						((JTextField)children_A[10]).setText(B.split(",")[cntA]);
					}
					if (children_A[11] instanceof JRadioButton) {
						if("0".equals(C.split(",")[cntA])) {
							((JRadioButton)children_A[11]).setSelected(true);
						}
					}
					if (children_A[12] instanceof JRadioButton) {
						if("1".equals(C.split(",")[cntA])) {
							((JRadioButton)children_A[12]).setSelected(true);
						}
					}
					cntA++;
				}
				if(children_A.length==8) {
					if (children_A[2] instanceof JTextField) {
						((JTextField)children_A[2]).setText(X);
					}
					if (children_A[4] instanceof JTextField) {
						((JTextField)children_A[4]).setText(Y);
					}
					if (children_A[6] instanceof JTextField) {
						((JTextField)children_A[6]).setText(RGB_D);
					}
				}
		    }
		}
	}
	
	public void addExecuteList() {
		DefaultListModel listModel = new DefaultListModel();
		String fullValue = "";
		String S = textField_S.getText();
		String CX = "";
		String CY = "";
		String R = "";
		String G = "";
		String B = "";
		String C = "";
		String X = "";
		String Y = "";
		String RGB_D = "";
		Component[] children = panel.getComponents();
		for (int i=0;i<children.length;i++) {
		    if (children[i] instanceof JPanel) {
		    	Component[] children_A = ((JPanel)children[i]).getComponents();
	    		System.out.println(children_A.length);
				if(children_A.length==14) {
					if (children_A[2] instanceof JTextField) {
						if(!"".equals(CX)) {
							CX += ",";
						}
						CX += ((JTextField)children_A[2]).getText();
					}
					if (children_A[4] instanceof JTextField) {
						if(!"".equals(CY)) {
							CY += ",";
						}
						CY += ((JTextField)children_A[4]).getText();
					}
					if (children_A[6] instanceof JTextField) {
						if(!"".equals(R)) {
							R += ",";
						}
						R += ((JTextField)children_A[6]).getText();
					}
					if (children_A[8] instanceof JTextField) {
						if(!"".equals(G)) {
							G += ",";
						}
						G += ((JTextField)children_A[8]).getText();
					}
					if (children_A[10] instanceof JTextField) {
						if(!"".equals(B)) {
							B += ",";
						}
						B += ((JTextField)children_A[10]).getText();
					}
					if (children_A[11] instanceof JRadioButton) {
						if(((JRadioButton)children_A[11]).isSelected()) {
							if(!"".equals(C)) {
								C += ",";
							}
							C += "0";
						}
					}
					if (children_A[12] instanceof JRadioButton) {
						if(((JRadioButton)children_A[12]).isSelected()) {
							if(!"".equals(C)) {
								C += ",";
							}
							C += "1";
						}
					}
				}
				if(children_A.length==8) {
					if (children_A[2] instanceof JTextField) {
						X = ((JTextField)children_A[2]).getText();
					}
					if (children_A[4] instanceof JTextField) {
						Y = ((JTextField)children_A[4]).getText();
					}
					if (children_A[6] instanceof JTextField) {
						RGB_D = ((JTextField)children_A[6]).getText();
					}
				}
		    }
		}
		fullValue = S+"|"+CX+"|"+CY+"|"+R+"|"+G+"|"+B+"|"+C+"|"+X+"|"+Y+"|"+RGB_D;
		System.out.println("S:"+S);
		System.out.println("CX:"+CX);
		System.out.println("CY:"+CY);
		System.out.println("R:"+R);
		System.out.println("G:"+G);
		System.out.println("B:"+B);
		System.out.println("C:"+C);
		System.out.println("X:"+X);
		System.out.println("Y:"+Y);
		System.out.println("RGB_D:"+RGB_D);
		System.out.println("fullValue:"+fullValue);
		if("".equals(S)) {
        	JOptionPane.showMessageDialog(null, "명령어를 입력하세요.");
			return;
		}
		BufferedReader bufReader = null;
		BufferedWriter bufWriter = null;
		try {
			// 파일 객체 생성
	        File file = new File(configFilePath+configFileName);
	        if(!file.isFile()) {
		        file.createNewFile();
	        }
	        //입력 버퍼 생성
            bufReader = new BufferedReader(new FileReader(file));
            String line = "";
            while((line = bufReader.readLine()) != null){
            	if(!"".equals(line.trim())) {
                	listModel.addElement(line.trim());
            	}
            }
            //.readLine()은 끝에 
			System.out.println("경로:"+file.getAbsolutePath());
			bufWriter = new BufferedWriter(new FileWriter(file)); 
			// 파일안에 문자열 쓰기
			for (int i = 0; i < listModel.size(); i++) {
				if(i!=0) {
					bufWriter.write("\n");
				}
				bufWriter.write(listModel.get(i).toString());
			}
			bufWriter.write("\n");
			bufWriter.write(fullValue);
        	if(!"".equals(fullValue.trim())) {
            	listModel.addElement(fullValue.trim());
        	}
        	bufWriter.flush();
            // 객체 닫기
        	bufWriter.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(bufReader!=null)
					bufReader.close();
				if(bufWriter!=null)
					bufWriter.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		list_1.setModel(listModel);
	}
	
	public void editeExecuteList() {
		int index = list_1.getSelectedIndex(); //선택된 항목의 인덱스를 가져온다.
		DefaultListModel listModel = (DefaultListModel<String>)list_1.getModel();
		String fullValue = "";
		String S = textField_S.getText();
		String CX = "";
		String CY = "";
		String R = "";
		String G = "";
		String B = "";
		String C = "";
		String X = "";
		String Y = "";
		String RGB_D = "";
		Component[] children = panel.getComponents();
		for (int i=0;i<children.length;i++) {
		    if (children[i] instanceof JPanel) {
		    	Component[] children_A = ((JPanel)children[i]).getComponents();
	    		System.out.println(children_A.length);
				if(children_A.length==14) {
					if (children_A[2] instanceof JTextField) {
						if(!"".equals(CX)) {
							CX += ",";
						}
						CX += ((JTextField)children_A[2]).getText();
					}
					if (children_A[4] instanceof JTextField) {
						if(!"".equals(CY)) {
							CY += ",";
						}
						CY += ((JTextField)children_A[4]).getText();
					}
					if (children_A[6] instanceof JTextField) {
						if(!"".equals(R)) {
							R += ",";
						}
						R += ((JTextField)children_A[6]).getText();
					}
					if (children_A[8] instanceof JTextField) {
						if(!"".equals(G)) {
							G += ",";
						}
						G += ((JTextField)children_A[8]).getText();
					}
					if (children_A[10] instanceof JTextField) {
						if(!"".equals(B)) {
							B += ",";
						}
						B += ((JTextField)children_A[10]).getText();
					}
					if (children_A[11] instanceof JRadioButton) {
						if(((JRadioButton)children_A[11]).isSelected()) {
							if(!"".equals(C)) {
								C += ",";
							}
							C += "0";
						}
					}
					if (children_A[12] instanceof JRadioButton) {
						if(((JRadioButton)children_A[12]).isSelected()) {
							if(!"".equals(C)) {
								C += ",";
							}
							C += "1";
						}
					}
				}
				if(children_A.length==8) {
					if (children_A[2] instanceof JTextField) {
						X = ((JTextField)children_A[2]).getText();
					}
					if (children_A[4] instanceof JTextField) {
						Y = ((JTextField)children_A[4]).getText();
					}
					if (children_A[6] instanceof JTextField) {
						RGB_D = ((JTextField)children_A[6]).getText();
					}
				}
		    }
		}
		fullValue = S+"|"+CX+"|"+CY+"|"+R+"|"+G+"|"+B+"|"+C+"|"+X+"|"+Y+"|"+RGB_D;
		System.out.println("S:"+S);
		System.out.println("CX:"+CX);
		System.out.println("CY:"+CY);
		System.out.println("R:"+R);
		System.out.println("G:"+G);
		System.out.println("B:"+B);
		System.out.println("C:"+C);
		System.out.println("X:"+X);
		System.out.println("Y:"+Y);
		System.out.println("RGB_D:"+RGB_D);
		System.out.println("fullValue:"+fullValue);
		if("".equals(S)) {
        	JOptionPane.showMessageDialog(null, "명령어를 입력하세요.");
			return;
		}
		listModel.set(index, fullValue);
		BufferedReader bufReader = null;
		BufferedWriter bufWriter = null;
		try {
			// 파일 객체 생성
			File file = new File(configFilePath+configFileName);
			bufWriter = new BufferedWriter(new FileWriter(file));
			// 파일안에 문자열 쓰기
			for (int i = 0; i < listModel.size(); i++) {
				if(i!=0) {
					bufWriter.write("\n");
				}
				bufWriter.write(listModel.get(i).toString());
			}
			bufWriter.flush();
			// 객체 닫기
			bufWriter.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(bufReader!=null)
					bufReader.close();
				if(bufWriter!=null)
					bufWriter.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void delExecuteList() {
		//삭제 버튼을 눌렀을때
		int index = list_1.getSelectedIndex(); //선택된 항목의 인덱스를 가져온다.
		//인덱스는 0부터 시작
		DefaultListModel listModel = (DefaultListModel<String>)list_1.getModel();
		listModel.remove(index);  //리스트모델에서 선택된 항목을 지운다.
		if(index == listModel.getSize()){ //인덱스와 리스트모델의 마지막항목이 같으면
			index--;      //즉,선택된 인덱스가 리스트의 마지막 항목이었으면
		}         //인덱스를 -1해서 인덱스를 옮겨준다.
		if(0<=index) {
			list_1.setSelectedIndex(index);  //
			list_1.ensureIndexIsVisible(index);
		}
		for (int i = 0; i < listModel.getSize(); i++) {
			if("".equals(listModel.get(i).toString())) {
				listModel.remove(i);
			}
		}
		BufferedReader bufReader = null;
		BufferedWriter bufWriter = null;
		try {
			// 파일 객체 생성
			File file = new File(configFilePath+configFileName);
			bufWriter = new BufferedWriter(new FileWriter(file));
			// 파일안에 문자열 쓰기
			for (int i = 0; i < listModel.size(); i++) {
				if(i!=0) {
					bufWriter.write("\n");
				}
				bufWriter.write(listModel.get(i).toString());
			}
			bufWriter.flush();
			// 객체 닫기
			bufWriter.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(bufReader!=null)
					bufReader.close();
				if(bufWriter!=null)
					bufWriter.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void setExecuteListData(boolean config) {
		if(config) {
			System.out.println("파일적용");
			configFileName = textField_8.getText();
		}
		DefaultListModel listModel = new DefaultListModel();
		BufferedReader bufReader = null;
		FileReader fr = null;
		try {
			// 파일 객체 생성
	        File file = new File(configFilePath+configFileName);
	        if(!file.isFile()) {
		        file.createNewFile();
	        }
	        fr = new FileReader(file);
	        //입력 버퍼 생성
	        bufReader = new BufferedReader(fr);
	        String line = "";
	        while((line = bufReader.readLine()) != null){
	        	listModel.addElement(line);
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(bufReader!=null)
					bufReader.close();
				if(fr!=null)
					fr.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		list_1.setModel(listModel);
	}
	
	public void createExecuteSubPanel(JPanel panel, JPanel excutePanel, ArrayList<Object> createExcuteSubPanelList) {
		if(createExcuteSubPanelList.size()>=9) {
    		JOptionPane.showMessageDialog(null, "추가할 수 없습니다.");
			return;
		}
		int y = 0;
		if(createExcuteSubPanelList.size()>0) {
			y = createExcuteSubPanelList.size()*25;
		}
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 30+y, 567, 20);
		panel_1.setLayout(null);
		panel_1.setName("panelA");
		
		JLabel lblNewLabel_7_1 = new JLabel("조건");
		lblNewLabel_7_1.setBounds(0, 0, 35, 20);
		lblNewLabel_7_1.setHorizontalAlignment(SwingConstants.LEFT);
		panel_1.add(lblNewLabel_7_1);
		
		JLabel label_CX = new JLabel("X");
		label_CX.setHorizontalAlignment(SwingConstants.CENTER);
		label_CX.setBounds(40, 0, 15, 20);
		panel_1.add(label_CX);
		
		JTextField textField_CX = new JTextField();
		textField_CX.setDocument(new IntegerDocument());
		textField_CX.setText("0");
		textField_CX.setBounds(60, 0, 35, 20);
		textField_CX.setHorizontalAlignment(SwingConstants.CENTER);
		textField_CX.setColumns(10);
		panel_1.add(textField_CX);
		
		JLabel label_CY = new JLabel("Y");
		label_CY.setHorizontalAlignment(SwingConstants.CENTER);
		label_CY.setBounds(100, 0, 15, 20);
		panel_1.add(label_CY);
		
		JTextField textField_CY = new JTextField();
		textField_CY.setDocument(new IntegerDocument());
		textField_CY.setText("0");
		textField_CY.setBounds(115, 0, 35, 20);
		textField_CY.setHorizontalAlignment(SwingConstants.CENTER);
		textField_CY.setColumns(10);
		panel_1.add(textField_CY);
		
		JLabel label_R = new JLabel("R");
		label_R.setHorizontalAlignment(SwingConstants.CENTER);
		label_R.setBounds(170, 0, 15, 20);
		panel_1.add(label_R);
		
		JTextField textField_R = new JTextField();
		textField_R.setDocument(new IntegerDocument());
		textField_R.setText("0");
		textField_R.setBounds(185, 0, 35, 20);
		textField_R.setHorizontalAlignment(SwingConstants.CENTER);
		textField_R.setColumns(10);
		panel_1.add(textField_R);
		
		JLabel label_G = new JLabel("G");
		label_G.setHorizontalAlignment(SwingConstants.CENTER);
		label_G.setBounds(225, 0, 15, 20);
		panel_1.add(label_G);
		
		JTextField textField_G = new JTextField();
		textField_G.setDocument(new IntegerDocument());
		textField_G.setText("0");
		textField_G.setBounds(245, 0, 35, 20);
		textField_G.setHorizontalAlignment(SwingConstants.CENTER);
		textField_G.setColumns(10);
		panel_1.add(textField_G);
		
		JLabel label_B = new JLabel("B");
		label_B.setHorizontalAlignment(SwingConstants.CENTER);
		label_B.setBounds(285, 0, 15, 20);
		panel_1.add(label_B);
		
		JTextField textField_B = new JTextField();
		textField_B.setDocument(new IntegerDocument());
		textField_B.setText("0");
		textField_B.setBounds(305, 0, 35, 20);
		textField_B.setHorizontalAlignment(SwingConstants.CENTER);
		textField_B.setColumns(10);
		panel_1.add(textField_B);

		ButtonGroup buttonGroup = new ButtonGroup();
		
		JRadioButton rdbtnNewRadioButton_T = new JRadioButton("참");
		rdbtnNewRadioButton_T.setActionCommand("0");
		rdbtnNewRadioButton_T.setBounds(355, 0, 43, 20);
		rdbtnNewRadioButton_T.setSelected(true);
		buttonGroup.add(rdbtnNewRadioButton_T);
		panel_1.add(rdbtnNewRadioButton_T);
		
		JRadioButton rdbtnNewRadioButton_F = new JRadioButton("거짓");
		rdbtnNewRadioButton_T.setActionCommand("1");
		rdbtnNewRadioButton_F.setBounds(395, 0, 62, 20);
		buttonGroup.add(rdbtnNewRadioButton_F);
		panel_1.add(rdbtnNewRadioButton_F);
		
		JButton btnNewButton_8_1 = new JButton("삭제");
		btnNewButton_8_1.setBounds(507, 0, 60, 20);
		btnNewButton_8_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeSubJPanel(panel, panel_1, excutePanel, createExcuteSubPanelList);
			}
		});
		panel_1.add(btnNewButton_8_1);
		
		createExcuteSubPanelList.add(panel_1);
		
		panel.add(panel_1);
		contentPane.updateUI();
		
		rePosition(createExcuteSubPanelList, excutePanel);
	}
	
	public void rePosition(ArrayList createExcuteSubPanelList, JPanel excutePanel) {
		System.out.println("createExcuteSubPanelList.size():"+createExcuteSubPanelList.size());
		int y = (createExcuteSubPanelList.size()-1)*25;
		excutePanel.setBounds(0, 55+y, 567, 20);
		System.out.println("변신 y:"+y);
	}
	
	public void removeSubJPanel(JPanel panel, JPanel panel_1, JPanel excutePanel, ArrayList createExcuteSubPanelList) {
		if(createExcuteSubPanelList.size()<=1) {
    		JOptionPane.showMessageDialog(null, "삭제할 수 없습니다.");
			return;
		}
		boolean flag = false;
		System.out.println("삭제시작 size:"+createExcuteSubPanelList.size());
		for (int i = 0; i < createExcuteSubPanelList.size(); i++) {
			if(createExcuteSubPanelList.get(i)==panel_1) {
				System.out.println("삭제 완료");
				createExcuteSubPanelList.remove(i);
				flag = true;
				break;
			}
		}
		if(!flag) {
			System.out.println("삭제 실패");
			return;
		}
		System.out.println("삭제완료 size:"+createExcuteSubPanelList.size());
		panel.remove(panel_1);
		for (int i = 0; i < createExcuteSubPanelList.size(); i++) {
			JPanel move = (JPanel)createExcuteSubPanelList.get(i);
			int y = i*25;
			move.setBounds(0, 30+y, 567, 20);
		}
		rePosition(createExcuteSubPanelList, excutePanel);
		contentPane.updateUI();
	}
	
	public boolean get_basic_check() {
		boolean flag = true;
		if(!get_adb_check()) {
			flag = false;
		} else if(!get_empty_check()) {
    		flag = false;
		} else if(!get_connet_check()) {
    		flag = false;
		}
		return flag;
	}
	
	public boolean get_dis_basic_check() {
		boolean flag = true;
		if(!get_adb_check()) {
			flag = false;
		} else if(!get_empty_check()) {
    		flag = false;
		} else if(!get_dis_connet_check()) {
    		flag = false;
		}
		return flag;
	}
	
	public boolean get_adb_check() {
		boolean flag = true;
		String command = txtAdb.getText()+"";
		String[] commands = new String[] { command };
		commands = new String[] { command };
		String msg = excuteCmd(commands,true);
		if("실행 오류 ADB를 확인해주세요.".equals(msg)) {
    		JOptionPane.showMessageDialog(null, msg);
    		flag = false;
		}
		return flag;
	}
	
	public boolean get_empty_check() {
		boolean flag = true;
		if(list.getSelectedValue()==null||list.getSelectedValue().equals("")) {
    		JOptionPane.showMessageDialog(null, "디바이스를 선택해주세요.");
    		flag = false;
		}
		return flag;
	}
	
	public boolean get_connet_check() {
		boolean flag = true;
		if(list.getSelectedValue().toString().contains(":")) {
			JOptionPane.showMessageDialog(null, "디바이스를 확인해주세요.");
    		flag = false;
		}
		return flag;
	}
	
	public boolean get_dis_connet_check() {
		boolean flag = true;
		if(!list.getSelectedValue().toString().contains(":")) {
			JOptionPane.showMessageDialog(null, "디바이스를 확인해주세요.");
    		flag = false;
		}
		return flag;
	}
	
	public void setServierIp() {
		try {
			InetAddress local = InetAddress.getLocalHost();
			ip = local.getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
						if(i>0) {
							returnValue.add(inputString.split("device")[0].trim());
						}
					}
					i++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		return returnValue;
	}
	
	public void setPort(String select_device) {
		try {
			String command = txtAdb.getText()+" "+"-s "+select_device+" tcpip 5555";
			String[] commands = new String[] { command };
			commands = new String[] { command };
			String msg = excuteCmd(commands,true);
			if(msg.startsWith("restarting in TCP mode port")) {
        		//JOptionPane.showMessageDialog(null, "포트설정 완료");
			} else {
        		JOptionPane.showMessageDialog(null, "포트설정 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	public void setConnect(String select_device) {
		try {
			String command = txtAdb.getText()+" "+"connect "+getIpaddr(select_device)+":5555";
			String[] commands = new String[] { command };
			commands = new String[] { command };
			String msg = excuteCmd(commands,true);
			if(msg.startsWith("connected to")) {
				list.setListData(getDevices().toArray());
        		JOptionPane.showMessageDialog(null, "무선연결 완료");
			} else if(msg.startsWith("already connected to")) {
				JOptionPane.showMessageDialog(null, "이미 설정 되어 있습니다.");
			} else {
				JOptionPane.showMessageDialog(null, "무선연결 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	public void setDisConnect(String select_device) {
		try {
			String command = txtAdb.getText()+" "+"disconnect "+getIpaddr(select_device);
			String[] commands = new String[] { command };
			commands = new String[] { command };
			String msg = excuteCmd(commands,true);
			if(msg.startsWith("disconnected")) {
				list.setListData(getDevices().toArray());
        		JOptionPane.showMessageDialog(null, "무선해지 완료");
			} else {
				JOptionPane.showMessageDialog(null, "무선해지 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	public String getIpaddr(String select_device) {
		String returnValue = "";
		try {
			String command = txtAdb.getText()+" "+"-s "+select_device+" shell ip addr show wlan0";
			String[] commands = new String[] { command };
			for (String cmd : commands) {
				Date today = new Date();
			    SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
				System.out.println(date.format(today)+" "+cmd);
				Process process = Runtime.getRuntime().exec(cmd);
				InputStream inputStream = process.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String inputString = null;
				int i=0;
				while ((inputString = bufferedReader.readLine()) != null) {
					System.out.println(date.format(today)+" "+i+"::"+inputString);
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
		return returnValue;
	}

	public void excuteDeviceScreenCapImgPull(String select_device, String saveFilePath) {
		String command = txtAdb.getText();
		command += " "+"-s "+select_device;
        command += " "+"shell screencap -p /sdcard/screencap-sample.png";
        String command2 = txtAdb.getText();
        command2 += " "+"-s "+select_device;
        command2 += " "+"pull /sdcard/screencap-sample.png"+" "+saveFilePath+"\\./screencap-sample.png";
        String[] commands = new String[] { command, command2 };
    	JOptionPane.showMessageDialog(null, "선택하신 경로에 캡쳐 파일이 저장 됩니다.\n저장 완료후 캡쳐 완료/실패 메시지가 뜸니다.");
        String msg = excuteCmd(commands,true);
        if(msg.contains("pulled")) {
        	newWindow = new NewWindow(saveFilePath+"/screencap-sample.png"); // 클래스 newWindow를 새로 만들어낸다
        } else {
        	JOptionPane.showMessageDialog(null, "캡쳐실패");
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
						System.out.println(returnValue);
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
			returnValue = "실행 오류 ADB를 확인해주세요.";
		} finally {
			if(bufferedReader!=null) {
				try {
					bufferedReader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(inputStreamReader!=null) {
				try {
					inputStreamReader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(inputStream!=null) {
				try {
					inputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(process!=null) {
				try {
					process.destroy();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return returnValue;
	}
	
	public void excuteScreencap(String select_device) {
		String command = txtAdb.getText();
		command += " "+"-s "+select_device;
        command += " "+"shell screencap -p /sdcard/screencap-sample.png";
		String[] commands = new String[] { command };
		excuteCmd(commands, true);
	}
	
	public class ServerThread extends Thread {
        Socket socket;
        ConnectionToClient conToClient;
        ServerThread(Socket socket) {
            this.socket = socket;
             conToClient = new ConnectionToClient(socket);
             clients.add(conToClient); 
        }
        public void run() {
        	System.out.println("시작");
            try {
                String input = "";
                while( (input = conToClient.read())!=null){
                	if(input.contains("R:")&&input.contains("G:")&&input.contains("B:")) {
                    	int R = Integer.parseInt(input.split(",")[1].split(":")[1]);
                    	int G = Integer.parseInt(input.split(",")[2].split(":")[1]);
                    	int B = Integer.parseInt(input.split(",")[3].split(":")[1]);
                    	lblNewLabel_5.setBackground(new Color(R, G, B));
                    	lblNewLabel_5.setOpaque(true);
                		textField_12.setText(String.valueOf(R));
                		textField_14.setText(String.valueOf(G));
                		textField_16.setText(String.valueOf(B));
                	}
                	if(input.trim().contains("|")) {
                		String x = input.trim().split("[|]")[0];
                		String y = input.trim().split("[|]")[1];
                		System.out.println("input x:"+x);
                		System.out.println("input y:"+y);
    					Lstart(list.getSelectedValue().toString(), x, y);
                	}
					if(input.contains("app")) {
						System.out.println("빈값");
					} else {
						System.out.println("파싱 시작");
					}
    				String ip = (((InetSocketAddress) socket.getRemoteSocketAddress()).getAddress()).toString().replace("/", "");
                    System.out.println("정보:"+ip+" > "+input);
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
    
    public void endClient(Socket socket) {
    	try {
    		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
    		String ip = (((InetSocketAddress) socket.getRemoteSocketAddress()).getAddress()).toString().replace("/", "");
    		System.out.println("clients.size():"+clients.size());
        	for (int i = 0; i < clients.size(); i++) {
        		ConnectionToClient client = (ConnectionToClient)clients.get(i);
        		boolean connected = client.socket.isConnected() && ! client.socket.isClosed();
        		String ip_sub = (((InetSocketAddress) client.socket.getRemoteSocketAddress()).getAddress()).toString().replace("/", "");
            	out = new PrintWriter(client.socket.getOutputStream(), true);
    			if(ip.equals(ip_sub)) {
            		if(connected) {
            			try {
        					client.socket.close();
        					client.socket = null;
        				} catch (IOException e) {
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

    public class ConnectionToClient {
        Socket socket;
        BufferedReader br;
        ObjectOutputStream oos;
        ConnectionToClient(Socket socket) {
            this.socket = socket;
            try {
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                oos = new ObjectOutputStream(socket.getOutputStream());
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
        public String read(){
            try{
                return br.readLine();
            }catch(Exception e){
            	return null;
            } finally {
			}
        }
        public void write(Object obj) {
            try {
            	System.out.println("전송:"+obj);
                oos.writeObject(obj);
                oos.flush();
            } catch (Exception e) {
            	//e.printStackTrace();
            } finally {
            	excuteCallApp(execute);
            	if(!execute) {
            		close_soket();
            	}
			}
        }
    }
	
	public static void close_soket() {
    	try {
			if(server!=null) {
				server.close();
				server = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void excuteStartServerSocket(boolean cap) {
		new Thread(new Runnable() {
	        @Override
	        public void run() {
				try {
					close_soket();
					Thread.sleep(3000);
					if(server==null) {
			            server = new ServerSocket(Integer.parseInt(textField.getText()));
			            Socket socket = null;
			            System.out.println("Server Opend");
			            if(cap) {
				        	excuteScreencap(list.getSelectedValue().toString());
				        	excuteStartServiceCC(list.getSelectedValue().toString());
			            }
			            excuteCallApp(execute);
			            while ((socket = server.accept()) != null) {
							String ip = (((InetSocketAddress) socket.getRemoteSocketAddress()).getAddress()).toString().replace("/", "");
							endClient(socket);
			            	System.out.println("접속 ip > "+ip);
			                new ServerThread(socket).start();
			            }
			            server.close();
					}
				} catch (SocketException e) {
					System.out.println("ServerSocket is closed!");
			    	excuteAllEnabled(true);
				} catch (Exception e) {
//		        	e.printStackTrace();
		        }
	        }
		}).start();
	}
    
    public void excuteStartService(String select_device) {
		ArrayList al = new ArrayList<String>();
    	DefaultListModel listModel = (DefaultListModel<String>)list_1.getModel();
    	String fullValue = "";
		String CX = "";
		String CY = "";
		String R = "";
		String G = "";
		String B = "";
		String C = "";
		String X = "";
		String Y = "";
		String RGB_D = "";
		System.out.println("listModel.getSize():"+listModel.getSize());
		for (int i = 0; i < listModel.getSize(); i++) {
			String data = listModel.get(i).toString();
			if("".equals(data.toString().trim())) {
				continue;
			}
			System.out.println("data:"+data);
			CX = data.split("[|]")[1].replace(",", "\\,");
			CY = data.split("[|]")[2].replace(",", "\\,");
			R = data.split("[|]")[3].replace(",", "\\,");
			G = data.split("[|]")[4].replace(",", "\\,");
			B = data.split("[|]")[5].replace(",", "\\,");
			C = data.split("[|]")[6].replace(",", "\\,");
			X = data.split("[|]")[7];
			Y = data.split("[|]")[8];
			RGB_D = data.split("[|]")[9];
			fullValue = CX+"|"+CY+"|"+R+"|"+G+"|"+B+"|"+C+"|"+X+"|"+Y+"|"+RGB_D;

			System.out.println("CX:"+CX);
			System.out.println("CY:"+CY);
			System.out.println("R:"+R);
			System.out.println("G:"+G);
			System.out.println("B:"+B);
			System.out.println("C:"+C);
			System.out.println("X:"+X);
			System.out.println("Y:"+Y);
			System.out.println("RGB_D:"+RGB_D);
			System.out.println("fullValue:"+fullValue);
			
			al.add(fullValue);
		}
		
		String data_list = al.toString();
		data_list = data_list.substring(1, data_list.length()-1 );

		port = textField.getText();
		String command = txtAdb.getText();
		command += " "+"-s"+" "+select_device;
		command += " "+"shell am startservice -n mr.linage.com/mr.linage.com.service.MyService ";
		command += "--es 'socket_server_ip' '"+ip+"' ";
		command += "--es 'socket_server_port' '"+port+"' ";
		command += "--es 'data_list' '"+data_list.toString()+"'";
		String[] commands = new String[] { command };
		excuteCmd(commands, true);
		
		Date today = new Date();
	    SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
	    textField_9.setText(date.format(today));
	}
    
    public void excuteStartServiceCC(String select_device) {
		port = textField.getText();
		String command = txtAdb.getText();
		command += " "+"-s"+" "+select_device;
		command += " "+"shell am startservice -n mr.linage.com/mr.linage.com.service.MyService ";
		command += "--es 'socket_server_ip' '"+ip+"' ";
		command += "--es 'socket_server_port' '"+port+"' ";
		command += "--es 'type' 'cc' ";
		command += "--es 'cc_x' '"+textField_1.getText()+"' ";
		command += "--es 'cc_y' '"+textField_2.getText()+"'";
		String[] commands = new String[] { command };
		excuteCmd(commands, true);
	}
    
    public void excuteStartServiceOne(String select_device) {
    	String fullValue = "";
		String CX = "";
		String CY = "";
		String R = "";
		String G = "";
		String B = "";
		String C = "";
		String X = "";
		String Y = "";
		String RGB_D = "";
		Component[] children = panel.getComponents();
		for (int i=0;i<children.length;i++) {
		    if (children[i] instanceof JPanel) {
		    	Component[] children_A = ((JPanel)children[i]).getComponents();
	    		System.out.println(children_A.length);
				if(children_A.length==14) {
					if (children_A[2] instanceof JTextField) {
						if(!"".equals(CX)) {
							CX += "\\,";
						}
						CX += ((JTextField)children_A[2]).getText();
					}
					if (children_A[4] instanceof JTextField) {
						if(!"".equals(CY)) {
							CY += "\\,";
						}
						CY += ((JTextField)children_A[4]).getText();
					}
					if (children_A[6] instanceof JTextField) {
						if(!"".equals(R)) {
							R += "\\,";
						}
						R += ((JTextField)children_A[6]).getText();
					}
					if (children_A[8] instanceof JTextField) {
						if(!"".equals(G)) {
							G += "\\,";
						}
						G += ((JTextField)children_A[8]).getText();
					}
					if (children_A[10] instanceof JTextField) {
						if(!"".equals(B)) {
							B += "\\,";
						}
						B += ((JTextField)children_A[10]).getText();
					}
					if (children_A[11] instanceof JRadioButton) {
						if(((JRadioButton)children_A[11]).isSelected()) {
							if(!"".equals(C)) {
								C += "\\,";
							}
							C += "0";
						}
					}
					if (children_A[12] instanceof JRadioButton) {
						if(((JRadioButton)children_A[12]).isSelected()) {
							if(!"".equals(C)) {
								C += "\\,";
							}
							C += "1";
						}
					}
				}
				if(children_A.length==8) {
					if (children_A[2] instanceof JTextField) {
						X = ((JTextField)children_A[2]).getText();
					}
					if (children_A[4] instanceof JTextField) {
						Y = ((JTextField)children_A[4]).getText();
					}
					if (children_A[6] instanceof JTextField) {
						RGB_D = ((JTextField)children_A[6]).getText();
					}
				}
		    }
		}
		fullValue = CX+"|"+CY+"|"+R+"|"+G+"|"+B+"|"+C+"|"+X+"|"+Y+"|"+RGB_D;
		System.out.println("CX:"+CX);
		System.out.println("CY:"+CY);
		System.out.println("R:"+R);
		System.out.println("G:"+G);
		System.out.println("B:"+B);
		System.out.println("C:"+C);
		System.out.println("X:"+X);
		System.out.println("Y:"+Y);
		System.out.println("RGB_D:"+RGB_D);
		System.out.println("fullValue:"+fullValue);
		ArrayList al = new ArrayList<String>();
		al.add(fullValue);
		
		String data_list = al.toString();
		data_list = data_list.substring(1, data_list.length()-1 );

		port = textField.getText();
		String command = txtAdb.getText();
		command += " "+"-s"+" "+select_device;
		command += " "+"shell am startservice -n mr.linage.com/mr.linage.com.service.MyService ";
		command += "--es 'socket_server_ip' '"+ip+"' ";
		command += "--es 'socket_server_port' '"+port+"' ";
		command += "--es 'data_list' '"+data_list.toString()+"'";
		String[] commands = new String[] { command };
		excuteCmd(commands, true);
	}
    
    public void excuteCallApp(boolean excute) {
    	if(excute) {
        	excuteScreencap(list.getSelectedValue().toString());
        	excuteStartService(list.getSelectedValue().toString());
    	}
    }
    
    public void excuteAllEnabled(boolean flag) {
    	textField.setEnabled(flag);
    	textField_1.setEnabled(flag);
    	textField_2.setEnabled(flag);
    	txtAdb.setEnabled(flag);
    	button.setEnabled(flag);
    	btnNewButton_1.setEnabled(flag);
    	btnNewButton_2.setEnabled(flag);
    	btnNewButton_3.setEnabled(flag);
    	btnNewButton_4.setEnabled(flag);
    	btnNewButton_6.setEnabled(flag);
    	btnNewButton_11.setEnabled(flag);
    	btnNewButton_10.setEnabled(flag);
    	button_1.setEnabled(flag);
    	button_4.setEnabled(flag);
    	list.setEnabled(flag);
    	list_1.setEnabled(flag);
    	Component[] cs = panel.getComponents();
    	for (int i=0;i<cs.length;i++) {
    		if(cs[i] instanceof JButton) {
    			((JButton)cs[i]).setEnabled(flag);
    		}
    		if(cs[i] instanceof JTextField) {
    			((JTextField)cs[i]).setEnabled(flag);
    		}
    		if (cs[i] instanceof JPanel) {
    			Component[] css = ((JPanel)cs[i]).getComponents();
    			for (int j=0;j<css.length;j++) {
    				if (css[j] instanceof JRadioButton) {
    					((JRadioButton)css[j]).setEnabled(flag);
    				}
    				if (css[j] instanceof JButton) {
            			((JButton)css[j]).setEnabled(flag);
    				}
    				if (css[j] instanceof JTextField) {
            			((JTextField)css[j]).setEnabled(flag);
    				}
    			}
    		}
    	}
    }
    
    public void Lstart(String select_device, String x, String y) {
    	String command = txtAdb.getText();
		command += " "+"-s "+select_device;
		command += " "+"shell input tap "+x+" "+y;
		String[] commands = new String[] { command };
		commands = new String[] { command };
		excuteCmd(commands,true);
	}
    
	public class NewWindow extends JFrame {
		ImageIcon imageIcon;
		JLabel newLabel;
        boolean mouse = false;
	    // 버튼이 눌러지면 만들어지는 새 창을 정의한 클래스
	    NewWindow(String path) {
	        setTitle("좌표 선택");
	        addWindowListener(new WindowAdapter() {
	        	@Override
	        	public void windowClosed(WindowEvent e) {
	        		close_soket();
	        		System.out.println("ServerSocket is closed!");
	        	}
			});
	        // 주의, 여기서 setDefaultCloseOperation() 정의를 하지 말아야 한다
	        // 정의하게 되면 새 창을 닫으면 모든 창과 프로그램이 동시에 꺼진다
	        JPanel newWindowContainer = new JPanel();
	        newLabel = new JLabel();
	        imageIcon = new ImageIcon(path);
	        imageIcon.getImage().flush();
	        newLabel.setIcon(imageIcon);
	        newLabel.setAutoscrolls(true);
	        MouseAdapter ma = new MouseAdapter() {
                private Point origin;
                @Override
                public void mousePressed(MouseEvent e) {
                    origin = new Point(e.getPoint());
                }
                @Override
                public void mouseReleased(MouseEvent e) {
                	if(origin.x==e.getX()&&origin.y==e.getY()) {
                    	mouse = true;
                	} else {
                    	mouse = false;
                	}
                	if (e.getButton () == MouseEvent.BUTTON3 && mouse) {
                		System.out.println("마우스 테스트");
                		BufferedImage bufferedImage = toBufferedImage(imageIcon.getImage());
                		imageIcon.setImage(getRotateImage(bufferedImage, 90));
                		newLabel.setIcon(imageIcon);
                		newWindowContainer.revalidate();
                		newWindowContainer.repaint();
                	}
					if (e.getButton () == MouseEvent.BUTTON1 && mouse) {
						System.out.println("x:"+e.getX());
						System.out.println("y:"+e.getY());
						textField_1.setText(e.getX()+"");
						textField_2.setText(e.getY()+"");
						BufferedImage image = null;  
					    try {
					        image = ImageIO.read(new File(path));
					        Color color = new Color(image.getRGB(e.getX(), e.getY()));   //좌표 선택
					        int A = color.getBlue();
							int R = color.getRed();
							int G = color.getGreen();
							int B = color.getBlue();
					        lblNewLabel_5.setBackground(new Color(R, G, B));
	                    	lblNewLabel_5.setOpaque(true);
					        textField_12.setText(R+"");
					        textField_14.setText(G+"");
					        textField_16.setText(B+"");
					    } catch (Exception error) {
					    	error.printStackTrace();
					    }
//						new Thread(new Runnable() {
//					        @Override
//					        public void run() {
//								execute = false;
//								excuteStartServerSocket(true);
//					        }
//						}).start();
//	            		JOptionPane.showMessageDialog(null, "좌표 및 컬러를 불러오고 있습니다.\n잠시만 기다려주세요.");
					}
                }
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (origin != null) {
                        JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, newLabel);
                        if (viewPort != null) {
                            int deltaX = origin.x - e.getX();
                            int deltaY = origin.y - e.getY();
                            Rectangle view = viewPort.getViewRect();
                            view.x += deltaX;
                            view.y += deltaY;
                            newLabel.scrollRectToVisible(view);
                        }
                    }
                }
            };
	        newLabel.addMouseListener(ma);
	        newLabel.addMouseMotionListener(ma);
	        newWindowContainer.add(newLabel);
	        JScrollPane jsp = new JScrollPane(newWindowContainer,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	        jsp.getVerticalScrollBar().setUnitIncrement(16);
	        setContentPane(jsp);
	        setSize(720, 640);
	        setResizable(true);
	        setVisible(true);
	    }
	    
	    public BufferedImage toBufferedImage(Image img) {
	        if (img instanceof BufferedImage) {
	            return (BufferedImage) img;
	        }
	        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	        Graphics2D bGr = bimage.createGraphics();
	        bGr.drawImage(img, 0, 0, null);
	        bGr.dispose();
	        return bimage;
	    }
	    
	    public BufferedImage getRotateImage(BufferedImage image, double angle){//angle : degree
	        double _angle = Math.toRadians(angle);
	        double sin = Math.abs(Math.sin(_angle));
	        double cos = Math.abs(Math.cos(_angle));
	        double w = image.getWidth();
	        double h = image.getHeight();
	        int newW = (int)Math.floor(w*cos + h*sin);
	        int newH = (int)Math.floor(w*sin + h*cos);
	        GraphicsConfiguration gc = getGraphicsConfiguration();
	        BufferedImage result = gc.createCompatibleImage(newW, newH, Transparency.TRANSLUCENT);
	        Graphics2D g = result.createGraphics();
	        g.translate((newW-w)/2, (newH-h)/2);
	        g.rotate(_angle, w/2, h/2);
	        g.drawRenderedImage(image, null);
	        g.dispose();
	        return result;
		}
	    
	}
	
}

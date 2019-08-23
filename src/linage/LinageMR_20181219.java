package linage;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

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
import java.io.BufferedReader;
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
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;

public class LinageMR_20181219 extends JFrame {

	private JPanel contentPane;
	private JTextField txtAdb;
	private JButton button;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JButton btnNewButton_4;
	private JButton btnNewButton_5;
	private JList list;
	private boolean excute = false;
	private NewWindow newWindow;

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
				        @Override public void windowClosing(WindowEvent e) {
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
	
	public class NewWindow extends JFrame {
		ImageIcon imageIcon;
		JLabel newLabel;
        boolean mouse = false;
	    // 버튼이 눌러지면 만들어지는 새 창을 정의한 클래스
	    NewWindow(String path) {
	        setTitle("좌표 선택");
	        // 주의, 여기서 setDefaultCloseOperation() 정의를 하지 말아야 한다
	        // 정의하게 되면 새 창을 닫으면 모든 창과 프로그램이 동시에 꺼진다
	        JPanel NewWindowContainer = new JPanel();
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
					if (e.getButton () == MouseEvent.BUTTON1 && mouse) {
						System.out.println("x:"+e.getX());
						System.out.println("y:"+e.getY());
						textField_1.setText(e.getX()+"");
						textField_2.setText(e.getY()+"");
						new Thread(new Runnable() {
					        @Override
					        public void run() {
								excute = false;
								excuteStartServerSocket();
					        	excuteScreencap(list.getSelectedValue().toString());
					        	excuteStartServiceCC(list.getSelectedValue().toString());
					        }
						}).start();
	            		JOptionPane.showMessageDialog(null, "좌표 및 컬러를 불러오고 있습니다.\n잠시만 기다려주세요.");
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
	        NewWindowContainer.add(newLabel);
	        JScrollPane jsp = new JScrollPane(NewWindowContainer,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	        jsp.getVerticalScrollBar().setUnitIncrement(16);
	        setContentPane(jsp);
	        setSize(720, 640);
	        setResizable(true);
	        setVisible(true);
	    }
	}
	
	/**
	 * Create the frame.
	 */
	public LinageMR_20181219() {
		setTitle("YDH 편한 세상");
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
		
		JButton btnNewButton_11 = new JButton("무선해제");
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
		textField.setBounds(115, 40, 40, 20);
		textField.setText(port);
		textField.setColumns(10);
		contentPane.add(textField);
		
		btnNewButton_4 = new JButton("실행");
		btnNewButton_4.setBounds(15, 510, 80, 20);
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(get_adb_check()&&get_empty_check()) {
			    	excuteAllEnabled(false);
			    	excute = true;
					excuteStartServerSocket();
				}
			}
		});
		contentPane.add(btnNewButton_4);
		
		btnNewButton_5 = new JButton("종료");
		btnNewButton_5.setBounds(15, 535, 80, 20);
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close_soket();
			}
		});
		contentPane.add(btnNewButton_5);
		
		textField_1 = new JTextField();
		textField_1.setText("0");
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setBounds(670, 155, 35, 20);
		textField_1.setColumns(10);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setText("0");
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_2.setBounds(730, 155, 35, 20);
		textField_2.setColumns(10);
		contentPane.add(textField_2);
		
		JButton btnNewButton_6 = new JButton("RGB 체크");
		btnNewButton_6.setBounds(545, 155, 100, 20);
		btnNewButton_6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(get_adb_check()&&get_empty_check()) {
					JFileChooser jfc = new JFileChooser();
	                jfc.setMultiSelectionEnabled(false);
	                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	                if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
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
		
		JList list_1 = new JList();
		scrollPane_1.setViewportView(list_1);
		
		JLabel lblNewLabel_10 = new JLabel("명령어 목록");
		lblNewLabel_10.setBounds(15, 250, 105, 18);
		contentPane.add(lblNewLabel_10);
		
		button_1 = new JButton("삭제");
		button_1.setBounds(105, 535, 80, 20);
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		contentPane.add(button_1);
		
		JLabel lblNewLabel_9_1 = new JLabel("명령어");
		lblNewLabel_9_1.setBounds(200, 250, 45, 20);
		contentPane.add(lblNewLabel_9_1);
		
		JButton btnNewButton_10 = new JButton("선택");
		btnNewButton_10.setBounds(105, 510, 80, 20);
		btnNewButton_10.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
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
	}

	public void createExcutePanel() {
		JPanel panel = new JPanel();
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
		createExcuteSubPanel(panel, excutePanel, createExcuteSubPanelList);
		
		JLabel label = new JLabel("X");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(40, 0, 15, 20);
		excutePanel.add(label);
		
		textField_8 = new JTextField();
		textField_8.setText("0");
		textField_8.setHorizontalAlignment(SwingConstants.CENTER);
		textField_8.setColumns(10);
		textField_8.setBounds(60, 0, 35, 20);
		excutePanel.add(textField_8);
		
		JLabel label_3 = new JLabel("Y");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(100, 0, 15, 20);
		excutePanel.add(label_3);
		
		textField_9 = new JTextField();
		textField_9.setText("0");
		textField_9.setHorizontalAlignment(SwingConstants.CENTER);
		textField_9.setColumns(10);
		textField_9.setBounds(115, 0, 35, 20);
		excutePanel.add(textField_9);
		
		JButton btnNewButton_9 = new JButton("실행");
		btnNewButton_9.setBounds(507, 0, 60, 20);
		btnNewButton_9.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		excutePanel.add(btnNewButton_9);
		
		JLabel lblRgb = new JLabel("RGB 편차");
		lblRgb.setBounds(170, 0, 65, 20);
		lblRgb.setHorizontalAlignment(SwingConstants.CENTER);
		excutePanel.add(lblRgb);
		
		textField_10 = new JTextField();
		textField_10.setBounds(245, 0, 35, 20);
		textField_10.setHorizontalAlignment(SwingConstants.CENTER);
		textField_10.setText("0");
		textField_10.setColumns(10);
		excutePanel.add(textField_10);
		
		JButton button_2_1 = new JButton("조건추가");
		button_2_1.setBounds(477, 5, 90, 20);
		button_2_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createExcuteSubPanel(panel, excutePanel, createExcuteSubPanelList);
			}
		});
		panel.add(button_2_1);
		
		textField_11 = new JTextField();
		textField_11.setToolTipText("명령어 제목");
		textField_11.setBounds(0, 5, 150, 20);
		textField_11.setColumns(10);
		panel.add(textField_11);
		
		btnNewButton_7 = new JButton("목록추가");
		btnNewButton_7.setBounds(155, 5, 90, 20);
		btnNewButton_7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		panel.add(btnNewButton_7);
		
	}
	
	
	public void createExcuteSubPanel(JPanel panel, JPanel excutePanel, ArrayList<Object> createExcuteSubPanelList) {
		if(createExcuteSubPanelList.size()>=8) {
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
		
		JLabel lblNewLabel_7_1 = new JLabel("조건");
		lblNewLabel_7_1.setBounds(0, 0, 35, 20);
		lblNewLabel_7_1.setHorizontalAlignment(SwingConstants.LEFT);
		panel_1.add(lblNewLabel_7_1);
		
		JLabel label_1_1 = new JLabel("X");
		label_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1_1.setBounds(40, 0, 15, 20);
		panel_1.add(label_1_1);
		
		JTextField textField_3_1 = new JTextField();
		textField_3_1.setBounds(60, 0, 35, 20);
		textField_3_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_3_1.setColumns(10);
		textField_3_1.setText("0");
		panel_1.add(textField_3_1);
		
		JLabel label_2_1 = new JLabel("Y");
		label_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_2_1.setBounds(100, 0, 15, 20);
		panel_1.add(label_2_1);
		
		JTextField textField_4_1 = new JTextField();
		textField_4_1.setBounds(115, 0, 35, 20);
		textField_4_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_4_1.setColumns(10);
		textField_4_1.setText("0");
		panel_1.add(textField_4_1);
		
		JLabel lblR_1 = new JLabel("R");
		lblR_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblR_1.setBounds(170, 0, 15, 20);
		panel_1.add(lblR_1);
		
		JTextField textField_5_1 = new JTextField();
		textField_5_1.setBounds(185, 0, 35, 20);
		textField_5_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_5_1.setColumns(10);
		textField_5_1.setText("0");
		panel_1.add(textField_5_1);
		
		JLabel lblG_1 = new JLabel("G");
		lblG_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblG_1.setBounds(225, 0, 15, 20);
		panel_1.add(lblG_1);
		
		JTextField textField_6_1 = new JTextField();
		textField_6_1.setBounds(245, 0, 35, 20);
		textField_6_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_6_1.setColumns(10);
		textField_6_1.setText("0");
		panel_1.add(textField_6_1);
		
		JLabel lblB_1 = new JLabel("B");
		lblB_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblB_1.setBounds(285, 0, 15, 20);
		panel_1.add(lblB_1);
		
		JTextField textField_7_1 = new JTextField();
		textField_7_1.setBounds(305, 0, 35, 20);
		textField_7_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_7_1.setColumns(10);
		textField_7_1.setText("0");
		panel_1.add(textField_7_1);

		ButtonGroup buttonGroup = new ButtonGroup();
		JRadioButton rdbtnNewRadioButton = new JRadioButton("참");
		rdbtnNewRadioButton.setBounds(355, 0, 43, 20);
		rdbtnNewRadioButton.setSelected(true);
		buttonGroup.add(rdbtnNewRadioButton);
		panel_1.add(rdbtnNewRadioButton);
		
		JRadioButton radioButton = new JRadioButton("거짓");
		radioButton.setBounds(395, 0, 62, 20);
		buttonGroup.add(radioButton);
		panel_1.add(radioButton);
		
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
	
	String ip = "";
	String port = "9999";
	
	private JTextField textField;
	
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
			String command = txtAdb.getText()+" "+"connect "+getIpaddr(select_device);
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
//        	JOptionPane.showMessageDialog(null, "캡쳐완료");
        	if(newWindow==null) {
            	newWindow = new NewWindow(saveFilePath+"/screencap-sample.png"); // 클래스 newWindow를 새로 만들어낸다
        	} else {
        		newWindow.imageIcon = new ImageIcon(saveFilePath+"/screencap-sample.png");
        		newWindow.imageIcon.getImage().flush();
        		newWindow.newLabel.setIcon(newWindow.imageIcon);
        	}
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
	
	public static ServerSocket server;
	public List<ConnectionToClient> clients = new ArrayList<>();
	private JTextField textField_1;
	private JTextField textField_2;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_7;
	private JLabel label_1;
	private JTextField textField_3;
	private JTextField textField_4;
	private JLabel label_2;
	private JLabel lblR;
	private JTextField textField_5;
	private JLabel lblG;
	private JTextField textField_6;
	private JLabel lblB;
	private JTextField textField_7;
	private JTextField textField_13;
	private JButton btnNewButton_7;
	private JTextField textField_15;
	private JButton button_1;
	private JButton button_2;
	private JButton btnNewButton_8;
	private JLabel lblNewLabel_8;
	private JLabel lblNewLabel_9;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_12;
	private JTextField textField_14;
	private JTextField textField_16;
	private JTextField textField_11;
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
                	System.out.println(input);
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
					Lstart(input.trim());
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
            	e.printStackTrace();
            } finally {
            	excuteCallApp(excute);
            	if(!excute) {
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
	
	public void excuteStartServerSocket() {
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
			            excuteCallApp(excute);
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
			port = textField.getText();
			String command = txtAdb.getText();
			command += " "+"-s"+" "+select_device;
			command += " "+"shell am startforegroundservice -n mr.linage.com/mr.linage.com.service.MyService ";
			command += "--es 'socket_server_ip' '"+ip+"' ";
			command += "--es 'socket_server_port' '"+port+"' ";
			command += "--es 'type' 'action' ";
			command += "--es 'action_x' '"+textField_3.getText()+"' ";
			command += "--es 'action_y' '"+textField_4.getText()+"' ";
			command += "--es 'action_r' '"+textField_5.getText()+"' ";
			command += "--es 'action_b' '"+textField_6.getText()+"' ";
			command += "--es 'action_g' '"+textField_7.getText()+"' ";
			String[] commands = new String[] { command };
			String log = excuteCmd(commands, true);
			System.out.println(log);
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
    
    public void excuteCallApp(boolean excute) {
    	if(excute) {
        	excuteScreencap(list.getSelectedValue().toString());
        	excuteStartService(list.getSelectedValue().toString());
    	}
    }
    
    public void excuteAllEnabled(boolean flag) {
    	textField.setEnabled(flag);
    	txtAdb.setEnabled(flag);
    	button.setEnabled(flag);
    	btnNewButton_1.setEnabled(flag);
    	btnNewButton_2.setEnabled(flag);
    	btnNewButton_3.setEnabled(flag);
    	btnNewButton_4.setEnabled(flag);
    	list.setEnabled(flag);
    }
    
    public void Lstart(String msg) {
    	System.out.println("Lstart:msg:"+msg);
//		if("app_log_1".equals(msg)) {
//			if(!"".equals(textField.getText())&&!"".equals(txtShellInputTap.getText())) {
//				start1(textField.getText(),txtShellInputTap.getText());
//			}
//		} else if("app_log_2".equals(msg)) {
//			if(!"".equals(textField_2.getText())&&!"".equals(txtShellInputTap_1.getText())) {
//				start2(textField_2.getText(),txtShellInputTap_1.getText());
//			}
//		} else if("app_log_3".equals(msg)) {
//			if(!"".equals(textField_3.getText())&&!"".equals(txtShellInputTap_2.getText())) {
//				start3(textField_3.getText(),txtShellInputTap_2.getText());
//			}
//		} else if("app_log_4".equals(msg)) {
//			if(!"".equals(textField_4.getText())&&!"".equals(txtShellInputTap_3.getText())) {
//				start4(textField_4.getText(),txtShellInputTap_3.getText());
//			}
//		}
	}
}

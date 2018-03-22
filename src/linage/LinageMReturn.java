package linage;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LinageMReturn extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JFrame jFrame = null;
	
	public int preNum = 0;
	public int num = 0;
	public boolean f_flag = false;
	public Timer m_timer = null;
	public TimerTask m_task = null;
	
	JPanel jp = new JPanel();
	
	JButton b1 = new JButton("실행");
	JButton b2 = new JButton("종료");
	JButton b3 = new JButton("바로한번실행");

	JLabel j1 = new JLabel("상태"); // 레이블 초기화
	JLabel j2 = new JLabel("미실행"); // 레이블 초기화
	JLabel j3 = new JLabel("로그파일 위치"); // 레이블 초기화
	JLabel j4 = new JLabel("ADB 위치"); // 레이블 초기화
	JLabel j5 = new JLabel("명령어"); // 레이블 초기화
	
	JTextField t1 = new JTextField(30); // 텍스트필드 초기화
	JTextField t2 = new JTextField(30); // 텍스트필드 초기화
	JTextField t3 = new JTextField(10); // 텍스트필드 초기화
	
	boolean alwaysOnTop = true;
	
	public LinageMReturn() {
//		super("리니지M귀환");
		
		jFrame = this;
		
		this.setLayout(new FlowLayout());
		
		JPanel row1 = new JPanel();
		row1.setLayout(new GridLayout(1, 2));
		row1.add(j1);
		row1.add(j2);

		j1.setVerticalAlignment(SwingConstants.CENTER);
		j1.setHorizontalAlignment(SwingConstants.CENTER);
		j2.setVerticalAlignment(SwingConstants.CENTER);
		j2.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel row2 = new JPanel();
		row2.setLayout(new GridLayout(1, 2));
		row2.add(b1);
		row2.add(b2);

		b1.setVerticalAlignment(SwingConstants.CENTER);
		b1.setHorizontalAlignment(SwingConstants.CENTER);
		b2.setVerticalAlignment(SwingConstants.CENTER);
		b2.setHorizontalAlignment(SwingConstants.CENTER);
		
		j3.setVerticalAlignment(SwingConstants.CENTER);
		j3.setHorizontalAlignment(SwingConstants.CENTER);
		
		j4.setVerticalAlignment(SwingConstants.CENTER);
		j4.setHorizontalAlignment(SwingConstants.CENTER);
		
		j5.setVerticalAlignment(SwingConstants.CENTER);
		j5.setHorizontalAlignment(SwingConstants.CENTER);
		
		jp.add(row1);
		jp.add(row2);
		jp.add(j3);
		jp.add(t1);
		jp.add(j4);
		jp.add(t2);
		jp.add(j5);
		jp.add(t3);
		jp.add(new JPanel());
		jp.add(b3);
		
		jp.setLayout(new GridLayout(10, 1));
		
		this.add(jp);
		
		t1.setText("/sdcard/app_log.txt");
		t2.setText("D:\\AppData\\Android\\sdk\\platform-tools\\adb.exe");
		t3.setText("shell input tap 1250 650");
		
		this.setAlwaysOnTop(alwaysOnTop);
		this.setResizable(false);
		this.setVisible(true);
		this.setSize(365, 93);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					j2.setText("실행중");
					if(m_timer == null) {
						m_timer = new Timer();
						m_task = new TimerTask() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								String returnValue = "";
								try {
									String command = t2.getText()+" shell cat "+" "+t1.getText();
									String[] commands = new String[] { command };
									returnValue = excuteCmd(commands, true);
									num = Integer.parseInt(returnValue.split("::")[2]);
								} catch (Exception e) {
									// TODO Auto-generated catch block
//									e.printStackTrace();
									j2.setText("에러 : " + returnValue);
								}
								if(!f_flag) {
									f_flag = true;
									System.out.println("정지");
								} else {
									if(preNum < num) {
										start();
										close();
//										JOptionPane.showMessageDialog(null, "완료");
									}
								}
								preNum = num;
							}
						};
						m_timer.schedule(m_task, 1000, 100);
					}
				} catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		
		b3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e1) {
				// TODO Auto-generated method stub
				start();
				System.out.println("종료");
			}
		});
		
		j1.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				if(jFrame.isAlwaysOnTop()) {
					alwaysOnTop = false;
					System.out.println("항상위 해제");
				} else {
					alwaysOnTop = true;
					System.out.println("항상위");
				}
				jFrame.setAlwaysOnTop(alwaysOnTop);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		j2.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				if(jFrame.getSize().height<325) {
					System.out.println("설정창 열기");
					jFrame.setSize(365, 325);
				} else {
					System.out.println("설정창 닫기");
					jFrame.setSize(365, 93);
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void start() {
		System.out.println("실행");
		String command = t2.getText()+" "+t3.getText(); // 귀환(마지막 단축창)
		String[] commands = new String[] { command };
		excuteCmd(commands, false);
	}
	
	public void close() {
		try {
			System.out.println("종료");
			j2.setText("종료");
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
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public String excuteCmd(String[] commands, boolean returnValueFlag) {
		String returnValue = "";
		try {
			if(returnValueFlag) {
				for (String cmd : commands) {
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
	
	public static void main(String[] arg) {
		 new LinageMReturn();
	}
	
}

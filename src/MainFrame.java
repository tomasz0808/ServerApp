import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import org.apache.log4j.Logger;

public class MainFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JPanel  contentPane;
	private JButton btnStartServer;
	private JButton btnStopServer;
	private JButton btnMinimizeServer;
	private JButton btnExit;
	private JButton btnOpenLogFile;
	
	private JLabel  lblCurrentStatus;
	private JLabel  lblCurrentCompName;
	private JLabel  lblCurrentIP;
	
	private static String STATUS_RUNNING = "Running";
	private static String STATUS_STOPPED = "Stopped";
	private String hostName = "Unknown";
	private String IP="";	
	private static String IMAGE_PATH = "res/ic_launcher.png";

	public MainFrame() {
		Logger log = Logger.getLogger(MainFrame.class.getName());
		log.debug("Test");
		log.trace("Test 2");
		initFrame();
		initStartSettings();	
	}

	private void initFrame() {
		setTitle("MyPrompter Server");
		setIconImage(Toolkit.getDefaultToolkit().getImage(IMAGE_PATH));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 420, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnStartServer = new JButton("Start");
		btnStartServer.setBounds(10, 228, 89, 23);
		btnStartServer.addActionListener(this);
		contentPane.add(btnStartServer);
		
		btnStopServer = new JButton("Stop");
		btnStopServer.setBounds(109, 228, 89, 23);
		btnStopServer.addActionListener(this);
		contentPane.add(btnStopServer);
		
		btnMinimizeServer = new JButton("Minimize");
		btnMinimizeServer.setBounds(208, 228, 89, 23);
		btnMinimizeServer.addActionListener(this);
		contentPane.add(btnMinimizeServer);
		
		btnExit = new JButton("Exit");
		btnExit.setBounds(305, 228, 89, 23);
		btnExit.addActionListener(this);
		contentPane.add(btnExit);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.BLACK, Color.GRAY));
		mainPanel.setBounds(10, 11, 384, 200);
		contentPane.add(mainPanel);
		mainPanel.setLayout(null);
		
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setBounds(10, 11, 46, 14);
		mainPanel.add(lblStatus);
		
		JLabel lblCompName = new JLabel("Computer Name:");
		lblCompName.setBounds(10, 36, 99, 14);
		mainPanel.add(lblCompName);
		
		JLabel lblIPAddress = new JLabel("IP Address: ");
		lblIPAddress.setBounds(10, 61, 99, 14);
		mainPanel.add(lblIPAddress);
		
		JLabel lblConnectionType = new JLabel("Connection Type:");
		lblConnectionType.setBounds(10, 86, 99, 14);
		mainPanel.add(lblConnectionType);
		
		lblCurrentStatus = new JLabel();
		lblCurrentStatus.setBounds(119, 11, 129, 14);
		mainPanel.add(lblCurrentStatus);
		
		lblCurrentCompName = new JLabel();
		lblCurrentCompName.setBounds(119, 36, 129, 14);
		mainPanel.add(lblCurrentCompName);
		
		lblCurrentIP = new JLabel();
		lblCurrentIP.setBounds(119, 61, 129, 14);
		mainPanel.add(lblCurrentIP);
		
		JRadioButton rdbtnWifi = new JRadioButton("WiFi");
		rdbtnWifi.setBounds(115, 82, 50, 23);
		mainPanel.add(rdbtnWifi);
		
		JRadioButton rdbtnBluetooth = new JRadioButton("Bluetooth");
		rdbtnBluetooth.setBounds(167, 82, 81, 23);
		mainPanel.add(rdbtnBluetooth);
		
		ButtonGroup radioBtnGroup = new ButtonGroup();
		radioBtnGroup.add(rdbtnWifi);
		radioBtnGroup.add(rdbtnBluetooth);	
		
		btnOpenLogFile = new JButton("Open log file");
		btnOpenLogFile.setBounds(10, 166, 99, 23);
		mainPanel.add(btnOpenLogFile);
	}
	

	private void initStartSettings() {	
		lblCurrentStatus.setText(STATUS_STOPPED);
		btnStopServer.setEnabled(false);	
		
		if(!SystemTray.isSupported()){
			btnMinimizeServer.setEnabled(false);
		}
		setIpAndHostName();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object btnClicked = e.getSource();
			if(btnClicked == btnStartServer) {
				startServer();
			} else if (btnClicked == btnStopServer) {
				stopServer();
			} else if (btnClicked == btnMinimizeServer) {
				minimizeFrame();
			} else if (btnClicked == btnExit) {
				exitServer();
			} else if (btnClicked == btnOpenLogFile) {
				exitServer();
			}
	}

	private void startServer() {
		lblCurrentStatus.setText(STATUS_RUNNING);
		btnStopServer.setEnabled(true);
		btnStartServer.setEnabled(false);
	}
	
	private void stopServer() {
		lblCurrentStatus.setText(STATUS_STOPPED);
		btnStopServer.setEnabled(false);
		btnStartServer.setEnabled(true);
	}
	
	private void minimizeFrame() {
		if(!SystemTray.isSupported()){
			btnMinimizeServer.setEnabled(false);
		}
		
	}
	
	private void exitServer(){
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	private void setIpAndHostName() {
		try {
			InetAddress IPaddres = Inet4Address.getLocalHost();
			hostName = IPaddres.getHostName();
			IP = IPaddres.getHostAddress();
			lblCurrentCompName.setText(hostName);
			lblCurrentIP.setText(IP);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}

package com.smartrfid.tagtest;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.*;


public class gui {

    static String jarPath;
    static String timeStr;
    static NotEditableTableModel Model1;
    
    static JLabel ReaderStatus;
	static JLabel IsReading;
	static JLabel lapJLabel;
	
    static JTextField TimerValue;
    
    static JButton StartBtn;
    static JButton getAntPowerButton;
    static JButton setAntPowerButton;
    static JButton getAntEnableButton;
    static JButton setAntEnableButton;
    static JButton getBaseBandButton;
    static JButton setBaseBandButton;
    static JButton searchButton;
    static JButton newLapButton;
    static JButton getRFBandButton;
    static JButton setRFBandButton;
    static JButton clearButton;
    static JButton saveButton;
    
    static JSpinner antenna1PwrJSpinner;
    static JSpinner antenna2PwrJSpinner;
    static JSpinner antenna3PwrJSpinner;
    static JSpinner antenna4PwrJSpinner;
	static JSpinner tmrVal;
	
	static JCheckBox ant1EnBox;
	static JCheckBox ant2EnBox;
	static JCheckBox ant3EnBox;
	static JCheckBox ant4EnBox;
	
    static Choice rfbandDropDown;
    static Choice EPCSpeedDropdown;
    static Choice SessionChoice;
    static Choice QVChoice;
    static Choice searchTypeChoice;
    
    static rfidreader reader;
    static rfidreader reader2;
    
    static ImageIcon greenIcon;
	static ImageIcon redIcon;
	static ImageIcon grayIcon;
	static ImageIcon logo;
	static ImageIcon saveIcon;
	static ImageIcon searchIcon;
	static ImageIcon newLapIcon;
	static ImageIcon openFileIcon;
	static ImageIcon clearIcon;
	static ImageIcon startIcon;
	static ImageIcon stopIcon;
	
    static int[] ScanStartTime = new int[4];
    static int[] CurrTime = new int[4];
    static int ScanTime = 10;
    static int maxLaps = 25;
    
    
    static Calendar current;
    static javax.swing.Timer timer;
    
    private static int SecondRowStartX = 50;
    private static int ThirdRowStartX = 50;

    private static int SecondRowStartY = 500;
    private static int ThirdRowStartY = 500;
    
    public static void RFBandSelect(int pos) {
    	rfbandDropDown.select(pos);
	}
	
	public static void AntPwrJSpinnerSetValue(int[] antval) {
		antenna1PwrJSpinner.setValue(antval[0]);
		antenna2PwrJSpinner.setValue(antval[1]);
		antenna3PwrJSpinner.setValue(antval[2]);
		antenna4PwrJSpinner.setValue(antval[3]);
	}

    public void SetReaderInstance(rfidreader instance, rfidreader instance2) {
		reader = instance;
		reader2 = instance2;
	}
	
	public void StartButtonSetEnabled(boolean state) {
		StartBtn.setEnabled(state);
	}
	
	public void SearchButtonSetEnabled(boolean state) {
		searchButton.setEnabled(state);
	}
	
	public void NewLapButtonSetEnabled(boolean state) {
		newLapButton.setEnabled(state);
	}
	
    public void changeReaderStatusIcon(boolean status){
        if (status){
        	ReaderStatus.setIcon(greenIcon);
        } else {
        	ReaderStatus.setIcon(grayIcon);
        }
    }
    
    public void changeReadingIcon(boolean status){
        if (status){
        	IsReading.setIcon(greenIcon);
        } else {
        	IsReading.setIcon(grayIcon);
        }
    }
    
    public static void changeIsReadingGray(boolean status){
        if (status){
        	IsReading.setIcon(redIcon);
        } else {
        	IsReading.setIcon(grayIcon);
        }
    }
    
    public void addEPC(String num, String epcString) {
		Object[] row = {num,epcString};
		Model1.addRow(row);
	}
    
    public void editCell(String dataString, int row, int col) {
    	Model1.setValueAt(dataString, row, col);
    }
    
    public void readerSettingsUIsetEnable(boolean state) {
    	setAntPowerButton.setEnabled(state);
    	getAntPowerButton.setEnabled(state);
    	rfbandDropDown.setEnabled(state);
    	setAntEnableButton.setEnabled(state);
    	getAntEnableButton.setEnabled(state);
    	setBaseBandButton.setEnabled(state);
    	getBaseBandButton.setEnabled(state);
    	getRFBandButton.setEnabled(state);
    	setRFBandButton.setEnabled(state);
    }
    
    public void switchStartIcon(boolean state) {
		if (state) StartBtn.setIcon(startIcon);
		else StartBtn.setIcon(stopIcon);
	}
    
    public void clearResultsTable(int rows) {
    	if (rows > 0)
		for (int row = 0; row < rows; row++) {
			for (int col = 4; col < maxLaps+4; col++) {
				Model1.setValueAt("", row, col);
			}
		}
	}
    
    public void clearTable(int rows) {
    	if (rows > 0)
    	for (int row = rows; row > 0; row--) {
    		Model1.removeRow(row-1);
    	}
	}
    
    public String getNumber(int row) {
    	String responseString;
    	if (Model1.getValueAt(row, 2) == null) responseString = "";
    	else responseString = (String) Model1.getValueAt(row, 2);
		return responseString;
	}
    
    public String getNote(int row) {
    	String responseString;
    	if (Model1.getValueAt(row, 3) == null) responseString = "";
    	else responseString = (String) Model1.getValueAt(row, 3);
		return responseString;
	}
    
    public void setNumber(String number, int row) {
    	Model1.setValueAt(number, row, 2);
    }
    
    public void setNote(String[] note, int row) {
    	Model1.setValueAt(note[0],row,2);
    	Model1.setValueAt(note[1],row,3);
	}
    
    public String getSum(int row) {
		return (String) Model1.getValueAt(row, maxLaps+4);
	}
    
    public void SetLapNum(int lap) {
		lapJLabel.setText(Integer.toString(lap));
	}
    
    void CreateGui() {  
    	ImageIcon logoIcon = null;
    	
        try {
        	greenIcon = new ImageIcon(ImageIO.read(gui.class.getResource("res/gc.png")));
        	redIcon = new ImageIcon(ImageIO.read(gui.class.getResource("res/rc.png")));
        	grayIcon = new ImageIcon(ImageIO.read(gui.class.getResource("res/grayInd.png")));
        	logo = new ImageIcon(ImageIO.read(gui.class.getResource("res/logo.png")));
        	saveIcon = new ImageIcon(ImageIO.read(gui.class.getResource("res/saveIcon.png")));
        	searchIcon = new ImageIcon(ImageIO.read(gui.class.getResource("res/searchIcon.png")));
        	newLapIcon = new ImageIcon(ImageIO.read(gui.class.getResource("res/newLap.png")));
        	openFileIcon = new ImageIcon(ImageIO.read(gui.class.getResource("res/openFile.png")));
        	clearIcon = new ImageIcon(ImageIO.read(gui.class.getResource("res/clearIcon.jpg")));
        	startIcon = new ImageIcon(ImageIO.read(gui.class.getResource("res/start.png")));
        	stopIcon = new ImageIcon(ImageIO.read(gui.class.getResource("res/stop.png")));
        	logoIcon = new ImageIcon(ImageIO.read(gui.class.getResource("res/sportid.jpg")));
        } catch (Exception e){
        	StringWriter sw = new StringWriter();
        	PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            log.WriteLogString(sw.toString());
        }
		
		Font smallFont = new Font("Arial", Font.PLAIN, 20);
		Font mediumFont = new Font("Arial", Font.PLAIN, 26);
		Font largeFont = new Font("Arial", Font.PLAIN, 32);
	    UIManager.put("ToolTip.font",new Font("SansSerif", Font.BOLD, 18));
	    
		/////////////////////////////// MAIN PANEL
	
	    JPanel panel = new JPanel();
	    panel.setSize(200,600);
	    panel.setLayout(null);
	    panel.setBackground(Color.WHITE);
	
	    Model1 = new NotEditableTableModel();
	    String[] columnNames = new String[maxLaps+5];
	    int lapString = 1;
	    for (int i = 0; i < maxLaps+5; i++) {
	    	if (i == 0) columnNames[i] = "Num"; else
			if (i == 1) columnNames[i] = "EPC"; else
			if (i == 2) columnNames[i] = "Num"; else
			if (i == 3) columnNames[i] = "Note"; else
			if (i == maxLaps+4) columnNames[i] = "Sum"; else {
				columnNames[i] = Integer.toString(lapString);
				lapString ++;
			}
				
			}
        Model1.setColumnIdentifiers(columnNames);
        
	    JTable table1 = new JTable(Model1);
	    table1.setFont(mediumFont);
	    table1.getColumnModel().getColumn(0).setPreferredWidth(30);	
	    table1.getColumnModel().getColumn(1).setPreferredWidth(370);	
	    table1.getColumnModel().getColumn(2).setPreferredWidth(70);	
	    table1.getColumnModel().getColumn(2).setPreferredWidth(70);	
	    for (int i = 3; i < maxLaps+3; i++) {
	    	table1.getColumnModel().getColumn(i).setPreferredWidth(45);	
		}
	    table1.setRowHeight(50);

	    JPanel tableJPanel = new JPanel();
	    tableJPanel.setLayout(new BorderLayout());
	    tableJPanel.setSize(new Dimension(1900,600));
	    
	    final JScrollPane scrollPane = new JScrollPane(table1);
	    tableJPanel.add(scrollPane);
        panel.add(tableJPanel);
	    
	    //Second Row
	    StartBtn = new JButton();
	    StartBtn.setSize(50,50);
	    StartBtn.setLocation(SecondRowStartX + 10,SecondRowStartY + 135);
	    StartBtn.setEnabled(false);
	    StartBtn.setFont(mediumFont);
	    switchStartIcon(true);
	    StartBtn.setContentAreaFilled(false); 
	    StartBtn.setFocusPainted(false); 
	    StartBtn.setOpaque(false);
	    StartBtn.addActionListener(new ActionListener()
	    {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	Main.switchTestMode();
	        }
	    });
	    panel.add(StartBtn);
	    
	    newLapButton = new JButton(newLapIcon);
	    newLapButton.setSize(50,50);
	    newLapButton.setLocation(SecondRowStartX + 70, SecondRowStartY + 135);
	    newLapButton.setContentAreaFilled(false); 
	    newLapButton.setFocusPainted(false); 
	    newLapButton.setOpaque(false);
	    newLapButton.setToolTipText("Новый круг");
	    newLapButton.setEnabled(false);
	    newLapButton.addActionListener(new ActionListener()
	    {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	Main.newLapIncr();
	        }
	    });
	    panel.add(newLapButton);
	    
	    searchButton = new JButton(searchIcon);
	    searchButton.setSize(50,50);
	    searchButton.setLocation(SecondRowStartX + 130, SecondRowStartY + 135);
	    searchButton.setContentAreaFilled(false); 
	    searchButton.setFocusPainted(false); 
	    searchButton.setOpaque(false);
	    searchButton.setEnabled(false);
	    searchButton.setToolTipText("Поиск меток");
	    searchButton.addActionListener(new ActionListener()
	    {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	Main.switchSearchingMode();
	        }
	    });
	    panel.add(searchButton);
	    
	    JLabel lapTipJLabel = new JLabel("Круг :");
	    lapTipJLabel.setFont(mediumFont);
	    lapTipJLabel.setSize(110,25);
	    lapTipJLabel.setLocation(SecondRowStartX + 190,SecondRowStartY + 140);
	    panel.add(lapTipJLabel);
	    
	    lapJLabel = new JLabel("0");
	    lapJLabel.setFont(mediumFont);
	    lapJLabel.setSize(100,25);
	    lapJLabel.setLocation(SecondRowStartX + 260,SecondRowStartY + 140);
	    panel.add(lapJLabel);
	    
	    clearButton = new JButton(clearIcon);
	    clearButton.setSize(50,50);
	    clearButton.setLocation(SecondRowStartX + 70, SecondRowStartY + 195);
	    clearButton.setContentAreaFilled(false); 
	    clearButton.setFocusPainted(false); 
	    clearButton.setOpaque(false);
	    clearButton.setToolTipText("Очистить таблицу");
	    clearButton.addActionListener(new ActionListener()
	    {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	Main.ClearResults();
	        }
	    });
	    panel.add(clearButton);
	    
	    saveButton = new JButton(saveIcon);
	    saveButton.setSize(50,50);
	    saveButton.setLocation(SecondRowStartX + 130, SecondRowStartY + 195);
	    saveButton.setContentAreaFilled(false); 
	    saveButton.setFocusPainted(false); 
	    saveButton.setOpaque(false);
	    saveButton.setToolTipText("Сохранить результаты");
	    saveButton.addActionListener(new ActionListener()
	    {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	try {
					Main.saveResults();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
	        }
	    });
	    panel.add(saveButton);
	    
	
		JButton connectbtn = new JButton("Подключиться");
	    connectbtn.setSize(220,50);
	    connectbtn.setLocation(SecondRowStartX + 345,SecondRowStartY + 135);
	    connectbtn.setFont(mediumFont);
	    connectbtn.addActionListener(new ActionListener()
	    {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            if (reader.TCPConnect()) {
					StartButtonSetEnabled(true);
					SearchButtonSetEnabled(true);
					changeReaderStatusIcon(true);
					readerSettingsUIsetEnable(true);
				}
	            else changeReaderStatusIcon(false);
	        }
	    });
	    panel.add(connectbtn);
	    
	    JLabel RdyLabel = new JLabel("Готов :");
	    RdyLabel.setFont(mediumFont);
	    RdyLabel.setSize(110,25);
	    RdyLabel.setLocation(SecondRowStartX + 663,SecondRowStartY + 145);
	    panel.add(RdyLabel);
		
	    ReaderStatus = new JLabel();
	    if (grayIcon != null){ReaderStatus.setIcon(grayIcon);}
	    ReaderStatus.setSize(45,45);
	    ReaderStatus.setLocation(SecondRowStartX + 750,SecondRowStartY + 135);
	    panel.add(ReaderStatus);
	    
	    //Third row    
	    JButton disconnectBtn = new JButton("Отключиться");
	    disconnectBtn.setSize(220,50);
	    disconnectBtn.setLocation(ThirdRowStartX + 345,ThirdRowStartY + 195);
	    disconnectBtn.setFont(mediumFont);
	    disconnectBtn.addActionListener(new ActionListener()
	    {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            reader.TCPDisconnect();
				changeReaderStatusIcon(false);
				StartButtonSetEnabled(false);
				SearchButtonSetEnabled(false);
				readerSettingsUIsetEnable(false);
	        }
	    });
	    panel.add(disconnectBtn);
	    
	    JLabel Readinglabel = new JLabel("Считывание :");
	    Readinglabel.setFont(mediumFont);
	    Readinglabel.setSize(200,25);
	    Readinglabel.setLocation(ThirdRowStartX + 580,ThirdRowStartY + 205);
	    panel.add(Readinglabel);
	
	    IsReading = new JLabel();
	    if (grayIcon != null){IsReading.setIcon(grayIcon);}
	    IsReading.setSize(45,45);
	    IsReading.setLocation(ThirdRowStartX + 750,ThirdRowStartY + 195);
	    panel.add(IsReading);
	

	    //////////////////////////////////SETTINGS PANEL
	   
	    int AntPwrX = 30;
	    int AntPwrY = 30;
	    
	    int RFbandX = 30;
	    int RFbandY = 240;
	    
	    int ConnectX = 30;
	    int ConnectY = 400;
	    
	    int AntEnX = 450;
	    int AntEnY = 30;
	    
	    int BaseBandX = 450;
	    int BaseBandY = 240;
	    
	    JPanel SettingsPanel = new JPanel();
	    SettingsPanel.setSize(200,600);
	    SettingsPanel.setLayout(null);
	    SettingsPanel.setBackground(Color.WHITE);
	    
	    
	    //Antenna Power Block
	    
	    JLabel antennaPwr = new JLabel();
	    antennaPwr.setText("Мощность антенн :");
	    antennaPwr.setFont(mediumFont);
	    antennaPwr.setSize(350,25);
	    antennaPwr.setLocation(AntPwrX + 30,AntPwrY + 0);
	    SettingsPanel.add(antennaPwr);
	
	    SpinnerModel ant1PowerModel = new SpinnerNumberModel(5,0,33,1);
		antenna1PwrJSpinner = new JSpinner(ant1PowerModel);
		antenna1PwrJSpinner.setSize(70,30);
		antenna1PwrJSpinner.setLocation(AntPwrX + 0, AntPwrY + 30);
		antenna1PwrJSpinner.setFont(mediumFont);
		SettingsPanel.add(antenna1PwrJSpinner);  
		
		SpinnerModel ant2PowerModel = new SpinnerNumberModel(5,0,33,1);
		antenna2PwrJSpinner = new JSpinner(ant2PowerModel);
		antenna2PwrJSpinner.setSize(70,30);
		antenna2PwrJSpinner.setLocation(AntPwrX + 80, AntPwrY + 30);
		antenna2PwrJSpinner.setFont(mediumFont);
		SettingsPanel.add(antenna2PwrJSpinner);  
		
		SpinnerModel ant3PowerModel = new SpinnerNumberModel(5,0,33,1);
		antenna3PwrJSpinner = new JSpinner(ant3PowerModel);
		antenna3PwrJSpinner.setSize(70,30);
		antenna3PwrJSpinner.setLocation(AntPwrX + 170, AntPwrY + 30);
		antenna3PwrJSpinner.setFont(mediumFont);
		SettingsPanel.add(antenna3PwrJSpinner);  
		
		SpinnerModel ant4PowerModel = new SpinnerNumberModel(5,0,33,1);
		antenna4PwrJSpinner = new JSpinner(ant4PowerModel);
		antenna4PwrJSpinner.setSize(70,30);
		antenna4PwrJSpinner.setLocation(AntPwrX + 250, AntPwrY + 30);
		antenna4PwrJSpinner.setFont(mediumFont);
		SettingsPanel.add(antenna4PwrJSpinner); 
		
		getAntPowerButton = new JButton();
		getAntPowerButton.setSize(80,40);
		getAntPowerButton.setLocation(AntPwrX + 50,AntPwrY + 70);
		getAntPowerButton.setFont(mediumFont);
		getAntPowerButton.setText("Get");
		getAntPowerButton.setEnabled(false);
		getAntPowerButton.addActionListener(new ActionListener(){
	    	@Override
	        public void actionPerformed(ActionEvent e) {
	    		AntPwrJSpinnerSetValue(reader.getAntennaPwr());
	        }
	    });
		SettingsPanel.add(getAntPowerButton);
	    
	    setAntPowerButton = new JButton();
	    setAntPowerButton.setSize(80,40);
	    setAntPowerButton.setLocation(AntPwrX + 140,AntPwrY + 70);
	    setAntPowerButton.setFont(mediumFont);
	    setAntPowerButton.setText("Set");
	    setAntPowerButton.setEnabled(false);
	    setAntPowerButton.addActionListener(new ActionListener(){
	    	@Override
	        public void actionPerformed(ActionEvent e) {
	            reader.SetAntPower((Integer)antenna1PwrJSpinner.getValue(),(Integer) antenna2PwrJSpinner.getValue(), (Integer)antenna3PwrJSpinner.getValue(),(Integer) antenna4PwrJSpinner.getValue());
	        }
	    });
	    SettingsPanel.add(setAntPowerButton);
	
	    
	    // RFBand block
	    rfbandDropDown = new Choice();
	    rfbandDropDown.setSize(320,50);
	    rfbandDropDown.setLocation(RFbandX + 0, RFbandY + 0);
	    rfbandDropDown.setFont(mediumFont);
	    rfbandDropDown.add("GB 920-925Мгц");
	    rfbandDropDown.add("GB 840-845Мгц");
	    rfbandDropDown.add("GB 840-845Мгц и 920-925Мгц");
	    rfbandDropDown.add("FCC 902-928Мгц");
	    rfbandDropDown.add("ETSI 866-868Мгц");
	    rfbandDropDown.setEnabled(false);
	    SettingsPanel.add(rfbandDropDown);
	    
	    getRFBandButton = new JButton();
	    getRFBandButton.setSize(80,40);
	    getRFBandButton.setLocation(RFbandX + 50,RFbandY + 70);
	    getRFBandButton.setFont(mediumFont);
	    getRFBandButton.setText("Get");
	    getRFBandButton.setEnabled(false);
	    getRFBandButton.addActionListener(new ActionListener(){
	    	@Override
	        public void actionPerformed(ActionEvent e) {
				RFBandSelect(reader.getRFBand());
	        }
	    });
		SettingsPanel.add(getRFBandButton);
	    
	    setRFBandButton = new JButton();
	    setRFBandButton.setSize(80,40);
	    setRFBandButton.setLocation(RFbandX + 140,RFbandY + 70);
	    setRFBandButton.setFont(mediumFont);
	    setRFBandButton.setText("Set");
	    setRFBandButton.setEnabled(false);
	    setRFBandButton.addActionListener(new ActionListener(){
	    	@Override
	        public void actionPerformed(ActionEvent e) {
	    		try {
					reader.setRFBand(rfbandDropDown.getSelectedIndex());
				} catch (InterruptedException ex) {
		        	StringWriter sw = new StringWriter();
		        	PrintWriter pw = new PrintWriter(sw);
		            ex.printStackTrace(pw);
		            log.WriteLogString(sw.toString());
				}
	        }
	    });
	    SettingsPanel.add(setRFBandButton);
	    
	
	    
	    // Connect String Block
	    JLabel tcpStringJLabel = new JLabel();
	    tcpStringJLabel.setSize(270,25);
	    tcpStringJLabel.setLocation(ConnectX + 0, ConnectY +  0);
	    tcpStringJLabel.setFont(mediumFont);
	    tcpStringJLabel.setText("Строка подключения");
	    SettingsPanel.add(tcpStringJLabel);
	    
	    JTextField tcpStringField = new JTextField();
	    tcpStringField.setSize(300,35);
	    tcpStringField.setLocation(ConnectX + 0, ConnectY +  40);
	    tcpStringField.setFont(mediumFont);
	    tcpStringField.setMargin(new Insets(0,20,0,0));
	    tcpStringField.setText(reader.getTCPString());
	    SettingsPanel.add(tcpStringField);
	    
	    JButton applyTcpStringButton = new JButton();
	    applyTcpStringButton.setSize(65,50);
	    applyTcpStringButton.setLocation(ConnectX + 310, ConnectY + 33);
	    applyTcpStringButton.setFont(smallFont);
	    applyTcpStringButton.setText("OK");
	    applyTcpStringButton.addActionListener(new ActionListener(){
	    	@Override
	        public void actionPerformed(ActionEvent e) {
	    		reader.setTCPString(tcpStringField.getText());
	        }
	    });
	    SettingsPanel.add(applyTcpStringButton);

	    //Antenna Enable Block
	    JLabel antEnableJLabel = new JLabel();
	    antEnableJLabel.setSize(200,25);
	    antEnableJLabel.setLocation(AntEnX + 80, AntEnY + 0);
	    antEnableJLabel.setFont(mediumFont);
	    antEnableJLabel.setText("Опрос антенн :");
	    SettingsPanel.add(antEnableJLabel);
	    
	    ant1EnBox = new JCheckBox();
	    ant1EnBox.setLocation(AntEnX + 0,AntEnY + 30);
	    ant1EnBox.setSize(100,40);
	    ant1EnBox.setFont(mediumFont);
	    ant1EnBox.setLabel("Ant 1");
	    SettingsPanel.add(ant1EnBox);
	    
	    ant2EnBox = new JCheckBox();
	    ant2EnBox.setLocation(AntEnX + 100,AntEnY + 30);
	    ant2EnBox.setSize(100,40);
	    ant2EnBox.setFont(mediumFont);
	    ant2EnBox.setLabel("Ant 2");
	    SettingsPanel.add(ant2EnBox);
	    
	    ant3EnBox = new JCheckBox();
	    ant3EnBox.setLocation(AntEnX + 200,AntEnY + 30);
	    ant3EnBox.setSize(100,40);
	    ant3EnBox.setFont(mediumFont);
	    ant3EnBox.setLabel("Ant 3");
	    SettingsPanel.add(ant3EnBox);
	    
	    ant4EnBox = new JCheckBox();
	    ant4EnBox.setLocation(AntEnX + 300,AntEnY + 30);
	    ant4EnBox.setSize(100,40);
	    ant4EnBox.setFont(mediumFont);
	    ant4EnBox.setLabel("Ant 4");
	    SettingsPanel.add(ant4EnBox);
	    
	    setAntEnableButton = new JButton();
	    setAntEnableButton.setSize(80,40);
	    setAntEnableButton.setLocation(AntEnX + 80,AntEnY + 80);
	    setAntEnableButton.setFont(mediumFont);
	    setAntEnableButton.setText("Get");
	    setAntEnableButton.setEnabled(false);
	    setAntEnableButton.addActionListener(new ActionListener(){
	    	@Override
	        public void actionPerformed(ActionEvent e) {
	            //ant1EnBox.isSelected();
	    		boolean[] antenabled = new boolean[4];
	    		antenabled = reader.getAntennasEnabled();
	    		ant1EnBox.setSelected(antenabled[0]);
	    		ant2EnBox.setSelected(antenabled[1]);
	    		ant3EnBox.setSelected(antenabled[2]);
	    		ant4EnBox.setSelected(antenabled[3]);
	        }
	    });
	    SettingsPanel.add(setAntEnableButton);
	    
	    getAntEnableButton = new JButton();
	    getAntEnableButton.setSize(80,40);
	    getAntEnableButton.setLocation(AntEnX + 170,AntEnY + 80);
	    getAntEnableButton.setFont(mediumFont);
	    getAntEnableButton.setText("Set");
	    getAntEnableButton.setEnabled(false);
	    getAntEnableButton.addActionListener(new ActionListener(){
	    	@Override
	        public void actionPerformed(ActionEvent e) {
	    		reader.setAntennasEnabled(ant1EnBox.isSelected(),ant2EnBox.isSelected(),ant3EnBox.isSelected(),ant4EnBox.isSelected());
	        }
	    });
	    SettingsPanel.add(getAntEnableButton);
	    
	    // BaseBand block
	    
	    JLabel EPCSpeedLabel = new JLabel("EPC Speed");
	    EPCSpeedLabel.setFont(smallFont);
	    EPCSpeedLabel.setSize(120,50);
	    EPCSpeedLabel.setLocation(BaseBandX + 0, BaseBandY + 0);
	    SettingsPanel.add(EPCSpeedLabel);
	    
	    EPCSpeedDropdown = new Choice();
	    EPCSpeedDropdown.setSize(420,50);
	    EPCSpeedDropdown.setLocation(BaseBandX + 120, BaseBandY + 0);
	    EPCSpeedDropdown.setFont(mediumFont);
	    EPCSpeedDropdown.add("0-Tari=25us, FM0, LHF=40KHz");
	    EPCSpeedDropdown.add("1-Tari=25us, Miller4, LHF=250KHz");
	    EPCSpeedDropdown.add("2-Tari=25us, Miller4, LHF=300KHz");
	    EPCSpeedDropdown.add("3-Tari=6.25us, FM0, LHF=400KHz");
	    EPCSpeedDropdown.add("255-Auto");
	    SettingsPanel.add(EPCSpeedDropdown);
	    
	    JLabel sessionJLabel = new JLabel("Session");
	    sessionJLabel.setFont(smallFont);
	    sessionJLabel.setSize(80,50);
	    sessionJLabel.setLocation(BaseBandX + 0, BaseBandY + 50);
	    SettingsPanel.add(sessionJLabel);
	    
	    SessionChoice = new Choice();
	    SessionChoice.setSize(80,50);
	    SessionChoice.setLocation(BaseBandX + 80, BaseBandY + 50);
	    SessionChoice.setFont(mediumFont);
	    SessionChoice.add("0");
	    SessionChoice.add("1");
	    SessionChoice.add("2");
	    SessionChoice.add("3");
	    SettingsPanel.add(SessionChoice);
	    
	    JLabel qVJLabel = new JLabel("QV");
	    qVJLabel.setFont(smallFont);
	    qVJLabel.setSize(50,50);
	    qVJLabel.setLocation(BaseBandX + 170, BaseBandY + 50);
	    SettingsPanel.add(qVJLabel);
	    
	    QVChoice = new Choice();
	    QVChoice.setSize(80,50);
	    QVChoice.setLocation(BaseBandX + 220, BaseBandY + 50);
	    QVChoice.setFont(mediumFont);
	    for (int i = 0; i < 16; i++) {
	    	QVChoice.add(Integer.toString(i));
		}
	    SettingsPanel.add(QVChoice);
	    
	    JLabel searchtypeJLabel = new JLabel("Search Type ");
	    searchtypeJLabel.setFont(smallFont);
	    searchtypeJLabel.setSize(150,50);
	    searchtypeJLabel.setLocation(BaseBandX + 0, BaseBandY + 100);
	    SettingsPanel.add(searchtypeJLabel);
	    
	    searchTypeChoice = new Choice();
	    searchTypeChoice.setSize(250,50);
	    searchTypeChoice.setLocation(BaseBandX + 150, BaseBandY + 100);
	    searchTypeChoice.setFont(mediumFont);
	    searchTypeChoice.add("0 - Single Flag");
	    searchTypeChoice.add("1 - Flag B");
	    searchTypeChoice.add("2 - Flag A&B");
	    SettingsPanel.add(searchTypeChoice);
	    
	    getBaseBandButton = new JButton("Get");
	    getBaseBandButton.setSize(80,40);
	    getBaseBandButton.setLocation(BaseBandX + 0,BaseBandY + 150);
	    getBaseBandButton.setFont(mediumFont);
	    getBaseBandButton.setEnabled(false);
	    getBaseBandButton.addActionListener(new ActionListener(){
	    	@Override
	        public void actionPerformed(ActionEvent e) {
	    		int[] params = new int[4];
	    		params = reader.getEPCBaseBand();
	    		if (params[0] == 255) EPCSpeedDropdown.select(4);
	    		else EPCSpeedDropdown.select(params[0]);
	    		QVChoice.select(params[1]);
	    		SessionChoice.select(params[2]);
	    		searchTypeChoice.select(params[3]);
	        }
	    });
	    SettingsPanel.add(getBaseBandButton);
	    
	    setBaseBandButton = new JButton("Set");
	    setBaseBandButton.setSize(80,40);
	    setBaseBandButton.setLocation(BaseBandX + 90,BaseBandY + 150);
	    setBaseBandButton.setFont(mediumFont);
	    setBaseBandButton.setEnabled(false);
	    setBaseBandButton.addActionListener(new ActionListener(){
	    	@Override
	        public void actionPerformed(ActionEvent e) {
	    		int basebandMode;
	    		if (EPCSpeedDropdown.getSelectedIndex() == 4) {basebandMode = 255;}
	    		else basebandMode = EPCSpeedDropdown.getSelectedIndex();
	    		reader.setEPCBaseBand(basebandMode, QVChoice.getSelectedIndex(), SessionChoice.getSelectedIndex(), searchTypeChoice.getSelectedIndex());
	        }
	    });
	    SettingsPanel.add(setBaseBandButton);
	    
	    JButton testButton = new JButton("Test");
	    testButton.setSize(40,40);
	    
	    table1.addMouseListener(new java.awt.event.MouseAdapter() {
	        @Override
	        public void mouseClicked(java.awt.event.MouseEvent evt) {
	            int row = table1.rowAtPoint(evt.getPoint());
	            int col = table1.columnAtPoint(evt.getPoint());
	            if (row >= 2 && col >= 2) {
	                System.out.println("click");
	            }
	        }
	    });
	    
	    /////////////////
	    
	    JTabbedPane tabbedpanel = new JTabbedPane();
	    tabbedpanel.addTab("Application", panel);
	    tabbedpanel.addTab("Settings", SettingsPanel);
	    tabbedpanel.setFont(mediumFont);
	    
	    
	    JFrame frame = new JFrame("Tag Test");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.getContentPane().add(tabbedpanel);//
	    //frame.setLayout(new BorderLayout());
	    frame.setPreferredSize(new Dimension(1920, 1080));
	    frame.setIconImage(logo.getImage());
	    frame.pack();
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	}
}
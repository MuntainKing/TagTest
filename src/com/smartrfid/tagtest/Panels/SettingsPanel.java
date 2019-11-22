package com.smartrfid.tagtest.Panels;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import com.smartrfid.tagtest.log;
import com.smartrfid.tagtest.rfidreader;

public class SettingsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	JButton getAntPowerButton;
    JButton setAntPowerButton;
    JButton getAntEnableButton;
    JButton setAntEnableButton;
    JButton getBaseBandButton;
    JButton setBaseBandButton;
    JButton getRFBandButton;
    JButton setRFBandButton;
    
    
    JSpinner antenna1PwrJSpinner;
    JSpinner antenna2PwrJSpinner;
    JSpinner antenna3PwrJSpinner;
    JSpinner antenna4PwrJSpinner;
	
	JCheckBox ant1EnBox;
	JCheckBox ant2EnBox;
	JCheckBox ant3EnBox;
	JCheckBox ant4EnBox;
	
    Choice rfbandDropDown;
    Choice EPCSpeedDropdown;
    Choice SessionChoice;
    Choice QVChoice;
    Choice searchTypeChoice;
    
    rfidreader reader;
	
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
    
    public SettingsPanel(rfidreader newReader) {
    	
    	reader = newReader;
    	
    	Font smallFont = new Font("Arial", Font.PLAIN, 20);
		Font mediumFont = new Font("Arial", Font.PLAIN, 26);
		Font largeFont = new Font("Arial", Font.PLAIN, 32);
    	
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
        //return SettingsPanel;
	}
    
	public void AntPwrJSpinnerSetValue(int[] antval) {
		antenna1PwrJSpinner.setValue(antval[0]);
		antenna2PwrJSpinner.setValue(antval[1]);
		antenna3PwrJSpinner.setValue(antval[2]);
		antenna4PwrJSpinner.setValue(antval[3]);
	}
	
    public void RFBandSelect(int pos) {
    	this.rfbandDropDown.select(pos);
	}
	
}

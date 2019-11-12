package com.smartrfid.tagtest;


import static com.clou.uhf.G3Lib.Enumeration.eReadType.Inventory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.management.StringValueExp;

import org.omg.CORBA.portable.ValueBase;

import com.clou.uhf.G3Lib.CLReader;
import com.clou.uhf.G3Lib.Tag6C;
import com.clou.uhf.G3Lib.ClouInterface.IAsynchronousMessage;
import com.clou.uhf.G3Lib.Enumeration.eRF_Range;
import com.clou.uhf.G3Lib.Models.GPI_Model;
import com.clou.uhf.G3Lib.Models.Tag_Model;

public class rfidreader implements IAsynchronousMessage{

	boolean conn;
	String tcpParam = "192.168.0.116:9090";
	String commParam = "COM12:115200";
	
	public void setEPCBaseBand(int basebandMode, int qValue, int session, int searchType) {
		try {
			CLReader._Config.SetEPCBaseBandParam(tcpParam, basebandMode, qValue, session, searchType);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public int[] getEPCBaseBand() {
		int[] params = new int[4];
		String[] answerStrings = new String[4];
		try {
			String answerString = CLReader._Config.GetEPCBaseBandParam(tcpParam);
			System.out.println(answerString);
			answerStrings = answerString.split("\\|");
			params[0] = Integer.parseInt(answerStrings[0]);
			params[1] = Integer.parseInt(answerStrings[1]);
			params[2] = Integer.parseInt(answerStrings[2]);
			params[3] = Integer.parseInt(answerStrings[3]);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return params;
		
	}
	
	public void setAntennasEnabled(boolean ant1, boolean ant2, boolean ant3, boolean ant4) {
		int antValue;
		int ant1val = ant1? 1 : 0;
		int ant2val = ant2? 1 : 0;
		int ant3val = ant3? 1 : 0;
		int ant4val = ant4? 1 : 0;
		antValue = ant1val * 1 + ant2val * 2 + ant3val * 4 + ant4val * 8;
		try {
			CLReader._Config.SetReaderANT(tcpParam, antValue);
			System.out.println("Antenna enable value " + antValue);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean[] getAntennasEnabled() {
		boolean[] array = new boolean[4];
		String antEnValueString;
		String[] values = new String[4];
		try {
			antEnValueString = CLReader._Config.GetReaderANT2(tcpParam);
			System.out.println("Antennas enabled string "+antEnValueString);
			values = antEnValueString.split(",");
			for (int i = 0; i < values.length; i++) {
				if (Integer.parseInt(values[i]) == 1) {
					array[0] = true;
				}
				if (Integer.parseInt(values[i]) == 2) {
					array[1] = true;
				}
				if (Integer.parseInt(values[i]) == 3) {
					array[2] = true;
				}
				if (Integer.parseInt(values[i]) == 4) {
					array[3] = true;
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Antennas enabled : "+Boolean.toString(array[0])+" "+Boolean.toString(array[1])+" "+Boolean.toString(array[2])+" "+Boolean.toString(array[3]));
		return array;
	}

	public void writeUserData() {
		Tag6C.WriteUserData(tcpParam, 1,"test123", 0);
		System.out.println("User data written");
		//Tag6C.WriteUserData_MatchEPC(tcpParam, 15, "test123", 0, "E2000019180B009405303E97", 0);
	}
	

	public void SetAntPower(int ant1val, int ant2val, int ant3val, int ant4val) {

		HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();

		hashMap.put(1, ant1val);
		hashMap.put(2, ant2val);
		hashMap.put(3, ant3val);
		hashMap.put(4, ant4val);

		for (HashMap.Entry entry : hashMap.entrySet()) {
			System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
		}
		try {
			com.clou.uhf.G3Lib.CLReader._Config.SetANTPowerParam(tcpParam,hashMap);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setTCPString(String tcpstring) {
		tcpParam = tcpstring;
	}
	public String getTCPString() {
		return tcpParam;
	}

	public boolean TCPConnect(){
		if (!conn) {
			conn = CLReader.CreateTcpConn(tcpParam,this);
			try {
				if(conn){
					System.out.println("Connection success " + tcpParam);
					return true;
				} else {
					System.out.println("Connection failure!");
					return false;
				}
			} catch (Exception e) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				log.WriteLogString(sw.toString());
				return false;
			}
		}
		return true;
	}

	public boolean COMConnect() {
		if (!conn) {
			conn = CLReader.CreateSerialConn(commParam,this);
			try {
				if(conn){
					System.out.println("connection success...");
					return true;
				} else {
					System.out.println("connection failure!");
					return false;
				}
			} catch (Exception e) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				log.WriteLogString(sw.toString());
				return false;
			}
		}
		return true;
	}

	public boolean TCPDisconnect(){
		if (conn) {
			System.out.println("Reader disconnected " + tcpParam);
			CLReader.CloseConn(tcpParam);
			conn = false;
			return true;
		} else return false;
	}

	public void stopReader() {
		 CLReader.Stop(tcpParam);
		 System.out.println("stopped reading");
	}
	
	public void startReader(String antEnString) {
		 Tag6C.GetEPC_TID(tcpParam, Integer.parseInt(antEnString, 2), Inventory);
		 System.out.println("started reading, ant enabled "+ Integer.parseInt(antEnString, 2));
	}
	
	public int[] getAntennaPwr(){
		String curpower;
		int[] AntennaPwr = new int[4];
		try {
			curpower = CLReader._Config.GetANTPowerParam2(tcpParam);
			String[][] resArray = new String[4][2];

			System.out.println("Antenna power string "+curpower);

			if (curpower != "Failed to get!") {
				String[] pairs = curpower.split("&");
				for (int i = 0; i < pairs.length; i++) {
					String pair = pairs[i];
					String[] keyValue = pair.split(",");
					resArray[i][0] = keyValue[0];
					resArray[i][1] = keyValue[1];
				}
				AntennaPwr[0] = Integer.parseInt(resArray[0][1]);
				AntennaPwr[1] = Integer.parseInt(resArray[1][1]);
				AntennaPwr[2] = Integer.parseInt(resArray[2][1]);
				AntennaPwr[3] = Integer.parseInt(resArray[3][1]);		
			}   
		} catch (InterruptedException e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			log.WriteLogString(sw.toString());
		}
		return AntennaPwr;
	}


	public void setRFBand(int setting) throws InterruptedException {
		eRF_Range rfsetting = eRF_Range.GB_840_to_845MHz;

		switch (setting) {
		case 0:
			rfsetting = eRF_Range.GB_920_to_925MHz;
			break;

		case 1:
			rfsetting = eRF_Range.GB_840_to_845MHz;
			break;

		case 2:
			rfsetting = eRF_Range.GB_920_to_925MHz_and_GB_840_to_845MHz;
			break;

		case 3:
			rfsetting = eRF_Range.FCC_902_to_928MHz;
			break;

		case 4:
			rfsetting = eRF_Range.ETSI_866_to_868MHz;
			break;
		}
		com.clou.uhf.G3Lib.CLReader._Config.SetReaderRF(tcpParam, rfsetting);
		System.out.println("New rfband "+rfsetting.toString());
	}

	public int getRFBand() {
		eRF_Range rfbandtERF_Range;
		int baseband = 0;
		try {
			rfbandtERF_Range = CLReader._Config.GetReaderRF(tcpParam);
			switch (rfbandtERF_Range) {
				case GB_920_to_925MHz: 
					baseband = 0;
					break;
				case GB_840_to_845MHz: 
					baseband = 1;
					break;
				case GB_920_to_925MHz_and_GB_840_to_845MHz: 
					baseband = 2;
					break;
				case FCC_902_to_928MHz: 
					baseband = 3;
					break;
				case ETSI_866_to_868MHz: 
					baseband = 4;
					break;
			default:
				break;
			}

		} catch (NumberFormatException e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			log.WriteLogString(sw.toString());
		} catch (InterruptedException e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			log.WriteLogString(sw.toString());
		}
		return baseband;
	}

	@Override
	public void GPIControlMsg(GPI_Model arg0) {}

	@Override
	public void OutPutTags(Tag_Model arg0) {
		Main.EPCEvent(arg0._EPC);
	}

	@Override
	public void OutPutTagsOver() {}

	@Override
	public void PortClosing(String arg0) {}

	@Override
	public void PortConnecting(String arg0) {}

	@Override
	public void WriteDebugMsg(String arg0) {}

	@Override
	public void WriteLog(String arg0) {}

}

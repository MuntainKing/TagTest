package com.smartrfid.tagtest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

import javax.swing.JFileChooser;

public class Main {
	static gui guiInst;
	static rfidreader reader;
	static boolean SearchingMode = false;
	static boolean ActualTestMode = false;
	static boolean[][] MainArray;
	static int lap = 0;
	static String[] epcStrings;
	static int epcCount = 0;
	static int Bottlecount = 0;
	static String jarPath;

	public static void main(String[] args) {
		guiInst = new gui();
		reader = new rfidreader();
		guiInst.SetReaderInstance(reader, null);
		guiInst.CreateGui();
		epcStrings = new String[80];
		MainArray = new boolean[80][25];
		jarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String decodedPath;
		try {
			decodedPath = URLDecoder.decode(jarPath, "UTF-8");
			jarPath = decodedPath.substring(0, decodedPath.lastIndexOf("/") + 1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(jarPath);
	}

	public static void EPCEvent(String epcString) {
		if (SearchingMode) {
			boolean uniqueEpc = true;
			//Ищем уники
			for (int i = 0; i < epcStrings.length; i++) {
				if (epcStrings[i] != null && epcStrings[i].equals(epcString)) {uniqueEpc = false;}
			}
			//Если найденная метка уникальна, то добавляем строку в таблицу, добавляем епс в  массив, ищем в файле номер и заметку и добавляем их в таблицу
			if (uniqueEpc) {
				guiInst.addEPC(Integer.toString(epcCount+1),epcString);
				epcStrings[epcCount] = epcString;
				guiInst.setNote(getNote(epcString), epcCount);
				epcCount++;
			}
		}
		if (ActualTestMode && lap < 25) {
			for (int i = 0; i < epcStrings.length; i++) {
				if (epcStrings[i] != null && epcStrings[i].equals(epcString)) {
					guiInst.editCell("Да", i, lap +4);
					MainArray[i][lap] = true;
				}
			}
		}
	}
	
	public static void newLapIncr() {
		if (ActualTestMode) {
			int sum = 0;
			for (int row = 0; row < epcCount; row++) {
				sum = 0;
				if (MainArray[row][lap] != true) {
					MainArray[row][lap] = false;
					guiInst.editCell("Нет", row, lap + 4);
				}
				for (int col = 0; col < MainArray[row].length; col++) {
					if (MainArray[row][col]) sum++;
				}
				guiInst.editCell(Integer.toString(sum), row, 29);
			}
			if (lap < 25) {
				lap++;
				guiInst.SetLapNum(lap+1);
			}
			if (lap == 25) switchTestMode();
			System.out.println("lap "+ lap);
		}
	}
	
	public static void switchTestMode() {
		if (ActualTestMode) {
			reader.stopReader();
			guiInst.changeReadingIcon(false);
			guiInst.NewLapButtonSetEnabled(false);
			guiInst.switchStartIcon(true);
			guiInst.SearchButtonSetEnabled(true);
		}
		else if (!SearchingMode) {
			reader.startReader("1111");
			guiInst.changeReadingIcon(true);
			guiInst.switchStartIcon(false);
			guiInst.NewLapButtonSetEnabled(true);
			guiInst.SearchButtonSetEnabled(false);	
		}
		ActualTestMode = !ActualTestMode;
	}
	
	public static void switchSearchingMode() {
		if (SearchingMode) {
			reader.stopReader();
			guiInst.changeReadingIcon(false);
			guiInst.readerSettingsUIsetEnable(true);
			guiInst.StartButtonSetEnabled(true);
		}
		else if (!ActualTestMode) {
			reader.startReader("1111");
			Bottlecount = 0;
			guiInst.changeReadingIcon(true);
			guiInst.readerSettingsUIsetEnable(false);
			guiInst.StartButtonSetEnabled(false);
			guiInst.clearTable(epcCount);
			for (int i = 0; i < MainArray.length; i++) {
				for (int j = 0; j < MainArray[i].length; j++) {
					MainArray[i][j] = false;
				}
			}
			for (int i = 0; i < epcStrings.length; i++) {
				epcStrings[i] = null;
			}
			epcCount = 0;
			lap = 0;
			guiInst.SetLapNum(lap+1);
		}
		SearchingMode = !SearchingMode;
	}
	
	public static boolean isSearching() {return SearchingMode;}
	
	public static void ClearResults() {
		guiInst.clearResultsTable(epcCount);
		for (int i = 0; i < MainArray.length; i++) {
			for (int j = 0; j < MainArray[i].length; j++) {
				MainArray[i][j] = false;
			}
		}
		lap = 0;
		guiInst.SetLapNum(lap+1);
	}
	
	public static void saveResults() throws IOException {
		File fileToSave = null;
		JFileChooser chooser = new JFileChooser();
		chooser.setSelectedFile(new File("test1.csv"));
		int userSelection = chooser.showSaveDialog(null);
		
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			fileToSave = chooser.getSelectedFile();
		    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
		}
		OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(fileToSave,false),Charset.forName("UTF-8").newEncoder());
		
		String writeTofileString;
		
		
		writeTofileString = "EPC;Numb;Note;";
		for (int j = 1; j < 26; j++) {
			writeTofileString = writeTofileString + Integer.toString(j)+";";
		}
		writeTofileString = writeTofileString + "Sum";
		os.append(writeTofileString);
		os.append("\r\n");
		
		
		for (int i = 0; i < epcCount; i++) {
			writeTofileString = epcStrings[i] + ";" + guiInst.getNumber(i)+";"+guiInst.getNote(i)+";";
			for (int j = 0; j < MainArray[i].length; j++) {
				writeTofileString = writeTofileString + String.valueOf(MainArray[i][j] + ";");
			}
			writeTofileString = writeTofileString + guiInst.getSum(i);
			os.append(writeTofileString);
			os.append("\r\n");
		}
		os.close();
	}
	
	private static String[] getNote(String EPC) {
		String[] Note = new String[2];
		String[] lineSpl;
		try {
			//BufferedReader reader = new BufferedReader(new FileReader(jarPath + "tags.csv"));
			File tagFile = new File(jarPath+"tags.csv");
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(tagFile.getAbsolutePath()), "UTF8"));
			String line = reader.readLine();
			while (line != null) {
				lineSpl = line.split(",");
				if (lineSpl[0].equals(EPC)) {
					Note[0] = lineSpl[1];
					Note[1] = lineSpl[2];
				}
				line = reader.readLine();
			}
			reader.close();
		}catch (Exception e) {
			System.out.println(e.getMessage());

		}
		if (Note[0] != null) {
			Bottlecount++;
			System.out.println("Bottle count "+Bottlecount);
		}
		System.out.println("Suggesting " + Note[0]+" "+Note[1]);
		return Note;
	}
}

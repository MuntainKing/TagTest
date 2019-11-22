package com.smartrfid.tagtest;

import javax.swing.*;
import javax.swing.table.*;

import org.hyperic.sigar.Cpu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class RFIDRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;
	boolean[][] status = new boolean[80][30];

    public void setArrayBoundaries(int rowsCount,int colsCount) {
		status = new boolean[rowsCount][colsCount];
	}
    
    public RFIDRenderer() {
        setOpaque(true);
    }

    public void setCellState(int row, int column, boolean state){
        this.status[row][column] = state;
    }

    public void resetData(){
        for(int i = 0; i < status.length; i++ ) {
        	for (int j = 0; j < status[i].length; j++) {
        		this.status[i][j] = false;
			}
        }
    }

    //@Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value,isSelected, hasFocus, row, column);
        
        if (this.status[row][column] == true){
            setBackground(Color.green);
            setForeground(Color.black);
        } else {
            setBackground(Color.white);
            setForeground(Color.black);
        }
        setValue(value);
        return this;
    }
}
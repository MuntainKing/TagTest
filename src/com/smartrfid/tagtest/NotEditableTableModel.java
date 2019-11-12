package com.smartrfid.tagtest;
import javax.swing.table.DefaultTableModel;

public class NotEditableTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	public boolean isCellEditable(int row, int column) {
		if (column == 1) return true;
		else return false;
    }
}

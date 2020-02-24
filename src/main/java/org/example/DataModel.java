package org.example;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class DataModel extends AbstractTableModel {
    private String[] columnNames={"序号","jar名称"};
    private  DataDao dataDao;
    private  Object[][] tableData;
    private List<String> globalJarNameList;

    public DataModel(){
        this.dataDao=new DataDao();
        this.tableData=dataDao.getTableData();
        this.globalJarNameList=dataDao.getGlobalList();
    }
    @Override
    public int getRowCount() {
        return this.tableData.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return tableData[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0,columnIndex).getClass();
    }

    /**
     * 统统返回true 这样才可以复制单元格内容
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    /**
     * 将表格模型（dataModel）的数据源切换成dataDao返回的新的表格数据,用于更新表格
     * @param jarNameList
     */
    public void updateTableData(List<String> jarNameList){
        this.tableData=dataDao.getTableData(jarNameList);
    }

    public void updateTableData(){
        this.tableData=dataDao.getTableData();
    }


    /**
     * 得到初始化时的完整的数据源（最开始的 list）
     * @return
     */
    public List<String> getGlobalJarNameList(){
        return this.globalJarNameList;
    }

    public DataDao getDataDao() {
        return dataDao;
    }
}

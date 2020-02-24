package org.example;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public  class GuiManager {

    private Map<String, JTable> tableMapCache;

    public GuiManager() {
        this.tableMapCache = new HashMap<>();
    }


    /**
     *
     * @param name String tableName
     * @param table JTable instance
     */
    public void setTableCache(String name,JTable table){
        this.tableMapCache.put(name,table);
    }

    /**
     *
     * @param name tableName
     * @return JTable
     */
    public JTable getTableCache(String name){
        JTable table = this.tableMapCache.get(name);
        if (null==table)return null;
        return table;
    }




}

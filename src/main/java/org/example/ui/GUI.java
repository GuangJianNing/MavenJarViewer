package org.example.ui;

import org.example.ui.modelCache.GuiManager;
import org.example.model.DataModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class GUI {
    private GuiManager guiManager = new GuiManager();
    private DataModel dataModel = new DataModel();

    private JFrame frame;
    private JTextField jarNameTextInput;
    private JTextField repositoryPathInput;
    private JButton setPathButton;
    private JButton searchButton;
    private JTable table;
    private JPanel jPanel;
    private JScrollPane jScrollPane;
    private JLabel pathLabel,jarNameTipLabel;

    public GUI(){
        this.graphGui();
    }

    private void initJLabel(){
        this.pathLabel=new JLabel("路径名");
        this.jarNameTipLabel=new JLabel("jar名字");
    }

    private void initJFrame() {
        this.frame = new JFrame("jarViewer");
        frame.setSize(1500, 700);
        frame.setLocation(200, 200);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    private void initJTextField() {
        //  columns优先级大于 dimension
        this.repositoryPathInput=new JTextField("路径名称，形如D:\\maven\\repository",40);
        this.jarNameTextInput = new JTextField("jar包名称", 30);
        jarNameTextInput.setPreferredSize(new Dimension(100, 40));
        repositoryPathInput.setPreferredSize(new Dimension(100,40));

    }

    private void initJButton() {
        this.searchButton = new JButton("搜索");
        this.setPathButton=new JButton("确定");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = jarNameTextInput.getText();
                List<String> globalList = dataModel.getGlobalJarNameList();
                List<String> filteredStringList = globalList.parallelStream().filter(str -> str.contains(text.toLowerCase())).collect(Collectors.toList());
                dataModel.updateTableData(filteredStringList);
                JTable table = guiManager.getTableCache("table");
                table.updateUI();
            }
        });

        setPathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pathText = repositoryPathInput.getText();
                dataModel.getDataDao().setRepositoryPath(pathText);
                try {
                    dataModel.updateTableData();
                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(null,"可能路径有误","错误提示",1);
                    System.out.println(ex);
                }
                JTable table = guiManager.getTableCache("table");
                table.updateUI();

            }
        });
    }

    private void initJPanel() {
        this.jPanel = new JPanel();
    }

    private void initJTable() {
        this.table = new JTable(this.dataModel);

        table.setRowHeight(30);
        table.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        guiManager.setTableCache("table", table);
    }

    private void initJScrollPanel(JTable table) {
        this.jScrollPane = new JScrollPane(table);
    }

    private void initUiComponents(){
        this.initJLabel();
        this.initJTextField();
        this.initJButton();
        this.initJPanel();
        this.initJTable();
        this.initJScrollPanel(this.table);
        this.initJFrame();//必须放在最后一个初始化
    }

    /**
     * 组装ui需要的组件
     */
    private void initUI(){
        jPanel.add(this.pathLabel);
        jPanel.add(this.repositoryPathInput);
        jPanel.add(this.setPathButton);
        jPanel.add(this.jarNameTipLabel);
        jPanel.add(this.jarNameTextInput);
        jPanel.add(this.searchButton);

        frame.add(this.jPanel, BorderLayout.NORTH);
        frame.add(this.jScrollPane, BorderLayout.CENTER);
    }


    public void graphGui() {
        this.initUiComponents();
        this.initUI();
    }


}

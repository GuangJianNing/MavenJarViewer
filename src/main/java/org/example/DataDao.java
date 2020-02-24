package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 */
/**设计思想
     * 设置一个全局的list指向全部数据
     * 再声明一个数据源list
     * 当搜索时将数据源切换至搜索以后的数据
     * 当不搜索，或者什么都不用输入的时候数据源切换至全局list
     * 最后刷新即可
 * 创建表格的时候将表格对象缓存起来，刷新表格的时候，复用该对象
 *   创建一个GUIManager
 *   在第一次创建表格的时候，将表格对象保存起来
 *   点击搜索的时候复用该对象，避免重复创建
 *
 */

public class DataDao {
    private List<String> globalList;
    private String repositoryPath="D:\\maven\\repository";

    public void setRepositoryPath(String repositoryPath) {
        this.repositoryPath = repositoryPath;
    }

    public List<String> getGlobalList() {
        return globalList;
    }

    /**
     * 根据当前的文件名称，递归寻找其绝对路径
     * @param stringBuffer 用于拼接字符串
     * @param file 需要寻找路径的文件
     * @return 文件路径，方向是反的
     */
    public String getRoots(StringBuffer stringBuffer, File file) {

        if (file.getParentFile() != null) {
//            System.out.print(file.getName());
            stringBuffer.append(".");
            stringBuffer.append(file.getName());
            getRoots(stringBuffer, file.getParentFile());
        }
        return stringBuffer.toString();

    }

    @Deprecated
    public List<String> getJars(List<String> jarFileNames, File file) throws StringIndexOutOfBoundsException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files[0].isFile()) {
                for (File fileItem : files) {
                    String name = fileItem.getName();
                    String fileType = null;
                    try {
                        //由于这里会有一个没有后缀名的文件会报异常 我也没找到在哪里
                        //需要捕获异常
                        fileType = name.substring(name.lastIndexOf("."));
                    } catch (StringIndexOutOfBoundsException e) {
                        String roots = getRoots(new StringBuffer(), fileItem);
                        System.err.println(roots);
                        System.err.println(e);
//                        continue;
                    }
                    if (file != null && fileType.equals(".jar")) {
                        jarFileNames.add(name);
                    }

                }
            } else {
                for (File fileItem : files) {
                    getJars(jarFileNames, fileItem);
                }
            }

        }


        return jarFileNames;
    }

    /**
     * extract jar names from the path with 递归
     * @param jarFileNames
     * @param file
     * @return
     */
    public List<String> getJar2(List<String> jarFileNames, File file) {
        File[] files = file.listFiles();
        if (file.isDirectory()) {
            for (File fileItem : files) {
                getJar2(jarFileNames, fileItem);
            }
        } else {
            String name = file.getName();
            String fileType;
            fileType = name.substring(name.lastIndexOf("."));
            if (fileType.equals(".jar")) {
                jarFileNames.add(name);
            }

        }
        return jarFileNames;
    }

    /**
     *
     * @param pathName the path of repository
     * @return
     * @throws NullPointerException
     */
    protected File getFile(String pathName) throws NullPointerException {
        File file = new File(pathName);
        return file.exists() ? file : null;
    }

    /**
     *调用getJar2以后，返回jar包名称的List
     * @return List contain jars' names from repository
     */
    protected List<String> getJarData(){
        List<String> data=new ArrayList<>();
        File file=this.getFile(this.repositoryPath);
        this.getJar2(data,file);
        this.globalList=data;
        return data;
    }

    /**
     * 返回包含全部jar名字的list，用于界面初始化
     * @return
     */
    public Object[][] getTableData(){

        Object[][] tableData = this.getTableData(this.getJarData());
        return tableData;
    }

    /**
     *当界面输入特定jar包名称搜索时，会在原始集合中过滤出相应的名字的list，重新生成表格数据
     * @param jarNames 过滤后的jar包名称的list
     * @return 过滤后的表格数据
     */
    public Object[][] getTableData(List<String> jarNames){
        List<String> stringList= jarNames;
        int rowNum=stringList.size();
        Object[][] data=new Object[rowNum][2];
        for (int i=0;i<rowNum;i++){
            data[i][0]=i+1;
            for (int j=1;j<2;j++){
                data[i][j]=stringList.get(i);
            }
        }
        return data;
    }



}

package org.example.subject;

import org.example.dao.Observer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PathSubject implements BaseSubject {
    private String repoPath;
    private boolean isStateChanged;
    private List<Observer> observerList=new ArrayList<>(2);

    @Override
    public void addObserve(Observer observer) {
        this.observerList.add(observer);
    }

    @Override
    public void removeObserve(Observer observer) {
        this.observerList.remove(observer);
    }

    @Override
    public boolean isStateChanged() {
        return this.isStateChanged;
    }



    @Override
    public void setState(String path) {
        this.isStateChanged=false;
        if (null==repoPath||!(repoPath.equals(repoPath))){
            this.repoPath=path;
            isStateChanged=true;
        }


    }

    @Override
    public void notifyObservers() {
        if (this.isStateChanged()){
            for (Observer observer:observerList){
                observer.setState(this.repoPath);
            }
        }
    }
}

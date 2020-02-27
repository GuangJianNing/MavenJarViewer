package org.example.subject;

import org.example.dao.DataDao;
import org.example.dao.Observer;

import javax.swing.*;

public interface BaseSubject {


    public void setState(String path);
    public void addObserve(Observer observer);
    public void removeObserve(Observer observer);
    public void notifyObservers();
    public boolean isStateChanged();
}

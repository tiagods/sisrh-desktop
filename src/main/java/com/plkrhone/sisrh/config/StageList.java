/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plkrhone.sisrh.config;

import java.util.HashMap;
import java.util.Map;
import javafx.stage.Stage;

/**
 * @author Tiago
 */
@SuppressWarnings("restriction")
public class StageList {

    private static StageList instance;
	private final Map<Object, Stage> mapStages = new HashMap<>();

    //classe usada para armazenar as stage abertas
    public static StageList getInstance() {
        if (instance == null)
            instance = new StageList();
        return instance;
    }
    private StageList(){
    }
	public Stage findScene(Object classe) {
        return mapStages.get(classe);
    }
	public void addScene(Object classe, Stage newScene) {
        this.mapStages.put(classe, newScene);
    }
    public void removeScene(Object classe) {
        this.mapStages.remove(classe);
    }
    public void clear(){
    	this.mapStages.clear();
    }
}

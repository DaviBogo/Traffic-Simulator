/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.udesc.ceavi.dsd.factory;

import br.udesc.ceavi.dsd.model.road.Road;


/**
 *
 * @author davib
 */
public interface AbstractFactory {
    
    public Road createRoad(int colunm, int row, int value);
}

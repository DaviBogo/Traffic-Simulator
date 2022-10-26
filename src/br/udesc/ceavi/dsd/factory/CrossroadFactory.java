/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.udesc.ceavi.dsd.factory;

import br.udesc.ceavi.dsd.model.road.Crossroad;
import br.udesc.ceavi.dsd.model.road.Road;

/**
 *
 * @author davib
 */
public class CrossroadFactory implements AbstractFactory {

    @Override
    public Road createRoad(int colunm, int row, int value) {
        return new Crossroad(colunm, row, value);
    }

}

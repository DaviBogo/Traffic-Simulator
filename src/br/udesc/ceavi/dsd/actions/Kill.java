/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.udesc.ceavi.dsd.actions;

import br.udesc.ceavi.dsd.service.SystemService;
import br.udesc.ceavi.dsd.model.car.Car;
import br.udesc.ceavi.dsd.model.road.Road;

/**
 *
 * @author davib
 */
public class Kill implements ActionInterface {

    private final Road currentCarRoad;

    public Kill(Road currentCarRoad) {
        this.currentCarRoad = currentCarRoad;
    }
    
    @Override
    public void execute() {
        Car car = currentCarRoad.getCar();
        currentCarRoad.setCar(null);
        currentCarRoad.release();
        currentCarRoad.repaint();

        car.kill();
        SystemService.getInstance().notifyCarDied(car);
    }

}

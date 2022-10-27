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
public class EnterMesh implements ActionInterface {
    
    private Car car;
    private Road road;

    public EnterMesh(Car car, Road randomRoad) {
        this.car = car;
        this.road = randomRoad;
    }
            
    @Override
    public void execute() {
        SystemService system = SystemService.getInstance();
        do {
            if (road.stopRoad()) {
                road.setCar(car);
                car.setRoad(road);
            } else {
                road = system.getMeshService().getRandomRespawn();
            }
        } while (car.getRoad() == null);
        road.repaint();
        system.notifyEnteredMesh(car);
    }
    
}

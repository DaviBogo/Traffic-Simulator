/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.udesc.ceavi.dsd.actions;

import br.udesc.ceavi.dsd.model.car.Car;
import br.udesc.ceavi.dsd.model.road.Road;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author davib
 */
public class MoveThroughCrossroad implements ActionInterface {
    
    private Road beggining;
    private Road destination;

    private final List<Road> path;
    private final Random random;

    public MoveThroughCrossroad(Road beggining, Road destination, List<Road> path) {
        this.beggining = beggining;
        this.destination = destination;
        this.path = Collections.unmodifiableList(path);
        this.random = new Random();
    }

    @Override
    public void execute() {
        Car car = beggining.getCar();
        int invalidExit = 0;
        int triesLimit = random.nextInt(10) + 1;
        boolean released;
        do {
            released = true;
            if (destination.stopRoad()) {

                for (int i = 0; i < path.size() - 1; i++) {
                    Road road = path.get(i);
                    if (!road.stopRoad()) {
                        destination.release();
                        released = releaseResource(i);
                        break;
                    }
                }

            } else {
                invalidExit++;
                released = false;
            }

            if (invalidExit >= triesLimit) {
                break;
            }

            if (!released) {
                car.sleep(100 + random.nextInt(200));
            }

        } while (!released);

        if (invalidExit < triesLimit) {
            int speed = car.getSpeed();

            beggining.removeCar();
            beggining.repaint();

            Road firstRoad = path.get(0);
            firstRoad.setCar(car);
            car.setRoad(firstRoad);
            firstRoad.repaint();

            beggining.release();
            car.sleep(speed);

            for (int i = 0; i < path.size() - 1; i++) {
                Road currentRoad = path.get(i);
                currentRoad.removeCar();
                currentRoad.repaint();

                Road newRoad = path.get(i + 1);
                newRoad.setCar(car);
                car.setRoad(newRoad);
                newRoad.repaint();

                currentRoad.release();

                car.sleep(speed);
            }
        }
    }

    private boolean releaseResource(int i) {
        for (int j = (i - 1); j >= 0; j--) {
            path.get(j).release();
        }
        return false;
    }

}

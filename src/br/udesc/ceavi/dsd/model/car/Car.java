/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.udesc.ceavi.dsd.model.car;

import br.udesc.ceavi.dsd.actions.ActionInterface;
import br.udesc.ceavi.dsd.actions.EnterMesh;
import br.udesc.ceavi.dsd.model.road.Road;
import br.udesc.ceavi.dsd.util.Image;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author davib
 */
public class Car extends Thread {
    
    private boolean alive;
    private Road road;
    private ActionInterface route;
    private final int speed;
    private final int rgb;
    
    public Car() {
        this.alive = true;
        this.speed = 200 + new Random().nextInt(300);
        this.rgb = Image.generateRGB();
    }
    
    public void kill() {
        this.alive = false;
    }

    public void getRouteFromRoad() {
        route = this.road.getRoute();
    }

    public void setRoad(Road newRoad) {
        this.road = newRoad;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Car other = (Car) obj;
        return this.getId() == other.getId();
    }

    public Road getRoad() {
        return this.road;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void sleep(int tempo) {
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException ex) {
            Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void enterSimulation(Road randomRoad) {
        this.route = new EnterMesh(this, randomRoad);
        start();
    }

    public void move() {
        this.route.execute();
        this.route = null;
    }

    @Override
    public void run() {
        move();
        sleep(this.speed);
        while (this.alive) {
            getRouteFromRoad();
            if (this.route == null) {
                break;
            }
            move();
            sleep(this.speed);
        }
    }

    public int getRgb() {
        return rgb;
    }
    
}

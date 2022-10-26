/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.udesc.ceavi.dsd.model.road;

import br.udesc.ceavi.dsd.model.car.Car;
import br.udesc.ceavi.dsd.actions.ActionInterface;
import br.udesc.ceavi.dsd.controller.SystemController;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author davib
 */
public abstract class Road {

    private Car car;
    private int column;
    private int row;
    private Random random;
    private List<ActionInterface> actions;
    private int value;
    private Lock lock;

    public Road(int column, int row, int value) {
        this.actions = new ArrayList<>();
        this.column = column;
        this.value = value;
        this.row = row;
        this.lock = new ReentrantLock(true);
    }

    public void addRoute(ActionInterface ActionInterface) {
        if (!actions.isEmpty()) {
            this.random = new Random();
        }
        actions.add(ActionInterface);
    }

    public ActionInterface getRoute() {
        if (actions.size() > 1) {
            return actions.get(random.nextInt(actions.size()));
        } else {
            return actions.get(0);
        }
    }

    @Override
    public String toString() {
        return "Road{" + "column=" + column + ", row=" + row + '}';
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public Car getCar() {
        return this.car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Car removeCar() {
        Car c = this.car;
        this.car = null;
        return c;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.column;
        hash = 59 * hash + this.row;
        return hash;
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
        final Road other = (Road) obj;
        if (this.column != other.column) {
            return false;
        }
        return this.row == other.row;
    }
    
    public void release() {
        lock.unlock();
    }

    public boolean stopRoad() {
        try {
            return lock.tryLock(15, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(Road.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public int getValue() {
        return value;
    }
    
    public abstract void move(Car car);

    public void repaint() {
        SystemController.getInstance().getMeshController().clearRoad(this.column, this.row);
        if (car != null) {
            SystemController.getInstance().getMeshController().printRoadCar(car.getRgb(), this.column, this.row);
        }
    }
}

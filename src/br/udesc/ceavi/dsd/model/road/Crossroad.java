/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.udesc.ceavi.dsd.model.road;

import br.udesc.ceavi.dsd.model.car.Car;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author davib
 */
public class Crossroad extends Road {
    
    private Lock lock;

    public Crossroad(int colunm, int row, int value) {
        super(colunm, row, value);
        this.lock = new ReentrantLock(true);
    }

    @Override
    public void move(Car car) {
        lock.lock();
        Road casaAnterior = car.getRoad();
        if (casaAnterior != null) {
            casaAnterior.setCar(null);
        }
        car.setRoad(this);
        setCar(car);
    }

    @Override
    public void release() {
        lock.unlock();
    }

    @Override
    public boolean stopRoad() {
        try {
            return lock.tryLock(15, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(Crossroad.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}

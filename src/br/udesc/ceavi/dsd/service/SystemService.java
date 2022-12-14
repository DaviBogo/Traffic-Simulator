package br.udesc.ceavi.dsd.service;

import br.udesc.ceavi.dsd.model.car.Car;
import br.udesc.ceavi.dsd.util.ReadMatrixFile;
import br.udesc.ceavi.dsd.view.MainFrameObserver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;



//bla

/**
 *
 * @author davib
 */
public class SystemService {

    private static SystemService instance;

    public static synchronized SystemService getInstance() {
        if (instance == null) {
            instance = new SystemService();
        }
        return instance;
    }

    private MeshService meshService;

    private int carsNumber;

    private boolean simulationAlive;

    private Random random;

    private Map<Long, Car> waitingCars;
    private Map<Long, Car> carsOnMesh;
    private List<MainFrameObserver> observers;

    private SystemService() {
        this.random = new Random();
        this.carsOnMesh = new HashMap<>();
        this.waitingCars = new HashMap<>();
        this.simulationAlive = false;
        this.observers = new ArrayList<>();
    }

    public void addObserver(MainFrameObserver aThis) {
        this.observers.add(aThis);
    }
    
    public void readFile(String text) throws Exception {
        ReadMatrixFile read = new ReadMatrixFile(text);
        meshService = new MeshService(read.getMatrix());
    }

    public boolean readySimulation() {
        return true;
    }

    public int getColumn() {
        return meshService.getLastColumn();
    }

    public int getRow() {
        return meshService.getLastRow();
    }

    public Object getCasa(int col, int row) {
        return meshService.getRoadValue(col, row);
    }

    public MeshService getMeshService() {
        return meshService;
    }

    public void rebutMesh() {
        this.meshService.removeObservers();
    }

    public void startSimulation(int numeroCarro) {
        this.carsNumber = numeroCarro;
        this.simulationAlive = true;

        for (int i = 0; i < carsNumber; i++) {
            newCarOnMesh();
        }

        Thread respawn = new Thread(() -> addAutomatically());
        respawn.start();
    }

    private void newCarOnMesh() {
        Car car = new Car();
        waitingCars.put(car.getId(), car);
        car.enterSimulation(meshService.getRandomRespawn());
    }

    public void notifyEnteredMesh(Car car) {
        waitingCars.remove(car.getId());
        carsOnMesh.put(car.getId(), car);
        SwingUtilities.invokeLater(() -> observers.forEach((observer) -> observer.notifyCarNumber(carsOnMesh.size())));
    }

    public void notifyCarDied(Car car) {
        carsOnMesh.remove(car.getId());
        SwingUtilities.invokeLater(() -> observers.forEach((observer) -> observer.notifyCarNumber(carsOnMesh.size())));
    }

    private void addAutomatically() {
        while (simulationAlive) {
            for (int i = (waitingCars.size() + carsOnMesh.size()); i < carsNumber; i++) {
                newCarOnMesh();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(SystemService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        List<Car> arrayList = new ArrayList<>();
        arrayList.addAll(carsOnMesh.values());
        arrayList.addAll(waitingCars.values());

        arrayList.forEach((value) -> {
            try {
                value.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(SystemService.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        observers.forEach(obs -> obs.notifySimulationOver());
    }

    public void stopRespawn() {
        simulationAlive = false;
    }
}

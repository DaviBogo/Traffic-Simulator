/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.udesc.ceavi.dsd.service;

import br.udesc.ceavi.dsd.actions.Kill;
import br.udesc.ceavi.dsd.actions.Move;
import br.udesc.ceavi.dsd.actions.MoveThroughCrossroad;
import br.udesc.ceavi.dsd.model.road.Road;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import br.udesc.ceavi.dsd.view.MatrixObserver;

/**
 *
 * @author davib
 */
public class MeshService {

    private int[][] matrix;
    private int validRoadsNumber;
    private Road[][] roadMatrix;

    private List<Road> respawnRoads;
    private List<Road> deathRoads;

    private List<MatrixObserver> observers;

    private Random random;

    public MeshService(int[][] matrix) {
        this.matrix = matrix;
        this.observers = new ArrayList<>();
        this.roadMatrix = new Road[matrix.length][matrix[0].length];
        this.deathRoads = new ArrayList<>();
        this.respawnRoads = new ArrayList<>();
        this.validRoadsNumber = 0;
        this.random = new Random();
    }

    public void initMesh() {
        initRoads();
        setRoadEnds();
        setCommands();
    }

    public void addObserver(MatrixObserver observer) {
        this.observers.add(observer);
    }

    public int getColumn() {
        return roadMatrix.length;
    }

    public int getRow() {
        return roadMatrix[0].length;
    }

    public Object getRoadValue(int col, int row) {
        return roadMatrix[col][row].getValue();
    }

    private void initRoads() {
        for (int row = 0; row < matrix[0].length; row++) {
            for (int column = 0; column < matrix.length; column++) {
                roadMatrix[column][row] = new Road(column, row, matrix[column][row]);
                int value = roadMatrix[column][row].getValue();
                if (value == 1 || value == 2 || value == 3 || value == 4) {
                    validRoadsNumber++;
                }
            }
        }
    }

    private void setRoadEnds() {
        deathRoads.clear();
        respawnRoads.clear();
        for (int row = 0; row < getRow(); row++) {
            for (int column = 0; column < getColumn(); column++) {
                if (roadMatrix[column][row].getValue() != 0) {
                    int side = 0;
                    int roadValue = roadMatrix[column][row].getValue();

                    if (column == 0) {
                        side = 1;
                    } else if (row == 0) {
                        side = 2;
                    } else if (column == getColumn() - 1) {
                        side = 3;
                    } else if (row == getRow() - 1) {
                        side = 4;
                    }
                    switch (side) {
                        case 1:
                            switch (roadValue) {
                                case 2:
                                    this.respawnRoads.add(roadMatrix[column][row]);
                                    break;
                                case 4:
                                    this.deathRoads.add(roadMatrix[column][row]);
                                    break;
                            }
                            break;
                        case 2:
                            switch (roadValue) {
                                case 1:
                                    this.deathRoads.add(roadMatrix[column][row]);
                                    break;
                                case 3:
                                    this.respawnRoads.add(roadMatrix[column][row]);
                                    break;
                            }
                            break;
                        case 3:
                            switch (roadValue) {
                                case 2:
                                    this.deathRoads.add(roadMatrix[column][row]);
                                    break;
                                case 4:
                                    this.respawnRoads.add(roadMatrix[column][row]);
                                    break;
                            }
                            break;
                        case 4:
                            switch (roadValue) {
                                case 1:
                                    this.respawnRoads.add(roadMatrix[column][row]);
                                    break;
                                case 3:
                                    this.deathRoads.add(roadMatrix[column][row]);
                                    break;
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    public void setCommands() {
        for (Road road : deathRoads) {
            road.addRoute(new Kill(road));
        }
        for (int linha = 0; linha < getRow(); linha++) {
            for (int coluna = 0; coluna < getColumn(); coluna++) {
                if (roadMatrix[coluna][linha].getValue() != 0) {
                    Road destination;
                    Road beggining = roadMatrix[coluna][linha];
                    if (!deathRoads.contains(beggining)) {
                        switch (roadMatrix[coluna][linha].getValue()) {
                            case 1:
                                destination = roadMatrix[coluna][linha - 1];
                                if (isCrossroad(destination)) {
                                    buildCrossroadPath(beggining);
                                } else {
                                    beggining.addRoute(new Move(beggining, destination));
                                }
                                break;
                            case 2:
                                destination = roadMatrix[coluna + 1][linha];
                                if (isCrossroad(destination)) {
                                    buildCrossroadPath(beggining);
                                } else {
                                    beggining.addRoute(new Move(beggining, destination));
                                }
                                break;
                            case 3:
                                destination = roadMatrix[coluna][linha + 1];
                                if (isCrossroad(destination)) {
                                    buildCrossroadPath(beggining);
                                } else {
                                    beggining.addRoute(new Move(beggining, destination));
                                }
                                break;
                            case 4:
                                destination = roadMatrix[coluna - 1][linha];
                                if (isCrossroad(destination)) {
                                    buildCrossroadPath(beggining);
                                } else {
                                    beggining.addRoute(new Move(beggining, destination));
                                }
                                break;
                            default:
                                beggining.addRoute(new Kill(beggining));
                                break;
                        }
                    }
                }
            }
        }
    }

    public boolean isCrossroad(Road casa) {
        int valor = casa.getValue();
        return !(valor == 1 || valor == 2 || valor == 3 || valor == 4);
    }

    public int getNumberOfValidRoads() {
        return validRoadsNumber;
    }

    public void removeObservers() {
        observers.clear();
    }

    public Road getRandomRespawn() {
        return respawnRoads.get(random.nextInt(respawnRoads.size()));
    }

    public void clearRoad(int colunm, int row) {
        observers.forEach((observer) -> observer.clearTableCell(colunm, row));
    }

    public void printRoadCar(int color, int colunm, int row) {
        observers.forEach((observer) -> observer.printCar(color, colunm, row));
    }

    private void buildCrossroadPath(Road beggining) {
        Road exit1;
        Road exit2;
        Road exit3;
        Road action1;
        Road action2;
        Road action3;
        switch (beggining.getValue()) {
            case 1:
                action1 = roadMatrix[beggining.getColumn()][beggining.getRow() - 1];
                action2 = roadMatrix[beggining.getColumn()][beggining.getRow() - 2];
                action3 = roadMatrix[beggining.getColumn() - 1][beggining.getRow() - 2];
                exit1 = roadMatrix[beggining.getColumn() + 1][beggining.getRow() - 1];
                if (isValidHouse(exit1)) {
                    beggining.addRoute(new MoveThroughCrossroad(beggining, exit1, Arrays.asList(action1, exit1)));
                }
                exit2 = roadMatrix[beggining.getColumn()][beggining.getRow() - 3];
                if (isValidHouse(exit2)) {
                    beggining.addRoute(new MoveThroughCrossroad(beggining, exit2, Arrays.asList(action1, action2, exit2)));
                }
                exit3 = roadMatrix[beggining.getColumn() - 2][beggining.getRow() - 2];
                if (isValidHouse(exit3)) {
                    beggining.addRoute(new MoveThroughCrossroad(beggining, exit3, Arrays.asList(action1, action2, action3, exit3)));
                }
                break;
            case 2:
                action1 = roadMatrix[beggining.getColumn() + 1][beggining.getRow()];
                action2 = roadMatrix[beggining.getColumn() + 2][beggining.getRow()];
                action3 = roadMatrix[beggining.getColumn() + 2][beggining.getRow() - 1];
                exit1 = roadMatrix[beggining.getColumn() + 1][beggining.getRow() + 1];
                if (isValidHouse(exit1)) {
                    beggining.addRoute(new MoveThroughCrossroad(beggining, exit1, Arrays.asList(action1, exit1)));
                }
                exit2 = roadMatrix[beggining.getColumn() + 3][beggining.getRow()];
                if (isValidHouse(exit2)) {
                    beggining.addRoute(new MoveThroughCrossroad(beggining, exit2, Arrays.asList(action1, action2, exit2)));
                }
                exit3 = roadMatrix[beggining.getColumn() + 2][beggining.getRow() - 2];
                if (isValidHouse(exit3)) {
                    beggining.addRoute(new MoveThroughCrossroad(beggining, exit3, Arrays.asList(action1, action2, action3, exit3)));
                }
                break;
            case 3:
                action1 = roadMatrix[beggining.getColumn()][beggining.getRow() + 1];
                action2 = roadMatrix[beggining.getColumn()][beggining.getRow() + 2];
                action3 = roadMatrix[beggining.getColumn() + 1][beggining.getRow() + 2];
                exit1 = roadMatrix[beggining.getColumn() - 1][beggining.getRow() + 1];
                if (isValidHouse(exit1)) {
                    beggining.addRoute(new MoveThroughCrossroad(beggining, exit1, Arrays.asList(action1, exit1)));
                }
                exit2 = roadMatrix[beggining.getColumn()][beggining.getRow() + 3];
                if (isValidHouse(exit2)) {
                    beggining.addRoute(new MoveThroughCrossroad(beggining, exit2, Arrays.asList(action1, action2, exit2)));
                }
                exit3 = roadMatrix[beggining.getColumn() + 2][beggining.getRow() + 2];
                if (isValidHouse(exit3)) {
                    beggining.addRoute(new MoveThroughCrossroad(beggining, exit3, Arrays.asList(action1, action2, action3, exit3)));
                }
                break;
            case 4:
                action1 = roadMatrix[beggining.getColumn() - 1][beggining.getRow()];
                action2 = roadMatrix[beggining.getColumn() - 2][beggining.getRow()];
                action3 = roadMatrix[beggining.getColumn() - 2][beggining.getRow() + 1];
                exit1 = roadMatrix[beggining.getColumn() - 1][beggining.getRow() - 1];
                if (isValidHouse(exit1)) {
                    beggining.addRoute(new MoveThroughCrossroad(beggining, exit1, Arrays.asList(action1, exit1)));
                }
                exit2 = roadMatrix[beggining.getColumn() - 3][beggining.getRow()];
                if (isValidHouse(exit2)) {
                    beggining.addRoute(new MoveThroughCrossroad(beggining, exit2, Arrays.asList(action1, action2, exit2)));
                }
                exit3 = roadMatrix[beggining.getColumn() - 2][beggining.getRow() + 2];
                if (isValidHouse(exit3)) {
                    beggining.addRoute(new MoveThroughCrossroad(beggining, exit3, Arrays.asList(action1, action2, action3, exit3)));
                }
                break;
        }
    }

    public boolean isValidHouse(Road casa) {
        return casa.getValue() != 0;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.udesc.ceavi.dsd.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

/**
 *
 * @author davib
 */
public class MainFrame extends JFrame {

    private static final Dimension sizePrefesss = new Dimension(700, 700);
    private JPanel jpOptions;
    private JPanel jpMesh;
    private JButton btnStop;
    private JButton btnStart;
    private JTextField jTFNumCars;
    private JLabel lbCarLimit;
    private JLabel lbCurrentlyNumberOfCars;

    private GridBagConstraints cons;

    public MainFrame() {
        initFrameProperty();
        initComponnnets();
    }

    private void initFrameProperty() {
        this.setSize(sizePrefesss);
        this.setMinimumSize(sizePrefesss);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Traffic Simulator");
        this.getContentPane().setLayout(new BorderLayout(1, 2));
    }

    private void initComponnnets() {
        Container contentPane = this.getContentPane();
        this.jpOptions = new JPanel();
        this.jpMesh = new JPanel();

        Dimension size = new Dimension(0, 70);
        setSizeI(jpOptions, size);
        this.jpOptions.setMaximumSize(size);

        initFrameComponents();

        contentPane.add(jpOptions, BorderLayout.NORTH);
        contentPane.add(jpMesh, BorderLayout.CENTER);

        jpOptions.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.jpMesh.setBackground(new Color(0, 0, 0));
    }

    private void setSizeI(JComponent c, Dimension d) {
        c.setSize(d);
        c.setMinimumSize(d);
        c.setPreferredSize(d);
    }

    private void initFrameComponents() {
        cons = new GridBagConstraints();

        JPanel jpOptionsII = new JPanel();
        jpOptionsII.setLayout(new GridBagLayout());
        cons.fill = GridBagConstraints.HORIZONTAL;
        this.jpOptions.add(jpOptionsII, cons);

        this.btnStop = new JButton("Stop");
        this.btnStart = new JButton("Start");
        this.jTFNumCars = new JTextField();
        jTFNumCars.setText("1");
        this.lbCarLimit = new JLabel("Max Car Number: ");

        Insets insets = new Insets(0, 10, 0, 10);

        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.insets = insets;
        jpOptionsII.add(this.lbCarLimit, cons);
        jpOptionsII.setBorder(BorderFactory.createLineBorder(new Color(102, 102, 0)));

        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.insets = insets;
        jpOptionsII.add(this.jTFNumCars, cons);

        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 0;
        cons.ipadx = 25;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.insets = insets;
        jpOptionsII.add(this.btnStart, cons);

        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 1;
        cons.ipadx = 25;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.insets = insets;
        this.btnStop.setEnabled(false);
        jpOptionsII.add(this.btnStop, cons);

        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 2;
        cons.gridwidth = 3;
        cons.fill = GridBagConstraints.NONE;
        cons.anchor = GridBagConstraints.CENTER;
        this.jpOptions.add(new JLabel("Currently Number of Cars: "), cons);

        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 3;
        cons.gridwidth = 3;
        cons.fill = GridBagConstraints.NONE;
        lbCurrentlyNumberOfCars = new JLabel("   ");
        this.jpOptions.add(lbCurrentlyNumberOfCars, cons);
    }
    
    public void updateNumberOfCars(int numberOfCars) {
        this.lbCurrentlyNumberOfCars.setText(String.valueOf(numberOfCars));
    }

}

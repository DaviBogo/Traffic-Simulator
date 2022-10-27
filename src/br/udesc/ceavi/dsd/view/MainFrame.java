package br.udesc.ceavi.dsd.view;

import br.udesc.ceavi.dsd.service.SystemService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 *
 * @author davib
 */
public class MainFrame extends JFrame implements MainFrameObserver {

    private static final Dimension sizePrefesss = new Dimension(800, 800);
    private JPanel jpOptions;
    private JPanel jpMesh;
    private JButton btnStop;
    private JButton btnStart;
    private JTextField jTFNumCars;
    private JLabel lbCarLimit;
    private JLabel lbCurrentlyNumberOfCars;

    private GridBagConstraints cons;
    private SystemService systemService;
    private MeshMatrix meshMatrix;

    public MainFrame() {
        systemService = SystemService.getInstance();
        initFrameProperty();
        initComponnnets();
        initListeners();
        systemService.addObserver(this);
    }

    private void initFrameProperty() {
        try {
            this.setSize(sizePrefesss);
            this.setMinimumSize(sizePrefesss);
            this.setLocationRelativeTo(null);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setTitle("Traffic Simulator");
            this.getContentPane().setLayout(new BorderLayout(1, 2));
            this.systemService.readFile("C:\\Users\\davib\\OneDrive\\Documentos\\Git\\Traffic-Simulator\\mesh\\malha-exemplo-3.txt");
            this.systemService.getMeshService().initMesh();
        } catch (Exception ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initComponnnets() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        Container contentPane = this.getContentPane();
        this.jpOptions = new JPanel();
        this.jpMesh = new JPanel();

        Dimension size = new Dimension(0, 70);
        setSizeI(jpOptions, size);
        this.jpOptions.setMaximumSize(size);

        initFrameComponents();

        contentPane.add(jpOptions, BorderLayout.NORTH);
        contentPane.add(jpMesh, BorderLayout.CENTER);
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
        initTableFrame();
    }

    public void updateNumberOfCars(int numberOfCars) {
        this.lbCurrentlyNumberOfCars.setText(String.valueOf(numberOfCars));
    }

    @Override
    public void notifyCarNumber(int carNumber) {
        lbCurrentlyNumberOfCars.setText("" + carNumber);
    }

    @Override
    public void notifySimulationOver() {
        btnStart.setEnabled(true);
        jTFNumCars.setEnabled(true);
    }

    public void initTableFrame() {
        meshMatrix = new MeshMatrix(jpMesh);
        JScrollPane pane = new JScrollPane();
        pane.setViewportView(meshMatrix);
        jpMesh.add(pane);
    }
    
    private void initListeners() {
        this.btnStart.addActionListener((e) -> btnStartListeners());
        this.btnStop.addActionListener((e) -> btnStopListeners());
    }
    
    private void btnStartListeners() {
        jTFNumCars.setEnabled(false);
        btnStart.setEnabled(false);
        btnStop.setEnabled(true);

        int numberOfCars = 1;
        try{
            numberOfCars = Integer.parseInt(jTFNumCars.getText());
        } catch (NumberFormatException e) {
            System.out.println("Simulação iniciará com apenas um carro");
        }
        systemService.startSimulation(numberOfCars);
    }
    
    private void btnStopListeners() {
        btnStop.setEnabled(false);
        systemService.stopRespawn();
    }

}

package br.udesc.ceavi.dsd.view;

import br.udesc.ceavi.dsd.service.MeshService;
import br.udesc.ceavi.dsd.service.SystemService;
import br.udesc.ceavi.dsd.util.Image;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author davib
 */
public class MeshMatrix extends JTable implements MatrixObserver {

    private MeshService meshService;
    private JPanel parentPanel;
    private BufferedImage[][] meshImages;
    private BufferedImage[][] canvas;

    public MeshMatrix(JPanel parent) {
        this.meshService = SystemService.getInstance().getMeshService();
        this.meshService.addObserver(this);
        this.parentPanel = parent;
        startBuffert();
    }

    public void startBuffert() {
        this.meshImages = new BufferedImage[meshService.getLastColumn()][meshService.getLastRow()];
        this.canvas = new BufferedImage[meshService.getLastColumn()][meshService.getLastRow()];
        this.initializeProperties();
        initImages();

        parentPanel.repaint();
        parentPanel.revalidate();
        this.repaint();
        this.revalidate();
    }

    private void initializeProperties() {
        this.setModel(new MeshTableModel());
        this.setDefaultRenderer(Object.class, new BoardTableRenderer());
        this.setBackground(new Color(0, 0, 0, 0));
        this.setRowSelectionAllowed(false);
        this.setCellSelectionEnabled(true);
        this.setDragEnabled(false);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setTableHeader(null);
        this.setFillsViewportHeight(true);
        this.setOpaque(false);
        this.setShowGrid(false);
        this.setEnabled(false);
    }

    private void initImages() {
        for (int colunm = 0; colunm < meshService.getLastColumn(); colunm++) {
            for (int row = 0; row < meshService.getLastRow(); row++) {
                meshImages[colunm][row] = Image.getImage((int) meshService.getRoadValue(colunm, row));
                canvas[colunm][row] = Image.getImage((int) meshService.getRoadValue(colunm, row));
            }
        }
        this.repaint();
        this.revalidate();
    }

    private class MeshTableModel extends AbstractTableModel {

        @Override
        public int getColumnCount() {
            return meshService.getLastColumn();
        }

        @Override
        public int getRowCount() {
            return meshService.getLastRow();
        }

        @Override
        public Object getValueAt(int row, int col) {
            return canvas[col][row];
        }
    }

    private class BoardTableRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value != null) {
                BufferedImage img = (BufferedImage) value;
                AffineTransform transform = AffineTransform.getScaleInstance((float) table.getColumnModel().getColumn(column).getWidth() / img.getWidth(),
                        (float) table.getRowHeight() / img.getHeight());
                AffineTransformOp operator = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
                this.setIcon(new ImageIcon(operator.filter(img, null)));
            }
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            return this;
        }
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        Dimension size = parentPanel.getSize();
        if (size.getWidth() <= 0 || size.getHeight() <= 0) {
            return new Dimension(0, 0);
        }
        int inset = 15;
        size.height -= inset;
        size.width -= inset;
        float scaleX = (float) size.getWidth();
        float scaleY = (float) size.getHeight();
        if (scaleX > scaleY) {
            int width = (int) (scaleY / scaleX * size.getWidth());
            size = new Dimension(width, (int) size.getHeight());
        } else {
            int height = (int) (scaleX / scaleY * size.getHeight());
            size = new Dimension((int) size.getWidth(), height);
        }
        this.setRowHeight((int) size.getHeight() / this.getModel().getRowCount());
        return size;
    }

    @Override
    public void printCar(int color, int column, int row) {
        BufferedImage origin = this.canvas[column][row];
        Graphics2D g = origin.createGraphics();
        g.drawImage(
                Image.replaceColor(
                        Image.getImage(Image.CAR),
                        color),
                0, 0, null);
        g.dispose();
    }

    @Override
    public void clearTableCell(int column, int row) {
        BufferedImage newImage = new BufferedImage(
                canvas[column][row].getWidth(),
                canvas[column][row].getHeight(),
                canvas[column][row].getType());

        Graphics2D g = newImage.createGraphics();
        g.drawImage(meshImages[column][row], 0, 0, null);
        this.canvas[column][row] = newImage;
        g.dispose();
        this.repaint();
        parentPanel.repaint();
    }

}

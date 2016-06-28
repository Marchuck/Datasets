package pl.datasets.widgets;

import javax.swing.*;
import java.awt.*;


/**
 * Datasets
 *
 * @author Lukasz Marczak
 * @since 28 cze 2016.
 * 16 : 01
 */
public class ResultCanvas extends JComponent {

    private int cellWidth = 3;
    private int cellHeight = 10;

    private java.util.List<Boolean> data;
    private Point position;
    private Color existanceColor = Color.YELLOW;
    private Color nonExistanceColor = Color.LIGHT_GRAY;
    private Color separatorColor = Color.BLACK;

    public ResultCanvas(Point position, java.util.List<Boolean> data) {
//        this.setMinimumSize(new Dimension(300, 40));
        this.setPreferredSize(new Dimension(300, 40));
        this.data = data;
        this.position = position;
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(int cellWidth) {
        this.cellWidth = cellWidth;
    }

    public Color getSeparatorColor() {
        return separatorColor;
    }

    public void setSeparatorColor(Color separatorColor) {
        this.separatorColor = separatorColor;
    }

    public Color getExistanceColor() {
        return existanceColor;
    }

    public void setExistanceColor(Color existanceColor) {
        this.existanceColor = existanceColor;
    }

    public Color getNonExistanceColor() {
        return nonExistanceColor;
    }

    public void setNonExistanceColor(Color nonExistanceColor) {
        this.nonExistanceColor = nonExistanceColor;
    }

    public ResultCanvas withCellSize(int cellSize) {
        setCellWidth(cellSize);
        return this;
    }

    public ResultCanvas withExistanceColor(Color existanceColor) {
        setExistanceColor(existanceColor);
        return this;
    }

    public ResultCanvas withNonExistanceColor(Color nonExistanceColor) {
        setNonExistanceColor(nonExistanceColor);
        return this;
    }

    @Override
    public void paint(Graphics g) {
        for (int j = 0; j < data.size(); j++) {
            boolean drawthisElement = data.get(j);
            if (drawthisElement) {
                g.setColor(existanceColor);
                g.fillRect(position.x + cellWidth * j, position.y, cellWidth, cellHeight);
            } else {
                g.setColor(nonExistanceColor);
                g.fillRect(position.x + cellWidth * j, position.y, cellWidth, cellHeight);
            }
            g.setColor(Color.BLACK);
            g.drawLine(position.x + cellWidth * j, position.y + 1, position.x + cellWidth * j, position.y + cellHeight - 1);

        }
    }
}

//public class FillRect {
//    public static void main(String[] a) {
//        JFrame window = new JFrame();
//        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        window.setBounds(30, 30, 300, 300);
//        window.getContentPane().add(new MyCanvas());
//        window.setVisible(true);
//    }
//
//}

package pl.datasets.widgets;

import javax.swing.*;
import java.awt.*;
import java.util.List;


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

    public ResultCanvas(Point position, java.util.List<Boolean> data, Color color) {
        super();
        this.existanceColor = color;
//        this.setMinimumSize(new Dimension(300, 40));
        this.setPreferredSize(new Dimension(300, 40));
        this.data = data;
        this.position = position;
    }

    public ResultCanvas(Point position, java.util.List<Boolean> data) {
        this(position, data, Color.YELLOW);
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
        repaint();
        invalidate();
        return this;
    }

    public ResultCanvas withNonExistanceColor(Color nonExistanceColor) {
        setNonExistanceColor(nonExistanceColor);
        return this;
    }

    @Override
    public void paint(Graphics g) {
        int index = 0;
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
            index++;
        }
    }

    private Color nextColor(int index) {
        return null;
    }

    public static class Builder {
        Point position;
        List<Boolean> data;
        Color color = Color.YELLOW;

        public Builder setPosition(Point position) {
            this.position = position;
            return this;
        }

        public Builder setData(List<Boolean> data) {
            this.data = data;
            return this;
        }

        public Builder setColor(Color color) {
            this.color = color;
            return this;
        }

        public ResultCanvas build() {
            if (data == null) throw new NullPointerException("CANNOT CREATE WITHOUT DATA");
            return new ResultCanvas(position, data, color);
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

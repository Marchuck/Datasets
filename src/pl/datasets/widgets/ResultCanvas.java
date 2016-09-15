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

    private static Color[] color;

    static {
        color = new Color[7];
        color[0] = Color.yellow;
        color[1] = Color.cyan;
        color[2] = Color.red;
        color[3] = Color.orange;
        color[4] = Color.magenta;
        color[5] = Color.green;
        color[6] = Color.blue;
    }

    private int cellWidth = 3;
    private int cellHeight = 10;
    private boolean manyModeEnabled;
    private java.util.List<Boolean> data;
    private java.util.List<Integer> manyData;
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

    public ResultCanvas(Point position, List<Integer> manyData, int width) {
        super();
        manyModeEnabled = true;
        this.position = position;
        this.manyData = manyData;
        this.setPreferredSize(new Dimension(width, 40));
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
        if (manyModeEnabled) {
            drawManyData(g, manyData);

        } else {
            drawData(g, data);
        }
    }

    private void drawManyData(Graphics g, List<Integer> data) {
        for (int j = 0; j < data.size(); j++) {
            int colorIndex = data.get(j);

            if (colorIndex != 0) {
                g.setColor(color[colorIndex]);
                g.fillRect(position.x + cellWidth * j, position.y, cellWidth, cellHeight);
            } else {
                g.setColor(nonExistanceColor);
                g.fillRect(position.x + cellWidth * j, position.y, cellWidth, cellHeight);
            }

            g.setColor(Color.BLACK);
            g.drawLine(position.x + cellWidth * j, position.y + 1, position.x + cellWidth * j, position.y + cellHeight - 1);
        }
    }

    private void drawData(Graphics g, List<Boolean> data) {
        for (int j = 0; j < data.size(); j++) {
            boolean shouldIdrawThisElement = data.get(j);

            if (shouldIdrawThisElement) {
                g.setColor(Color.YELLOW);
                g.fillRect(position.x + cellWidth * j, position.y, cellWidth, cellHeight);
            } else {
                g.setColor(nonExistanceColor);
                g.fillRect(position.x + cellWidth * j, position.y, cellWidth, cellHeight);
            }

            g.setColor(Color.BLACK);
            g.drawLine(position.x + cellWidth * j, position.y + 1, position.x + cellWidth * j, position.y + cellHeight - 1);
        }
    }


    public static class Builder {
        Point position;
        int preferredWidth = 350;
        List<Boolean> data;
        List<Integer> manyData;
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
            if (manyData != null) return new ResultCanvas(position, manyData, preferredWidth);
            else return new ResultCanvas(position, data, color);
        }

        public Builder setManyData(List<Integer> data) {
            this.manyData = data;
            return this;
        }

        public Builder setPreferredWidth(int i) {
            preferredWidth = i;
            return this;
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

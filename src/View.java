

import java.awt.Component;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


public class View {
    private double x = 0, y = 0, z = 1;
    private Point2D lastGrab;
  //  private Cell hoverCell;

    private final Component c;
    private final Rectangle2D bounds;
    private final int nRow, nCol;
    private final double cellWidth, cellHeight;

    public View(Component c, Rectangle2D bounds, int nRow, int nCol,
                double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.c = c;
        this.bounds = bounds;
        this.nCol = nCol;
        this.nRow = nRow;
        this.cellWidth = bounds.getWidth() / nCol;
        this.cellHeight = bounds.getHeight() / nRow;
    }

    public View(Component c, Rectangle2D bounds, int nRow, int nCol,
                double x, double y) {
        this(c, bounds, nRow, nCol, x, y, .25);
    }

    public View(Component c, Rectangle2D bounds, int nRow, int nCol) {
        this(c, bounds, nRow, nCol, bounds.getCenterX(), bounds.getCenterY());
    }

    public double getCellWidth() {
        return cellWidth;
    }

    public double getCellHeight() {
        return cellHeight;
    }

    public Rectangle2D getBounds() {
        return bounds;
    }

    public Component getComponent() {
        return c;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public Point2D getLastGrab() {
        return lastGrab;
    }

//    public Cell getHoverCell() {
//        return hoverCell;
//    }

    public void release() {
        lastGrab = null;
    }
    
    public void grab(double xx, double yy) {
        lastGrab = new Point2D.Double(xx, yy);
        hover(xx, yy);
    }

    public void moveDelta(double dx, double dy) {
        x += (dx) / z;
        y += (dy) / z;
        hover(x, y);    
    }

    public void moveTo(double xx, double yy) {
        x = xx;
        y = yy;
        hover(xx, yy);
    }

    public void zoomOut(double f, double xx, double yy) {
        z *= f;
        hover(xx, yy);
    }
    public void move(double dx, double dy) {
        x += (lastGrab.getX() - dx) / z;
        y += (lastGrab.getY() - dy) / z;
        hover(x, y);
    }

    public void hover(double xx, double yy) {
        double j = (xx / z + x - bounds.getMinX() - c.getWidth() / 2 / z)
                 / cellWidth;
        double i = (yy / z + y - bounds.getMinY() - c.getHeight() / 2 / z)
                 / cellHeight;
//        if (i >= 0 && i < nRow && j >= 0 && j < nCol)
//            hoverCell = new Cell((int)i, (int)j);
    }

}
package Utils;


import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import static java.lang.Math.*;

public class MyMath {

	public static final double EPS_LEN = 1e-8;
	public static final double EPS_AREA = EPS_LEN * EPS_LEN;
	public static final double EPS_COS = EPS_LEN;
	public static final double EPS_SIN = EPS_COS;
	public static final double DEG2RAD = Math.PI / 180;
	public static final double RAD2DEG = 180 / Math.PI;

	public static class Point2DComparator implements Comparator<Point2D> {
		private Point2D p;

		public Point2DComparator(Point2D p) {
			this.p = p;
		}

		@Override
		public int compare(Point2D a, Point2D b) {
			double d0 = a.distance(p);
			double d1 = b.distance(p);
			return abs(d0 - d1) < EPS_LEN ? 0
					: a.distance(p) < b.distance(p) ? -1 : 1;
		}
	};

	/** a + b **/
	public static Point2D add(Point2D a, Point2D b) {
		return new Point2D.Double(a.getX() + b.getX(), a.getY() + b.getY());
	}

	/** a - b **/
	public static Point2D subtract(Point2D a, Point2D b) {
		return new Point2D.Double(a.getX() - b.getX(), a.getY() - b.getY());
	}

	/** a * b **/
	public static Point2D multiply(Point2D a, double b) {
		return new Point2D.Double(a.getX() * b, a.getY() * b);
	}

	/** Angle from (1,0) to a in radians. **/
	public static double angle(Point2D a) {
		return Math.atan2(a.getY(), a.getX());
	}

	/** Angle from a to b in radians. **/
	public static double angle(Point2D a, Point2D b) {
		// TODO use dot product instead
		double ang = angle(b) - angle(a);
		if (ang <= Math.PI)
			ang += 2 * Math.PI;
		if (ang >= Math.PI)
			ang -= 2 * Math.PI;
		return ang;
	}

	/** a . b **/
	public static double dot(Point2D a, Point2D b) {
		return a.getX() * b.getX() + a.getY() * b.getY();
	}

	public static boolean equals(Point2D a, Point2D b) {
		return a.distance(b) <= EPS_LEN;
	}

	public static Point2D interpolate(Line2D l, double alpha) {
		return add(multiply(subtract(l.getP2(), l.getP1()), alpha), l.getP1());
	}

	public static Point2D interpolateAbs(Line2D l, double dist) {
		return interpolate(l, dist / l.getP1().distance(l.getP2()));
	}

	public static Point2D noise(Point2D p, double noise) {
		return add(p, new Point2D.Double(2 * noise * Math.random() - noise, 2
				* noise * Math.random() - noise));
	}

	public static void main(String[] args) {
		List<Point2D> l = new ArrayList<Point2D>();
		l.add(new Point2D.Double(0, -.1));
		l.add(new Point2D.Double(0, 0));
		l.add(new Point2D.Double(.1, 0));
		l.add(new Point2D.Double(.1, .1));
		Collections.sort(l, new Point2DComparator(new Point2D.Double(0, 0)));
		System.out.println(l);

		System.out.println(interpolate(new Line2D.Double(0, 0, 1, 2), .9));
		System.out.println(interpolateAbs(new Line2D.Double(1, 1, 1, 4), -.1));
		System.out.println(angle(new Point2D.Double(-1, 0), new Point2D.Double(
				1, -.1))
				* RAD2DEG);
	}

}

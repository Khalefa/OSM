import java.awt.geom.Point2D;

public class CoordinatesConverter {
    private static double RADIANS = 57.2957795;

    private static double DEG_TO_RADIANS(double x) {
        return (x / RADIANS);
    }

    private static double METERS_DEGLON(double x) {
        double d2r = DEG_TO_RADIANS(x);
        return ((111415.13 * Math.cos(d2r)) - (94.55 * Math.cos(3.0 * d2r)) + (0.12 * Math
                .cos(5.0 * d2r)));
    }

    private static double METERS_DEGLAT(double x) {
        double d2r = DEG_TO_RADIANS(x);
        return (111132.09 - (566.05 * Math.cos(2.0 * d2r))
                + (1.20 * Math.cos(4.0 * d2r)) - (0.002 * Math.cos(6.0 * d2r)));
    }

    public static Point2D translateCoordCyl(double slat, double slon,
                                            double olat, double olon) {
      double xx, yy, r, ct, st, angle;
      angle = DEG_TO_RADIANS(0);

      xx = (slon - olon) * METERS_DEGLON(olat);
      yy = (slat - olat) * METERS_DEGLAT(olat);

      r = Math.sqrt(xx * xx + yy * yy);

      /*
       * alert('LL_TO_XY: xx=' + xx + ' yy=' + yy + ' r=' + r); return false;
       */

      if (r > 0) {
          ct = xx / r;
          st = yy / r;
          xx = r * ((ct * Math.cos(angle)) + (st * Math.sin(angle)));
          yy = r * ((st * Math.cos(angle)) - (ct * Math.sin(angle)));
      }
      return new Point2D.Double(xx, yy);
    }

    public static Point2D translateCoordMerc(double slat, double slon) {
         return new Point2D.Double(m.mercX(slon), m.mercY(slat));
    }

    static Mercator m = new Mercator();

    static class Mercator {
        final private static double R_MAJOR = 6378137.0;
        final private static double R_MINOR = 6356752.3142;

        public double[] merc(double x, double y) {
            return new double[] { mercX(x), mercY(y) };
        }

        private double mercX(double lon) {
            return R_MAJOR * Math.toRadians(lon);
        }

        private double mercY(double lat) {
            if (lat > 89.5) {
                lat = 89.5;
            }
            if (lat < -89.5) {
                lat = -89.5;
            }
            double temp = R_MINOR / R_MAJOR;
            double es = 1.0 - (temp * temp);
            double eccent = Math.sqrt(es);
            double phi = Math.toRadians(lat);
            double sinphi = Math.sin(phi);
            double con = eccent * sinphi;
            double com = 0.5 * eccent;
            con = Math.pow(((1.0 - con) / (1.0 + con)), com);
            double ts = Math.tan(0.5 * ((Math.PI * 0.5) - phi)) / con;
            double y = 0 - R_MAJOR * Math.log(ts);
            return y;
        }
    }

    public static void main(String[] args) {
        double x0 = 0;
        double x1 = 0;
        double y0 = 0;
        double y1 = 1;

        Mercator m = new Mercator();

        Point2D a = translateCoordMerc(y0, x0);
        Point2D b = translateCoordMerc(y1, x1);

        Point2D c = new Point2D.Double(m.mercX(x0), m.mercY(y0));
        Point2D d = new Point2D.Double(m.mercX(x1), m.mercY(y1));
        System.out.println(a.distance(b) + " " + c.distance(d));

        Point2D x = translateCoordMerc(0, 0);
        Point2D y = translateCoordMerc(1, 0);
        Point2D z = translateCoordMerc(0, 1);
        // 110579.965220 111319.490793 mercator
        // 110567.238000 111320.700000 spherical
        System.out.printf("%f %f\n", x.distance(y), x.distance(z));
    }
}

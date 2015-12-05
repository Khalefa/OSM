package osm;

public class TPConstants {
    public static enum PROJ {
        CYLINDRICAL, MERCATOR
    };

    public static final String CELL_TAG_ID = "cell";
    public static final String ORIGINAL_EDGE_TAG_ID = "old-edge";
    public static final String ORIGINAL_NODE_TAG_ID = "old-node";
    public static final String IS_ORIGINAL_NODE_TAG_ID = "isOriginal";
    public static final String OSM_WAY_ID = "osm-way-id";
    public static final String AVG_TRAFFIC_SGN_WAIT = "average-ts-time";
    public static final float PRUNE_THRESHOLD = 0;
    public static final float CONG_THRESHOLD = 0.3333333333f;
    public static final int CONG_CALC_FUNC_DIV = 25;
    public static final double EPS = 1e-6;
    public static final double INF = 1e18;
    /** In meters. **/
    public static final double STREET_WIDTH = 2;
    /** In meters. **/
    public static final double CAR_LENGTH = 2;
    /** In meters. **/
    public static final double CAR_WIDTH = 1.5;
}

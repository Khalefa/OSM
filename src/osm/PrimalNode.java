package osm;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class PrimalNode implements INode {

    private static int max_id = 0;

    private final Point2D coord;
    private final List<IEdge> edges;
    private final int id;
    private long osmId = -1;
    private Map<String, Object> tags = null;// = new HashMap<String,
                                            // Object>();

    public PrimalNode(int id, Point2D coord) {
    	
        if (coord == null)
            throw new IllegalArgumentException();
        this.coord = coord;
        this.edges = new ArrayList<IEdge>();
        this.id = id;
    }

    public PrimalNode(Point2D coord) {
        this(max_id++, coord);
    }

    @Override
    public Point2D getCoordinates() {
        return coord;
    }

    @Override
    public void addEdge(IEdge e) {
        // if (edges.contains(e))
        // throw new IllegalArgumentException();
        edges.add(e);
    }

    @Override
    public List<IEdge> getEdges() {
        return edges;
    }

    @Override
    public IEdge getEdge(INode to) {
        if (to == null)
            throw new IllegalArgumentException();
        for (IEdge e : edges)
            if (e.getEnd().equals(to))
                return e;
        return null;
    }

//    @Override
//    public Map<String, Object> getTags() {
//        if (tags == null)
//            tags = new HashMap<>();
//        return tags;
//    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PrimalNode)
            return id == ((PrimalNode) obj).id;
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "n" + id;
    }

    @Override
    public void setOsmID(long id) {
        this.osmId = id;
    }

    @Override
    public long getOsmID() {
        return osmId;
    }

    @Override
    public int getID() {
        return id;
    }
}
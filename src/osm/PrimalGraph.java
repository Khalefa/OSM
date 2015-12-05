package osm;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class PrimalGraph implements IGraph {

    private final List<INode> nodes = new ArrayList<INode>();
    private final Map<Integer, INode> id2Node = new HashMap<Integer, INode>(7000000);
    private final List<IEdge> edges = new ArrayList<IEdge>();
    private Map<Long, INode> osmIdMap = new HashMap<Long, INode>(7000000);

    @Override
    public INode addNode(Point2D coord) {
        PrimalNode node = new PrimalNode(coord);
        nodes.add(node);
        id2Node.put(node.getID(), node);
        return node;
    }

    @Override 
    public INode addNode(int id, Point2D coord) {
        PrimalNode node = new PrimalNode(id, coord);
        nodes.add(node);
        id2Node.put(node.getID(), node);
        return node;
    }

    @Override
    public List<INode> getNodes() {
        return Collections.unmodifiableList(nodes);
    }

    @Override
    public INode getNode(int id) {
        return id2Node.get(id);
    }

    @Override
    public IEdge addEdge(int id, INode start, INode end, float maxSpeedMpS) {
        float lengthM
            = (float)start.getCoordinates().distance(end.getCoordinates());
        PrimalEdge edge = new PrimalEdge((PrimalNode) start, (PrimalNode) end,
                lengthM, maxSpeedMpS);
        edges.add(id, edge);
        start.addEdge(edge);
        return edge;
    }

    @Override
    public IEdge addEdge(INode start, INode end, float maxSpeedMpS) {
        float lengthM
            = (float)start.getCoordinates().distance(end.getCoordinates());
        PrimalEdge edge = new PrimalEdge((PrimalNode) start, (PrimalNode) end,
                lengthM, maxSpeedMpS);
        edges.add(edge);
        start.addEdge(edge);
        return edge;
    }

    @Override
    public List<IEdge> getEdges() {
        return Collections.unmodifiableList(edges);
    }

    @Override
    public Rectangle2D getBounds() {
        double l = Double.POSITIVE_INFINITY;
        double r = Double.NEGATIVE_INFINITY;
        double u = Double.POSITIVE_INFINITY;
        double d = Double.NEGATIVE_INFINITY;
        for (INode n : getNodes()) {
            if (n.getCoordinates().getX() < l)
                l = n.getCoordinates().getX();
            if (n.getCoordinates().getX() > r)
                r = n.getCoordinates().getX();
            if (n.getCoordinates().getY() < u)
                u = n.getCoordinates().getY();
            if (n.getCoordinates().getY() > d)
                d = n.getCoordinates().getY();
        }
        return new Rectangle2D.Double(l, u, r - l, d - u);
    }

    @Override
    public IGraph removeZeroDegreeNode() {
        PrimalGraph g = new PrimalGraph();
        Map<INode, INode> oldToNew = new HashMap<INode, INode>();
        for (INode n : getNodes())
            if (n.getEdges().size() > 0)
                oldToNew.put(n, g.addNode(n.getID(), n.getCoordinates()));
        for (IEdge e : getEdges())
            if (oldToNew.containsKey(e.getStart())
                    && oldToNew.containsKey(e.getEnd()))
                g.addEdge(e.getID(),
                          oldToNew.get(e.getStart()), oldToNew.get(e.getEnd()),
                          e.getMaxSpeed());
        return g;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (IEdge e : edges)
            b.append(e + "\n");
        return b.toString();
    }

    @Override
    public INode getNodeFromOsmID(long osmID) {
        return osmIdMap.get(osmID);
    }

    @Override
    public void registerNodeOsmID(long osmID, INode node) {
        node.setOsmID(osmID);
        osmIdMap.put(osmID, node);

    }
}

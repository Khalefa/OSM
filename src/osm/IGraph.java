package osm;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.List;

/** Maintains server state. **/
public interface IGraph extends Serializable {

    INode addNode(Point2D coord);
    INode addNode(int id, Point2D coord);

    List<INode> getNodes();
    INode getNode(int nextInt);

    IEdge addEdge(INode start, INode end, float maxSpeedMpS);
    IEdge addEdge(int id, INode start, INode end, float maxSpeedMpS);

    List<IEdge> getEdges();

    IGraph removeZeroDegreeNode();

    Rectangle2D getBounds();

    INode getNodeFromOsmID(long osmID);

    void registerNodeOsmID(long osmID, INode INode);


}

package osm;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer;
import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Relation;
import org.openstreetmap.osmosis.core.domain.v0_6.Tag;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;
import org.openstreetmap.osmosis.core.domain.v0_6.WayNode;
import org.openstreetmap.osmosis.core.task.v0_6.RunnableSource;
import org.openstreetmap.osmosis.core.task.v0_6.Sink;
import org.openstreetmap.osmosis.xml.common.CompressionMethod;
import org.openstreetmap.osmosis.xml.v0_6.XmlReader;



public class OsmGraphBuilder extends GraphBuilder {

    protected static final float DEFAULT_SPEED = 28;

    private boolean forceTwoWay = false;
    private TPConstants.PROJ projType = TPConstants.PROJ.MERCATOR;
    private Point2D o = new Point2D.Double(0, 0);

    public OsmGraphBuilder(IGraph graph) {
        super(graph);
    }

    public void setForceTwoWay(boolean twoWay) {
        this.forceTwoWay = twoWay;
    }

    public void setProjType(TPConstants.PROJ projType) {
        this.projType = projType;
    }

    public void setOrigin(Point2D o) {
        this.o = o;
    }

    private Point2D get2Dcoord(Node n) {
        if (projType == TPConstants.PROJ.MERCATOR)
            return CoordinatesConverter.translateCoordMerc
                (n.getLatitude(), n.getLongitude());
        if (projType == TPConstants.PROJ.CYLINDRICAL)
            return CoordinatesConverter.translateCoordCyl
                (n.getLatitude(), n.getLongitude(), o.getX(), o.getY());
        return null;
    }

//    private void printTags(Collection<Tag> tags) {
//        for (Tag tag : tags)
//            System.out.print(tag + " ");
//        System.out.println();
//    }

    @Override
    public IGraph build(File file) throws Exception {

        Sink sinkImplementation = new Sink() {

            public void process(EntityContainer entityContainer) {
                Entity entity = entityContainer.getEntity();
                // node
                if (entity instanceof Node) {
                    Node osmNode = (Node) entity;
                  //  get2Dcoord(osmNode);
                    INode graphNode = graph.addNode();
                    graphNode.setOsmID(osmNode.getId());
                    graph.registerNodeOsmID(osmNode.getId(), graphNode);

                }
                // way
                else if (entity instanceof Way) {
                    // roads only
                    Way osmWay = (Way) entity;
                    boolean isRoad = false;
                    for (Tag t : osmWay.getTags())
                        if (t.getKey().equals("highway")
                                || t.getKey().equals("junction"))
                            isRoad = true;
                    // road direction
                    boolean forward = true, backward = true;
                    if (!forceTwoWay)
                        for (Tag t : osmWay.getTags())
                            if (t.getKey().equals("oneway")) {
                                if (t.getValue().equals("yes"))
                                    backward = false;
                                else if (t.getValue().equals("-1"))
                                    forward = false;
                            }
                    // add way edges
                    if (isRoad) {
//                        System.out.print(forward + " " + backward + " ");
                        WayNode prev = osmWay.getWayNodes().get(0);
                        for (int i = 1; i < osmWay.getWayNodes().size(); i++) {
                            WayNode curr = osmWay.getWayNodes().get(i);
                            INode start = graph.getNodeFromOsmID(prev
                                    .getNodeId());
                            INode end = graph.getNodeFromOsmID(curr
                                    .getNodeId());
                            if (start == null || end == null)
                                continue;
                            if (forward)
                                graph.addEdge(start, end);
                            if (backward)
                                graph.addEdge(end, start);
                            prev = curr;
                        }
                    }
                }
                // relation
                else if (entity instanceof Relation) {
                    Relation r = (Relation) entity;
                }
            }

            public void release() {
            }

            public void complete() {
            }

            @Override
            public void initialize(Map<String, Object> arg0) {
            }
        };

        boolean pbf = false;
        CompressionMethod compression = CompressionMethod.None;

        if (file.getName().endsWith(".pbf")) {
            pbf = true;
        } else if (file.getName().endsWith(".gz")) {
            compression = CompressionMethod.GZip;
        } else if (file.getName().endsWith(".bz2")) {
            compression = CompressionMethod.BZip2;
        }

        RunnableSource reader;

        if (pbf) {
            reader = new crosby.binary.osmosis.OsmosisReader(
                    new FileInputStream(file));
        } else {
            reader = new XmlReader(file, false, compression);
        }

        reader.setSink(sinkImplementation);

        Thread readerThread = new Thread(reader);
        readerThread.start();

        while (readerThread.isAlive()) {
            try {
                readerThread.join();
            } catch (InterruptedException e) {
                /* do nothing */
            }
        }
        return graph;
    }

    @Override
    public IGraph build(InputStream in) throws Exception {
        throw new UnsupportedOperationException();
    }

}

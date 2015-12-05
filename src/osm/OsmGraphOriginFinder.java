package osm;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer;
import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.task.v0_6.RunnableSource;
import org.openstreetmap.osmosis.core.task.v0_6.Sink;
import org.openstreetmap.osmosis.xml.common.CompressionMethod;
import org.openstreetmap.osmosis.xml.v0_6.XmlReader;

public class OsmGraphOriginFinder {
    int nNode = 0;

	 public Point2D getOrigin(File file) throws Exception {

	        Point2D origin = new Point2D.Double(0, 0);
	         nNode = 0;

	        Sink sinkImplementation = new Sink() {
	            public void process(EntityContainer entityContainer) {
	                Entity entity = entityContainer.getEntity();
	                // node
	                if (entity instanceof Node) {
	                    Node osmNode = (Node)entity;
	                    origin.setLocation(origin.getX() + osmNode.getLatitude(),
	                                       origin.getY() + osmNode.getLongitude());
	                    nNode++;
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
	        return new Point2D.Double(origin.getX() / nNode,
	                                  origin.getY() / nNode);
	    }
}

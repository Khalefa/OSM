import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import osm.*;
import osm.TPConstants.PROJ;
import gpx.*;

public class GraphBuilder {

	static void Write(DualGraph g, String logFile) {
		BufferedWriter writer = null;
		try {
			// create a temporary file

			writer = new BufferedWriter(new FileWriter(logFile));
			for (IEdge e : g.getEdges()) {
				writer.write(e.getStart().getID() + " " + e.getEnd().getID() + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Close the writer regardless of what happens...
				writer.close();
			} catch (Exception e) {
			}

		}

	}

	public static IGraph BuildGraph(String osmMap) {
		try {
			OsmGraphBuilder bb = new OsmGraphBuilder(new PrimalGraph());

			bb.setForceTwoWay(true);
			bb.setProjType(PROJ.CYLINDRICAL);
			OsmGraphOriginFinder o = new OsmGraphOriginFinder();
			bb.setOrigin(o.getOrigin(new File(osmMap)));
			// build
			return bb.build(new File(osmMap));
			//System.out.println("done");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
static IGraph BuildDualGraph(IGraph g){

	DualGraph dg = new DualGraph(g);

//	Write(dg, "c:\\data\\graph.edge");
	return dg;
	
}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String osmMap = "c:/data/osm/alexandria.osm";
		
		BuildGraph(osmMap);
		// GPXParser.parse("c:\\data\\OSM\\a.gpx");
		
	}

}

import java.io.File;

import osm.*;
import osm.TPConstants.PROJ;

public class Build {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		String osmMap = "c:/data/osm/alexandria.osm";
		OsmGraphBuilder b = new OsmGraphBuilder(new PrimalGraph());

		b.setForceTwoWay(true);
		b.setProjType(PROJ.CYLINDRICAL);
		OsmGraphOriginFinder o = new OsmGraphOriginFinder();
		b.setOrigin(o.getOrigin(new File(osmMap)));
		// build
		IGraph g = b.build(new File(osmMap));
		for (INode n : g.getNodes()) {
			System.out.println(n);
		}
		System.out.println("done");

	}

}

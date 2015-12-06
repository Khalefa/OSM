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
//		IGraph g=new PrimalGraph();
//		INode a=g.addNode(0);
//		INode b=g.addNode(1);
//		INode c=g.addNode(2);
//		INode d=g.addNode(3);
//		g.addEdge(a, b);
//		g.addEdge(b, a);
//		g.addEdge(b,c);
//		g.addEdge(c, b);
//		g.addEdge(b,d);
		for (INode n : g.getNodes()) {
			System.out.println(n);
		}
		for (IEdge e : g.getEdges()) {
			System.out.println(e);
		}
		DualGraph dg= new DualGraph(g);
		
		for (INode n : dg.getNodes()) {
			System.out.println(n);
		}
		for (IEdge e : dg.getEdges()) {
			System.out.println(e);
		}
		System.out.println("done");
		

	}

}

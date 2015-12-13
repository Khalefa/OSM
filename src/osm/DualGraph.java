package osm;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import osm.*;

public class DualGraph extends PrimalGraph {
	public DualGraph(IGraph g) {
		for (int i = 0; i < g.getEdges().size(); i++) {
			IEdge e = g.getEdges().get(i);
			addNode(e.getDual());
		}
		System.out.println("aa");
		for (int i = 0; i < g.getEdges().size(); i++) {
			IEdge start = g.getEdges().get(i);
			for (int j = 0; j < g.getEdges().size(); j++) {
				if (j == i)
					continue;
				IEdge end = g.getEdges().get(j);
				if (start.getEnd().getID() == end.getStart().getID())
					addEdge(start.getDual(), end.getDual());
			}
		}
	}
}

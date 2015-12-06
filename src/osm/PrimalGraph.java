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
	public INode addNode(int id) {
		PrimalNode node = new PrimalNode(id);
		nodes.add(node);
		id2Node.put(node.getID(), node);
		return node;
	}
	@Override
	public INode addNode() {
		PrimalNode node = new PrimalNode();
		nodes.add(node);
		id2Node.put(node.getID(), node);
		return node;
	}
	@Override
	public INode addNode(INode node) {
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
	public IEdge addEdge(int id, INode start, INode end) {

		PrimalEdge edge = new PrimalEdge((PrimalNode) start, (PrimalNode) end);
		edges.add(id, edge);
		return edge;
	}

	@Override
	public IEdge addEdge(INode start, INode end) {

		PrimalEdge edge = new PrimalEdge((PrimalNode) start, (PrimalNode) end);
		edges.add(edge);
		// start.addEdge(edge);
		return edge;
	}

	@Override
	public List<IEdge> getEdges() {
		return Collections.unmodifiableList(edges);
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

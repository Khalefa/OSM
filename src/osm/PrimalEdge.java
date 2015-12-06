package osm;

import java.util.HashMap;
import java.util.Map;

import Utils.MyMath;

public class PrimalEdge implements IEdge {

	private static int max_id = 0;

	private final PrimalNode start, end;
	private int id;
	public PrimalNode dual=null;

	public PrimalEdge(int id, PrimalNode start, PrimalNode end) {
		if (start == null || end == null)
			throw new IllegalArgumentException();
		this.start = start;
		this.end = end;
		this.id = id;
	}

	public PrimalEdge(PrimalNode start, PrimalNode end) {
		this(max_id++, start, end);
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public INode getStart() {
		return start;
	}

	@Override
	public INode getEnd() {
		return end;
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PrimalEdge)
			return id == ((PrimalEdge) obj).id;
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "[e" + id + "," + start + "," + end + "]";
	}

	public INode getDual() {
		if(dual==null){
			dual= new PrimalNode(id);
			dual.dual=this;
		}
		return dual;
	}

}

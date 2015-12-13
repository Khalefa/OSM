package osm;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PrimalNode implements INode {

	private static int max_id = 0;
	private final int id;
	private long osmId = -1;
	public  PrimalEdge dual=null;
	private Point2D coord;

	public PrimalNode(int id) {
		this.id = id;
	}

	public PrimalNode() {
		this(max_id++); 
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PrimalNode)
			return id == ((PrimalNode) obj).id;
		return super.equals(obj);
	}

	@Override
	public String toString() {
		if(dual==null)
		return "n" + id;
		else 
			return "n" + id+" "+dual.toString();
	}

	@Override
	public void setOsmID(long id) {
		this.osmId = id;
	}

	@Override
	public long getOsmID() {
		return osmId;
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public IEdge getDual() {
		return dual;
	}

	@Override
	public Point2D getCoordinates() {
		return coord;
	}
	public void setCoordinates(Point2D cord){
		coord=cord;
	}
	}

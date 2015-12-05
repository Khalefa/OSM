import java.util.HashMap;
import java.util.Map;

import Utils.MyMath;

public class PrimalEdge implements IEdge {

	private static int max_id = 0;

	private final PrimalNode start, end;
	private final float lengthM, maxSpeedMpS;
	private final int id;
	
	private Map<String, Object> tags = null;// = new HashMap<String, Object>();

	public PrimalEdge(int id, PrimalNode start, PrimalNode end, float lengthM,
			float maxSpeedMpS) {
		if (start == null || end == null)
			throw new IllegalArgumentException();
		this.start = start;
		this.end = end;
		this.lengthM = lengthM;
		this.maxSpeedMpS = maxSpeedMpS;
		this.id = id;
	}

	public PrimalEdge(PrimalNode start, PrimalNode end, float lengthM,
	                  float maxSpeedMpS) {
       this(max_id++, start, end, lengthM, maxSpeedMpS);
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
	public IEdge getReverse() {
		for (int e = 0; e < end.getEdges().size(); e++)
			if (end.getEdges().get(e).getEnd().equals(start))
				return end.getEdges().get(e);
		return null;
	}

	@Override
	public float getLength() {
		return lengthM;
	}

	@Override
	public float getMaxSpeed() {
		return maxSpeedMpS;
	}

	/**
	 * this version is for greedy prediction on server, cares about cell state
	 * at time t
	 * 
	 * @param t
	 *            time
	 * @return
	 */
	@Override
	public float getAverageSpeed(long t) {
		// TODO make this depend on the state of MY cell
		return maxSpeedMpS;
	}

	/***
	 * this version is for index construction, does not care about cell state
	 */
	@Override
	public float getAverageSpeed() {
		// TODO improve this
		return maxSpeedMpS;
	}

	@Override
	public double angleTo(IEdge e) {
		return MyMath.angle(MyMath.subtract(end.getCoordinates(),
				start.getCoordinates()), MyMath.subtract(e.getEnd()
				.getCoordinates(), e.getStart().getCoordinates()));
	}

//	@Override
//	public Map<String, Object> getTags() {
//		if (tags == null)
//			tags = new HashMap<>();
//		return tags;
//	}

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

	
}

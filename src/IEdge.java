
import java.io.Serializable;

public interface IEdge extends Serializable {
    int getID();

    INode getStart();

	INode getEnd();

	IEdge getReverse();

	/** In meter. **/
	float getLength();

	/** In meter per sec. **/
	float getMaxSpeed();

	/** In meter per sec. **/
	float getAverageSpeed();

	/** In meter per sec. **/
	float getAverageSpeed(long t);

	/** Angle from this to e in radian. **/
	double angleTo(IEdge e);

}
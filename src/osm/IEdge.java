package osm;

import java.io.Serializable;

public interface IEdge extends Serializable {
    int getID();

    INode getStart();
	INode getEnd();

    INode getDual();
}
package osm;


import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.List;

public interface INode extends Serializable {
    /** In meters. **/
  
    void setOsmID(long id);
    long getOsmID();
    int getID();
    IEdge getDual();
}

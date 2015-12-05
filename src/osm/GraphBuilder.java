package osm;

import java.io.File;
import java.io.InputStream;



public abstract class GraphBuilder {

    protected final IGraph graph;

    public GraphBuilder(IGraph graph) {
        this.graph = graph;
    }

    public abstract IGraph build(InputStream in) throws Exception;
    public abstract IGraph build(File in) throws Exception;
}

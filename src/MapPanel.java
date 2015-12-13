
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;

import osm.*;

public class MapPanel extends JPanel {

	private static final long serialVersionUID = -6197906999180489954L;

	private final IGraph graph;
	private int gridCols = 1, gridRows = 1;
	private double cellWidth, cellHeight;
	private final Rectangle2D bounds;

	// public final Map<Integer, DemoEntity> demoEntities;
	public Set<IEdge> highlightedEdges = new HashSet<IEdge>();

	private View view;

	public MapPanel(String fileName, final int gridCols, final int gridRows) {
		try {
			// String osmMap = "data/osm/Beijing.osm.pbf";
			//
			// Point2D origin = new OsmGraphOriginFinder().getOrigin
			// (new File(osmMap));
			// System.out.println(origin);
			// OsmGraphBuilder b = new OsmGraphBuilder(new PrimalGraph(),
			// origin.getX(), origin.getY());
			// this.graph = b.build(new File(osmMap)).removeZeroDegreeNode();

			// b = new MapGraphBuilder(new PrimalGraph());
			this.graph = GraphBuilder.BuildGraph(fileName);
			this.bounds = this.graph.getBounds();
			System.out.println(bounds);
			this.gridCols = gridCols;
			this.gridRows = gridRows;
			this.cellWidth = bounds.getWidth() / gridCols;
			this.cellHeight = bounds.getHeight() / gridRows;
			// this.demoEntities = new HashMap<Integer, DemoEntity>();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("faksan");
		}
		// MOUSE
		view = new View(this, bounds, gridRows, gridCols);
		// this.sim = new Simulation(view, bounds);
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				int l=1000;
				char ch = e.getKeyChar();
				switch (ch) {
				case '1':
					view.moveDelta(l,l);
			
				//	System.out.println("Key pressed code=" + e.getKeyCode() + ", char=" + e.getKeyChar());
					
					break;
				case '2':
					view.moveDelta(l, -l);
					break;
				case '3':
					view.moveDelta(-l, l);
					break;
				case '4':
					view.moveDelta(-l, -l);
					break;
				case'z':
					view.setZ(view.getZ()*2);
					break;
				case'Z':
					view.setZ(view.getZ()/2);
					break;
				}
				view.getComponent().repaint();
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				view.release();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				view.grab(e.getX(), e.getY());
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				view.move(e.getX(), e.getY());
				view.grab(e.getX(), e.getY());
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				view.hover(e.getX(), e.getY());
				view.getComponent().repaint();
			}
		});
		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				double f = Math.exp(e.getPreciseWheelRotation() / 10);
				view.zoomOut(f, e.getX(), e.getY());
			}
		});
		setFocusable(true);
		requestFocus();
	}

	public void viewGoto(Point2D loc) {
		if (loc != null) {
			view.moveTo(loc.getX(), loc.getY());
		}
	}

	private final Stroke srk1 = new BasicStroke(2);
	private final Stroke srk2 = new BasicStroke(1);

	@Override
	public void paint(Graphics g) {
		// view coordination
		System.out.println("painting");
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(Color.WHITE);
		g2d.clearRect(0, 0, getWidth(), getHeight());
		// sim.drawUI(g2d);
		g2d.translate(+getWidth() / 2, +getHeight() / 2);
		g2d.scale(view.getZ() / 10, view.getZ() / 10);
		g2d.translate(-getWidth() / 2, -getHeight() / 2);
		g2d.translate(+getWidth() / 2, +getHeight() / 2);
		g2d.translate(-(int) view.getX(), -(int) view.getY());
		// road edges
		g2d.setStroke(srk1);
		for (IEdge e : graph.getEdges()) {
			if (highlightedEdges.contains(e))
				g2d.setColor(Color.RED);
			else
				g2d.setColor(Color.BLACK);
			g2d.drawLine((int) e.getStart().getCoordinates().getX(), (int) e.getStart().getCoordinates().getY(),
					(int) e.getEnd().getCoordinates().getX(), (int) e.getEnd().getCoordinates().getY());
		}
		/*// road nodes
		g2d.setStroke(srk2);
		g2d.setColor(new Color(1.f, 0.f, 0.f, .4f));
		for (INode n : graph.getNodes()) {
			g2d.drawArc((int) (n.getCoordinates().getX() - 4), (int) (n.getCoordinates().getY() - 4), 8, 8, 0, 360);
		}
		*/

	}

	public IGraph getGraph() {
		return graph;
	}

	public View getView() {
		return view;
	}

	public String getInfo() {
		return view.getX() + "," + view.getY();
	}

}
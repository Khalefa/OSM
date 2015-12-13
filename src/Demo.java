import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;



public class Demo extends JFrame {

	private static final long serialVersionUID = -5890097586919373671L;
    private final MapPanel mapPanel;
 //   private final JTextArea infoLabel;
 //	private int numUsers = 0;
   // private final JButton addButton, trajButton;

	public Demo(String map, int gridCols, int gridRows) {
	    try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

	    getContentPane().setPreferredSize(new Dimension(1024, 768));
	    getContentPane().setLayout(new BorderLayout());
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    pack();

	    // CONTENT
	    //       top
	    // ------------------
	    // left | map | right
	    // ------------------
	    //      bottom

	    // MAP PANEL
	    this.mapPanel = new MapPanel(map, gridCols, gridRows);
		getContentPane().add(mapPanel, BorderLayout.CENTER);
		

		// LEFT PANEL

		// [+][-][*]
		//  _______ 
		// |user1  |
		// |user2  |
		// |_______|
		//  . . . . 
		// : info  :
		//  . . . . 
	//	final JPanel leftPanel = new JPanel();
	//	leftPanel.setPreferredSize(new Dimension(256, 600));
	//	leftPanel.setLayout(new BorderLayout());

	//	JPanel buttonsPanel = new JPanel();
	//	buttonsPanel.setLayout(new BorderLayout());
     
        
      //  leftPanel.add(buttonsPanel, BorderLayout.PAGE_START);
        
 
      //  leftPanel.add(infoLabel = new JTextArea(""), BorderLayout.PAGE_END);
    //    infoLabel.setEditable(false);
     //   infoLabel.setPreferredSize(new Dimension(256, 256));

      //  getContentPane().add(leftPanel, BorderLayout.LINE_START);

        // CONSOLE
 	}

   
	
    public void infoRefresh() {
        String info = mapPanel.getInfo() + "\r\n";
     
       // infoLabel.setText(info);
    }

    public MapPanel getMapPanel() {
        return mapPanel;
    }

	public static void main(String[] args) throws Exception {
		Demo d = new Demo("c:/data/osm/alexandria.osm", 30, 30);
		d.setVisible(true);
	}

}

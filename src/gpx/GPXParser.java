package gpx;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Vector;

import javax.lang.model.element.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GPXParser {
static String file="";
	public static void main(String[] args) throws Exception {
		for (String filename : args) {
			 file=filename.substring(filename.lastIndexOf("/")+1).replace(".gpx", "");
			 System.out.println(file);
			SAXParserFactory parserFactor = SAXParserFactory.newInstance();
			SAXParser parser = parserFactor.newSAXParser();
			SAXHandler handler = new SAXHandler();
			parser.parse(new File(filename), handler);

			// Printing the list of employees obtained from XML
			for (trk t : handler.trks) {
				System.out.println(t);
			}
			System.out.println("");
		}
	}
}

/**
 * The Handler for SAX Events.
 */
class SAXHandler extends DefaultHandler {

	List<trk> trks = new ArrayList<>();
	trkpt pt = null;
	trk t = null;
	String content = null;

	@Override
	// Triggered when the start of tag is found.
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {

		switch (qName) {
		case "trk":
			t = new trk();
			break;
		case "trkseg":
			pt = new trkpt();
			break;
		case "trkpt":
			int len = atts.getLength();
			// Loop through all attributes and save them as needed
			for (int i = 0; i < len; i++) {
				String sAttrName = atts.getQName(i);

				if (sAttrName.compareTo("lat") == 0) {
					pt.lat = atts.getValue(i);
				}
				if (sAttrName.compareTo("lon") == 0) {
					pt.lng = atts.getValue(i);

				}
			}
			break;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		switch (qName) {
		case "trk":
			trks.add(t);
			break;
		case "trkpt":
			t.trkpts.add(pt);
			break;
		case "ele":
			pt.ele = content;
			break;
		case "time":
			pt.time = content;
			break;
		case "number":
			t.number = content;
			break;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		content = String.copyValueOf(ch, start, length).trim();
	}

}

class trk {
	String number;
	List<trkpt> trkpts = new Vector<>();

	@Override
	public String toString() {
		String s = "";
		for (trkpt p : trkpts) {
			s = s + GPXParser.file+","+number + "," + p.toString() + "\n";
		}
		return s;
	}
}

class trkpt {

	String lat;
	String lng;
	String ele;
	String time;

	Date getDate() {
	
		Date date = new Date();
		try {
			date = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")).parse(time.replaceAll("Z$", "+0000"));
		} catch (Exception e) {

		}
		return date;

	}

	@Override
	public String toString() {
		return lat + "," + lng + "," + ele + "," + getDate().getTime();
	}
}
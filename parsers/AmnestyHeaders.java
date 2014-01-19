package TestPDF;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.pdfbox.exceptions.InvalidPasswordException;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;

// PART TWO: COUNTRY ENTRIES
// Amnesty International visits/reports


public class AmnestyHeaders {
	
	List<String> labels = new ArrayList<String>();
	List<String> tLabels = new ArrayList<String>();
	
	public static void main(String[] args) throws Exception {
		int year = 2012;
		AmnestyHeaders test = new AmnestyHeaders();
		test.parsePDF(year);
		test.deDup(year);
		test.process(year);
	}

	public void process(int year) throws Exception {
		BufferedReader reader = new BufferedReader( new InputStreamReader( new FileInputStream("c:/users/karl/downloads/" + year + " extract.txt"), Charset.forName("UTF-8") ) );
		String line;
		boolean start = false;
		boolean tFound = false;
		BufferedWriter writer = null;
		String lastFound = null;
		boolean saveParagraph = false;
		while ( (line=reader.readLine()) != null ) {
			if ( line.toLowerCase().contains("part two: country entries")) {
				start = true;
				System.out.println("start");
			}
			if ( !start ) continue;
			
			for ( String country: countries ) {
				if ( line.equals(country.toUpperCase()) ) {
					// duplicate country name?
					if ( country.equals(lastFound)) break;
					// no, make a new writer.
					if ( writer != null ) {
						writer.close();
						if( !tFound ) {
							File file = new File("c:/users/karl/downloads/" + lastFound+".txt");
							file.delete();
						}
					}
					lastFound = country;
					writer = new BufferedWriter( new OutputStreamWriter(new FileOutputStream("c:/users/karl/downloads/" + year + " " + country+".txt")));
					tFound = false;
					saveParagraph = false;
				}
			}
			
			if ( writer == null ) continue;
	
			for ( String label: tLabels ) {
				if ( line.toLowerCase().equals(label) ) {
					saveParagraph = true;
					tFound = true;
					break;
				}
			}
			for ( String label: labels ) {
				if ( line.toLowerCase().equals(label) ) {
					saveParagraph = false;
					break;
				}
			}
			if ( saveParagraph ) {
				if ( !line.contains("Amnesty International Report") && line.length() > 1) {
					writer.write(line);
					writer.newLine();
					}
			}
			
		}
		if ( writer != null ) {
			writer.close();
		}
		reader.close();
	}
	
	public void deDup(int year) throws Exception {
		Set<String> set = new TreeSet<String>();
		BufferedReader reader = new BufferedReader( new InputStreamReader( new FileInputStream("c:/users/karl/downloads/" + year + " headers.txt"), Charset.forName("UTF-8") ) );
		addLines(reader, set);
		reader.close();

		for(String s: set ) {
			if ( s.startsWith("torture")) tLabels.add(s);
			else labels.add(s);
		}
	}
	
	private void addLines(BufferedReader reader, Set<String> set) throws Exception {
		String line;
		while ( (line=reader.readLine()) != null ) {
			line = line.replace("|", "").toLowerCase();
			boolean f = false;
			for ( String country: countries ) {
				if ( line.equals(country.toLowerCase()) ) {
					f = true;
					break;
				}
			}
			if ( !set.contains(line) && !f) set.add(line);
		}
		
	}

	public void parsePDF(int year) throws Exception {

//        PDFParser parser = new PDFParser( TestPDF.class.getResourceAsStream("/TestPDF.pdf") );
		PDFParser parser = new PDFParser( new FileInputStream("c:/users/karl/downloads/" + year + ".pdf") );
        parser.parse();
        PDDocument document = parser.getPDDocument();
        if( document.isEncrypted() )
        {
            try
            {
                document.decrypt( "" );
            }
            catch( InvalidPasswordException e )
            {
                System.err.println( "Error: Document is encrypted with a password." );
                System.exit( 1 );
            }
        }

//        Writer output = new OutputStreamWriter( System.out );
        Writer output = new OutputStreamWriter (new FileOutputStream( "c:/users/karl/downloads/" + year + " extract.txt" ) );
        output2 = new OutputStreamWriter (new FileOutputStream( "c:/users/karl/downloads/" + year + " headers.txt" ) );
        
        MyStripper stripper = new MyStripper();
        stripper.writeText(document, output);
        
        output.close();
        output2.close();
        document.close();
	}
	
	public static class MyStripper extends  PDFTextStripper {
		public MyStripper() throws IOException { super(); }
		
		@Override
	    protected void writeWordSeparator() throws IOException
	    {
	        output.write(getWordSeparator());
	        if ( startLine ) output2.write(" ");
	    }

		@Override
	    protected void writeLineSeparator( ) throws IOException
	    {
			maybeNewLine = true;
//	        output.write(getLineSeparator());
	        if ( startLine ) {
	        	maybeNewLine2 = true;
//	        	output2.write("\r\n");
	        	startLine = false;
	        }
	    }

		@Override
	    protected void writeString(String text, List<TextPosition> textPositions) throws IOException
	    {
			if ( maybeNewLine && Character.isUpperCase(text.charAt(0)) ) {
		        output.write(getLineSeparator());
				maybeNewLine = false;
			} else if ( maybeNewLine && Character.isLowerCase(text.charAt(0)) ) {
		        output.write(getWordSeparator());
				maybeNewLine = false;
			}
	        TextPosition textPosition = textPositions.get(0);
	        float h = textPosition.getHeight();
	        if ( text.length() != 1 || h != 6.0 ) writeString(text);
	        if ( h > 6 ) {
				if ( maybeNewLine2 && Character.isUpperCase(text.charAt(0)) ) {
		        	output2.write("\r\n");
					maybeNewLine2 = false;
				} else if ( maybeNewLine2 && Character.isLowerCase(text.charAt(0)) ) {
			        output2.write(getWordSeparator());
					maybeNewLine2 = false;
				}
	        	output2.write(text);
	        	startLine = true;
//		        if ( text.length() == 1 ) output2.write("--"+h+"--");
	        }
	    }
		
		
	}

	static boolean startLine;
	static boolean maybeNewLine;
	static boolean maybeNewLine2;
    static Writer output2;
    
	static String[] countries = {
		"Afghanistan",
		"Albania",
		"Algeria",
		"Angola",
		"Argentina",
		"Armenia",
		"Australia",
		"Austria",
		"Azerbaijan",
		"Bahamas",
		"Bahrain",
		"Bangladesh",
		"Belarus",
		"Belgium",
		"Benin",
		"Bolivia",
		"Bosnia and Herzegovina",
		"Brazil",
		"Bulgaria",
		"Burkina Faso",
		"Burundi",
		"Cambodia",
		"Cameroon",
		"Canada",
		"Central African Republic",
		"Chad",
		"Chile",
		"China",
		"Colombia",
		"Congo (Republic of)",
		"Côte d’Ivoire",
		"Croatia",
		"Cuba",
		"Cyprus",
		"Czech Republic",
		"Democratic Republic of the Congo",
		"Denmark",
		"Dominican Republic",
		"Ecuador",
		"Egypt",
		"El Salvador",
		"Equatorial Guinea",
		"Eritrea",
		"Ethiopia",
		"Fiji",
		"Finland",
		"France",
		"Gambia",
		"Georgia",
		"Germany",
		"Ghana",
		"Greece",
		"Guatemala",
		"Guinea",
		"Guinea-Bissau",
		"Guyana",
		"Haiti",
		"Honduras",
		"Hungary",
		"India",
		"Indonesia",
		"Iran",
		"Iraq",
		"Ireland",
		"Israel and the Occupied Palestinian Territories",
		"Italy",
		"Jamaica",
		"Japan",
		"Jordan",
		"Kazakhstan",
		"Kenya",
		"Korea (Democratic People’s Republic of)",
		"Korea (Republic of)",
		"Kuwait",
		"Kyrgyzstan",
		"Laos",
		"Lebanon",
		"Liberia",
		"Libya",
		"Lithuania",
		"Macedonia",
		"Madagascar",
		"Malawi",
		"Malaysia",
		"Maldives",
		"Mali",
		"Malta",
		"Mauritania",
		"Mexico",
		"Moldova",
		"Mongolia",
		"Montenegro",
		"Morocco",
		"Western Sahara",
		"Mozambique",
		"Myanmar",
		"Namibia",
		"Nepal",
		"Netherlands",
		"New Zealand",
		"Nicaragua",
		"Niger",
		"Nigeria",
		"Norway",
		"Oman",
		"Pakistan",
		"Palestinian Authority",
		"Panama",
		"Paraguay",
		"Peru",
		"Philippines",
		"Poland",
		"Portugal",
		"Puerto Rico",
		"Qatar",
		"Romania",
		"Russian Federation",
		"Rwanda",
		"Saudi Arabia",
		"Senegal",
		"Serbia",
		"Sierra Leone",
		"Singapore",
		"Slovakia",
		"Slovenia",
		"Somalia",
		"South Africa",
		"South Sudan",
		"Spain",
		"Sri Lanka",
		"Sudan",
		"Swaziland",
		"Sweden",
		"Switzerland",
		"Syria",
		"Taiwan",
		"Tajikistan",
		"Tanzania",
		"Thailand",
		"Timor-Leste",
		"Togo",
		"Trinidad and Tobago",
		"Tunisia",
		"Turkey",
		"Turkmenistan",
		"Uganda",
		"Ukraine",
		"United Arab Emirates",
		"United Kingdom",
		"United States of America",
		"Uruguay",
		"Uzbekistan",
		"Venezuela",
		"Viet Nam",
		"Yemen",
		"Zimbabwe"
	};
	
}
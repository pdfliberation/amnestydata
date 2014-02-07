package ProcessAmnesty;

import java.io.*;
import java.util.*;

import org.apache.pdfbox.exceptions.InvalidPasswordException;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;

import com.example.model.Alert;
import com.example.model.Alerts;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ProcessAmnesty {
	
	private static final String workingDirectory = "c:/users/karl/downloads/";

	List<String> labels = new ArrayList<String>();
	List<String> tLabels = new ArrayList<String>();
	List<String> countries = new ArrayList<String>();
	List<Alert> alerts = new ArrayList<Alert>();
	
	public static void main(String[] args) throws Exception {
		int year = 2008;
		ProcessAmnesty test = new ProcessAmnesty();
		test.parsePDF(year);
		test.readCountries(year);
		test.dedupLabels(year);
		test.process(year);
	}

	public void process(int year) throws Exception {
		StringBuffer writer = new StringBuffer();
		BufferedReader reader = new BufferedReader( new InputStreamReader( new FileInputStream(workingDirectory + year + " extract.txt"), "UTF-8" ) );
		Alert alert = null; 
		String line;
		boolean start = false;
		String lastFound = null;
		boolean saveParagraph = false;
		while ( (line=reader.readLine()) != null ) {
			line = line.replace("\u0003", "").replace("\u0002", "").replace("\u0001", "").replace("\u0004", "");
			if ( line.toLowerCase().contains("part two: country entries")) {
				start = true;
				System.out.println("start");
			}
			if ( !start ) continue;
			if ( line.toLowerCase().contains("part three: selected human rights treaties") ) {
				break;
			}
			
			for ( String country: countries ) {
				if ( line.equals(country.toUpperCase()) ) {
					// duplicate country name?
					if ( country.equals(lastFound)) break;
					// no, make a new writer.
					lastFound = country;
					// start new country section
					writer.append("========== " + country.toUpperCase() + " ==========\r\n");
					if ( alert != null ) alerts.add(alert);
					alert = new Alert(country.toUpperCase(), Integer.toString(year), "" ); 
					saveParagraph = false;
				}
			}
			
			if ( writer == null ) continue;
	
			for ( String label: tLabels ) {
				if ( line.toLowerCase().equals(label) ) {
					saveParagraph = true;
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
					writer.append(line+"\r\n");
					alert.setDescription( alert.getDescription() + line );
					}
			}
			
		}
		reader.close();
		BufferedWriter bw = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(workingDirectory + year + " torture extract.txt"), "UTF-8"));
		bw.write(writer.toString());
		bw.close();

		bw = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(workingDirectory + year + " torture extract.json"), "UTF-8"));
		ObjectMapper mapper =  new ObjectMapper();
		
		List<Alert> tAlerts = new ArrayList<Alert>();
		for ( Alert tAlert: alerts) {
			if ( !tAlert.getDescription().isEmpty() ) {
				tAlert.setDescription( tAlert.getDescription().replace("Torture and other ill-treatment", "") );
				tAlerts.add(tAlert);
			}
		}
			
		mapper.writerWithDefaultPrettyPrinter().writeValue(bw, new Alerts(tAlerts));
		bw.close();
		
	}
	
	public void readCountries(int year) throws Exception {
		BufferedReader reader = new BufferedReader( new InputStreamReader( new FileInputStream(workingDirectory + year + " headers.txt"), "UTF-8" ) );
		boolean start = false;
		String line;
		while ( (line=reader.readLine()) != null ) {
			if ( line.toLowerCase().contains("part two: country entries")) {
				start = true;
				System.out.println("start");
			}
			if ( !start ) continue;
			if ( line.toLowerCase().contains("amnesty international report") ) continue;
			if ( line.toLowerCase().contains("part three: selected human rights treaties") ) {
				break;
			}
			boolean country = true;
			for ( int i=0, j=line.length(); i<j; ++i ) {
				if ( Character.isLowerCase(line.charAt(i))) {
					country = false;
					break;
				}
			}
			if ( country) countries.add(line);
		}
		reader.close();

	}
	
	public void dedupLabels(int year) throws Exception {
		Set<String> set = new TreeSet<String>();
		BufferedReader reader = new BufferedReader( new InputStreamReader( new FileInputStream(workingDirectory + year + " headers.txt"), "UTF-8" ) );
		boolean start = false;

		String line;
		while ( (line=reader.readLine()) != null ) {
			if ( line.toLowerCase().contains("part two: country entries")) {
				start = true;
				System.out.println("start");
			}
			if ( !start ) continue;
			line = line.replace("|", "").toLowerCase();
			boolean f = false;
			for ( String country: countries ) {
				if ( line.equals(country.toLowerCase()) ) {
					f = true;
					break;
				}
			}
			if ( line.contains("amnesty international report") ) continue;
			if ( line.contains("part three: selected human rights treaties") ) {
				break;
			}
			if ( line.trim().length() <= 1) continue;
			if ( !set.contains(line) && !f) set.add(line);
		}

		reader.close();

		for(String s: set ) {
			if ( s.startsWith("torture")) tLabels.add(s);
			else {
				labels.add(s);
			}
		}
	}
	
	public void parsePDF(int year) throws Exception {

//        PDFParser parser = new PDFParser( TestPDF.class.getResourceAsStream("/TestPDF.pdf") );
		PDFParser parser = new PDFParser( new FileInputStream(workingDirectory + year + ".pdf") );
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

        MyStripper stripper = new MyStripper();
        
        Writer output = new OutputStreamWriter (new FileOutputStream( workingDirectory + year + " extract.txt" ), "UTF-8" );
        stripper.headerOutput = new OutputStreamWriter (new FileOutputStream( workingDirectory + year + " headers.txt" ), "UTF-8" );

        stripper.writeText(document, output);
        
        output.close();
        stripper.headerOutput.close();
        document.close();
	}
	

    public class MyStripper extends  PDFTextStripper {
        public Writer headerOutput;
    	boolean startLine;
    	boolean maybeNewLine;
    	boolean maybeNewLine2;
    	
		public MyStripper() throws IOException { super(); }
		
		@Override
	    protected void writeWordSeparator() throws IOException
	    {
	        output.write(getWordSeparator());
	        if ( startLine ) headerOutput.write(" ");
	    }

		@Override
	    protected void writeLineSeparator( ) throws IOException
	    {
			maybeNewLine = true;
	        if ( startLine ) {
	        	maybeNewLine2 = true;
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
		        	headerOutput.write("\r\n");
					maybeNewLine2 = false;
				} else if ( maybeNewLine2 && Character.isLowerCase(text.charAt(0)) ) {
			        headerOutput.write(getWordSeparator());
					maybeNewLine2 = false;
				}
	        	headerOutput.write(text);
	        	startLine = true;
	        }
	    }
	}

}

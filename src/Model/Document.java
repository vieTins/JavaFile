package Model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Document {
 private List<String> lines ;

public Document() {
	super();
	this.lines =  new ArrayList<String>()	;
}

public List<String> getLines() {
	return lines;
}

public void setLines(List<String> lines) {
	this.lines = lines;
}
 public void addLines (String line) {
	 lines.add(line) ;
 }
 public void removeLine(int index) {
	lines.remove(index) ;
}
 public boolean deleteFile(File file) {
	 return file.delete() ;
 }
}

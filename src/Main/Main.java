package Main;

import Controller.Controller;
import Model.Document;
import View.TextEditer;

public class Main {
public static void main(String[] args) {
	Document model = new Document() ;
	TextEditer frame = new TextEditer();
	Controller controller = new Controller(model, frame) ;
	frame.setVisible(true);
}
}

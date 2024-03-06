package application;

import controller.DrawingController;
import drawing.FrmDrawing;
import model.DrawingModel;

public class DrawingApp {
	
public static void main(String[] args) {
		
	    FrmDrawing frame = new FrmDrawing();
		frame.setVisible(true);
		frame.setTitle("IT60-2017-Katarina-Mitrovic");
		DrawingModel model = new DrawingModel();
		frame.getView().setModel(model);
		frame.setController(new DrawingController(model, frame));
	}

}

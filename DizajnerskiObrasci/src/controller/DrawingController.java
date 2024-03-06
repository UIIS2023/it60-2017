package controller;

import java.awt.Color;

import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Stack;

import javax.swing.DefaultListModel;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import adapter.HexagonAdapter;
import command.CmdAddShape;
import command.CmdBringToBack;
import command.CmdBringToFront;
import command.CmdCircleUpdate;
import command.CmdDelete;
import command.CmdDonutUpdate;
import command.CmdHexagonUpdate;
import command.CmdLineUpdate;
import command.CmdPointUpdate;
import command.CmdRectangleUpdate;
import command.CmdSelectShape;
import command.CmdToBack;
import command.CmdToFront;
import command.Command;
import drawing.DlgCircle;
import drawing.DlgDonut;
import drawing.DlgHexagon;
import drawing.DlgLine;
import drawing.DlgPoint;
import drawing.DlgRectangle;
import drawing.FrmDrawing;
import hexagon.Hexagon;
import model.DrawingModel;
import shapes.Circle;
import shapes.Donut;
import shapes.Line;
import shapes.Point;
import shapes.Rectangle;
import shapes.Shape;
import strategy.LogFile;
import strategy.ManagerFile;
import strategy.SaveDraw;
import strategy.SerializableFile;

public class DrawingController {

	private DrawingModel model;
	private FrmDrawing frame;
	private Color edgeColor;
	private Color innerColor;
	private Stack<Command> commands;
	private Stack<Command> undoCommands;
	private int selectedShapesCount = 0;
	private DefaultListModel<String> log;
	private ManagerFile manager;
	private Point initialPointOfLine;
	private PropertyChangeSupport propertyChangeSupport;
	private Color ChEdgeColor;
	private Color ChInnerColor;

	public DrawingController(DrawingModel model, FrmDrawing frame) {

		this.model = model;
		this.frame = frame;

		this.edgeColor = Color.BLACK;
		this.innerColor = Color.WHITE;

		log = frame.getList();
		commands = new Stack<>();
		undoCommands = new Stack<>();

		initialPointOfLine = null;
		propertyChangeSupport = new PropertyChangeSupport(this);
	}

	public void addPropertyChangedListener(PropertyChangeListener propertyChangeListener) {
		propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
	}

	public Color btnEdgeColorClicked() {
		ChEdgeColor = JColorChooser.showDialog(null, "Pallete", edgeColor);
		if (ChEdgeColor != null) {
			if (ChEdgeColor.equals(Color.WHITE)) {
				JOptionPane.showMessageDialog(null, "Background is white");
				return null;
			}
			edgeColor = ChEdgeColor;
			return edgeColor;
		}
		return ChEdgeColor;
	}

	public Color btnInteriorColorClicked() {
		ChInnerColor = JColorChooser.showDialog(null, "Pallete", innerColor);
		if (ChInnerColor != null) {
			innerColor = ChInnerColor;
			return innerColor;
		}
		return ChInnerColor;
	}


	public void btnSelectShapeClicked(MouseEvent click) {
		ListIterator<Shape> it = model.getAll().listIterator();
		ArrayList<Integer> listOfShape = new ArrayList<>(); // pomocna lista oblika

		while (it.hasNext()) {
			Shape selectedShape = it.next();
			if (selectedShape.containsClick(click.getX(), click.getY()))
				listOfShape.add(model.getIndexOf(selectedShape));
		}

		if (!listOfShape.isEmpty()) {
			Shape shape = model.getByIndex(Collections.max(listOfShape));

			if (!shape.isSelected()) { // oblik nije selektovan, selektuj
				++selectedShapesCount;
				Command cmd = new CmdSelectShape(shape, true);
				executeCommand(cmd);
				log.addElement(cmd.txtForLog());
			} else { // oblik je selektovan, diselect
				--selectedShapesCount;
				Command cmd = new CmdSelectShape(shape, false);
				executeCommand(cmd);
				log.addElement(cmd.txtForLog());
			}
			handleSelectButtons();
		}

		frame.getView().repaint();
	}

	public void handleSelect(String s, String command) {
		if (command.equals("redo")) {
			if (s.equals("Selected"))
				++selectedShapesCount;
			else
				--selectedShapesCount;
			handleSelectButtons();
		} else if (command.equals("undo")) {
			if (s.equals("Selected"))
				--selectedShapesCount;
			else
				++selectedShapesCount;
			handleSelectButtons();
		} else if (command.equals("parser")) {
			if (s.equals("Selected"))
				++selectedShapesCount;
			else
				--selectedShapesCount;
		}
	}

	public void handleSelectButtons() {
		if (selectedShapesCount == 0)
			propertyChangeSupport.firePropertyChange("unselected", false, true);
		else if (selectedShapesCount == 1) {
			propertyChangeSupport.firePropertyChange("update turn on", false, true);
			propertyChangeSupport.firePropertyChange("selected", false, true);
		} else if (selectedShapesCount > 1)
			propertyChangeSupport.firePropertyChange("update turn off", false, true);
	}
	
	public void executeCommand(Command command) {
		command.execute();
		commands.push(command);

		if (!undoCommands.isEmpty()) {
			undoCommands.removeAllElements();
			propertyChangeSupport.firePropertyChange("redo turn off", false, true);
		}

		if (model.getAll().isEmpty())
			propertyChangeSupport.firePropertyChange("don't exist", false, true);
		else if (model.getAll().size() == 1)
			propertyChangeSupport.firePropertyChange("exist", false, true);

		if (commands.isEmpty())
			propertyChangeSupport.firePropertyChange("draw is empty", false, true);
		else if (commands.size() == 1)
			propertyChangeSupport.firePropertyChange("draw is not empty", false, true);
		frame.getView().repaint();
	}

//crtanje

//Tacka
	public void btnAddPointClicked(MouseEvent e) {
		Point point = new Point(e.getX(), e.getY(), edgeColor);
		Command cmd = new CmdAddShape(point, model);
		executeCommand(cmd);
		log.addElement(cmd.txtForLog());
	}

//Linija
	public void btnAddLineClicked(MouseEvent e) {
		if (initialPointOfLine == null)
			initialPointOfLine = new Point(e.getX(), e.getY(), edgeColor);
		else {
			Line line = new Line(initialPointOfLine, new Point(e.getX(), e.getY()), edgeColor);
			Command cmd = new CmdAddShape(line, model);
			executeCommand(cmd);
			log.addElement(cmd.txtForLog());
			initialPointOfLine = null;
		}
	}

//Pravougaonik
	public void btnAddRectangleClicked(MouseEvent e) {
		DlgRectangle dlgRectangle = new DlgRectangle();
		dlgRectangle.write(e.getX(), e.getY(), frame.getView().getWidth(), frame.getView().getHeight());
		dlgRectangle.deleteButtons();
		dlgRectangle.setVisible(true);
		if (dlgRectangle.isConfirmed()) {
			Rectangle rectangle = new Rectangle(new Point(e.getX(), e.getY()), dlgRectangle.getRectangleWidth(),
					dlgRectangle.getRectangleHeight(), edgeColor, innerColor);
			Command cmd = new CmdAddShape(rectangle, model);
			executeCommand(cmd);
			log.addElement(cmd.txtForLog());
		}
	}

//Krug
	public void btnAddCircleClicked(MouseEvent e) {
		DlgCircle dlgCircle = new DlgCircle();
		dlgCircle.write(e.getX(), e.getY(), frame.getView().getWidth(), frame.getView().getHeight());
		dlgCircle.deleteButtons();
		dlgCircle.setVisible(true);
		if (dlgCircle.isConfirmed()) {
			Circle circle = new Circle(new Point(e.getX(), e.getY()), dlgCircle.getRadiusLength(), edgeColor,
					innerColor);
			Command cmd = new CmdAddShape(circle, model);
			executeCommand(cmd);
			log.addElement(cmd.txtForLog());
		}
	}

//Krofna
	public void btnAddDonutClicked(MouseEvent e) {
		DlgDonut dlgDonut = new DlgDonut();
		dlgDonut.write(e.getX(), e.getY(), frame.getView().getWidth(), frame.getView().getHeight());
		dlgDonut.deleteButtons();
		dlgDonut.setVisible(true);
		if (dlgDonut.isConfirmed()) {
			Donut donut = new Donut(new Point(e.getX(), e.getY()), dlgDonut.getRadiusLength(),
					dlgDonut.getInnerRadiusLength(), edgeColor, innerColor);
			Command cmd = new CmdAddShape(donut, model);
			executeCommand(cmd);
			log.addElement(cmd.txtForLog());
		}
	}

//Hexagon
	public void btnAddHexagonClicked(MouseEvent e) {
		DlgHexagon dlgHexagon = new DlgHexagon();
		dlgHexagon.write(e.getX(), e.getY(), frame.getView().getWidth(), frame.getView().getHeight());
		dlgHexagon.deleteButtons();
		dlgHexagon.setVisible(true);
		if (dlgHexagon.isConfirmed()) {
			Hexagon hexagon = new Hexagon(e.getX(), e.getY(), dlgHexagon.getRadiusLength());
			hexagon.setBorderColor(edgeColor);
			hexagon.setAreaColor(innerColor);
			HexagonAdapter hexagonAdapter = new HexagonAdapter(hexagon);
			Command cmd = new CmdAddShape(hexagonAdapter, model);
			executeCommand(cmd);
			log.addElement(cmd.txtForLog());
		}
	}

	public Shape getSelectedShape() {
		Iterator<Shape> iterator = model.getAll().iterator();
		while (iterator.hasNext()) {
			Shape shapeForModification = iterator.next();
			if (shapeForModification.isSelected())
				return shapeForModification;
		}
		return null;
	}

//Update
//Point
	public void btnUpdatePointClicked(Point oldPoint) {
		DlgPoint dlgPoint = new DlgPoint();
		dlgPoint.write(oldPoint, frame.getView().getWidth(), frame.getView().getHeight());
		dlgPoint.setVisible(true);
		if (dlgPoint.isConfirmed()) {
			Point newPoint = new Point(dlgPoint.getXcoordinate(), dlgPoint.getYcoordinate(), dlgPoint.getColor());
			Command cmd = new CmdPointUpdate(oldPoint, newPoint);
			log.addElement(cmd.txtForLog());
			executeCommand(cmd);
		}
	}

	public void btnUpdateLineClicked(Line oldLine) {
		DlgLine dlgLine = new DlgLine();
		dlgLine.write(oldLine);
		dlgLine.setVisible(true);
		if (dlgLine.isConfirmed()) {
			Line newLine = new Line(new Point(dlgLine.getXcoordinateInitial(), dlgLine.getYcoordinateInitial()),
					new Point(dlgLine.getXcoordinateLast(), dlgLine.getYcoordinateLast()), dlgLine.getColor());
			Command cmd = new CmdLineUpdate(oldLine, newLine);
			log.addElement(cmd.txtForLog());
			executeCommand(cmd);

		}
	}

	public void btnUpdateRectangleClicked(Rectangle oldRectangle) {
		DlgRectangle dlgRectangle = new DlgRectangle();
		dlgRectangle.fillUp(oldRectangle, frame.getView().getWidth(), frame.getView().getHeight());
		dlgRectangle.setVisible(true);
		if (dlgRectangle.isConfirmed()) {
			Rectangle newRectangle = new Rectangle(
					new Point(dlgRectangle.getXcoordinate(), dlgRectangle.getYcoordinate()),
					dlgRectangle.getRectangleWidth(), dlgRectangle.getRectangleHeight(), dlgRectangle.getEdgeColor(),
					dlgRectangle.getInteriorColor());
			Command cmd = new CmdRectangleUpdate(oldRectangle, newRectangle);
			log.addElement(cmd.txtForLog());
			executeCommand(cmd);

		}
	}

	public void btnUpdateCircleClicked(Circle oldCircle) {
		DlgCircle dlgCircle = new DlgCircle();
		dlgCircle.fillUp(oldCircle, frame.getView().getWidth(), frame.getView().getHeight());
		dlgCircle.setVisible(true);
		if (dlgCircle.isConfirmed()) {
			Circle newCircle = new Circle(
					new Point(dlgCircle.getXcoordinateOfCenter(), dlgCircle.getYcoordinateOfCenter()),
					dlgCircle.getRadiusLength(), dlgCircle.getEdgeColor(), dlgCircle.getInteriorColor());
			Command cmd = new CmdCircleUpdate(oldCircle, newCircle);
			log.addElement(cmd.txtForLog());
			executeCommand(cmd);
		}
	}

	public void btnUpdateDonutClicked(Donut oldDonut) {
		DlgDonut dlgDonut = new DlgDonut();
		dlgDonut.fillUp(oldDonut, frame.getView().getWidth(), frame.getView().getHeight());
		dlgDonut.setVisible(true);
		if (dlgDonut.isConfirmed()) {
			Donut newDonut = new Donut(new Point(dlgDonut.getXcoordinateOfCenter(), dlgDonut.getYcoordinateOfCenter()),
					dlgDonut.getRadiusLength(), dlgDonut.getInnerRadiusLength(), dlgDonut.getEdgeColor(),
					dlgDonut.getInteriorColor());
			Command cmd = new CmdDonutUpdate(oldDonut, newDonut);
			log.addElement(cmd.txtForLog());
			executeCommand(cmd);

		}
	}

	public void btnUpdateHexagonClicked(HexagonAdapter oldHexagon) {
		DlgHexagon dlgHexagon = new DlgHexagon();
		dlgHexagon.fillUp(oldHexagon, frame.getView().getWidth(), frame.getView().getHeight());
		dlgHexagon.setVisible(true);
		if (dlgHexagon.isConfirmed()) {
			Hexagon hex = new Hexagon(dlgHexagon.getXcoordinate(), dlgHexagon.getYcoordinate(),
					dlgHexagon.getRadiusLength());
			hex.setAreaColor(dlgHexagon.getInteriorColor());
			hex.setBorderColor(dlgHexagon.getEdgeColor());
			HexagonAdapter newHexagon = new HexagonAdapter(hex);
			Command cmd = new CmdHexagonUpdate(oldHexagon, newHexagon);
			log.addElement(cmd.txtForLog());
			executeCommand(cmd);

		}
	}

	public void updateShapeClicked() {
		Shape shape = getSelectedShape();
		if (shape instanceof Point)
			btnUpdatePointClicked((Point) shape);
		else if (shape instanceof Line)
			btnUpdateLineClicked((Line) shape);
		else if (shape instanceof Rectangle)
			btnUpdateRectangleClicked((Rectangle) shape);
		else if (shape instanceof Donut)
			btnUpdateDonutClicked((Donut) shape);
		else if (shape instanceof Circle)
			btnUpdateCircleClicked((Circle) shape);
		else if (shape instanceof HexagonAdapter)
			btnUpdateHexagonClicked((HexagonAdapter) shape);
	}

	public void toFront() {
		Shape shape = getSelectedShape();

		if (model.getIndexOf(shape) == model.getAll().size() - 1)
			return;

		CmdToFront cmdToFront = new CmdToFront(model, shape);
		executeCommand(cmdToFront);
		log.addElement(cmdToFront.txtForLog());

	}

	public void bringToFront() {
		Shape shape = getSelectedShape();

		if (model.getIndexOf(shape) == model.getAll().size() - 1)
			return;

		CmdBringToFront cmdBringToFront = new CmdBringToFront(model, shape, model.getAll().size() - 1);
		executeCommand(cmdBringToFront);
		log.addElement(cmdBringToFront.txtForLog());

	}

	public void toBack() {
		Shape shape = getSelectedShape();

		if (model.getIndexOf(shape) == 0)
			return;

		CmdToBack cmdToBack = new CmdToBack(model, shape);
		executeCommand(cmdToBack);
		log.addElement(cmdToBack.txtForLog());

	}

	public void bringToBack() {
		Shape shape = getSelectedShape();

		if (model.getIndexOf(shape) == 0)
			return;

		CmdBringToBack cmdBringToBack = new CmdBringToBack(model, shape);
		executeCommand(cmdBringToBack);
		log.addElement(cmdBringToBack.txtForLog());

	}

	public void DeleteSelected() {
		Iterator<Shape> it = model.getAll().iterator();
		ArrayList<Shape> shapesForDeletion = new ArrayList<Shape>();

		while (it.hasNext()) {
			Shape shape = it.next();
			if (shape.isSelected()) {
				shapesForDeletion.add(shape);
				selectedShapesCount--;

			}
		}
		Command commandDelete = new CmdDelete(shapesForDeletion, model);
		executeCommand(commandDelete);
		log.addElement(commandDelete.txtForLog());
		handleSelectButtons();
	}

	public void btnDeleteShapeClicked() {
		if (JOptionPane.showConfirmDialog(null, "Are you sure that you want to delete selected shape?", "Warning!",
				JOptionPane.YES_NO_OPTION) == 0) {
			DeleteSelected();
		}
	}


	public void undo() {
		if (commands.isEmpty())
			return;
		commands.peek().unexecute();
		log.addElement("Undo->" + commands.peek().txtForLog());
		if (commands.peek() instanceof CmdSelectShape)
			handleSelect((log.get(log.size() - 1)).split("->")[0], "undo");
		undoCommands.push(commands.pop());

		if (undoCommands.size() == 1)
			propertyChangeSupport.firePropertyChange("redo turn on", false, true);
		frame.getView().repaint();
	}

	public void redo() {
		if (undoCommands.isEmpty())
			return;
		commands.push(undoCommands.pop());
		commands.peek().execute();
		log.addElement("Redo->" + commands.peek().txtForLog());
		if (commands.peek() instanceof CmdSelectShape)
			handleSelect((log.get(log.size() - 1)).split("->")[0], "redo");

		if (undoCommands.isEmpty())
			propertyChangeSupport.firePropertyChange("redo turn off", false, true);
		if (commands.size() == 1) {
			propertyChangeSupport.firePropertyChange("exist", false, true);
			propertyChangeSupport.firePropertyChange("draw is not empty", false, true);
			propertyChangeSupport.firePropertyChange("log turn on", false, true);
		}
		frame.getView().repaint();
	}

	public void save() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.enableInputMethods(false);
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileHidingEnabled(false);
		chooser.setEnabled(true);
		chooser.setDialogTitle("Save");
		chooser.setAcceptAllFileFilterUsed(false);
		if (!model.getAll().isEmpty()) {
			chooser.setFileFilter(new FileNameExtensionFilter("Serialized draw", "ser"));
			chooser.setFileFilter(new FileNameExtensionFilter("Picture", "jpeg"));
		}
		if (!commands.isEmpty())
			chooser.setFileFilter(new FileNameExtensionFilter("Commands log", "log"));
		if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			if (chooser.getFileFilter().getDescription().equals("Serialized draw"))
				manager = new ManagerFile(new SerializableFile(model));
			else if (chooser.getFileFilter().getDescription().equals("Commands log"))
				manager = new ManagerFile(new LogFile(frame, model, this));
			else
				manager = new ManagerFile(new SaveDraw(frame));
			manager.save(chooser.getSelectedFile());
		}
		chooser.setVisible(false);
	}

	public void open() {
		JFileChooser chooser = new JFileChooser();
		chooser.enableInputMethods(true);
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileHidingEnabled(false);
		chooser.setEnabled(true);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
		chooser.setFileFilter(new FileNameExtensionFilter("Serialized draw", "ser"));
		chooser.setFileFilter(new FileNameExtensionFilter("Commands log", "log"));
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			model.removeAll();
			log.removeAllElements();
			undoCommands.clear();
			commands.clear();
			frame.getView().repaint();
			if (chooser.getFileFilter().getDescription().equals("Serialized draw")) {
				manager = new ManagerFile(new SerializableFile(model));
				propertyChangeSupport.firePropertyChange("serialized draw opened", false, true);
			} else if (chooser.getFileFilter().getDescription().equals("Commands log"))
				manager = new ManagerFile(new LogFile(frame, model, this));
			manager.open(chooser.getSelectedFile());
		}
		chooser.setVisible(false);
	}

	public void newDraw() {

		if (JOptionPane.showConfirmDialog(null, "Are you sure that you want to start new draw?", "Warning",
				JOptionPane.YES_NO_OPTION) == 0) {
			model.removeAll();
			log.removeAllElements();
			undoCommands.clear();
			commands.clear();
			propertyChangeSupport.firePropertyChange("draw is empty", false, true);
			frame.getView().repaint();
		}
	}
}
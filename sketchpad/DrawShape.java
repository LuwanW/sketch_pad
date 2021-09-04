package sketchpad;
// import sketchpad.shape;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;
public class DrawShape implements MouseListener, ActionListener, MouseMotionListener ,KeyEventDispatcher  {

	Graphics g;
	JPanel canvas;
	Shape shape;
	DeletedShape<Shape> deletedShape;
	ArrayList<Shape> shapes;
	Shape closest = new Shape();
	String mode = "free";
	int special_case = 0, dmin;
	ArrayList<Integer> coordinates_x;
	ArrayList<Integer> coordinates_y;
	int finished=1 ; 
	Color color = Color.BLACK;
	int selected = -1;
	Shape selected_shape = new Shape();

	DrawShape(JPanel canvas, ArrayList<Shape> shapes, DeletedShape<Shape> deletedShape){
		this.shapes = shapes;
		this.canvas = canvas;
		this.deletedShape = deletedShape;

	}
	public Shape getShape(){
		return shapes.get(shapes.size() - 1);
		
	}

	public void setmode(String mode) {
		this.mode = mode;
	}



	public void mousePressed(MouseEvent e) {
		if(mode == "drag"){
			selected_shape = shapes.get(selected);
		}
		if(mode != "polygons" || (mode == "polygons" && finished ==1) ){
			coordinates_x = new ArrayList<Integer>();
			coordinates_y = new ArrayList<Integer>();
			coordinates_x.add(e.getX());
			coordinates_y.add(e.getY());
			special_case = 0;

			shape = new Shape(coordinates_x, coordinates_y,special_case, mode,color);
			shapes.add(shape);
			finished = 0;
		}else if (finished == 0 && mode == "polygons" ){

			int x1 = shape.coordinates_x.get(0);
			int y1 = shape.coordinates_y.get(0);
			// if this point is close to the first point, connect and finish drawing
			if (Math.sqrt(Math.pow(y1-e.getY(),2) + Math.pow((x1-e.getX()),2)) < 30 ){
				shape.addCoordinates(x1, y1);
				finished = 1;
			// else keep drawing
			}else{
				shape.addCoordinates(e.getX(), e.getY());
			}	
				
			
		}
		canvas.repaint();

	}
	public void mouseDragged(MouseEvent e) {
		int x1 = e.getX();
		int y1 = e.getY();
		if(mode == "polygons"){
			shape.updateCoordinates(x1, y1);
		}else if(mode == "drag"){
			int x_dis = x1 - selected_shape.coordinates_x.get(0);
			int y_dis = y1 - selected_shape.coordinates_y.get(0);
			selected_shape.updateAllCoordinates(x_dis, y_dis);
		}else{
			shape.addCoordinates(x1, y1);
			shape.updateSpecialCase(special_case);
		}
		canvas.repaint();

	}

	public void mouseMoved(MouseEvent e) {
		int x1 = e.getX();
		int y1 = e.getY();

		dmin = 9999999;
		if(mode == "drag"){
			for(int i=0;i<shapes.size(); i++){
				shape = shapes.get(i);
				shape.selected = 0; 
				int d=shape.segdist(x1,y1,shape.coordinates_x.get(0),shape.coordinates_y.get(0), shape.coordinates_x.get(shape.coordinates_x.size()-1),shape.coordinates_y.get(shape.coordinates_y.size()-1)   );
				if( d< dmin ) { closest=shape; dmin=d; selected = i;}
			}

			closest.selected = 1 ;
			canvas.repaint();

	   }   


	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String selected_mode = e.getActionCommand();
		if(selected_mode == "undo"){
			deletedShape.push(shapes.get(shapes.size()-1));
			shapes.remove(shapes.size()-1);
			canvas.repaint();
		}else if(selected_mode == "redo" && deletedShape.size()>0){
			shapes.add(deletedShape.pop());
			canvas.repaint();

		}
		this.mode = selected_mode;
		
	}

	@Override
	// press any key to draw in special mode
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (e.getID() == KeyEvent.KEY_PRESSED) {
			special_case = 1;
			// press any key to finish drawing polygon(draw open polygon)
			if(mode == "polygons"){
				finished = 1;
			}
		} else if (e.getID() == KeyEvent.KEY_RELEASED) {
			special_case = 0;
		} 
		return false;
	}


	// unused overrides
	public void mouseReleased(MouseEvent e) {
		if (mode != "polygons"){
			finished = 1;
		}

	}




	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}





}
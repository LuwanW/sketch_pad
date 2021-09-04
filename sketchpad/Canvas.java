package sketchpad;
import java.awt.*;
import javax.swing.border.Border;
import javax.swing.*;
import java.util.ArrayList;
public class Canvas extends JPanel {
    ArrayList<Shape> shapes;
    DeletedShape<Shape> deletedShape;
    DrawShape drawShape;
    Canvas(ArrayList<Shape> shapes, DeletedShape<Shape> deletedShape){
        this.shapes = shapes;
        Border blackline = BorderFactory.createLineBorder(Color.black);
        setBorder(blackline);
        setSize(600,600);
        this.setVisible(true);
        this.deletedShape = deletedShape;


    }
    public void setDrawShape(DrawShape drawShape) {
        this.drawShape = drawShape;

    }

    @Override
    // same as paint
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(g!=null && shapes.size()>0){
            shapes.forEach(shape->{
                g.setColor(shape.color);
                if (shape.selected ==1){
                    g.setColor(Color.red);
                }
                if(shape.type == "line"){
                    g.drawLine(shape.coordinates_x.get(0), shape.coordinates_y.get(0),shape.coordinates_x.get(shape.coordinates_x.size()-1), shape.coordinates_y.get(shape.coordinates_y.size()-1));

                }
                else if(shape.type == "free"){
                    int x;
                    int y; 
                    for (int i = 0; i < shape.coordinates_x.size(); i++) {
                        x = shape.coordinates_x.get(i);
                        y = shape.coordinates_y.get(i);
                        g.drawLine(x, y, x, y);

                    }
            
                }
                else if(shape.type == "rectangle"){
                    int x0 = shape.coordinates_x.get(0);
                    int x1 = shape.coordinates_x.get(shape.coordinates_x.size()-1);
                    int y0 = shape.coordinates_y.get(0);
                    int y1 = shape.coordinates_y.get(shape.coordinates_y.size()-1);
                    if(shape.special_case == 0){
                        g.drawRect(x0, y0, Math.abs(x1-x0), Math.abs(y1-y0));
                    }else{
                        int distance = Math.abs(x1-x0);
                        g.drawRect(x0, y0,distance, distance);

                    }
                }
                else if(shape.type == "ellipses"){
                    int x0 = shape.coordinates_x.get(0);
                    int x1 = shape.coordinates_x.get(shape.coordinates_x.size()-1);
                    int y0 = shape.coordinates_y.get(0);
                    int y1 = shape.coordinates_y.get(shape.coordinates_y.size()-1);
                    if(shape.special_case == 0){
                        g.drawOval(Math.min(x0, x1), Math.min(y0, y1), Math.abs(x0 - x1), Math.abs(y0 - y1));
                    }else{
                        int distance = Math.abs(x1-x0);
                        g.drawOval(Math.min(x0, x1), Math.min(y0, y1),distance, distance);                    }
                }
                else if(shape.type == "polygons"){
                    int x, y, x1, y1;
                    for (int i = 0; i < shape.coordinates_x.size()-1; i++) {
                        x = shape.coordinates_x.get(i);
                        y = shape.coordinates_y.get(i);
                        x1 = shape.coordinates_x.get(i+1);
                        y1 = shape.coordinates_y.get(i+1);
                        g.drawLine(x, y, x1, y1);
                    }
                }
                if (shape.selected ==1){
                    g.setColor(shape.color);
                }
            });
        }
    }
 
}

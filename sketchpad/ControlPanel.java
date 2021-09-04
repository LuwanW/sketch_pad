package sketchpad;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.*;  
import javax.swing.*; 
import java.util.ArrayList;
import java.awt.KeyboardFocusManager;
import java.lang.reflect.Field;

public class ControlPanel extends  JFrame implements ActionListener {  

  
    ArrayList<Shape> shapes = new ArrayList<Shape>();
    // store upto 10 deleted shapes
    DeletedShape<Shape> deletedShape = new DeletedShape<Shape>(10);
    ArrayList<JButton> buttons = new ArrayList<JButton>();
    String[] name = {"line", "free", "rectangle", "ellipses", "polygons", "drag", "undo", "redo"};
    JComboBox colors;
    JLabel mode_label;
    DrawShape drawShapeListener;
    public ControlPanel(){
        // beauty
        Border blackline = BorderFactory.createLineBorder(Color.black);

        // initialize window and canvas
        JFrame window=new JFrame("scatch pad");  
        window.setVisible(true); 
        Canvas canvas = new Canvas(shapes, deletedShape);
        canvas.setBorder(blackline);
        canvas.setSize(999,599);
        drawShapeListener =  new DrawShape(canvas, shapes, deletedShape);
        canvas.setDrawShape(drawShapeListener);

        // add mouselistener and keyboardlistner
        canvas.addMouseListener(drawShapeListener);
		canvas.addMouseMotionListener(drawShapeListener);
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(drawShapeListener);

        // show mode and status
        mode_label = new JLabel("not selected");
        JLabel special_case_label = new JLabel(" press and hold any key to draw in special case or to finish a polygon(open polygon)");
        special_case_label.setBounds(400,30,500,40);
        mode_label.setBounds(50,30,100,40); 
        canvas.add(mode_label);
        window.add(special_case_label);

        // add mode buttons
        int buttton_x_location = 150;
		for (int i = 0; i < name.length; i++) {
			JButton modelButton = new JButton(name[i]);
            modelButton.setBounds(buttton_x_location,30,100,40); 
			modelButton.addActionListener(drawShapeListener);
            modelButton.addActionListener(this);
            buttton_x_location += 100;
            canvas.add(modelButton);
            buttons.add(modelButton);
		}


        // add color selection 
        String coloString[]={"black", "blue", "green", "pink", "cyan", "orange", "white", "magenta"}; 
        colors =new JComboBox(coloString);
        colors.addActionListener(this);
        canvas.add(colors);
        
        // window setting
        window.add(canvas, BorderLayout.CENTER);
        window.setSize(1200,700);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    // show mode states
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == colors) {
            String color_str = (String) colors.getSelectedItem();
            Color color = getColor(color_str);
            drawShapeListener.color = color; 

        }else{
            int index = buttons.indexOf(e.getSource());
            mode_label.setText("mode: "+name[index]+"   ");
        }

    }
    
	public Color getColor(String color_str) {
        Color color;
		try {
			Field field = Color.class.getField(color_str);
			color = (Color)field.get(null);
		} catch (Exception e) {
			color = null; // Not defined
		}
		return color;
	}



}  

package sketchpad;
import java.util.ArrayList;
import java.awt.*;

public class Shape {
        String type;
		Color color;
        int special_case;
        ArrayList<Integer> coordinates_x;
        ArrayList<Integer> coordinates_y;
		int selected = 0;
		Shape( ArrayList<Integer> coordinates_x, ArrayList<Integer> coordinates_y, int special_case, String type, Color color) {
			this.coordinates_y = coordinates_y;
			this.coordinates_x = coordinates_x;
			this.type = type;
			this.color = color;
            this.special_case = special_case;

		}
        Shape(){}
        public  ArrayList<Integer> getX() {
            return coordinates_x;
        }
        public  ArrayList<Integer> getY() {
            return coordinates_y;
        }
        public void addCoordinates(int x, int y){
            this.coordinates_y.add(y);
            this.coordinates_x.add(x);
        }
		public void updateCoordinates(int x, int y){
			int index = this.coordinates_y.size()-1;
			if (index!=0){
	            this.coordinates_y.remove(index);
            	this.coordinates_x.remove(index);
				this.addCoordinates(x, y);			
			}else{
				this.addCoordinates(x, y);			

			}

        }
		public void updateAllCoordinates(int x, int y){
			for(int i=0; i<coordinates_x.size();i++){
				int old_x = coordinates_x.get(i);
				int old_y = coordinates_y.get(i);
				this.coordinates_x.set(i,old_x+x);
				this.coordinates_y.set(i,old_y+y);

			}

        }

        public void updateSpecialCase(int special_case){
            this.special_case = special_case;
        }
        int segdist(int xp, int yp, int xi, int yi, int xj, int yj) { // distance from point (xp,yp) to line segment (xi,yi,xj,yj)
			ipair I = new ipair(xi, yi), J = new ipair(xj, yj), P = new ipair(xp, yp), V, N;
            V = sub(J, I); // V is the vector from I to J
			int k = dot(V, sub(P, I)); // k is the non-normalized projection from P-I to V
			int L2 = dot(V, V); // L2 is the length of V, squared
			if (k <= 0)
				N = I; // if the projection is negative, I is nearest (N)
			else if (k >= L2)
				N = J; // if the projection too large, J is nearest (N)
			else
				N = add(I, scale(V, (float) k / (float) L2)); // otherwise, N is scaled onto V by k/L2
			return dist(P, N);
		}
        class ipair {
			int x, y;

			ipair(int xx, int yy) {
				x = xx;
				y = yy;
			}
		}
        ipair add(ipair U, ipair W) {
			return new ipair(U.x + W.x, U.y + W.y);
		}

		ipair sub(ipair U, ipair W) {
			return new ipair(U.x - W.x, U.y - W.y);
		}

		ipair scale(ipair U, float s) {
			return new ipair((int) (s * (float) U.x), (int) (s * (float) U.y));
		}

		int dist(ipair P, ipair Q) {
			return (int) Math.sqrt((P.x - Q.x) * (P.x - Q.x) + (P.y - Q.y) * (P.y - Q.y));
		}

		int dot(ipair P, ipair Q) {
			return P.x * Q.x + P.y * Q.y;
		}


	
}

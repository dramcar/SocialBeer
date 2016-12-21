import java.awt.Color;

public class Circulo {

	private int x;
	private int y;
	private int r = 10;
	private Color color;
	private String label;

	public Circulo (){
		/* Constructor */
	}


	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public int getR(){
		return r;
	}

	public Color getColor(){
		return color;
	}

	public String getLabel(){
		return label;
	}


	public void setX(int x){
		this.x = x;
	}

	public void setY(int y){
		this.y = y;
	}

	public void setR(int r){
		this.r = r;
	}

	public void setColor(Color color){
		this.color = color;
	}

	public void setLabel(String label){
		this.label = label;
	}

}
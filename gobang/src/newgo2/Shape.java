package newgo2;

import java.awt.Color;
import java.awt.Graphics;

/**
 * 此类用于设置棋子的位置颜色等参数，继承接口
 * @author JAVA
 *
 */
public class Shape implements Config{

	public int x1,y1;   
	 private Color color;	 
	 public Shape(int x1,int y1,Color color)
	 {
		 this.x1=x1;
		 this.y1=y1;
		 this.color=color;
	 }	
	 public void drawShape(Graphics g)
	 {
		 g.setColor(color);
		 g.drawOval(x1-CHESS_SIZE/2, y1-CHESS_SIZE/2, CHESS_SIZE, CHESS_SIZE);
		 g.fillOval(x1-CHESS_SIZE/2, y1-CHESS_SIZE/2, CHESS_SIZE, CHESS_SIZE);		 		
	 }

}

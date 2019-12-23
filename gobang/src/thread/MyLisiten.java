package thread;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyLisiten implements MouseListener{
	public MyFreme mf;
	int turn = 1; // 1表示该黑子下棋了 2表示白子下棋
	int end = 0; //游戏结束判定 end=【5】时游戏结束
	int arrX,arrY;// 记录鼠标坐标相对应的数组位置
	
	public void setG(MyFreme mf) {
		this.mf = mf;
	}
//public int gameCompleteUP(int arrX,int arrY) {
////判断游戏是否结束
//if(arrY<=15 && end<=5) {
//if(mf.isArray[arrX][arrY] == 1) {
//end++;
//}
//int up = gameCompleteUP(arrX,arrY+1);
//		int down = gameCompleteUP(arrX,arrY-1);
//		end = up+down;	
//	}
//	return end;
//		
//}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自动生成的方法存根
		int x = e.getX();               //获取鼠标点击的坐标
		int y = e.getY();
		
		// 进行位置范围的判定  与点击位置相近的为下棋的位置
		if(x%40<=20 && y%40<=20) {
			arrX = x/40-1;
			arrY = y/40-1;
		}else if (x%40>20 && y%40<=20) {
			arrX = x/40;
			arrY = y/40-1;
		}else if (x%40<20 && y%40>20) {
			arrX = x/40-1;
			arrY = y/40;
		}else {
			arrX = x/40;
			arrY = y/40;
		}
		if(mf.isArray[arrX][arrY]!= 0) {
			System.out.println("这个位置已经有棋子了,请换个位置！");
		}else {
			Graphics g = mf.getGraphics();
			System.out.println(arrX + "  " +arrY); // 初步测试是否实现鼠标监听的功能
			
			if(turn == 1) {    //黑子下棋
				int countX = 40*arrX+20;
				int countY = 40*arrY+20;
				g.setColor(Color.black);
				g.fillOval(countX, countY, 40, 40);
				mf.isArray[arrX][arrY] = 1;
				//gameCompleteUP(arrX, arrY);
				if(end>=5) {
					System.out.println("黑子获胜！");
					return;
				}
				
				turn++;
			}else {           //白子下棋
				int countX = 40*arrX+20;
				int countY = 40*arrY+20;
				g.setColor(Color.red);
				g.fillOval(countX, countY, 40, 40);
				mf.isArray[arrX][arrY] = 2;
				turn--;
			}
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}
	//为服务器提供接收数组X的方法
	public int getArrX() {
		return arrX;
	}
	//为服务器提供接收数组y的方法
	public int getArrY() {
		return arrY;
	}

	
}

package thread;

//import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Graphics;
//import java.awt.event.AWTEventListener;
//import java.awt.event.MouseEvent;

import javax.swing.JFrame;
public class MyFreme extends JFrame {
	private static final long serialVersionUID = 1L;
	//static Graphics g; //新建画笔
	static int [][] isArray = new int[16][16];  //储存棋子的二维数组
	public static void main(String[] args) {
	//isArray[5][5] = 1;                        测试数据
		MyFreme myfreme = new MyFreme();     //创建游戏窗口变量
		myfreme.start();                     //加载游戏窗口
		
	}
	public void start() {
		MyFreme mf = new MyFreme();
		mf.setSize(800, 800);
		//mf.setLocation(200, 200);
		mf.setVisible(true);
		mf.setDefaultCloseOperation(3);
		mf.setLocationRelativeTo(null);
		mf.setBackground(Color.GRAY);
		// 添加对鼠标点击的自定义监听
		MyLisiten ml = new MyLisiten();  
		ml.setG(mf);                        //将当前游戏窗口导入监听类中
		mf.addMouseListener(ml);            // 为当前窗口添加监听
		
	}
	@Override
	public void paint(Graphics g) {
		// TODO 自动生成的方法存根
//		super.paint(g);
		for (int i = 0; i <=15; i++) {  //画行
			//从窗口自上向下画行,(x1,y1,x2,y2)
			//(x1,y1)是最左边点的坐标,(x2,y2)是最右边点的坐标
			g.drawLine(40, 40*i+40, 640, 40*i+40); 
			//在最后一行下面写上数字   0-15
			g.drawString(""+i, 40*i+33, 660);
			
		}
		for (int j = 0; j <=15; j++) {  //画列
			//从窗口自左向右画列,(x1,y1,x2,y2)
			//(x1,y1)是最上边点的坐标,(x2,y2)是最下边点的坐标
			g.drawLine(40*j+40, 40, 40*j+40, 640); 
			//在最左边一列左边写上数字   
			g.drawString(""+j,20, 40*j+20);
		}
		//画棋子
		for (int i = 0; i < 15; i++) {
			
			for (int j = 0; j < 15; j++) {
				//黑子为1
				if (isArray[i][j]==1 ) {
					int countX = 40*i+20;
					int countY = 40*j+20;
					g.setColor(Color.black);
					g.fillOval(countX, countY, 40, 40);
				}
				//白子为2
				if (isArray[i][j]==2 ) {
					int countX = 40*i+20;
					int countY = 40*j+20;
					g.setColor(Color.red);
					g.fillOval(countX, countY, 40, 40);
				}
			}
		}
	}
}
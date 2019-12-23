package online;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class serverChess extends JFrame{
	public static myFrame2 frame=null;
	public serverChess(myFrame2 frame) {
	    this.frame=frame;
	}

	public void start() throws Exception{

	    ServerSocket server=new ServerSocket(8089);
	    System.out.println("服务器端启动");
	    while(true){
	        Socket socket=server.accept();
	        final PrintWriter writer=new PrintWriter(socket.getOutputStream(),true);
	        Scanner sc=new Scanner(System.in);
	        new Thread(new Runnable() {

	            String line;
	            @Override
	            public void run() {
	                while((line=sc.nextLine())!=null) {
	                    //frame.text.setText("我："+line);
	                    frame.text.append("我："+line+"\r\n");
	                    writer.println("chat:"+line);
	                }
	            }

	        }).start();

	        new Thread(new Runnable(){
	            @Override
	            public void run() {
	                while(!frame.isWin()) {
	                    writer.println(frame.getRow()+","+frame.getCol());
	                    if(frame.backChess) {
	                        writer.println("back:"+frame.getRow()+":"+frame.getCol());
	                    }
	                }
	                writer.println("win:"+frame.isWin());
	            }

	        }).start();
	        new serverThread(socket,frame).start();
	    }

	}

	public static void main(String[] args) throws Exception {
	    myFrame2 frame=new myFrame2();
	    serverChess server=new serverChess(frame);
	    server.start();
	}	
}


class serverThread extends Thread{
	private Socket socket=null;
	private myFrame2 frame=null;
	
	public serverThread(Socket socket,myFrame2 frame2){
	this.socket=socket;
	this.frame=frame2;
	}
	@Override
	public void run() {
	
	    BufferedReader reader = null;
	    PrintWriter writer = null;
	    try {
	        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        writer = new PrintWriter(socket.getOutputStream(),true);
	        String line =reader.readLine();
	
	        while((line=reader.readLine()) != null) {
	            if(line.contains("ok")){
	                myFrame2.occupy=false;
	            }
	            if(line.contains(",")) {
	                String[] data=line.split(",");
	                int row=Integer.parseInt(data[0]);
	                int col=Integer.parseInt(data[1]);
	                if(frame.chess[row][col]==0){
	                    writer.println("ok");
	                    frame.chess[row][col]=1;
	                    frame.repaint();
	                    //writer.println(frame.getRow()+","+frame.getCol());
	                }   
	            }
	
	
	
	            if(line.contains("win:")) {
	                //System.out.println("你输了");
	                JOptionPane.showMessageDialog(null, "你输了");
	                frame.setWin(true);
	                frame.repaint();
	            }
	
	            if(line.contains("chat:")) {
	                String[] data=line.split(":");
	                //frame.text.setText("对方："+data[1]);
	                frame.text.append("对方："+data[1]+"\r\n");
	            }
	            if(line.contains("back:")) {
	
	
	
	                System.out.println(line);
	             // String[] data=line.split(“:”);
	             // int row=Integer.parseInt(data[1]);
	             // int col=Integer.parseInt(data[2]);
	             // frame.chess[row][col]=0;
	             // frame.repaint();
	             // frame.setRow(Integer.parseInt(data[1]));
	             // frame.setCol(Integer.parseInt(data[2]));
	             // System.out.println(frame.getRow()+”,”+frame.getCol());
	             // frame.chess[row][col]=1;
	            }
	        }
	        this.socket.close(); // 连接关闭，所有的流都会随之关闭

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	}
}

	
class serverPanel extends JPanel{
	public static int[][] board=new int[16][16];
	public serverPanel(int[][] board){
	    this.board=board;
	}
	@Override
	public void paint(Graphics g) {
	    g.setColor(new Color(165,185,75));
	    g.fillRect(60, 60, 360, 360);
	    g.setColor(Color.black);
	    //绘制棋盘
	    for(int i=0;i<15;i++) {
	        g.drawLine(65, 65+i*25, 415, 65+i*25);
	        g.drawLine(65+i*25, 65, 65+i*25, 415);
	    }
	    //绘制棋子
	    for(int i=1;i<board.length;i++) {
	        for(int j=1;j<board[i].length;j++) {
	            if(board[i][j]!=0) {
	                if(board[i][j]==1) {
	                    g.setColor(Color.BLACK);
	                }else {
	                    g.setColor(Color.WHITE);
	                }
	                g.fillOval(25*(j+2), 25*(i+2), 20, 20);

	                if(board[i][j]==2) {
	                    g.setColor(Color.BLACK);
	                    g.drawOval(25*(j+2), 25*(i+2), 20, 20);
	                }
	            }   
	        }
	    }
	}
	//初始化数组
	public boolean addPiece(int row,int col,boolean isBlack) {
	    if(board[row][col]==0) {
	        board[row][col]=isBlack?1:2;
	        return true;
	    }
	    return false;

	}

	public boolean reducePiece(int row,int col) {
	    if(board[row][col]!=0) {
	        board[row][col]=0;
	        return true;
	    }
	    return false;
	}



	public boolean isWin(int row,int col,boolean isBlack) {

	    return checkH(row, col, isBlack) || 
	            checkV(row, col, isBlack) || 
	            checkX1(row, col, isBlack) || 
	            checkX2(row, col, isBlack); 

	}
	//判断从右上到左下的斜线上是否连成5颗棋子
	private boolean checkX2(int row, int col, boolean isBlack) {
	    int counter=1;
	    int currentRow=row;
	    int currentCol=col;
	    int v=isBlack?1:2;
	    if(currentRow>0 && currentCol<14 && board[--currentRow][++currentCol]==v) {
	        counter++;
	    }
	    currentRow=row;
	    currentCol=col;
	    if(currentRow<14 && currentCol>0 && board[++currentRow][--currentCol]==v) {
	        counter++;
	    }
	    return counter>=5;
	}
	//判断从左上到右下
	private boolean checkX1(int row, int col, boolean isBlack) {
	    int counter=1;
	    int currentRow=row;
	    int currentCol=col;
	    int v=isBlack?1:2;
	    while(currentRow>0 && currentCol>0 && board[--currentRow][--currentCol]==v) {
	        counter++;
	    }
	    currentRow=row;
	    currentCol=col;
	    while(currentRow<14 && currentCol<14 && board[++currentRow][++currentCol]==v) {
	        counter++;
	    }
	    return counter>=5;
	}
	//判断竖着方向是否连成5颗棋子
	private boolean checkV(int row, int col, boolean isBlack) {
	    int counter=1;
	    int currentRow=row;
	    int currentCol=col;
	    int v=isBlack?1:2;
	    while(currentRow>0 && board[--currentRow][currentCol]==v) {
	        counter++;
	    }
	    currentRow=row;
	    currentCol=col;
	    while(currentRow<14  && board[++currentRow][currentCol]==v) {
	        counter++;
	    }
	    return counter>=5;
	}

	private boolean checkH(int row, int col, boolean isBlack) {

	    int counter=1;
	    int currentRow=row;
	    int currentCol=col;
	    int v=isBlack?1:2;
	    while(currentCol>0 && board[currentRow][--currentCol]==v) {
	        counter++;
	    }
	    currentRow=row;
	    currentCol=col;
	    while(currentCol<14 && board[currentRow][++currentCol]==v) {
	        counter++;
	    }
	    return counter>=5;

	}
}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
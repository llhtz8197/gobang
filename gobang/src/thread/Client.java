package thread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * 聊天室客服端
 * @author JAVA
 *
 */
public class Client {
	/*
	 * java.net.Socket  套接字
	 * Socket封装了TCP通讯的细节,使得我们使用
	 * TCP协议在两台计算机(客户端程序与服务端程序)
	 * 之间通讯以流的读写操作完成.  
	 */
	private Socket socket;
	
	private MyLisiten ml;
	/**
	 * 构造方法,用来初始化客户端
	 */
	public Client() {
		try {
			/*
			 * 实例化Socket的过程就是与服务器端建立连接的过程
			 * 这里需要传入两个参数:
			 * 1:服务端地址信息(IP地址)
			 * 2:服务端打开的端口
			 * 我们通过IP可以找到网络上的服务端所在的计算机,
			 * 通过端口可以找到运行在该机器上的服务端应用程序
			 * 
			 */
			System.out.println("正在连接服务端........");
			socket =new Socket("localhost",8088);//localhost为本地ip
			System.out.println("已连接服务端");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 客户端开始工作的方法
	 */
	public void start() {
		try {
			//首先启动读取服务端消息的线程
			ServerHandler handler = new ServerHandler();
			Thread t = new Thread(handler);
			t.start();
			/*
			 * OutputStream getOutputStream()
			 * 通过Socket获取一个输出流,
			 * 通过这个输出流写出的字节会发送给远端计算机
			 */
			OutputStream out = socket.getOutputStream();
			OutputStreamWriter osw =new OutputStreamWriter(out,"utf-8");
			BufferedWriter bw = new BufferedWriter(osw);
			PrintWriter pw = new PrintWriter(bw,true);
			
			//获取棋子的数组位置,将它发给服务器
			ml.getArrX();
			ml.getArrY();
			Scanner scan = new Scanner(System.in);
			while(true) {
			String str = scan.nextLine();
			pw.println(str);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Client client = new Client();
		client.start();
	}
	/**
	 * 该线程负责循环读取服务
	 * @author JAVA
	 *
	 */
	private class ServerHandler implements Runnable{
		public void run(){
			try {
				/*
				 * 通过socket获取输入流,读取服务端发送过来的消息
				 */
				InputStream is= socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is,"utf-8");
				BufferedReader br = new BufferedReader(isr);
				
				String message = null;
				while((message = br.readLine())!=null) {
					System.out.println(message);
				}
			} catch (Exception e) {
			}
		}
}

}











package thread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * 聊天室服务端
 * @author JAVA
 *
 */
public class Server {
	/*
	 * java.net.ServerSocket
	 * 运行在服务端的ServerSocket主要有两个工作:
	 * 1:向服务端申请服务端口,客户端就是通过
	 * 这个端口与服务端建立连接的
	 * 2:监听服务端口,一旦客户端连接了就会自动启动
	 * 创建一个Socket实例,通过该Socket就可以与客户端交互了
	 */
	private ServerSocket server;
	private String host;
	
	/*
	 * 用来保存所有ClientHandler中对应客户端的输出流,
	 * 以便广播消息使用
	 */
	private PrintWriter[] allOut = {};
	private int index=0;
	/**
	 * 构造方法,用来初始化服务端
	 */
	public Server() {			
		try {
			System.out.println("正在启动服务端.......");
			server =new ServerSocket(8088);
			System.out.println("服务端启动完毕");
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	
	/**
	 * 服务端开始工作的方法
	 */
	public void start() {
		try {
			/*
			 * Socket accept()
			 * ServerSocket提供的该方法用于等待客户端的连接,
			 * 一旦一个客户端建立连接,该方法会立刻返回一个Socket实例,
			 * 通过该Socket就可以与该客户端交互了.
			 * 多次调用该方法可以等待多个客户端的连接
			 */
			while(true) {
			System.out.println("等待客服端连接.......");
			Socket socket = server.accept();
			//获得客户端的ip地址
			host=socket.getInetAddress().getHostAddress();
			//实例化ip地址的方法
			Name name=new Name();
			//将ip转换为姓名之后传入
			host=name.name(host);
			System.out.println(host+"客户端连接了");
			//创建一个线程处理该客户端交互
			ClientHandler handler = new ClientHandler(socket,host);
			Thread t = new Thread(handler);
			t.start();
			index++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		server.start();
	}

	private class ClientHandler implements Runnable{
		
		private Socket socket;
		private String host;
		public ClientHandler(Socket socket,String host) {
			this.socket=socket;
			this.host=host;
		}
		public void run() {
			PrintWriter pw=null;
			try {
				/*
				 * InputStream getInputStream()
				 * 通过Socket获取的输入流读取的字节
				 * 是远端计算机发送过来的字节.
				 */	
			InputStream in = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(in,"utf-8");
			BufferedReader br = new BufferedReader(isr);
			
			/*
			 * 通过socket获取输出流,用于给客户端发送消息
			 */
			OutputStream out = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(out,"utf-8");
			BufferedWriter bw = new BufferedWriter(osw);
			pw = new PrintWriter(bw,true);
			/*
			 * 将该输出流存入allOut中用于共享
			 */
			synchronized(allOut) {
				//1.对allOut数组扩容
				allOut = Arrays.copyOf(allOut, allOut.length+1);
				//2.将pw存入数组
				allOut[allOut.length-1] = pw;
			}
			System.out.println(host+"上线了,当前在线人数:"+allOut.length);
			
			String message = null;
				/*
				 * 使用BufferedReader读取客户端发送过来的一行字符串时,
				 * 当客户端断开连接,此时客户端的系统不同时,反应通常不同:
				 * 当windows的客户端断开时,readLine方法通常会直接抛出SockeException
				 * 当linux的客户端断开时,readLine方法会返回null值
				 */
				while((message = br.readLine())!=null) { 
				System.out.println(host+"说:"+message);
				
				synchronized(allOut) {
					for(int i=0;i<allOut.length;i++)
						//遍历allOut,将消息发送给所有客户端
						allOut[i].println(host+"说:"+message);
				}
				}
			} catch (Exception e) {
				
			}finally {
				//这里处理客户端断开连接后的操作
				synchronized(allOut) {
				//将该客户端的输出流从allOut中删除
					for(int i=0;i<allOut.length;i++) {
						if(allOut[i]==pw) {
							allOut[i]=allOut[allOut.length-1];
							allOut = Arrays.copyOf(allOut, allOut.length-1);
							break;
						}
					}
				}
				System.out.println(host+"下线了,当前在线人数:"+allOut.length);
				try {
					socket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	};
}

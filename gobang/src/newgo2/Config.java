package newgo2;

import java.util.HashMap;

/**
 * 写了一个接口，其中主要是关于棋盘内棋子，网格等尺寸的参数的保存，一般也在此处修改参数
 * @author JAVA
 *
 */
public interface Config {

	public static final int X0=192;    //棋盘左上角坐标
    public static final int Y0=97;      
    public static final int SIZE=30;	//格子宽
    public static final int LINE=15;	//行列数
	  public static final int CHESS_SIZE=28;//棋子的大小
	  //AI进攻     0：空    1：黑子    2：白子    3：边界
	  public static final int AI_A_1=20000;	   //  AI黑：11110  AI白：22220
	  public static final int AI_A_2=18000;	   //  AI黑：11112  AI白：22221
	  public static final int AI_A_3=17000;	   //  AI黑：11113  AI白：22223
	  public static final int AI_A_4=10000;	   //  AI黑：1110   AI白：2220
	  public static final int AI_A_5=650;	   //  AI黑：1112   AI白：2221
	  public static final int AI_A_6=600;	   //  AI黑：1113   AI白：2223
	  public static final int AI_A_7=400;	   //  AI黑：110    AI白：220
	  public static final int AI_A_8=270;	   //  AI黑：112    AI白：221
	  public static final int AI_A_9=250;	   //  AI黑：113    AI白：223
	  public static final int AI_A_10=200;       //  AI黑：10     AI白：20
	  public static final int AI_A_11=120;	   //  AI黑：12     AI白：21
	  public static final int AI_A_12=100;	   //  AI黑：13     AI白：23
	  public static final int AI_A_13=40;       //  AI黑：2     AI白：1
    //AI防守    
	  public static final int AI_G_1=11000;	   //  AI黑：22220  AI白：11110
	  public static final int AI_G_2=16000;	   //  AI黑：22221  AI白：11112
	  public static final int AI_G_3=15000;	   //  AI黑：22223  AI白：11113
	  public static final int AI_G_4=5000;	   //  AI黑：2220   AI白：1110
	  public static final int AI_G_5=600;	   //  AI黑：2221   AI白：1112
	  public static final int AI_G_6=550;	   //  AI黑：2223   AI白：1113
	  public static final int AI_G_7=500;	   //  AI黑：220    AI白：110
	  public static final int AI_G_8=220;	   //  AI黑：221    AI白：112
	  public static final int AI_G_9=200;	   //  AI黑：223    AI白：113
	  public static final int AI_G_10=100;       //  AI黑：20     AI白：10
	  public static final int AI_G_11=40;	   //  AI黑：21     AI白：12
	  public static final int AI_G_12=30;	   //  AI黑：23     AI白：13	  
    public static final int AI_G_13=25;       //  AI黑：1    AI白：2  
    public static final int AI_1=20;         //0
    public static final int AI_2=10;         //3

}

package we;

public class Demo {
public static void main(String[] args) {
	
	// 1）基本类型和包装类型
	int a = 100;
	Integer b = 100;
	System.out.println(a == b);
	
	// 2）两个包装类型
	Integer c = 100;
	Integer d = 100;
	System.out.println(c == d);
	
	// 3）
	c = 200;
	d = 200;
	System.out.println(c==d);
}
}

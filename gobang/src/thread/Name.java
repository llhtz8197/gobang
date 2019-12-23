package thread;

public class Name {
	
	public String name(String host) {
		if(host.equals("176.148.23.175"))
			return "ÖÜÁÖº£";
		if(host.equals("176.148.23.21"))
			return "ÉòÔÆÅô"; 
		if(host.equals("176.148.23.22"))
			return "¶­ºÆ";
		if(host.equals("176.148.23.243"))
			return "ÍõÎÄ²©";
		if(host.equals("176.148.23.244"))
			return "ÑîĞŞÃñ";
		if(host.equals("176.148.23.245"))
			return "ñûÏşŞ±";
		if(host.equals("176.148.23.31"))
			return "³Â¶°»Í";
		if(host.equals("127.0.0.1"))
			return "¸¨Öú";
		if(host.equals("176.148.23.30"))
			return "ÀÍÓĞ¼ó";
		else return host;
	}
}

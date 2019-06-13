package Protobuf_IO;

public class Data {

	/**
	 * day = 1;
	 * server = 1;
	 * year = 1;
	 * time_stamp = 1;
	 * time_stamp2 = 1;
	 * 
	 * 111 111 111 111 111  
	 */
	int day;
	int server;
	int year;
	long time_stamp;
	long time_stamp2;
	

	public Data(int day,int server,int year,int time_stamp,int time_stamp2) {
		this.day = day;
		this.server = server;
		this.year = year;
		this.time_stamp = time_stamp;
		this.time_stamp2 = time_stamp2;
		
	}
	
	
	public Data() {
		
	}
	public void show() {
		System.out.println("day:"+day);
		System.out.println("server:"+server);
		System.out.println("year:"+year);
		System.out.println("time_stamp:"+time_stamp);
		System.out.println("time_stamp2:"+time_stamp2);
	}
	public void setValue(Object value , int index) {
		switch (index) {
		case 1:
			day = (int) value;
			break;
		case 2:
			server = (int) value;
			break;
		case 3:
			year = (int) value;
			break;
		case 4:
			time_stamp =  (long) value;
			break;
		case 5:
			time_stamp2 =  (long) value;
			break;	
		}
	}
	
}//byte1  char2   int4           一个byte8个比特           


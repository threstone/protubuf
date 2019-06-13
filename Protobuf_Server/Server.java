package Protobuf_Server;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Protobuf.Student.student_data;
import Protobuf_IO.Data;

public class Server {

	private ServerSocket serverSocket;
	private DataInputStream dataInputStream;

	public void startServer() {
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(10003);
			socket = serverSocket.accept();
			System.out.println("con");
			dataInputStream = new DataInputStream(socket.getInputStream());
			
			GetMessageFromClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private void GetMessageFromClient() {

		try {
			// 获取消息的长度
			int length = dataInputStream.readInt();
			
			System.out.println("length "+length);
			// 获取消息
			byte[] body = new byte[length];

			dataInputStream.read(body);
			for(int i = 0 ;i<body.length ; i++) {
				System.out.print(body[i]+"\t");
			}
			getData(body);
//			GET / HTTP/1.1
//			System.out.println("客户端说：" + message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void getData(byte[] b) {
		
		System.out.println("===== 使用gps 反序列化生成对象开始 =====");
		Student.student_data stu = null;
		
		try {
			stu = Student.student_data.parseFrom(b);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("age:"+stu.getAge());
		System.out.println("contry:"+stu.getContry());
		System.out.println("sex:"+stu.getSex());
		System.out.println("name:"+stu.getName());
		System.out.println("grade:"+stu.getGrade());

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server server = new Server();
		server.startServer();
//		//10      6       230     157     142     231     163     138     16      20      34      6       228     184     173             229     155     189     40      1
//		//10	6	-26	-99	-114	-25	-93	-118	16	20	34	6	-28	-72	-83	-27	-101	-67	40	1	
//		byte[] a = new byte[] {10,6,-26,-99,-114,-25,-93,-118,16,20,34,6,-28,-72,-83,-27,-101,-67,40,1};
//		getData(a);
	}

}

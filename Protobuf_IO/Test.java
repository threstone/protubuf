package Protobuf_IO;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;

import jdk.jfr.Unsigned;

public class Test {
	private static ByteBuffer buffer = ByteBuffer.allocate(8);

	public static void main(String[] args) {

		Data data = new Data(257, 1, 2, 3, 5);
//		byte[] a = intToByteArray(0);
//		for (int i = 0; i < a.length; i++) {
//			System.out.print(a[i]);
//		}
		
		
//		 write("C:\\Users\\79032\\Desktop\\新建文件夹\\a.bin", data);
//		 Data aData = read("C:\\Users\\79032\\Desktop\\新建文件夹\\a.bin");
		
		
		
		
		// aData.show();
	}

	public static Data read(String filePath) {
		Data data = new Data();
		try {
			File target = new File(filePath);
			byte[] a = new byte[(int) target.length()];
			FileInputStream fis = new FileInputStream(target);
			BufferedInputStream bis = new BufferedInputStream(fis);
			bis.read(a);
			data = getData(a);
			fis.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return data;
	}

	public static Data getData(byte[] b) {
		Data data = new Data();
		for (int i = 0; i < b.length; i++) {
			int length = b[i] >> 3;
			byte num = b[i];
			b[i] = (byte) (b[i] >> 3);
			b[i] = (byte) (b[i] << 3);
			int index = num - b[i];
			byte[] valueArray = new byte[length];
			long value = 0;
			for (int z = 0; z < length; z++) {
				valueArray[z] = b[i + z + 1];
			}
			if (length > 4) {
				value = bytesToLong(valueArray);
				System.out.println("value:" + value + "\nindex:" + index);
				// data.setValue(value, index);

			} else {
				value = byteArrayToInt(valueArray);
				// data.setValue(value, index);
				System.out.println("value:" + value + "\tindex:" + index);
			}
			i += length;
		}

		return data;
	}

	public static int byteArrayToInt(byte[] b) {

		if (b.length < 4) {
			int length = b.length;
			byte[] temp = new byte[4];
			for (int i = 0, k = 0; i < 4; i++) {
				if (i + length >= 4) {
					temp[i] = b[k];
					k++;
				}
			}
			b = temp;
		}

		return b[3] & 0xFF | (b[2] & 0xFF) << 8 | (b[1] & 0xFF) << 16 | (b[0] & 0xFF) << 24;
	}

	public static long bytesToLong(byte[] b) {
		if (b.length < 8) {
			int length = b.length;
			byte[] temp = new byte[8];
			for (int i = 0, k = 0; i < 8; i++) {
				if (i + length >= 8) {
					temp[i] = b[k];
					k++;
				}
			}
			b = temp;
		}

		buffer.put(b, 0, b.length);
		buffer.flip();// need flip
		return buffer.getLong();
	}

	public static byte[] intToByteArray(int a) {
		return new byte[] { (byte) ((a >> 24) & 0xFF), (byte) ((a >> 16) & 0xFF), (byte) ((a >> 8) & 0xFF),
				(byte) (a & 0xFF) };
	}

	public static byte[] longToBytes(long x) {
		buffer.putLong(0, x);
		return buffer.array();
	}

	public static void write(String filePath, Data data) {
		File target = new File(filePath);
		if (target.exists() && target.isFile()) {
			boolean flag = target.delete();
		}
		try {
			if (target.createNewFile()) {

				FileOutputStream fos = new FileOutputStream(target);

				byte[] b1 = checkByteLength(intToByteArray(data.day));
				if (b1 != null) {
					b1 = markByteArray(b1, 1);
					fos.write(b1);
				}
				byte[] b2 = checkByteLength(intToByteArray(data.server));
				if (b2 != null) {
					b2 = markByteArray(b2, 2);
					fos.write(b2);
				}
				

				byte[] b3 = checkByteLength(intToByteArray(data.year));
				if (b3 != null) {
					b3 = markByteArray(b3, 3);
					fos.write(b3);
				}
				

				byte[] b4 = checkByteLength(longToBytes(data.time_stamp));
				if (b4 != null) {
					b4 = markByteArray(b4, 4);
					fos.write(b4);
				}
				

				byte[] b5 = checkByteLength(longToBytes(data.time_stamp2));
				if (b5 != null) {
					b5 = markByteArray(b5, 5);
					fos.write(b5);
				}
				
				fos.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static byte[] markByteArray(byte[] b, int index) {
		int length = b.length;
		byte mark = (byte) length;
		mark = (byte) (mark << 3);
		mark = (byte) (mark | index);
		byte[] by = new byte[length + 1];
		by[0] = mark;
		for (int i = 1; i < by.length; i++) {
			by[i] = b[i - 1];
		}
		return by;
	}

	public static byte[] checkByteLength(byte[] b) {
		int index = 0;// [0][1][1][1]
		int length = b.length;
		for (int i = 0; i < length; i++) {
			if (b[i] != 0) {
				index = i;
				break;
			}
		}

		if (index > 0) {
			byte[] temp = new byte[length - index];
			for (int i = 0; i < temp.length; i++) {
				temp[i] = b[index];
				index++;
			}
			b = temp;
		}
		return b;

	}

}

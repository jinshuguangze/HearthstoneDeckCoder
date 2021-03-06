package com.hearthstoneDeckCoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class CodeAnalyzer {
	/**
	 * 代码分析者
	 */
	public static int readVarInt(DataInputStream dis) throws IOException {
		/**
		 * 将Varint转化为整数 原作者：http://wiki.vg/Data_types
		 */
		int numRead = 0;
		int result = 0;
		byte read;
		do {
			read = dis.readByte();
			int value = (read & 0b01111111);
			result |= (value << (7 * numRead));

			numRead++;
			if (numRead > 5) {
				throw new RuntimeException("VarInt is too big");
			}
		} while ((read & 0b10000000) != 0);

		return result;
	}

	public static void writeVarInt(DataOutputStream dos, int value) throws IOException {
		/**
		 * 将整数转化为Varint格式 原作者：http://wiki.vg/Data_types
		 */
		do {
			byte temp = (byte) (value & 0b01111111);
			value >>>= 7;
			if (value != 0) {
				temp |= 0b10000000;
			}
			dos.writeByte(temp);
		} while (value != 0);
	}

	public static List<Integer> code2list(String code) throws IOException {
		/**
		 * 将卡组代码转化为ID列表
		 */
		byte[] byteArray = Base64.getDecoder().decode(code);
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(byteArray));
		List<Integer> list = new ArrayList<>();
		for (int i = 0;; i++) {
			int j = CodeAnalyzer.readVarInt(dis);
			list.add(j);
			if (j == 0 && i != 0)
				break;
		}
		dis.close();
		return list;
	}

	public static String list2code(List<Integer> list) throws IOException {
		/**
		 * 将ID列表转化为卡组代码
		 */
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		for (Integer integer : list) {
			CodeAnalyzer.writeVarInt(dos, integer);
		}
		String string = Base64.getEncoder().encodeToString(baos.toByteArray());
		baos.close();
		dos.close();
		return string;
	}
}

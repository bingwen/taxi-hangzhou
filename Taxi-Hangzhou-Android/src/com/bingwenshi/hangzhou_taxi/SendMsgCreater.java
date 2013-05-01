package com.bingwenshi.hangzhou_taxi;

import java.util.Calendar;
import java.util.Date;

public class SendMsgCreater {

	public byte[] imsi = new byte[18];

	public byte[] getLoginBytes(String username, String password) {
		int func = 1;
		byte[] usernameByte = ParseMethod.string2bytes(username);
		byte[] passwordByte = ParseMethod.string2bytes(password);
		
		byte[] appCode = {0x01,0x02,0x00,0x01,0x01};
		byte[] version = {0x02,0x00,0x01,0x02,0x00,0x05,0x00,0x01,0x00,0x00,0x00,0x01};

		byte[] body = null;
		body = ParseMethod.addBytes(body, usernameByte);
		body = ParseMethod.addBytes(body, passwordByte);
		body = ParseMethod.addBytes(body, appCode);
		body = ParseMethod.addBytes(body, version);

		return getByteWithHead(func, body);

	}

	public byte[] getLogoutBytes() {
		int func = 2;

		byte[] body = {};

		return getByteWithHead(func, body);

	}

	public byte[] getRegisterBytes(String username, String password,
			String phoneNum, String carNum, String serviceNum, String personNum) {
		int func = 3;
		byte[] usernameByte = ParseMethod.string2bytes(username);
		byte[] passwordByte = ParseMethod.string2bytes(password);
		byte[] phonenumByte = ParseMethod.string2bytes(phoneNum);
		byte[] carNumByte = ParseMethod.string2bytes(carNum);
		byte[] serviceNumByte = ParseMethod.string2bytes(serviceNum);
		byte[] personNumByte = ParseMethod.string2bytes(personNum);

		byte[] body  = usernameByte;
		body = ParseMethod.addBytes(body, passwordByte);
		body = ParseMethod.addBytes(body, phonenumByte);
		body = ParseMethod.addBytes(body, carNumByte);
		body = ParseMethod.addBytes(body, serviceNumByte);
		body = ParseMethod.addBytes(body, personNumByte);

		return getByteWithHead(func, body);

	}
	public byte[] getHistoryBytes(Calendar startDate, Calendar endDate, String carNum) {
		int func = 4;

		Date startDateDate = startDate.getTime();
		Date endDateDate = endDate.getTime();
		
		byte[] startByte = ParseMethod.dateToAsciiByte(startDateDate);
		byte[] endByte = ParseMethod.dateToAsciiByte(endDateDate);
		byte[] carNumByte = ParseMethod.string2bytes(carNum);
		
		byte[] body = startByte;
		body = ParseMethod.addBytes(body, endByte);
		body = ParseMethod.addBytes(body, carNumByte);

		return getByteWithHead(func, body);

	}

	public byte[] getCurrentBytes(int num, String carNum) {
		int func = 5;

		byte[] body = ParseMethod.shortToByte((short)num);
		byte[] carNumByte = ParseMethod.string2bytes(carNum);
		body = ParseMethod.addBytes(body, carNumByte);
		return getByteWithHead(func, body);

	}
	//附近的乘客
	public byte[] getCustomBytes(int num,double Longitude,double Latitude) {
		int func = 6;
		
		byte[] body =ParseMethod.shortToByte((short)10);
		body = ParseMethod.addBytes(body, ParseMethod.shortToByte((short)num));
		body = ParseMethod.addBytes(body, ParseMethod.doubleToByte(Longitude));
		body = ParseMethod.addBytes(body, ParseMethod.doubleToByte(Latitude));
		

		return getByteWithHead(func, body);

	}


	public byte[] getByteWithHead(int func, byte[] content) {

		short length = (short) (content.length + 27);
		
		byte[] byteArray = { (byte) 0xFF, (byte) 0x27 };
		byteArray = ParseMethod.addBytes(byteArray, ParseMethod.shortToByte(length));//长度  2字节
		byteArray = ParseMethod.addBytes(byteArray, (byte)func);//功能码  1字节
		byteArray = ParseMethod.addBytes(byteArray, imsi);//imsi 18字节
		byteArray = ParseMethod.addBytes(byteArray, (byte)2);//设备类型 1字节
		byteArray = ParseMethod.addBytes(byteArray, ParseMethod.shortToByte((short) 1));// 数据条数  2字节
		byteArray = ParseMethod.addBytes(byteArray, ParseMethod.shortToByte((short) 1));// 序号 2字节
		byteArray = ParseMethod.addBytes(byteArray, content);  //内容
		byte chekByte = (byte) 0x00;
		for (int i = 2; i < 26; i++) {
			chekByte += byteArray[i];
		}
		byteArray = ParseMethod.addBytes(byteArray, chekByte);//校验
		byteArray = ParseMethod.addBytes(byteArray,
				ParseMethod.shortToByte((short) 0xFF28)); //包尾

		return byteArray;
	}

}

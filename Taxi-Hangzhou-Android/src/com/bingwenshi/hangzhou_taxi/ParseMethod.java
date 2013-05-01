package com.bingwenshi.hangzhou_taxi;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.bingwenshi.buffer.IoBuffer;

public class ParseMethod {
	
	
/*	public static String getGBKString(ByteBuffer in,int length){
		byte[] tmp=new byte[length];
		boolean isend = false;
		for(int i=0;i<length;i++){
			byte temp = in.get();
			if(temp == 0){
				isend = true;
			}
			if(!isend)
				tmp[i]=temp;
		}
		try {
			return new String(tmp,"GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			return new String("Error");
		}
	}
	public static String getGBKShortString(byte[] data,int startIndex){
		short length = byteToShort(data, startIndex);
		byte[] tmp=new byte[length];
		
		boolean isend = false;
		for(int i=0;i<length;i++){
			byte temp = data[startIndex+4+i];
			if(temp == 0){
				isend = true;
			}
			if(!isend)
				tmp[i]=temp;
		}
		try {
			return new String(tmp,"GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			return new String("Error");
		}
	}
	public static String getGBKLongString(byte[] data,int startIndex){
		int length = byteToInt(data, startIndex);
		byte[] tmp=new byte[length];
		
		boolean isend = false;
		for(int i=0;i<length;i++){
			byte temp = data[startIndex+4+i];
			if(temp == 0){
				isend = true;
			}
			if(!isend)
				tmp[i]=temp;
		}
		try {
			return new String(tmp,"GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			return new String("Error");
		}
	}
	public static String getGBKFixString(byte[] data,int startIndex,int length){

		byte[] tmp=new byte[length];
		
		boolean isend = false;
		for(int i=0;i<length;i++){
			byte temp = data[startIndex+i];
			if(temp == 0){
				isend = true;
			}
			if(!isend)
				tmp[i]=temp;
		}
		try {
			return new String(tmp,"GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			return new String("Error");
		}
	}
	
	public static String getGBKString(String oldStr){
		try {
			return new String(oldStr.getBytes(), "GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			return new String("Error");
		}	
	}
	public static byte[] setGBKChangebleString(String oldStr){
		byte[] builder=null;
		try {
			byte[] newb = oldStr.getBytes("GBK");
			builder = ParseMethod.shortToByte((short)newb.length);
			builder = ParseMethod.addBytes(builder,newb);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return builder;
	}
	public static byte[] setGBKChangebleLongString(String oldStr){
		byte[] builder=null;
		String newStr=null;
		try {
			newStr = new String(oldStr.getBytes("utf-8"), "gbk");
			byte[] newb = oldStr.getBytes("GBK");
			builder = ParseMethod.intToByte(newb.length);
			builder = ParseMethod.addBytes(builder,newb);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		builder=ParseMethod.addBytes(builder,newStr.getBytes());
		return builder;
	}
	public static byte[] setGBKFixString(short length,String str){
		byte[] builder=new byte[length];
		byte[] gbkstr;
		try {
			gbkstr=str.getBytes("GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			gbkstr=null;
			e.printStackTrace();
		}
		for(int i=0;i<gbkstr.length&&i<length;i++)
			builder[i]=gbkstr[i];
		return builder;
	}
	
	*/
	public static byte[] string2bytes(String str) {
		try {
			byte[] strbytes= str.getBytes("GBK");
			byte[] buffer = new byte[str.length()+1];
			System.arraycopy(strbytes, 0, buffer, 0, str.length());
			buffer[str.length()] = 0;
			return buffer;
		} catch (Exception e) {
			// TODO: handle exception
		}
		byte[] buffer = {0};
		return buffer;
	}	
	public static String byte2String(byte[] data,int index) {
		String str;
		try {
			IoBuffer buffer = IoBuffer.allocate(512);
			byte[] temp = new byte[data.length-index];
			System.arraycopy(data, index, temp, 0, temp.length);
			buffer.setAutoExpand(true);
			buffer.put(temp);
			buffer.flip();
			CharsetDecoder cha =Charset.forName("GBK").newDecoder();
			str = buffer.getString(cha);
		} catch (Exception e) {
			// TODO: handle exception
			str = "";
		}
		return str;
	}
	
	public static byte[] doubleToByte(double d){
		byte[] b=new byte[8];
		long l=Double.doubleToLongBits(d);
		for(int i=b.length-1;i>=0;i--){
			 b[i]=new Long(l).byteValue();
			 l=l>>8;
		}
		return b;
	}
	public static double byteToDouble(byte[] data,int index){
		long num = 0;
		for (int i=0;i < 8;i++){
			int temp = data[index+i] & 0xff;
			num = num<<8;
			num += temp;
		}
		return Double.longBitsToDouble(num);
	}
	public static byte[] shortToByte(short number) {        
		int temp = number;        
		byte[] b = new byte[2];        
		for (int i = b.length-1; i >=0 ; i--) {            
			b[i] = new Integer(temp & 0xff).byteValue();         
			temp = temp >> 8;         
		}        
		return b;    
	}
	public static short byteToShort(byte[] data,int index) {        
		short num = 0;
		for (int i=0;i < 2;i++){
			short temp = (short) (data[index+i] & 0xff);
			num = (short) (num<<8);
			num += temp;
		}
		return num;
	}
	public static byte[] intToByte(int number) {
		int temp = number;
		byte[] b=new byte[4];
		for (int i=b.length-1;i>-1;i--){
			b[i] = new Integer(temp&0xff).byteValue(); 
			temp = temp >> 8; 
		}
		return b;
	}
	public static int byteToInt(byte[] data,int index) {
		
		int num = 0;
		for (int i=0;i < 4;i++){
			int temp = data[index+i] & 0xff;
			num = num<<8;
			num += temp;
		}
		return num;
	}
	public static byte[] addBytes(byte[] src1, byte[] src2) { 
		if (src1 == null) {
			byte[] dest = new byte[src2.length];   
	        System.arraycopy(src2, 0, dest, 0, src2.length);   
	        return dest; 
		}
		else{
			byte[] dest = new byte[src1.length + src2.length];   
	        System.arraycopy(src1, 0, dest, 0, src1.length);   
	        System.arraycopy(src2, 0, dest, src1.length, src2.length);   
	        return dest; 
		}
          
    }   
	public static byte[] addBytes(byte[] src1, byte src2) {   
		if (src1 == null) {
			 byte[] dest = new byte[1];    
		        dest[1]=src2;
		        return dest; 
		}
		else{
			 byte[] dest = new byte[src1.length + 1];   
		        System.arraycopy(src1, 0, dest, 0, src1.length);   
		        dest[src1.length]=src2;
		        return dest; 
		}
         
    } 
	
	public static String stringToAscii(String value)
	{
		StringBuffer sbu = new StringBuffer();
		char[] chars = value.toCharArray(); 
		for (int i = 0; i < chars.length; i++) {
			if(i != chars.length - 1)
			{
				sbu.append((int)chars[i]).append(",");
			}
			else {
				sbu.append((int)chars[i]);
			}
		}
		return sbu.toString();
	}
	public static String asciiToString(String value)
	{
		StringBuffer sbu = new StringBuffer();
		String[] chars = value.split(",");
		for (int i = 0; i < chars.length; i++) {
			sbu.append((char) Integer.parseInt(chars[i]));
		}
		return sbu.toString();
	}
	
	public static byte[] dateToAsciiByte(Date date){

		
		String pattern = "yyyyMMddHHmmss";
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		String startdateString = dateFormat.format(date);
		
		return str2Bcd(stringToAscii(startdateString));
		
		
	}
	
	public static Date asciiByteToDate(byte[] data,int index){
		byte[] canlendarByte = new byte[7];
		System.arraycopy(canlendarByte, 0, data, index, 7);
		
		String canlendarString = asciiToString(bcd2Str(canlendarByte));
		
		String pattern = "yyyyMMddHHmmss";
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		
		try {
			Date canlendarDate = dateFormat.parse(canlendarString);
			return canlendarDate;
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}

	/**
	 * @函数功能: BCD码转为10进制串(阿拉伯数据)
	 * @输入参数: BCD码
	 * @输出结果: 10进制串
	 */
	public static String bcd2Str(byte[] b) {
		if (b.length == 0)
			return "";
		StringBuffer sb = new StringBuffer(b.length * 2);

		for (int i = 0; i < b.length; i++) {
			int iTemp = 0;
			int high = ((iTemp = ((b[i] & 0xff) >> 4)) > 9 ? (iTemp + '0' + 7)
					: (iTemp + '0'));
			sb.append((char) high);
			iTemp = 0;
			int low = ((iTemp = (b[i] & 0x0f)) > 9 ? (iTemp + '0' + 7)
					: (iTemp + '0'));
			sb.append((char) low);
		}
		/*
		 * for(int i=0;i<bytes.length;i++){ temp.append((byte)((bytes[i]&
		 * 0xf0)>>>4)); temp.append((byte)(bytes[i]& 0x0f)); }
		 */
		return sb.toString();// .substring(0,1).equalsIgnoreCase("0")?sb.toString().substring(1):sb.toString();
	}

	/**
	 * @函数功能: 10进制串转为BCD码
	 * @输入参数: 10进制串
	 * @输出结果: BCD码
	 */
	public static byte[] str2Bcd(String asc) {
		int len = asc.length();
		int mod = len % 2;

		if (mod != 0) {
			asc = "0" + asc;
			len = asc.length();
		}

		byte abt[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}

		byte bbt[] = new byte[len];
		abt = asc.getBytes();
		int j, k;

		for (int p = 0; p < asc.length() / 2; p++) {
			if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
				j = abt[2 * p] - '0';
			} else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
				j = abt[2 * p] - 'a' + 0x0a;
			} else {
				j = abt[2 * p] - 'A' + 0x0a;
			}

			if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
				k = abt[2 * p + 1] - '0';
			} else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
				k = abt[2 * p + 1] - 'a' + 0x0a;
			} else {
				k = abt[2 * p + 1] - 'A' + 0x0a;
			}

			int a = (j << 4) + k;
			byte b = (byte) a;
			bbt[p] = b;
		}
		return bbt;
	}
}

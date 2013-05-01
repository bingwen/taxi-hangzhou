package com.bingwenshi.hangzhou_taxi;

import java.util.Date;
import java.util.HashMap;

import com.baidu.platform.comapi.basestruct.GeoPoint;

import android.R.bool;

public class GotMsgDecoder {
	public int msgState = 0;
	public static final int MSG_STATE_START = 0;// �ȴ�
	public static final int MSG_STATE_SUCCESS = 1;// �ɹ�
	public static final int MSG_STATE_FAILED = 2;// ʧ��
	public static final int MSG_STATE_ERROR = 3;// ��Ϣ��ʽ����
	public static final int MSG_STATE_WAIT = 4;// ��Ϣ��ʽ����


	int bodyLength = 0;
	int func = 0;
	int device_type = 0;
	byte[] imsi = null;
	
	public HashMap<String, Object> resultMap = new HashMap<String, Object>();
	
	public boolean decodeSuccess() {
		if (msgState == MSG_STATE_SUCCESS) {
			return true;
		}
		return false;
	}	
	public void handleData(byte[] dataArray) {
		try {
			int arraryLength = dataArray.length;
			//����ͷ ��β
			if (dataArray[0] != -1 || dataArray[1] != 39
					|| dataArray[arraryLength - 2] != -1
					|| dataArray[arraryLength - 1] != 40) {
				msgState = MSG_STATE_ERROR;
				return;
			}
			int bodyLength = ParseMethod.byteToShort(dataArray, 2)-27;//�����ݳ���
			func = dataArray[4]&0xff;//���ܺ�  1�ֽ�

			imsi = new byte[18];
			System.arraycopy(dataArray, 5, imsi, 0, 18);

			device_type = dataArray[23]&0xff;//�豸���� 1�ֽ�

			int dataNum = ParseMethod.byteToShort(dataArray, 24);
			int dataIndex = ParseMethod.byteToShort(dataArray, 26);
			
			byte[] result = new byte[bodyLength];
			System.arraycopy(dataArray, 28, result, 0, bodyLength);
			handleDataBody(result);
			
		} catch (Exception e) {
			// TODO: handle exception
			msgState = MSG_STATE_ERROR;
			return;
		}
			
	}

	public void handleDataBody(byte[] dataArray) {

		switch (func) {
		case 0x01:
			handleLoginResult(dataArray);
			break;
		case 0x02:
			handleLogoutResult(dataArray);
			break;
		case 0x03:
			handleRegisterResult(dataArray);
			break;
		case 0x04:
			handleHistoryResult(dataArray);
			break;
		case 0x05:
			handleRecentResult(dataArray);
			break;
		case 0x06:
			handleCustomResult(dataArray);
			break;
		case 0x07:
			handleScoreResult(dataArray);
			break;
		case 0x08:
			handleNoticeResult(dataArray);
			break;
		case 0x09:
			handleCardResult(dataArray);
			break;
		case 0x10:
			handleOperationResult(dataArray);
			break;
		case 0x11:
			handleQualityResult(dataArray);
			break;
		case 0x12:
			handleBreaksResult(dataArray);
		case 0x13:
			handleComplanResult(dataArray);
			break;
		case 0x14:
			handleQuestionResult(dataArray);
		default:
			break;
		}

		msgState = MSG_STATE_SUCCESS;
	}

	public void handleLoginResult(byte[] dataArray) {
		// ��½
		try {
			int index = 0;
			int resultState = dataArray[0]&0xff;
			index+=1;
			String username = ParseMethod.byte2String(dataArray, index);
			index += username.getBytes("GBK").length+1;
			String phoneString = ParseMethod.byte2String(dataArray, index);
			index+= phoneString.getBytes("GBK").length+1;
			String carNumString = ParseMethod.byte2String(dataArray,index);
			index+= carNumString.getBytes("GBK").length+1;
			String serverString =ParseMethod.byte2String(dataArray,index);
			index+= serverString.getBytes("GBK").length+1;
			int version = dataArray[dataArray.length-2]&0xff;
			int test = dataArray[dataArray.length-1]&0xff;
			
			resultMap.clear();
			resultMap.put("msgMode", MyApplication.MSG_MODE_LOGIN);
			resultMap.put("state", resultState);
			resultMap.put("username", username);
			resultMap.put("phone", phoneString);
			resultMap.put("carnum", carNumString);
			resultMap.put("servicenum", serverString);
			resultMap.put("version", version);
			resultMap.put("test", test);
			
			msgState = MSG_STATE_SUCCESS;
		} catch (Exception e) {
			// TODO: handle exception
			msgState = MSG_STATE_ERROR;
		}	
	}

	public void handleLogoutResult(byte[] dataArray) {
		// �ǳ�
		try {
			int resultState = dataArray[0];
			
			resultMap.clear();
			resultMap.put("state", resultState);
			resultMap.put("msgMode", MyApplication.MSG_MODE_LOGOUT);
			msgState =MSG_STATE_ERROR;
		} catch (Exception e) {
			// TODO: handle exception
			msgState = MSG_STATE_ERROR;
		}
	}

	public void handleRegisterResult(byte[] dataArray) {
		// ע��
		try {
			int resultState = dataArray[0];
			
			resultMap.clear();
			resultMap.put("state", resultState);
			resultMap.put("msgMode", MyApplication.MSG_MODE_REGISTER);
			msgState = MSG_STATE_SUCCESS;
		} catch (Exception e) {
			// TODO: handle exception
			msgState = MSG_STATE_ERROR;
		}
	}

	public void handleHistoryResult(byte[] dataArray) {
		// ��ʷ��¼

		try {
			int index = 0;
			int resultState = dataArray[index];
			index += 1;
			String num = ParseMethod.byte2String(dataArray, index);
			index += num.length()*2+1;
			String address = ParseMethod.byte2String(dataArray, index);
			index += address.length()*2+1;
			String customName = ParseMethod.byte2String(dataArray, index);
			index += customName.length()*2+1;
			int gender = dataArray[index];
			index += 1;
			String phone = ParseMethod.byte2String(dataArray, index);
			index += phone.length()*2+1;
			String addressDescribe = ParseMethod.byte2String(dataArray, index);
			index += addressDescribe.length()*2+1;
			int schedulState = dataArray[index];
			index += 1;
			Date sendCarDate = ParseMethod.asciiByteToDate(dataArray, index);
			index += 7;
			Date orderCarDate = ParseMethod.asciiByteToDate(dataArray, index);
			index += 7;
			double Longitude = ParseMethod.byteToDouble(dataArray, index);
			index += 8;
			double Latitude = ParseMethod.byteToDouble(dataArray, index);
			index += 8;
			String unLoadAddress = ParseMethod.byte2String(dataArray, index);
			index += addressDescribe.length()*2+1;
			Date useCarTime = ParseMethod.asciiByteToDate(dataArray, index);
			index += 7;
			
			StringBuilder sb = new StringBuilder();
			sb.append("ҵ�񵥺�:").append(num).append("\n");
			sb.append("����:").append(customName).append("\n");
			sb.append("�绰��").append(phone).append("\n");
			sb.append("�ϳ��ص�:").append(address).append("\n");
			sb.append("�ϳ�������:").append(addressDescribe).append("\n");
			sb.append("�³��ص�:").append(unLoadAddress).append("\n");
			sb.append("�ɳ�ʱ��:").append(sendCarDate).append("\n");
			sb.append("�µ�ʱ��:").append(orderCarDate).append("\n");
			switch (schedulState) {
			case 0:
				sb.append("����״̬:�ɹ�");
				break;
			case 1:
				sb.append("����״̬:ʧ��");
				break;
			case 2:
				sb.append("����״̬:ȡ��");
				break;
			case 3:
				sb.append("����״̬:�ɳ���");
				break;

			default:
				sb.append("����״̬:δ֪");
				break;
			}
			
			resultMap.clear();
			resultMap.put("msgMode", MyApplication.MSG_MODE_NEARBY);
			resultMap.put("msg", sb.toString());
			resultMap.put("phone", phone);
			GeoPoint point =new GeoPoint((int)Longitude,(int)Latitude);
			resultMap.put("point", point);

			msgState = MSG_STATE_SUCCESS;
		} catch (Exception e) {
			// TODO: handle exception
			msgState = MSG_STATE_ERROR;
		}
	}

	public void handleRecentResult(byte[] dataArray) {
		// �����¼

		handleHistoryResult(dataArray);
	}

	public void handleCustomResult(byte[] dataArray) {
		// �����ĳ˿�

		try {
			int index = 0;
			int serialNum =  ParseMethod.byteToShort(dataArray, index);
			index += 2;
			String address = ParseMethod.byte2String(dataArray, index);
			index += address.length()*2+1;
			String customName = ParseMethod.byte2String(dataArray, index);
			index += customName.length()*2+1;
			int gender = dataArray[index];
			index += 1;
			String phone = ParseMethod.byte2String(dataArray, index);
			index += phone.length()*2+1;
			String addressDescribe = ParseMethod.byte2String(dataArray, index);
			index += addressDescribe.length()*2+1;
			
			double Longitude = ParseMethod.byteToDouble(dataArray, index);
			index += 8;
			double Latitude = ParseMethod.byteToDouble(dataArray, index);
			index += 8;
			String unLoadAddress = ParseMethod.byte2String(dataArray, index);
			index += addressDescribe.length()*2+1;
			Date useCarTime = ParseMethod.asciiByteToDate(dataArray, index);
			index += 7;
			
			StringBuilder sb = new StringBuilder();
			sb.append("���:").append(serialNum).append("\n");
			sb.append("����:").append(customName).append("\n");
			sb.append("�绰��").append(phone).append("\n");
			sb.append("�ϳ��ص�:").append(address).append("\n");
			sb.append("�ϳ�������:").append(addressDescribe).append("\n");
			sb.append("�³��ص�:").append(unLoadAddress).append("\n");
			
			resultMap.clear();
			resultMap.put("msgMode", MyApplication.MSG_MODE_NEARBY);
			resultMap.put("msg", sb.toString());
			resultMap.put("phone", phone);
			resultMap.put("Longitude", Longitude);
			resultMap.put("Latitude", Latitude);

			
			msgState = MSG_STATE_SUCCESS;
		} catch (Exception e) {
			// TODO: handle exception
			msgState = MSG_STATE_ERROR;
		}
	}

	public void handleScoreResult(byte[] dataArray) {
		// ����
		try {
			int serialNum = ParseMethod.byteToShort(dataArray, 0);
			int score = ParseMethod.byteToInt(dataArray, 2);
			
			StringBuilder sb = new StringBuilder();
			sb.append("���кţ�").append(serialNum).append("\n").append("���֣�").append(score).append("\n");
			
			resultMap.clear();
			resultMap.put("msgMode", MyApplication.MSG_MODE_SCORE);
			
			resultMap.put("msg", sb.toString());
			
			msgState = MSG_STATE_SUCCESS;
		} catch (Exception e) {
			// TODO: handle exception
			msgState = MSG_STATE_ERROR;
		}	
	}
//������δʵ��
	public void handleQualityResult(byte[] dataArray) {
		// ����
		try {
			int index = 0;
			int serialNum = ParseMethod.byteToShort(dataArray, index);
			index += 2;
			Date sendCarDate = ParseMethod.asciiByteToDate(dataArray, index);
			index += 7;
			String serviceState = ParseMethod.byte2String(dataArray, index);
			
			resultMap.clear();
			resultMap.put("serialNum", serialNum);
			resultMap.put("sendCarDate", sendCarDate);
			resultMap.put("serviceState", serviceState);
			
			msgState = MSG_STATE_SUCCESS;
		} catch (Exception e) {
			// TODO: handle exception
			msgState = MSG_STATE_ERROR;
		}
	}

	public void handleCardResult(byte[] dataArray) {
		// ˢ��

		msgState = MSG_STATE_SUCCESS;
	}

	public void handleOperationResult(byte[] dataArray) {
		// Ӫ��

		msgState = MSG_STATE_SUCCESS;
	}

	public void handleNoticeResult(byte[] dataArray) {
		// ֪ͨ

		msgState = MSG_STATE_SUCCESS;
	}

	public void handleBreaksResult(byte[] dataArray) {
		// Υ��

		msgState = MSG_STATE_SUCCESS;
	}

	public void handleComplanResult(byte[] dataArray) {
		// Ͷ��

		msgState = MSG_STATE_SUCCESS;
	}

	public void handleQuestionResult(byte[] dataArray) {
		// ����

		msgState = MSG_STATE_SUCCESS;
	}
}

package com.fly.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author wjn
 * MD5鍔犲瘑宸ュ叿绫�
 */
public class MD5Utils {

	public static String getPassword(String pwd){
		//鍔犲瘑鍔ㄤ綔锛岃幏鍙栧姞瀵嗙殑瀵硅薄
		try {
			MessageDigest digest = MessageDigest.getInstance("md5");
			//瀵规暟鎹繘琛屽姞瀵�,鍔犲瘑鐨勫姩浣滃凡缁忓畬鎴�
			byte[] bs = digest.digest(pwd.getBytes());
			
			//瀵瑰姞瀵嗗悗鐨勭粨鏋滐紝杩涜浼樺寲锛屽皢鍔犲瘑鍚庣殑缁撴灉锛屽厛杞崲鎴愭鏁帮紝鐒跺悗锛岃浆鎹㈡垚16杩涘埗鏍煎紡
			String password = "";
			for (byte b : bs) {
				//杞崲鎴愭鏁�
				//b绫诲瀷byte   杩涜 & 255 int绫诲瀷鏁版嵁锛岃嚜鍔ㄧ被鍨嬫彁鍗�
				//b锛�1111 1001
				//b杞崲鎴恑nt绫诲瀷涔嬪悗锛�0000 0000 0000 0000 0000 0000 1111 1001锛屾渶楂樹綅鍙樻垚0涔嬪悗杞崲鎴愭鏁�
				int temp = b & 255;
				//杞崲鎴�16杩涘埗鏍煎紡
				String hexString = Integer.toHexString(temp);
				if(temp >=0 && temp < 16){
					password = password +"0"+ hexString;
				}else{
					password = password + hexString;
				}
				//宸ュ叿绫诲姞瀵嗙殑缁撴灉锛�90150983cd24fb0d6963f7d28e17f72
				//宸ュ叿绫诲姞瀵嗙殑缁撴灉锛�900150983cd24fb0d6963f7d28e17f72
				//mysql鍔犲瘑缁撴灉锛� 900150983cd24fb0d6963f7d28e17f72
			}
			
			return password;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		} 
	}
	
	public static void main(String[] args) {
		System.out.println(MD5Utils.getPassword("abc"));
	}
}

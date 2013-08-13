package com.prl.cvto.util;

public class BlindEye {	
	public static byte[] transform(byte[] input, byte[] key) {
		int size = input.length;
		byte[] output = new byte[size];
		
		int index = -1;
		for (int i = 0; i < size; i++) {
			index = (index == key.length - 1) ? 0 : index + 1;
			output[i] = (byte) (input[i] ^ key[index]); 
		}
		return output;
	}
}
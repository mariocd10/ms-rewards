package utils;

import java.util.Random;

public class WordGen {
	private String result;
	private char[] alpha= {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o'
			,'p','q','r','s','t','u','v','w','x','y','z'};
	
	public String generateWord() {

		StringBuilder sb = new StringBuilder();
		int size = random(25)+random(25);
		for(int i=0; i< size; i++) {
			sb.append(getRandomChar());
		}
		result = sb.toString();
		return result;
	}
	
	private char getRandomChar() {
		return alpha[random(alpha.length)];
	}
	
	private int random(int length) {
//		return (int)Math.random()*length;
		return new Random().nextInt(length);
	}
	
}

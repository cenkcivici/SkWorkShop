package com.sk.util.builder;

import java.util.Random;

public final class RandomEnumBuilder {

	private RandomEnumBuilder(){}
	
	public static <T extends Enum<T>> T randomEnum(Class<T> enumClass){
		
		T[] enumConstants = enumClass.getEnumConstants();
		return enumConstants[new Random().nextInt(enumConstants.length)];
	}
}

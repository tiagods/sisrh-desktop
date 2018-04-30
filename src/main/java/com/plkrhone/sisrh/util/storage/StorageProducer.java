package com.plkrhone.sisrh.util.storage;

public class StorageProducer{
	public static Storage newConfig() {
		return new AWSStorage();
	}
	
}

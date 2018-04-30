package com.plkrhone.sisrh.repository.interfaces;

public interface Paginacao {
	int getPageNumber();
	int getPageSize();
	boolean first();
	boolean next();
	boolean previousOrFirst();
	boolean hasPrevious();
	long getOffset();
}

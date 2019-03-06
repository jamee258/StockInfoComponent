package com.info.service;

public interface StockService {

	boolean authenticate(int userId, String password);

	String getStockInfo(String symbol);

}
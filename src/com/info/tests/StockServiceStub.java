package com.info.tests;

import java.util.HashMap;
import java.util.Map;

import com.info.service.StockService;

public class StockServiceStub implements StockService {

	@Override
	public boolean authenticate(int userId, String password) {
		return true;
	}

	@Override
	public String getStockInfo(String symbol) {
		return getStockData().get(symbol);
	}

	private Map<String, String> getStockData() {
		Map<String, String> stockData = new HashMap<String, String>();
		stockData.put("CNA", "CNA,Centrica PLC,123.45,10000000");
		stockData.put("ESL", "ESL,Eddie Stobart Limited,102.00,110000");
		stockData.put("MKS", "MSK,Marks and Spencer,279.50,1000000");

		return stockData;
	}

}
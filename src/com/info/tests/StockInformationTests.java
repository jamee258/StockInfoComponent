package com.info.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.info.domain.StockInformation;
import com.info.service.StockService;

public class StockInformationTests {

	private StockService stockServiceStub;

	@Before
	public void setUp() throws Exception {
		stockServiceStub = new StockServiceStub();
	}

	@Test
	public void testUserIDIsValid() {
		// Arrange
		// userId: valid range = 1 -> 9999
		// password: Alpha-numeric with symbols
		int userId = 2;
		String password = "Pa$$w0rd";
		String stockSymbol = "CNA";
		StockInformation si = new StockInformation(userId, password, stockSymbol, stockServiceStub);

		// Act

		// Assert
		assertTrue(si.isValidUser());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIncorrectlyFormattedStockSymbolThrowsException() {
		// Arrange
		// Only consists of letters, numbers or a combination of both, in either
		// upper / lower case
		int userId = 2;
		String password = "Pa$$w0rd";
		String stockSymbol = "C$1";

		StockInformation si = new StockInformation(userId, password, stockSymbol, stockServiceStub);

		// Act

		// Assert
	}

	@Test
	public void testPasswordIsIncorrectlyFormatted() {
		// Arrange
		// Combination of upper / lower case, numbers and symbols
		// At least 8 characters long
		int userId = 2;
		String password = "hello";
		String stockSymbol = "CNA";

		StockInformation si = new StockInformation(userId, password, stockSymbol, stockServiceStub);

		// Act

		// Assert
		assertFalse(si.isValidPassword());
	}

	@Test
	public void testToStringCreatesFormattedStockInformation() {
		// Arrange
		int id = 2;
		String password = "Pa$$w0rd";
		String stockSymbol = "CNA";
		String expectedFormattedInfo = "Centrica PLC[CNA] 123.45";

		StockInformation si = new StockInformation(id, password, stockSymbol, stockServiceStub);
		String actualFormattedInfo = si.toString();

		// Act

		// Assert
		assertEquals(expectedFormattedInfo, actualFormattedInfo);
	}

	@Test
	public void testMarketCapitalisationIsCalculated() {
		// Arrange
		// Expected Value = 1,234,500,000
		double expectedMarketCapitalisationInMillions = 12;
		int id = 2;
		String password = "Pa$$w0rd";
		String stockSymbol = "CNA";

		StockInformation si = new StockInformation(id, password, stockSymbol, stockServiceStub);
		Double actualMarketCapitalisationInMillions = si.calculateMarketCapitalisation();

		// Act

		// Assert
		assertEquals(expectedMarketCapitalisationInMillions, actualMarketCapitalisationInMillions, 0.1);

	}

	@Test
	public void testGetStockInformationReturns_NoSuchSymbol_ForNonExistentSymbol() {
		// Arrange
		String symbol = "UFO";
		int id = 23;
		String expectedStockInfo = "No Such Symbol";
		String password = "Pa$$w0rd";
		StockInformation si = new StockInformation(id, password, symbol, stockServiceStub);
		String actualStockInfo = si.toString();

		// Act

		// Assert
		assertEquals(expectedStockInfo, actualStockInfo);
	}

}
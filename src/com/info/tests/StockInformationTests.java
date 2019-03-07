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

		// Act
		String actualFormattedInfo = si.toString();

		// Assert
		assertEquals(expectedFormattedInfo, actualFormattedInfo);
	}

	@Test
	public void testMarketCapitalisationIsCalculated() {
		// Arrange
		double expectedMarketCapitalisationInMillions = 12;
		int id = 2;
		String password = "Pa$$w0rd";
		String stockSymbol = "CNA";

		StockInformation si = new StockInformation(id, password, stockSymbol, stockServiceStub);

		// Act
		Double actualMarketCapitalisationInMillions = si.calculateMarketCapitalisation();

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

		// Act
		String actualStockInfo = si.toString();

		// Assert
		assertEquals(expectedStockInfo, actualStockInfo);
	}

	@Test
	public void test_GetStockInformation_Returns_Defaults_When_Not_Authenticated() {
		// Arrange
		int id = 5;
		String password = "Pa$$w0rd";
		String symbol = "CNA";
		String expectedStockInfo = "  Not Allowed 0.0 0";

		StockInformation si = new StockInformation(id, password, symbol, stockServiceStub);

		// Act
		String actualStockInfo = si.toString();

		// Assert
		assertEquals(expectedStockInfo, actualStockInfo);
	}

}
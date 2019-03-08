package com.info.domain;

import java.util.regex.Pattern;

import com.info.service.StockService;

public class StockInformation {

	private static final int SHARES_OUTSTANDING_POS = 3;
	private static final int CURRENT_PRICE_POS = 2;
	private static final int COMPANY_POS = 1;
	private static final int SYMBOL_POS = 0;

	private int userId;
	private String password;
	private String symbol;

	private String companyName;
	private Double currentPrice;
	private Integer numberOfSharesOutstanding;
	private Integer marketCapitalisationInMillions;

	private String stockInfo;

	@SuppressWarnings("unused")
	private StockService stockService;

	private boolean available;
	private boolean exists;
	private boolean loggedIn;

	public StockInformation(int userId, String password, String symbol, StockService stockService) {
		this.userId = userId;
		this.password = password;
		this.symbol = symbol;
		this.stockService = stockService;
		if (isValidUser() && isValidPassword()) {
			validateStockSymbol();
			loggedIn = stockService.authenticate(userId, password);
			if (loggedIn) {
				// Begin processing the stock info retrieved from the web service
				stockInfo = stockService.getStockInfo(symbol);
			} else {
				this.symbol = " ";
				this.companyName = "Not Allowed";
				this.currentPrice = (double) 0;
				this.numberOfSharesOutstanding = 0;
				this.stockInfo = this.symbol + "," + companyName + "," + currentPrice + "," + numberOfSharesOutstanding;
			}
		}
	}

	@Override
	public String toString() {
		return getFormattedStockInfo();
	}

	public String getFormattedStockInfo() {

		StringBuilder sb = new StringBuilder();

		if (stockInfo != null && loggedIn) {
			// Process the comma-separated string
			String[] stockData = stockInfo.split(",");
			symbol = stockData[SYMBOL_POS];
			companyName = stockData[COMPANY_POS];
			currentPrice = Double.valueOf(stockData[CURRENT_PRICE_POS]);
			numberOfSharesOutstanding = Integer.valueOf(stockData[3]);
			// ABC Computing [ABC] 10.5
			sb.append(companyName);
			sb.append("[").append(symbol).append("]").append(" ").append(currentPrice);
		} else if (stockInfo != null && !loggedIn) {
			String[] stockData = stockInfo.split(",");
			symbol = stockData[SYMBOL_POS];
			companyName = stockData[COMPANY_POS];
			currentPrice = Double.valueOf(stockData[CURRENT_PRICE_POS]);
			numberOfSharesOutstanding = Integer.valueOf(stockData[3]);
			// ABC Computing [ABC] 10.5
			sb.append(symbol);
			sb.append(" ").append(companyName).append(" ").append(currentPrice).append(" ")
					.append(numberOfSharesOutstanding);
		}

		else {
			// No such symbol?
			sb.append("No Such Symbol");
		}
		return sb.toString();
	}

	public boolean isValidUser() {
		boolean isValid = false;
		if (userId >= 1 && userId <= 9999) {
			isValid = true;
		}
		return isValid;
	}

	public void validateStockSymbol() {
		if (symbol != null) {
			// Check that all characters are valid
			if (!Pattern.compile("^[\\p{L}0-9]*$").matcher(symbol).matches()) {
				throw new IllegalArgumentException(
						"Invalid Symbol Provided: Please use correct format - Letters and Numbers only.");
			}
		}
	}

	public boolean isValidPassword() {
		boolean isValid = false;
		String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
		if (password != null) {
			if (Pattern.compile(regex).matcher(password).matches()) {
				isValid = true;
			} else {
				isValid = false;
			}
		}
		return isValid;
	}

	public double calculateMarketCapitalisation() {
		double marketCapitalisation = 0;
		String[] stockData = stockInfo.split(",");
		currentPrice = Double.valueOf(stockData[CURRENT_PRICE_POS]);
		numberOfSharesOutstanding = Integer.valueOf(stockData[SHARES_OUTSTANDING_POS]);

		if (symbol != null) {
			marketCapitalisation += (currentPrice / 100 * numberOfSharesOutstanding);
			marketCapitalisation /= 1000000;
		}
		marketCapitalisationInMillions = (int) Math.floor(marketCapitalisation);
		return marketCapitalisationInMillions;
	}

	public String getSymbol() {
		return symbol;
	}

	@SuppressWarnings("unused")
	private void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public boolean isAvailable() {
		return available;
	}

	@SuppressWarnings("unused")
	private void setAvailable(boolean available) {
		this.available = available;
	}

	public boolean isExists() {
		return exists;
	}

	@SuppressWarnings("unused")
	private void setExists(boolean exists) {
		this.exists = exists;
	}

	public String getCompanyName() {
		return companyName;
	}

	@SuppressWarnings("unused")
	private void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Double getCurrentPrice() {
		return currentPrice;
	}

	@SuppressWarnings("unused")
	private void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Integer getNumberOfSharesOutstanding() {
		return numberOfSharesOutstanding;
	}

	@SuppressWarnings("unused")
	private void setNumberOfSharesOutstanding(Integer numberOfSharesOutstanding) {
		this.numberOfSharesOutstanding = numberOfSharesOutstanding;
	}

	public Integer getMarketCapitalisationInMillions() {
		return marketCapitalisationInMillions;
	}

	@SuppressWarnings("unused")
	private void setMarketCapitalisationInMillions(Integer marketCapitalisationInMillions) {
		this.marketCapitalisationInMillions = marketCapitalisationInMillions;
	}

}
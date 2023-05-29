package com.binance.api.client.domain.account;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.binance.api.client.constant.BinanceApiConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DustConvertableDetailsResponse {

	private String asset;
	private String assetFullName;
	private String amountFree;
	private String toBTC;
	private String toBNB;
	private String toBNBOffExchange;
	private String exchange;

	public String getAsset() {
		return asset;
	}

	public void setAsset(String asset) {
		this.asset = asset;
	}

	public String getAssetFullName() {
		return assetFullName;
	}

	public void setAssetFullName(String assetFullName) {
		this.assetFullName = assetFullName;
	}

	public String getAmountFree() {
		return amountFree;
	}

	public void setAmountFree(String amountFree) {
		this.amountFree = amountFree;
	}

	public String getToBTC() {
		return toBTC;
	}

	public void setToBTC(String toBTC) {
		this.toBTC = toBTC;
	}

	public String getToBNB() {
		return toBNB;
	}

	public void setToBNB(String toBNB) {
		this.toBNB = toBNB;
	}

	public String getToBNBOffExchange() {
		return toBNBOffExchange;
	}

	public void setToBNBOffExchange(String toBNBOffExchange) {
		this.toBNBOffExchange = toBNBOffExchange;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, BinanceApiConstants.TO_STRING_BUILDER_STYLE).append("asset", asset)
				.append("assetFullName", assetFullName).append("amountFree", amountFree).append("toBTC", toBTC)
				.append("toBNB", toBNB).append("toBNBOffExchange", toBNBOffExchange).append("exchange", exchange)
				.toString();
	}
	
}
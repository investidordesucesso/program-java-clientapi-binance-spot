package com.binance.api.client.domain.account;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.binance.api.client.constant.BinanceApiConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DustConvertableResponse {

	private List<DustConvertableDetailsResponse> details;
	private String totalTransferBtc;
	private String totalTransferBNB;
	private String dribbletPercentage;

	@Override
	public String toString() {
		return new ToStringBuilder(this, BinanceApiConstants.TO_STRING_BUILDER_STYLE).append("details", details)
				.append("totalTransferBtc", totalTransferBtc).append("totalTransferBNB", totalTransferBNB)
				.append("dribbletPercentage", dribbletPercentage).toString();
	}

	public List<DustConvertableDetailsResponse> getDetails() {
		return details;
	}

	public void setDetails(List<DustConvertableDetailsResponse> details) {
		this.details = details;
	}

	public String getTotalTransferBtc() {
		return totalTransferBtc;
	}

	public void setTotalTransferBtc(String totalTransferBtc) {
		this.totalTransferBtc = totalTransferBtc;
	}

	public String getTotalTransferBNB() {
		return totalTransferBNB;
	}

	public void setTotalTransferBNB(String totalTransferBNB) {
		this.totalTransferBNB = totalTransferBNB;
	}

	public String getDribbletPercentage() {
		return dribbletPercentage;
	}

	public void setDribbletPercentage(String dribbletPercentage) {
		this.dribbletPercentage = dribbletPercentage;
	}

}
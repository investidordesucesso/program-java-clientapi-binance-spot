package com.binance.api.client.impl;

import static com.binance.api.client.impl.BinanceApiServiceGenerator.createService;
import static com.binance.api.client.impl.BinanceApiServiceGenerator.executeSync;

import java.util.List;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.config.BinanceApiConfig;
import com.binance.api.client.constant.BinanceApiConstants;
import com.binance.api.client.domain.account.Account;
import com.binance.api.client.domain.account.DepositAddress;
import com.binance.api.client.domain.account.DepositHistory;
import com.binance.api.client.domain.account.DustConvertableResponse;
import com.binance.api.client.domain.account.DustTransferResponse;
import com.binance.api.client.domain.account.NewOCO;
import com.binance.api.client.domain.account.NewOCOResponse;
import com.binance.api.client.domain.account.NewOrder;
import com.binance.api.client.domain.account.NewOrderResponse;
import com.binance.api.client.domain.account.Order;
import com.binance.api.client.domain.account.OrderList;
import com.binance.api.client.domain.account.SubAccountTransfer;
import com.binance.api.client.domain.account.Trade;
import com.binance.api.client.domain.account.TradeHistoryItem;
import com.binance.api.client.domain.account.WithdrawHistory;
import com.binance.api.client.domain.account.WithdrawResult;
import com.binance.api.client.domain.account.request.AllOrderListRequest;
import com.binance.api.client.domain.account.request.AllOrdersRequest;
import com.binance.api.client.domain.account.request.CancelOrderListRequest;
import com.binance.api.client.domain.account.request.CancelOrderListResponse;
import com.binance.api.client.domain.account.request.CancelOrderRequest;
import com.binance.api.client.domain.account.request.CancelOrderResponse;
import com.binance.api.client.domain.account.request.OrderListStatusRequest;
import com.binance.api.client.domain.account.request.OrderRequest;
import com.binance.api.client.domain.account.request.OrderStatusRequest;
import com.binance.api.client.domain.general.Asset;
import com.binance.api.client.domain.general.ExchangeInfo;
import com.binance.api.client.domain.market.AggTrade;
import com.binance.api.client.domain.market.BookTicker;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.binance.api.client.domain.market.OrderBook;
import com.binance.api.client.domain.market.TickerPrice;
import com.binance.api.client.domain.market.TickerStatistics;

import retrofit2.Call;

/**
 * Implementation of Binance's REST API using Retrofit with synchronous/blocking
 * method calls.
 */
public class BinanceApiRestClientImpl implements BinanceApiRestClient {

	private final BinanceApiService binanceApiService;

	public BinanceApiRestClientImpl(String apiKey, String secret) {
		binanceApiService = createService(BinanceApiService.class, apiKey, secret);
	}

	// General endpoints

	@Override
	public void ping() {
		executeSync(binanceApiService.ping());
	}

	@Override
	public Long getServerTime() {
		return executeSync(binanceApiService.getServerTime()).getServerTime();
	}

	@Override
	public ExchangeInfo getExchangeInfo() {
		return executeSync(binanceApiService.getExchangeInfo());
	}

	@Override
	public List<Asset> getAllAssets() {
		return executeSync(binanceApiService
				.getAllAssets(BinanceApiConfig.getAssetInfoApiBaseUrl() + "assetWithdraw/getAllAsset.html"));
	}

	// Market Data endpoints

	@Override
	public OrderBook getOrderBook(String symbol, Integer limit) {
		return executeSync(binanceApiService.getOrderBook(symbol, limit));
	}

	@Override
	public List<TradeHistoryItem> getTrades(String symbol, Integer limit) {
		return executeSync(binanceApiService.getTrades(symbol, limit));
	}

	@Override
	public List<TradeHistoryItem> getHistoricalTrades(String symbol, Integer limit, Long fromId) {
		return executeSync(binanceApiService.getHistoricalTrades(symbol, limit, fromId));
	}

	@Override
	public List<AggTrade> getAggTrades(String symbol, String fromId, Integer limit, Long startTime, Long endTime) {
		return executeSync(binanceApiService.getAggTrades(symbol, fromId, limit, startTime, endTime));
	}

	@Override
	public List<AggTrade> getAggTrades(String symbol) {
		return getAggTrades(symbol, null, null, null, null);
	}

	@Override
	public List<Candlestick> getCandlestickBars(String symbol, CandlestickInterval interval, Integer limit,
			Long startTime, Long endTime) {
		return executeSync(
				binanceApiService.getCandlestickBars(symbol, interval.getIntervalId(), limit, startTime, endTime));
	}

	@Override
	public List<Candlestick> getCandlestickBars(String symbol, CandlestickInterval interval) {
		return getCandlestickBars(symbol, interval, null, null, null);
	}

	@Override
	public TickerStatistics get24HrPriceStatistics(String symbol) {
		return executeSync(binanceApiService.get24HrPriceStatistics(symbol));
	}

	@Override
	public List<TickerStatistics> getAll24HrPriceStatistics() {
		return executeSync(binanceApiService.getAll24HrPriceStatistics());
	}

	@Override
	public TickerPrice getPrice(String symbol) {
		return executeSync(binanceApiService.getLatestPrice(symbol));
	}

	@Override
	public List<TickerPrice> getAllPrices() {
		return executeSync(binanceApiService.getLatestPrices());
	}

	@Override
	public List<BookTicker> getBookTickers() {
		return executeSync(binanceApiService.getBookTickers());
	}

	@Override
	public NewOrderResponse newOrder(NewOrder order) {
		final Call<NewOrderResponse> call;
		if (order.getQuoteOrderQty() == null) {
			call = binanceApiService.newOrder(order.getSymbol(), order.getSide(), order.getType(),
					order.getTimeInForce(), order.getQuantity(), order.getPrice(), order.getNewClientOrderId(),
					order.getStopPrice(), order.getIcebergQty(), order.getNewOrderRespType(), order.getRecvWindow(),
					order.getTimestamp());
		} else {
			call = binanceApiService.newOrderQuoteQty(order.getSymbol(), order.getSide(), order.getType(),
					order.getTimeInForce(), order.getQuoteOrderQty(), order.getPrice(), order.getNewClientOrderId(),
					order.getStopPrice(), order.getIcebergQty(), order.getNewOrderRespType(), order.getRecvWindow(),
					order.getTimestamp());
		}
		return executeSync(call);
	}

	@Override
	public void newOrderTest(NewOrder order) {
		executeSync(binanceApiService.newOrderTest(order.getSymbol(), order.getSide(), order.getType(),
				order.getTimeInForce(), order.getQuantity(), order.getPrice(), order.getNewClientOrderId(),
				order.getStopPrice(), order.getIcebergQty(), order.getNewOrderRespType(), order.getRecvWindow(),
				order.getTimestamp()));
	}

	// Account endpoints

	@Override
	public Order getOrderStatus(OrderStatusRequest orderStatusRequest) {
		return executeSync(binanceApiService.getOrderStatus(orderStatusRequest.getSymbol(),
				orderStatusRequest.getOrderId(), orderStatusRequest.getOrigClientOrderId(),
				orderStatusRequest.getRecvWindow(), orderStatusRequest.getTimestamp()));
	}

	@Override
	public CancelOrderResponse cancelOrder(CancelOrderRequest cancelOrderRequest) {
		return executeSync(
				binanceApiService.cancelOrder(cancelOrderRequest.getSymbol(), cancelOrderRequest.getOrderId(),
						cancelOrderRequest.getOrigClientOrderId(), cancelOrderRequest.getNewClientOrderId(),
						cancelOrderRequest.getRecvWindow(), cancelOrderRequest.getTimestamp()));
	}

	@Override
	public List<CancelOrderResponse> cancelAllOpenOrders(OrderRequest order) {
		return executeSync(
				binanceApiService.cancelAllOpenOrders(order.getSymbol(), order.getRecvWindow(), order.getTimestamp()));
	}

	@Override
	public List<Order> getOpenOrders(OrderRequest orderRequest) {
		return executeSync(binanceApiService.getOpenOrders(orderRequest.getSymbol(), orderRequest.getRecvWindow(),
				orderRequest.getTimestamp()));
	}

	@Override
	public List<Order> getAllOrders(AllOrdersRequest orderRequest) {
		return executeSync(binanceApiService.getAllOrders(orderRequest.getSymbol(), orderRequest.getOrderId(),
				orderRequest.getStartTime(), orderRequest.getEndTime(),
				orderRequest.getLimit(), orderRequest.getRecvWindow(), orderRequest.getTimestamp()));
	}

	@Override
	public NewOCOResponse newOCO(NewOCO oco) {
		return executeSync(binanceApiService.newOCO(oco.getSymbol(), oco.getListClientOrderId(), oco.getSide(),
				oco.getQuantity(), oco.getLimitClientOrderId(), oco.getPrice(), oco.getLimitIcebergQty(),
				oco.getStopClientOrderId(), oco.getStopPrice(), oco.getStopLimitPrice(), oco.getStopIcebergQty(),
				oco.getStopLimitTimeInForce(), oco.getNewOrderRespType(), oco.getRecvWindow(), oco.getTimestamp()));
	}

	@Override
	public CancelOrderListResponse cancelOrderList(CancelOrderListRequest cancelOrderListRequest) {
		return executeSync(binanceApiService.cancelOrderList(cancelOrderListRequest.getSymbol(), cancelOrderListRequest.getOrderListId(),
				cancelOrderListRequest.getListClientOrderId(), cancelOrderListRequest.getNewClientOrderId(),
				cancelOrderListRequest.getRecvWindow(), cancelOrderListRequest.getTimestamp()));
	}

	@Override
	public OrderList getOrderListStatus(OrderListStatusRequest orderListStatusRequest) {
		return executeSync(binanceApiService.getOrderListStatus(orderListStatusRequest.getOrderListId(), orderListStatusRequest.getOrigClientOrderId(),
				orderListStatusRequest.getRecvWindow(), orderListStatusRequest.getTimestamp()));
	}

	@Override
	public List<OrderList> getAllOrderList(AllOrderListRequest allOrderListRequest) {
		return executeSync(binanceApiService.getAllOrderList(allOrderListRequest.getFromId(), allOrderListRequest.getStartTime(),
				allOrderListRequest.getEndTime(), allOrderListRequest.getLimit(), allOrderListRequest.getRecvWindow(), allOrderListRequest.getTimestamp()));
	}

	@Override
	public Account getAccount(Long recvWindow, Long timestamp) {
		return executeSync(binanceApiService.getAccount(recvWindow, timestamp));
	}

	@Override
	public Account getAccount() {
		return getAccount(BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis());
	}

	@Override
	public List<Trade> getMyTrades(String symbol, Long orderId, Long startTime, Long endTime, Integer limit, Long fromId, Long recvWindow,
			Long timestamp) {
		return executeSync(binanceApiService.getMyTrades(symbol, orderId, startTime, endTime, limit, fromId, recvWindow,
				timestamp));
	}

	@Override
	public List<Trade> getMyTrades(String symbol, Integer limit) {
		return getMyTrades(symbol, null, null, null, limit, null, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
				System.currentTimeMillis());
	}

	@Override
	public List<Trade> getMyTrades(String symbol) {
		return getMyTrades(symbol, null, null, null, null, null, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
				System.currentTimeMillis());
	}

	@Override
	public List<Trade> getMyTrades(String symbol, Long fromId) {
		return getMyTrades(symbol, null, null, null, null, fromId, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
				System.currentTimeMillis());
	}

	@Override
	public WithdrawResult withdraw(String asset, String address, String amount, String name, String addressTag) {
		return executeSync(binanceApiService.withdraw(asset, address, amount, name, addressTag,
				BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis()));
	}

	@Override
	public DustTransferResponse dustTransfer(List<String> asset) {
		return executeSync(binanceApiService.dustTransfer(asset, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis()));
	}

	@Override
	public DustConvertableResponse dustConvertable() {
		return executeSync(binanceApiService.dustConvertable(BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis()));
	}
	
	@Override
	public DepositHistory getDepositHistory(String asset) {
		return executeSync(binanceApiService.getDepositHistory(asset, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
				System.currentTimeMillis()));
	}

	@Override
	public WithdrawHistory getWithdrawHistory(String asset) {
		return executeSync(binanceApiService.getWithdrawHistory(asset, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
				System.currentTimeMillis()));
	}

	@Override
	public List<SubAccountTransfer> getSubAccountTransfers() {
		return executeSync(binanceApiService.getSubAccountTransfers(System.currentTimeMillis()));
	}

	@Override
	public DepositAddress getDepositAddress(String asset) {
		return executeSync(binanceApiService.getDepositAddress(asset, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
				System.currentTimeMillis()));
	}

	// User stream endpoints

	@Override
	public String startUserDataStream() {
		return executeSync(binanceApiService.startUserDataStream()).toString();
	}

	@Override
	public void keepAliveUserDataStream(String listenKey) {
		executeSync(binanceApiService.keepAliveUserDataStream(listenKey));
	}

	@Override
	public void closeUserDataStream(String listenKey) {
		executeSync(binanceApiService.closeAliveUserDataStream(listenKey));
	}

}

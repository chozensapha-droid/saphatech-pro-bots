package com.saphatech.probot;

/** Broker-neutral interface. Implement a supported Exness/broker API adapter here. */
public interface BrokerGateway {
    boolean connect();
    boolean isConnected();
    MarketSnapshot snapshot(String symbol, String timeframe);
    OrderResult marketOrder(String symbol, Side side, double volume);
    boolean closePosition(String positionId);
}

enum Side { BUY, SELL }
record MarketSnapshot(double bid, double ask, long timeMs) {}
record OrderResult(boolean accepted, String id, String message) {}

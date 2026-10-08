package com.saphatech.probot;

import java.util.*;

/** Multi-timeframe decision layer. Broker data/execution is intentionally separated. */
public final class StrategyEngine {
    private static final List<String> TFS=List.of("M1","M5","M15","M30","H1","H4","D1");
    private StrategyEngine() {}
    public static String decide(Map<String,MarketSnapshot> data){
        if(data==null || data.size()<3) return "WAIT";
        // Placeholder for the user's selected strategy. Never trade from missing data.
        return "WAIT";
    }
    public static List<String> timeframes(){ return TFS; }
}

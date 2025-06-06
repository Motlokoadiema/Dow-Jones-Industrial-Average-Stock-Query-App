package com.stockapp;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import java.util.logging.Logger;
import java.util.Queue;
import java.util.ServiceLoader;
import java.util.LinkedList;
import java.util.Calendar;

public class App {
    public interface MyService {
    void execute();
    }

    public class MyServiceImpl implements MyService {
    @Override
    public void execute() {
        logger.info("Service Executed!");
        }
    }

    //The application uses Logger instead of System.out for outputs
    private static final Logger logger = Logger.getLogger(App.class.getName());
    public static void main (String[] args){
         ServiceLoader<MyService> loader = ServiceLoader.load(MyService.class);
        for (MyService service : loader) {
            service.execute();

        try {
        Stock stock = YahooFinance.get("DJI");
        Queue<Double> stockValues = new LinkedList<>();
        stockValues.add(stock.getQuote().getPrice().doubleValue());
        Calendar lastTradeTime = stock.getQuote().getLastTradeTime();
         if (lastTradeTime != null) {
            logger.info("Last trade timestamp: " + lastTradeTime.getTime());
        } else {
            logger.info("Timestamp data not available.");
        }
        logger.info("Current Dow Jones Industrial Average: " + stock.getQuote().getPrice());
    } catch (Exception e) {
        logger.info("Error fetching stock data: " + e.getMessage());
    }
    }
    }
}
package org.knowm.xchange.examples.bitcurex;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitcurex.BitcurexExchange;
import org.knowm.xchange.bitcurex.dto.marketdata.BitcurexDepth;
import org.knowm.xchange.bitcurex.service.polling.BitcurexMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Demonstrate requesting Order Book at Bitcurex
 */
public class BitcurexDepthDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get the Bitcurex exchange API using default settings
    Exchange bitcurex = ExchangeFactory.INSTANCE.createExchange(BitcurexExchange.class.getName());
    requestData(bitcurex, CurrencyPair.BTC_EUR);
    requestData(bitcurex, CurrencyPair.BTC_PLN);
  }

  private static void requestData(Exchange bitcurex, CurrencyPair pair) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = bitcurex.getPollingMarketDataService();

    generic(marketDataService, pair);
    raw((BitcurexMarketDataServiceRaw) marketDataService, pair.counter.getCurrencyCode());
  }

  private static void generic(PollingMarketDataService marketDataService, CurrencyPair pair) throws IOException {

    // Get the latest order book data for BTC/CAD
    OrderBook orderBook = marketDataService.getOrderBook(pair);

    System.out.println(
        "Current Order Book size for BTC / " + pair.counter.getCurrencyCode() + ": " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());

    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());

    System.out.println(orderBook.toString());
  }

  private static void raw(BitcurexMarketDataServiceRaw marketDataService, String counterSymbol) throws IOException {

    // Get the latest order book data for BTC/CAD
    BitcurexDepth bitcurexDepth = marketDataService.getBitcurexOrderBook(counterSymbol);

    System.out
        .println("Current Order Book size for BTC / " + counterSymbol + ": " + (bitcurexDepth.getAsks().size() + bitcurexDepth.getBids().size()));

    System.out.println("First Ask: " + bitcurexDepth.getAsks().get(0)[0].toString());

    System.out.println("First Bid: " + bitcurexDepth.getBids().get(0)[0].toString());

    System.out.println(bitcurexDepth.toString());
  }

}

package net.bddtrader.tradingdata.services;

import net.bddtrader.news.NewsItem;
import net.bddtrader.stocks.TopStock;
import net.bddtrader.tradingdata.TradingDataAPI;
import net.bddtrader.tradingdata.exceptions.IllegalPriceManiuplationException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpMethod.GET;

public class IEXtradingAPI implements TradingDataAPI {
    @Override
    public List<NewsItem> getNewsFor(String stockid) {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<NewsItem>> response =
                restTemplate.exchange("https://api.iextrading.com/1.0/stock/{stockid}/news",
                        GET, null,
                        new ParameterizedTypeReference<List<NewsItem>>() {},
                        stockid);
        return response.getBody();
    }

    @Override
    public Double getPriceFor(String stockid) {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Double> response =
                restTemplate.exchange("https://api.iextrading.com/1.0/stock/{stockid}/price",
                        GET, null,
                        new ParameterizedTypeReference<Double>() {},
                        stockid);
        return response.getBody();
    }

    @Override
    public List<String> getPopularStocks() {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<TopStock>> response =
                restTemplate.exchange("https://api.iextrading.com/1.0/tops",
                        GET, null,
                        new ParameterizedTypeReference<List<TopStock>>() {});

        return requireNonNull(response.getBody()).stream()
                                                 .map(TopStock::getSymbol)
                                                 .collect(Collectors.toList());
    }

    @Override
    public void updatePriceFor(String stockid, Double currentPrice) {
        throw new IllegalPriceManiuplationException("Attempt to update prices in production");
    }
}

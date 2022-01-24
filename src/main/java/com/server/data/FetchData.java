package com.server.data;

import com.server.entities.Currency;
import com.server.entities.CurrentValue;
import com.server.repositories.CurrencyRepository;
import com.server.repositories.CurrentValueRepository;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

@Service
public class FetchData {

    public WebClient buildClient(String baseUrl) {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofMillis(5000))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(baseUrl)
                .build();
    }

    public void checkBestSpread(CurrentValueRepository currentValueRepository, CurrencyRepository currencyRepository) {
        currentValueRepository.findAll().forEach(data -> data.setBestPrice(false));
        for (Currency currencyRecord : currencyRepository.findAll()){
            List<CurrentValue> currentValueList = currentValueRepository.findCurrentValueByCurrencyAbbr(currencyRecord.getAbbr());
            CurrentValue minSpread = currentValueList
                    .stream()
                    .min(Comparator.comparing(CurrentValue::getSpread))
                    .orElseThrow(NoSuchElementException::new);
            minSpread.setBestPrice(true);
            currentValueRepository.save(minSpread);

        }
    }
}

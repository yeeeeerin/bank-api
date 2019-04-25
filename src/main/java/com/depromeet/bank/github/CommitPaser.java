package com.depromeet.bank.github;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class CommitPaser {

    public Optional<Integer> paser(String url) throws IOException {

        Document document = Jsoup.connect(url)
                .method(Connection.Method.GET)
                .execute()
                .parse();

        Elements elements = document.select("g[transform=translate(561, 0)] rect");

        Integer sum = elements.stream()
                .mapToInt(element -> Integer.valueOf(element.attr("data-count")))
                .sum();

        return Optional.ofNullable(sum);
    }
}

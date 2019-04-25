package com.depromeet.bank.git;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CommitParser {

    public int parser(String url) throws IOException {

        Document document = Jsoup.connect(url)
                .method(Connection.Method.GET)
                .execute()
                .parse();

        Elements elements = document.select("g[transform=translate(561, 0)] rect");

        return elements.stream()
                .mapToInt(element -> Integer.valueOf(element.attr("data-count")))
                .sum();

    }
}

package com.depromeet.bank.git;


import com.depromeet.bank.exception.NotFoundException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CommitParser {

    public int parser(String url) {

        Document document;
        try {
            document = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .execute()
                    .parse();
        } catch (IOException e) {
            throw new NotFoundException();
        }

        Elements elements = document.select("g[transform=translate(561, 0)] rect");

        return elements.stream()
                .mapToInt(element -> Integer.valueOf(element.attr("data-count")))
                .sum();

    }
}

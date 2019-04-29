package com.depromeet.bank.git;


import com.depromeet.bank.exception.NotFoundException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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

        Element element = document.select("g[transform=translate(572, 0)] rect").last();

        return Integer.parseInt(element.attr("data-count"));

    }
}

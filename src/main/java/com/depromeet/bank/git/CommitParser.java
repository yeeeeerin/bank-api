package com.depromeet.bank.git;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CommitParser {

    public int parse(String url) {

        Document document;
        try {
            document = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .execute()
                    .parse();
        } catch (IOException e) {
            return 0;
        }


        Element element = document.select("g[transform=translate(572, 0)] rect").last();
        String commit;
        if (element != null) {
            commit = element.attr("data-count");
        } else {
            commit = "0";
        }


        System.out.println(commit);

        return Integer.parseInt(commit);

    }
}

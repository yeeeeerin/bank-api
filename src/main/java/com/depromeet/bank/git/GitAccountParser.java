package com.depromeet.bank.git;

import com.depromeet.bank.exception.NotFoundException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GitAccountParser {

    public List<String> accountParser() {
        Document document;
        try {
            document = Jsoup.connect("https://github.com/orgs/depromeet/teams/6/members")
                    .method(Connection.Method.GET)
                    .execute()
                    .parse();
        } catch (IOException e) {
            throw new NotFoundException();
        }


        return document.getElementsByAttribute("data-bulk-actions-id")
                .stream()
                .map(account -> account.attr("data-bulk-actions-id"))
                .collect(Collectors.toList());

    }

}

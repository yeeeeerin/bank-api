package com.depromeet.bank.adapter.openapi;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Getter
public class AirPollutionResponse {
    @XmlElement
    private Body body;

    @XmlRootElement(name = "body")
    @XmlAccessorType(XmlAccessType.FIELD)
    @Getter
    @Setter
    @ToString
    public static class Body {

        @XmlElement(name = "item")
        @XmlElementWrapper(name = "items")
        private List<Item> items = new ArrayList<>();

        @Getter
        @Setter
        @ToString
        @XmlRootElement(name = "item")
        @XmlAccessorType(XmlAccessType.FIELD)
        public static class Item {

            @XmlElement
            private String dataTime;

            @XmlElement
            private Long pm10Value;

            @XmlElement
            private Long pm25Value;

            @XmlElement
            private Long o3Value;

            @XmlElement
            private Integer pm10Grade;

            @XmlElement
            private Integer pm25Grade;

        }

        @XmlElement
        private Integer totalCount;

        @XmlElement
        private Integer pageNo;

        @XmlElement
        private Integer numOfRows;
    }

    public Body.Item getItem() {
        return getBody().getItems().get(0);
    }

    public Long getPm10Value() {
        return getItem().pm10Value;
    }

    public Long getPm25Value() {
        return getItem().pm25Value;
    }
}

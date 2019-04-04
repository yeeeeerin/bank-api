package com.depromeet.bank.adaptor.openapi;

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
    }

    public Body.Item getItem() {
        return getBody().getItems().get(0);
    }
}

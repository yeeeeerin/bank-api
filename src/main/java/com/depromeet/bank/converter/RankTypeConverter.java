package com.depromeet.bank.converter;

import com.depromeet.bank.dto.RankType;
import org.springframework.core.convert.converter.Converter;

public class RankTypeConverter implements Converter<String, RankType> {

    @Override
    public RankType convert(String source) {
        return RankType.from(source);
    }
}

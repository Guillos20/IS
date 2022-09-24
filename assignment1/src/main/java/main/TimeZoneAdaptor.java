package main;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.ZonedDateTime;

import java.time.format.DateTimeFormatter;

public class TimeZoneAdaptor extends XmlAdapter<String, ZonedDateTime> {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    @Override
    public ZonedDateTime unmarshal(String v) throws Exception {
        return ZonedDateTime.parse(v, dateTimeFormatter);
    }

    @Override
    public String marshal(ZonedDateTime v) throws Exception {
        return dateTimeFormatter.format(v);
    }
}

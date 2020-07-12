package com.example.sell_master.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;


import java.io.IOException;
import java.util.Date;

/**
 * @zbh
 * @2020/2/23 13:14
 */
//转换Date->Long
public class Date2LongSerializer extends JsonSerializer<Date>{
    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(date.getTime()/1000);

    }
}

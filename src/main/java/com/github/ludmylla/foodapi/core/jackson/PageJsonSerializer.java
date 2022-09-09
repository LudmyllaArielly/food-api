package com.github.ludmylla.foodapi.core.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import java.io.IOException;

/* Classe para customizar informações do page no retorno do json*/
@JsonComponent
public class PageJsonSerializer extends JsonSerializer<Page<?>> {

    @Override
    public void serialize(Page<?> page, JsonGenerator gen, SerializerProvider serializer) throws IOException {
        gen.writeStartObject();

        gen.writeObjectField("content", page.getContent());
        gen.writeNumberField("size", page.getSize());
        gen.writeNumberField("totalElements", page.getTotalElements());
        gen.writeNumberField("totalPages", page.getTotalPages());
        gen.writeNumberField("number", page.getNumber());

        gen.writeEndObject();
    }
}

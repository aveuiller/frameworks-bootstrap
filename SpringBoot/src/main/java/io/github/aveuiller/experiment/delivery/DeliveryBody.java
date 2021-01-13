package io.github.aveuiller.experiment.delivery;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class DeliveryBody {
//    public static class Serializer extends JsonSerializer<Delivery> {
//        @Override
//        public void serialize(Delivery value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//            gen.writeStartObject();
//            gen.writeFieldName("id");
//            gen.writeNumber(value.getId());
//            gen.writeFieldName("location");
//            gen.writeString(value.getLocation());
//            gen.writeFieldName("state");
//            gen.writeString(value.getState().name());
//            gen.close();
//        }
//    }
}

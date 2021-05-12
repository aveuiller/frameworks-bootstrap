package io.github.aveuiller.learning.kafka.raw;

import io.github.aveuiller.learning.kafka.raw.generated.AvroHelloMessage;
import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

/**
 * This is a chicken and egg situation for this project as the build was done as follow:
 * - {@link HelloMessage} was used to send messages in Json format.
 * - {@link HelloMessage} served as a reference to create an Avro schema in this class.
 * - The resulting schema has been placed in the resources as 'hello_message.avsc'.
 * - The latter servers as reference to automatically generate {@link AvroHelloMessage}.
 *
 * Of course, in a production project, you may need to chose between both methods (preferably Avro),
 * then only generate one side of this circle.
 *
 * References:
 * - Class to Schema generation: http://avro.apache.org/docs/1.7.6/api/java/org/apache/avro/reflect/package-summary.html
 * - Schema to Class generation: https://docs.confluent.io/platform/current/schema-registry/schema_registry_onprem_tutorial.html#client-applications-writing-avro
 */
public class SchemaGenerator {
    private URL generateFile(String name) {
        // This will write in target/classes/avro/hello_message.avsc
        return getClass().getClassLoader().getResource(Paths.get("avro", name + ".avsc").toString());
    }

    public void generateSchema(String name, Class<?> clazz) throws IOException {
        Schema schema = ReflectData.get().getSchema(clazz);

        URL generatedUrl = generateFile(name);
        try (FileOutputStream out = new FileOutputStream(generatedUrl.getFile())) {
            out.write(schema.toString().getBytes(StandardCharsets.UTF_8));
        }
    }

    public static void main(String[] args) throws IOException {
        new SchemaGenerator().generateSchema("hello_message", HelloMessage.class);
    }
}

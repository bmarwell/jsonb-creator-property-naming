package io.github.bmarwell.jsonb.creator.json;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.config.PropertyNamingStrategy;
import jakarta.json.bind.config.PropertyVisibilityStrategy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonbProvider implements AutoCloseable {

  private static final Logger LOG = Logger.getLogger(JsonbProvider.class.getName());

  private final Jsonb jsonb;

  public JsonbProvider() {
    final JsonbConfig jsonbConfig =
        new JsonbConfig()
            .withNullValues(Boolean.TRUE)
            .withFormatting(Boolean.TRUE)
            .withPropertyVisibilityStrategy(new PrivateVisibilityStrategy())
            .withPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE_WITH_UNDERSCORES);

    this.jsonb = JsonbBuilder.newBuilder()
        .withConfig(jsonbConfig)
        .build();

    LOG.log(
        Level.INFO,
        () -> "Using JSON-B implementation: [%s]." .formatted(
            this.jsonb.getClass().getCanonicalName()));
  }

  public Jsonb getJsonb() {
    return this.jsonb;
  }

  @Override
  public void close() {
    try {
      this.jsonb.close();
    } catch (Exception javaLangException) {

    }
  }


  static class PrivateVisibilityStrategy implements PropertyVisibilityStrategy {

    @Override
    public boolean isVisible(Field field) {
      return false;
    }

    @Override
    public boolean isVisible(Method method) {
      return true;
    }
  }
}

package io.github.bmarwell.jsonb.creator.value;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.bmarwell.jsonb.creator.json.JsonbProvider;
import jakarta.json.bind.Jsonb;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AuthorTest {

  private static JsonbProvider jsonbProvider;

  private static Jsonb jsonb;

  @BeforeAll
  public static void setUp() {
    jsonbProvider = new JsonbProvider();
    jsonb = jsonbProvider.getJsonb();
  }

  @AfterAll
  public static void tearDown() {
    if (jsonbProvider!=null) {
      jsonbProvider.close();
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  void authorRestDto_serializes_to_json() {
    // given
    final Author authorRestDto = new Author(
        "rpfeynman",
        "Richard",
        "Feynman"
    );

    // when
    final String json = jsonb.toJson(authorRestDto);

    // then
    assertThat(jsonb.fromJson(json, Map.class))
        .containsEntry("id", "rpfeynman")
        .containsEntry("first_name", "Richard");
  }

  @Test
  void authorRestDto_deserializes() {
    // given
    var json = """
        {
            "birth_date": "1918-05-11",
            "first_name": "Richard",
            "id": "rpfeynman",
            "last_name": "Feynman",
            "nicknames": [
                "Dick"
            ],
            "profession": "physicist"
        }""";

    // when
    final Author dto = jsonb.fromJson(json, Author.class);

    // then
    assertThat(dto)
        .isNotNull()
        .extracting(Author::id, Author::firstName, Author::lastName)
        .contains("rpfeynman", "Richard", "Feynman");
  }
}

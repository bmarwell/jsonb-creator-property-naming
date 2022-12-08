package io.github.bmarwell.jsonb.creator.value;

import static java.util.Objects.requireNonNull;

import jakarta.json.bind.annotation.JsonbCreator;

public record Author(String id, String firstName, String lastName) {

  @JsonbCreator
  public Author {
    requireNonNull(id, "id");
    requireNonNull(firstName, "firstName");
    requireNonNull(lastName, "lastName");
  }
}

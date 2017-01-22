package storage;

import com.google.auto.value.AutoValue;

import java.nio.file.Path;

/**
 * A data object for mapping words to file paths.
 */
//@AutoValue
abstract class Word {

  public static Builder builder() {
    return new AutoValue_Word.Builder();
  }

  public abstract Path path();

  public abstract String word();

//  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder path(Path path);

    public abstract Builder word(String word);

    public abstract Word build();
  }
}

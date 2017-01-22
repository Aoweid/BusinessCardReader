package storage;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Status;
import com.google.auto.value.AutoValue;

import java.nio.file.Path;
import java.util.List;

import javax.annotation.Nullable;

/**
 * A data object for mapping text to file paths.
 */
//@AutoValue
abstract class ImageText {

  public static Builder builder() {
    return new AutoValue_ImageText.Builder();
  }

  public abstract Path path();

  public abstract List<EntityAnnotation> textAnnotations();

  @Nullable
  public abstract Status error();

//  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder path(Path path);

    public abstract Builder textAnnotations(List<EntityAnnotation> ts);

    public abstract Builder error(@Nullable Status err);

    public abstract ImageText build();
  }
}

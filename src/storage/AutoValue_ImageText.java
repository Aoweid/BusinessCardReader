package storage;

import java.nio.file.Path;
import java.util.List;

import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Status;
//import com.google.cloud.vision.samples.text.AutoValue_ImageText;
//import com.google.cloud.vision.samples.text.ImageText;

public class AutoValue_ImageText extends ImageText{
	private final Path path;
	  private final List<EntityAnnotation> textAnnotations;
	  private final Status error;

	  private AutoValue_ImageText(
	      Path path,
	      List<EntityAnnotation> textAnnotations,
	      @javax.annotation.Nullable Status error) {
	    if (path == null) {
	      throw new NullPointerException("Null path");
	    }
	    this.path = path;
	    if (textAnnotations == null) {
	      throw new NullPointerException("Null textAnnotations");
	    }
	    this.textAnnotations = textAnnotations;
	    this.error = error;
	  }

	  @Override
	  public Path path() {
	    return path;
	  }

	  @Override
	  public List<EntityAnnotation> textAnnotations() {
	    return textAnnotations;
	  }

	  @javax.annotation.Nullable
	  @Override
	  public Status error() {
	    return error;
	  }

	  @Override
	  public String toString() {
	    return "ImageText{"
	        + "path=" + path + ", "
	        + "textAnnotations=" + textAnnotations + ", "
	        + "error=" + error
	        + "}";
	  }

	  @Override
	  public boolean equals(Object o) {
	    if (o == this) {
	      return true;
	    }
	    if (o instanceof ImageText) {
	      ImageText that = (ImageText) o;
	      return (this.path.equals(that.path()))
	           && (this.textAnnotations.equals(that.textAnnotations()))
	           && ((this.error == null) ? (that.error() == null) : this.error.equals(that.error()));
	    }
	    return false;
	  }

	  @Override
	  public int hashCode() {
	    int h = 1;
	    h *= 1000003;
	    h ^= path.hashCode();
	    h *= 1000003;
	    h ^= textAnnotations.hashCode();
	    h *= 1000003;
	    h ^= (error == null) ? 0 : error.hashCode();
	    return h;
	  }

	  static final class Builder extends ImageText.Builder {
	    private Path path;
	    private List<EntityAnnotation> textAnnotations;
	    private Status error;
	    Builder() {
	    }
	    Builder(ImageText source) {
	      this.path = source.path();
	      this.textAnnotations = source.textAnnotations();
	      this.error = source.error();
	    }
	    @Override
	    public ImageText.Builder path(Path path) {
	      this.path = path;
	      return this;
	    }
	    @Override
	    public ImageText.Builder textAnnotations(List<EntityAnnotation> textAnnotations) {
	      this.textAnnotations = textAnnotations;
	      return this;
	    }
	    @Override
	    public ImageText.Builder error(Status error) {
	      this.error = error;
	      return this;
	    }
	    @Override
	    public ImageText build() {
	      String missing = "";
	      if (path == null) {
	        missing += " path";
	      }
	      if (textAnnotations == null) {
	        missing += " textAnnotations";
	      }
	      if (!missing.isEmpty()) {
	        throw new IllegalStateException("Missing required properties:" + missing);
	      }
	      return new AutoValue_ImageText(
	          this.path,
	          this.textAnnotations,
	          this.error);
	    }
	  }
}

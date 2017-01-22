package storage;

import java.nio.file.Path;

public class AutoValue_Word extends Word{
	private final Path path;
	  private final String word;

	  private AutoValue_Word(
	      Path path,
	      String word) {
	    if (path == null) {
	      throw new NullPointerException("Null path");
	    }
	    this.path = path;
	    if (word == null) {
	      throw new NullPointerException("Null word");
	    }
	    this.word = word;
	  }

	  @Override
	  public Path path() {
	    return path;
	  }

	  @Override
	  public String word() {
	    return word;
	  }

	  @Override
	  public String toString() {
	    return "Word{"
	        + "path=" + path + ", "
	        + "word=" + word
	        + "}";
	  }

	  @Override
	  public boolean equals(Object o) {
	    if (o == this) {
	      return true;
	    }
	    if (o instanceof Word) {
	      Word that = (Word) o;
	      return (this.path.equals(that.path()))
	           && (this.word.equals(that.word()));
	    }
	    return false;
	  }

	  @Override
	  public int hashCode() {
	    int h = 1;
	    h *= 1000003;
	    h ^= path.hashCode();
	    h *= 1000003;
	    h ^= word.hashCode();
	    return h;
	  }

	  static final class Builder extends Word.Builder {
	    private Path path;
	    private String word;
	    Builder() {
	    }
	    Builder(Word source) {
	      this.path = source.path();
	      this.word = source.word();
	    }
	    @Override
	    public Word.Builder path(Path path) {
	      this.path = path;
	      return this;
	    }
	    @Override
	    public Word.Builder word(String word) {
	      this.word = word;
	      return this;
	    }
	    @Override
	    public Word build() {
	      String missing = "";
	      if (path == null) {
	        missing += " path";
	      }
	      if (word == null) {
	        missing += " word";
	      }
	      if (!missing.isEmpty()) {
	        throw new IllegalStateException("Missing required properties:" + missing);
	      }
	      return new AutoValue_Word(
	          this.path,
	          this.word);
	    }
	  }
}

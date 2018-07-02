package application.Core.Errors;

public class UnrecognizableFileType extends Exception {
	private static final long serialVersionUID = 1L;
	public UnrecognizableFileType() {
		super("The file provided is unrecognised/unsupported. Supported file types are: .mp3, .mp4, .m4a, .m4v, and .wav");
	}

}

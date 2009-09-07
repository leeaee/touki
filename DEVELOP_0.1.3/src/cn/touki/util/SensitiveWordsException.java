package cn.touki.util;



/**
 * The runtime exception thrown when sensitive word found by {@link SensitiveWords#checkValid}.
 *
 * @author <A href="mailto:gregory@konlink.com">Gregory Song</A>
 * @version $Revision: 1.1.1.1 $
 * @since 2.0
 */
public class SensitiveWordsException extends RuntimeException {

    //Properties
	private static final long serialVersionUID = 1L;

	//Constructor
    public SensitiveWordsException(String msg) {
        super(msg);
    }

    public SensitiveWordsException() {
    }

    //Methods
}
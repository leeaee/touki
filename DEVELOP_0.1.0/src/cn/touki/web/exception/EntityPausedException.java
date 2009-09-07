package cn.touki.web.exception;

/**
 * Exception thrown when something is paused.
 *
 * @author <A href="mailto:gregory@konlink.com">Gregory Song</A>
 * @version $Revision: 1.1.1.1 $
 * @since 7.00.00
 */
public class EntityPausedException extends WebException {

	private static final long serialVersionUID = 1L;
	//Properties
    private static final String PAUSED = "exception.entity.paused";

    //Constructor

    /**
     * 指明被暂停的实体的类型key和具体的实体主键. 如：
     * <pre>
     * throw new EntityPausedException("entity.user", "gregory");
     * </pre>
     *
     * @param entityKey 实体的类型的字典key
     * @param entityId  实体的主键
     */
    public EntityPausedException(String entityKey, Object entityId) {
        super(PAUSED, new Object[]{"{" + entityKey + "}", entityId});
    }

    //Methods
}
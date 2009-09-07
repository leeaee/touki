package cn.touki.web.exception;

/**
 * Exception thrown when some entity has already existed.
 *
 * @author <A href="mailto:gregory@konlink.com">Gregory Song</A>
 * @version $Revision: 1.2 $
 * @since 7.00.00
 */
public class EntityAlreadyExistException extends WebException {

	private static final long serialVersionUID = 1L;
	//Properties
    private static final String BASE_KEY = "exception.entity.exist";

    //Constructor
    /**
     * 用一个消息的key构造，仅仅简单的说明。
     */
    public EntityAlreadyExistException(String msg) {
        super(msg);
    }

    /**
     * 指明已经存在的实体的类型key和具体的实体主键. 如：
     * <pre>
     * throw new EntityAlreadyExistException("entity.user", "gregory");
     * or
     * throw new EntityAlreadyExistException(EntityAlreadyExistException.GROUP, "konlink");
     * </pre>
     *
     * @param entityKey 实体的类型的字典key
     * @param entityId  实体的主键
     */
    public EntityAlreadyExistException(String entityKey, Object entityId) {
        super(BASE_KEY, new Object[]{"{" + entityKey + "}", entityId});
    }

    //Methods
}
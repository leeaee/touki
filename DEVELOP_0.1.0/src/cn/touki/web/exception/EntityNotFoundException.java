package cn.touki.web.exception;

/**
 * Exception to describe entity is missing.
 *
 * @author <A href="mailto:gregory@konlink.com">Gregory Song</A>
 * @version $Revision: 1.2 $
 * @since 7.00.00
 */
public class EntityNotFoundException extends WebException {

	private static final long serialVersionUID = 1L;
	//Properties
    private static final String BASE_KEY = "exception.entity.missing";

    //Constructor
    /**
     * 用一个消息的key构造，仅仅简单的说明。
     */
    public EntityNotFoundException(String msg) {
        super(msg);
    }

    /**
     * 指明没有找到的实体的类型key和具体的实体主键. 如：
     * <pre>
     * throw new EntityNotFoundException("entity.user", "gregory");
     * or
     * throw new EntityNotFoundException(EntityNotFoundException.GROUP, "konlink");
     * </pre>
     *
     * @param entityKey 实体的类型的字典key
     * @param entityId  实体的主键
     */
    public EntityNotFoundException(String entityKey, Object entityId) {
        super(BASE_KEY, new Object[]{"{" + entityKey + "}", entityId});
    }

    //Methods
}
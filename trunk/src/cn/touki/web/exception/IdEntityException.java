package cn.touki.web.exception;

/**
 * Exception to describe entity is missing.
 *
 * @author <A href="mailto:leeaee@gmail.com">Lee yi</A>
 * 
 */
public class IdEntityException extends WebException {

	private static final long serialVersionUID = 1L;
	
    private static final String BASE_KEY = "exception.id";

    /**
     * 用一个消息的key构造，仅仅简单的说明。
     */
    public IdEntityException(String msg) {
        super(msg);
    }

    /**
     * 指明没有找到的实体的类型key和具体的实体主键. 如：
     * <pre>
     * throw new IdEntityException("entity.user", 1);
     * or
     * throw new IdEntityException(IdEntityException.GROUP, "sun");
     * </pre>
     *
     * @param entityKey 实体的类型的字典key
     * @param entityId  实体的主键
     */
    public IdEntityException() {
        super(BASE_KEY);
    }

    //Methods
}
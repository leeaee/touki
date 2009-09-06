package cn.touki.web.exception;

public class EntityDefaultDeleteException extends WebException {

	private static final long serialVersionUID = 1L;
    private static final String BASE_KEY = "exception.entity.defaultdelete";
	
	/**
	 *  返回的错误信息.
	 */ 

    //Constructor
    /**
     * 用一个消息的key构造，仅仅简单的说明。
     */
    public EntityDefaultDeleteException(String msg) {
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
    public EntityDefaultDeleteException(String entityKey, Object entityId) {
        super(BASE_KEY, new Object[]{"{" + entityKey + "}", entityId});
    }    
}

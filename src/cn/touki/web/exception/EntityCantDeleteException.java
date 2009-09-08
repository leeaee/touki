package cn.touki.web.exception;

public class EntityCantDeleteException extends WebException {

	private static final long serialVersionUID = 1L;
    private static final String BASE_KEY = "exception.entity.cantdelete";
	
	/**
	 *  返回的错误信息.
	 */ 

    //Constructor
    /**
     * 用一个消息的key构造，仅仅简单的说明。
     */
    public EntityCantDeleteException(String msg) {
        super(msg);
    }
    

    /**
     * 指明没有找到的实体的类型key和具体的实体主键. 如：
     * <pre>
     * throw new EntityNotFoundException("entity.user", "leeaee");
     * or
     * throw new EntityNotFoundException(EntityNotFoundException.GROUP, "sun");
     * </pre>
     *
     * @param entityKey 实体的类型的字典key
     * @param entityId  实体的主键
     */
    public EntityCantDeleteException(String entityKey, Object entityId) {
        super(BASE_KEY, new Object[]{"{" + entityKey + "}", entityId});
    }    
}

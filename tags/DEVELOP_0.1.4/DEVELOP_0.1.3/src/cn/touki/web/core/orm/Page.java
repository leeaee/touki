package cn.touki.web.core.orm;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 与具体ORM实现无关的分页参数及查询结果封装.
 * 
 * @param <T> Page中记录的类型.
 * @author Liyi
 */
public class Page<T> {
	
	/**
	 * 排序参数
	 */
	public static final String ASC = "asc";
	
	public static final String DESC = "desc";
	
	
	/**
	 * 每页显示参数
	 */
	public static final int MIN_PAGESIZE = 10;
	public static final int MAX_PAGESIZE = 100;

    /**
     * 总页数
     */
	protected int pageCount;
    
    /**
     * 当前页数
     */
	protected int pageIndex;
    
    /**
     * 当前页面显示记录数
     */
	protected int pageSize;
	
    /**
     * 总记录数
     */
	protected long entryCount;	
    
    /**
     * 记录开始索引
     */
	protected int entryFromIndex;

    /**
     * 记录结束索引
     */    
	protected int entryToIndex;    
	
	/**
	 * 排序属性参数
	 */
	protected String orderBy = null;
	
	/**
	 * 顺序 (asc or desc)
	 */
	protected String order = null;
	
	/**
	 * 自动重新获取长度
	 */
	protected boolean autoCount = true;	
	
	/**
	 * 对象结果
	 */
	protected List<T> result = Collections.emptyList();
	

	// 构造函数

	public Page() {
		super();
	}

	public Page(final int pageSize) {
		setPageSize(pageSize);
	}

	public Page(final int pageSize, final boolean autoCount) {
		setPageSize(pageSize);
		this.autoCount = autoCount;
	}

	
	//查询参数函数
	
	/**
	 * 取得总记录数.
	 */
	public long getEntryCount() {
		return entryCount;
	}

	public void setEntryCount(long entryCount) {
		this.entryCount = entryCount;
	}	

	/**
	 * 根据pageSize与entryCount计算总页数
	 */
	public int getPageCount() {
		
		pageCount = ((int)entryCount - 1) / pageSize + 1;
		
        if (pageIndex < 1 || pageIndex > pageCount) {
            throw new IndexOutOfBoundsException("pageIndex is invalid!");
        }		

		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}		

	/**
	 * 获得当前页的页号,序号从1开始,默认为1.
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
	 */
	public void setPageIndex(final int pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * 获得每页的记录数量.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页的记录数量,超出 MIN_PAGESIZE 与 MAX_PAGESIZE 范围时会自动调整.
	 */
	public void setPageSize(final int pageSize) {
		
		this.pageSize = pageSize;

		if (pageSize < MIN_PAGESIZE) {
			this.pageSize = MIN_PAGESIZE;
		}
		if (pageSize > MAX_PAGESIZE) {
			this.pageSize = MAX_PAGESIZE;
		}
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从0开始.
	 */
	public int getEntryStart() {
		return ((pageIndex - 1) * pageSize);
	}

	/**
	 * 获取每页开始条目数
	 */
	public int getEntryFromIndex() {
		
		entryFromIndex = getEntryStart() + 1;
		
		if (entryFromIndex < 1) entryFromIndex = 1; 
			
		return entryFromIndex;
	}

	public void setEntryFromIndex(int entryFromIndex) {
        this.entryFromIndex = entryFromIndex;
	}

	/**
	 * 获取每页结束条目数
	 */	
	public int getEntryToIndex() {
		
		entryToIndex = entryFromIndex + pageSize -1;
		
		if (entryToIndex > entryCount) {
			entryToIndex = (int)entryCount;
		}
		
		return entryToIndex;
	}

	public void setEntryToIndex(int entryToIndex) {
		this.entryToIndex = pageSize;
	}

	/**
	 * 获得排序字段,无默认值.多个排序字段时用','分隔,仅在Criterion查询时有效.
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * 设置排序字段.多个排序字段时用','分隔.仅在Criterion查询时有效.
	 */
	public void setOrderBy(final String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * 是否已设置排序字段,仅在Criterion查询时有效.
	 */
	public boolean isOrderBySetted() {
		return StringUtils.isNotBlank(orderBy);
	}

	/**
	 * 获得排序方向,默认为asc,仅在Criterion查询时有效.
	 * 
	 * @param order 可选值为desc或asc,多个排序字段时用','分隔.
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * 设置排序方式向,仅在Criterion查询时有效.
	 * 
	 * @param order 可选值为desc或asc,多个排序字段时用','分隔.
	 */
	public void setOrder(final String order) {
		//检查order字符串的合法值
		String[] orders = StringUtils.split(StringUtils.lowerCase(order), ',');
		for (String orderStr : orders) {
			if (!StringUtils.equals(DESC, orderStr) && !StringUtils.equals(ASC, orderStr))
				throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
		}

		this.order = StringUtils.lowerCase(order);
	}

	/**
	 * 查询对象时是否自动另外执行count查询获取总记录数,默认为false,仅在Criterion查询时有效.
	 */
	public boolean isAutoCount() {
		return autoCount;
	}

	/**
	 * 查询对象时是否自动另外执行count查询获取总记录数,仅在Criterion查询时有效.
	 */
	public void setAutoCount(final boolean autoCount) {
		this.autoCount = autoCount;
	}

	
	// 查询结果函数

	/**
	 * 取得页内的记录列表.
	 */
	public List<T> getResult() {
		return result;
	}

	public void setResult(final List<T> result) {
		this.result = result;
	}

	/**
	 * 是否还有下一页.
	 */
	public boolean isHasNext() {
		return (pageIndex + 1 <= getPageCount());
	}

	/**
	 * 取得下页的页号,序号从1开始. 
	 */
	public int getNextPage() {
		
		if (isHasNext()) return pageIndex + 1;
		else return pageIndex;
		
	}

	/**
	 * 是否还有上一页. 
	 */
	public boolean isHasPre() {
		return (pageIndex - 1 >= 1);
	}

	/**
	 * 取得上页的页号,序号从1开始.
	 */
	public int getPrePage() {
		if (isHasPre())
			return pageIndex - 1;
		else
			return pageIndex;
	}
}

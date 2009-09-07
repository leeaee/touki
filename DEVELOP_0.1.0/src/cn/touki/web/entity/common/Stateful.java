package cn.touki.web.entity.common;

/**
 * Interface to provide stateful properties for entities.
 *
 * @author Liyi
 */
public interface Stateful {

    /**
     * 状态：正常
     */
    int NORMAL = 0;

    /**
     * 状态：暂停
     */
    int PAUSED = 1;

    /**
     * 状态：暂停前预警期
     */
    int WARNING = 2;

    /**
     * 状态：过期
     */
    int EXPIRED = 3;

    /**
     * 状态：初始待审
     */
    int OPEN = 4;

    /**
     * 状态：关闭
     */
    int CLOSED = 5;

    /**
     * 未设置/不限制/所有
     */
    int NA = -1;

    /**
     * 状态的 KEY
     */
    String[] TEXT = new String[]{
        "option.state.normal",
        "option.state.paused",
        "option.state.warning",
        "option.state.expired",
        "option.state.open",
        "option.state.close",
        "option.state.na"
    };

    /**
     * 得到状态.
     *
     * @return 状态值
     */
    Integer getState();

    /**
     * 设置状态.
     *
     * @param state 状态值
     */
    void setState(Integer state);

} // end interface

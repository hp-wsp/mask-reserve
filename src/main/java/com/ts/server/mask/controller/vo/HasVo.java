package com.ts.server.mask.controller.vo;

/**
 * 查询记录存在
 *
 * @author TS Group
 */
public class HasVo {
    private final boolean has;
    private final Object data;

    private HasVo(boolean has, Object data) {
        this.has = has;
        this.data = data;
    }

    public boolean isHas() {
        return has;
    }

    public Object getData() {
        return data;
    }

    public static  HasVo hasData(boolean has, Object t){
        return new HasVo(has, t);
    }

    public static HasVo noneData(boolean has){
        return new HasVo(has, null);
    }
}

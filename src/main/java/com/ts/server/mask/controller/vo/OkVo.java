package com.ts.server.mask.controller.vo;

/**
 * 执行是否成功
 *
 * @author TS Group
 */
public class OkVo {
    private final boolean ok;

    public OkVo(boolean ok) {
        this.ok = ok;
    }

    public boolean isOk() {
        return ok;
    }
}

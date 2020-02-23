package com.ts.server.mask.common.id;

/**
 * 自动生成ID
 * 
 * @author TS Group
 */
public interface IdGenerator<T> {

	/**
	 * 生成ID
	 *
	 * @return id
	 */
	T generate();
	
}

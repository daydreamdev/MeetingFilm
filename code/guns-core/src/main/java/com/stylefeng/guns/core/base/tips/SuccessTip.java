package com.stylefeng.guns.core.base.tips;

/**
 * 返回给前台的成功提示
 *
 * @author fengshuonan
 * @date 2016年11月12日 下午5:05:22
 */
public class SuccessTip extends Tip {
	
	public SuccessTip(){
		super.status = 200;
		super.msg = "操作成功";
	}
}

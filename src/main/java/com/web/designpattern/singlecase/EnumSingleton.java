package com.web.designpattern.singlecase;

import javax.xml.crypto.Data;
import java.util.Date;

/****
 *	1、enum默认private私有化构造器
 *  2、定义interface和方法
 *  3、在枚举单元素实现
 */


interface MySingleton {
    //void doSomething();
}

public enum EnumSingleton implements MySingleton {
    INSTANCE;
	/*{
		@Override
		public void doSomething() {
		System.out.println("单列模式");
			
		}
	}*/

    public static EnumSingleton getInstance() {
        return EnumSingleton.INSTANCE;
    }

}



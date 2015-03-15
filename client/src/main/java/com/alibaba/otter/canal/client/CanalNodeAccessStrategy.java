package com.alibaba.otter.canal.client;

import java.net.SocketAddress;

/**
 * 集群节点访问控制接口
 * 
 * @author jianghang 2012-10-29 下午07:55:41
 * @version 1.0.0
 */

/*
 * note by cy
 *  这有必要一个文件就弄一个接口？？一个文件，一个工厂类，一个接口类，两个工厂方法利用匿名类返回不同的对象
 *  Simple: List<SocketAddress> nodes; int index; 
 *  问题：
 *  (1)java一个文件可以定义几个顶层类？内部类外部可见么？java的接口只有方法，没有数据？？数据不同，如何同类？
 *  (2)try {return xx;} finally {index++;}是何意义?
 */

public interface CanalNodeAccessStrategy {

    SocketAddress nextNode();
}

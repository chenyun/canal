package com.alibaba.otter.canal.client;

import java.net.SocketAddress;
import java.util.List;

import com.alibaba.otter.canal.client.impl.ClusterCanalConnector;
import com.alibaba.otter.canal.client.impl.ClusterNodeAccessStrategy;
import com.alibaba.otter.canal.client.impl.SimpleCanalConnector;
import com.alibaba.otter.canal.client.impl.SimpleNodeAccessStrategy;
import com.alibaba.otter.canal.common.zookeeper.ZkClientx;

/**
 * canal connectors创建工具类
 * 
 * @author jianghang 2012-10-29 下午11:18:50
 * @version 1.0.0
 * 
 */
/*
 * note by cy
 * Referenced by:
 *   canal.example: SimpleCanalClientTest.main(), ClusterCanalClientTest.main()
 *   
 * API:
 * CanalConnector newSimpleConnector(SocketAddress address, String destination, String username, String password) 单链接
 * CanalConnector newClusterConnector(List<? extends SocketAddress> addresses, String destination, String username, String password) 多链接?
 * CanalConnector newClusterConnector(
 * 
 * Brief:
 *    CanalConnectors是一个工厂类，提供三种工厂方法，分别是单链接的, 多服务器地址为参数的, zkServers为参数的，返回对象为
 *    CanalConnector(SimpleCanalConnector, ClusterCanalConnector), 其中后两种使用的多地址被
 *    CanalNodeAccessStrategy()所包装
 *    
 * DataS:
 *    new XXXCanalConnectors(username, password, destination, new XXXNodeAccessStrategy(addresses|zkServers))
 *    注意: String zkServers必须被ZkClientx.getZKClient(zkServers)处理才能作为NodeAccessStrategy的参数
 */
public class CanalConnectors {

    /**
     * 创建单链接的客户端链接
     * 
     * @param address
     * @param username
     * @param password
     * @return
     */
    public static CanalConnector newSingleConnector(SocketAddress address, String destination, String username,
                                                    String password) {
        SimpleCanalConnector canalConnector = new SimpleCanalConnector(address, username, password, destination);
        canalConnector.setSoTimeout(30 * 1000);
        return canalConnector;
    }

    /**
     * 创建带cluster模式的客户端链接，自动完成failover切换
     * 
     * @param addresses
     * @param username
     * @param password
     * @return
     */
    public static CanalConnector newClusterConnector(List<? extends SocketAddress> addresses, String destination,
                                                     String username, String password) {
        ClusterCanalConnector canalConnector = new ClusterCanalConnector(username, password, destination,
                                                                         new SimpleNodeAccessStrategy(addresses));
        canalConnector.setSoTimeout(30 * 1000);
        return canalConnector;
    }

    /**
     * 创建带cluster模式的客户端链接，自动完成failover切换，服务器列表自动扫描
     * 
     * @param username
     * @param password
     * @return
     */
    public static CanalConnector newClusterConnector(String zkServers, String destination, String username,
                                                     String password) {
        ClusterCanalConnector canalConnector = new ClusterCanalConnector(
                                                                         username,
                                                                         password,
                                                                         destination,
                                                                         new ClusterNodeAccessStrategy(
                                                                                                       destination,
                                                                                                       ZkClientx.getZkClient(zkServers)));
        canalConnector.setSoTimeout(30 * 1000);
        return canalConnector;
    }
}

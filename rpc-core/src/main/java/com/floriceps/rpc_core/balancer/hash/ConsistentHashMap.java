package com.floriceps.rpc_core.balancer.hash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.TreeMap;

public class ConsistentHashMap {

    /**
     * Hash 环
     */
    private TreeMap<Integer, String> treeMapHash;
    private static Logger log = LoggerFactory.getLogger(ConsistentHashMap.class);

    public ConsistentHashMap() {
        this.treeMapHash = new TreeMap<>();
    }

    /**
     * 添加服务器节点。
     * @param serverNodeName 服务器节点名称。
     */
    public void addServerNode(String serverNodeName) {
        int hash = new DefaultHash().hashCode(serverNodeName);
        treeMapHash.put(hash, serverNodeName);
        log.info("服务器节点：{} 上线", serverNodeName);
    }

    /**
     * 删除服务器节点.
     * @param serverNodeName 服务器节点名称。
     */
    public void delServerNode(String serverNodeName) {
        int hash = new DefaultHash().hashCode(serverNodeName);
        treeMapHash.remove(hash);
        log.info("服务器节点：{} 下线", serverNodeName);
    }

    /**
     * 通过客户端的请求 URL 选择最终的服务器节点。
     * @param requestURL 客户端请求 URL。
     * @return 被选中的服务器节点。
     */
    public String selectServerNode(String requestURL) {
        int hash = new DefaultHash().hashCode(requestURL);
        // 向右寻找第一个 key
        Map.Entry<Integer, String> subEntry = treeMapHash.ceilingEntry(hash);
        // 设置成一个环，如果超过尾部，则取第一个点
        subEntry = subEntry == null ? treeMapHash.firstEntry() : subEntry;
        String VNNode = subEntry.getValue();
        return VNNode.substring(0, VNNode.indexOf("&&"));
    }
}

package com.floriceps.rpc_core.balancer.hash;

/**
 * Hash 算法接口
 */
public interface Hash {
    int hashCode(String origin);
}

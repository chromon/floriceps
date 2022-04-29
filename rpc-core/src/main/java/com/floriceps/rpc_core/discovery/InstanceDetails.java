package com.floriceps.rpc_core.discovery;

/**
 * 服务实例详细信息。
 */
public class InstanceDetails {
    /**
     * 服务说明信息
     */
    private String description;

    /**
     * 服务权重
     */
    private int weight;

    public InstanceDetails() {}

    public InstanceDetails(String description) {
        this.description = description;
        this.weight = 0;
    }

    public InstanceDetails(String description, int weight) {
        this.description = description;
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "InstanceDetails {" +
                "description='" + description + '\'' +
                ", weight=" + weight +
                '}';
    }
}

package com.bjtu.sdtest.pojo.table;

public class Dataset {
    private String datasetName;

    private Integer datasetId;

    private String datasetLocation;

    private Integer userId;

    private String name;

    public String getDatasetName() {
        return datasetName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName == null ? null : datasetName.trim();
    }

    public Integer getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(Integer datasetId) {
        this.datasetId = datasetId;
    }

    public String getDatasetLocation() {
        return datasetLocation;
    }

    public void setDatasetLocation(String datasetLocation) {
        this.datasetLocation = datasetLocation == null ? null : datasetLocation.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}
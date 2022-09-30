package com.bjtu.sdtest.mapper;

import com.bjtu.sdtest.pojo.table.Dataset;
import com.bjtu.sdtest.pojo.table.DatasetExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DatasetMapper {
    int countByExample(DatasetExample example);

    int deleteByExample(DatasetExample example);

    int deleteByPrimaryKey(@Param("datasetName") String datasetName, @Param("datasetId") Integer datasetId);

    int insert(Dataset record);

    int insertSelective(Dataset record);

    List<Dataset> selectByExample(DatasetExample example);

    Dataset selectByPrimaryKey(@Param("datasetName") String datasetName, @Param("datasetId") Integer datasetId);

    int updateByExampleSelective(@Param("record") Dataset record, @Param("example") DatasetExample example);

    int updateByExample(@Param("record") Dataset record, @Param("example") DatasetExample example);

    int updateByPrimaryKeySelective(Dataset record);

    int updateByPrimaryKey(Dataset record);
}
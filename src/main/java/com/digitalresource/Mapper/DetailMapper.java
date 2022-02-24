package com.digitalresource.Mapper;

import com.digitalresource.Entity.ChartCount;
import com.digitalresource.Entity.Detail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DetailMapper {
    public int registDetails(@Param("resource_id") int resource_id,
                             @Param("list") List<Detail> detailList);

    public List<Detail> SelectDetailListByResource(@Param("resource_id") int resource_id);

  ChartCount SelectFileCount(@Param("user_group") int user_group);
}

package com.digitalresource.Mapper;

import com.digitalresource.Entity.Trait;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TraitMapper {

  List<Trait> SelectTraitList();

  int SelectTraitCount();
}

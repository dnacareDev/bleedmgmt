package com.digitalresource.Mapper;

import com.digitalresource.Entity.Trait;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TraitMapper {

  List<Trait> SelectTraitList();

  int SelectTraitCount();

  int insertTrait(Map<String, Object> param);

  int changeTrait(Map<String, Object> param);

  List<Trait> traitDescriptionList();
}

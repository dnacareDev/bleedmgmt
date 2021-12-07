package com.digitalresource.ServiceImple;

import com.digitalresource.Entity.Trait;
import com.digitalresource.Mapper.TraitMapper;
import com.digitalresource.Service.TraitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TraitServiceImpl implements TraitService {

  @Autowired
  private TraitMapper mapper;

  @Override
  public List<Trait> SelectTraitList() {
    return mapper.SelectTraitList();
  }

  @Override
  public int SelectTraitCount() {
    return mapper.SelectTraitCount();
  }

	@Override
	public int insertTrait(Map<String, Object> param) {
		return mapper.insertTrait(param);
	}

	@Override
	public int changeTrait(Map<String, Object> param) {
		return mapper.changeTrait(param);
	}
}

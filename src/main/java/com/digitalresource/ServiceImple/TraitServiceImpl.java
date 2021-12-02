package com.digitalresource.ServiceImple;

import com.digitalresource.Entity.Trait;
import com.digitalresource.Mapper.TraitMapper;
import com.digitalresource.Service.TraitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
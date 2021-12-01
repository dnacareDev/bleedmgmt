package com.digitalresource.Service;

import com.digitalresource.Entity.Trait;

import java.util.List;

public interface TraitService {

  List<Trait> SelectTraitList();

  int SelectTraitCount();

}

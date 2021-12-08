package com.digitalresource.Service;

import java.util.List;
import java.util.Map;

import com.digitalresource.Entity.Trait;

public interface TraitService {

	List<Trait> SelectTraitList();

	int SelectTraitCount();

	int insertTrait(Map<String, Object> param);

	int changeTrait(Map<String, Object> param);

	List<Trait> traitDescriptionList();

}

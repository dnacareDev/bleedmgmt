package com.digitalresource.Service;

import com.digitalresource.Entity.Character;
import com.digitalresource.Entity.Feature;

import java.util.List;

public interface FeatureService {
    public List<Feature> selectFeatureByCharacter(int character_id);

    public int registFeatureList(Character character, List<Feature> featureList);

    public int deleteFeatureByCharacter(int character_id);
}

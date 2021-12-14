package com.digitalresource.Service;

import com.digitalresource.Entity.Character;
import com.digitalresource.Entity.Feature;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface FeatureService {
    public List<Feature> selectFeatureByCharacter(int character_id);

    public int registFeatureList(Character character, List<Feature> featureList);

    public int deleteFeatureByCharacter(int character_id);

	public int seedResourcesInsert(String attribute_item, String crop_name, String cropCategory, String file_name,String origin_file_name, String feature_count, int cropSubCategory );
}

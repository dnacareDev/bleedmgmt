package com.digitalresource.ServiceImple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalresource.Entity.Character;
import com.digitalresource.Entity.Crop;
import com.digitalresource.Entity.Feature;
import com.digitalresource.Mapper.FeatureMapper;
import com.digitalresource.Service.CropService;
import com.digitalresource.Service.FeatureService;
import com.digitalresource.Service.TraitService;

@Service
public class FeatureServiceImpl implements FeatureService {

  @Autowired
  private FeatureMapper featureMapper;

  @Autowired
  private CropService cropService;

  @Autowired
  private TraitService traitService;

  @Override
  public List<Feature> selectFeatureByCharacter(int character_id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int registFeatureList(Character character, List<Feature> featureList) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int deleteFeatureByCharacter(int character_id) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int seedResourcesInsert(String attribute_item, String crop_name, String cropCategory, String file_name, String origin_file_name, String feature_count, int cropSubCategory) {
    Map<String, Object> param = new HashMap<String, Object>();
    JSONArray arr = new JSONArray(attribute_item);
    if (!crop_name.isEmpty()) {
      // insert crop
      int category_id = Integer.parseInt(cropCategory);
      Crop crop = new Crop();
      crop.setCategory_id(category_id);
      crop.setCrop_name(crop_name);
      cropService.registCrop(crop);
    }

    // select feature group
    int feature_group = featureMapper.selectFeatureGroup();

    Feature feature = new Feature();
    feature.setFeature_group(feature_group);
    for (int i = 0; i < arr.length(); i++) {
      JSONObject jsonObject = arr.getJSONObject(i);

      System.out.println(jsonObject);

      feature.setFeature_num(Double.valueOf(String.valueOf(jsonObject.get("feature_num"))));
      feature.setFeature_name(String.valueOf(jsonObject.get("feature_name")));
      if(jsonObject.has("feature_level")) {
        String feature_level = String.valueOf(jsonObject.get("feature_level"));
        feature.setFeature_level(feature_level);
      } else if(!jsonObject.has("feature_level")) {
        feature.setFeature_level("");
      }
      if(jsonObject.has("feature_expression")) {
        feature.setFeature_expression(String.valueOf(jsonObject.get("feature_expression")));
      } else if(!jsonObject.has("feature_expression")) {
        feature.setFeature_expression("");
      }
      feature.setFeature_investigate(String.valueOf(jsonObject.get("feature_investigation")));
      feature.setFeature_file(file_name);
      feature.setFeature_origin_file(origin_file_name);
      featureMapper.seedResourcesInsert(feature);
    }

    // 입력이 끝나고 나서 trait insert
    param.put("crop_name", crop_name);
    param.put("feature_group", feature_group);
    param.put("feature_count", feature_count);
    param.put("cropSubCategory", cropSubCategory);
    param.put("trait_file", file_name);
    int result = traitService.insertTrait(param);

    return result;
  }

}

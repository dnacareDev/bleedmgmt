package com.digitalresource.ServiceImple;

import com.digitalresource.Entity.*;
import com.digitalresource.Mapper.BreedMapper;
import com.digitalresource.Service.BreedService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BreedServiceImpl implements BreedService {

  @Autowired
  private BreedMapper breedMapper;

  @Override
  public int insertBreed(int resource_id, String data, int crop_id, String resource_name) {
    Map<String, Object> param = new HashMap<String, Object>();
    Map<String, Object> map = new HashMap<String, Object>();
    int result = 0;
    param.put("crop_id", crop_id);
    param.put("resource_name", resource_name);
    resource_id = breedMapper.selectResourceId(param);
    Breed breed = new Breed();
    breed.setResource_id(resource_id);
    // insert breed
    int test = breedMapper.insertBreed(breed);
    map.put("breed_id", breed.getBreed_id());
    CountSelect countDetail = breedMapper.selectDetailCount(resource_id);

    // insert standard
    JSONArray arr = new JSONArray(data);
    int arrLength = arr.length();
    int arrMin = 0;
    String changeI = "";
    int cnt = 0;
    for (int i = countDetail.getMin(); i <= countDetail.getMax(); i++) {
      if (arrMin != arrLength) {
        JSONObject jsonObject = arr.getJSONObject(arrMin);
        changeI = Integer.toString(i);
        if (changeI.equals(String.valueOf(jsonObject.get("detail_id")))) {
          map.put("resource_id", resource_id);
          map.put("limit", cnt);
          map.put("standard_data", jsonObject.get("standard"));
          arrMin++;
        } else {
          map.put("resource_id", resource_id);
          map.put("limit", cnt);
          map.put("standard_data", null);
        }
      } else {
        map.put("resource_id", resource_id);
        map.put("limit", cnt);
        map.put("standard_data", null);
      }
      result = breedMapper.insertStandard(map);
      cnt++;
    }
    return result;
  }

  @Override
  public int insertStandard() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int updateStandardCell(StandardList data) {
    int result = 0;
    result = breedMapper.updateStandardCell(data);
    return result;
  }

  @Override
  public List<Map<String, Object>> selectStandard(int resourceId) {
    List<StandardList> list = breedMapper.selectStandard(resourceId);
    List<Map<String, Object>> bodyList = new ArrayList<Map<String, Object>>();
    Map<String, Object> dataMap = new HashMap<String, Object>();
//    int idx = 0;
    int breed_id = 0;
    int detail_id = 0;
    int breed_row = 0;

    if (list.size() != 0) {
      breed_row = list.get(0).getBreed_row();
//      idx = 1;
    }

    int count = 0;

    for (StandardList item : list) {
      System.out.println(count);
      if (item.getBreed_row() == breed_row) {
        dataMap.put(String.valueOf(item.getDetail_id()), item);
        //System.out.println("breed_id : " + dataMap.get("breed_id"));
      } else {
        dataMap.put("breed_row", item.getBreed_row());
        dataMap.put("breed_id", item.getBreed_id());

        System.out.println("breed_id : " + dataMap.get("breed_id"));

        dataMap.put("detail_id", item.getDetail_id());
//        dataMap.put("idx", idx++);
        bodyList.add(dataMap);

        breed_row = item.getBreed_row();
        breed_id = item.getBreed_id();
        detail_id = item.getDetail_id();
        dataMap = new HashMap<String, Object>();
        dataMap.put(String.valueOf(item.getDetail_id()), item);
      }

      count++;
    }

//    dataMap.put("idx", idx++);
    dataMap.put("breed_row", breed_row);
    dataMap.put("breed_id", breed_id);
    dataMap.put("detail_id", detail_id);

    bodyList.add(dataMap);

    return bodyList;
  }

  @Override
  public List<StandardList> SelectStandard(int breed_id) {
    return breedMapper.SelectStandard(breed_id);
  }

  @Override
  public int deleteBreed(String breed_id) {

    int deleteResult = breedMapper.deleteStandard(breed_id);
    int result = 0;
    if (deleteResult != 0) {
      breedMapper.deleteStandards(breed_id);
      result = breedMapper.deleteBreed(breed_id);
      System.out.println(result);
    }
    return result;
  }

  @Override
  public int InsertBreedFile(BreedFile breed_file) {
    return breedMapper.InsertBreedFile(breed_file);
  }

  @Override
  public int InsertBreedUpload(Uploads upload) {
    return breedMapper.InsertBreedUpload(upload);
  }

  @Override
  public List<BreedFile> SelectBreedFile(int breed_id, int file_type) {
    return breedMapper.SelectBreedFile(breed_id, file_type);
  }

  @Override
  public int UpdateBreedFile(BreedFile breed_file) {
    return breedMapper.UpdateBreedFile(breed_file);
  }

  @Override
  public int UpdateBreedUpload(Uploads upload) {
    return breedMapper.UpdateBreedUpload(upload);
  }

  @Override
  public List<Breed> SearchBreed(String breed_name) {
    return breedMapper.SearchBreed(breed_name);
  }

  @Override
  public List<Breed> SearchBreed2(String breed_name, int resource_id) {
    return breedMapper.SearchBreed2(breed_name, resource_id);
  }

  @Override
  public List<Breed> SearchBreed3(int resource_id) {
    return breedMapper.SearchBreed3(resource_id);
  }

  @Override
  public String SearchCropName(int breed_name) {
    return breedMapper.SearchCropName(breed_name);
  }

  @Override
  public int InsertBreed(Breed breed) {
    return breedMapper.InsertBreed(breed);
  }

  @Override
  public List<Detail> SelectDetailExcel(int resource_id) {
    return breedMapper.SelectDetailExcel(resource_id);
  }

  @Override
  public List<Detail> SelectDetailExcel2(int resource_id) {
    return breedMapper.SelectDetailExcel2(resource_id);
  }

  @Override
  public int InsertExcel(List<StandardList> standards) {
    return breedMapper.InsertExcel(standards);
  }

  @Override
  public List<StandardList> SelectBreedStandard(int breed_id) {
    return breedMapper.SelectBreedStandard(breed_id);
  }

  @Override
  public List<StandardList> SelectBreedStandard2(int breed_id) {
    return breedMapper.SelectBreedStandard2(breed_id);
  }

  @Override
  public int UpdateBreed(int breed_id, int detail_id, String standard) {
    return breedMapper.UpdateBreed(breed_id, detail_id, standard);
  }

  @Override
  public int UpdateAllBreed(List<StandardList> list) {
    return breedMapper.UpdateAllBreed(list);
  }
}

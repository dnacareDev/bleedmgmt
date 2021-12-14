package com.digitalresource.ServiceImple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalresource.Entity.Breed;
import com.digitalresource.Entity.StandardList;
import com.digitalresource.Mapper.BreedMapper;
import com.digitalresource.Service.BreedService;
import com.fasterxml.jackson.databind.ser.std.StdArraySerializers.IntArraySerializer;

@Service
public class BreedServiceImpl implements BreedService {

	@Autowired
	private BreedMapper breedMapper;
	
	@Override
	public int insertBreed(int resource_id, String data) {
		Map<String,Object> map = new HashMap<String, Object>();
		int result = 0;
		Breed breed = new Breed();
		breed.setResource_id(resource_id);
		// insert breed
		int test = breedMapper.insertBreed(breed);
		map.put("breed_id", breed.getBreed_id());
		int countDetail = breedMapper.selectDetailCount(resource_id);
		// insert standard
		JSONArray arr = new JSONArray(data);
		int arrLength = arr.length();
		int arrMin = 0;
		String changeI = "";
		for(int i=1; i<=countDetail; i++) {
			if(arrMin != arrLength) {				
				JSONObject jsonObject = arr.getJSONObject(arrMin);
				changeI = Integer.toString(i);
				if(changeI.equals(String.valueOf(jsonObject.get("detail_id")))) {
					map.put("resource_id",resource_id);
					map.put("limit",(int)jsonObject.get("detail_id") -1);
					map.put("standard_data", jsonObject.get("standard"));
					arrMin++;
				}else {
					map.put("resource_id",resource_id);
					map.put("limit",i);
					map.put("standard_data", "");
				}
				
			
			}else {				
					map.put("resource_id",resource_id);
					map.put("limit",i);
					map.put("standard_data", "");
			}
			result = breedMapper.insertStandard(map);
		}
		return result;
	}

	@Override
	public int insertStandard() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Map<String, Object>> selectStandard(int resourceId) {
		
		List<StandardList> list =  breedMapper.selectStandard(resourceId);
		List<Map<String, Object>> bodyList = new ArrayList<Map<String, Object>>();
		Map<String , Object> dataMap = new HashMap<String , Object>();
		//Map<String , Object> breed = new HashMap<String , Object>();
		int breed_row = list.get(0).getBreed_row();		
			for(StandardList item : list) {
				if(item.getBreed_row() == breed_row) {
					dataMap.put(String.valueOf(item.getDetail_id()) , item.getStandard_data());

				}else {
					dataMap.put("breed_id", breed_row);
					bodyList.add(dataMap);
					//bodyList.add(breed);
					breed_row = item.getBreed_row();
					dataMap = new HashMap<String , Object>();
					//breed = new HashMap<String , Object>();
					dataMap.put(String.valueOf(item.getDetail_id()) , item.getStandard_data());
					//breed.put("breed_id", item.getBreed_row());
				}
			}
			dataMap.put("breed_id", breed_row);
			bodyList.add(dataMap);
			//bodyList.add(breed);
		return bodyList;
	}

	@Override
	public int deleteBreed(String breed_id) {
		int deleteResult = breedMapper.deleteStandard(breed_id);
		int result = 0;
		if(deleteResult != 0) {
			result = breedMapper.deleteBreed(breed_id);
		}
		return result;
	}
}

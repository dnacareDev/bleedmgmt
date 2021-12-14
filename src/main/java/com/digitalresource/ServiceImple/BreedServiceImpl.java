package com.digitalresource.ServiceImple;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalresource.Entity.Breed;
import com.digitalresource.Mapper.BreedMapper;
import com.digitalresource.Service.BreedService;

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
		// insert standard
		JSONArray arr = new JSONArray(data);
		for(int i=0; i<arr.length(); i++) {
			JSONObject jsonObject = arr.getJSONObject(i);
			System.out.println(jsonObject);
			map.put("detail_id", jsonObject.get("detail_id"));
			map.put("standard_data", jsonObject.get("standard"));
			result = breedMapper.insertStandard(map);
		}
		return result;
	}

	@Override
	public int insertStandard() {
		// TODO Auto-generated method stub
		return 0;
	}

}

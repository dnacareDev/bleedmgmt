package com.digitalresource.Service;

import com.digitalresource.Entity.Breed;

public interface BreedService {
	
	public int insertStandard();

	int insertBreed(int resource_id, String data);
}

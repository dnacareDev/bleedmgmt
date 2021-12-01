package com.digitalresource.Service;

import com.digitalresource.Entity.Character;

import java.util.List;

public interface CharacterService {
    public List<Character> selectCharacterByCrop(int crop_id);

    public List<Character> selectCharacterList();

    public Character selectCharacterById(int character_id);

    public int registCharacter(Character character);

    public int deleteCharacter(int character_id);
}

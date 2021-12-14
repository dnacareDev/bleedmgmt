package com.digitalresource.Mapper;

import com.digitalresource.Entity.Character;
import com.digitalresource.Entity.Feature;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CharacterMapper {
    public List<Character> selectCharacterByCrop(int crop_id);

    public List<Character> selectCharacterList();

    public Character selectCharacterById(int character_id);

    public int registCharacter(Character character);

    public int deleteCharacter(int character_id);
}

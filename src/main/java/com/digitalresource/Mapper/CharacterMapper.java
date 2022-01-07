package com.digitalresource.Mapper;

import com.digitalresource.Entity.Character;
import com.digitalresource.Entity.Feature;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CharacterMapper {
    public List<Character> selectCharacterByCrop(@Param("crop_id") int crop_id);

    public List<Character> selectCharacterList();

    public Character selectCharacterById(@Param("character_id") int character_id);

    public int registCharacter(Character character);

    public int deleteCharacter(@Param("character_id") int character_id);
}

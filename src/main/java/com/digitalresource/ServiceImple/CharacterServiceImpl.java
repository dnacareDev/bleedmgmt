package com.digitalresource.ServiceImple;

import com.digitalresource.Entity.Character;
import com.digitalresource.Entity.Feature;
import com.digitalresource.Entity.File;
import com.digitalresource.Mapper.CharacterMapper;
import com.digitalresource.Service.CharacterService;
import com.digitalresource.Service.FeatureService;
import com.digitalresource.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CharacterServiceImpl implements CharacterService{
    @Autowired
    private CharacterMapper mapper;

    @Autowired
    private FeatureService featureService;

    @Autowired
    private FileService fileService;

    @Override
    public List<Character> selectCharacterByCrop(int crop_id) {
        List<Character> characterList = mapper.selectCharacterByCrop(crop_id);
        return characterList;
    }

    @Override
    public List<Character> selectCharacterList() {
        List<Character> characterList = mapper.selectCharacterList();
        return characterList;
    }

    @Override
    public Character selectCharacterById(int character_id) {
        Character character = mapper.selectCharacterById(character_id);
        if(character == null){
            //Err
            return null;
        }

        List<Feature> featureList = featureService.selectFeatureByCharacter(character_id);
        character.setFeatureList(featureList);

        return character;
    }

    @Override
    public int registCharacter(Character character) {
        int result = -1;
        result = mapper.registCharacter(character);
        if(result <= 0){
            //Err
            return result;
        }

        File file = character.getCharacter_file();
        result = fileService.registFile(file);
        if(result < 0){
            //Err
            return result;
        }
        character.setCharacter_file_id(file.getFile_id());

        result = featureService.registFeatureList(character, character.getFeatureList());
        if(result < 0){
            //Err
            return result;
        }
        return result;
    }

    @Override
    public int deleteCharacter(int character_id) {
        int result = -1;

        Character character = mapper.selectCharacterById(character_id);

        result = mapper.deleteCharacter(character_id);
        if(result <= 0){
            //Err
            return result;
        }
        result = featureService.deleteFeatureByCharacter(character_id);
        if(result != character.getFeature_count()){
            //Err
            return result;
        }
        fileService.deleteFile(character.getCharacter_file_id());

        return result;
    }
}

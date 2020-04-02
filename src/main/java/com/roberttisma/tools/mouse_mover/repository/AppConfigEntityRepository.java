package com.roberttisma.tools.mouse_mover.repository;


import com.roberttisma.tools.mouse_mover.entity.AppConfigEntity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.roberttisma.tools.mouse_mover.entity.AppConfigEntity.DESERIALIZER;
import static com.roberttisma.tools.mouse_mover.entity.AppConfigEntity.SERIALIZER;
import static com.roberttisma.tools.mouse_mover.utils.Checks.checkArgument;
import static com.roberttisma.tools.mouse_mover.utils.Checks.checkNotNull;
import static com.roberttisma.tools.mouse_mover.utils.Files.readLines;
import static com.roberttisma.tools.mouse_mover.utils.Files.writeLines;
import static com.roberttisma.tools.mouse_mover.utils.Strings.checkBlank;

public class AppConfigEntityRepository implements Repository<String, AppConfigEntity> {

  private final File storageFile;

  public AppConfigEntityRepository(File storageFile) {
    checkNotNull(storageFile);
    this.storageFile = storageFile;
  }

  @Override
  public List<String> listIds() {
    List<String> ids = new ArrayList<String>();
    for (AppConfigEntity c : findAll()){
      ids.add(c.getId());
    }
    return ids;
  }

  @Override
  public List<AppConfigEntity> findAll() {
    return loadConfigs();
  }

  @Override
  public AppConfigEntity findOne(String id) {
    checkBlank(id, "The id cannot be blank");
    List<AppConfigEntity> configs = loadConfigs();
    for( AppConfigEntity c : configs){
      if (c.getId().equals(id)){
        return c;
      }
    }
    return null;
  }

  @Override
  public void save(AppConfigEntity appConfig) {
    checkArgument(appConfig != null, "The input appConfig object cannot be null");
    List<AppConfigEntity> configs = loadConfigs();
    mergeConfig(configs, appConfig);
    writeConfigs(configs);
  }


  @Override
  public AppConfigEntity delete(String id) {
    checkBlank(id, "The id cannot be blank");
    List<AppConfigEntity> configs = loadConfigs();
    for(int i=0; i<configs.size(); i++){
      AppConfigEntity c = configs.get(i);
      if (c.getId().equals(id)){
        configs.remove(i);
        writeConfigs(configs);
        return c;
      }
    }
    return null;
  }

  private void mergeConfig(List<AppConfigEntity> configs, AppConfigEntity configToMergeIn){
    for(int i=0; i<configs.size(); i++){
      AppConfigEntity c = configs.get(i);
      if (c.getId().equals(configToMergeIn.getId())){
        configs.set(i, configToMergeIn);
        return;
      }
    }
    configs.add(configToMergeIn);
  }

  private List<AppConfigEntity> loadConfigs(){
    try {
      return readLines(storageFile, DESERIALIZER);
    } catch (FileNotFoundException e){
      return new ArrayList<AppConfigEntity>();
    } catch (IOException e) {
      throw new IllegalStateException("Could not load configs", e);
    }
  }

  private void writeConfigs(List<AppConfigEntity> configs) {
    try {
      writeLines(configs, SERIALIZER, storageFile);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }
}

package ro.wolfnet.programmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.wolfnet.programmanager.entity.ProgramEntity;
import ro.wolfnet.programmanager.repository.ProgramRepository;

@Service
public class ProgramService {

  @Autowired
  private ProgramRepository programRepository;
  
  public void insertDummy() {
    ProgramEntity program = new ProgramEntity();
    programRepository.save(program);
  }
  
  public void findAll() {
    programRepository.findAll();
  }

}

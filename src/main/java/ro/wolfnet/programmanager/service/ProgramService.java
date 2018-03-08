package ro.wolfnet.programmanager.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.wolfnet.programmanager.entity.ProgramEntity;
import ro.wolfnet.programmanager.model.EmployeeModel;
import ro.wolfnet.programmanager.model.EmployeeStatusModel;
import ro.wolfnet.programmanager.model.ProgramModel;
import ro.wolfnet.programmanager.model.StationModel;
import ro.wolfnet.programmanager.repository.ProgramRepository;
import ro.wolfnet.programmanager.service.generate.AssignedStationsRule;
import ro.wolfnet.programmanager.service.generate.GenerateRule;
import ro.wolfnet.programmanager.service.generate.LessWorkedRule;
import ro.wolfnet.programmanager.utils.IncompatibleRulesException;

/**
 * The Class ProgramService.
 *
 * @author isti
 * @since Feb 20, 2018
 */
@Service
public class ProgramService {

  /** The program repository. */
  @Autowired
  private ProgramRepository programRepository;

  /** The station service. */
  @Autowired
  private StationService stationService;

  /** The employee service. */
  @Autowired
  private EmployeeService employeeService;

  /**
   * Insert dummy.
   */
  public void insertDummy() {
    ProgramEntity program = new ProgramEntity();
    programRepository.save(program);
  }

  /**
   * Find all.
   *
   * @param dayOfProgram the day of program
   * @return the list
   */
  public List<ProgramModel> findAllForOneDay(Date dayOfProgram) {
    List<ProgramEntity> entities = programRepository.findAllByDate(dayOfProgram);
    List<ProgramModel> models = new ArrayList<>();
    for (ProgramEntity entity : entities) {
      int stationIdx = models.indexOf(new ProgramModel(entity.getStation().getName()));
      if (stationIdx > -1) {
        models.get(stationIdx).appendEmployee(employeeService.getModelFromEntity(entity.getEmployee()));
      }
      else {
        models.add(getModelFromEntity(entity));
      }
    }
    return models;
  }

  /**
   * Gets the model from entity.
   *
   * @param entity the entity
   * @return the model from entity
   */
  private ProgramModel getModelFromEntity(ProgramEntity entity) {
    if (entity == null) {
      return null;
    }

    ProgramModel model = new ProgramModel();
    model.setStationName(entity.getStation().getName());
    model.appendEmployee(employeeService.getModelFromEntity(entity.getEmployee()));
    return model;
  }

  /**
   * Generate program for one day.
   *
   * @param dayOfProgram the day of program
   * @throws IncompatibleRulesException the incompatible rules exception
   */
  public void generateProgramsForOneDay(Date dayOfProgram) throws IncompatibleRulesException {
    programRepository.deleteByDate(dayOfProgram);

    List<StationModel> allStations = stationService.findAll();
    List<EmployeeStatusModel> allEmployees = employeeService.findEmployeeStatuses();
    List<ProgramEntity> programsForDay = null;
    for (long generateStartMillis = System.currentTimeMillis(); programsForDay == null;) {
      programsForDay = getProgramsForOneDay(dayOfProgram, allStations, copyEmployeeList(allEmployees));
      if ((System.currentTimeMillis() - generateStartMillis) > 10 * 1000) {
        throw new IncompatibleRulesException();
      }
    }

    programRepository.save(programsForDay);
  }

  /**
   * Copy employee list.
   *
   * @param allEmployees the all employees
   * @return the list
   */
  private List<EmployeeStatusModel> copyEmployeeList(List<EmployeeStatusModel> allEmployees) {
    if (allEmployees == null || allEmployees.size() == 0) {
      return null;
    }

    List<EmployeeStatusModel> result = new ArrayList<>();
    for (EmployeeStatusModel employee : allEmployees) {
      result.add(employee);
    }
    return result;
  }

  /**
   * Gets the programs for one day.
   *
   * @param dayOfProgram the day of program
   * @param allStations the all stations
   * @param allEmployees the all employees
   * @return the programs for one day
   */
  private List<ProgramEntity> getProgramsForOneDay(Date dayOfProgram, List<StationModel> allStations, List<EmployeeStatusModel> allEmployees) {
    try {
      List<ProgramEntity> programsForDay = new ArrayList<>();
      for (StationModel station : allStations) {
        programsForDay.addAll(getProgramsForStation(station, dayOfProgram, allEmployees));
      }
      return programsForDay;
    } catch (IncompatibleRulesException e) {
      System.out.println("Failed to generate for day: " + dayOfProgram);
      //ignore incompatibility and try again
    }
    return null;
  }

  /**
   * Gets the programs for station.
   *
   * @param station the station
   * @param date the date
   * @param allEmployees the all employees
   * @return the programs for station
   * @throws IncompatibleRulesException the incompatible rules exception
   */
  private List<ProgramEntity> getProgramsForStation(StationModel station, Date date, List<EmployeeStatusModel> allEmployees) throws IncompatibleRulesException {
    if (station == null || station.getCapacity() == 0) {
      throw new IncompatibleRulesException();
    }

    List<ProgramEntity> newPrograms = new ArrayList<>();
    for (int cnt = 0; cnt < station.getCapacity(); cnt++) {
      EmployeeStatusModel chosenEmployee = getRandomEmployeeModelFromList(station.getId(), allEmployees);
      allEmployees.remove(chosenEmployee);

      ProgramEntity program = new ProgramEntity();
      program.setEmployee(employeeService.getEntityFromModel(chosenEmployee));
      program.setStation(stationService.getEntityFromModel(station));
      program.setWorkedHours(24);
      program.setDate(date);
      newPrograms.add(program);
    }
    return newPrograms;
  }

  /** The generate rules. */
  private static List<GenerateRule> generateRules = Arrays.asList(new AssignedStationsRule(), new LessWorkedRule());

  /**
   * Gets the random employee model from list.
   *
   * @param stationId the station id
   * @param allEmployees the all employees
   * @return the random employee model from list
   * @throws IncompatibleRulesException the incompatible rules exception
   */
  private EmployeeStatusModel getRandomEmployeeModelFromList(long stationId, List<EmployeeStatusModel> allEmployees) throws IncompatibleRulesException {
    if (allEmployees == null || allEmployees.size() == 0) {
      throw new IncompatibleRulesException();
    }

    for (GenerateRule rule : generateRules) {
      allEmployees = rule.filterEmployees(stationId, allEmployees);
    }

    try {
      int idx = (int) (Math.random() * (allEmployees.size() - 0)) + 0;
      return allEmployees.get(idx);
    } catch (IndexOutOfBoundsException e) {
      throw new IncompatibleRulesException();
    }
  }

  /**
   * Generate programs for one month.
   *
   * @param monthDay the month day
   * @throws IncompatibleRulesException the incompatible rules exception
   */
  public void generateProgramsForOneMonth(Date monthDay) throws IncompatibleRulesException {
    List<Date> datesOfMonth = getAllDaysFromMonth(monthDay);
    if (datesOfMonth == null || datesOfMonth.size() == 0) {
      System.out.println("Missing month dates!");
      return;
    }

    for (Date date : datesOfMonth) {
      programRepository.deleteByDate(date);
    }

    List<StationModel> allStations = stationService.findAll();
    List<EmployeeStatusModel> allEmployees = employeeService.findEmployeeStatuses();
    List<ProgramEntity> programsForMonth = new ArrayList<>();
    long generateStartMillis = System.currentTimeMillis();
    for (Date date : datesOfMonth) {
      List<ProgramEntity> programsForDay = null;
      while (programsForDay == null) {
        programsForDay = getProgramsForOneDay(date, allStations, copyEmployeeList(allEmployees));
        if ((System.currentTimeMillis() - generateStartMillis) > 60 * 1000) {
          throw new IncompatibleRulesException();
        }
      }
      addEmployeeWorkingHoursFromPrograms(allEmployees, programsForDay);
      programsForMonth.addAll(programsForDay);
    }

    programRepository.save(programsForMonth);
  }

  /**
   * Adds the employee working hours from programs.
   *
   * @param employees the employees
   * @param programs the programs
   */
  private void addEmployeeWorkingHoursFromPrograms(List<EmployeeStatusModel> employees, List<ProgramEntity> programs) {
    if (employees == null || programs == null) {
      return;
    }

    for (ProgramEntity program : programs) {
      for (EmployeeStatusModel employee : employees) {
        if (employee.getId() == program.getEmployee().getId()) {
          employee.setWorkedHours(employee.getWorkedHours() + program.getWorkedHours());
        }
      }
    }
  }

  /**
   * Gets the all days from month.
   *
   * @param monthDay the month day
   * @return the all days from month
   */
  private List<Date> getAllDaysFromMonth(Date monthDay) {
    if (monthDay == null) {
      return null;
    }

    List<Date> dates = new ArrayList<>();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(monthDay);
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    int month = calendar.get(Calendar.MONTH);
    while (month == calendar.get(Calendar.MONTH)) {
      dates.add(calendar.getTime());
      calendar.add(Calendar.DAY_OF_MONTH, 1);
    }
    return dates;
  }

  /**
   * Export program for one month.
   *
   * @param dayOfProgram the day of program
   * @return the input stream
   * @throws Exception the exception
   */
  public InputStream exportProgramForOneMonth(Date dayOfProgram) throws Exception {
    XWPFDocument document = new XWPFDocument();
    XWPFTable table = document.createTable();

    CTBody body = document.getDocument().getBody();
    if (!body.isSetSectPr()) {
      body.addNewSectPr();
    }
    CTSectPr section = body.getSectPr();
    if (!section.isSetPgSz()) {
      section.addNewPgSz();
    }
    CTPageSz pageSize = section.getPgSz();
    pageSize.setOrient(STPageOrientation.LANDSCAPE);
    pageSize.setW(BigInteger.valueOf(16840));
    pageSize.setH(BigInteger.valueOf(11900));

    CTTblPr tblpro = table.getCTTbl().getTblPr();
    CTTblBorders borders = tblpro.addNewTblBorders();
    borders.addNewBottom().setVal(STBorder.SINGLE);
    borders.addNewLeft().setVal(STBorder.SINGLE);
    borders.addNewRight().setVal(STBorder.SINGLE);
    borders.addNewTop().setVal(STBorder.SINGLE);
    borders.addNewInsideH().setVal(STBorder.SINGLE);
    borders.addNewInsideV().setVal(STBorder.SINGLE);
    CTTblWidth tblW = tblpro.getTblW();
    tblW.setW(BigInteger.valueOf(5000));
    tblW.setType(STTblWidth.PCT);

    addProgramsOfMonthToTable(dayOfProgram, table);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    document.write(baos);
    document.close();

    return new ByteArrayInputStream(baos.toByteArray());
  }

  /**
   * Adds the programs of month to table.
   *
   * @param dayOfProgram the day of program
   * @param table the table
   */
  private void addProgramsOfMonthToTable(Date dayOfProgram, XWPFTable table) {
    XWPFTableRow row = table.createRow();
    row.getCell(0).setText("");

    List<EmployeeModel> employees = employeeService.findAll();
    for (EmployeeModel employee : employees) {
      row = table.createRow();
      row.getCell(0).setText(employee.getName());
    }

    List<Date> dates = getAllDaysFromMonth(dayOfProgram);
    for (Date date : dates) {
      row = table.getRows().get(1);
      addTextToCell(row.addNewTableCell(), getDayFromDate(date));

      List<ProgramEntity> programs = programRepository.findAllByDate(date);
      for (int i = 0; i < employees.size(); i++) {
        row = table.getRows().get(i + 2);
        int idx = programs.indexOf(new ProgramEntity(employeeService.getEntityFromModel(employees.get(i))));
        String text = "";
        if (idx > -1) {
          text = programs.get(idx).getStation().getName();
        }
        addTextToCell(row.addNewTableCell(), text);
      }
    }
  }

  /**
   * Adds the text to cell.
   *
   * @param cell the cell
   * @param text the text
   */
  private void addTextToCell(XWPFTableCell cell, String text) {
    XWPFParagraph tempParagraph = cell.getParagraphs().get(0);
    tempParagraph.setAlignment(ParagraphAlignment.CENTER);
    XWPFRun tempRun = tempParagraph.createRun();
    tempRun.setText(text);
  }

  /**
   * Gets the day from date.
   *
   * @param date the date
   * @return the day from date
   */
  private String getDayFromDate(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    return cal.get(Calendar.DAY_OF_MONTH) + "";
  }

}

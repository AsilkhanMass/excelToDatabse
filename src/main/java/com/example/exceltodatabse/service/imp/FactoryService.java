package com.example.exceltodatabse.service.imp;

import com.example.exceltodatabse.entity.Factory;
import com.example.exceltodatabse.repository.FactoryRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

@Service
public class FactoryService {
    private final FactoryRepository factoryRepository;

    public FactoryService(FactoryRepository factoryRepository) {
        this.factoryRepository = factoryRepository;
    }

    public Factory add(String name, String invNumber, String serialNumber){
        Factory factory = Factory.builder()
                .name(name)
                .invNumber(invNumber)
                .serialNumber(serialNumber)
                .build();
        return factoryRepository.save(factory);

    }

    public void importExcelData(String filePath) {
        try (FileInputStream file = new FileInputStream(new File(filePath));
             XSSFWorkbook workbook = new XSSFWorkbook(file)) {

            XSSFSheet sheet = workbook.getSheetAt(0);
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0 || row.getRowNum() == 1 || row.getRowNum() == 2 || row.getRowNum() == 3) {
                    continue;
                }
                Factory factory = new Factory();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    switch (cell.getColumnIndex()) {
                        case 1:
                            if (cell.getCellType() == CellType.STRING) {
                                factory.setName(cell.getStringCellValue());
                            }
                            break;
                        case 2:
                            if (cell.getCellType() == CellType.STRING) {
                                factory.setInvNumber(cell.getStringCellValue());
                            }
                            break;
                        case 3:
                            if (cell.getCellType() == CellType.STRING) {
                                factory.setSerialNumber(cell.getStringCellValue());
                            }
                            break;
                        case 4:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    Date date = cell.getDateCellValue();
                                    factory.setDateOfAcceptance(date);
                                }
                            } else if (cell.getCellType() == CellType.STRING) {
                                try {
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    Date date = dateFormat.parse(cell.getStringCellValue());
                                    factory.setDateOfAcceptance(date);
                                }catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        case 5:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    Date date = cell.getDateCellValue();
                                    factory.setDateOfAcceptanceResponsibleStuff(date);
                                }
                            } else if (cell.getCellType() == CellType.STRING) {
                                try {
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    Date date = dateFormat.parse(cell.getStringCellValue());
                                    factory.setDateOfAcceptanceResponsibleStuff(date);
                                }catch (ParseException e) {
                                    e.printStackTrace();
                                }}
                                break;
                        case 6:
                            if (cell.getCellType() == CellType.STRING) {
                                factory.setResponsiblePerson(cell.getStringCellValue());
                            }
                            break;
                        case 7:
                            if (cell.getCellType() == CellType.STRING) {
                                factory.setRealResponsiblePerson(cell.getStringCellValue());
                            }
                            break;
                        case 8:
                            if (cell.getCellType() == CellType.FORMULA) {
                                factory.setPost(cell.getStringCellValue());
                            }
                            break;
                        case 9:
                            if (cell.getCellType() == CellType.FORMULA) {
                                factory.setStuff(cell.getStringCellValue());
                            }
                            break;
                        case 10:
                            if (cell.getCellType() == CellType.STRING) {
                                factory.setBasisTransfer(cell.getStringCellValue());
                            }
                            break;
                        case 11:
                            if (cell.getCellType() == CellType.STRING) {
                                factory.setNote(cell.getStringCellValue());
                            }
                            break;
                        case 12:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                factory.setPercentagePerYearAmortization(cell.getNumericCellValue());
                            }
                            break;
                        case 13:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                factory.setIncome(cell.getNumericCellValue());
                            }
                            break;
                        case 14:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                factory.setPriceForBeggingOfTheNextYear(cell.getNumericCellValue());
                            }
                            break;
                        case 15:
                            if (cell.getCellType() == CellType.FORMULA) {
                                CellValue cellValue = evaluator.evaluate(cell);
                                if(cellValue.getCellType() == CellType.NUMERIC) {
                                    factory.setYearPricePlan(cell.getNumericCellValue());
                                }
//                                 if(cellValue.getCellType() == CellType.STRING){
//                                    factory.setYearPricePlan(cell.getStringCellValue());
//                                }
                            }
                            break;
                        case 16:
                            if (cell.getCellType() == CellType.FORMULA) {
                                CellValue cellValue = evaluator.evaluate(cell);
                                if(cellValue.getCellType() == CellType.NUMERIC) {
                                    factory.setIncreasingPriceInFact(cell.getNumericCellValue());
                                }}
                            break;
                        case 17:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                factory.setJanuary(cell.getNumericCellValue());
                            }
                            break;
                        case 18:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                factory.setFebruary(cell.getNumericCellValue());
                            }
                            break;
                        case 19:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                factory.setMarch(cell.getNumericCellValue());
                            }
                            break;
                        case 20:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                factory.setApril(cell.getNumericCellValue());
                            }
                            break;
                        case 21:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                factory.setMay(cell.getNumericCellValue());
                            }
                            break;
                        case 22:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                factory.setJune(cell.getNumericCellValue());
                            }
                            break;
                        case 23:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                factory.setJule(cell.getNumericCellValue());
                            }
                            break;
                        case 24:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                factory.setAugust(cell.getNumericCellValue());
                            }
                            break;
                        case 25:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                factory.setSeptember(cell.getNumericCellValue());
                            }
                            break;
                        case 26:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                factory.setOctober(cell.getNumericCellValue());
                            }
                            break;
                        case 27:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                factory.setNovember(cell.getNumericCellValue());
                            }
                            break;
                        case 28:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                factory.setDecember(cell.getNumericCellValue());
                            }
                            break;
                        case 29:
                            if (cell.getCellType() == CellType.FORMULA) {
                                CellValue cellValue = evaluator.evaluate(cell);
                                if(cellValue.getCellType() == CellType.NUMERIC) {
                                    factory.setPriceForEndOfPeriod(cell.getNumericCellValue());
                                }}
                            break;
                    }
                }
                factoryRepository.save(factory);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

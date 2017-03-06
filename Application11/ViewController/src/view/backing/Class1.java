package view.backing;

import java.io.IOException;
import java.io.InputStream;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.UploadedFile;


import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Class1 {
    
    private RichTable empTable;
    
    public void setEmpTable(RichTable empTable) {
        this.empTable = empTable;
    }

    public RichTable getEmpTable() {
        return empTable;
    }
    public Class1() {
        super();
    }


  public BindingContainer getBindingsCont() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /**
     * Generic Method to execute operation
     * */
    public OperationBinding executeOperation(String operation) {
        OperationBinding createParam = getBindingsCont().getOperationBinding(operation);
        return createParam;

    }

    /**
     * Method to read xlsx file and upload to table.
     * @param myxls
     * @throws IOException
     */
    public void readNProcessExcelx(InputStream xlsx) throws IOException {
        System.out.println("5");
        CollectionModel cModel = (CollectionModel) empTable.getValue();
        System.out.println("6");
        JUCtrlHierBinding tableBinding = (JUCtrlHierBinding) cModel.getWrappedData();
        //Acess the ADF iterator binding that is used with ADF table binding
        System.out.println("7");
        DCIteratorBinding iter = tableBinding.getDCIteratorBinding();
        System.out.println("8");
        //Use XSSFWorkbook for XLS file
        XSSFWorkbook WorkBook = null;
        System.out.println("9");
        int sheetIndex = 0;
        System.out.println("10");
        try {
            WorkBook = new XSSFWorkbook(xlsx);
            System.out.println("11");
        } catch (IOException e) {
            System.out.println("12");

        }
        XSSFSheet sheet = WorkBook.getSheetAt(sheetIndex);
        System.out.println("13");
        Integer skipRw = 1;
        Integer skipcnt = 1;

        //Iterate over excel rows
        for (Row tempRow : sheet) {
            System.out.println("14");
            if (skipcnt > skipRw) {
                    System.out.println("15");//skip first n row for labels.
                //Create new row in table
                executeOperation("CreateInsert").execute();
                System.out.println("16");
                //Get current row from iterator
                oracle.jbo.Row row = iter.getNavigatableRowIterator().getCurrentRow();
                System.out.println("17");
                int Index = 0;
                //Iterate over row's columns
                for (int column = 0; column < tempRow.getPhysicalNumberOfCells(); column++) {
                    System.out.println("18");
                    Cell MytempCell = tempRow.getCell(column);
                    System.out.println("19");
                    if (MytempCell != null) {
                        System.out.println("20");
                        Index = MytempCell.getColumnIndex();
                        System.out.println("21");
                    } else {
                        System.out.println("22");
                        Index++;
                    }
                    if (Index == 0) {
                        System.out.println("23");
                        row.setAttribute("CountryId", MytempCell.getStringCellValue());
                        System.out.println("24");

                    } else if (Index == 1) {
                        System.out.println("25");
                        row.setAttribute("CountryName", MytempCell.getStringCellValue());
                        System.out.println("26");
             
                    }/* else if (Index == 4) {    System.out.println("27");
                        row.setAttribute("RegionId", MytempCell.getStringCellValue());
                        System.out.println("28");
       
              
                 
                    }*/
                }
            }
            skipcnt++;
        }
        executeOperation("Execute").execute();
    }


    /**Method to read xls file and upload to table.
     * @param xls
     * @throws IOException
     */
    public void readNProcessExcel(InputStream xls) throws IOException {

        CollectionModel cModel = (CollectionModel) empTable.getValue();
        JUCtrlHierBinding tableBinding = (JUCtrlHierBinding) cModel.getWrappedData();
        DCIteratorBinding iter = tableBinding.getDCIteratorBinding();

        //Use HSSFWorkbook for XLS file
        HSSFWorkbook WorkBook = null;

        int sheetIndex = 0;

        try {
            WorkBook = new HSSFWorkbook(xls);
        } catch (IOException e) {
            System.out.println("Exception : " + e);
        }

        HSSFSheet sheet = WorkBook.getSheetAt(sheetIndex);

        Integer skipRw = 1;
        Integer skipcnt = 1;
        Integer sno = 1;

        //Iterate over excel rows
        for (Row tempRow : sheet) {
            System.out.println(skipcnt + "--" + skipRw);
            if (skipcnt > skipRw) { //skip first n row for labels.
                //Create new row in table
                executeOperation("CreateInsert").execute();
                //Get current row from iterator
                oracle.jbo.Row row = iter.getNavigatableRowIterator().getCurrentRow();

                int Index = 0;
                //Iterate over row's columns
                for (int column = 0; column < tempRow.getPhysicalNumberOfCells(); column++) {
                    Cell MytempCell = tempRow.getCell(column);
                    if (MytempCell != null) {
                        Index = MytempCell.getColumnIndex();
                    } else {
                        Index++;
                    }
                    if (Index == 0) {
                        row.setAttribute("CountryId", MytempCell.getNumericCellValue());

                    } else if (Index == 1) {
                        row.setAttribute("CountryName", MytempCell.getStringCellValue());
                        } else if (Index == 4) {
                            row.setAttribute("RegionId", MytempCell.getStringCellValue());



              
                
                    }
                }
                sno++;
            }
            skipcnt++;
        }
        //Execute table viewObject
        executeOperation("Execute").execute();
    }

    /**Method to upload XLS/XLSX file and read data from table
     * @param valueChangeEvent
     */
    public void uploadFileVCE(ValueChangeEvent valueChangeEvent) {
        System.out.println("1");
        UploadedFile file = (UploadedFile) valueChangeEvent.getNewValue();
        System.out.println("2");

        try {
            //Check if file is XLSX
            System.out.println("3");

            if (file.getContentType().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") ||
                file.getContentType().equalsIgnoreCase("application/xlsx")) {
                System.out.println("4");

                readNProcessExcelx(file.getInputStream()); //for xlsx
            

            }
            //Check if file is XLS
            
            else if (file.getContentType().equalsIgnoreCase("application/vnd.ms-excel")) {
                System.out.println("6");

                if (file.getFilename().toUpperCase().endsWith(".XLS")) {
                    System.out.println("7");

                    readNProcessExcel(file.getInputStream()); //for xls
                }

            } else {
                FacesMessage msg = new FacesMessage("File format not supported.-- Upload XLS or XLSX file");
                msg.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(empTable);

        } catch (IOException e) {
            // TODO
        }
    }

 
}


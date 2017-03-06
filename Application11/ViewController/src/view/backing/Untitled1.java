package view.backing;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.adapter.dataformat.csv.CSVParser;
import oracle.adf.view.rich.component.rich.RichDocument;
import oracle.adf.view.rich.component.rich.RichForm;
import oracle.adf.view.rich.component.rich.input.RichInputNumberSpinbox;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.output.RichMessages;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.model.UploadedFile;

public class Untitled1 {
    private RichForm f1;
    private RichDocument d1;
    private RichMessages m1;
    private RichPanelFormLayout pfl1;
    private RichInputText it2;
    private RichInputNumberSpinbox it1;
    private RichCommandButton cb1;
    private RichCommandButton cb2;

    public void setF1(RichForm f1) {
        this.f1 = f1;
    }

    public RichForm getF1() {
        return f1;
    }

    public void setD1(RichDocument d1) {
        this.d1 = d1;
    }

    public RichDocument getD1() {
        return d1;
    }


    public void setM1(RichMessages m1) {
        this.m1 = m1;
    }

    public RichMessages getM1() {
        return m1;
    }


    public void parse(String[] lineValues){
        BindingContainer bdCont=BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operation = bdCont.getOperationBinding("createRow");
       operation.getParamsMap().put("lineValues", lineValues);
       operation.execute();


    }
    public void fileUploadValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println(" uploading file ");
        UploadedFile file = (UploadedFile)valueChangeEvent.getNewValue();
        

        try {
           CSVParser csvParser = new CSVParser(file.getInputStream());
            
            //skip the first line since it holds the header
            csvParser.nextLine();
            
            while(csvParser.nextLine()){
                String[] lineValues = csvParser.getLineValues();
                this.parse(lineValues);
            }
                       
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPfl1(RichPanelFormLayout pfl1) {
        this.pfl1 = pfl1;
    }

    public RichPanelFormLayout getPfl1() {
        return pfl1;
    }


    public void setIt2(RichInputText it2) {
        this.it2 = it2;
    }

    public RichInputText getIt2() {
        return it2;
    }

    public void setIt1(RichInputNumberSpinbox it1) {
        this.it1 = it1;
    }

    public RichInputNumberSpinbox getIt1() {
        return it1;
    }

    public void setCb1(RichCommandButton cb1) {
        this.cb1 = cb1;
    }

    public RichCommandButton getCb1() {
        return cb1;
    }

    public void setCb2(RichCommandButton cb2) {
        this.cb2 = cb2;
    }

    public RichCommandButton getCb2() {
        return cb2;
    }
}

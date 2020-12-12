package ar.edu.unsl.frontend.view_controllers;

import java.net.URL;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.PropertyValueFactory;

public abstract class TableViewCntlr extends ViewCntlr
{
    private List<ObservableList> tableDataLists;

    private List<SelectionMode> selectionModes;

    private List<TableView> tables;

    private List<TableColumn> columns;

    private List<PropertyValueFactory> propertiesValues;

    // ================================= FXML variables =================================

        //column2.setCellFactory(TextFieldTableCell.forTableColumn());

        //column2.setOnEditCommit(e->
        //{
        //    e.getTableView().getItems().get(e.getTablePosition().getRow()).setAlias(e.getNewValue());
        //});

        //resultTable.setEditable(true);

    // ================================= FXML methods ===================================

    // ================================= private methods ================================

    /**
     * This method initialize all the registered tables. If the quantity of columns
     * is greater than the quantity of properties an exception will be thrown. If
     * the quantity of properties is greather than the quantity of columns, the
     * remaining properties simply will be ignored.
     */
    private void initTables()
    {
        for (int i = 0; i < this.tables.size(); i++)
        {
            this.tableDataLists.add(FXCollections.observableArrayList());
            this.tables.get(i).setItems(this.tableDataLists.get(i));
        }

        Iterator<TableColumn> columnsIterator = this.columns.iterator();
        Iterator<PropertyValueFactory> propertiesValuesIterator = this.propertiesValues.iterator();

        while (columnsIterator.hasNext())
            columnsIterator.next().setCellValueFactory(propertiesValuesIterator.next());
        
    }

    // ================================= protected methods =============================
    protected void registerTable(TableView table)
    {
        this.tables.add(table);
    }

    protected void registerTables(List<TableView> tables)
    {
        this.tables.addAll(tables);
    }

    protected void registerColumn(TableColumn column)
    {
        this.columns.add(column);
    }

    protected void registerColumns(List<TableColumn> columns)
    {
        this.columns.addAll(columns);
    }

    protected void registerPropertiesValues(PropertyValueFactory propertyValue)
    {

        this.propertiesValues.add(propertyValue);
    }

    protected void registerPropertiesValues(List<PropertyValueFactory> propertiesValues)
    {
        this.propertiesValues.addAll(propertiesValues);
    }

    protected void loadData(int tableNumber, Object data)
    {
        this.tableDataLists.get(tableNumber).add(data);
    }

    protected void loadData(int tableNumber, int position, Object data)
    {
        this.tableDataLists.get(tableNumber).add(position, data);
    }

    protected void loadData(int tableNumber, List data)
    {
        this.tableDataLists.get(tableNumber).addAll(data);
    }

    protected void loadData(int tableNumber, int position, List data)
    {
        this.tableDataLists.get(tableNumber).addAll(position, data);
    }

    protected void filterTable(int tableNumber, Predicate predicate)
    {
        //this.tableDataLists.get(tableNumber).filtered(predicate);
        //FilteredList<Entity> filteredData = new FilteredList<>(this.tableDataLists.get(tableNumber), s -> true);
        //filteredData.setPredicate(predicate);
        this.tables.get(tableNumber).setItems(this.tableDataLists.get(tableNumber).filtered(predicate)); 
    }

    // ================================= public methods =================================

    /**
     * This method is called inside the initialize method. IMPORTANT: This method is
     * supposed to be used for register the tables, the columns and the properties
     * values. Also, the order in which you register the table, columns and
     * properties values is important to match all the elements correctly.
     * Tables id will be: 0 for the first registered table, 1 for the second and so on.
     */
    public abstract void customTableViewInitialize(URL location, ResourceBundle resources) throws Exception;

    @Override
    public void customInitialize(URL location, ResourceBundle resources) throws Exception
    {
        this.tables = new ArrayList<>();
        this.columns = new ArrayList<>();
        this.propertiesValues = new ArrayList<>();
        this.tableDataLists = new ArrayList<>();
        
        try
        {
            this.customTableViewInitialize(location, resources);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        this.initTables();
    }
}
package ar.edu.unsl.backend.util;

import java.util.List;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyCode;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class AutoCompletionTool
{
    private TextField textField;
    private ObservableList<String> suggestions;
    private ListView<String> suggestionsList;
    private double listHeight = 200;

    public AutoCompletionTool(TextField textField, List<String> suggestions)
    {
        this.textField = textField;
        this.suggestions = FXCollections.observableArrayList(suggestions);
        this.listHeight = 200;
        this.suggestionsList = new ListView<>();
        this.suggestionsList.setVisible(false);

        try
        {
            this.build(false);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public AutoCompletionTool(TextField textField, List<String> suggestions, double listHeight)
    {
        this.textField = textField;
        this.suggestions = FXCollections.observableArrayList(suggestions);
        this.listHeight = listHeight;
        this.suggestionsList = new ListView<>();
        this.suggestionsList.setVisible(false);

        try
        {
            this.build(false);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public AutoCompletionTool(TextField textField, List<String> suggestions, ListView<String> suggestionsList)
    {
        this.textField = textField;
        this.suggestions = FXCollections.observableArrayList(suggestions);
        this.suggestionsList = suggestionsList;
        this.suggestionsList.setVisible(false);

        try
        {
            this.build(true);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public TextField getTextField()
    {
        return this.textField;
    }

    public void setTextField(TextField textField)
    {
        this.textField = textField;
    }

    public ObservableList<String> getSuggestions()
    {
        return this.suggestions;
    }

    public void setSuggestions(ObservableList<String> suggestions)
    {
        this.suggestions = suggestions;
    }

    public ListView<String> getSuggestionsList()
    {
        return this.suggestionsList;
    }

    public void setSuggestionsList(ListView<String> suggestionsList)
    {
        this.suggestionsList = suggestionsList;
    }

    private void build(Boolean customListView) throws Exception
    {
        // setting listView sizes if not custom list view was indicated
        if(!customListView)
        {
            this.suggestionsList.setPrefSize(this.textField.getPrefWidth(), this.listHeight);
            this.suggestionsList.setMinHeight(this.suggestionsList.getPrefHeight());
            this.suggestionsList.setMaxHeight(this.suggestionsList.getPrefHeight());
            this.suggestionsList.setMinWidth(this.suggestionsList.getPrefWidth());
            this.suggestionsList.setMaxWidth(this.suggestionsList.getPrefWidth());
            this.suggestionsList.setLayoutX(this.textField.getLayoutX());
            this.suggestionsList.setLayoutY(this.textField.getLayoutY()+this.textField.getPrefHeight()+1);

            if(this.textField.getParent() != null)
            {
                ((Pane) this.textField.getParent()).getChildren().add(this.suggestionsList);
            }
            else
            {
                throw new Exception("no parent pane found for this textfield. Make sure that the textfield is contained in some pane");
            }
        }
        
        // setting events listeners
        this.suggestionsList.setOnKeyPressed(event ->
        {
            if(this.suggestionsList.isVisible())
            {
                if( event.getCode() == KeyCode.ENTER )
                {
                    this.textField.setText(this.suggestionsList.getSelectionModel().getSelectedItem());
                    this.textField.requestFocus();
                    this.suggestionsList.setVisible(false);
                }
                else if(event.getCode() == KeyCode.ESCAPE)
                {
                    this.textField.requestFocus();
                    this.suggestionsList.setVisible(false);
                }
            }
        });

        this.suggestionsList.setOnMouseClicked(event ->
        {
            this.textField.setText(this.suggestionsList.getSelectionModel().getSelectedItem());
            this.textField.requestFocus();
            this.suggestionsList.setVisible(false);
        });

        FilteredList<String> filteredData = new FilteredList<>(this.suggestions, s -> true);
        this.textField.textProperty().addListener(obs->
        {
            this.suggestionsList.setVisible(true);

            String filter = this.textField.getText();
            if(filter == null || filter.length() == 0)
            {
                filteredData.setPredicate(s -> true);
            }
            else
            {
                filteredData.setPredicate(s -> s.contains(filter) || s.toLowerCase().contains(filter));
            }

            this.textField.setOnKeyPressed(event ->
            {
                if(this.suggestionsList.isVisible())
                {
                    if(event.getCode() == KeyCode.DOWN)
                    {
                        this.suggestionsList.requestFocus();
                    }
                    else if(event.getCode() == KeyCode.ESCAPE)
                    {
                        this.suggestionsList.setVisible(false);
                    }
                }     
            });
        });

        this.suggestionsList.setItems(filteredData);
    }
}
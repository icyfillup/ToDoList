package com.sargent.mark.todolist.data;

/**
 * Created by mark on 7/4/17.
 */

public class ToDoItem {
    private String description;
    private String dueDate;
    private String categoryItem;

    //NOTE[Philip]: added the categoryItem in a pojo and updated the constructor and added its getter and setter
    public ToDoItem(String description, String dueDate, String categoryItem) {
        this.description = description;
        this.dueDate = dueDate;
        this.categoryItem = categoryItem;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getCategoryItem() {
        return categoryItem;
    }

    public void setCategoryItem(String categoryItem) {
        this.categoryItem = categoryItem;
    }
}

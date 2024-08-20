package com.yb.digilib.model;

import com.yb.digilib.view.ViewFactory;

/**
 * Singleton class that serves as the central model of the application.
 * It provides global access to the ViewFactory, which manages the application's views.
 */
public class AppModel {
    private static AppModel instance;
    private final ViewFactory viewFactory;

    /**
     * Private constructor to prevent direct instantiation.
     * Initializes the ViewFactory instance.
     */
    private AppModel() {
        this.viewFactory = new ViewFactory();
        this.viewFactory.getSelectedMenuItem().set("dashboardOverview");
    }


    /**
     * Returns the single instance of AppModel, creating it if necessary.
     *
     * @return the singleton instance of AppModel.
     */
    public static AppModel getInstance() {
        if (instance == null) {
            instance = new AppModel();
        }
        return instance;
    }


    /**
     * Returns the ViewFactory instance, which manages the application's views.
     *
     * @return the ViewFactory instance.
     */
    public ViewFactory getViewFactory() {
        return viewFactory;
    }
}

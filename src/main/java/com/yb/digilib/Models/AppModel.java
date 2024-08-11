package com.yb.digilib.Models;

import com.yb.digilib.Views.ViewFactory;

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

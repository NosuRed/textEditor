package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Country {

    /**
     * The attributes reflecting one country. Since these objects will be
     * observed by GUI elements, they are declared as observable properties.
     */
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty captital = new SimpleStringProperty();
    private SimpleIntegerProperty area = new SimpleIntegerProperty();
    private SimpleIntegerProperty population = new SimpleIntegerProperty();

    /**
     * The constructor stores parameter in properties to make them observable.
     *
     * @param name name of the country
     * @param capital capital of the country
     * @param area area of the country
     * @param population population of the country
     */
    Country(String name, String capital, int area, int population) {
        this.name = new SimpleStringProperty(name);
        this.captital = new SimpleStringProperty(capital);
        this.area = new SimpleIntegerProperty(area);
        this.population = new SimpleIntegerProperty(population);
    }

    /*
      The folling Getter-Methods will be accessed via reflections! Warnings of IDEs
      they might not be used have to be ignored! They all have to be public, because
      they will be accessed by classes of the GUI framework, which are not part of
      the package of these program.
     */

    /**
     * Getter method to get the name of a country.
     *
     * @return name of country
     */
    public String getName() {
        return this.name.get();
    }

    /**
     * Getter method to get the capital of a country.
     *
     * @return capital of country
     */
    public String getCapital() {
        return this.captital.get();
    }

    /**
     * Getter method to get the area of a country.
     *
     * @return area of country
     */
    public int getArea() {
        return this.area.get();
    }

    /**
     * Getter method to get the population of a country.
     *
     * @return population of country
     */
    public int getPopulation() {
        return this.population.get();
    }
}

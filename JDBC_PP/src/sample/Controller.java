package sample;

import javafx.collections.ObservableList;

/**
 * Controller as bridge between view and model classes
 */
public class Controller {

    private CountryDao countryDao = new CountryDao();

    /**
     * Passes filename of DB to DAO.
     *
     * @param filename filename of the DB
     */
    public void setFilename(String filename) {
        countryDao.setFilename(filename);
    }

    /**
     * Passes filter for country names to DAO.
     *
     * @param filterName filter term for filtering countries by name
     */
    public void setFilterName(String filterName) {
        countryDao.setFilterName(filterName);
    }

    /**
     * Passes list of queried countries from DAO.
     *
     * @return list with all queried countries
     */
    public ObservableList<Country> getCountries() {
        return countryDao.getCountries();
    }

    /**
     * Passes mean population of all queried countries from DAO.
     *
     * @return mean population of all queried countries from DAO
     */
    public long getMeanPopulation() {
        return countryDao.getMeanPopulation();
    }

    public void setCountryName(String countryName){
        countryDao.setCountryName(countryName);
    }

}
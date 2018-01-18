package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

class CountryDao {
        private String countryName;
        private String test;
        private String name;
        private String capital;
        private int area;
        private int population;
        private String sqlQuery;
        private long sum = 0;
        private int size = 0;
        private long mean = 0;


    public void setCountryName(String countryName) {
            this.countryName = countryName;
            query();
        }

        protected CountryDao(){
            String driver = "org.sqlite.JDBC";
            try {
                Class.forName(driver);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


        /**
         * Filename of SQLite-file.
         */
        private String filename = null;

        /**
         * Filter term for requested country names.
         */
        private String filterName = null;

        /**
         * List with countries from SQLite-response.
         */
        private final ObservableList<Country> countries = FXCollections.observableArrayList();

        /**
         * Setter method to set filename of SQLite-file to connect to.
         *
         * @param filename Filename of SQLite-file to connect to.
         */
        public void setFilename(String filename) {
            this.filename = filename;
            query();
        }

        /**
         * Setter method to set filter term for countries.
         *
         * @param filterName filter term for filtering countries by name
         */
        public void setFilterName(String filterName) {
            // TODO: trim parameter
            // TODO: check, if parameter triggers new query (length ≥3 or switching to <3)
            // TODO: query, if needed
            if (filterName.length() > 3 ){
                System.out.println(filterName);
            }

        }

        /**
         * Getter method to get observable list of all countries. Use this method to connect DAO
         * with GUI.
         *
         * @return Observable list with all countries of the last query.
         */
        public ObservableList<Country> getCountries() {
            return this.countries;
        }

        /**
         * Returns the mean population of all countries of the current query.
         *
         * @return mean population of all countries of the current query.
         */
        public long getMeanPopulation() {
            for (int i = 0; i < countries.size();i++ ){

            }
            return mean;

        }

        /**
         * Private method to trigger a SQL query. The method builds up a connection to the
         * SQLite-DB, executes a query with the current filter settings and closes the
         * connection again.
         */
        private void query() {
            synchronized (this.countries) {
               this.countries.clear();
               String jdbc = "jdbc:sqlite";
               String url = jdbc + ":" + filename;
               this.sqlQuery = "SELECT name, capital, area, population FROM Country";

               try {
                    PreparedStatement preparedStatement;
                    try (Connection connection = DriverManager.getConnection(url)) {
                        preparedStatement = connection.prepareStatement(sqlQuery);
                        //preparedStatement.setString(1, countryName);
                        ResultSet cursor = preparedStatement.executeQuery();
                        while (cursor.next()) {
                            String name = cursor.getString(1);
                            String capital = cursor.getString(2);
                            int area = cursor.getInt(3);
                            int population = cursor.getInt(4);
                            //System.out.println(name + " " + capital + " " + area + " " + population);
                            Country country = new Country(name, capital, area, population);
                            countries.addAll(country);
                            this.population = country.getPopulation();
                            for (int i = 0; i < countries.size(); i++){
                                this.population = population;
                            }

                            this.size = countries.size();
                            this.sum += population;
                            this.mean = sum / size;
                        }
                    }
                    } catch (SQLException e) {
                    e.printStackTrace();
                }

                // TODO: clear list of countries
                // TODO: preparequery
                // TODO: connect to db
                // TODO: prepare statement
                // TODO: execute query
                // TODO: populate list with countries from result set
                // TODO: close statement
                // TODO: close connection
            }
        }
}

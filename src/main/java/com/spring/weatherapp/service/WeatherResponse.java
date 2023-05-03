package com.spring.weatherapp.service;


import java.util.List;

public class WeatherResponse {

    private List<TimestampWeather> list;
    private City city;


    public List<TimestampWeather> getList() {
        return list;
    }

    public void setList(List<TimestampWeather> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public static class TimestampWeather {

        private Main main;
        private List<WeatherInfo> weather;
        private Clouds clouds;
        private Wind wind;
        private Integer visibility;
        private String dt_txt;

        public Main getMain() {
            return main;
        }

        public void setMain(Main main) {
            this.main = main;
        }

        public List<WeatherInfo> getWeather() {
            return weather;
        }

        public void setWeather(List<WeatherInfo> weather) {
            this.weather = weather;
        }

        public Clouds getClouds() {
            return clouds;
        }

        public void setClouds(Clouds clouds) {
            this.clouds = clouds;
        }

        public Wind getWind() {
            return wind;
        }

        public void setWind(Wind wind) {
            this.wind = wind;
        }

        public Integer getVisibility() {
            return visibility;
        }

        public void setVisibility(Integer visibility) {
            this.visibility = visibility;
        }

        public String getDt_txt() {
            return dt_txt;
        }

        public void setDt_txt(String dt_txt) {
            this.dt_txt = dt_txt;
        }

        public static class Main{
            private Double temp;
            private Double feels_like;
            private Integer pressure;
            private Integer humidity;

            public Double getTemp() {
                return temp;
            }

            public void setTemp(Double temp) {
                this.temp = temp;
            }

            public Double getFeels_like() {
                return feels_like;
            }

            public void setFeels_like(Double feels_like) {
                this.feels_like = feels_like;
            }

            public Integer getPressure() {
                return pressure;
            }

            public void setPressure(Integer pressure) {
                this.pressure = pressure;
            }

            public Integer getHumidity() {
                return humidity;
            }

            public void setHumidity(Integer humidity) {
                this.humidity = humidity;
            }
        }

        public static class WeatherInfo{
            private String main;
            private String description;

            public String getMain() {
                return main;
            }

            public void setMain(String main) {
                this.main = main;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }

        public static class Clouds{
            private Integer all;

            public Integer getAll() {
                return all;
            }

            public void setAll(Integer all) {
                this.all = all;
            }
        }

        public static class Wind{
            private Double speed;

            public Double getSpeed() {
                return speed;
            }

            public void setSpeed(Double speed) {
                this.speed = speed;
            }
        }


    }

    public static class City {
        private String name;
        private String country;
        private Integer population;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public Integer getPopulation() {
            return population;
        }

        public void setPopulation(Integer population) {
            this.population = population;
        }
    }


}

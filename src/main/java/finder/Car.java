package finder;

import java.time.Year;

public class Car {

    public enum FuelType{
        DIESEL,
        GASOLINE,
        ELECTRIC,
        HYBRID_DIESEL,
        HYBRID_GASOLINE,
        UNKNOWN
    }

    private String licensePlate;
    private String brand;
    private String model;
    private String type;
    private String carBody;
    private int constructionYear;
    private int kilometers;
    private int doors;
    private String colour;
    private double price;
    private FuelType fuelType;

    public Car(String licensePlate, String brand, String model, String type, String carBody, int constructionYear, int kilometers, int doors, String colour, double price, FuelType fuelType) {
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.carBody = carBody;
        this.constructionYear = constructionYear;
        this.kilometers = kilometers;
        this.doors = doors;
        this.colour = colour;
        this.price = price;
        this.fuelType = fuelType;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCarBody() {
        return carBody;
    }

    public void setCarBody(String carBody) {
        this.carBody = carBody;
    }

    public int getConstructionYear() {
        return constructionYear;
    }

    public void setConstructionYear(int constructionYear) {
        this.constructionYear = constructionYear;
    }

    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    public int getDoors() {
        return doors;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }
}

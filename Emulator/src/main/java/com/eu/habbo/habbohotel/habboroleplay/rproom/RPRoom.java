package com.eu.habbo.habbohotel.habboroleplay.rproom;

public class RPRoom {
    private int id;
    private String cityRP;
    private boolean hospitalRP;
    private boolean prisonRP;
    private boolean courtRP;
    private boolean prisonBackRP;
    private boolean camioneroRP;
    private boolean mecanicoRP;
    private boolean basureroRP;
    private boolean armeroRP;
    private boolean polStationRP;

    public RPRoom(int id, String cityRP, boolean hospitalRP, boolean prisonRP, boolean courtRP, boolean prisonBackRP, boolean camioneroRP, boolean mecanicoRP, boolean basureroRP, boolean armeroRP, boolean polStationRP) {
        this.id = id;
        this.cityRP = cityRP;
        this.hospitalRP = hospitalRP;
        this.prisonRP = prisonRP;
        this.courtRP = courtRP;
        this.prisonBackRP = prisonBackRP;
        this.camioneroRP = camioneroRP;
        this.mecanicoRP = mecanicoRP;
        this.basureroRP = basureroRP;
        this.armeroRP = armeroRP;
        this.polStationRP = polStationRP;
    }

    public int getId() {
        return id;
    }

    public String getCityRP() {
        return cityRP;
    }

    public boolean isHospitalRP() {
        return hospitalRP;
    }

    public boolean isPrisonRP() {
        return prisonRP;
    }

    public boolean isCourtRP() {
        return courtRP;
    }

    public boolean isPrisonBackRP() {
        return prisonBackRP;
    }

    public boolean isCamioneroRP() {
        return camioneroRP;
    }

    public boolean isMecanicoRP() {
        return mecanicoRP;
    }

    public boolean isBasureroRP() {
        return basureroRP;
    }

    public boolean isArmeroRP() {
        return armeroRP;
    }

    public boolean isPolStationRP() {
        return polStationRP;
    }
}

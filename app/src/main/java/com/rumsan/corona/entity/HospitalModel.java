package com.rumsan.corona.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HospitalModel {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("is_full")
    @Expose
    private Boolean isFull;
    @SerializedName("government_approved")
    @Expose
    private Boolean governmentApproved;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("contact_person")
    @Expose
    private String contactPerson;
    @SerializedName("contact_person_number")
    @Expose
    private String contactPersonNumber;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("hospital_id")
    @Expose
    private String hospitalId;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("capacity")
    @Expose
    private Capacity capacity;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Boolean getIsFull() {
        return isFull;
    }

    public void setIsFull(Boolean isFull) {
        this.isFull = isFull;
    }

    public Boolean getGovernmentApproved() {
        return governmentApproved;
    }

    public void setGovernmentApproved(Boolean governmentApproved) {
        this.governmentApproved = governmentApproved;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPersonNumber() {
        return contactPersonNumber;
    }

    public void setContactPersonNumber(String contactPersonNumber) {
        this.contactPersonNumber = contactPersonNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Capacity getCapacity() {
        return capacity;
    }

    public void setCapacity(Capacity capacity) {
        this.capacity = capacity;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public class Capacity {
        @SerializedName("beds")
        @Expose
        private String beds;
        @SerializedName("ventilators")
        @Expose
        private String ventilators;
        @SerializedName("isolation_beds")
        @Expose
        private String isolationBeds;
        @SerializedName("occupied_beds")
        @Expose
        private String occupiedBeds;
        @SerializedName("doctors")
        @Expose
        private String doctors;
        @SerializedName("nurses")
        @Expose
        private String nurses;

        public String getBeds() {
            return beds;
        }

        public void setBeds(String beds) {
            this.beds = beds;
        }

        public String getVentilators() {
            return ventilators;
        }

        public void setVentilators(String ventilators) {
            this.ventilators = ventilators;
        }

        public String getIsolationBeds() {
            return isolationBeds;
        }

        public void setIsolationBeds(String isolationBeds) {
            this.isolationBeds = isolationBeds;
        }

        public String getOccupiedBeds() {
            return occupiedBeds;
        }

        public void setOccupiedBeds(String occupiedBeds) {
            this.occupiedBeds = occupiedBeds;
        }

        public String getDoctors() {
            return doctors;
        }

        public void setDoctors(String doctors) {
            this.doctors = doctors;
        }

        public String getNurses() {
            return nurses;
        }

        public void setNurses(String nurses) {
            this.nurses = nurses;
        }
    }
public class Location {
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("coordinates")
    @Expose
    private List<Double> coordinates = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }
}

    @Override
    public String toString() {
        return "HospitalModel{" +
                "name='" + name + '\'' +
                '}';
    }
}

package utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Booking {

    private String bookingId;
    private String userId;
    private String stationCO;
    private String stationCI;
    private String co;
    private String ci;
    private String carGroup;
    private List<String> priceOptions = new ArrayList<>();
    private String deductible;
    private String state;
    private String createdAt;
    private String payNow;

    private String firstName;
    private String lastName;
    private Date birthDay;
    private String city;
    private String street;
    private String zip;
    private String country;
    private String phone;
    private String mobile;
    private String email;
    private String cardId;
    private String lastNumbers;
    private String customerLang;
    private String companyName;
    private String businessStreet;
    private String businessCity;
    private String businessZipCode;
    private String businessCountry;
    private String businessCardId;
    private String businessLastNumbers;
    private String shortHash;
    private Boolean rsoProcessed;



    public String getCustomerLang() {
        return customerLang;
    }

    public void setCustomerLang(String customerLang) {
        this.customerLang = customerLang;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStationCO() {
        return stationCO;
    }

    public void setStationCO(String stationCO) {
        this.stationCO = stationCO;
    }

    public String getStationCI() {
        return stationCI;
    }

    public void setStationCI(String stationCI) {
        this.stationCI = stationCI;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getCarGroup() {
        return carGroup;
    }

    public void setCarGroup(String carGroup) {
        this.carGroup = carGroup;
    }

    public List<String> getPriceOptions() {
        return priceOptions;
    }

    public void setPriceOptions(List<String> priceOptions) {
        this.priceOptions = priceOptions;
    }

    public String getDeductible() {
        return deductible;
    }

    public void setDeductible(String deductible) {
        this.deductible = deductible;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getLastNumbers() {
        return lastNumbers;
    }

    public void setLastNumbers(String lastNumbers) {
        this.lastNumbers = lastNumbers;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBusinessStreet() {
        return businessStreet;
    }

    public void setBusinessStreet(String businessStreet) {
        this.businessStreet = businessStreet;
    }

    public String getBusinessCity() {
        return businessCity;
    }

    public void setBusinessCity(String businessCity) {
        this.businessCity = businessCity;
    }

    public String getBusinessZipCode() {
        return businessZipCode;
    }

    public void setBusinessZipCode(String businessZipCode) {
        this.businessZipCode = businessZipCode;
    }

    public String getBusinessCountry() {
        return businessCountry;
    }

    public void setBusinessCountry(String businessCountry) {
        this.businessCountry = businessCountry;
    }

    public String getBusinessCardId() {
        return businessCardId;
    }

    public void setBusinessCardId(String businessCardId) {
        this.businessCardId = businessCardId;
    }

    public String getBusinessLastNumbers() {
        return businessLastNumbers;
    }

    public void setBusinessLastNumbers(String businessLastNumbers) {
        this.businessLastNumbers = businessLastNumbers;
    }

    public String getShortHash() {
        return shortHash;
    }

    public void setShortHash(String shortHash) {
        this.shortHash = shortHash;
    }

    public String getPayNow() {
        return payNow;
    }

    public void setPayNow(String payNow) {
        this.payNow = payNow;
    }

    public Boolean getRsoProcessed() {
        return rsoProcessed;
    }

    public void setRsoProcessed(Boolean rsoProcessed) {
        this.rsoProcessed = rsoProcessed;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BookingUserDto{");
        sb.append("bookingId='").append(bookingId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", stationCO='").append(stationCO).append('\'');
        sb.append(", stationCI='").append(stationCI).append('\'');
        sb.append(", co='").append(co).append('\'');
        sb.append(", ci='").append(ci).append('\'');
        sb.append(", carGroup=").append(carGroup);
        sb.append(", priceOptions=").append(priceOptions);
        sb.append(", deductible=").append(deductible);
        sb.append(", state=").append(state);
        sb.append(", createdAt='").append(createdAt).append('\'');
        sb.append(", payNow=").append(payNow);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", birthDay=").append(birthDay);
        sb.append(", city='").append(city).append('\'');
        sb.append(", street='").append(street).append('\'');
        sb.append(", zip='").append(zip).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", mobile='").append(mobile).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", cardId='").append(cardId).append('\'');
        sb.append(", lastNumbers='").append(lastNumbers).append('\'');
        sb.append(", customerLang=").append(customerLang);
        sb.append(", companyName='").append(companyName).append('\'');
        sb.append(", businessStreet='").append(businessStreet).append('\'');
        sb.append(", businessCity='").append(businessCity).append('\'');
        sb.append(", businessZipCode='").append(businessZipCode).append('\'');
        sb.append(", businessCountry='").append(businessCountry).append('\'');
        sb.append(", businessCardId='").append(businessCardId).append('\'');
        sb.append(", businessLastNumbers='").append(businessLastNumbers).append('\'');
        sb.append(", shortHash='").append(shortHash).append('\'');
        sb.append(", rsoProcessed=").append(rsoProcessed);
        sb.append('}');
        return sb.toString();
    }
}
